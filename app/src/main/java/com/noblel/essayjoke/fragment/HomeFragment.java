package com.noblel.essayjoke.fragment;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.noblel.baselibrary.base.BaseFragment;
import com.noblel.baselibrary.ioc.ViewById;
import com.noblel.essayjoke.R;
import com.noblel.framelibrary.view.indicator.ColorTrackTextView;
import com.noblel.framelibrary.view.indicator.IndicatorAdapter;
import com.noblel.framelibrary.view.indicator.TrackIndicatorView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Noblel
 */
public class HomeFragment extends BaseFragment {
    private String[] mItems = {"直播", "推荐", "视频", "图片", "段子", "精华", "同城", "游戏"};

    @ViewById(R.id.indicator_view)
    private TrackIndicatorView mTrackIndicatorView;

    private List<ColorTrackTextView> mIndicators;

    @ViewById(R.id.view_pager)
    private ViewPager mViewPager;

    @Override
    protected void initData() {
        mIndicators = new ArrayList<>();
        initIndicator();
        initViewPager();
    }

    private void initViewPager() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return ItemFragment.newInstance(mItems[position]);
            }

            @Override
            public int getCount() {
                return mItems.length;
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset > 0) {
                    //获取左边
                    ColorTrackTextView left = mIndicators.get(position);
                    left.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
                    left.setCurrentProgress(1 - positionOffset);

                    //获取右边
                    ColorTrackTextView right = mIndicators.get(position + 1);
                    right.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT);
                    right.setCurrentProgress(positionOffset);
                }
            }
        });
    }

    private void initIndicator() {
        mTrackIndicatorView.setAdapter(new IndicatorAdapter<ColorTrackTextView>() {

            @Override
            public int getCount() {
                return mItems.length;
            }

            @Override
            public ColorTrackTextView getView(int position, ViewGroup parent) {
                ColorTrackTextView colorTrackTextView = new ColorTrackTextView(mContext);
                //设置颜色
                colorTrackTextView.setTextSize(20);
                colorTrackTextView.setChangeColor(Color.RED);
                colorTrackTextView.setText(mItems[position]);
                //加入集合
                mIndicators.add(colorTrackTextView);
                return colorTrackTextView;
            }

            @Override
            public void highLightIndicator(ColorTrackTextView view) {
                view.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
                view.setCurrentProgress(1);
            }

            @Override
            public void restoreIndicator(ColorTrackTextView view) {
                view.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
                view.setCurrentProgress(0);
            }
        }, mViewPager);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }
}
