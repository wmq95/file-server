package top.fan2wan.fileserver.oss;

import cn.hutool.http.HttpUtil;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.DownloadUrl;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @Author: fanT
 * @Date: 2021/10/15 13:27
 * @Description: test for oss
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class OssTest {
    private static Logger logger = LoggerFactory.getLogger(OssTest.class);

    @Value("${oss.bucket}")
    private String bucket;
    @Value("${oss.accessKey}")
    private String accessKey;
    @Value("${oss.secretKey}")
    private String secretKey;
    @Value("${oss.domain}")
    private String domain;

    private String localFilePath = "d:/aspose.pdf";

    @Test
    public void testDownload() throws IOException {
        String url = getUrl();
        logger.info("url was :{}", url);
        HttpUtil.downloadFile(url, "d:/download.doc");
    }

    private String getUrl() throws QiniuException {
        DownloadUrl url = new DownloadUrl(domain, false, "2021/10/12/1.doc");
        long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
        Auth auth = Auth.create(accessKey, secretKey);
        return url.buildURL(auth, System.currentTimeMillis() + expireInSeconds);
    }

    @Test
    public void testUpload() {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huanan());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        /*String accessKey = "your access key";
        String secretKey = "your secret key";
        String bucket = "your bucket name";
        logger.info("bucket was :{}, \naccessKey was :{}\nsecretKey was :{}", bucket, accessKey, secretKey);*/

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        // 空间不支持目录 但是可以通过文件名称达到虚拟目录得目的
        String uploadFileName = "2021/10/12/2.pdf";

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, uploadFileName, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            ex.printStackTrace();
        }
    }
}
