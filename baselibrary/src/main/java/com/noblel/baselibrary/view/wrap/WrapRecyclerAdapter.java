package com.noblel.baselibrary.view.wrap;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.noblel.baselibrary.adapter.BaseRecyclerAdapter;
import com.noblel.baselibrary.adapter.BaseViewHolder;
import com.noblel.baselibrary.adapter.MultiTypeSupport;

import java.util.List;


/**
 * @author Noblel
 */
public abstract class WrapRecyclerAdapter<T> extends BaseRecyclerAdapter<T> {

    private static final String TAG = WrapRecyclerAdapter.class.getSimpleName();
    //数据列表的Adapter不包含头部
    private BaseRecyclerAdapter mAdapter;
    //头部和底部的集合
    private SparseArray<View> mHeaders, mFooters;
    //头部索引
    private static int BASE_ITEM_TYPE_HEADER = 10000000;
    //底部索引
    private static int BASE_ITEM_TYPE_FOOTER = 20000000;
    //刷新
    private View mRefreshView;
    //加载更多
    private View mLoadMoreView;

    public WrapRecyclerAdapter(BaseRecyclerAdapter adapter) {
        mAdapter = adapter;
        init();
    }

    private void init() {
        mHeaders = new SparseArray<>();
        mFooters = new SparseArray<>();
    }

    public WrapRecyclerAdapter(Context context, List<T> data, int layoutId) {
        super(context, data, layoutId);
        init();
    }

    public WrapRecyclerAdapter(Context context, List<T> data, MultiTypeSupport typeLayout) {
        super(context, data, typeLayout);
        init();
    }

    /**
     * 添加头部
     */
    public void addHeader(View view) {
        if (view == null) {
            return;
        }
        int position = mHeaders.indexOfValue(view);
        if (position >= 0) {
            return;
        }
        mHeaders.put(BASE_ITEM_TYPE_HEADER++, view);
        notifyDataSetChanged();
    }

    public void addRefreshView(View view) {
        if (view == null) {
            return;
        }
        mRefreshView = view;
        int pos = mHeaders.indexOfValue(view);

        if (mHeaders.size() == 0) {
            if (pos < 0) {
                mHeaders.put(BASE_ITEM_TYPE_HEADER++, view);
            } else {
                mHeaders.setValueAt(pos, view);
            }
        } else {
            SparseArray<View> temp = mHeaders.clone();
            mHeaders.clear();
            BASE_ITEM_TYPE_HEADER = 1000000;
            if (pos < 0) {
                mHeaders.put(BASE_ITEM_TYPE_HEADER++, view);
            } else {
                mHeaders.setValueAt(pos, view);
            }
            for (int i = 0; i < temp.size(); i++) {
                int key = temp.keyAt(i);
                mHeaders.put(BASE_ITEM_TYPE_HEADER++, temp.get(key));
            }
        }
        notifyDataSetChanged();
    }

    public void removeHeader(View view) {
        if (mHeaders.indexOfValue(view) < 0) return;
        mHeaders.removeAt(mHeaders.indexOfValue(view));
        notifyDataSetChanged();
    }

    public void addFooter(View view) {
        if (mFooters.indexOfValue(view) < 0) {
            //集合没有就添加
            mFooters.put(BASE_ITEM_TYPE_FOOTER++, view);
        }
        if (mLoadMoreView != null) {
            int pos = mFooters.indexOfValue(mLoadMoreView);
            if (pos < 0) {
                mFooters.put(BASE_ITEM_TYPE_FOOTER++, mLoadMoreView);
            } else {
                mFooters.removeAt(pos);
                mFooters.put(BASE_ITEM_TYPE_FOOTER++, mLoadMoreView);
            }
        }
        notifyDataSetChanged();
    }

    public void addLoadMoreView(View view) {
        if (view == null) {
            return;
        }
        mLoadMoreView = view;
        int pos = mFooters.indexOfValue(view);
        if (pos < 0) {
            mFooters.put(BASE_ITEM_TYPE_FOOTER++, view);
        } else {
            mFooters.setValueAt(pos, view);
        }
        notifyDataSetChanged();
    }

    public void removeFooter(View view) {
        if (mFooters.indexOfValue(view) < 0) return;
        mFooters.removeAt(mFooters.indexOfValue(view));
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        //头部类型
        if (isHeaderPosition(position)) {
            Log.i(TAG, "mHeaders -- > " + mHeaders.size());
            return mHeaders.keyAt(position);
        }
        if (isFooterPosition(position)) {
            return mFooters.keyAt(position - mHeaders.size() - super.getItemCount());
        }
        return super.getItemViewType(position - mHeaders.size());
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isHeaderViewType(viewType)) {
            //头部
            return createHeaderViewHolder(parent, viewType);
        }
        if (isFooterViewType(viewType)) {
            //底部
            return createFooterViewHolder(parent, viewType);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    private BaseViewHolder createFooterViewHolder(ViewGroup parent, int viewType) {
        View view = mFooters.get(viewType);
        Log.i(TAG, "createFooterViewHolder: " + view);
        return new BaseViewHolder(view);
    }

    private BaseViewHolder createHeaderViewHolder(ViewGroup parent, int viewType) {
        View view = mHeaders.get(viewType);
        Log.i(TAG, "createHeaderViewHolder: " + view);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (isHeaderPosition(position) || isFooterPosition(position)) {
            Log.i(TAG, "onBindViewHolder: 有头部或者底部");
            return;
        }
        super.onBindViewHolder(holder, position - mHeaders.size());
    }

    private boolean isFooterViewType(int viewType) {
        return mFooters.indexOfKey(viewType) >= 0;
    }

    private boolean isHeaderViewType(int viewType) {
        return mHeaders.indexOfKey(viewType) >= 0;
    }

    private boolean isFooterPosition(int position) {
        return position >= mHeaders.size() + super.getItemCount();
    }

    private boolean isHeaderPosition(int position) {
        return position < mHeaders.size();
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + mFooters.size() + mHeaders.size();
    }

    public void adjustSpanSize(RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    boolean isHeaderOrFooter = isHeaderPosition(position) ||
                            isFooterPosition(position);
                    return isHeaderOrFooter ? layoutManager.getSpanCount() : 1;
                }
            });
        }
    }
}
