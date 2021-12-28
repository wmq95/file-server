package top.fan2wan.fileserver.mq;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.config.BindingServiceProperties;
import org.springframework.cloud.stream.function.StreamBridge;

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

        streamBridge.send("log-out-0", " test log");

        MqTestApplication.getBean(BindingServiceProperties.class);
        try {
            Thread.sleep(1000 * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
