package com.noblel.baselibrary.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author Noblel
 */

public abstract class AbsNavigationBar<P extends AbsNavigationBar.Builder.AbsNavigationParams> implements INavigationBar {

    private P mParams;
    private View mNavigationView;

    public AbsNavigationBar(P params) {
        mParams = params;
        createAndBindView();
    }

    public P getParams() {
        return mParams;
    }

    //设置文本
    protected void setText(int viewId, String text) {
        TextView tv = findViewById(viewId);
        if(!TextUtils.isEmpty(text)){
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        }
    }

    //设置点击事件
    protected void setOnClickListener(int viewId, View.OnClickListener listener){
        findViewById(viewId).setOnClickListener(listener);
    }

    //设置视图可见性
    protected void setVisibility(int viewId, int visibility) {
        findViewById(viewId).setVisibility(visibility);
    }

    public <T extends View> T findViewById(int viewId){
        return (T) mNavigationView.findViewById(viewId);
    }

    private void createAndBindView() {
        //如果没有传viewId的话则需要自动去找他的根布局
        if ( mParams.mParent == null){
            //这样拿到的是自己定义的布局外面的那层布局
            ViewGroup activityRoot = (ViewGroup) ((Activity)(mParams.mContext)).getWindow().getDecorView();
            //获取了activity的根布局,然后用(ViewGroup) activityRoot.getChildAt(0)拿到了自己写的布局layout的第一个根布局
//            ViewGroup activityRoot = (ViewGroup) ((Activity)(mParams.mContext)).findViewById(android.R.id.content);

            mParams.mParent = (ViewGroup) activityRoot.getChildAt(0);
        }

        if (mParams.mParent == null)
            return;
        //创建view
        mNavigationView = LayoutInflater.from(mParams.mContext)
                .inflate(bindLayoutId(), mParams.mParent,false);
        //添加
        mParams.mParent.addView(mNavigationView, 0);

        //绑定
        applyView();
    }

    //Builder设计模式  AbsNavigationBar/Builder/参数Params
    public abstract static class Builder {
        AbsNavigationParams P;

        public Builder(Context context, ViewGroup parent) {
            P = new AbsNavigationParams(context, parent);
        }

        public abstract AbsNavigationBar builder();

        public static class AbsNavigationParams {

            public Context mContext;
            public ViewGroup mParent;

            public AbsNavigationParams(Context context, ViewGroup parent) {
                mContext = context;
                mParent = parent;
            }
        }
    }
}
