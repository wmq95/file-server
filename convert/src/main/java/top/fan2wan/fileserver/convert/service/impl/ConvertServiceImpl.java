package top.fan2wan.fileserver.convert.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.fan2wan.fileserver.convert.service.IConvertService;
import top.fan2wan.fileserver.mq.dto.FileCallbackDto;
import top.fan2wan.fileserver.mq.dto.FileDto;

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

    private static Logger logger = LoggerFactory.getLogger(IConvertService.class);

    @Override
    public void sendFileCallback(FileCallbackDto fileCallback) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void accept(FileDto file) {
        logger.info("receive file was :{}", file);

    }
}
