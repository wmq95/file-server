package top.fan2wan.fileserver.searchengine.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: fanT
 * @Date: 2022/1/24 9:04
 * @Description: help to eliminate try catch
 */
public class TryHelper {
    private static final String ERROR_MSG = "failed to call function";

    private static Logger logger = LoggerFactory.getLogger(TryHelper.class);

    @FunctionalInterface
    public interface ExceptionFunction<T, R> {
        R apply(T t) throws Exception;
    }

    public static <T, R> R function(ExceptionFunction<T, R> function, T t) {

        return function(function, t, ERROR_MSG);
    }

    public static <T, R> R function(ExceptionFunction<T, R> function, T t, String errorMsg) {

        try {
            return function.apply(t);
        } catch (Exception e) {
            logger.error("failed to call function, error was:{}", e);
            throw new RuntimeException(errorMsg);
        }
    }
}
