package com.noblel.framelibrary.view.banner;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Noblel
 */
public class BannerViewPager extends ViewPager {
    private BannerAdapter mAdapter;
    private final int SCROLL_MSG = 0x0011;
    //默认切换间隔时间
    private int mCutDownTime = 3500;
    //自定义改变的速率
    private BannerScroller mScroller;
    private List<View> mConvertViews;
    private BannerItemClickListener mListener;
    //内存优化
    private Activity mActivity;
    private LoopHandler mHandler;

    public void setSwitchTime(int time) {
        mCutDownTime = time;
    }

    private class LoopHandler extends Handler {

        public LoopHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SCROLL_MSG:
                    //每隔多少秒切换
                    int index = getCurrentItem() + 1;
                    setCurrentItem(index, true);
                    //不断的循环执行
                    startRoll();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        //改变ViewPager切换的速率
        //源码中mScroller.startScroll(sx, sy, dx, dy, duration);
        //反射设置持续时间执行startScroll方法
        mHandler = new LoopHandler(Looper.getMainLooper());
        mScroller = new BannerScroller(context);
        mActivity = (Activity) context;
        mConvertViews = new ArrayList<>();
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            field.set(this, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setScrollDuration(int scrollDuration) {
        mScroller.setScrollerDuration(scrollDuration);
    }

    public void setAdapter(BannerAdapter adapter) {
        mAdapter = adapter;
        super.setAdapter(new BannerPagerAdapter());
    }

    /**
     * 实现自动轮播
     */
    public void startRoll() {
        if (mAdapter == null) {
            return;
        }
        if (mAdapter.getCount() != 1 && mHandler != null) {
            //清除消息
            mHandler.removeMessages(SCROLL_MSG);
            Message msg = Message.obtain();
            msg.what = SCROLL_MSG;
            //消息延迟时间
            mHandler.sendMessageDelayed(msg, mCutDownTime);
        }
    }

    /**
     * 停止轮播
     */
    public void stopRoll() {
        if (mHandler != null) {
            mHandler.removeMessages(SCROLL_MSG);
            mHandler = null;
        }
    }

    /**
     * 销毁Handler停止发送
     */
    @Override
    protected void onDetachedFromWindow() {
        if (mHandler != null) {
            stopRoll();
            mActivity.getApplication().unregisterActivityLifecycleCallbacks(mActivityLifecycleCallbacks);

        }
        super.onDetachedFromWindow();
    }


    @Override
    protected void onAttachedToWindow() {
        if (mAdapter != null) {
            mHandler = new LoopHandler(Looper.getMainLooper());
            startRoll();
            //管理Activity生命周期
            mActivity.getApplication().registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
        }
        super.onAttachedToWindow();
    }

    private class BannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            //为了实现无线循环
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        /**
         * 创建ViewPagerItem回调的方法
         */
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            //采用Adapter设计模式
            View bannerItemView = mAdapter.getView(position % mAdapter.getCount(),
                    getConvertView());
            //添加到ViewPager里面
            container.addView(bannerItemView);
            bannerItemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.click(position % mAdapter.getCount());
                    }
                }
            });
            return bannerItemView;
        }

        /**
         * 销毁ViewPagerItem回调的方法
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            mConvertViews.add((View) object);
        }
    }

    /**
     * 获取复用界面
     */
    private View getConvertView() {
        for (View view : mConvertViews){
            if (view.getParent() == null){
                return view;
            }
        }
        return null;
    }


    public void setListener(BannerItemClickListener listener) {
        mListener = listener;
    }

    //管理Activity的生命周期
    private Application.ActivityLifecycleCallbacks mActivityLifecycleCallbacks =
            new DefaultActivityLifecycleCallBacks() {
                @Override
                public void onActivityResumed(Activity activity) {
                    //判断是不是监听当前Activity的声明周期
                    if (activity == mActivity) {
                        //开启轮播
                        mHandler.sendEmptyMessageDelayed(mCutDownTime, SCROLL_MSG);
                    }
                }

                @Override
                public void onActivityPaused(Activity activity) {
                    if (activity == mActivity) {
                        //停止轮播
                        mHandler.removeMessages(SCROLL_MSG);
                    }
                }
            };
}
