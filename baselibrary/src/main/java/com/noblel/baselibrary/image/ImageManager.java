package com.noblel.baselibrary.image;

import android.content.Context;
import android.widget.ImageView;

/**
 * @author Noblel
 *         图片管理器,管理图片的加载，压缩，切割等
 */
public class ImageManager {

    private Context sContext;

    /**
     * 图片操作管理者
     */
    private IImageOperator mImageImpl;

    private static class SingleHolder {
        static final ImageManager sInstance = new ImageManager();
    }

    /**
     * 构造方法私有化
     */
    private ImageManager() {

    }

    /**
     * 内部类方式单例
     *
     * @return
     */
    public static ImageManager getInstance() {
        return SingleHolder.sInstance;
    }

    /**
     * 初始化方法
     *
     * @param context
     * @param oper
     */
    public void init(Context context, IImageOperator oper) {
        sContext = context.getApplicationContext();
        mImageImpl = oper;
        mImageImpl.init();
    }

    public void setImageOper(IImageOperator oper) {
        mImageImpl = oper;
    }

    /**
     * 加载图片
     *
     * @param url
     * @param imageView
     */
    public void loadImage(ImageView imageView, String url) {
        mImageImpl.loadImage(imageView, url);
    }

    /**
     * 加载图片
     *
     * @param url
     * @param imageView
     * @param placeId   占位符
     */
    public void loadImage(ImageView imageView, String url, int placeId) {
        mImageImpl.loadImage(imageView, url, placeId, 0);
    }

    /**
     * 加载图片
     *
     * @param imageView
     * @param url
     * @param placeResId
     * @param errResId
     */
    public void loadImage(ImageView imageView, String url, int placeResId, int errResId) {
        mImageImpl.loadImage(imageView, url, placeResId, errResId);
    }

    /**
     * 加载图片
     *
     * @param imageView
     * @param targetX
     * @param targetY
     * @param url
     */
    public void loadImage(ImageView imageView, int targetX, int targetY, String url) {
        mImageImpl.loadImage(imageView, targetX, targetY, url);
    }

}
