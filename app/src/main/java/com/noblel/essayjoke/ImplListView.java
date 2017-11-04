package com.noblel.essayjoke;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @author Noblel
 */

public class ImplListView extends ListView {
    public ImplListView(Context context) {
        super(context);
    }

    public ImplListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImplListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //三十二位 高两位是模式,低30位是值
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
