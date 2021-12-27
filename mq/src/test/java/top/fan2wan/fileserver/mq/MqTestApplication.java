package top.fan2wan.fileserver.mq;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

/**
 * @Author: fanT
 * @Date: 2021/12/27 8:37
 * @Description: test for mq
 */
@SpringBootApplication
public class MqTestApplication {

    @Bean
    public Consumer<String> log() {
        return System.out::println;
    }
}
