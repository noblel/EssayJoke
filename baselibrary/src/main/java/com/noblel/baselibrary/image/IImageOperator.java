package com.noblel.baselibrary.image;

import android.widget.ImageView;

import java.io.File;

/**
 * @author Noblel
 *         所有图片的操作
 */
public interface IImageOperator {

    /**
     * 初始化
     */
    void init();

    /**
     * 加载图片
     *
     * @param imageView
     * @param url
     */
    void loadImage(ImageView imageView, String url);

    void loadImage(ImageView imageView, String url, int placeResId, int errResId);

    void loadImage(ImageView imageView, File file);

    void loadImage(ImageView imageView, int resId);

    void loadImage(ImageView imageView, int targetX, int targetY, String url);
}
