package com.noblel.framelibrary.db.operate;

import android.database.sqlite.SQLiteDatabase;

import com.noblel.framelibrary.db.DaoUtil;

/**
 * @author Noblel
 */

public class DeleteSupport<T> {

    private String mWhereClause;
    private String[] mWhereArgs;
    private Class<?> mClass;
    private SQLiteDatabase mSQLiteDatabase;

    public DeleteSupport(SQLiteDatabase sqLiteDatabase, Class<T> clazz) {
        mSQLiteDatabase = sqLiteDatabase;
        mClass = clazz;
    }

    public DeleteSupport whereClause(String whereClause) {
        mWhereClause = whereClause;
        return this;
    }

    public DeleteSupport whereArgs(String... whereArgs) {
        mWhereArgs = whereArgs;
        return this;
    }

    public void delete() {
        mSQLiteDatabase.delete(DaoUtil.getTableName(mClass), mWhereClause, mWhereArgs);
    }
}
