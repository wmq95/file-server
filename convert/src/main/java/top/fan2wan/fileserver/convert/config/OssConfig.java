package top.fan2wan.fileserver.convert.config;

import com.qiniu.storage.Region;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import top.fan2wan.fileserver.oss.config.AbstractOssProperty;
import top.fan2wan.fileserver.oss.service.OssService;
import top.fan2wan.fileserver.oss.service.XiNiuOssServiceImpl;

/**
 * @Author: fanT
 * @Date: 2021/10/22 13:16
 * @Description: config for oss
 */
@Configuration
public class OssConfig {
    final OssProperty oss;

    public OssConfig(OssProperty oss) {
        this.oss = oss;
    }

    @Bean
    public OssService ossService() {
        return new XiNiuOssServiceImpl(oss.getBucket(), oss.getAccessKey(),
                oss.getSecretKey(), oss.getDomain(), Region.huanan(), oss.getExpire());
    }
}

@Component
@ConfigurationProperties(prefix = "oss")
class OssProperty extends AbstractOssProperty {

}
