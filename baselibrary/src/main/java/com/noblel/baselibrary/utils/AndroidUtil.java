package com.noblel.baselibrary.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.noblel.baselibrary.common.RuntimeEnv;

/**
 * @author Noblel
 */
public class AndroidUtil {
    /**
     * 是否在Debug状态下
     */
    public static boolean isDebug(Context context) {
        return context.getApplicationInfo() != null
                && (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    /**
     * @return 版本号
     */
    public static String getAppVersionName(Context context) {
        String appVersion;
        try {
            appVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0)
                    .versionName;
        } catch (PackageManager.NameNotFoundException e) {
            appVersion = "1.0.0";
        }
        return appVersion;
    }

    /**
     * 返回 data/data/packageName/files  不同系统上稍微有差异 ，例如8.0：/data/user/0/packageName/files
     */
    public static String getInnerDataPath() {
        if (RuntimeEnv.appContext == null) {
            throw new NullPointerException("RuntimeEnv.appContext is null");
        }
        return RuntimeEnv.appContext.getFilesDir().getAbsolutePath();
    }

    /**
     * 返回 Android/data/packageName/files 路径 ，完整路径 /storage/emulated/0/Android/data/packageName/files
     *
     * @return
     */
    public static String getExternalDataPath() {
        if (RuntimeEnv.appContext == null) {
            throw new NullPointerException("RuntimeEnv.appContext is null");
        }
        return RuntimeEnv.appContext.getExternalFilesDir(null).getAbsolutePath();
    }

    /**
     * 返回SD卡根路径 / ，完整路径：/storage/emulated/0
     *
     * @return
     */
    public static String getSdCardDataPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }
}
