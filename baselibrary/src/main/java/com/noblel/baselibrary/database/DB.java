package com.noblel.baselibrary.database;


import com.noblel.baselibrary.utils.AndroidUtil;

import java.io.File;

/**
 * @author Noblel
 */
public class DB {

    /**
     * 调试标志
     */
    public static final String TAG = "<DB>";

    /**
     * 数据库路径，存放在Android/data/xxx.xxx.xxx/database/下
     */
    public static final String DATABASE_PATH = AndroidUtil.getExternalDataPath() + File.separator
            + "database" + File.separator;
}
