package com.noblel.framelibrary.skin;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.noblel.framelibrary.skin.attrs.SkinView;
import com.noblel.framelibrary.skin.callback.ISkinChangeListener;
import com.noblel.framelibrary.skin.config.SkinConfig;
import com.noblel.framelibrary.skin.config.SkinPreUtils;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Noblel
 */
public class SkinManager {
    private static SkinManager mInstance;
    private Context mContext;
    private Map<ISkinChangeListener, List<SkinView>> mSkinViews = new ArrayMap<>();
    private SkinResource mSkinResource;

    static {
        mInstance = new SkinManager();
    }

    public void init(Context context) {
        mContext = context.getApplicationContext();
        String currentSkinPath = SkinPreUtils.getInstance(context).getSkinPath();
        File file = new File(currentSkinPath);
        if (!file.exists()) {
            SkinPreUtils.getInstance(context).clearSkinInfo();
            return;
        }

        String packageName = context.getPackageManager()
                .getPackageArchiveInfo(currentSkinPath, PackageManager.GET_ACTIVITIES).packageName;
        if (TextUtils.isEmpty(packageName)) {
            SkinPreUtils.getInstance(context).clearSkinInfo();
            return;
        }

        //TODO 校验签名
        mSkinResource = new SkinResource(mContext, currentSkinPath);
    }

    public static SkinManager getInstance() {
        return mInstance;
    }

    public int loadSkin(String path) {

        File file = new File(path);
        if (!file.exists()) {
            return SkinConfig.SKIN_FILE_NOT_EXISTS;
        }

        String packageName = mContext.getPackageManager()
                .getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES).packageName;
        if (TextUtils.isEmpty(packageName))
            return SkinConfig.SKIN_FILE_ERROR;

        //当前的皮肤是否一样
        String currentSkinPath = SkinPreUtils.getInstance(mContext).getSkinPath();
        if (path.equals(currentSkinPath))
            return SkinConfig.SKIN_CHANGE_SUCCESS;

        //校验签名
        //初始化资源管理
        mSkinResource = new SkinResource(mContext, path);
        changeSkin();
        //保存皮肤状态
        saveSkinStatus(path);
        return SkinConfig.SKIN_CHANGE_SUCCESS;
    }

    /*
        改变皮肤
     */
    private void changeSkin() {
        Set<ISkinChangeListener> keys = mSkinViews.keySet();
        for (ISkinChangeListener listener : keys) {
            List<SkinView> skinViews = mSkinViews.get(listener);
            for (SkinView skinView : skinViews) {
                skinView.skin();
            }
        }
    }

    private void saveSkinStatus(String path) {
        SkinPreUtils.getInstance(mContext).saveSkinPath(path);
    }

    public int restore() {
        //判断当前有没有皮肤
        String currentSkinPath = SkinPreUtils.getInstance(mContext).getSkinPath();
        if (TextUtils.isEmpty(currentSkinPath))
            return SkinConfig.SKIN_DEFAULT;
        String path = mContext.getPackageResourcePath();
        //初始化资源管理
        mSkinResource = new SkinResource(mContext, path);
        changeSkin();
        SkinPreUtils.getInstance(mContext).clearSkinInfo();
        return SkinConfig.SKIN_CHANGE_SUCCESS;
    }

    public List<SkinView> getSkinViews(ISkinChangeListener skinChangeListener) {
        return mSkinViews.get(skinChangeListener);
    }

    public void register(ISkinChangeListener skinChangeListener, List<SkinView> skinViews) {
        mSkinViews.put(skinChangeListener, skinViews);
    }

    public SkinResource getSkinResource() {
        return mSkinResource;
    }

    public void checkChangeSkin(SkinView skinView) {
        //如果保存了皮肤路径
        String path = SkinPreUtils.getInstance(mContext).getSkinPath();
        if (!TextUtils.isEmpty(path))
            skinView.skin();
    }

    //防止内存泄漏
    public void unregister(ISkinChangeListener skinChangeListener) {
        mSkinViews.remove(skinChangeListener);
    }
}
