package com.noblel.baselibrary.http.okhttp;


import com.noblel.baselibrary.http.base.HttpHelper;
import com.noblel.baselibrary.http.base.HttpRequest;
import com.noblel.baselibrary.http.base.IHttpCallback;
import com.noblel.baselibrary.http.base.IHttpEngine;
import com.noblel.baselibrary.log.LogManager;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author Noblel
 */
public class OkHttpEngine implements IHttpEngine {

    /**
     * OkHttpClient客户端
     */
    private static OkHttpClient mClient = new OkHttpClient();


    @Override
    public void get(HttpRequest req, final IHttpCallback callback) {

        String url = HttpHelper.buildGetRequest(req);
        LogManager.e("Get请求路径：", url);
        Request.Builder builder = new Request.Builder().url(url);
        Request request = builder.build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFail(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()){
                    callback.onSuccess(response.body().string());
                }else {
                    callback.onFail(new Exception("error"));
                }
            }
        });
    }

    @Override
    public void post(HttpRequest httpRequest, final IHttpCallback callback) {

        RequestBody requestBody = buildPostRequest(httpRequest);

        Request request = new Request.Builder()
                .url(httpRequest.getUrl())
                .post(requestBody)
                .build();

        LogManager.e("Post请求路径：", httpRequest.getUrl());

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFail(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()){
                    callback.onSuccess(response.body().string());
                }else {
                    callback.onFail(new Exception("error"));
                }
            }
        });

    }

    /**
     * 创建post请求的requestBody
     * @param request
     * @return
     */
    private RequestBody buildPostRequest(HttpRequest request){
        return null;
    }
}
