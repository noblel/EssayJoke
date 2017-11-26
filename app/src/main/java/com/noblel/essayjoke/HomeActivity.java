package com.noblel.essayjoke;

import com.noblel.baselibrary.ioc.OnClick;
import com.noblel.essayjoke.fragment.FindFragment;
import com.noblel.essayjoke.fragment.FragmentManagerHelper;
import com.noblel.essayjoke.fragment.HomeFragment;
import com.noblel.essayjoke.fragment.MessageFragment;
import com.noblel.essayjoke.fragment.NewFragment;
import com.noblel.framelibrary.BaseSkinActivity;
import com.noblel.framelibrary.DefaultNavigationBar;

public class HomeActivity extends BaseSkinActivity {

    private HomeFragment mHomeFragment;
    private MessageFragment mMessageFragment;
    private NewFragment mNewFragment;
    private FindFragment mFindFragment;
    private FragmentManagerHelper mFragmentManagerHelper;

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
        setContentView(R.layout.activity_home);
    }


    @Override
    protected void initData() {
        mFragmentManagerHelper = new FragmentManagerHelper(getSupportFragmentManager(),
                R.id.main_tab_f1);
        mHomeFragment = new HomeFragment();
        mFragmentManagerHelper.add(mHomeFragment);
    }

    @Override
    protected void initView() {

    }

    @OnClick(R.id.home_rb)
    private void homeRbClick() {
        if (mHomeFragment == null) {
            mHomeFragment = new HomeFragment();
        }
        mFragmentManagerHelper.switchFragment(mHomeFragment);
    }

    @OnClick(R.id.find_rb)
    private void findRbClick() {
        if (mFindFragment == null) {
            mFindFragment = new FindFragment();
        }
        mFragmentManagerHelper.switchFragment(mFindFragment);
    }

    @OnClick(R.id.new_rb)
    private void newRbClick() {
        if (mNewFragment == null) {
            mNewFragment = new NewFragment();
        }
        mFragmentManagerHelper.switchFragment(mNewFragment);
    }

    @OnClick(R.id.message_rb)
    private void messageRbClick() {
        if (mMessageFragment == null) {
            mMessageFragment = new MessageFragment();
        }
        mFragmentManagerHelper.switchFragment(mMessageFragment);
    }
}
