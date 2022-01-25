package top.fan2wan.fileserver.convert;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.fan2wan.fileserver.convert.util.OpenOfficeUtil;
import top.fan2wan.fileserver.oss.dto.OssFileDto;
import top.fan2wan.fileserver.oss.service.OssService;

import java.net.ConnectException;

/**
 * @Author: fanT
 * @Date: 2021/10/22 13:24
 * @Description:
 */
@SpringBootTest(classes = ConvertApplication.class)
public class ApplicationTest {

    @Autowired
    private OssService ossService;
    private static String dir = "d:/openoffice/work/";

    @Test
    public void test() throws ConnectException {
        OssFileDto download = new OssFileDto();
        download.setOssFilePath("2021/10/12/1.doc");
        download.setLocalPath(dir + "download.doc");
        ossService.download(download);

        //转换
        OpenOfficeUtil util = new OpenOfficeUtil();
        util.convert2Pdf(dir + "download.doc", dir + "convert.pdf");

        //上传
        OssFileDto save = new OssFileDto();
        save.setLocalPath(dir + "convert.pdf");
        save.setOssFilePath("2021/10/12/convert.pdf");
        ossService.save(save);
    }
}
