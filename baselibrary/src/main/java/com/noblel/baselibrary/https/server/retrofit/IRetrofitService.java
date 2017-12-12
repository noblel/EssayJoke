package com.noblel.baselibrary.https.server.retrofit;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;

/**
 * @author Noblel
 */
public interface IRetrofitService {

    /**
     * 获取发现列表
     * @param params get请求参数 建议都按照这样写，方便参数传递
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("discovery/")
    Call<Object> getDiscoverList(@QueryMap Map<String, String> params);
}
