package top.fan2wan.fileserver.mq.service;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import top.fan2wan.fileserver.mq.dto.IFileCallback;
import top.fan2wan.fileserver.mq.dto.ISendFile;

/**
 * @Author: fanT
 * @Date: 2021/12/24 13:48
 * @Description: impl for fileMq
 */
@Service
public class FileMqServiceImpl implements FileMqService {

    private final StreamBridge streamBridge;

    public FileMqServiceImpl(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    /**
     * 发送文件转换请求到mq
     *
     * @param sendFile fileDto
     */
    @Override
    public void sendFile(ISendFile sendFile) {
        streamBridge.send("", sendFile);
    }

    /**
     * 转换服务转换文件之后调用
     *
     * @param callback 文件转换状态
     */
    @Override
    public void sendFileCallback(IFileCallback callback) {
        streamBridge.send("", callback);
    }
}
