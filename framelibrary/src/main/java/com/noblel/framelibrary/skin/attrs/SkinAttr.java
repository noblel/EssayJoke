package com.noblel.framelibrary.skin.attrs;

import android.view.View;

/**
 * @author Noblel
 */
public class SkinAttr {
    private String mResName;

    private SkinType mType;

    public SkinAttr(String resName, SkinType skinType) {
        mResName = resName;
        mType = skinType;
    }

    public void skin(View view) {
        mType.skin(view, mResName);
    }

}
