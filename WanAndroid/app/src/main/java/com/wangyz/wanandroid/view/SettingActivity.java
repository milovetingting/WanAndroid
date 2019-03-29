package com.wangyz.wanandroid.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPUtils;
import com.wangyz.wanandroid.ConstantValue;
import com.wangyz.wanandroid.R;
import com.wangyz.wanandroid.base.BaseActivity;
import com.wangyz.wanandroid.bean.event.Event;
import com.wangyz.wanandroid.contract.Contract;
import com.wangyz.wanandroid.presenter.SettingActivityPresenter;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author wangyz
 * @time 2019/1/29 14:29
 * @description SettingActivity
 */
public class SettingActivity extends BaseActivity<Contract.SettingActivityView, SettingActivityPresenter> implements Contract.SettingActivityView {

    @BindView(R.id.night)
    Button mNight;

    @BindView(R.id.feedback)
    Button mFeedBack;

    @BindView(R.id.about)
    Button mAbout;

    private Context mContext;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();
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

    @OnClick(R.id.back)
    public void back() {
        finish();
    }

    @OnClick(R.id.night)
    public void setNightMode() {
        mNight.setSelected(!mNight.isSelected());
        SPUtils.getInstance(ConstantValue.CONFIG_SETTINGS).put(ConstantValue.KEY_NIGHT_MODE, mNight.isSelected());
        changeMode();
    }

    @OnClick(R.id.feedback)
    public void feedBack() {
        Intent intent = new Intent(mContext, ArticleActivity.class);
        intent.putExtra(ConstantValue.KEY_LINK, ConstantValue.URL_FEEDBACK);
        intent.putExtra(ConstantValue.KEY_TITLE, mContext.getString(R.string.feedback));
        startActivity(intent);
    }

    @OnClick(R.id.about)
    public void about() {
        Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.getDecorView().setPadding(20, 0, 20, 20);
        window.setAttributes(layoutParams);

        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_about, null, false);
        window.setContentView(view);

        TextView versionName = view.findViewById(R.id.dialog_versionName_tv);
        TextView detail = view.findViewById(R.id.dialog_detail_tv);
        versionName.setText(AppUtils.getAppVersionName());
        detail.setText(getString(R.string.function_detail));
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
}
