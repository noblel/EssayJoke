package com.noblel.baselibrary.dc.impl;

import android.content.Context;
import android.content.SharedPreferences;

import com.noblel.baselibrary.common.RuntimeEnv;

/**
 * @author Noblel
 * SharePreferences 中数据
 */
public class SPDataBuffer implements IDataBuffer {

    /**
     * Context
     */
    private Context mContext;
    /**
     * 后缀
     */
    private String suffix = "_sharePref";
    /**
     * 存储的数据大小
     */
    private static final int VALUE_SIZE = 100;
    /**
     * 保存的sharePreferences
     */
    private SharedPreferences mSharedPreferences;
    /**
     * 保存的sharePreferences
     */
    private SharedPreferences.Editor mEditor;

    /**
     * 同一个包下可以引用
     * @param context
     */
    SPDataBuffer(Context context){
        mContext = context;
        //名称默认为包名
        mSharedPreferences = mContext.getSharedPreferences(RuntimeEnv.packageName + suffix,Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    @Override
    public void setValue(String uri, String value) {
        mEditor.putString(uri,value);
        if (value.length() > VALUE_SIZE){
            mEditor.commit();
        }else {
            mEditor.apply();
        }
    }

    @Override
    public String getValue(String uri) {
        return mSharedPreferences.getString(uri,null);
    }

    @Override
    public void deleteValue(String uri) {
        mEditor.remove(uri);
        mEditor.commit();
    }

}
