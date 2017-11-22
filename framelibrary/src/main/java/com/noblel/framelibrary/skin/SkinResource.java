package com.noblel.framelibrary.skin;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import java.io.File;
import java.lang.reflect.Method;

/**
 * @author Noblel
 *         皮肤资源管理
 */
public class SkinResource {
    private Resources mResources;

    private String mPackageName;

    public SkinResource(Context context, String path) {
        try {
            //读取本地.skin资源
            Resources superRes = context.getResources();
            AssetManager assetManager = AssetManager.class.newInstance();
            Method method = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            //反射执行方法
            method.invoke(assetManager, path);
            mResources = new Resources(assetManager, superRes.getDisplayMetrics(),
                    superRes.getConfiguration());
            mPackageName = context.getPackageManager()
                    .getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES).packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Drawable getDrawableByName(String resName) {
        try {
            int resId = mResources.getIdentifier(resName, "drawable", mPackageName);
            return mResources.getDrawable(resId);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ColorStateList getColorByName(String resName) {
        try {
            int resId = mResources.getIdentifier(resName, "color", mPackageName);
            return mResources.getColorStateList(resId);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
