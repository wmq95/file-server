package top.fan2wan.fileserver.searchengine.service;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import org.apache.lucene.document.*;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.fan2wan.fileserver.searchengine.dto.IFileIndex;
import top.fan2wan.fileserver.searchengine.dto.ISearchIndex;
import top.fan2wan.fileserver.searchengine.dto.SimpleFileDto;
import top.fan2wan.fileserver.searchengine.util.IfHelper;
import top.fan2wan.fileserver.searchengine.util.LuceneUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: fanT
 * @Date: 2022/1/14 9:10
 * @Description: lucene for searchEngine
 */
public class LuceneImpl implements SearchEngineService {

    private static Logger logger = LoggerFactory.getLogger(LuceneImpl.class);

    private final LuceneUtil luceneUtil;

    public LuceneImpl(LuceneUtil luceneUtil) {
        this.luceneUtil = luceneUtil;
    }

    private final static String ID = "id";
    private final static String CONTENT = "content";
    private final static String PATH = "path";
    private final static String NAME = "name";
    private final static String CREATE_TIME = "createTime";
    private final static String SIZE = "size";
    private final static String TYPE = "type";
    private final static String PREFIX = "_";
    private final static IFileIndex EMPTY_FILE = SimpleFileDto.FileIndexDtoBuilder.aFileIndexDto().build();
    private final static int MAX_SEARCH_NUMBER = 1000;

    @Override
    public boolean saveIndex(IFileIndex fileIndex) {
        Assert.notNull(fileIndex.getId(), "id can not be null");
        Assert.notEmpty(fileIndex.getContent(), "content can not be empty");
        Assert.notBlank(fileIndex.getName(), "name can not be empty");
        Assert.notBlank(fileIndex.getPath(), "path can not be empty");
        Assert.notNull(fileIndex.getSize(), "size can not be null");
        Assert.notBlank(fileIndex.getType(), "type can not be empty");
        Assert.notNull(fileIndex.getCreateTime(), "createTime can not be null");

        try {
            return luceneUtil.save(getDocument(fileIndex));
        } catch (IOException e) {
            logger.error("failed to indexFile, error was:", e);
            return false;
        }
    }

    @Override
    public boolean deleteIndexById(Long id) {
        Assert.notNull(id);
        try {
            luceneUtil.deleteByQuery(LongPoint.newExactQuery(PREFIX + ID, id));
        } catch (IOException e) {
            logger.error("failed to delete index, error was: {}", e);
        }
        return false;
    }

    @Override
    public List<IFileIndex> searchIndex(ISearchIndex searchIndex) {
        Assert.checkBetween(searchIndex.getSearchNumber(), 0, MAX_SEARCH_NUMBER);

        BooleanClause.Occur occur = searchIndex.isAllMatch() ? BooleanClause.Occur.MUST : BooleanClause.Occur.SHOULD;
        BooleanQuery.Builder queryBuilder = new BooleanQuery.Builder();

        IfHelper.isNotBlank(searchIndex.getContent(), (content) -> queryBuilder.add(new TermQuery(new Term(CONTENT, content)), occur));
        IfHelper.isNotBlank(searchIndex.getName(), (name) -> queryBuilder.add(new TermQuery(new Term(NAME, name)), occur));
        IfHelper.isNotBlank(searchIndex.getType(), (type) -> queryBuilder.add(new TermQuery(new Term(TYPE, type)), occur));

        /**
         * 排序字段 最后需要加上前缀去查询
         */
        String orderBy = StrUtil.isNotBlank(searchIndex.getOrderBy()) ? searchIndex.getOrderBy() : CREATE_TIME;
        orderBy += PREFIX;

        List<IFileIndex> res = new LinkedList<>();
        try {
            TopDocs search = luceneUtil.search(queryBuilder.build(), searchIndex.getSearchNumber(),
                    new Sort(new SortField(orderBy, SortField.Type.LONG, searchIndex.isDesc())));

            res.addAll(Arrays.stream(search.scoreDocs)
                    .map(this::transform).collect(Collectors.toList()));
        } catch (IOException e) {
            logger.error("failed to search index, param was:{},error was:{}", searchIndex, e);
            return res;
        }
        return res;
    }

    private IFileIndex transform(ScoreDoc scoreDoc) {

        try {
            Document document = luceneUtil.searchByDocId(scoreDoc.doc);
            return SimpleFileDto.FileIndexDtoBuilder.aFileIndexDto()
                    .withId(document.getField(ID).numericValue().longValue())
                    .withType(document.get(TYPE))
                    .withSize(document.getField(SIZE).numericValue().longValue())
                    .withCreateTime(document.getField(CREATE_TIME).numericValue().longValue())
                    .withName(document.get(NAME))
                    .withPath(document.get(PATH))
                    .build();
        } catch (IOException e) {
            return EMPTY_FILE;
        }
    }

    private Document getDocument(IFileIndex fileIndex) {
        Document document = new Document();
        /**
         * 存储原有的属性
         * 注意 文件内容不存储于lucene 节省空间 也就意味着查询不到文件内容
         * TextField 会分词
         * StringField 不会分词
         */
        document.add(new StoredField(ID, fileIndex.getId()));
        document.add(new TextField(CONTENT, fileIndex.getContent(), Field.Store.NO));
        document.add(new StoredField(PATH, fileIndex.getPath()));
        document.add(new TextField(NAME, fileIndex.getName(), Field.Store.YES));
        document.add(new StoredField(CREATE_TIME, fileIndex.getCreateTime()));
        document.add(new StoredField(SIZE, fileIndex.getSize()));
        document.add(new StringField(TYPE, fileIndex.getType(), Field.Store.YES));
        /**
         * 其他存储
         * id 用于主键搜索 精确匹配
         * createTime 用于排序
         */
        document.add(new LongPoint(PREFIX + ID, fileIndex.getId()));
        document.add(new NumericDocValuesField(PREFIX + CREATE_TIME, fileIndex.getCreateTime()));
        return document;
    }
}
