package top.fan2wan.fileserver.searchengine;

import top.fan2wan.fileserver.searchengine.dto.IFileIndex;
import top.fan2wan.fileserver.searchengine.dto.SimpleFileDTO;
import top.fan2wan.fileserver.searchengine.service.LuceneImpl;
import top.fan2wan.fileserver.searchengine.service.SearchEngineService;
import top.fan2wan.fileserver.searchengine.util.LuceneUtil;

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
    }

    private static IFileIndex buildFile() {
        return SimpleFileDTO.FileIndexDtoBuilder.aFileIndexDto()
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
        return SimpleFileDTO.FileIndexDtoBuilder.aFileIndexDto()
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
