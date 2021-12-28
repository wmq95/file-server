package top.fan2wan.fileserver.convert.service.impl;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import top.fan2wan.fileserver.convert.service.IConvertService;
import top.fan2wan.fileserver.mq.dto.IFile;
import top.fan2wan.fileserver.mq.dto.IFileCallback;

/**
 * @Author: fanT
 * @Date: 2021/10/22 13:22
 * @Description: impl for convert
 */
@Service
public class ConvertServiceImpl implements IConvertService {

    private final StreamBridge streamBridge;

    public ConvertServiceImpl(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public void sendFileCallback(IFileCallback fileCallback) {

    }

    @Override
    public void accept(IFile file) {

    }
}
