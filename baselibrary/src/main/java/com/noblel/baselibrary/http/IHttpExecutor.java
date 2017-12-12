package com.noblel.baselibrary.http;

import android.support.v4.app.FragmentManager;

import com.noblel.baselibrary.http.base.HttpRequest;
import com.noblel.baselibrary.http.base.INetCallback;

/**
 * @author Noblel
 */
public interface IHttpExecutor {

    /**
     * 执行https请求
     * @param request
     * @param callback
     * @param <T>
     * @return
     */
    <T> String execute(HttpRequest request, final INetCallback<T> callback);

    /**
     * 执行https请求
     * @param fragmentManager
     * @param request
     * @param callback
     * @param <T>
     * @return
     */
    <T> String execute(FragmentManager fragmentManager, HttpRequest request, final INetCallback<T> callback);

    /**
     * 取消网络请求
     * @param taskId 需要取消的 id
     */
    void cancel(String taskId);

}
