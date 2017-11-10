package com.noblel.framelibrary.db;

import android.database.sqlite.SQLiteDatabase;

import com.noblel.framelibrary.db.operate.DeleteSupport;
import com.noblel.framelibrary.db.operate.InsertSupport;
import com.noblel.framelibrary.db.operate.QuerySupport;
import com.noblel.framelibrary.db.operate.UpdateSupport;

import java.lang.reflect.Field;

/**
 * @author Noblel
 */

public class DaoSupportImpl<T> implements IDaoSupport<T> {
    private SQLiteDatabase mSQLiteDatabase;
    private Class<T> mClazz;

    private InsertSupport<T> mInsertSupport;
    private DeleteSupport<T> mDeleteSupport;
    private UpdateSupport<T> mUpdateSupport;
    private QuerySupport<T> mQuerySupport;

    public void init(SQLiteDatabase database, Class<T> clazz) {
        this.mSQLiteDatabase = database;
        mClazz = clazz;
        //创建表
        StringBuffer sb = new StringBuffer();
        sb.append("create table if not exists ");
        sb.append(DaoUtil.getTableName(clazz));
        sb.append(" (id integer primary key autoincrement, ");
        //通过反射拿到类的属性
        Field[] fields = mClazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            String type = field.getType().getSimpleName();//获得的是int(integer) string() boolean 所以需要转换
            sb.append(name).append(DaoUtil.getColumnType(type)).append(", ");
        }
        sb.replace(sb.length() - 2, sb.length(), ")");
        String sql = sb.toString();
        mSQLiteDatabase.execSQL(sql);
    }

    @Override
    public InsertSupport<T> insertSupport() {
        if (mInsertSupport == null) {
            mInsertSupport = new InsertSupport<>(mSQLiteDatabase, mClazz);
        }
        return mInsertSupport;
    }

    @Override
    public DeleteSupport<T> deleteSupport() {
        if (mDeleteSupport == null) {
            mDeleteSupport = new DeleteSupport<>(mSQLiteDatabase, mClazz);
        }
        return mDeleteSupport;
    }

    @Override
    public QuerySupport<T> querySupport() {
        if (mQuerySupport == null) {
            mQuerySupport = new QuerySupport<>(mSQLiteDatabase, mClazz);
        }
        return mQuerySupport;
    }

    @Override
    public UpdateSupport<T> updateSupport() {
        if (mUpdateSupport == null) {
            mUpdateSupport = new UpdateSupport<>(mSQLiteDatabase, mClazz);
        }
        return mUpdateSupport;
    }


}
