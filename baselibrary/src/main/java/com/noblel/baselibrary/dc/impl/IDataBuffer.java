package com.noblel.baselibrary.dc.impl;

/**
 * @author Noblel
 * 数据缓存的操作接口
 */
public interface IDataBuffer {

    /**
     * 保存值
     * @param uri
     * @param value
     */
    void setValue(String uri, String value);

    /**
     * 读取值
     * @param uri
     * @return
     */
    String getValue(String uri);

    /**
     * 删除指定uri的数据
     * @param uri
     */
    void deleteValue(String uri);

}
