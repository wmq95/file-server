package top.fan2wan.fileserver.mq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: fanT
 * @Date: 2021/12/27 8:38
 * @Description: test
 */
@SpringBootTest(classes = MqTestApplication.class)
@RunWith(SpringRunner.class)
public class MqTest {

    @Autowired
    StreamBridge streamBridge;

    @Test
    public void test_producer() {
        try {
            Thread.sleep(1000*5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        streamBridge.send("log-out-0", " test log");
        try {
            Thread.sleep(1000*5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
