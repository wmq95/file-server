package top.fan2wan.fileserver.searchengine.dto;

import java.util.Objects;

/**
 * @Author: fanT
 * @Date: 2022/1/19 9:29
 * @Description: dto for searchIndex
 */
public class SearchIndexDto implements ISearchIndex {

    private String content;

    private String name;

    private String type;

    private String orderBy;

    private Boolean isDesc;

    private Boolean isAllMatch;

    private int searchNumber;

    @Override
    public String getContent() {
        return content;
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
    public String getOrderBy() {
        return orderBy;
    }

    @Override
    public boolean isDesc() {
        return Objects.isNull(isDesc) ? false : true;
    }

    @Override
    public boolean isAllMatch() {
        return Objects.isNull(isAllMatch) ? false : true;
    }

    @Override
    public int getSearchNumber() {
        return searchNumber;
    }

    public static final class SearchIndexDtoBuilder {
        private String content;
        private String name;
        private String type;
        private String orderBy;
        private Boolean isDesc;
        private Boolean isAllMatch;
        private int searchNumber;

        private SearchIndexDtoBuilder() {
        }

        public static SearchIndexDtoBuilder aSearchIndexDto() {
            return new SearchIndexDtoBuilder();
        }

        public SearchIndexDtoBuilder withContent(String content) {
            this.content = content;
            return this;
        }

        public SearchIndexDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public SearchIndexDtoBuilder withType(String type) {
            this.type = type;
            return this;
        }

        public SearchIndexDtoBuilder withOrderBy(String orderBy) {
            this.orderBy = orderBy;
            return this;
        }

        public SearchIndexDtoBuilder withIsDesc(Boolean isDesc) {
            this.isDesc = isDesc;
            return this;
        }

        public SearchIndexDtoBuilder withIsAllMatch(Boolean isAllMatch) {
            this.isAllMatch = isAllMatch;
            return this;
        }

        public SearchIndexDtoBuilder withSearchNumber(int searchNumber) {
            this.searchNumber = searchNumber;
            return this;
        }

        public SearchIndexDto build() {
            SearchIndexDto searchIndexDto = new SearchIndexDto();
            searchIndexDto.name = this.name;
            searchIndexDto.orderBy = this.orderBy;
            searchIndexDto.isAllMatch = this.isAllMatch;
            searchIndexDto.isDesc = this.isDesc;
            searchIndexDto.searchNumber = this.searchNumber;
            searchIndexDto.content = this.content;
            searchIndexDto.type = this.type;
            return searchIndexDto;
        }
    }
}
