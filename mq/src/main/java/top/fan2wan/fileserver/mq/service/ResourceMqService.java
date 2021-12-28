package top.fan2wan.fileserver.mq.service;

import top.fan2wan.fileserver.mq.dto.IFile;
import top.fan2wan.fileserver.mq.dto.IFileCallback;

import java.util.function.Consumer;

/**
 * @Author: fanT
 * @Date: 2021/12/28 13:55
 * @Description: service for ResourceMq
 */
public interface ResourceMqService extends Consumer<IFileCallback> {

    /**
     * 发送文件转换请求
     *
     * @param file file
     */
    void sendFile(IFile file);
}
