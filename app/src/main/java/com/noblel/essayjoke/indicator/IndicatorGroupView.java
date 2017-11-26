package com.noblel.essayjoke.indicator;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * @author Noblel
 *         Indicator的容器,包含ItemView和底部跟踪的指示器
 */
public class IndicatorGroupView extends FrameLayout {
    //指示器条目的容器
    private LinearLayout mIndicatorGroup;
    private View mBottomTrackView;
    //一个Item宽度
    private int mItemWidth;
    private LayoutParams mParams;
    private int mInitLeftMargin;

    public IndicatorGroupView(@NonNull Context context) {
        this(context, null);
    }

    public IndicatorGroupView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorGroupView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mIndicatorGroup = new LinearLayout(context);
        addView(mIndicatorGroup);
    }

    public void addItemView(View itemView) {
        mIndicatorGroup.addView(itemView);
    }

    public View getItemAt(int position) {
        return mIndicatorGroup.getChildAt(position);
    }

    /**
     * 添加底部指示器
     */
    public void addBottomTrackView(View bottomTrackView, int itemWidth) {
        if (bottomTrackView == null) return;
        mBottomTrackView = bottomTrackView;
        mItemWidth = itemWidth;
        //添加底部跟踪的View
        addView(mBottomTrackView);
        //显示在底部,宽度是一个Item的宽度
        showInBottom();
        setWidth();
    }

    private void setWidth() {
        int trackWidth = mParams.width;
        //没有设置宽度
        if (mParams.width == ViewGroup.LayoutParams.MATCH_PARENT) {
            trackWidth = mItemWidth;
        }
        //设置宽度过大
        if (trackWidth > mItemWidth) {
            trackWidth = mItemWidth;
        }
        //最后确定宽度
        mParams.width = trackWidth;
        //确保在最中间
        mInitLeftMargin = (mItemWidth - trackWidth) / 2;
        mParams.leftMargin = mInitLeftMargin;
    }

    /**
     * 显示在底部
     */
    private void showInBottom() {
        mParams = (LayoutParams) mBottomTrackView.getLayoutParams();
        mParams.gravity = Gravity.BOTTOM;
    }

    /**
     * 滚动底部的指示器
     */
    public void scrollBottomTrack(int position, float positionOffset) {
        if (mBottomTrackView == null) {
            return;
        }
        //控制leftMargin
        int leftMargin = (int) ((position + positionOffset) * mItemWidth);
        mParams.leftMargin = leftMargin + mInitLeftMargin;
        mBottomTrackView.setLayoutParams(mParams);
    }

    public void scrollBottomTrack(int position) {
        if (mBottomTrackView == null) {
            return;
        }
        //最终要移动的位置
        int finalLeftMargin = position * mItemWidth + mInitLeftMargin;
        //当前位置
        int currentLeftMargin = mParams.leftMargin;
        //移动的距离
        int distance = finalLeftMargin - currentLeftMargin;

        //开启动画
        ValueAnimator animator = ObjectAnimator.ofFloat(currentLeftMargin, finalLeftMargin)
                .setDuration((long) (Math.abs(distance) * 0.4f));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mParams.leftMargin = (int) value + mInitLeftMargin;
                mBottomTrackView.setLayoutParams(mParams);
            }
        });
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }
}
