package com.noblel.baselibrary.server.binder;

import android.os.RemoteException;

import com.noblel.baselibrary.log.LogManager;
import com.noblel.baselibrary.server.core.CoreBinderConstant;
import com.noblel.baselibrary.server.core.ICompute;


/**
 * @author Noblel
 */
public class ComputeImpl extends ICompute.Stub {

    @Override
    public int add(int a, int b) throws RemoteException {
        LogManager.i(CoreBinderConstant.TAG, "ComputeImpl add : " + a + " + " + b);
        return a + b + 1;
    }

}

