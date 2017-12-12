package com.noblel.essayjoke.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.noblel.baselibrary.log.LogManager;
import com.noblel.baselibrary.utils.ToastUtil;
import com.noblel.eassayjoke.IProcessConnection;


public class RemoteService extends Service {
    private static final String TAG = "Service";
    @Override
    public IBinder onBind(Intent intent) {
        return new IProcessConnection.Stub(){
            @Override
            public void connect() throws RemoteException {
                ToastUtil.showLongToast("RemoteCoreService 已经建立连接 ！");
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bindService(new Intent(RemoteService.this, LocalService.class),mServiceConnection, Context.BIND_IMPORTANT);
        LogManager.d(TAG,"LocalService 已经启动");
        return START_STICKY;
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ToastUtil.showLongToast("与LocalService 已经建立连接");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            startService(new Intent(RemoteService.this, LocalService.class));
            bindService(new Intent(RemoteService.this, LocalService.class),mServiceConnection,Context.BIND_IMPORTANT);
        }
    };

}
