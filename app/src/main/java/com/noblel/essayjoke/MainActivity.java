package com.noblel.essayjoke;

import android.Manifest;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.noblel.baselibrary.permission.PermissionFail;
import com.noblel.baselibrary.permission.PermissionHelper;
import com.noblel.baselibrary.permission.PermissionSuccess;
import com.noblel.framelibrary.BaseSkinActivity;
import com.noblel.framelibrary.DefaultNavigationBar;

public class MainActivity extends BaseSkinActivity {


    private static final int CALL_PHONE = 1;

    @Override
    protected void initTitle() {
        DefaultNavigationBar navigationBar = new DefaultNavigationBar
                .Builder(this)
                .hideLeftIcon()
                .setTitle("首页")
                .builder();
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    public void callPhone(View view) {
        PermissionHelper.with(this)
                .requestCode(CALL_PHONE)
                .requestPermission(Manifest.permission.CALL_PHONE)
                .request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionHelper.requestPermissionResult(this,requestCode,permissions);
    }

    @PermissionSuccess(requestCode = CALL_PHONE)
    public void callPhoneSuccess() {
        Toast.makeText(this, "成功", Toast.LENGTH_SHORT).show();
    }

    @PermissionFail(requestCode = CALL_PHONE)
    public void callPhoneFail() {
        Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show();
    }
}
