package com.noblel.baselibrary.https.api;

/**
 * @author Noblel
 *         http请求监听器
 */
public interface IHttpListener<T> {
    /**
     * 成功的回调
     *
     * @param response
     */
    void onSuccess(T response);

    /**
     * 失败的回调
     *
     * @param exception
     */
    void onFailure(Exception exception);
}
