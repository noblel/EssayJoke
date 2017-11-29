package com.noblel.essayjoke.fragment;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.noblel.baselibrary.adapter.OnItemClickListener;
import com.noblel.baselibrary.base.BaseFragment;
import com.noblel.baselibrary.http.HttpUtils;
import com.noblel.baselibrary.ioc.ViewById;
import com.noblel.baselibrary.wrap.WrapRecyclerView;
import com.noblel.essayjoke.R;
import com.noblel.essayjoke.adapter.DiscoverListAdapter;
import com.noblel.essayjoke.model.DiscoverResult;
import com.noblel.essayjoke.model.DiscoverResult.DataBean.RotateBannerBean.BannersBean;
import com.noblel.framelibrary.bannner.BannerAdapter;
import com.noblel.framelibrary.bannner.BannerView;
import com.noblel.framelibrary.http.HttpCallBack;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Noblel
 */
public class FindFragment extends BaseFragment {

    private static final String TAG = FindFragment.class.getSimpleName();

    @ViewById(R.id.recycler_view)
    private WrapRecyclerView mWrapRecyclerView;

    @Override
    protected void initData() {
        HttpUtils.width(mContext).url("http://is.snssdk.com/2/essay/discovery/v3/?")
                .addParam("iid", "6152551759")
                .addParam("aid", "7")
                .execute(new HttpCallBack<DiscoverResult>() {
                    @Override
                    public void onSuccess(DiscoverResult result) {
                        Log.i(TAG, "onSuccess: " + Thread.currentThread().getName());
                        //显示列表
                        showListData(result.getData().getCategories().getCategory_list());
                        //添加轮播图
                        addBannerView(result.getData().getRotate_banner().getBanners());
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }

    private void addBannerView(final List<BannersBean> banners) {
        //没有轮播就不添加
        if (banners.size() <= 0) {
            return;
        }

        BannerView bannerView = (BannerView) LayoutInflater.from(mContext)
                .inflate(R.layout.layout_banner_view, mWrapRecyclerView, false);
        bannerView.setAdapter(new BannerAdapter() {
            @Override
            public View getView(int position, View convertView) {
                if (convertView == null) {
                    convertView = new ImageView(mContext);
                }
                ((ImageView) convertView).setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(mContext).load(banners.get(position).getBanner_url()
                        .getUrl_list().get(0).getUrl()).into((ImageView) convertView);
                return convertView;
            }

            @Override
            public int getCount() {
                return banners.size();
            }

            @Override
            public String getBannerDesc(int position) {
                return banners.get(position).getBanner_url().getTitle();
            }
        });
        bannerView.startRoll();
        mWrapRecyclerView.addHeader(bannerView);
        int type = mWrapRecyclerView.getAdapter().getItemViewType(0);
        Log.i(TAG, "type -->" + type);
    }

    private void showListData(final List<DiscoverResult.DataBean.CategoriesBean.CategoryListBean> list) {
        DiscoverListAdapter listAdapter = new DiscoverListAdapter(mContext, list,
                R.layout.channel_list_item);
        listAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.i(TAG, "onItemClick: " + position);
                Toast.makeText(mContext, "" + list.get(position - 1).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        mWrapRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mWrapRecyclerView.setAdapter(listAdapter);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find;
    }
}
