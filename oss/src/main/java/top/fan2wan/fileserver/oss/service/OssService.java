package top.fan2wan.fileserver.oss.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.fan2wan.fileserver.oss.dto.IOssFile;

/**
 * @Author: fanT
 * @Date: 2021/10/19 13:10
 * @Description: 对象存储抽象
 */
public interface OssService {
    Logger logger = LoggerFactory.getLogger(OssService.class);

    /**
     * 保存oss文件
     *
     * @param ossFile 文件对象
     */
    void save(IOssFile ossFile);

    /**
     * 下载oss文件
     *
     * @param ossFile 文件对象
     */
    void download(IOssFile ossFile);
}
