package com.noblel.baselibrary.common;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;

import com.noblel.baselibrary.log.ExceptionCrashHandler;
import com.noblel.baselibrary.log.LogManager;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * @author Noblel
 * 运行时参数
 */
public class RuntimeEnv {
    private static final String TAG = RuntimeEnv.class.getSimpleName();

    //运行时的Application类型的Context
    public static Context appContext = null;

    //进程名,子进程将按照 '主进程_子进程' 显示
    public static String processName = "";

    //包名
    public static String packageName = "";

    //应用名称
    public static String appName = "";

    /**
     * 初始化
     */
    public static void init(Context context) {
        if (context == null) throw new NullPointerException("RuntimeEnv context is null");

        //防止多次初始化
        if (appContext != null) return;
        //初始化appContext
        appContext = context.getApplicationContext();
        //初始化包名
        packageName = context.getPackageName();

        if (TextUtils.isEmpty(packageName)){
            packageName = "com.noblel";
        }
        Log.i(TAG,"packageName --> " + packageName);
        String[] names = packageName.split("\\.");
        if (names.length > 1) {
            appName = names[names.length - 1];
        } else {
            appName = "noblel";
        }
        Log.i(TAG,"appName --> " + appName);
        //获取当前进程的pid
        int pid = Process.myPid();
        ActivityManager activityManager = (ActivityManager)
                appContext.getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : activityManager.getRunningAppProcesses()) {
            //找到相同的pid就是当前进程了
            if (appProcessInfo.pid == pid){
                processName = appProcessInfo.processName;
            }
        }
        if (!TextUtils.isEmpty(processName)){
            // : 说明是子进程
            if (processName.contains(":")){
                //将 : 替换成 _
                processName = processName.replace(":","_");
            }
        }
        Log.i(TAG,"pid -- > " + pid + " processName --> " + processName);

        LogManager.writeFile(true);

        //初始化CrashHandler
        ExceptionCrashHandler.getInstance().init();

    }

    /**
     * 获取一些简单的信息,软件版本，手机版本，型号等信息存放在HashMap中
     * @return
     */
    public static Map<String,String> getAppInfo(){
        Map<String,String> map = new HashMap<>();
        PackageManager packageManager = appContext.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(appContext.getPackageName(),PackageManager.GET_ACTIVITIES);
            if (packageInfo != null){
                map.put("versionName",packageInfo.versionName);
                map.put("versionCode", "" + packageInfo.versionCode);
                map.put("MODEL", "" + Build.MODEL);
                map.put("SDK_INT", "" + Build.VERSION.SDK_INT);
                map.put("PRODUCT", "" + Build.PRODUCT);
                map.put("MOBLE_INFO", getDeviceInfo());
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获取设备信息
     * @return
     */
    private static String getDeviceInfo(){
        StringBuffer sb = new StringBuffer();
        Field[] fields = Build.class.getDeclaredFields();
        try {
            for (Field field : fields){
                field.setAccessible(true);
                sb.append(field.getName());
                sb.append(" = ");
                sb.append(field.get(null).toString());
                sb.append("\n");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /***
     * 获取当前运行的类的方法 和行数
     * @return
     */
    public static String getCurrentMethodName() {
        StackTraceElement element = getCallLogManagerStackTrace();
        if (element != null){
            String methodName = element.getMethodName();
            int lineNumber = element.getLineNumber();
            return methodName + "() " + lineNumber;
        }
        return null;
    }

    /**
     * 获取当前运行的Class
     * @return
     */
    public static String getCurrentClassName() {
        StackTraceElement element = getCallLogManagerStackTrace();
        if (element != null){
            String clazz = element.getClassName();
            //去最后一个即 类的简名
            if (clazz.contains(".")){
                String strArray[] = clazz.split("\\.");
                clazz = strArray[strArray.length -1];
            }
            return clazz;
        }
        return null;
    }

    /**
     * 获取当前运行的Class
     * @return
     */
    public static String getCurrentFileName() {
        StackTraceElement element = getCallLogManagerStackTrace();
        if (element != null){
            String fileName = element.getFileName();
            return fileName;
        }
        return null;
    }

    /**
     * 获取调用LogManager的调用栈
     * @return
     */
    private static StackTraceElement getCallLogManagerStackTrace(){
        int level = 0;
        //LogManager的全限定名称
        String clazzName = LogManager.class.getCanonicalName();
        //方法数组
        String array[] = new String[]{"v","d","i","w","e"};

        StackTraceElement[] stacks = new Throwable().getStackTrace();
        //依次寻找，找到LogManager的上一级
        for (level = 0 ;level < stacks.length;level++){
            String method = stacks[level].getMethodName();

            if (clazzName.equals(stacks[level].getClassName()) && (method.equals(array[0])
                    || method.equals(array[1]) || method.equals(array[2])
                    || method.equals(array[3]) || method.equals(array[4]))){
                break;
            }
        }

        //返回上一级的调用栈
        if (stacks.length > (level + 1)){
            return stacks[level +1];
        }
        return null;
    }

    /**
     * 申请必要的权限
     */
    public static void requestPermissions(Context context){
        //如果没有权限
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(context, Manifest.permission.VIBRATE)
                        != PackageManager.PERMISSION_GRANTED){
        }
    }
}
