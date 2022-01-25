package top.fan2wan.fileserver.common.util;

import cn.hutool.core.util.StrUtil;

import java.util.function.Consumer;

/**
 * @Author: fanT
 * @Date: 2022/1/20 9:17
 * @Description: help to eliminate if-else
 */
public class IfHelper {

    public static void isNotBlank(String str, Consumer<String> consumer) {
        if (StrUtil.isNotBlank(str)) {
            consumer.accept(str);
        }
    }
}
