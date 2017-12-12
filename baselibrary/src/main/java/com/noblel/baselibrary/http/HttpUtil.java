package com.noblel.baselibrary.http;

import android.text.TextUtils;


import com.google.gson.Gson;
import com.noblel.baselibrary.database.DatabaseManager;
import com.noblel.baselibrary.database.IDatabaseSession;
import com.noblel.baselibrary.http.cache.HttpCache;
import com.noblel.baselibrary.log.LogManager;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author Noblel
 */
public class HttpUtil {

    private static final String TAG = "HTTP";

    private static IDatabaseSession<HttpCache> mEssayJokeDB = DatabaseManager
            .getInstance().openSession("essayjoke.db", HttpCache.class);

    /**
     * 拼接参数
     * @param url
     * @param params
     * @return
     */
    public static String jointParams(String url, Map<String, Object> params) {
        if (params == null || params.size() <= 0) {
            return url;
        }

        StringBuffer stringBuffer = new StringBuffer(url);
        if (!url.contains("?")) {
            stringBuffer.append("?");
        } else {
            if (!url.endsWith("?")) {
                stringBuffer.append("&");
            }
        }

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
        }

        stringBuffer.deleteCharAt(stringBuffer.length() - 1);

        return stringBuffer.toString();
    }

    /**
     * 将json解析为字符串
     * @param json
     * @param clazz
     * @param <T>
     */
    public static <T> T parseJson(String json,Class<T> clazz){
        if (json == null){
            return null;
        }

        Type genType = null;
        genType = clazz.getGenericInterfaces()[0];
        LogManager.d(TAG,"genType:" + genType.toString());

        //判断是不是参数化类型
        if (genType instanceof ParameterizedType){
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            Gson gson = new Gson();
            LogManager.d(TAG,"params:" + params.toString());
            T obj = (T) gson.fromJson(json,(Class) params[0]);
            return obj;
        }
        return null;
    }

    /**
     * 获取缓存
     * @param key
     * @return
     */
    public static String getCache(String key){

        //从数据库中去取
        List<HttpCache> caches = mEssayJokeDB.queryBuilder().selection("key = ?").selectionArgs(key).query();
        //有数据
        if (caches != null && caches.size() > 0){
            HttpCache cache = caches.get(0);
            return cache.getValue();
        }
        return null;
    }

    /**
     * 设置缓存
     * @param key
     * @param value
     * @return
     */
    public static long setCache(String key,String value){
        if (TextUtils.isEmpty(key)){
            return 0;
        }
        mEssayJokeDB.delete("key = ?",key);
        return mEssayJokeDB.insert(new HttpCache(key,value));
    }

    /**
     * 设置缓存
     * @param cache
     * @return
     */
    public static long setCache(HttpCache cache){
        if (cache != null){
            return 0;
        }
        mEssayJokeDB.delete("key = ?",cache.getKey());
        return mEssayJokeDB.insert(cache);
    }

}
