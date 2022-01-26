package top.fan2wan.resource.service;

import top.fan2wan.fileserver.mq.service.ResourceMqService;
import top.fan2wan.resource.dto.IFileResource;

/**
 * @Author: fanT
 * @Date: 2022/1/26 9:25
 * @Description: service for fileResource
 */
public interface IFileResourceService extends ResourceMqService {

    /**
     * 新增文件
     *
     * @param fileResource dto
     * @return boolean
     */
    boolean saveFileResource(IFileResource fileResource);
}
