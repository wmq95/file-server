package top.fan2wan.fileserver.searchengine.service;

import top.fan2wan.fileserver.searchengine.dto.IFileIndex;

/**
 * @Author: fanT
 * @Date: 2022/1/14 9:07
 * @Description: interface for searchEngine
 */
public interface SearchEngineService {

    /**
     * 索引文件
     *
     * @param fileIndex dto
     * @return boolean
     */
    boolean indexFile(IFileIndex fileIndex);
}
