package com.noblel.essayjoke;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.noblel.baselibrary.ExceptionCrashHandler;
import com.noblel.baselibrary.fix.FixDexManager;
import com.noblel.baselibrary.ioc.OnClick;
import com.noblel.baselibrary.ioc.ViewById;
import com.noblel.framelibrary.BaseSkinActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.widget.Toast.*;


public class MainActivity extends BaseSkinActivity {

    private Button btn_main;

    @Override
    protected void initData() {
        fixDexBug();
//        andFixBug();
    }

    private void fixDexBug() {
        File fixFile = new File(Environment.getExternalStorageDirectory(), "fix.dex");
        if (fixFile.exists()) {
            //修复bug
            FixDexManager manager = new FixDexManager(this);
            try {
                manager.fix(fixFile.getAbsolutePath());
                Toast.makeText(this, "修复成功", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "修复失败", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void initView() {
        btn_main = (Button) findViewById(R.id.btn_main);
        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, 2/0 + "Bug修复测试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void setContentView() {
        //设置没有Title,只有Content全屏显示
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
    }

    //阿里热修复
    private void andFixBug() {

//        //获取上次崩溃信息上传到服务器
//        File crashFile = ExceptionCrashHandler.getInstance().getCrashFile();
//        if (crashFile.exists()){
//            //上传到服务器
//            try {
//                InputStreamReader isr = new InputStreamReader(new FileInputStream(crashFile));
//                char[] buffer = new char[1024];
//                int len = 0;
//                while ((len = isr.read(buffer)) != -1){
//                    String str = new String(buffer,0,len);
//                    Log.d("TAG",str);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
        //每次启动从后台获取差分包，修复本地bug
        //直接获取内存卡里面的fix.aptach
        File fixFile = new File(Environment.getExternalStorageDirectory(), "fix.apatch");
        if (fixFile.exists()) {
            //修复bug
            try {
                App.sManager.addPatch(fixFile.getAbsolutePath());
                Toast.makeText(this, "修复成功", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "修复失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
