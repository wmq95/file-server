package top.fan2wan.fileserver.convert.util;

import java.util.concurrent.Future;

/**
 * @Author: fanT
 * @Date: 2022/1/7 10:29
 * @Description: util for future
 */
public class FutureUtil {

    public static String getAsStringOrElse(Future<String> future, String elseValue) {
        try {
            return future.get();
        } catch (Exception e) {
            return elseValue;
        }
    }
}
