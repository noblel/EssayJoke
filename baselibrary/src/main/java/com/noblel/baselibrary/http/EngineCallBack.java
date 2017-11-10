package com.noblel.baselibrary.http;

import android.content.Context;

import java.util.Map;

/**
 * @author Noblel
 */

public interface EngineCallBack{
    //在执行之前回调的方法
    void onPreExecute(Context context, Map<String, Object> params);

    void onError(Exception e);

    void onSuccess(String result);

    //默认的回调
    EngineCallBack DEFAULT_CALL_BACK = new EngineCallBack() {
        @Override
        public void onPreExecute(Context context, Map<String, Object> params) {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onSuccess(String result) {

        }
    };
}
