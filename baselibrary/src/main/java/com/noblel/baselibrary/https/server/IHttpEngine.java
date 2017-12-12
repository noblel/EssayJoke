package com.noblel.baselibrary.https.server;


import android.os.Handler;

/**
 * @author Noblel
 * Https引擎
 */
public interface IHttpEngine {

    /**
     * get请求
     * @param task
     * @param callback
     * @return 返回task id
     */
    <T,R> void enqueueGetCall(final HttpTask<T> task, final IHttpCallback<R> callback);

    /**
     * post请求
     * @param task
     * @param callback
     * @return 返回task id
     */
    <T,R> void enqueuePostCall(final HttpTask<T> task, final IHttpCallback<R> callback);

    /**
     * download请求
     * @param task
     * @param callback
     * @return 返回task id
     */
    <T,R> void enqueueDownloadCall(final HttpTask<T> task, final IHttpTransferCallback<R> callback);

    /**
     * upload请求
     * @param task
     * @param callback
     * @return 返回task id
     */
    <T,R> void enqueueUploadCall(final HttpTask<T> task, final IHttpTransferCallback<R> callback);

    /**
     * 取消http请求
     * @param taskId
     */
    void cancelHttpCall(String taskId);

    /**
     * 获取主线程的Hanlder
     * @return
     */
    Handler getHandler();
}
