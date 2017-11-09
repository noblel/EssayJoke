package com.noblel.baselibrary.http;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Noblel
 */

public class HttpUtils{
    private String mUrl;
    private int mMethod = GET;
    private static final int POST = 0x0011;
    private static final int GET = 0x0001;
    private Context mContext;

    private Map<String,Object> mParams;

    private HttpUtils(Context context) {
        mContext = context;
        //mParams = new ArrayMap<>();//比起HashMap更高效一些
        mParams = new HashMap<>();
    }

    public static HttpUtils width(Context context) {
        return new HttpUtils(context);
    }

    public HttpUtils url(String url) {
        mUrl = url;
        return this;
    }

    public HttpUtils post() {
        mMethod = POST;
        return this;
    }

    public HttpUtils get() {
        mMethod = GET;
        return this;
    }

    //添加参数
    public HttpUtils addParam(String key, Object value) {
        mParams.put(key,value);
        return this;
    }

    public HttpUtils addParams(Map<String,Object> params) {
        mParams.putAll(params);
        return this;
    }

    //添加回调执行
    public void execute(EngineCallBack callBack) {
        if (callBack == null) {
            callBack = EngineCallBack.DEFAULT_CALL_BACK;
        }

        //判断执行方法
        if (mMethod == POST) {
            post(mUrl,mParams,callBack);
        }

        if (mMethod == GET) {
            get(mUrl,mParams,callBack);
        }
    }

    public void execute() {
        execute(null);
    }

    //默认是OkHttpEngine
    private static IHttpEngine mHttpEngine = new OkHttpEngine();

    //初始化引擎
    public static void init(IHttpEngine httpEngine) {
        mHttpEngine = httpEngine;
    }

    public void exchangeEngine(IHttpEngine httpEngine) {
        mHttpEngine = httpEngine;
    }

    //拼接参数
    public static String jointParams(String url, Map<String, Object> params) {
        if (params == null || params.size() < 0){
            return url;
        }

        StringBuilder sb = new StringBuilder(url);
        if (!url.contains("?")){
            sb.append("?");
        }else if (!url.endsWith("&")){
            sb.append("&");
        }

        for (Map.Entry<String, Object> entry : params.entrySet()){
            sb.append(entry.getKey() + "=" + entry.getValue() + "&");
        }

        sb.deleteCharAt(sb.length() - 1);

        System.out.println(sb.toString());

        return sb.toString();
    }


    private void get(String url, Map<String, Object> params, EngineCallBack callBack) {
        mHttpEngine.get(mContext,url, params, callBack);
    }

    private void post(String url, Map<String, Object> params, EngineCallBack callBack) {
        mHttpEngine.post(mContext,url, params, callBack);
    }
}
