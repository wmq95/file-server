package top.fan2wan.fileserver.common.util;

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
    public interface ExceptionSupplier<R> {
        R get() throws Exception;
    }

    @FunctionalInterface
    public interface ExceptionFunction<T, R> {
        R apply(T t) throws Exception;
    }

    @FunctionalInterface
    public interface BiExceptionFunction<T, U, R> {
        R apply(T t, U u) throws Exception;
    }

    @FunctionalInterface
    public interface ThreeExceptionFunction<T, U, V, R> {
        R apply(T t, U u, V v) throws Exception;
    }

    public static <T> T function(ExceptionSupplier<T> function) {

        return function(function, ERROR_MSG);
    }

    public static <T> T function(ExceptionSupplier<T> function, String msg) {

        try {
            return function.get();
        } catch (Exception e) {
            logger.error("failed to call function, error was:{}", e);
            throw new RuntimeException(msg);
        }
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

    public static <T, R> R functionOrElse(ExceptionFunction<T, R> function, T t, R r) {

        try {
            return function.apply(t);
        } catch (Exception e) {
            logger.error("failed to call function, error was:{}", e);
            return r;
        }
    }

    public static <T, U, R> R function(BiExceptionFunction<T, U, R> function, T t, U u) {

        return function(function, t, u, ERROR_MSG);
    }

    public static <T, U, R> R function(BiExceptionFunction<T, U, R> function, T t, U u, String msg) {

        try {
            return function.apply(t, u);
        } catch (Exception e) {
            logger.error("failed to call function, error was:{}", e);
            throw new RuntimeException(msg);
        }
    }

    public static <T, U, R> R functionOrElse(BiExceptionFunction<T, U, R> function, T t, U u, R r) {

        try {
            return function.apply(t, u);
        } catch (Exception e) {
            logger.error("failed to call function, error was:{}", e);
            return r;
        }
    }

    public static <T, U, V, R> R function(ThreeExceptionFunction<T, U, V, R> function, T t, U u, V v) {

        return function(function, t, u, v, ERROR_MSG);
    }

    public static <T, U, V, R> R function(ThreeExceptionFunction<T, U, V, R> function, T t, U u, V v, String msg) {

        try {
            return function.apply(t, u, v);
        } catch (Exception e) {
            logger.error("failed to call function, error was:{}", e);
            throw new RuntimeException(msg);
        }
    }

    public static <T, U, V, R> R functionOrElse(ThreeExceptionFunction<T, U, V, R> function, T t, U u, V v, R r) {

        try {
            return function.apply(t, u, v);
        } catch (Exception e) {
            logger.error("failed to call function, error was:{}", e);
            return r;
        }
    }
}
