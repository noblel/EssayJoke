package com.noblel.essayjoke;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.noblel.baselibrary.ExceptionCrashHandler;
import com.noblel.baselibrary.dialog.AlertDialog;
import com.noblel.baselibrary.fix.FixDexManager;
import com.noblel.baselibrary.http.EngineCallBack;
import com.noblel.baselibrary.http.HttpUtils;
import com.noblel.baselibrary.ioc.OnClick;
import com.noblel.baselibrary.ioc.ViewById;
import com.noblel.framelibrary.BaseSkinActivity;
import com.noblel.framelibrary.DefaultNavigationBar;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.widget.Toast.*;


public class MainActivity extends BaseSkinActivity {

    private Button btn_main;

    @Override
    protected void initData() {
        HttpUtils.width(this).url("http://lf.snssdk.com/neihan/stream/mix/v1/?mpic=1&webp=1&essence=1&content_type=-101&message_cursor=-1&longitude=123.918176&latitude=47.351132&am_longitude=123.925003&am_latitude=47.353204&am_city=%E9%BD%90%E9%BD%90%E5%93%88%E5%B0%94%E5%B8%82&am_loc_time=1510204195965&count=30&min_time=1510204199&screen_width=1080&iid=17233551625&device_id=35449750107&ac=wifi&channel=xiaomi&aid=7&app_name=joke_essay&version_code=555&version_name=5.5.5&device_platform=android&ssmix=a&device_type=A0001&device_brand=oneplus&os_api=23&os_version=6.0.1&uuid=864587029896611&openudid=bbb66ce3cbf8f5c3&manifest_version_code=555&resolution=1080*1920&dpi=480&update_version_code=5551").get().execute(new EngineCallBack() {
            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onSuccess(String result) {
                Log.e("TAG", result);
            }
        });
    }


    @Override
    protected void initView() {
        btn_main = (Button) findViewById(R.id.btn_main);
        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setContentView(R.layout.detail_comment_dialog)
                        .fullWith()
                        .fromBottom(true)
                        .show();

                final EditText comment = dialog.getView(R.id.comment_editor);

                dialog.setOnClickListener(R.id.submit_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, comment.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void initTitle() {
        DefaultNavigationBar navigationBar = new DefaultNavigationBar
                .Builder(this)
                .setTitle("首页")
                .setRightText("我的")
                .builder();
    }

    @Override
    protected void setContentView() {
        //设置没有Title,只有Content全屏显示
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
    }
}
