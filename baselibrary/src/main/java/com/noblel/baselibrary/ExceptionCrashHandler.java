package com.noblel.baselibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Noblel
 * 单例模式异常的捕捉类
 */

public class ExceptionCrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "ExceptionCrashHandler";

    private static ExceptionCrashHandler mInstance;

    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultUncaughtExceptionHandler;

    public void init(Context context) {
        this.mContext = context;

        //设置全局的异常为本类
        Thread.currentThread().setUncaughtExceptionHandler(this);
        //获取系统默认的
        mDefaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    public static ExceptionCrashHandler getInstance() {
        if (mInstance == null) {
            synchronized (ExceptionCrashHandler.class) {
                if (mInstance == null) {
                    mInstance = new ExceptionCrashHandler();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        //全局异常
        Log.e(TAG, "报异常了");
        //写入本地文件, 异常,当前的版本,手机信息
        //1.崩溃详细信息

        //2.应用信息 包名 版本号
        getMobileInfo();
        //3.手机信息
        //4.保存当前文件，等应用再次启动上传(不在此处处理)
        String crashFileName = saveInfoToSD(e);
        Log.e(TAG, "fileName --> " + crashFileName);

        //缓存崩溃日志文件
        cacheCrashFile(crashFileName);
        //让系统默认处理
        mDefaultUncaughtExceptionHandler.uncaughtException(t, e);
    }

    /**
     * 获取崩溃文件信息
     *
     * @return
     */
    public File getCrashFile() {
        String crashFileName = mContext.getSharedPreferences("crash", Context.MODE_PRIVATE).getString("CRASH_FILE_NAME", "");
        return new File(crashFileName);
    }

    private HashMap<String, String> obtainSimpleInfo(Context context) {
        HashMap<String, String> map = new HashMap<>();
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("versionName", info.versionName);
        map.put("versionCode", "" + info.versionCode);
        map.put("MODEL", Build.MODEL);
        map.put("SDK_INT", "" + Build.VERSION.SDK_INT);
        map.put("PRODUCT", Build.PRODUCT);
        map.put("MOBILE_INFO", getMobileInfo());
        map.put("versionName", Build.MODEL);
        return map;
    }

    private String saveInfoToSD(Throwable ex) {
        String fileName = null;
        StringBuffer sb = new StringBuffer();

        //1.手机信息,应用信息
        for (Map.Entry<String, String> entry : obtainSimpleInfo(mContext).entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append("=").append(value).append("\n");
        }

        //2.崩溃的详细信息
        sb.append(obtainExceptionInfo(ex));

        //3.保存文件  手机应用目录，没有拿手机SD卡目录 6.0需要申请权限
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File dir = new File(mContext.getFilesDir() + File.separator + "crash" + File.separator);
            //删除之前的异常信息
            if (dir.exists()) {
                //删除该目录的子文件
                deleteDir(dir);
            }
            if (!dir.exists()) {
                dir.mkdir();
            }
            try {
                fileName = dir.toString() + File.separator + getAssignTime("yyyy_MM_dd_HH_mm") + ".txt";
                FileOutputStream fos = new FileOutputStream(fileName);
                fos.write(sb.toString().getBytes());
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

    private String getAssignTime(String dataFormatStr) {
        DateFormat dateFormat = new SimpleDateFormat(dataFormatStr);
        long currentTime = System.currentTimeMillis();
        return dateFormat.format(currentTime);
    }

    private void cacheCrashFile(String fileName) {
        SharedPreferences sp = mContext.getSharedPreferences("crash", Context.MODE_PRIVATE);
        sp.edit().putString("CRASH_FILE_NAME", fileName).commit();
    }

    private boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            //递归删除目录的子目录
            for (File child : children) {
                child.delete();
            }
        }
        return true;
    }

    /**
     * 获取系统未捕获的错误信息
     *
     * @param throwable
     * @return
     */
    private String obtainExceptionInfo(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        printWriter.close();
        return stringWriter.toString();
    }

    /**
     * 获取手机信息
     *
     * @return
     */
    private static String getMobileInfo() {
        StringBuffer sb = new StringBuffer();
        try {
            //利用反射获取Build的所有属性
            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                String value = field.get(null).toString();
                sb.append(name + "=" + value);
                sb.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
