package com.noblel.essayjoke.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.noblel.baselibrary.adapter.BaseViewHolder;
import com.noblel.essayjoke.R;

/**
 * @author Noblel
 */
public class GlideImageLoader extends BaseViewHolder.HolderImageLoader {
    public GlideImageLoader(String path) {
        super(path);
    }

    @Override
    public void loadImage(Context context, ImageView imageView, String path) {
        Glide.with(context).load(path).placeholder(R.drawable.ic_discovery_default_channel)
                .centerCrop().into(imageView);
    }


}
