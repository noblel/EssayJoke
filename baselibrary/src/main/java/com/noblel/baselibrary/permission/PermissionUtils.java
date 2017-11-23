package com.noblel.baselibrary.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Noblel
 */
public class PermissionUtils {
    private PermissionUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /*
    判断Android系统版本
     */
    public static boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static void executeSucceedMethod(Object object, int requestCode) {
        //获取class中所有的方法
        Method[] methods = object.getClass().getDeclaredMethods();
        for (Method method : methods) {
            PermissionSuccess successMethod = method.getAnnotation(PermissionSuccess.class);
            if (successMethod != null) {
                int methodCode = successMethod.requestCode();
                if (requestCode == methodCode) {
                    //反射执行方法
                    executeMethod(object,method);
                }
            }
        }
    }

    private static void executeMethod(Object reflectObj,Method method) {
        try {
            method.setAccessible(true);
            method.invoke(reflectObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param object Activity&Fragment
     * @return 没有授予权限的列表
     */
    public static List<String> getDeniedPermission(Object object, String[] requestPermission) {
        List<String> deniedPermissions = new ArrayList<>();
        for (String permission : requestPermission) {
            if (ContextCompat.checkSelfPermission(getActivity(object),permission)
                    == PackageManager.PERMISSION_DENIED){
                deniedPermissions.add(permission);
            }
        }
        return deniedPermissions;
    }

    public static Activity getActivity(Object object) {
        if (object instanceof Activity) {
            return (Activity)object;
        }
        if (object instanceof Fragment) {
            return ((Fragment)object).getActivity();
        }
        return null;
    }

    //执行失败的方法
    public static void executeFailedMethod(Object object, int requestCode) {
        //获取class中所有的方法
        Method[] methods = object.getClass().getDeclaredMethods();
        for (Method method : methods) {
            PermissionFail failMethod = method.getAnnotation(PermissionFail.class);
            if (failMethod != null) {
                int methodCode = failMethod.requestCode();
                if (requestCode == methodCode) {
                    //反射执行方法
                    executeMethod(object,method);
                }
            }
        }
    }
}
