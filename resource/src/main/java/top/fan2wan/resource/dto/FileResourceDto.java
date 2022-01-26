package top.fan2wan.resource.dto;

import java.util.StringJoiner;

/**
 * @Author: fanT
 * @Date: 2022/1/26 9:34
 * @Description: FileResourceDto
 */
public class FileResourceDto implements IFileResource{

    private Long id;

    private String name;

    private String type;

    private Long size;

    private Long createTime;

    private String convertedPath;

    private String convertedStatus;

    private String path;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Long getSize() {
        return size;
    }

    @Override
    public Long getCreateTime() {
        return createTime;
    }

    @Override
    public String getConvertedPath() {
        return convertedPath;
    }

    @Override
    public String getConvertedStatus() {
        return convertedStatus;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FileResourceDto.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("type='" + type + "'")
                .add("size=" + size)
                .add("createTime=" + createTime)
                .add("convertedPath='" + convertedPath + "'")
                .add("convertedStatus='" + convertedStatus + "'")
                .add("path='" + path + "'")
                .toString();
    }


    public static final class FileResourceDtoBuilder {
        private Long id;
        private String name;
        private String type;
        private Long size;
        private Long createTime;
        private String convertedPath;
        private String convertedStatus;
        private String path;

        private FileResourceDtoBuilder() {
        }

        public static FileResourceDtoBuilder aFileResourceDto() {
            return new FileResourceDtoBuilder();
        }

        public FileResourceDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public FileResourceDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public FileResourceDtoBuilder withType(String type) {
            this.type = type;
            return this;
        }

        public FileResourceDtoBuilder withSize(Long size) {
            this.size = size;
            return this;
        }

        public FileResourceDtoBuilder withCreateTime(Long createTime) {
            this.createTime = createTime;
            return this;
        }

        public FileResourceDtoBuilder withConvertedPath(String convertedPath) {
            this.convertedPath = convertedPath;
            return this;
        }

        public FileResourceDtoBuilder withConvertedStatus(String convertedStatus) {
            this.convertedStatus = convertedStatus;
            return this;
        }

        public FileResourceDtoBuilder withPath(String path) {
            this.path = path;
            return this;
        }

        public FileResourceDto build() {
            FileResourceDto fileResourceDto = new FileResourceDto();
            fileResourceDto.size = this.size;
            fileResourceDto.path = this.path;
            fileResourceDto.name = this.name;
            fileResourceDto.convertedPath = this.convertedPath;
            fileResourceDto.id = this.id;
            fileResourceDto.createTime = this.createTime;
            fileResourceDto.convertedStatus = this.convertedStatus;
            fileResourceDto.type = this.type;
            return fileResourceDto;
        }
    }
}
