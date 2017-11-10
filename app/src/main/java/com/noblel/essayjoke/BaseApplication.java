package com.noblel.essayjoke;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.noblel.baselibrary.ExceptionCrashHandler;
import com.noblel.baselibrary.http.HttpUtils;
import com.noblel.baselibrary.http.OkHttpEngine;


/**
 * @author Noblel
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HttpUtils.init(new OkHttpEngine());
        //设置全局异常捕捉类
        ExceptionCrashHandler.getInstance().init(this);

    }

    public static int getLocalVersion(Context ctx) {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    public static String getLocalVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }
}