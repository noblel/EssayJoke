package com.noblel.baselibrary.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * @author Noblel
 * Dialog View辅助处理类
 */
class DialogViewHelper {
    private View mContentView = null;
    //软引用,防止内存泄漏
    private SparseArray<WeakReference<View>> mViews;

    public DialogViewHelper() {
        mViews = new SparseArray<>();
    }

    public DialogViewHelper(Context context, int viewLayoutResId) {
        this();
        mContentView = LayoutInflater.from(context).inflate(viewLayoutResId, null);
    }

    //设置布局
    public void setContentView(View view) {
        this.mContentView = view;
    }

    //设置文本
    public void setText(int viewId, CharSequence text) {
        //为了减少每次点击时都findViewById的次数，因此弄一个缓存来保存，如果查找的在缓存中则不需要继续查找下去
        TextView tv = getViews(viewId);
        if (tv != null){
            tv.setText(text);
        }
    }

    public  <T extends View> T getViews(int viewId) {
        WeakReference<View> vRef = mViews.get(viewId);
        View v = null;
        if (vRef != null){
            v = vRef.get();
        }
        if (v == null){
            v = mContentView.findViewById(viewId);
            if (v != null)
                mViews.put(viewId, new WeakReference<>(v));
        }
        return (T)v;
    }

    //设置点击事件
    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getViews(viewId);
        if(view != null){
            view.setOnClickListener(listener);
        }
    }

    //获取contentView
    public View getContentView() {
        return mContentView;
    }
}
