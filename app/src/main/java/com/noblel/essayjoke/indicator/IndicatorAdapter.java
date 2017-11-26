package com.noblel.essayjoke.indicator;

import android.view.View;
import android.view.ViewGroup;

/**
 * @author Noblel
 */
public abstract class IndicatorAdapter<T extends View> {
    //获取总共显示的显示条目
    public abstract int getCount();
    //根据当前位置获取View
    public abstract T getView(int position, ViewGroup parent);

    //高亮当前位置
    public void highLightIndicator(T view) {

    }

    public void restoreIndicator(T view) {

    }

    public View getBottomTrackView() {
        return null;
    }
}
