package com.noblel.baselibrary.https.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noblel.baselibrary.R;

/**
 * @author Noblel
 * http请求时显示的对话框
 */
public class LoadingDialog extends DialogFragment {

    public LoadingDialog(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_loading_dialog, null, false);
    }

}
