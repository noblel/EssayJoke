package com.noblel.baselibrary.database.engine.greendao;

import com.noblel.baselibrary.database.DB;
import com.noblel.baselibrary.log.LogManager;

import org.greenrobot.greendao.AbstractDao;

import static com.noblel.baselibrary.database.engine.greendao.GreenDaoMaster.DAO;

/**
 * @author Noblel
 */
public class GreenDaoUtil {

    public static Class<? extends AbstractDao<?, ?>> getDaoClass(Class<?> clazz){
        try {
            Class<? extends AbstractDao<?, ?>> daoClass = (Class<? extends AbstractDao<?,?>>)
                    Class.forName(clazz.getCanonicalName() + DAO);
            LogManager.i(DB.TAG,"daoClass:" + daoClass.getName());
            return daoClass;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
