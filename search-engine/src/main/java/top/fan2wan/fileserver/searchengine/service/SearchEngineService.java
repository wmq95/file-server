package top.fan2wan.fileserver.searchengine.service;

import top.fan2wan.fileserver.searchengine.dto.IFileIndex;
import top.fan2wan.fileserver.searchengine.dto.ISearchIndex;

import java.util.List;

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
    boolean saveIndex(IFileIndex fileIndex);

    /**
     * 根据文件id 删除索引文件
     *
     * @param id 文件id
     * @return boolean
     */
    boolean deleteIndexById(Long id);

    /**
     * 查询索引
     *
     * @param searchIndex 查询参数
     * @return List<IFileIndex>
     */
    List<IFileIndex> searchIndex(ISearchIndex searchIndex);

    /**
     * 根据文件id 获取索引
     * 注意 这儿没有返回内容
     *
     * @param id id
     * @return IFileIndex
     */
    IFileIndex getIndexById(Long id);

    /**
     * 更新索引
     *
     * @param updateIndex 索引更新
     * @return boolean
     */
    boolean updateIndex(IFileIndex updateIndex);
}
