package top.fan2wan.fileserver.mq.service;

import top.fan2wan.fileserver.mq.dto.IFileCallback;
import top.fan2wan.fileserver.mq.dto.ISendFile;

/**
 * @Author: fanT
 * @Date: 2021/12/24 13:44
 * @Description: service for fileMq
 */
public interface FileMqService {

    /**
     * 发送文件转换请求到mq
     *
     * @param sendFile fileDto
     */
    void sendFile(ISendFile sendFile);

    /**
     * 转换服务转换文件之后调用
     *
     * @param callback 文件转换状态
     */
    void sendFileCallback(IFileCallback callback);
}
