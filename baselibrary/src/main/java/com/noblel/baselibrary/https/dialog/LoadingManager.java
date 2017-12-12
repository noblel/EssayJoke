package com.noblel.baselibrary.https.dialog;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * @author Noblel
 */
public class LoadingManager {

    /**
     * 显示对话框
     *
     * @param manager
     * @param tag
     */
    public static void showDialog(Object manager, String tag) {
        if (manager == null) {
            return;
        }
        LoadingDialog dialog = new LoadingDialog();
        dialog.setCancelable(false);
        if (manager instanceof FragmentManager) {

            FragmentManager fragmentManager = (FragmentManager) manager;
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(dialog, tag);
            fragmentTransaction.commitNowAllowingStateLoss();//立即执行
        } else if (manager instanceof android.app.FragmentManager) {
            android.app.FragmentManager fragmentManager = (android.app.FragmentManager) manager;
            android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.add(dialog, tag);
//            fragmentTransaction.commitNowAllowingStateLoss();//立即执行
        }
    }

    /**
     * 取消对话框显示
     *
     * @param manager
     * @param tag
     */
    public static void dismissDialog(Object manager, String tag) {
        if (manager == null) {
            return;
        }
        if (manager instanceof FragmentManager) {
            FragmentManager fragmentManager = (FragmentManager) manager;
            LoadingDialog dialog = (LoadingDialog) fragmentManager.findFragmentByTag(tag);
            if (dialog != null) {
                dialog.dismissAllowingStateLoss();
            }
        } else if (manager instanceof android.app.FragmentManager) {
            android.app.FragmentManager fragmentManager = (android.app.FragmentManager) manager;
            android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.add(dialog, tag);
//            fragmentTransaction.commitNowAllowingStateLoss();//立即执行
        }
    }

    /**
     * 显示对话框
     *
     * @param manager
     * @param tag
     */
    public static void showProgressDialog(Object manager, String tag) {
        if (manager == null) {
            return;
        }

        ProgressDialog dialog = new ProgressDialog();

        dialog.setCancelable(false);
        if (manager instanceof FragmentManager) {

            FragmentManager fragmentManager = (FragmentManager) manager;
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(dialog, tag);
            fragmentTransaction.commitNowAllowingStateLoss();//立即执行
        } else if (manager instanceof android.app.FragmentManager) {
            android.app.FragmentManager fragmentManager = (android.app.FragmentManager) manager;
            android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.add(dialog, tag);
//            fragmentTransaction.commitNowAllowingStateLoss();//立即执行
        }
    }

    /**
     * 取消对话框显示
     *
     * @param manager
     * @param tag
     */
    public static void dismissProgressDialog(Object manager, String tag) {
        if (manager == null) {
            return;
        }
        if (manager instanceof FragmentManager) {
            FragmentManager fragmentManager = (FragmentManager) manager;
            ProgressDialog dialog = (ProgressDialog) fragmentManager.findFragmentByTag(tag);
            if (dialog != null) {
                dialog.dismissAllowingStateLoss();
            }
        } else if (manager instanceof android.app.FragmentManager) {
            android.app.FragmentManager fragmentManager = (android.app.FragmentManager) manager;
            android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.add(dialog, tag);
//            fragmentTransaction.commitNowAllowingStateLoss();//立即执行
        }
    }

    /**
     * 取消对话框显示
     *
     * @param manager
     * @param tag
     * @param progress
     */
    public static void setProgress(Object manager, String tag, int progress) {
        if (manager == null) {
            return;
        }
        if (manager instanceof FragmentManager) {
            FragmentManager fragmentManager = (FragmentManager) manager;
            ProgressDialog dialog = (ProgressDialog) fragmentManager.findFragmentByTag(tag);
            if (dialog != null) {
                dialog.setProgress(progress);
            }
        } else if (manager instanceof android.app.FragmentManager) {
            android.app.FragmentManager fragmentManager = (android.app.FragmentManager) manager;
            android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.add(dialog, tag);
//            fragmentTransaction.commitNowAllowingStateLoss();//立即执行
        }
    }

}
