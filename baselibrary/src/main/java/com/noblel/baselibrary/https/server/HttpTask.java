package com.noblel.baselibrary.https.server;

import com.noblel.baselibrary.https.api.HttpRequest;
import com.noblel.baselibrary.https.api.IHttpListener;
import com.noblel.baselibrary.utils.UUIDUtil;

/**
 * @author Noblel
 */
public class HttpTask<T> {
    /**
     * 任务id
     */
    protected String mTaskId;
    /**
     * http task tag
     */
    protected String mTag;

    /**
     * 请求回调
     */
    protected IHttpListener mListener;

    /**
     * HTTP 请求
     */
    protected HttpRequest<T> mRequest;

    /**
     * FragmentManager
     */
    protected Object mFragmentManager;

    /**
     * 构造器
     * @param tag
     * @param listener
     */
    public HttpTask(String tag,IHttpListener listener) {
        mTag = tag;
        mListener = listener;
        mTaskId = mTag + "_" + UUIDUtil.get32UUID();
    }

    /**
     * 构造器
     * @param tag taskTAG
     * @param request Http请求
     * @param listener 回调Listener
     */
    public HttpTask(String tag, HttpRequest<T> request, IHttpListener listener,Object object) {
        mTag = tag;
        mRequest = request;
        mListener = listener;
        mTaskId = mTag + "_" + UUIDUtil.get32UUID();
        mFragmentManager = object;
    }

    /**
     * 获取请求，由子类实现
     * @return
     */
    public HttpRequest getRequest(){
        return mRequest;
    }

    /**
     * @return {@link #mTaskId}
     */
    public String getTaskId() {
        return mTaskId;
    }

    /**
     * @return {@link #mListener}
     */
    public IHttpListener getListener() {
        return mListener;
    }

    /**
     * @return {@link #mFragmentManager}
     */
    public Object getFragmentManager() {
        return mFragmentManager;
    }
}
