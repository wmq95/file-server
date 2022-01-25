package top.fan2wan.fileserver.searchengine.dto;

/**
 * @Author: fanT
 * @Date: 2022/1/12 14:10
 * @Description: fileIndex
 * 文件
 * id
 * 内容 不存储内容
 * 路径
 * 名称
 * 创建时间
 * 大小
 * 类型
 */
public interface IFileIndex {

    /**
     * 文件id
     *
     * @return id
     */
    Long getId();

    /**
     * 文件内容
     *
     * @return 文件内容
     */
    String getContent();

    /**
     * 文件路径
     *
     * @return 文件路径
     */
    String getPath();

    /**
     * 文件名称
     *
     * @return 文件名称
     */
    String getName();

    /**
     * 创建时间
     *
     * @return 创建时间
     */
    Long getCreateTime();

    /**
     * 文件大小
     *
     * @return 文件大小
     */
    Long getSize();

    /**
     * 文件类型
     *
     * @return 文件类型
     */
    String getType();
}
