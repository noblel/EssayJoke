package com.noblel.framelibrary.skin.attrs;

import android.view.View;

import java.util.List;

/**
 * @author Noblel
 */
public class SkinView {
    private View mView;

    private List<SkinAttr> mSkinAttrs;

    public SkinView(View view, List<SkinAttr> skinAttrs) {
        mView = view;
        mSkinAttrs = skinAttrs;
    }

    public void skin() {
        for (SkinAttr attr : mSkinAttrs) {
            attr.skin(mView);
        }
    }
}
