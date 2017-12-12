package com.noblel.baselibrary.navigationbar;

import android.content.Context;
import android.view.ViewGroup;

/**
 * @author Noblel
 *         导航栏参数
 */
public class AbsNavigationParams {

    public Context mContext;
    public ViewGroup mParent;

    public AbsNavigationParams(Context context, ViewGroup parent) {
        mContext = context;
        mParent = parent;
    }
}
