package com.noblel.baselibrary.server.base;

import android.content.ComponentName;
import android.os.IBinder;


/**
 * @author Noblel
 */
public final class ServerManager {

    /**
     * 内部类方式构造单例
     */
    private static class SingleHolder {
        static final ServerManager sInstance = new ServerManager();
    }

    /**
     * 构造方法私有化
     */
    private ServerManager() {

    }

    /**
     * 内部类方式获取单例
     *
     * @return
     */
    public static ServerManager getInstance() {
        return SingleHolder.sInstance;
    }

    /**
     * 添加Server
     *
     * @param name
     * @param componentName
     * @param server
     */
    public void addService(String name, ComponentName componentName, IBinder server) {

    }

    /**
     * 移除service
     *
     * @param name
     */
    public void removeService(String name) {

    }

}
