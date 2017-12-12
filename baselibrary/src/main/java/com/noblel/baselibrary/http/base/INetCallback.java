package com.noblel.baselibrary.http.base;

/**
 * @author Noblel
 * 网络请求的回调，对用户透明，展示
 */
public interface INetCallback<T> {
    /**
     * 成功的回调
     * @param result
     */
    void onSuccess(T result);

    /**
     * 失败的回调
     * @param e
     */
    void onFail(Exception e);

}
