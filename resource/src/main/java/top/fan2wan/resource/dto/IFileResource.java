package top.fan2wan.resource.dto;

/**
 * @Author: fanT
 * @Date: 2022/1/26 9:26
 * @Description: IFileResource
 */
public interface IFileResource {

    /**
     * id
     *
     * @return id
     */
    Long getId();

    /**
     * 文件名
     *
     * @return 文件名
     */
    String getName();

    /**
     * 文件路径 表示oss源文件路径
     *
     * @return 文件路径
     */
    String getPath();

    /**
     * 文件大小
     *
     * @return 文件大小
     */
    Long getSize();

    /**
     * 文件创建时间
     *
     * @return 文件创建时间
     */
    Long getCreateTime();

    /**
     * 文件类型
     *
     * @return 文件类型
     */
    String getType();

    /**
     * 转换后的文件路径
     *
     * @return 转换后的文件路径
     */
    String getConvertedPath();

    /**
     * 文件转换状态
     *
     * @return 文件转换状态
     */
    String getConvertedStatus();
}
