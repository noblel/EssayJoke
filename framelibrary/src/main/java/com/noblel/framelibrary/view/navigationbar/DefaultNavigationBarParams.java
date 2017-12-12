package com.noblel.framelibrary.view.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.noblel.baselibrary.navigationbar.AbsNavigationParams;

/**
 * @author Noblel
 */
public class DefaultNavigationBarParams extends AbsNavigationParams{
    /**
     * 主标题
     */
    public String mTitle;

    /**
     * 标题右侧文字
     */
    public String mRightText;

    /**
     * 右侧点击事件
     */
    public View.OnClickListener mRightClickListener;
    /**
     * 左侧点击事件
     */
    public View.OnClickListener mLeftClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 关闭当前Activity
            ((Activity) mContext).finish();
        }
    };

    /**
     * 左侧图标是否可见
     */
    public int mLeftIconVisible = View.VISIBLE;
    /**
     * 背景色
     */
    public int mBackgroundColor;

    public DefaultNavigationBarParams(Context context, ViewGroup parent) {
        super(context, parent);
    }
}
