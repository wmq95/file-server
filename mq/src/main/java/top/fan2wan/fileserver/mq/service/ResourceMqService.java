package top.fan2wan.fileserver.mq.service;

import top.fan2wan.fileserver.mq.dto.FileCallbackDto;
import top.fan2wan.fileserver.mq.dto.FileDto;

import java.util.function.Consumer;

/**
 * @Author: fanT
 * @Date: 2021/12/28 13:55
 * @Description: service for ResourceMq
 */
public interface ResourceMqService extends Consumer<FileCallbackDto> {

    /**
     * 发送文件转换请求
     *
     * @param file file
     */
    void sendFile(FileDto file);
}
