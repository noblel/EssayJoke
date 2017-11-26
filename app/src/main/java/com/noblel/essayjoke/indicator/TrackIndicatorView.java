package com.noblel.essayjoke.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

import com.noblel.essayjoke.R;

import static android.support.v4.view.ViewPager.SCROLL_STATE_DRAGGING;
import static android.support.v4.view.ViewPager.SCROLL_STATE_IDLE;

/**
 * @author Noblel
 *         ViewPager指示器
 */
public class TrackIndicatorView extends HorizontalScrollView implements ViewPager.OnPageChangeListener {
    private IndicatorAdapter mAdapter;

    //指示器条目的容器
    private IndicatorGroupView mIndicatorGroup;
    //一屏显示的数量
    private int mTabVisibleNum = 0;
    //Item宽度
    private int mItemWidth;
    private ViewPager mViewPager;

    //点击或者切换时候改变状态-当前位置
    private int mCurrentPosition = 0;
    //解决点击抖动的问题
    private boolean mIsExecuteScroll = false;

    public TrackIndicatorView(Context context) {
        this(context, null);
    }

    public TrackIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrackIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mIndicatorGroup = new IndicatorGroupView(context);
        addView(mIndicatorGroup);
        //指定Item的宽度
        initAttribute(context, attrs);
    }

    //初始化自定义属性
    private void initAttribute(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TrackIndicatorView);
        mTabVisibleNum = array.getInteger(R.styleable.TrackIndicatorView_tabVisibleNum, mTabVisibleNum);
        //回收
        array.recycle();
    }

    //设置适配器
    public void setAdapter(IndicatorAdapter adapter) {
        if (adapter == null)
            throw new NullPointerException("adapter is null");
        mAdapter = adapter;
        //动态添加View
        int count = mAdapter.getCount();
        //循环添加ItemView
        for (int i = 0; i < count; i++) {
            View itemView = mAdapter.getView(i, mIndicatorGroup);
            mIndicatorGroup.addItemView(itemView);
            if (mViewPager != null) {
                //与ViewPager联动设置点击事件
                switchItemClick(itemView, i);
            }
        }

        //默认点亮第一个位置
        mAdapter.highLightIndicator(mIndicatorGroup.getItemAt(0));
    }

    private void switchItemClick(View itemView, final int position) {
        itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(position);
                smoothScrollIndicator(position);
                //移动下标
                mIndicatorGroup.scrollBottomTrack(position);
            }
        });
    }

    /**
     * 点击移动 带动画
     */
    private void smoothScrollIndicator(int position) {
        //当前总共位置
        float totalScroll = position * mItemWidth;
        //左边的偏移量
        int offsetScroll = (getWidth() - mItemWidth) / 2;

        final int finalScroll = (int) (totalScroll - offsetScroll);

        smoothScrollTo(finalScroll, 0);
    }

    public void setAdapter(IndicatorAdapter adapter, ViewPager viewPager) {
        if (viewPager == null) {
            throw new NullPointerException("ViewPager is null");
        }
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(this);
        setAdapter(adapter);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            //指定item宽度
            mItemWidth = getItemWidth();
            //循环指定Item宽度
            for (int i = 0; i < mAdapter.getCount(); i++) {
                mIndicatorGroup.getItemAt(i).getLayoutParams().width = mItemWidth;
            }
            //添加底部跟踪指示器
            mIndicatorGroup.addBottomTrackView(mAdapter.getBottomTrackView(), mItemWidth);
        }
    }

    /**
     * 获取Item的宽度
     */
    private int getItemWidth() {
        //如果指定了
        int parentWidth = getWidth();
        if (mTabVisibleNum != 0) {
            return parentWidth / mTabVisibleNum;
        }
        int itemWidth = 0;
        //获取最宽的Item宽度
        int maxItemWidth = 0;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            int currentItemWidth = mIndicatorGroup.getItemAt(i).getMeasuredWidth();
            maxItemWidth = Math.max(currentItemWidth, maxItemWidth);
        }

        itemWidth = maxItemWidth;

        int allWidth = mAdapter.getCount() * itemWidth;
        //所有Item宽度相加是否是一屏幕
        if (allWidth < parentWidth) {
            itemWidth = parentWidth / mAdapter.getCount();
        }
        return itemWidth;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mIsExecuteScroll) {
            scrollCurrentIndicator(position, positionOffset);
            mIndicatorGroup.scrollBottomTrack(position, positionOffset);
            //如果是点击事件就不执行onPageScrolled方法
        }
    }

    private void scrollCurrentIndicator(int position, float positionOffset) {
        //当前总共位置
        float totalScroll = (position + positionOffset) * mItemWidth;
        //左边的偏移量
        int offsetScroll = (getWidth() - mItemWidth) / 2;

        final int finalScroll = (int) (totalScroll - offsetScroll);

        scrollTo(finalScroll, 0);
    }

    @Override
    public void onPageSelected(int position) {
        //上一个位置重置
        mAdapter.restoreIndicator(mIndicatorGroup.getItemAt(mCurrentPosition));
        //将当前位置高亮
        mCurrentPosition = position;
        mAdapter.highLightIndicator(mIndicatorGroup.getItemAt(mCurrentPosition));
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == SCROLL_STATE_DRAGGING) {
            mIsExecuteScroll = true;
        }
        if (state == SCROLL_STATE_IDLE) {
            mIsExecuteScroll = false;
        }
    }
}
