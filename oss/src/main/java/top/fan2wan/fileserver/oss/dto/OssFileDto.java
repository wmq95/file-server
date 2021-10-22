package top.fan2wan.fileserver.oss.dto;

import com.google.common.base.MoreObjects;

/**
 * @Author: fanT
 * @Date: 2021/10/22 13:08
 * @Description: dot for ossFile
 */
public class OssFileDto implements IOssFile {

    private String savePath;

    private String downloadPath;

    private String fileName;

    private String ossFilePath;

    private long fileSize;

    private String filePolicy;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("savePath", savePath)
                .add("downloadPath", downloadPath)
                .add("fileName", fileName)
                .add("ossFilePath", ossFilePath)
                .add("fileSize", fileSize)
                .add("filePolicy", filePolicy)
                .toString();
    }

    @Override
    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    @Override
    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getOssFilePath() {
        return ossFilePath;
    }

    public void setOssFilePath(String ossFilePath) {
        this.ossFilePath = ossFilePath;
    }

    @Override
    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public String getFilePolicy() {
        return filePolicy;
    }

    public void setFilePolicy(String filePolicy) {
        this.filePolicy = filePolicy;
    }
}
