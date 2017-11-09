package com.noblel.baselibrary.http;

/**
 * @author Noblel
 */

public interface EngineCallBack{
    void onError(Exception e);

    void onSuccess(String result);

    //默认的回调
    EngineCallBack DEFAULT_CALL_BACK = new EngineCallBack() {
        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onSuccess(String result) {

        }
    };
}
