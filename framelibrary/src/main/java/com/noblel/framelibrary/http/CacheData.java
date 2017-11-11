package com.noblel.framelibrary.http;

/**
 * @author Noblel
 * 缓存类
 */

public class CacheData {
    private String mUrlKey;
    private String mResultJson;

    public CacheData() {

    }

    public CacheData(String urlKey, String resultJson) {
        mUrlKey = urlKey;
        mResultJson = resultJson;
    }

    public String getUrlKey() {
        return mUrlKey;
    }

    public void setUrlKey(String urlKey) {
        mUrlKey = urlKey;
    }

    public String getResultJson() {
        return mResultJson;
    }

    public void setResultJson(String resultJson) {
        mResultJson = resultJson;
    }
}
