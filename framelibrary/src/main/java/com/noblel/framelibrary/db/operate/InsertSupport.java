package com.noblel.framelibrary.db.operate;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.util.ArrayMap;

import com.noblel.framelibrary.db.DaoUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author Noblel
 */

public class InsertSupport<T> {
    private SQLiteDatabase mSQLiteDatabase;
    private Class<?> mClass;
    private static final Object[] sPutMethodArgs = new Object[2];
    private static final Map<String, Method> sPutMethodsMap = new ArrayMap<>();

    public InsertSupport(SQLiteDatabase SQLiteDatabase, Class<?> aClass) {
        mSQLiteDatabase = SQLiteDatabase;
        mClass = aClass;
    }

    //插入
    public long insert(T o) {
        ContentValues contentValues = createContentValuesByObj(o);
        return mSQLiteDatabase.insert(DaoUtil.getTableName(mClass), null, contentValues);
    }

    public void insertList(List<T> data) {
        //开启事务
        mSQLiteDatabase.beginTransaction();
        for (T t : data) {
            //调用单条插入
            insert(t);
        }
        mSQLiteDatabase.setTransactionSuccessful();
        mSQLiteDatabase.endTransaction();
    }

    //obj -->  ContentValues
    private ContentValues createContentValuesByObj(T o) {
        ContentValues values = new ContentValues();
        //封装values
        Field[] fields = mClass.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                String key = field.getName();
                Object value = field.get(o);
                //模仿AppCompatViewInflater源码进行优化 缓存方法
                sPutMethodArgs[0] = key;
                sPutMethodArgs[1] = value;
                String filedTypeName = field.getType().getName();
                //获取put方法通过反射执行
                Method putMethod = sPutMethodsMap.get(filedTypeName);
                if (putMethod == null) {
                    putMethod = ContentValues.class.getDeclaredMethod("put", String.class, value.getClass());
                    sPutMethodsMap.put(filedTypeName, putMethod);
                }
                putMethod.invoke(values, sPutMethodArgs);
            } catch (Exception e) {
                return null;
            } finally {
                sPutMethodArgs[0] = null;
                sPutMethodArgs[1] = null;
            }
        }
        return values;
    }
}
