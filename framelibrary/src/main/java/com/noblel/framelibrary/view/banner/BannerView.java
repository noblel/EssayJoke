package com.noblel.framelibrary.view.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noblel.framelibrary.R;

/**
 * @author Noblel
 */
public class BannerView extends FrameLayout {
    private static final String TAG = BannerView.class.getSimpleName();

    private Context mContext;
    private BannerViewPager mBannerVp;
    //轮播的描述
    private TextView mBannerDescTv;
    //点容器
    private LinearLayout mDotContainer;
    private BannerAdapter mAdapter;

    private Drawable mIndicatorFocusDrawable;
    private Drawable mIndicatorDefaultDrawable;
    //当前位置
    private int mCurrentPosition = 0;
    //点的位置,默认在左边
    private int mDotGravity = Gravity.RIGHT;
    //点的大小
    private int mDotSize = 8;
    //点的间距
    private int mDotDistance = 8;
    private int mBottomColor = Color.TRANSPARENT;
    //宽高比例
    private float mWidthProportion, mHeightProportion;
    //底部容器
    private View mBannerBv;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        inflate(context,R.layout.ui_banner_layout, this);
        initView();
        initAttribute(attrs);
    }

    /**
     * 初始化自定义属性
     */
    private void initAttribute(AttributeSet attrs) {
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.BannerView);
        //获取点的位置
        mDotGravity = array.getInt(R.styleable.BannerView_dotGravity, mDotGravity);
        mIndicatorDefaultDrawable = array.getDrawable(R.styleable.BannerView_dotIndicatorDefault);
        if (mIndicatorDefaultDrawable == null) {
            //没有配置则设置默认值
            mIndicatorDefaultDrawable = new ColorDrawable(Color.WHITE);
        }
        mIndicatorFocusDrawable = array.getDrawable(R.styleable.BannerView_dotIndicatorFocus);
        if (mIndicatorFocusDrawable == null) {
            mIndicatorFocusDrawable = new ColorDrawable(Color.RED);
        }
        mDotSize = (int) array.getDimension(R.styleable.BannerView_dotSize, dip2px(mDotSize));
        mDotDistance = (int) array.getDimension(R.styleable.BannerView_dotDistance,
                dip2px(mDotDistance));
        mBottomColor = array.getColor(R.styleable.BannerView_dotBottomColor, mBottomColor);
        mBannerBv.setBackgroundColor(mBottomColor);
        mWidthProportion = array.getFloat(R.styleable.BannerView_withProportion, mWidthProportion);
        mHeightProportion = array.getFloat(R.styleable.BannerView_heightProportion, mHeightProportion);
        array.recycle();
    }

    private void initView() {
        mBannerVp = (BannerViewPager) findViewById(R.id.banner_vp);
        mBannerDescTv = (TextView) findViewById(R.id.banner_desc_tv);
        mDotContainer = (LinearLayout) findViewById(R.id.dot_container);
        mBannerBv = findViewById(R.id.banner_bottom_view);
    }

    public void setAdapter(BannerAdapter adapter) {
        mAdapter = adapter;
        Log.i(TAG, "mAdapter --> " + mAdapter.getCount());
        mBannerVp.setAdapter(adapter);
        //初始化点指示器
        initDotIndicator();
        pageSelected(0);
        mBannerVp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //监听当前选中的位置
                pageSelected(position);
            }
        });
        if (mHeightProportion == 0 || mWidthProportion == 0) {
            return;
        }

        post(new Runnable() {
            @Override
            public void run() {
                // 动态指定宽高  计算高度
                int width = getMeasuredWidth();
                // 计算高度
                int height = (int) (width * mHeightProportion / mWidthProportion);
                // 指定宽高
                getLayoutParams().height = height;
                mBannerVp.getLayoutParams().height = height;
                //不添加 这句代码的话Params的高要下一个切换页面的时候才会变   时间太长
                mBannerVp.setCurrentItem(mCurrentPosition + 1);
            }
        });
    }

    /**
     * 初始化广告的描述和指示器的滚动
     */
    private void pageSelected(int position) {
        String bannerDescription = mAdapter.getBannerDesc(position % mAdapter.getCount());
        mBannerDescTv.setText(bannerDescription);
        //之前位置恢复
        DotIndicatorView preIndicator = (DotIndicatorView) mDotContainer.getChildAt(mCurrentPosition);
        preIndicator.setDrawable(mIndicatorDefaultDrawable);
        //把当前位置的点点亮
        mCurrentPosition = position % mAdapter.getCount();
        DotIndicatorView currentIndicator = (DotIndicatorView) mDotContainer.getChildAt(mCurrentPosition);
        currentIndicator.setDrawable(mIndicatorFocusDrawable);
    }

    private void initDotIndicator() {
        //获取广告的数量
        int count = mAdapter.getCount();
        //让点的位置在右边
        mDotContainer.setGravity(getDotGravity(mDotGravity));
        for (int i = 0; i < count; i++) {
            DotIndicatorView indicatorView = new DotIndicatorView(mContext);
            //设置大小
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mDotSize, mDotSize);
            //左右间距
            params.leftMargin = mDotDistance;
            indicatorView.setLayoutParams(params);
            if (i == 0) {
                //第一个位置默认选中
                indicatorView.setDrawable(mIndicatorFocusDrawable);
            } else {
                indicatorView.setDrawable(mIndicatorDefaultDrawable);
            }
            mDotContainer.addView(indicatorView);
        }
        Log.i(TAG, "initDotIndicator: mDotContainer.size()-->" + mDotContainer.getChildCount());
    }

    /**
     * 获取点的位置
     */
    private int getDotGravity(int gravity) {
        switch (gravity) {
            case 0:
                return Gravity.CENTER;
            case 1:
                return Gravity.END;
            case -1:
                return Gravity.START;
            default:
                return mDotGravity;
        }
    }


    /**
     * 开启轮播
     */
    public void startRoll() {
        mBannerVp.startRoll();
    }

    /**
     * 停止轮播
     */
    public void stopRoll() {
        mBannerVp.stopRoll();
    }

    /**
     * 设置页面切换时间
     */
    public void setSwitchTime(int time) {
        mBannerVp.setSwitchTime(time);
    }

    /**
     * 设置轮播动画的持续时间
     */
    public void setDuration(int time) {
        mBannerVp.setScrollDuration(time);
    }

    /**
     * 设置viewpager中的item的点击事件
     */
    public void setItemClickListener(BannerItemClickListener listener) {
        mBannerVp.setListener(listener);
    }


    /**
     * dip转px
     */
    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dip, getResources().getDisplayMetrics());
    }
}
