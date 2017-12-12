package com.noblel.baselibrary.http.base;

/**
 * @author Noblel
 */
public interface IHttpCallback {
    /**
     * 成功的回调
     * @param result
     */
    void onSuccess(String result);

    /**
     * 失败的回调
     * @param e
     */
    void onFail(Exception e);
}
