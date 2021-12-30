package top.fan2wan.fileserver.oss.dto;

import com.google.common.base.MoreObjects;

/**
 * @Author: fanT
 * @Date: 2021/10/22 13:08
 * @Description: dot for ossFile
 */
public class OssFileDto implements IOssFile {
    private String localPath;

    private String fileName;

    private String ossFilePath;

    private long fileSize;

    private String filePolicy;

    @Override
    public String getLocalPath() {
        return localPath;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public String getOssFilePath() {
        return ossFilePath;
    }

    @Override
    public long getFileSize() {
        return fileSize;
    }

    @Override
    public String getFilePolicy() {
        return filePolicy;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("localPath", localPath)
                .add("fileName", fileName)
                .add("ossFilePath", ossFilePath)
                .add("fileSize", fileSize)
                .add("filePolicy", filePolicy)
                .toString();
    }


    public static final class OssFileDtoBuilder {
        private String localPath;
        private String fileName;
        private String ossFilePath;
        private long fileSize;
        private String filePolicy;

        private OssFileDtoBuilder() {
        }

        public static OssFileDtoBuilder anOssFileDto() {
            return new OssFileDtoBuilder();
        }

        public OssFileDtoBuilder withLocalPath(String localPath) {
            this.localPath = localPath;
            return this;
        }

        public OssFileDtoBuilder withFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public OssFileDtoBuilder withOssFilePath(String ossFilePath) {
            this.ossFilePath = ossFilePath;
            return this;
        }

        public OssFileDtoBuilder withFileSize(long fileSize) {
            this.fileSize = fileSize;
            return this;
        }

        public OssFileDtoBuilder withFilePolicy(String filePolicy) {
            this.filePolicy = filePolicy;
            return this;
        }

        public OssFileDto build() {
            OssFileDto ossFileDto = new OssFileDto();
            ossFileDto.ossFilePath = this.ossFilePath;
            ossFileDto.fileName = this.fileName;
            ossFileDto.localPath = this.localPath;
            ossFileDto.filePolicy = this.filePolicy;
            ossFileDto.fileSize = this.fileSize;
            return ossFileDto;
        }
    }
}
