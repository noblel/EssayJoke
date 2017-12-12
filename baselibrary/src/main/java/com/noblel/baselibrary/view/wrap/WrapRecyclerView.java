package com.noblel.baselibrary.view.wrap;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.noblel.baselibrary.adapter.BaseRecyclerAdapter;
import com.noblel.baselibrary.adapter.BaseViewHolder;

/**
 * @author Noblel
 */
public class WrapRecyclerView extends RecyclerView {
    //包裹一层的头部底部的Adapter
    private WrapRecyclerAdapter mWrapRecyclerAdapter;
    //列表数据的Adapter
    private BaseRecyclerAdapter mAdapter;
    private AdapterDataObserver mDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            if (mWrapRecyclerAdapter != mAdapter) {
                mWrapRecyclerAdapter.notifyDataSetChanged();
            }
            dataChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            if (mWrapRecyclerAdapter != mAdapter)
                mWrapRecyclerAdapter.notifyItemChanged(positionStart);
            dataChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            if (mWrapRecyclerAdapter != mAdapter)
                mWrapRecyclerAdapter.notifyItemChanged(positionStart, payload);
            dataChanged();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            if (mWrapRecyclerAdapter != mAdapter)
                mWrapRecyclerAdapter.notifyItemInserted(positionStart);
            dataChanged();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            if (mWrapRecyclerAdapter != mAdapter)
                mWrapRecyclerAdapter.notifyItemRemoved(positionStart);
            dataChanged();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            if (mWrapRecyclerAdapter != mAdapter)
                mWrapRecyclerAdapter.notifyItemMoved(fromPosition, toPosition);
            dataChanged();
        }
    };
    //增加一些通用功能
    //空列表应该显示空View
    //正在加载数据页面
    private View mEmptyView, mLoadingView;

    public WrapRecyclerView(Context context) {
        this(context, null);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        //为了防止多次设置Adapter
        if (mAdapter != null) {
            mAdapter.unregisterAdapterDataObserver(mDataObserver);
            mAdapter = null;
        }
        mAdapter = (BaseRecyclerAdapter) adapter;

        if (adapter instanceof WrapRecyclerAdapter) {
            mWrapRecyclerAdapter = (WrapRecyclerAdapter) adapter;
        } else {
            mWrapRecyclerAdapter = new WrapRecyclerAdapter(mAdapter) {
                @Override
                public void convert(BaseViewHolder holder, Object item, int position) {

                }
            };
        }
        super.setAdapter(adapter);
        //注册一个观察者
        adapter.registerAdapterDataObserver(mDataObserver);
        //解决GridLayout添加头部底部也要占据一行
        mWrapRecyclerAdapter.adjustSpanSize(this);
    }

    public void addHeader(View view) {
        if (mWrapRecyclerAdapter != null) {
            mWrapRecyclerAdapter.addHeader(view);
        }
    }

    public void removeHeader(View view) {
        if (mWrapRecyclerAdapter != null) {
            mWrapRecyclerAdapter.removeHeader(view);
        }
    }

    public void addFooter(View view) {
        if (mWrapRecyclerAdapter != null) {
            mWrapRecyclerAdapter.addFooter(view);
        }
    }

    public void removeFooter(View view) {
        if (mWrapRecyclerAdapter != null) {
            mWrapRecyclerAdapter.removeFooter(view);
        }
    }

    public void addEmptyView(View emptyView) {
        mEmptyView = emptyView;
    }

    public void addLoadingView(View loadingView) {
        mLoadingView = loadingView;
    }

    public void addRefreshView(View view) {
        mWrapRecyclerAdapter.addRefreshView(view);
    }

    public void setLoadingView(View view) {
        if (view != null) {
            mLoadingView = view;
        }
    }

    private void dataChanged() {
        if (mEmptyView != null) {
            if (mAdapter.getItemCount() == 0) {
                mEmptyView.setVisibility(VISIBLE);
            } else {
                mEmptyView.setVisibility(GONE);
            }
        }
    }
}
