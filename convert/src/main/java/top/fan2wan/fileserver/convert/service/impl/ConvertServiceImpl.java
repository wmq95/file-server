package top.fan2wan.fileserver.convert.service.impl;

import cn.hutool.core.util.StrUtil;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(IConvertService.class);
    @Value("${file.tmpDir}")
    private String localFileDir;

    @Override
    public void sendFileCallback(FileCallbackDto fileCallback) {
        streamBridge.send("", fileCallback);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void accept(FileDto file) {

        LOGGER.info("receive file was :{}", file);
        Preconditions.checkArgument(!Strings.isNullOrEmpty(file.getOssPath()), " path can not be empty");
        Preconditions.checkArgument(Objects.nonNull(file.getFileId()), "fileId can not be null");

        /**
         * 是否需要下载转换--只有office 文档需要 mp3 图片等无需 而且如果本身就是pdf 也不用下载
         */
        // 这么取 意味着 文件名里面不能带.  还是换一种方法 取获取最后的.的位置
        int lastIndex = file.getOssPath().lastIndexOf(BusinessCons.DOT);
        Preconditions.checkArgument(lastIndex > 0, "invalid path");
        String fileName = file.getOssPath().substring(0, lastIndex - 1);
        String fileType = file.getOssPath().substring(lastIndex, file.getOssPath().length() - 1);

        boolean needConverted = needConverted(fileType);
        boolean isPdf = StrUtil.equals(fileType, BusinessCons.PDF);

        if (!needConverted && !isPdf) {
            // 无需转换  并且不是pdf  不去读取内同  意味着是一些图片音频之类的
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

        /**
         * 使用openOffice 转成pdf
         * 上传pdf 作为convertedPath 无需转换的文件 就是用原来的ossPath
         */
        String pdfFilePath;
        String convertedPath;
        if (isPdf) {
            pdfFilePath = localPath;
            convertedPath = file.getOssPath();
        } else {
            pdfFilePath = localPath.replace(fileType, BusinessCons.PDF);

            convertFile2Pdf(localPath, pdfFilePath);

            convertedPath = fileName + BusinessCons.PDF;
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
        util.convert2Pdf(localPath, pdfFilePath);
    }

    private String readPdfContent(String pdfFilePath) {
        PdfBoxUtil pdfBoxUtil = new PdfBoxUtil(localFileDir);
        return pdfBoxUtil.readPdf(pdfFilePath);
    }


    private boolean needConverted(@NotNull String fileType) {
        switch (fileType) {
            case BusinessCons.PDF:
                return false;
            default:
                return true;
        }
    }
}
