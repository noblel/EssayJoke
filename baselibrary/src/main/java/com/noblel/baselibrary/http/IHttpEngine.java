package com.noblel.baselibrary.http;

import android.content.Context;

import java.util.Map;

/**
 * @author Noblel
 *         引擎规范
 */

public interface IHttpEngine {
    //get请求
    void get(boolean isCache, Context context, String url, Map<String, Object> params, EngineCallBack callBack);

    //post请求
    void post(boolean isCache, Context context, String url, Map<String, Object> params, EngineCallBack callBack);

    //下载文件
    //上传文件
    //https添加证书
}
