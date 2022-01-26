package top.fan2wan.fileserver.mq.dto;

import com.google.common.base.MoreObjects;

/**
 * @Author: fanT
 * @Date: 2021/12/29 14:14
 * @Description: dto for fileCallback
 */
public class FileCallbackDto {

    private Long fileId;

    private String status;

    private String convertedPath;

    private String fileContent;

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConvertedPath() {
        return convertedPath;
    }

    public void setConvertedPath(String convertedPath) {
        this.convertedPath = convertedPath;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("fileId", fileId)
                .add("status", status)
                .add("convertedPath", convertedPath)
                //.add("fileContent", fileContent)
                .toString();
    }


    public static final class FileCallbackDtoBuilder {
        private Long fileId;
        private String status;
        private String convertedPath;
        private String fileContent;

        private FileCallbackDtoBuilder() {
        }

        public static FileCallbackDtoBuilder aFileCallbackDto() {
            return new FileCallbackDtoBuilder();
        }

        public FileCallbackDtoBuilder withFileId(Long fileId) {
            this.fileId = fileId;
            return this;
        }

        public FileCallbackDtoBuilder withStatus(String status) {
            this.status = status;
            return this;
        }

        public FileCallbackDtoBuilder withConvertedPath(String convertedPath) {
            this.convertedPath = convertedPath;
            return this;
        }

        public FileCallbackDtoBuilder withFileContent(String fileContent) {
            this.fileContent = fileContent;
            return this;
        }

        public FileCallbackDto build() {
            FileCallbackDto fileCallbackDto = new FileCallbackDto();
            fileCallbackDto.setFileId(fileId);
            fileCallbackDto.setStatus(status);
            fileCallbackDto.setConvertedPath(convertedPath);
            fileCallbackDto.setFileContent(fileContent);
            return fileCallbackDto;
        }
    }
}
