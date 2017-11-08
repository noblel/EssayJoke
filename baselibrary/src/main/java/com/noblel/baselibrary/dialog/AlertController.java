package com.noblel.baselibrary.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


/**
 * @author Noblel
 */
class AlertController {

    private AlertDialog mDialog;
    private Window mWindow;

    private DialogViewHelper mViewHelper;


    public AlertController(AlertDialog dialog, Window window) {
        mDialog = dialog;
        mWindow = window;
    }

    public AlertDialog getDialog() {
        return mDialog;
    }

    public void setViewHelper(DialogViewHelper viewHelper) {
        this.mViewHelper = viewHelper;
    }

    //获取Dialog的Window
    public Window getWindow() {
        return mWindow;
    }

    //设置文本
    public void setText(int viewId, CharSequence text) {
        //为了减少每次点击时都findViewById的次数，因此弄一个缓存来保存，如果查找的在缓存中则不需要继续查找下去
        mViewHelper.setText(viewId, text);
    }

    public <T extends View> T getViews(int viewId) {
        return mViewHelper.getViews(viewId);
    }

    //设置点击事件
    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        mViewHelper.setOnClickListener(viewId, listener);
    }


    public static class AlertParams {
        public Context mContext;
        public int mThemeResId;
        //点击空白是否能够取消
        public boolean mCancelable = true;
        //dialog Cancel监听
        public DialogInterface.OnCancelListener mOnCancelListener;
        //dialog Dismiss监听
        public DialogInterface.OnDismissListener mOnDismissListener;
        //dialog Key监听
        public DialogInterface.OnKeyListener mOnKeyListener;

        public View mView;
        public int mViewLayoutResId;
        //存放字体的修改(这个存储方式比HashMap更加高效)
        public SparseArray<CharSequence> mTextArray = new SparseArray<>();
        public SparseArray<View.OnClickListener> mListenerArray = new SparseArray<>();

        //宽度
        public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        //高度
        public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        //动画
        public int mAnimation = 0;
        //位置
        public int mGravity = Gravity.CENTER;

        public AlertParams(Context context, int themeResId) {
            mContext = context;
            mThemeResId = themeResId;
        }

        /**
         * 绑定和设置参数
         *
         * @param alert
         */
        public void apply(AlertController alert) {
            //设置参数
            DialogViewHelper viewHelper = null;
            if (mViewLayoutResId != 0) {
                viewHelper = new DialogViewHelper(mContext, mViewLayoutResId);
            }
            if (mView != null) {
                viewHelper = new DialogViewHelper();
            }
            if (viewHelper == null) {
                throw new IllegalArgumentException("请设置布局，调用setContentView方法");
            }
            //设置布局
            alert.getDialog().setContentView(viewHelper.getContentView());
            //设置controller的viewHelper类
            alert.setViewHelper(viewHelper);

            //设置文本
            int textArraySize = mTextArray.size();
            for (int i = 0; i < textArraySize; i++) {
                alert.setText(mTextArray.keyAt(i), mTextArray.valueAt(i));
            }
            //设置点击
            int clickArraySize = mListenerArray.size();
            for (int i = 0; i < clickArraySize; i++) {
                alert.setOnClickListener(mListenerArray.keyAt(i), mListenerArray.valueAt(i));
            }
            //配置自定义的效果 全屏 从底部弹出 默认动画
            Window window = alert.getWindow();

            //设置位置
            window.setGravity(mGravity);
            if (mAnimation != 0) {
                window.setWindowAnimations(mAnimation);
            }

            //设置动画
            if (mAnimation != 0) {
                window.setWindowAnimations(mAnimation);
            }

            //设置宽高
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = mWidth;
            params.height = mHeight;
            window.setAttributes(params);
        }


    }

}
