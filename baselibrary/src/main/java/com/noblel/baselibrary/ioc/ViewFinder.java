package com.noblel.baselibrary.ioc;

import android.app.Activity;
import android.view.View;

/**
 * @author Noblel
 * View的findViewById()的辅助类
 */

public class ViewFinder {
    private Activity mActivity;
    private View mView;

    public ViewFinder(Activity activity) {
        mActivity =  activity;
    }

    public ViewFinder(View view) {
        mView = view;
    }

    public View findViewById(int viewId){
        if (mActivity != null) {
            return mActivity.findViewById(viewId);
        }
        if (mView != null) {
            return mView.findViewById(viewId);
        }
        return null;
    }
}
