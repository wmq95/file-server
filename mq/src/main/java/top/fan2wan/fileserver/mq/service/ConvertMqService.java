package top.fan2wan.fileserver.mq.service;

import top.fan2wan.fileserver.mq.dto.IFile;
import top.fan2wan.fileserver.mq.dto.IFileCallback;

import java.util.function.Consumer;

/**
 * @Author: fanT
 * @Date: 2021/12/28 13:53
 * @Description: service for converMq
 */
public interface ConvertMqService extends Consumer<IFile> {

    /**
     * 发送文件转换结果
     * @param fileCallback fileCallback
     */
    void sendFileCallback(IFileCallback fileCallback);
}
