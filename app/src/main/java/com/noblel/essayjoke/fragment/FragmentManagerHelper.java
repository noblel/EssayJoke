package com.noblel.essayjoke.fragment;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

/**
 * @author Noblel
 */
public class FragmentManagerHelper {
    private FragmentManager mFragmentManager;
    private int mContainerViewId;

    public FragmentManagerHelper(@NonNull FragmentManager fragmentManager,
                                 @IdRes int containerViewId) {
        mFragmentManager = fragmentManager;
        mContainerViewId = containerViewId;
    }

    public void add(Fragment fragment) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.add(mContainerViewId,fragment);
        ft.commit();
    }


    public void switchFragment(Fragment fragment) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        //隐藏当前所有的Fragment
        List<Fragment> childFragments = mFragmentManager.getFragments();
        for (Fragment childFragment : childFragments) {
            ft.hide(childFragment);
        }

        if (!childFragments.contains(fragment)) {
            ft.add(mContainerViewId, fragment);
        } else {
            ft.show(fragment);
        }
        ft.commit();

    }
}
