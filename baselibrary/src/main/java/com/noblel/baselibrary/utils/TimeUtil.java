package com.noblel.baselibrary.utils;

import java.text.SimpleDateFormat;

/**
 * @author Noblel
 */
public class TimeUtil {

    public static final String FORMAT_1 = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 格式化时间
     *
     * @param time
     * @param format
     * @return
     */
    public static String formatTime(long time, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.format(time);
    }

}
