package com.noblel.framelibrary.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;

/**
 * @author Noblel
 */

public class DaoSupportFactory {

    private static DaoSupportFactory mFactory;
    private SQLiteDatabase mSQLiteDatabase;

    public static DaoSupportFactory getInstance() {
        if (mFactory == null) {
            synchronized (DaoSupportFactory.class) {
                if (mFactory == null) {
                    return new DaoSupportFactory();
                }
            }
        }
        return mFactory;
    }

    //持有外部数据库的引用
    private DaoSupportFactory() {
        //把数据库放到内存卡里面   是否有存储卡  6.0动态申请权限
        File dbRoot = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "essay_joke" + File.separator + "database");
        if (!dbRoot.exists()) {
            dbRoot.mkdirs();
        }

        File dbFile = new File(dbRoot, "essay.db");
        mSQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
    }

    public <T> IDaoSupport getDao(Class<T> clazz) {
        IDaoSupport<T> daoSupport = new DaoSupportImpl<>();
        daoSupport.init(mSQLiteDatabase,clazz);
        return daoSupport;
    }

}
