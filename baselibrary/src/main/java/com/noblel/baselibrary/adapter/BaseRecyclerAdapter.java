package com.noblel.baselibrary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author Noblel
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    private static final String TAG = BaseRecyclerAdapter.class.getSimpleName();
    protected Context mContext;
    private int mLayoutId;
    /** 多条目适配 */
    private MultiTypeSupport<T> mTypeSupport;
    private List<T> mData;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mClickListener;
    private OnItemLongClickListener mLongClickListener;

    public BaseRecyclerAdapter() {

    }

    public BaseRecyclerAdapter(Context context, List<T> data, int layoutId) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mData = data;
        mLayoutId = layoutId;
    }

    public BaseRecyclerAdapter(Context context, List<T> data, MultiTypeSupport<T> typeSupport) {
        this(context, data, -1);
        mTypeSupport = typeSupport;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mTypeSupport != null) {
            //需要多布局
            mLayoutId = viewType;
        }
        //创建view
        View view = mLayoutInflater.inflate(mLayoutId, parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (mTypeSupport != null) {
            return mTypeSupport.getLayoutId(mData.get(position));
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        //Item点击事件
        if (mClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onItemClick(holder.getAdapterPosition());
                }
            });
        }

        if (mLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return mLongClickListener.onItemLongClick(holder.getAdapterPosition());
                }
            });
        }
        //ViewHolder优化
        convert(holder, mData.get(position), position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener clickListener) {
        mLongClickListener = clickListener;
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        mClickListener = clickListener;
    }

    /**
     * 数据绑定操作
     */
    public abstract void convert(BaseViewHolder holder, T item, int position);

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * 设置数据
     */
    public void setData(List<T> data) {
        mData = data;
        notifyDataSetChanged();
    }
}
