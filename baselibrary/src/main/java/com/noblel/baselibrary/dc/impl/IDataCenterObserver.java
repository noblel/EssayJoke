package com.noblel.baselibrary.dc.impl;

import java.util.Set;

/**
 * @author Noblel
 * 数据中心变化通知客户端
 */
public interface IDataCenterObserver {

    /**
     * 数据更新
     * @param urlSet
     */
    void onDataChanged(Set<String> urlSet);

    /**
     * 数据被删除
     * @param urlSet
     */
    void onDataDeleted(Set<String> urlSet);


}
