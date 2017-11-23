package com.noblel.baselibrary.permission;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import java.util.List;

/**
 * @author Noblel
 */
public class PermissionHelper {
    private Object mObject;
    private int mRequestCode;
    private String[] mRequestPermission;

    private PermissionHelper(Object object) {
        mObject = object;
    }

    public static void requestPermission(Activity activity, int requestCode, String[] permission) {
        PermissionHelper.with(activity)
                .requestCode(requestCode)
                .requestPermission(permission)
                .request();
    }

    public static PermissionHelper with(Activity activity) {
        return new PermissionHelper(activity);
    }

    public static PermissionHelper with(Fragment fragment) {
        return new PermissionHelper(fragment);
    }

    public PermissionHelper requestCode(int requestCode) {
        mRequestCode = requestCode;
        return this;
    }

    //添加请求权限数据
    public PermissionHelper requestPermission(String... permissions) {
        mRequestPermission = permissions;
        return this;
    }

    //最终判断和发起请求权限
    public void request() {
        //1.判断当前的版本是否是6.0及以上
        if (!PermissionUtils.isOverMarshmallow()) {
            //1.1如果不是6.0以上直接反射执行方法
            PermissionUtils.executeSucceedMethod(mObject, mRequestCode);
            return;
        }
        //1.2如果是6.0是首先判断权限是否授予
        List<String> deniedPermissions =
                PermissionUtils.getDeniedPermission(mObject, mRequestPermission);
        //1.2.1授予权限,直接反射执行方法
        if (deniedPermissions.size() == 0) {
            PermissionUtils.executeSucceedMethod(mObject, mRequestCode);
        } else {
            //1.2.2没有授予权限,请求权限
            ActivityCompat.requestPermissions(PermissionUtils.getActivity(mObject),
                    deniedPermissions.toArray(new String[deniedPermissions.size()]),mRequestCode);
        }
    }

    /**
     * 处理申请权限的回调
     */
    public static void requestPermissionResult(Object object, int requestCode, String[] permissions) {
        List<String> deniedPermission = PermissionUtils.getDeniedPermission(object, permissions);
        if (deniedPermission.size() == 0) {
            //权限全部同意
            PermissionUtils.executeSucceedMethod(object,requestCode);
        } else {
            //有未同意权限
            PermissionUtils.executeFailedMethod(object,requestCode);
        }
    }
}
