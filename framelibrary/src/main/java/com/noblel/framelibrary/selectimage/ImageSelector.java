package com.noblel.framelibrary.selectimage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.noblel.framelibrary.selectimage.bean.ImageEntity;

import java.util.ArrayList;

/**
 * @author Noblel
 */
public class ImageSelector {

    public static final String TAG = ImageSelector.class.getSimpleName();
    //多选
    private static final int MODE_MULTI = 0x0011;
    //单选
    private static final int MODE_SINGLE = 0x0012;
    //是否显示相机的EXTRA_KEY
    private static final String EXTRA_SHOW_CAMERA = "EXTRA_SHOW_CAMERA";
    //总共可以显示多少张图片的EXTRA_KEY
    private static final String EXTRA_SELECT_COUNT = "EXTRA_SELECT_COUNT";
    //选择模式的EXTRA_KEY
    private static final String EXTRA_SELECT_MODE = "EXTRA_SELECT_MODE";
    //原始的图片路径的EXTRA_KEY
    private static final String EXTRA_DEFAULT_SELECT_LIST = "EXTRA_DEFAULT_SELECT_LIST";
    //返回选择图片列表的EXTRA_KEY
    public static final String EXTRA_RESULT = "EXTRA_RESULT";
    //参数
    private static final String KEY_ARGS = "KEY_ARGS";
    //单选或者多选 int类型的type
    private int mMode = MODE_MULTI;
    //图片张数
    private int mMaxCount = 8;
    //是否显示拍照按钮
    private boolean mShowCamera = true;
    //已经选择好的图片集合
    private ArrayList<ImageEntity> mOrigin;

    private ImageSelector() {

    }

    public static ImageSelector create() {
        return new ImageSelector();
    }

    /**
     * 单选模式
     */
    public ImageSelector single() {
        mMode = MODE_SINGLE;
        return this;
    }

    /**
     * 多选模式
     */
    public ImageSelector multi() {
        mMode = MODE_MULTI;
        return this;
    }

    /**
     * 最大支持图片数
     */
    public ImageSelector maxCount(int maxCount) {
        mMaxCount = maxCount;
        return this;
    }

    public ImageSelector showCamera(boolean showCamera) {
        mShowCamera = showCamera;
        return this;
    }

    /**
     * 原来选择好的图片
     */
    public ImageSelector origin(ArrayList<ImageEntity> origin) {
        mOrigin = origin;
        return this;
    }

    public void start(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, ImageSelectActivity.class);
        addParamsByIntent(intent);
        activity.startActivityForResult(intent, requestCode);
    }

    public void start(Fragment fragment, int requestCode) {
        Intent intent = new Intent(fragment.getContext(), ImageSelectActivity.class);
        addParamsByIntent(intent);
        fragment.startActivityForResult(intent, requestCode);
    }

    private void addParamsByIntent(Intent intent) {
        Bundle bundle = new Bundle();

        bundle.putInt(EXTRA_SELECT_COUNT, mMaxCount);
        bundle.putBoolean(EXTRA_SHOW_CAMERA, mShowCamera);

        if (mOrigin != null && mMode == MODE_MULTI) {
            bundle.putParcelableArrayList(EXTRA_DEFAULT_SELECT_LIST, mOrigin);
        }
        bundle.putInt(EXTRA_DEFAULT_SELECT_LIST, mMode);
        intent.putExtra(ImageSelector.KEY_ARGS, bundle);
    }
}
