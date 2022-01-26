package top.fan2wan.resource.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.fan2wan.fileserver.searchengine.service.LuceneImpl;
import top.fan2wan.fileserver.searchengine.service.SearchEngineService;
import top.fan2wan.fileserver.searchengine.util.LuceneUtil;

/**
 * @Author: fanT
 * @Date: 2022/1/26 9:45
 * @Description: config for third part
 */
@Configuration
public class ThirdPartServiceConfig {
    @Value("${searchEngine.fileDir}")
    String dir;

    @Bean
    public SearchEngineService searchEngineService() {
        return new LuceneImpl(new LuceneUtil(dir));
    }
}
