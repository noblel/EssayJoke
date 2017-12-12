package com.noblel.baselibrary.https.api;

/**
 * @author Noblel
 */
public interface IHttpTransferListener<T> extends IHttpListener<T> {
    /**
     * 成功的回调
     *
     * @param response
     */
    @Override
    void onSuccess(T response);

    /**
     * 进度回调
     *
     * @param currentLength
     * @param totalLength
     */
    void onProgress(long currentLength, long totalLength);

    /**
     * 失败的回调
     *
     * @param exception
     */
    @Override
    void onFailure(Exception exception);

}
