package com.wangyz.wx.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.wangyz.wx.R;

/**
 * @author wangyz
 * @time 2019/2/13 15:48
 * @description MainActivity
 */
public class MainActivity extends FragmentActivity {

    private FragmentManager mFragmentManager;

    private FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wx_activity_main);
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        WxFragment fragment = new WxFragment();
        mFragmentTransaction.add(R.id.wx_main_container, fragment);
        mFragmentTransaction.commit();
    }

}
