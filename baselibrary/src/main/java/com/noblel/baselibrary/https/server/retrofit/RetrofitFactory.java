package com.noblel.baselibrary.https.server.retrofit;


import com.noblel.baselibrary.https.server.okhttp.OkHttpFactory;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Noblel
 * 创建Retrofit对象的工厂类
 */
public class RetrofitFactory {

    /**
     * RetrofitMap
     */
    private static Map<String,Retrofit> sRetrofitMap = new HashMap<>();

    /**
     * 创建Retrofit实例
     * @param baseUrl 基础url
     * @return
     */
    public static Retrofit createRetrofit(String baseUrl){
        Retrofit retrofit = sRetrofitMap.get(baseUrl);
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .client(OkHttpFactory.createOkHttpClient())
                    .build();
        }
        return retrofit;
    }

}
