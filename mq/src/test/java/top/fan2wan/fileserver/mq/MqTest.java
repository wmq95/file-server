package top.fan2wan.fileserver.mq;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.function.StreamBridge;
import top.fan2wan.fileserver.mq.dto.FileDto;

/**
 * @Author: fanT
 * @Date: 2021/12/27 8:38
 * @Description: test
 */
@SpringBootTest(classes = MqTestApplication.class)
public class MqTest {

    @Autowired
    StreamBridge streamBridge;

    @Test
    public void test_producer() {

        //streamBridge.send("log-out-0", " test log");

        //MqTestApplication.getBean(BindingServiceProperties.class);

        // 不能使用接口 因为rabbitmq 实列化的时候接口没有构造函数 所有回报错
        //streamBridge.send("log-out-0", FileDto.FileDtoBuilder.aFileDto()
        //        .withFileId(122L).withOssPath("path").build());
        try {
            Thread.sleep(1000 * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
