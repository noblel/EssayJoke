package com.noblel.baselibrary.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.noblel.baselibrary.log.LogManager;

/**
 * @author Noblel
 */

public abstract class AbsNavigationBar<P extends AbsNavigationParams> implements INavigationBar {

    private static final String TAG = AbsNavigationBar.class.getSimpleName();
    private P mParams;
    private View mNavigationView;

    public AbsNavigationBar(P params) {
        mParams = params;
        createAndBindView();
    }

    public P getParams() {
        return mParams;
    }

    /**
     * 设置文本
     *
     * @param viewId
     * @param text
     */
    protected void setText(int viewId, String text) {
        TextView tv = getView(viewId);
        if (!TextUtils.isEmpty(text)) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        }
    }

    /**
     * 设置点击事件
     *
     * @param viewId
     * @param listener
     */
    protected void setOnClickListener(int viewId, View.OnClickListener listener) {
        getView(viewId).setOnClickListener(listener);
    }

    /**
     * 设置视图可见性
     *
     * @param viewId
     * @param visibility
     */
    protected void setVisibility(int viewId, int visibility) {
        getView(viewId).setVisibility(visibility);
    }

    /**
     * 设置背景色
     *
     * @param viewId
     * @param color
     */
    protected void setBackgroundColor(int viewId, int color) {
        getView(viewId).setBackgroundColor(color);
    }

    /**
     * 设置图片
     *
     * @param viewId
     * @param resId
     */
    protected void setDrawable(int viewId, int resId) {
        ImageView imv = getView(viewId);
        if (imv != null) {
            imv.setVisibility(View.VISIBLE);
            imv.setImageResource(resId);
        }
    }

    public <T extends View> T getView(int viewId) {
        return (T) mNavigationView.findViewById(viewId);
    }

    private void createAndBindView() {
        ViewGroup activityRoot = null;
        //如果没有传viewId的话则需要自动去找他的根布局
        if (mParams.mParent == null || activityRoot == null) {
            //获取了activity的根布局,然后用(ViewGroup) activityRoot.getChildAt(0)拿到了自己写的布局layout的第一个根布局
            activityRoot = (ViewGroup) ((Activity) (mParams.mContext))
                    .findViewById(android.R.id.content);
            mParams.mParent = (ViewGroup) activityRoot.getChildAt(0);
            LogManager.i(TAG, "activityRoot size : " + activityRoot.getChildCount()
                    + " mParams.mParent:" + mParams.mParent);
        }
        //这样拿到的是自己定义的布局外面的那层布局
        activityRoot = (ViewGroup) ((Activity) (mParams.mContext)).getWindow().getDecorView();
        if (mParams.mParent == null)
            return;
        //创建view
        mNavigationView = LayoutInflater.from(mParams.mContext)
                .inflate(bindLayoutId(), mParams.mParent, false);
        if (mParams.mParent instanceof RelativeLayout) {
            //相对布局
            //先构造一个线性布局,指定垂直排列
            LinearLayout newActivityRoot = new LinearLayout(mParams.mContext);
            newActivityRoot.setOrientation(LinearLayout.VERTICAL);

            //移除原有的activityRoot的parent，否则会报"The specified child already has a parent. " +
            //"You must call removeView() on the child's parent first. 异常
            ViewGroup viewParent = (ViewGroup) mParams.mParent.getParent();
            viewParent.removeAllViews();

            //将titleBar添加为第一个child,原来的activityRoot为第二个
            newActivityRoot.addView(mNavigationView, 0);
            newActivityRoot.addView(mParams.mParent, 1);
            //将新的activityRoot添加到android.R.id.content中
            activityRoot.addView(newActivityRoot, 0);

            LogManager.i(TAG, "mParams.mActivityRoot,child at 0 view:");

        } else {
            //添加View到mParams.mParent中
            mParams.mParent.addView(mNavigationView, 0);
        }
        LogManager.i(TAG, "mParams.mActivityRoot,child:" + mParams.mParent.getChildCount());
        //绑定
        applyView();
    }

    /**
     * Builder设计模式  AbsNavigationBar/Builder/参数Params
     */
    public abstract static class Builder {

        public Builder(Context context, ViewGroup parent) {

        }

        public abstract AbsNavigationBar builder();
    }
}
