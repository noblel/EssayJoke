package com.noblel.framelibrary.http;

import com.noblel.framelibrary.db.DaoSupportFactory;
import com.noblel.framelibrary.db.IDaoSupport;

import java.util.List;

/**
 * @author Noblel
 */

public class CacheUtil {
    private static IDaoSupport<CacheData> sCacheDataIDaoSupport =
            DaoSupportFactory.getInstance().getDao(CacheData.class);

    /**
     * 从数据库获取缓存数据
     *
     * @param urlKey
     * @return
     */
    public static String getCacheResultJson(String urlKey) {
        String resultJson = null;
        List<CacheData> cacheData = sCacheDataIDaoSupport.querySupport()
                .selection("mUrlKey=?").selectionArgs(urlKey).query();
        if (cacheData.size() > 0) {
            CacheData data = cacheData.get(0);
            resultJson = data.getResultJson();
            return resultJson;
        }
        return resultJson;
    }

    /**
     * 缓存数据
     *
     * @return
     */
    public static long cacheData(String urlKey, String result) {
        sCacheDataIDaoSupport.deleteSupport().whereClause("mUrlKey=?").whereArgs(urlKey).delete();
        return sCacheDataIDaoSupport.insertSupport().insert(new CacheData(urlKey,result));
    }

}
