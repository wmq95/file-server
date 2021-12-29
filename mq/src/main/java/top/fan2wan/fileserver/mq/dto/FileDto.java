package top.fan2wan.fileserver.mq.dto;

/**
 * @Author: fanT
 * @Date: 2021/12/29 14:07
 * @Description: dto for file
 */
public class FileDto {

    private Long fileId;

    private String ossPath;

    public static final class FileDtoBuilder {
        private Long fileId;
        private String ossPath;

        private FileDtoBuilder() {
        }

        public static FileDtoBuilder aFileDto() {
            return new FileDtoBuilder();
        }

        public FileDtoBuilder withFileId(Long fileId) {
            this.fileId = fileId;
            return this;
        }

        public FileDtoBuilder withOssPath(String ossPath) {
            this.ossPath = ossPath;
            return this;
        }

        public FileDto build() {
            FileDto fileDto = new FileDto();
            fileDto.fileId = this.fileId;
            fileDto.ossPath = this.ossPath;
            return fileDto;
        }
    }
}
