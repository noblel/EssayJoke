package com.noblel.framelibrary.bannner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
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
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //每隔多少秒切换
            setCurrentItem(getCurrentItem() + 1);
            //不断的循环执行
            startRoll();
        }
    };
    private List<View> mConvertViews;
    private BannerItemClickListener mListener;
    //内存优化
    private Activity mActivity;

    public BannerViewPager(Context context) {
        super(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        //改变ViewPager切换的速率
        //源码中mScroller.startScroll(sx, sy, dx, dy, duration);
        //反射设置持续时间执行startScroll方法
        mActivity = (Activity) context;
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            mScroller = new BannerScroller(context);
            field.setAccessible(true);
            field.set(this, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mConvertViews = new ArrayList<>();
    }

    public void setScrollDuration(int scrollDuration) {
        mScroller.setScrollerDuration(scrollDuration);
    }

    public void setAdapter(BannerAdapter adapter) {
        mAdapter = adapter;
        setAdapter(new BannerPagerAdapter());
        //管理Activity生命周期
        mActivity.getApplication().registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
    }

    /**
     * 实现自动轮播
     */
    public void startRoll() {
        //清除消息
        mHandler.removeMessages(SCROLL_MSG);
        //消息延迟时间
        mHandler.sendEmptyMessageDelayed(SCROLL_MSG, mCutDownTime);
    }

    /**
     * 销毁Handler停止发送
     */
    @Override
    protected void onDetachedFromWindow() {
        mHandler.removeMessages(SCROLL_MSG);
        mHandler = null;
        mActivity.getApplication().unregisterActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
        super.onDetachedFromWindow();
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
                        mListener.onClick(position % mAdapter.getCount());
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
        for (int i = 0; i < mConvertViews.size(); i++) {
            //获取没有在ViewPager中的复用视图
            if (mConvertViews.get(i).getParent() == null) {
                return mConvertViews.get(i);
            }
        }
        return null;
    }


    public void setListener(BannerItemClickListener listener) {
        mListener = listener;
    }

    public interface BannerItemClickListener {
        public void onClick(int position);
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
