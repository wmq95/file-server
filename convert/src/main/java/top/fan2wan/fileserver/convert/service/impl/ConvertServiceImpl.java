package top.fan2wan.fileserver.convert.service.impl;

import cn.hutool.core.util.StrUtil;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import top.fan2wan.fileserver.convert.cons.BusinessCons;
import top.fan2wan.fileserver.convert.service.IConvertService;
import top.fan2wan.fileserver.convert.util.FutureUtil;
import top.fan2wan.fileserver.convert.util.OpenOfficeUtil;
import top.fan2wan.fileserver.convert.util.TiKaUtil;
import top.fan2wan.fileserver.mq.dto.FileCallbackDto;
import top.fan2wan.fileserver.mq.dto.FileDto;
import top.fan2wan.fileserver.oss.dto.OssFileDto;
import top.fan2wan.fileserver.oss.service.OssService;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * @Author: fanT
 * @Date: 2021/10/22 13:22
 * @Description: impl for convert
 */
@Service
public class ConvertServiceImpl implements IConvertService {

    private final StreamBridge streamBridge;
    private final OssService ossService;

    public ConvertServiceImpl(StreamBridge streamBridge,
                              OssService ossService) {
        this.streamBridge = streamBridge;
        this.ossService = ossService;
    }

    private static Logger logger = LoggerFactory.getLogger(IConvertService.class);
    @Value("${file.tempDir}")
    private String localFileDir;
    private Executor readContentExecutor = new ThreadPoolExecutor(2, 5, 1, TimeUnit.MINUTES,
            new ArrayBlockingQueue<>(5));

    @Override
    public void sendFileCallback(FileCallbackDto fileCallback) {
        //streamBridge.send("", fileCallback);
        logger.info("sendFileCallback, fileId was :{}, status was :{},convertedPath was:{}.content length was :{}",
                fileCallback.getFileId(), fileCallback.getStatus(), fileCallback.getConvertedPath(), fileCallback.getFileContent().length());
    }

    @Override
    public void accept(FileDto file) {

        logger.info("receive file was :{}", file);
        Preconditions.checkArgument(!Strings.isNullOrEmpty(file.getOssPath()), " path can not be empty");
        Preconditions.checkArgument(Objects.nonNull(file.getFileId()), "fileId can not be null");

        int lastIndex = file.getOssPath().lastIndexOf(BusinessCons.DOT);
        Preconditions.checkArgument(lastIndex > 0, "invalid path");
        String fileType = file.getOssPath().substring(lastIndex + 1);

        boolean isPdf = StrUtil.equals(fileType, BusinessCons.PDF);
        boolean needConverted = isPdf ? true : needConverted(fileType);

        if (!isPdf && !needConverted) {
            // 无需转换  并且不是pdf  直接返回
            sendFileCallback(FileCallbackDto.FileCallbackDtoBuilder.aFileCallbackDto()
                    .withFileId(file.getFileId())
                    .withConvertedPath(file.getOssPath())
                    .withStatus(NO_REQUIRED).build());
            return;
        }

        /**
         * 根据oss路径去下载 文件到临时目录
         */
        String localPath = localFileDir + File.separator + file.getOssPath();
        ossService.download(OssFileDto.OssFileDtoBuilder.anOssFileDto()
                .withOssFilePath(file.getOssPath()).withLocalPath(localPath).build());

        CompletableFuture<String> contentFuture = CompletableFuture
                .supplyAsync(() -> parseContent(localPath), readContentExecutor)
                .exceptionally((e) -> null);

        String convertedPath = isPdf ? file.getOssPath() : convertAndUploadPdf(localPath, fileType, file.getOssPath());

        /**
         * 发送回调
         */
        sendFileCallback(FileCallbackDto.FileCallbackDtoBuilder.aFileCallbackDto()
                .withStatus(CONVERTED_SUCCESS).withConvertedPath(convertedPath).withFileId(file.getFileId())
                .withFileContent(FutureUtil.getAsStringOrElse(contentFuture, null))
                .build());

        /**
         * 清理工作目录...
         */
    }

    private String convertAndUploadPdf(String localPath, String fileType, String ossPath) {
        String pdfFilePath = localPath.replace(fileType, BusinessCons.PDF);
        if (convertFile2Pdf(localPath, pdfFilePath)) {
            String pdfOssPath = ossPath.replace(fileType, BusinessCons.PDF);
            ossService.save(OssFileDto.OssFileDtoBuilder.anOssFileDto()
                    .withLocalPath(pdfFilePath)
                    .withOssFilePath(pdfOssPath)
                    .build());
            return pdfOssPath;
        }
        return null;
    }

    private String parseContent(String localPath) {
        try {
            return TiKaUtil.parseContent(localPath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean convertFile2Pdf(String localPath, String pdfFilePath) {
        OpenOfficeUtil util = new OpenOfficeUtil();
        try {
            util.convert2Pdf(localPath, pdfFilePath);
            return true;
        } catch (Exception e) {
            logger.error("failed to convertFile2Pdf,error was :{}", e);
            return false;
        }
    }

    /**
     * 是否需要转换  根据文件类型判断
     *
     * @param fileType 文件类型后缀
     * @return true ->需要
     */
    private boolean needConverted(@NotNull String fileType) {
        // pdf 音频  图片
        switch (fileType) {
            case BusinessCons.PDF:
                return false;
            default:
                return true;
        }
    }
}
