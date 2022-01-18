package top.fan2wan.fileserver.searchengine;

import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexableField;
import top.fan2wan.fileserver.searchengine.util.LuceneUtil;

import java.io.IOException;
import java.time.Instant;

/**
 * @Author: fanT
 * @Date: 2022/1/14 10:10
 * @Description: test for luceneUtil
 * <p>
 * Long值存储有2中方式 一个用#{@link StoredField}
 * 一个用#{@link LongPoint}
 * 2这的区别 LongPoint 不会存储Long的值，但是可以用于rangeQuery
 * StoredField会存储Long的值
 */
public class LuceneUtilTest {

    //private static LuceneIndexUtil luceneIndexUtil = new LuceneIndexUtil("F:\\tempdir\\lucene");

    private static LuceneUtil util = new LuceneUtil("F:\\tempdir\\lucene");

    public static void main(String[] args) {
        save_test();
    }


    public static void save_test() {
        try {
            util.deleteAll();
            //util.save(getFileDocument());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Document getFileDocument() {
        Document document = new Document();
        document.add(new LongPoint("id", 1L));
        document.add(new TextField("content", "无锡是个好地方啊", Field.Store.NO));
        document.add(new StringField("path", "/a/a/a/a", Field.Store.YES));
        document.add(new TextField("name", "无锡文档", Field.Store.YES));
        //document.add(new StoredField("createTime", Instant.now().toEpochMilli()));
        document.add(new NumericDocValuesField("createTime", Instant.now().toEpochMilli()));
        document.add(new StoredField("size", 485639L));
        document.add(new StringField("type", "doc", Field.Store.YES));
        return document;
    }
}
