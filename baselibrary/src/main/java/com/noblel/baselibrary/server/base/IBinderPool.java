package com.noblel.baselibrary.server.base;

import android.os.IBinder;

/**
 * @author Noblel
 *         Binder连接池
 */

public interface IBinderPool {

    /**
     * 添加Binder
     *
     * @param name
     * @param binder
     */
    void addBinder(String name, IBinder binder);

    /**
     * 移除Binder
     *
     * @param name
     */
    void removeBinder(String name);

    /**
     * 根据名称查询服务
     *
     * @param name
     * @return
     */
    IBinder queryBinder(String name);


}
