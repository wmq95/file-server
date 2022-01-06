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
import top.fan2wan.fileserver.convert.util.OpenOfficeUtil;
import top.fan2wan.fileserver.convert.util.PdfBoxUtil;
import top.fan2wan.fileserver.mq.dto.FileCallbackDto;
import top.fan2wan.fileserver.mq.dto.FileDto;
import top.fan2wan.fileserver.oss.dto.OssFileDto;
import top.fan2wan.fileserver.oss.service.OssService;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.util.Objects;

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
        String fileName = file.getOssPath().substring(0, lastIndex);
        String fileType = file.getOssPath().substring(lastIndex + 1);

        boolean isPdf = StrUtil.equals(fileType, BusinessCons.PDF);
        boolean needConverted = isPdf ? true : needConverted(fileType);

        if (!isPdf && !needConverted) {
            // 无需转换  并且不是pdf  直接返回
            sendFileCallback(FileCallbackDto.FileCallbackDtoBuilder.aFileCallbackDto()
                    .withFileId(file.getFileId())
                    .withConvertedPath(file.getOssPath())
                    .withStatus(BusinessCons.NO_REQUIRED).build());
            return;
        }

        /**
         * 根据oss路径去下载 文件到临时目录
         */
        String localPath = localFileDir + File.separator + file.getOssPath();
        ossService.download(OssFileDto.OssFileDtoBuilder.anOssFileDto()
                .withOssFilePath(file.getOssPath()).withLocalPath(localPath).build());


        String pdfFilePath;
        String convertedPath;
        if (isPdf) {
            pdfFilePath = localPath;
            convertedPath = file.getOssPath();
        } else {
            pdfFilePath = localPath.replace(fileType, BusinessCons.PDF);

            /**
             * 文件转换
             */
            convertFile2Pdf(localPath, pdfFilePath);

            convertedPath = fileName + BusinessCons.DOT + BusinessCons.PDF;
            ossService.save(OssFileDto.OssFileDtoBuilder.anOssFileDto()
                    .withLocalPath(pdfFilePath)
                    .withOssFilePath(convertedPath).build());
        }

        /**
         * 读取pdf 内容
         */
        String content = readPdfContent(pdfFilePath);

        /**
         * 发送回调
         */
        sendFileCallback(FileCallbackDto.FileCallbackDtoBuilder.aFileCallbackDto()
                .withStatus(BusinessCons.CONVERTED_SUCCESS).withConvertedPath(convertedPath)
                .withFileId(file.getFileId()).withFileContent(content).build());
        /**
         * 清理工作目录...
         */
    }

    private void convertFile2Pdf(String localPath, String pdfFilePath) {
        OpenOfficeUtil util = new OpenOfficeUtil();
        try {
            util.convert2Pdf(localPath, pdfFilePath);
        } catch (ConnectException e) {
            throw new RuntimeException(e);
        }
    }

    private String readPdfContent(String pdfFilePath) {
        PdfBoxUtil pdfBoxUtil = new PdfBoxUtil(localFileDir);
        try {
            return pdfBoxUtil.readPdf(pdfFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
