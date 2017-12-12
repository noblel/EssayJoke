package com.noblel.baselibrary.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Noblel
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    /** 缓存已找到的View */
    protected SparseArray<View> mViews;

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public BaseViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    public BaseViewHolder setText(int viewId, CharSequence text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public BaseViewHolder setText(int viewId, int resId) {
        TextView tv = getView(viewId);
        tv.setText(resId);
        return this;
    }

    public BaseViewHolder setTextColor(int viewId, int color){
        TextView textView = getView(viewId);
        textView.setTextColor(color);
        return this;
    }

    public BaseViewHolder setTextSize(int viewId, int size){
        TextView textView = getView(viewId);
        textView.setTextSize(size);
        return this;
    }

    public BaseViewHolder setVisibility(int viewId, int visibility) {
        getView(viewId).setVisibility(visibility);
        return this;
    }

    public BaseViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView imageView = getView(viewId);
        imageView.setImageBitmap(bitmap);
        return this;
    }
    public BaseViewHolder setImageResource(int viewId, int resourceId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resourceId);
        return this;
    }

    public void setOnClickListener(int viewId,View.OnClickListener listener) {
        getView(viewId).setOnClickListener(listener);
    }

    public void setOnLongClickListener(int viewId,View.OnLongClickListener listener) {
        getView(viewId).setOnLongClickListener(listener);
    }

    public BaseViewHolder setImagePath(int viewId, HolderImageLoader imageLoader) {
        ImageView imageView = getView(viewId);
        imageLoader.loadImage(imageView.getContext(),imageView,imageLoader.getPath());
        return this;
    }

    public abstract static class HolderImageLoader {
        private String mPath;

        public HolderImageLoader (String path) {
            mPath = path;
        }

        public abstract void loadImage(Context context, ImageView imageView, String path);

        public String getPath() {
            return mPath;
        }
    }

}
