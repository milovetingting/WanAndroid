package com.wangyz.user.view;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPUtils;
import com.wangyz.common.ConstantValue;
import com.wangyz.common.base.BaseActivity;
import com.wangyz.common.bean.event.Event;
import com.wangyz.user.R;
import com.wangyz.user.contract.Contract;
import com.wangyz.user.presenter.SettingActivityPresenter;

import org.greenrobot.eventbus.EventBus;

/**
 * @author wangyz
 * @time 2019/1/29 14:29
 * @description SettingActivity
 */
public class SettingActivity extends BaseActivity<Contract.SettingActivityView, SettingActivityPresenter> implements Contract.SettingActivityView, View.OnClickListener {

    ImageView mBack;

    Button mNight;

    Button mFeedBack;

    private Context mContext;

    @Override
    protected int getContentViewId() {
        return R.layout.user_activity_settings;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();

        mBack = findViewById(R.id.back);
        mNight = findViewById(R.id.night);
        mFeedBack = findViewById(R.id.feedback);

        mBack.setOnClickListener(this);
        mNight.setOnClickListener(this);
        mFeedBack.setOnClickListener(this);

        boolean nightMode = SPUtils.getInstance(ConstantValue.CONFIG_SETTINGS).getBoolean(ConstantValue.KEY_NIGHT_MODE, false);
        mNight.setSelected(nightMode);
    }

    @Override
    protected SettingActivityPresenter createPresenter() {
        return new SettingActivityPresenter();
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoadSuccess() {

    }

    @Override
    public void onLoadFailed() {

    }

    public void changeMode() {
        int currentMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        getDelegate().setLocalNightMode(currentMode == Configuration.UI_MODE_NIGHT_NO ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        recreate();
        boolean nightMode = SPUtils.getInstance(ConstantValue.CONFIG_SETTINGS).getBoolean(ConstantValue.KEY_NIGHT_MODE, false);
        AppCompatDelegate.setDefaultNightMode(nightMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        Event event = new Event();
        event.target = Event.TARGET_MAIN;
        event.type = Event.TYPE_CHANGE_DAY_NIGHT_MODE;
        EventBus.getDefault().post(event);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.back) {
            finish();
        } else if (id == R.id.night) {
            mNight.setSelected(!mNight.isSelected());
            SPUtils.getInstance(ConstantValue.CONFIG_SETTINGS).put(ConstantValue.KEY_NIGHT_MODE, mNight.isSelected());
            changeMode();
        } else if (id == R.id.feedback) {
            ARouter.getInstance().build(ConstantValue.ROUTE_ARTICLE).withString(ConstantValue.KEY_LINK, ConstantValue.URL_FEEDBACK).withString(ConstantValue.KEY_TITLE, mContext.getString(R.string.common_feedback)).navigation();
        }
    }
}
