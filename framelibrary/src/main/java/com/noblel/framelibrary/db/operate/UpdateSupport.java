package com.noblel.framelibrary.db.operate;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.util.ArrayMap;

import com.noblel.framelibrary.db.DaoUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author Noblel
 */

public class UpdateSupport<T> {
    private String mWhereClause;
    private String[] mWhereArgs;
    private Class<T> mClass;
    private SQLiteDatabase mSQLiteDatabase;
    //     contentValues执行put方法时传入的参数
    private static final Object[] mPutMethodArgs = new Object[2];
    //    缓存Put方法
    private static final Map<String, Method> mPutMethods = new ArrayMap<>();

    public UpdateSupport(SQLiteDatabase SQLiteDatabase, Class<T> clazz) {
        mClass = clazz;
        mSQLiteDatabase = SQLiteDatabase;
    }

    public UpdateSupport whereClause(String whereClause) {
        mWhereClause = whereClause;
        return this;
    }

    public UpdateSupport whereArgs(String... whereArgs) {
        mWhereArgs = whereArgs;
        return this;
    }

    public int update(T obj) {
        ContentValues values = createContentValuesByObj(obj);
        return mSQLiteDatabase.update(DaoUtil.getTableName(mClass),
                values, mWhereClause, mWhereArgs);
    }

    private ContentValues createContentValuesByObj(T obj) {
        ContentValues values = new ContentValues();

//        封装values
        Field[] fields = mClass.getDeclaredFields();
        for (Field field : fields) {
            try {
//              设置访问权限
                field.setAccessible(true);

                String key = field.getName();
                //"$change"和"serialVersionUID"这两个字段是Android studio2.0的Instant Run（用于加快应用安装）功能自动生成的
//            Android Studio2.0之前没有这两个字段，或者把Instant Run功能关闭也不会产生这两个字段
                if (key.equalsIgnoreCase("$change") || key.equalsIgnoreCase("serialVersionUID")) {
                    continue;
                }
//                获取value
                Object value = field.get(obj);
                mPutMethodArgs[0] = key;
                mPutMethodArgs[1] = value;

//              使用反射 获取方法 反射一定程度上影响性能 需要缓存方法
                String fieldTypeName = field.getType().getName();
                Method putMethod = mPutMethods.get(fieldTypeName);
                if (putMethod == null) {
                    putMethod = ContentValues.class.getDeclaredMethod("put", String.class, value.getClass());
                    mPutMethods.put(fieldTypeName, putMethod);
                }
//                通过反射执行方法
                putMethod.invoke(values, mPutMethodArgs[0], mPutMethodArgs[1]);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mPutMethodArgs[0] = null;
                mPutMethodArgs[1] = null;
            }

        }
        return values;
    }
}
