package com.noblel.framelibrary.http;

import android.content.Context;

import com.google.gson.Gson;
import com.noblel.baselibrary.http.EngineCallBack;
import com.noblel.baselibrary.http.HttpUtils;

import java.util.Map;

/**
 * @author Noblel
 */

public abstract class HttpCallBack<T> implements EngineCallBack {
    @Override
    public void onPreExecute(Context context, Map<String, Object> params) {
        //与项目业务逻辑相关
        //http://lf.snssdk.com/2/essay/discovery/v3/?iid=17314746569
        params.put("app_name", "joke_essay");
        params.put("version_code", "570");
        params.put("version_name", "5.7.0");
        params.put("device_platform", "android");
        params.put("ac", "wifi");
        params.put("device_id", "35449750107");
        params.put("device_brand", "oneplus");
        params.put("device_type", "A0001");
        params.put("os_api", "23");
        params.put("os_version", "6.0.1");
        params.put("update_version_code", "5701");
        params.put("dpi", "480");
        params.put("resolution", "1080*1920");
        params.put("manifest_version_code", "570");
        params.put("openudid", "bbb66ce3cbf8f5c3");
        params.put("uuid", "864587029896611");
        params.put("ssmix", "a");
        params.put("ssmix", "a");
        params.put("channel", "360");

        onPreExecute();
    }

    //开始执行
    public void onPreExecute() {

    }

    @Override
    public void onSuccess(String result) {
        Gson gson = new Gson();
        T objResult = (T) gson.fromJson(result, HttpUtils.analysisClazzInfo(this));
        onSuccess(objResult);
    }

    //返回直接可以操作的对象
    public abstract void onSuccess(T result);
}
