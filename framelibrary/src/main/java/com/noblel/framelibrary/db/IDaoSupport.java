package com.noblel.framelibrary.db;

import android.database.sqlite.SQLiteDatabase;

import com.noblel.framelibrary.db.operate.DeleteSupport;
import com.noblel.framelibrary.db.operate.InsertSupport;
import com.noblel.framelibrary.db.operate.QuerySupport;
import com.noblel.framelibrary.db.operate.UpdateSupport;


/**
 * @author Noblel
 */

public interface IDaoSupport<T> {
    void init(SQLiteDatabase database, Class<T> clazz);

    //增加
    InsertSupport<T> insertSupport();

    //删除
    DeleteSupport<T> deleteSupport();

    //查询
    QuerySupport<T> querySupport();

    //更新
    UpdateSupport<T> updateSupport();

}
