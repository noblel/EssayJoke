package com.noblel.essayjoke.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.noblel.baselibrary.adapter.OnItemClickListener;
import com.noblel.baselibrary.base.BaseFragment;
import com.noblel.baselibrary.http.HttpManager;
import com.noblel.baselibrary.http.base.HttpRequest;
import com.noblel.baselibrary.http.base.INetCallback;
import com.noblel.baselibrary.http.base.RequestMethod;
import com.noblel.baselibrary.ioc.ViewById;
import com.noblel.baselibrary.log.LogManager;
import com.noblel.baselibrary.utils.ToastUtil;
import com.noblel.baselibrary.view.wrap.WrapRecyclerView;
import com.noblel.essayjoke.R;
import com.noblel.essayjoke.adapter.DiscoverListAdapter;
import com.noblel.essayjoke.model.DiscoverResult;
import com.noblel.essayjoke.model.DiscoverResult.DataBean.RotateBannerBean.BannersBean;
import com.noblel.framelibrary.view.banner.BannerAdapter;
import com.noblel.framelibrary.view.banner.BannerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Noblel
 */
public class FindFragment extends BaseFragment {

    private static final String TAG = FindFragment.class.getSimpleName();

    @ViewById(R.id.recycler_view)
    private WrapRecyclerView mWrapRecyclerView;

    @Override
    protected void initData() {
        new HttpManager().execute(getChildFragmentManager(), buildRequest(),
                new INetCallback<DiscoverResult>() {
                    @Override
                    public void onSuccess(DiscoverResult result) {
                        LogManager.d(TAG, "name --> " + result.getData().getCategories().getName());
                        showListData(result.getData().getCategories().getCategory_list());
                        addBannerView(result.getData().getRotate_banner().getBanners());
                    }

                    @Override
                    public void onFail(Exception e) {
                        LogManager.d(TAG, e.getMessage());
                        ToastUtil.showLongToast(e.getMessage());
                    }
                });
    }

    private HttpRequest buildRequest() {

        HttpRequest request = new HttpRequest();

        request.setUrl("http://is.snssdk.com/2/essay/discovery/v3/");
        Map<String, Object> params = new HashMap<>();

        params.put("iid", "6152551759");
        params.put("aid", "7");
        params.put("channel", 360);

        addCommonParams(params);

        request.setParams(params);
        request.setRequestMethod(RequestMethod.GET);
        request.setUseCache(true);
        return request;
    }

    private void addCommonParams(Map<String, Object> params) {
        params.put("app_name", "joke_essay");
        params.put("version_name", "5.7.0");
        params.put("ac", "wifi");
        params.put("device_id", "30036118478");
        params.put("device_brand", "Xiaomi");
        params.put("update_version_code", "5701");
        params.put("manifest_version_code", "570");
        params.put("longitude", "113.000366");
        params.put("latitude", "28.171377");
        params.put("device_platform", "android");
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
