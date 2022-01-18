package top.fan2wan.fileserver.searchengine.service;

import cn.hutool.core.lang.Assert;
import org.apache.lucene.document.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.fan2wan.fileserver.searchengine.dto.IFileIndex;
import top.fan2wan.fileserver.searchengine.util.LuceneUtil;

import java.io.IOException;

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

    @Override
    public boolean indexFile(IFileIndex fileIndex) {
        Assert.notNull(fileIndex.getId());
        Assert.notEmpty(fileIndex.getContent());
        Assert.notBlank(fileIndex.getName());
        Assert.notBlank(fileIndex.getPath());
        Assert.notNull(fileIndex.getSize());
        Assert.notBlank(fileIndex.getType());
        Assert.notNull(fileIndex.getCreateTime());

        try {
            return luceneUtil.save(getDocument(fileIndex));
        } catch (IOException e) {
            logger.error("failed to indexFile, error was:", e);
            return false;
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
        document.add(new SortedNumericDocValuesField(PREFIX + CREATE_TIME, fileIndex.getCreateTime()));
        return document;
    }
}
