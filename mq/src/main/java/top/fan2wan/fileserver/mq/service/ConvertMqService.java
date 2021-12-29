package top.fan2wan.fileserver.mq.service;

import top.fan2wan.fileserver.mq.dto.FileCallbackDto;
import top.fan2wan.fileserver.mq.dto.FileDto;

import java.util.function.Consumer;

/**
 * @Author: fanT
 * @Date: 2021/12/28 13:53
 * @Description: service for converMq
 */
public interface ConvertMqService extends Consumer<FileDto> {

    /**
     * 发送文件转换结果
     *
     * @param fileCallback fileCallback
     */
    void sendFileCallback(FileCallbackDto fileCallback);
}
