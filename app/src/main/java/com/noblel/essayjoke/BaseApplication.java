package com.noblel.essayjoke;

import android.app.Application;
import com.noblel.baselibrary.SDKManager;
import com.noblel.framelibrary.skin.SkinManager;

/**
 * @author Noblel
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            SDKManager.initSDK(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //设置全局异常捕捉类
        SkinManager.getInstance().init(this);
    }
}
