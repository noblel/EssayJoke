package com.noblel.baselibrary.dc.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.noblel.baselibrary.dc.DCConstant;
import com.noblel.baselibrary.log.LogManager;

/**
 * @author Noblel
 */
public class SQLDataHelper extends SQLiteOpenHelper {
    /**
     * 构造方法
     * @param context
     * @param name
     * @param version
     */
    public SQLDataHelper(Context context, String name,int version) {
        super(context, name, null, version);
    }

    /**
     * 第一次创建调用
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLDataBuffer.SQL_CREATE_TABLE);

        LogManager.i(DCConstant.TAG,"SQLDataHelper,  onCreate(SQLiteDatabase db) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQLDataBuffer.SQL_UPDATE_TABLE);
        LogManager.i(DCConstant.TAG,"SQLDataHelper,  onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) ");
    }

}
