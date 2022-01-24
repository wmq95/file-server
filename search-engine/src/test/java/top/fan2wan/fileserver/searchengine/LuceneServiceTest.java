package top.fan2wan.fileserver.searchengine;

import org.apache.lucene.document.*;
import top.fan2wan.fileserver.searchengine.dto.IFileIndex;
import top.fan2wan.fileserver.searchengine.dto.SearchIndexDto;
import top.fan2wan.fileserver.searchengine.dto.SimpleFileDto;
import top.fan2wan.fileserver.searchengine.service.LuceneImpl;
import top.fan2wan.fileserver.searchengine.service.SearchEngineService;
import top.fan2wan.fileserver.searchengine.util.LuceneUtil;

import java.io.IOException;
import java.time.Instant;

/**
 * @Author: fanT
 * @Date: 2022/1/18 9:20
 * @Description: test for luceneService
 */
public class LuceneServiceTest {

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        LuceneUtil util = new LuceneUtil("F:\\tempdir\\lucene\\file");

        SearchEngineService service = new LuceneImpl(util);
        service.saveIndex(buildFile());
        service.saveIndex(buildFile2());

        //System.out.println(service.searchIndex(buildSearchIndex()));
        /*try {
            util.deleteAll();
            util.save(buildNewDocument());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private static Document buildNewDocument() {
        Document document = new Document();
        document.add(new StringField("businessType","china", Field.Store.YES));

        document.add(new StoredField("id", 1254862456L));
        document.add(new TextField("content", "中华人民共和国简称中国，是一个有13亿人口的国家", Field.Store.YES));
        document.add(new StoredField("path", "/2021/10/11/a.pdf"));
        document.add(new TextField("name", "中国", Field.Store.YES));
        document.add(new StoredField("createTime", Instant.now().toEpochMilli()));
        document.add(new StoredField("size", 45245L));
        document.add(new StringField("type", "pdf", Field.Store.YES));

        document.add(new LongPoint("_id", 1254862456L));
        document.add(new NumericDocValuesField("_createTime", Instant.now().toEpochMilli()));
        return document;
    }

    private static SearchIndexDto buildSearchIndex() {
        return SearchIndexDto.SearchIndexDtoBuilder.aSearchIndexDto()
                .withContent("一")
                .withSearchNumber(100)
                .build();
    }

    private static IFileIndex buildFile() {
        return SimpleFileDto.FileIndexDtoBuilder.aFileIndexDto()
                .withId(123456L)
                .withContent("呵呵 这是个测试的文件")
                .withName("测试文件")
                .withPath("/2021/10/12/test.doc")
                .withCreateTime(Instant.now().toEpochMilli())
                .withSize(128L)
                .withType("doc")
                .build();
    }

    private static IFileIndex buildFile2() {
        return SimpleFileDto.FileIndexDtoBuilder.aFileIndexDto()
                .withId(12325L)
                .withContent("这又是一个测试文件啊")
                .withName("测试文件2")
                .withPath("/2021/10/12/test2.doc")
                .withCreateTime(Instant.now().toEpochMilli() + 1000 * 60 * 60)
                .withSize(1025L)
                .withType("doc")
                .build();
    }
}
