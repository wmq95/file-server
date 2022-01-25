package top.fan2wan.fileserver.searchengine.dto;

/**
 * @Author: fanT
 * @Date: 2022/1/18 13:23
 * @Description: interface for searchIndex
 */
public interface ISearchIndex {

    /**
     * 内容
     *
     * @return 内容
     */
    String getContent();

    /**
     * 文件名
     *
     * @return 文件名
     */
    String getName();

    /**
     * 文件类型
     *
     * @return 文件类型
     */
    String getType();

    /**
     * 排序字段
     *
     * @return 排序字段
     */
    String getOrderBy();

    /**
     * 降序排序
     *
     * @return 降序排序
     */
    boolean isDesc();

    /**
     * 都满足
     *
     * @return 查询条件 是或还是与
     */
    boolean isAllMatch();

    /**
     * 查询条数
     * @return 查询条数
     */
    int getSearchNumber();
}
