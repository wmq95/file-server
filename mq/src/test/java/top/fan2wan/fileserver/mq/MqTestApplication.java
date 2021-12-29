package top.fan2wan.fileserver.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

/**
 * @Author: fanT
 * @Date: 2021/12/27 8:37
 * @Description: test for mq
 */
@SpringBootApplication
public class MqTestApplication implements ApplicationContextAware {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static ApplicationContext context;

    @Bean
    public Consumer<String> log() {
        return s -> {
            System.out.println(s);
            //int i = Integer.parseInt(s);
            //System.out.println(i);
        };
    }

    /*@Bean
    public Consumer<IFile> log() {
        return System.out::println;
    }*/

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static void getBean(Class zclass) {
        try {
            System.out.println(objectMapper.writer().writeValueAsString(context.getBean(zclass)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
