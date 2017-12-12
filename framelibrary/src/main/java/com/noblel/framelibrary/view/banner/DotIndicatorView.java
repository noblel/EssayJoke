package com.noblel.framelibrary.view.banner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Noblel
 *         点的指示器
 */
public class DotIndicatorView extends View {
    private Drawable mDrawable;

    public DotIndicatorView(Context context) {
        this(context, null);
    }

    public DotIndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mDrawable != null) {
            //画圆
            Bitmap bitmap = drawableToBitmap(mDrawable);
            //把bitmap变成圆形的
            Bitmap circleBitmap = getCircleBitmap(bitmap);
            canvas.drawBitmap(circleBitmap,0,0, null);
        }
    }

    /**
     * 获取圆形Bitmap
     */
    private Bitmap getCircleBitmap(Bitmap bitmap) {
        //创建一个Bitmap
        Bitmap circleBitmap = Bitmap.createBitmap(getMeasuredWidth(),getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(circleBitmap);
        //在画布上画圆
        Paint paint = new Paint();
        //设置抗锯齿
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        //设置防抖动
        paint.setDither(true);
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2,
                getMeasuredWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap,0,0,paint);

        //内存优化回收Bitmap
        bitmap.recycle();
        bitmap = null;

        return circleBitmap;
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        //如果是BitmapDrawable类型
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        //其他类型：ColorDrawable....
        Bitmap outBitmap = Bitmap.createBitmap(getMeasuredWidth(),getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(outBitmap);
        drawable.setBounds(0,0,getMeasuredWidth(),getMeasuredHeight());
        drawable.draw(canvas);
        return outBitmap;
    }

    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
        //重新刷新页面,调用onDraw()方法
        invalidate();
    }
}
