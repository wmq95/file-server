package top.fan2wan.fileserver.searchengine.service;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import org.apache.lucene.document.*;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wltea.analyzer.lucene.IKAnalyzer;
import top.fan2wan.fileserver.common.util.IfHelper;
import top.fan2wan.fileserver.common.util.TryHelper;
import top.fan2wan.fileserver.searchengine.dto.IFileIndex;
import top.fan2wan.fileserver.searchengine.dto.ISearchIndex;
import top.fan2wan.fileserver.searchengine.dto.LuceneFileIndexDto;
import top.fan2wan.fileserver.searchengine.util.LuceneUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: fanT
 * @Date: 2022/1/14 9:10
 * @Description: lucene for searchEngine
 * <p>
 * 扩展: 索引字段添加 - 需要重新建立索引
 * 思路: service 初始化之前 进行重建索引。
 * 根据路径获取索引的字段 document.getFields 获取文件中的索引字段
 * 比对service中需要索引的字段 如果一直 无需重建索引
 * 不一致:
 * 分批 -- 获取索引 然后重新构建索引写入临时目录中
 * 完成后删除就的索引
 * 重命名临时目录 改为原来的目录名称
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
    private final static int MAX_SEARCH_NUMBER = 1000;

    @Override
    public boolean saveIndex(IFileIndex fileIndex) {
        Assert.notNull(fileIndex.getId(), "id can not be null");
        Assert.notBlank(fileIndex.getName(), "name can not be empty");
        Assert.notBlank(fileIndex.getPath(), "path can not be empty");
        Assert.notNull(fileIndex.getSize(), "size can not be null");
        Assert.notBlank(fileIndex.getType(), "type can not be empty");
        Assert.notNull(fileIndex.getCreateTime(), "createTime can not be null");

        return TryHelper.function(luceneUtil::save, getDocument(fileIndex));
    }

    @Override
    public boolean deleteIndexById(Long id) {
        Assert.notNull(id);
        TryHelper.function(luceneUtil::deleteByQuery, LongPoint.newExactQuery(PREFIX + ID, id));
        return true;
    }

    @Override
    public List<IFileIndex> searchIndex(ISearchIndex searchIndex) {
        Assert.checkBetween(searchIndex.getSearchNumber(), 0, MAX_SEARCH_NUMBER);

        BooleanClause.Occur occur = searchIndex.isAllMatch() ? BooleanClause.Occur.MUST : BooleanClause.Occur.SHOULD;
        BooleanQuery.Builder queryBuilder = new BooleanQuery.Builder();
        /*如果query条件为空 lucene 返回数据为空 而不是全部数据 所以加上一个永远成立的条件 查询所有*/
        queryBuilder.add(LongPoint.newRangeQuery(PREFIX + ID, 0, Long.MAX_VALUE), BooleanClause.Occur.MUST);

        IfHelper.isNotBlank(searchIndex.getContent(), (content) -> queryBuilder.add(queryWithTokenized(CONTENT, content), occur));
        IfHelper.isNotBlank(searchIndex.getName(), (name) -> queryBuilder.add(queryWithTokenized(NAME, name), occur));
        IfHelper.isNotBlank(searchIndex.getType(), (type) -> queryBuilder.add(new TermQuery(new Term(TYPE, type)), occur));

        /**
         * 排序字段 最后需要加上前缀去查询
         */
        String orderBy = StrUtil.isNotBlank(searchIndex.getOrderBy()) ? searchIndex.getOrderBy() : CREATE_TIME;
        orderBy = PREFIX + orderBy;

        TopDocs search = TryHelper.function(luceneUtil::search, queryBuilder.build(), searchIndex.getSearchNumber(),
                new Sort(new SortField(orderBy, SortField.Type.LONG, searchIndex.isDesc())));

        return Arrays.stream(search.scoreDocs)
                .map(this::transform).collect(Collectors.toList());
    }

    @Override
    public IFileIndex getIndexById(Long id) {
        Assert.notNull(id);
        Document document = searchDocumentById(id);
        Assert.notNull(document, "file not exist");
        return getIFileIndex(document);
    }

    @Override
    public boolean updateIndex(IFileIndex updateIndex) {
        Assert.notNull(updateIndex.getId());
        Document document = searchDocumentById(updateIndex.getId());
        Assert.notNull(document, "file not exist");

        /**
         * 更新本质就是先删除 然后新增
         */
        deleteIndexById(updateIndex.getId());
        saveIndex(updateIndex);
        return false;
    }

    private Document searchDocumentById(Long id) {
        TopDocs docs = TryHelper.function(luceneUtil::search, LongPoint.newExactQuery(PREFIX + ID, id), 10);
        return docs.scoreDocs.length == 1 ? TryHelper.function(luceneUtil::searchByDocId, docs.scoreDocs[0].doc)
                : null;
    }

    private Query queryWithTokenized(String field, String content) {
        QueryParser queryParser = new QueryParser(field, new IKAnalyzer());

        return TryHelper.function(queryParser::parse, content);
    }

    private IFileIndex transform(ScoreDoc scoreDoc) {
        Document document = TryHelper.function(luceneUtil::searchByDocId, scoreDoc.doc);

        return getIFileIndex(document);

    }

    private LuceneFileIndexDto getIFileIndex(Document document) {
        return LuceneFileIndexDto.FileIndexDtoBuilder.aFileIndexDto()
                .withId(document.getField(ID).numericValue().longValue())
                .withType(document.get(TYPE))
                .withSize(document.getField(SIZE).numericValue().longValue())
                .withCreateTime(document.getField(CREATE_TIME).numericValue().longValue())
                .withName(document.get(NAME))
                .withPath(document.get(PATH))
                .build();
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
        document.add(new TextField(CONTENT, fileIndex.getContent(), Field.Store.YES));
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
