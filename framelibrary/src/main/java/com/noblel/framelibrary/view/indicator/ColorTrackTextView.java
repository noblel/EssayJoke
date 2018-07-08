package com.noblel.framelibrary.view.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import com.noblel.framelibrary.R;


/**
 * 自定义变色字体，继承自TextView
 *
 * @author Noblel
 */

public class ColorTrackTextView extends android.support.v7.widget.AppCompatTextView {
    private Paint mOriginPaint, mChangePaint;
    private float mCurrentProgress = 0;

    //实现不同朝向
    private Direction mDirection = Direction.LEFT_TO_RIGHT;

    private Rect mRect = new Rect();
    private Rect mBounds = new Rect();
    private String text;


    public enum Direction {
        LEFT_TO_RIGHT, RIGHT_TO_LEFT
    }

    public ColorTrackTextView (Context context) {
        this(context, null);
    }

    public ColorTrackTextView (Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTrackTextView (Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context, attrs);
    }

    private void initPaint (Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackTextView);
        int originColor = array.getColor(R.styleable.ColorTrackTextView_originColor, Color.BLACK);
        int changeColor = array.getColor(R.styleable.ColorTrackTextView_changeColor, Color.RED);

        mOriginPaint = getPaintByColor(originColor);
        mChangePaint = getPaintByColor(changeColor);

        array.recycle();

    }

    private Paint getPaintByColor (int color) {
        Paint paint = new Paint();
        //设置颜色
        paint.setColor(color);
        //设置抗锯齿
        paint.setAntiAlias(true);
        //设置防抖动
        paint.setDither(true);
        //设置字体大小
        paint.setTextSize(getTextSize());
        return paint;
    }

    @Override
    protected void onDraw (Canvas canvas) {
        //canvas.clipRect() 裁剪区域
        int middle = (int) (mCurrentProgress * getWidth());
        text = getText().toString();
        if (mDirection == Direction.LEFT_TO_RIGHT) {//左边是红色右边是黑色
            //绘制变色
            drawText(canvas, mChangePaint, 0, middle);
            drawText(canvas, mOriginPaint, middle, getWidth());
        } else {
            drawText(canvas, mChangePaint, getWidth() - middle, getWidth());
            drawText(canvas, mOriginPaint, 0, getWidth() - middle);
        }
    }

    /**
     * 绘制Text
     *
     * @param canvas
     * @param paint
     * @param start
     * @param end
     */
    private void drawText (Canvas canvas, Paint paint, int start, int end) {
        canvas.save();
        //绘制不变色
        mRect.set(start, 0, end, getHeight());
        canvas.clipRect(mRect);

        paint.getTextBounds(text, 0, text.length(), mBounds);
        //获取字体的宽度
        int x = getWidth() / 2 - mBounds.width() / 2;
        //基线
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(text, x, baseLine, paint);
        canvas.restore();
    }

    /**
     * 设置方向
     * @param direction
     */
    public void setDirection (Direction direction) {
        this.mDirection = direction;
    }

    /**
     * 设置进度
     * @param progress
     */
    public void setCurrentProgress (float progress) {
        this.mCurrentProgress = progress;
        invalidate();
    }

    /**
     * 设置变化后的颜色
     * @param changeColor
     */
    public void setChangeColor (int changeColor) {
        this.mChangePaint.setColor(changeColor);
    }

    /**
     * 设置字体原始颜色
     * @param originColor
     */
    public void setOriginColor (int originColor) {
        this.mOriginPaint.setColor(originColor);
    }
}
