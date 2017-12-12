package com.noblel.baselibrary.https.api;


import android.support.v4.app.FragmentManager;

import com.noblel.baselibrary.https.IHttpExecutor;
import com.noblel.baselibrary.https.server.HttpServerProxy;


/**
 * @author Noblel
 */
public class HttpManager implements IHttpExecutor {

    /**
     * HttpServer 服务代理
     */
    private HttpServerProxy mProxy;

    /**
     * 构造方法
     */
    public HttpManager() {
        mProxy = new HttpServerProxy();
    }

    /**
     * 执行https请求
     *
     * @param request  请求参数
     * @param listener 回调listener
     * @param <T>      泛型参数
     * @return 该任务的taskId
     */
    @Override
    public <T, R> String execute(HttpRequest<T> request, IHttpListener<R> listener) {
        return mProxy.execute(request, listener);
    }

    /**
     * 执行https请求
     *
     * @param fragmentManager 显示对话框的fragment
     * @param request         请求参数
     * @param listener        回调listener
     * @param <T>             泛型参数
     * @return 该任务的taskId
     */
    @Override
    public <T, R> String execute(FragmentManager fragmentManager, HttpRequest<T> request, IHttpListener<R> listener) {
        return mProxy.execute(fragmentManager, request, listener);
    }

    /**
     * 执行https请求
     *
     * @param fragmentManager 显示对话框的fragment
     * @param request         请求参数
     * @param listener        回调listener
     * @param <T>             泛型参数
     * @return 该任务的taskId
     */
    @Override
    public <T, R> String execute(android.app.FragmentManager fragmentManager, HttpRequest<T> request, IHttpListener<R> listener) {
        return mProxy.execute(fragmentManager, request, listener);
    }

    /**
     * 取消网络请求
     *
     * @param taskId 需要取消的 taskId
     */
    @Override
    public void cancel(String taskId) {
        mProxy.cancel(taskId);
    }

}
