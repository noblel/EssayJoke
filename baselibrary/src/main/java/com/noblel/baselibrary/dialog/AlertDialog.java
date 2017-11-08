package com.noblel.baselibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.noblel.baselibrary.R;

/**
 * @author Noblel
 *         万能Dialog
 */
public class AlertDialog extends Dialog {

    private AlertController mAlert;

    public AlertDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mAlert = new AlertController(this, getWindow());
    }

    //设置文本
    public void setText(int viewId, CharSequence text) {
        //为了减少每次点击时都findViewById的次数，因此弄一个缓存来保存，如果查找的在缓存中则不需要继续查找下去
        mAlert.setText(viewId, text);
    }

    public <T extends View> T getView(int viewId) {
        return mAlert.getViews(viewId);
    }

    //设置点击事件
    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        mAlert.setOnClickListener(viewId, listener);
    }

    public static class Builder {
        private final AlertController.AlertParams P;

        public Builder(Context context) {
            this(context, R.style.dialog);
        }

        public Builder(Context context, int themeResId) {
            P = new AlertController.AlertParams(context, themeResId);
        }


        public AlertDialog create() {
            final AlertDialog dialog = new AlertDialog(P.mContext, P.mThemeResId);
            P.apply(dialog.mAlert);
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }
            return dialog;
        }


        public AlertDialog show() {
            final AlertDialog dialog = create();
            dialog.show();
            return dialog;
        }

        public Builder fullWith() {
            P.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        /**
         * 从底部弹出
         * @param isAnimation 是否有动画
         * @return
         */
        public Builder fromBottom(boolean isAnimation) {
            if (isAnimation)
                P.mAnimation = R.style.dialog_from_bottom_anim;
            P.mGravity = Gravity.BOTTOM;
            return this;
        }

        /**
         * 设置对话框宽高
         * @param width
         * @param height
         * @return
         */
        public Builder setWidthAndHeight(int width, int height) {
            P.mWidth = width;
            P.mHeight = height;
            return this;
        }

        public Builder addDefaultAnimation() {
            P.mAnimation = R.style.dialog_scale_anim;
            return this;
        }

        public Builder setAnimations(@IdRes int styleAnimation) {
            P.mWidth = styleAnimation;
            return this;
        }


        public Builder setContentView(View view) {
            P.mView = view;
            P.mViewLayoutResId = 0;
            return this;
        }

        //设置布局Id
        public Builder setContentView(int layoutId) {
            P.mView = null;
            P.mViewLayoutResId = layoutId;
            return this;
        }

        //设置文本
        public Builder setText(int viewId, CharSequence text) {
            P.mTextArray.put(viewId,text);
            return this;
        }

        //设置点击事件
        public Builder setOnClickListener(int viewId, View.OnClickListener listener) {
            P.mListenerArray.put(viewId,listener);
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            P.mCancelable = cancelable;
            return this;
        }

        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            P.mOnCancelListener = onCancelListener;
            return this;
        }


        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            P.mOnDismissListener = onDismissListener;
            return this;
        }

        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            P.mOnKeyListener = onKeyListener;
            return this;
        }
    }
}
