package top.fan2wan.fileserver.searchengine;

import top.fan2wan.fileserver.searchengine.dto.SimpleFileDTO;
import top.fan2wan.fileserver.searchengine.dto.IFileIndex;
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
        SearchEngineService service = new LuceneImpl(new LuceneUtil("F:\\tempdir\\lucene\\file"));
        service.indexFile(buildFile());
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
}
