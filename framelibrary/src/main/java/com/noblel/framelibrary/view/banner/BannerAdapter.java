package com.noblel.framelibrary.view.banner;

import android.view.View;

/**
 * @author Noblel
 */
public abstract class BannerAdapter {

    /**
     * 根据位置获取ViewPager里面的子View
     */
    public abstract View getView(int position, View convertView);

    /**
     * 获取轮播的数量
     */
    public abstract int getCount();

    public String getBannerDesc(int position) {
        return "";
    }
}
