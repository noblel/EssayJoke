package com.noblel.essayjoke.adapter;


import android.content.Context;
import android.text.Html;

import com.noblel.baselibrary.adapter.BaseViewHolder;
import com.noblel.baselibrary.wrap.WrapRecyclerAdapter;
import com.noblel.essayjoke.R;
import com.noblel.essayjoke.model.DiscoverResult.DataBean.CategoriesBean.CategoryListBean;

import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


/**
 * @author Noblel
 */
public class DiscoverListAdapter extends WrapRecyclerAdapter<CategoryListBean> {

    public DiscoverListAdapter(Context context, List<CategoryListBean> data, int resId) {
        super(context, data, resId);
    }

    @Override
    public void convert(BaseViewHolder holder, CategoryListBean item, int position) {
        //显示数据
        String str = item.getSubscribe_count() + "订阅 | " + "总帖数 <font color='#FF678D'>"
                + item.getTotal_updates() + "</font>";
        holder.setText(R.id.channel_text,item.getName())
                .setText(R.id.channel_topic,item.getIntro())
                .setText(R.id.channel_update_info, Html.fromHtml(str));
        //是否是最新
        if (item.isIs_recommend()) {
            holder.setVisibility(R.id.recommend_label,VISIBLE);
        } else {
            holder.setVisibility(R.id.recommend_label,GONE);
        }
        holder.setImagePath(R.id.channel_icon, new GlideImageLoader(item.getIcon_url()));
    }
}
