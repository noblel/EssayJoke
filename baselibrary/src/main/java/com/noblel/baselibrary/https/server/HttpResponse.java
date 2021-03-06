package com.noblel.baselibrary.https.server;

import java.util.Map;

/**
 * @author Noblel
 */
public class HttpResponse<R> {
    /**
     * 头部信息
     */
    private Map<String,String> mHeaderMap;
    /**
     * 内容
     */
    private R mContent;

    /**
     * 无参构造方法
     */
    public HttpResponse() {
    }

    /**
     * 构造方法
     * @param content 响应主题
     */
    public HttpResponse(R content) {
        mContent = content;
    }

    /**
     * @return {@link #mContent}
     */
    public R getContent() {
        return mContent;
    }

    /**
     * @param content the {@link #mContent} to set
     */
    public void setContent(R content) {
        mContent = content;
    }

    /**
     * @return {@link #mHeaderMap}
     */
    public Map<String, String> getHeaderMap() {
        return mHeaderMap;
    }

    /**
     * @param headerMap the {@link #mHeaderMap} to set
     */
    public void setHeaderMap(Map<String, String> headerMap) {
        mHeaderMap = headerMap;
    }

    /**
     * 判断响应是否是ok
     * @param response
     * @param <R>
     * @return
     */
    public static <R> boolean isOK(HttpResponse<R> response){
        // TODO: 2017/10/24 后面要改
//        if (response != null && response.getContent() != null){
//            return true;
//        }
//        return false;
        return true;
    }

}
