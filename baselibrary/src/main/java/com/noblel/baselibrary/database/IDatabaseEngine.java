package com.noblel.baselibrary.database;

import android.content.Context;

/**
 * @author Noblel
 * 数据库引擎
 */
public interface IDatabaseEngine {

    /**
     * 初始化数据库
     * @param context
     * @param path
     * @param dbName
     * @return
     */
    boolean initDatabase(Context context, String path, String dbName);

    /**
     * 更新数据库
     * @param dbName
     * @param oldVersion
     * @param newVersion
     */
    void updateDatabase(String dbName, int oldVersion, int newVersion);

    /**
     * 删除数据库
     * @param dbName
     */
    void dropDatabase(String dbName);

    /**
     * 获取数据库操作的Session
     * @param dbName
     * @return
     */
    <T> IDatabaseSession<T> getDBSession(String dbName, Class<T> clazz);
}
