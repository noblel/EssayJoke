package com.noblel.essayjoke.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.noblel.baselibrary.log.LogManager;
import com.noblel.baselibrary.utils.ToastUtil;
import com.noblel.essayjoke.R;

import static com.noblel.baselibrary.dc.DCConstant.TAG;

public class WelcomeActivity extends Activity {

    private static final long WAIT_TIME = 3000;

    private static final int MY_PERMISSIONS_REQUEST_WRITE_STORE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initData();
        // wait for a moment start activity
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                //接入广告
                //进入主Activity
                Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, WAIT_TIME);
    }

    private void initData() {
        //如果没有权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSIONS_REQUEST_WRITE_STORE);
        }else {
            initDataBase();
        }
    }

    private void initDataBase() {
        //创建数据库操作
        ToastUtil.showShortToast("获取存储卡的权限");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_STORE){
            LogManager.d(TAG,"onRequestPermissionsResult,size:" + grantResults.length);

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                initDataBase();
            }else {
                ToastUtil.showLongToast("你拒绝了获取存储卡的权限");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
