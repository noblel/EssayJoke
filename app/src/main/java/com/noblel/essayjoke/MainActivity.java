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

    }

    @Override
    protected void setContentView() {
        //设置没有Title,只有Content全屏显示
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
    }
}
