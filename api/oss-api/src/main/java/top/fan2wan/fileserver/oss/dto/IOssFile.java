package top.fan2wan.fileserver.oss.dto;

/**
 * @Author: fanT
 * @Date: 2021/10/19 13:20
 * @Description: dto for OssFile
 */
public interface IOssFile {
    /**
     * 本地路径
     *
     * @return 文件本地路径
     */
    String getLocalPath();

    /**
     * 获取文件名称
     *
     * @return 文件名称
     */
    String getFileName();

    /**
     * 文件oss 对应地址
     *
     * @return 文件保存路径
     */
    String getOssFilePath();

    /**
     * 获取文件大小
     *
     * @return 文件大小
     */
    long getFileSize();

    /**
     * 获取文件 策略
     *
     * @return 文件策略
     */
    String getFilePolicy();
}
