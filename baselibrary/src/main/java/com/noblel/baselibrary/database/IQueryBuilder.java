package com.noblel.baselibrary.database;


import java.util.List;

/**
 * @author Noblel
 * 查询支持
 */
public interface IQueryBuilder<T> {

    // TODO: 接口参考greenDao的设计

    /**
     * 设置查询的行数
     *
     * @param columns
     * @return
     */
    IQueryBuilder columns(String... columns);

    /**
     * 查询的行数参数
     *
     * @param selectionArgs
     * @return
     */
    IQueryBuilder selectionArgs(String... selectionArgs);

    IQueryBuilder having(String having);

    IQueryBuilder orderBy(String orderBy);

    IQueryBuilder limit(String limit);

    IQueryBuilder groupBy(String groupBy);

    IQueryBuilder selection(String selection);

    /**
     * 返回查询条件说查询到的
     *
     * @return
     */
    List<T> query();

    /**
     * 查询所有
     *
     * @return
     */
    List<T> queryAll();

}
