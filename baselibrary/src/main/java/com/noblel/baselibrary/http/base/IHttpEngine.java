package com.noblel.baselibrary.http.base;

/**
 * @author Noblel
 * http的引擎规范
 */
public interface IHttpEngine {

    /**
     * get请求
     * @param httpRequest
     * @param callback
     */
    void get(HttpRequest httpRequest, IHttpCallback callback);

    /**
     * post请求
     * @param httpRequest
     * @param callback
     */
    void post(HttpRequest httpRequest, IHttpCallback callback);


    //文件下载

    //文件下载

}
