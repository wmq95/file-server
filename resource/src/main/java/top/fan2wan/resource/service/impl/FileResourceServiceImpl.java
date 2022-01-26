package top.fan2wan.resource.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import top.fan2wan.fileserver.mq.dto.FileCallbackDto;
import top.fan2wan.fileserver.mq.dto.FileDto;
import top.fan2wan.fileserver.searchengine.dto.IFileIndex;
import top.fan2wan.fileserver.searchengine.dto.LuceneFileIndexDto;
import top.fan2wan.fileserver.searchengine.service.SearchEngineService;
import top.fan2wan.resource.dto.IFileResource;
import top.fan2wan.resource.service.IFileResourceService;

import java.time.Instant;

/**
 * @Author: fanT
 * @Date: 2022/1/26 9:38
 * @Description: impl for fileResourceService
 */
@Service("resourceService")
public class FileResourceServiceImpl implements IFileResourceService {

    private final SearchEngineService searchEngineService;
    private final StreamBridge streamBridge;

    public FileResourceServiceImpl(SearchEngineService searchEngineService,
                                   StreamBridge streamBridge) {
        this.searchEngineService = searchEngineService;
        this.streamBridge = streamBridge;
    }

    private static Logger logger = LoggerFactory.getLogger(FileResourceServiceImpl.class);

    @Override
    public boolean saveFileResource(IFileResource fileResource) {

        Long id = saveEntity(fileResource);

        sendFile(FileDto.FileDtoBuilder.aFileDto()
                .withFileId(id)
                .withOssPath(fileResource.getPath())
                .build());
        return true;
    }

    private Long saveEntity(IFileResource fileResource) {
        //保存到数据库
        logger.info("save to db....");
        return 1254789456L;
    }

    @Override
    public void sendFile(FileDto file) {
        streamBridge.send("convertService", file);
    }

    @Override
    public void accept(FileCallbackDto fileCallbackDto) {
        logger.info("received FileCallbackDto was :{}", fileCallbackDto);

        searchEngineService.saveIndex(getFileIndex(fileCallbackDto));
    }

    private IFileIndex getFileIndex(FileCallbackDto fileCallbackDto) {
        // 查询数据库

        return LuceneFileIndexDto.FileIndexDtoBuilder.aFileIndexDto()
                .withContent(fileCallbackDto.getFileContent())
                .withId(fileCallbackDto.getFileId())
                .withPath(fileCallbackDto.getConvertedPath())
                .withName("测试文件")
                .withCreateTime(Instant.now().toEpochMilli())
                .withSize(14258L)
                .withType("doc")
                .build();
    }
}
