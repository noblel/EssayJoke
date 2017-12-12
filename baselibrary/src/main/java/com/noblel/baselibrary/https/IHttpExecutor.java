package com.noblel.baselibrary.https;

import android.support.v4.app.FragmentManager;

import com.noblel.baselibrary.https.api.HttpRequest;
import com.noblel.baselibrary.https.api.IHttpListener;


/**
 * @author Noblel
 */
public interface IHttpExecutor {

    /**
     * 执行https请求
     * @param request 请求参数
     * @param listener 回调listener
     * @param <T> 请求泛型参数
     * @param <R> 响应泛型参数
     * @return 该任务的taskId
     */
    <T,R> String execute(HttpRequest<T> request, IHttpListener<R> listener);

    /**
     * 执行https请求
     * @param fragmentManager 显示对话框的fragment
     * @param request 请求参数
     * @param listener 回调listener
     * @param <T> 请求泛型参数
     * @param <R> 响应泛型参数
     * @return 该任务的taskId
     */
    <T,R> String execute(FragmentManager fragmentManager, HttpRequest<T> request, IHttpListener<R> listener);

    /**
     * 执行https请求
     * @param fragmentManager 显示对话框的fragment
     * @param request 请求参数
     * @param listener 回调listener
     * @param <T> 请求泛型参数
     * @param <R> 响应泛型参数
     * @return 该任务的taskId
     */
    <T,R> String execute(android.app.FragmentManager fragmentManager, HttpRequest<T> request, IHttpListener<R> listener);

    /**
     * 取消网络请求
     * @param taskId 需要取消的 taskId
     */
    void cancel(String taskId);
}
