package com.noblel.baselibrary.database.engine.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;


import com.noblel.baselibrary.database.DB;
import com.noblel.baselibrary.database.IDatabaseEngine;
import com.noblel.baselibrary.database.IDatabaseSession;
import com.noblel.baselibrary.log.LogManager;

import java.io.File;
import java.io.IOException;

/**
 * @author Noblel
 */
public class SQLiteDatabaseEngine implements IDatabaseEngine {

    /**
     * 数据库路径
     */
    private String mDatabasePath;

    /**
     * 数据库名称
     */
    private String mDatabaseName;


    @Override
    public boolean initDatabase(Context context, String path, String dbName) {
        mDatabasePath = path;
        mDatabaseName = dbName;

        boolean flag = false;

        //数据库放在Android/data下
        File dbDir = new File(DB.DATABASE_PATH + mDatabasePath);

        if (!dbDir.exists()){
            dbDir.mkdirs();
        }

        File dbFile = new File(dbDir,mDatabaseName);
        LogManager.i(DB.TAG,"dbFile --> " + dbFile);

        //如果存在就删除文件
        if (dbFile.exists()){
            dbFile.delete();
        }

        boolean create = false;

        try {
            create = dbFile.createNewFile();
        } catch (IOException e) {
            create = false;
            e.printStackTrace();
        }

        if (create){
            //打开创建数据库
            SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(dbFile,null);
            if (database != null){
                flag = true;
                return flag;
            }
        }

        LogManager.e(DB.TAG,"create database fail:" + dbName);
        return flag;
    }

    @Override
    public void updateDatabase(String dbName, int oldVersion, int newVersion) {

    }

    @Override
    public void dropDatabase(String dbName) {

    }

    @Override
    public <T> IDatabaseSession<T> getDBSession(String dbName, Class<T> clazz) {
        SQLiteDatabase database = null;
        if (TextUtils.isEmpty(mDatabasePath)){
            database = SQLiteDatabase.openDatabase(DB.DATABASE_PATH + dbName,null,SQLiteDatabase.OPEN_READWRITE);
        }else {
            database = SQLiteDatabase.openDatabase(DB.DATABASE_PATH + mDatabasePath + File.separator + dbName,null,SQLiteDatabase.OPEN_READWRITE);
        }
        return new SQLiteDatabaseSession<T>(database,clazz);
    }
}
