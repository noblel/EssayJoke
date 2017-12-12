package com.noblel.baselibrary.https.server;

/**
 * @author Noblel
 * https内部回调
 */
public interface IHttpTransferCallback<R> extends IHttpCallback<R>{

    /**
     * 成功的回调
     * @param response
     */
    @Override
    void onSuccess(HttpResponse<R> response);

    /**
     * 进度回调
     * @param currentLength
     * @param totalLength
     */
    void onProgress(long currentLength, long totalLength);

    /**
     * 失败的回调
     * @param exception
     */
    @Override
    void onFailure(Exception exception);
}
