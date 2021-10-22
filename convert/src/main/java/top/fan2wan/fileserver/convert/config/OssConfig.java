package top.fan2wan.fileserver.convert.config;

import com.qiniu.storage.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.fan2wan.fileserver.oss.service.OssService;
import top.fan2wan.fileserver.oss.service.XiNiuOssServiceImpl;

/**
 * @Author: fanT
 * @Date: 2021/10/22 13:16
 * @Description: config for oss
 */
@Configuration
public class OssConfig {
    @Value("${oss.bucket}")
    private String bucket;
    @Value("${oss.accessKey}")
    private String accessKey;
    @Value("${oss.secretKey}")
    private String secretKey;
    @Value("${oss.domain}")
    private String domain;
    @Value("${oss.expire}")
    private long expire;

    @Bean
    public OssService ossService() {
        return new XiNiuOssServiceImpl(bucket, accessKey, secretKey, domain, Region.huanan(), expire);
    }
}
