package com.noblel.baselibrary.https.server;

/**
 * @author Noblel
 * http内部回调
 */
public interface IHttpCallback<R>{

    /**
     * 成功的回调
     * @param response
     */
    void onSuccess(HttpResponse<R> response);

    /**
     * 失败的回调
     * @param exception
     */
    void onFailure(Exception exception);

}
