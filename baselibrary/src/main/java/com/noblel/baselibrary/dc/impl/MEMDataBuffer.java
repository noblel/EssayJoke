package com.noblel.baselibrary.dc.impl;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Noblel
 * 内存数据
 */
public class MEMDataBuffer implements IDataBuffer {

    /**
     * 保存数据的Map
     */
    private Map<String,String> mDataMap;

    /**
     * 同一个包下可以引用
     * @param context
     */
    MEMDataBuffer(Context context){
        mDataMap = new HashMap<>();
    }

    @Override
    public void setValue(String uri, String value) {
        mDataMap.put(uri,value);
    }

    /**
     * 获取值
     * @param key
     * @return
     */
    @Override
    public String getValue(String key) {
        return mDataMap.get(key);
    }

    @Override
    public void deleteValue(String uri) {
        mDataMap.remove(uri);
    }

}
