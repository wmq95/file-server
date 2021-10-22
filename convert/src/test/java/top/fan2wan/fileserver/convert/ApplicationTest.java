package top.fan2wan.fileserver.convert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.fan2wan.fileserver.convert.util.OpenOfficeUtil;
import top.fan2wan.fileserver.oss.dto.OssFileDto;
import top.fan2wan.fileserver.oss.service.OssService;

/**
 * @Author: fanT
 * @Date: 2021/10/22 13:24
 * @Description:
 */
@SpringBootTest(classes = ConvertApplication.class)
@RunWith(SpringRunner.class)
public class ApplicationTest {

    @Autowired
    private OssService ossService;
    private static String dir = "d:/openoffice/work/";

    @Test
    public void test() {
        OssFileDto download = new OssFileDto();
        download.setOssFilePath("2021/10/12/1.doc");
        download.setDownloadPath(dir + "download.doc");
        ossService.download(download);

        //转换
        OpenOfficeUtil util = new OpenOfficeUtil();
        util.convert2Pdf(dir + "download.doc", dir + "convert.pdf");

        //上传
        OssFileDto save = new OssFileDto();
        save.setSavePath(dir + "convert.pdf");
        save.setOssFilePath("2021/10/12/convert.pdf");
        ossService.save(save);
    }
}
