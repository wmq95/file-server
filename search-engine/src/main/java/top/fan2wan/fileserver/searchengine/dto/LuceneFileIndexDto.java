package top.fan2wan.fileserver.searchengine.dto;

import java.util.StringJoiner;

/**
 * @Author: fanT
 * @Date: 2022/1/18 9:22
 * @Description: dto for fileIndex
 * toString 没有打印内容
 */
public class LuceneFileIndexDto implements IFileIndex {

    private Long id;

    private String content;

    private String path;

    private String name;

    private Long createTime;

    private Long size;

    private String type;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Long getCreateTime() {
        return createTime;
    }

    @Override
    public Long getSize() {
        return size;
    }

    @Override
    public String getType() {
        return type;
    }


    public static final class FileIndexDtoBuilder {
        private Long id;
        private String content;
        private String path;
        private String name;
        private Long createTime;
        private Long size;
        private String type;

        private FileIndexDtoBuilder() {
        }

        public static FileIndexDtoBuilder aFileIndexDto() {
            return new FileIndexDtoBuilder();
        }

        public FileIndexDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public FileIndexDtoBuilder withContent(String content) {
            this.content = content;
            return this;
        }

        public FileIndexDtoBuilder withPath(String path) {
            this.path = path;
            return this;
        }

        public FileIndexDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public FileIndexDtoBuilder withCreateTime(Long createTime) {
            this.createTime = createTime;
            return this;
        }

        public FileIndexDtoBuilder withSize(Long size) {
            this.size = size;
            return this;
        }

        public FileIndexDtoBuilder withType(String type) {
            this.type = type;
            return this;
        }

        public LuceneFileIndexDto build() {
            LuceneFileIndexDto luceneFileIndexDTO = new LuceneFileIndexDto();
            luceneFileIndexDTO.path = this.path;
            luceneFileIndexDTO.createTime = this.createTime;
            luceneFileIndexDTO.id = this.id;
            luceneFileIndexDTO.content = this.content;
            luceneFileIndexDTO.size = this.size;
            luceneFileIndexDTO.type = this.type;
            luceneFileIndexDTO.name = this.name;
            return luceneFileIndexDTO;
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", LuceneFileIndexDto.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("path='" + path + "'")
                .add("name='" + name + "'")
                .add("createTime=" + createTime)
                .add("size=" + size)
                .add("type='" + type + "'")
                .toString();
    }
}
