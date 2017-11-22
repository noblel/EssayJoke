package com.noblel.framelibrary.skin.config;

import android.content.Context;
import android.content.SharedPreferences;

import static com.noblel.framelibrary.skin.config.SkinConfig.SKIN_INFO_NAME;
import static com.noblel.framelibrary.skin.config.SkinConfig.SKIN_PATH_NAME;

/**
 * @author Noblel
 */
public class SkinPreUtils {
    private static SkinPreUtils mInstance;
    private Context mContext;

    private SkinPreUtils(Context context) {
        mContext = context.getApplicationContext();
    }

    public static SkinPreUtils getInstance(Context context) {
        if (mInstance == null) {
            synchronized (SkinPreUtils.class) {
                if (mInstance == null) {
                    mInstance = new SkinPreUtils(context);
                }
            }
        }
        return mInstance;
    }

    //保存当前皮肤路径
    public void saveSkinPath(String skinPath) {
        SharedPreferences sp = mContext.getSharedPreferences(SKIN_INFO_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(SKIN_PATH_NAME,skinPath).apply();
    }

    public String getSkinPath() {
        return mContext.getSharedPreferences(SKIN_INFO_NAME, Context.MODE_PRIVATE)
                .getString(SKIN_PATH_NAME,"");
    }

    public void clearSkinInfo() {
        saveSkinPath("");
    }
}
