package com.wangyz.wanandroid.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.wangyz.wanandroid.ConstantValue;
import com.wangyz.wanandroid.R;
import com.wangyz.wanandroid.base.BaseActivity;
import com.wangyz.wanandroid.bean.event.Event;
import com.wangyz.wanandroid.bean.model.Login;
import com.wangyz.wanandroid.contract.Contract;
import com.wangyz.wanandroid.presenter.LoginActivityPresenter;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author wangyz
 * @time 2019/1/24 11:45
 * @description LoginActivity
 */
public class LoginActivity extends BaseActivity<Contract.LoginActivityView, LoginActivityPresenter> implements Contract.LoginActivityView {

    @BindView(R.id.back)
    ImageView mBack;

    @BindView(R.id.username)
    EditText mUsername;

    @BindView(R.id.password)
    EditText mPassword;

    @BindView(R.id.login)
    Button mLogin;

    @BindView(R.id.go_register)
    Button mRegister;

    @BindView(R.id.loading)
    ImageView mLoading;

    private Context mContext;

    private String mReferrer;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();
        try {
            mReferrer = getIntent().getExtras().getString(ConstantValue.EXTRA_KEY_REFERRER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected LoginActivityPresenter createPresenter() {
        return new LoginActivityPresenter();
    }

    @Override
    public void onLoading() {
        LogUtils.i();
    }

    @Override
    public void onLoadSuccess() {
        LogUtils.i();
    }

    @Override
    public void onLoadFailed() {
        LogUtils.e();
        stopAnim();
    }

    @Override
    public void onLogin(Login result) {
        LogUtils.i();
        stopAnim();
        if (result != null) {
            if (result.getErrorCode() == 0) {

                Event event = new Event();
                event.target = Event.TARGET_HOME;
                event.type = Event.TYPE_LOGIN;
                EventBus.getDefault().post(event);

                Event treeEvent = new Event();
                treeEvent.target = Event.TARGET_TREE;
                treeEvent.type = Event.TYPE_LOGIN;
                EventBus.getDefault().post(treeEvent);

                Event projectEvent = new Event();
                projectEvent.target = Event.TARGET_PROJECT;
                projectEvent.type = Event.TYPE_LOGIN;
                EventBus.getDefault().post(projectEvent);

                Event wxEvent = new Event();
                wxEvent.target = Event.TARGET_WX;
                wxEvent.type = Event.TYPE_LOGIN;
                EventBus.getDefault().post(wxEvent);

                Event menuEvent = new Event();
                menuEvent.target = Event.TARGET_MENU;
                menuEvent.type = Event.TYPE_LOGIN;
                menuEvent.data = mUsername.getText().toString();
                EventBus.getDefault().post(menuEvent);

                finish();

                if (TextUtils.equals(ConstantValue.EXTRA_VALUE_COLLECT, mReferrer)) {
                    Intent intent = new Intent(mContext, CollectActivity.class);
                    startActivity(intent);
                }

            } else {
                ToastUtils.showShort(result.getErrorMsg());
            }
        }
    }

    @OnClick(R.id.back)
    public void back() {
        finish();
    }

    @OnClick(R.id.login)
    public void login() {
        if (TextUtils.isEmpty(mUsername.getText()) || TextUtils.isEmpty(mPassword.getText())) {
            ToastUtils.showShort(mContext.getString(R.string.complete_info));
            return;
        }
        startAnim();
        mPresenter.login(mUsername.getText().toString(), mPassword.getText().toString());
    }

    @OnClick(R.id.go_register)
    public void register() {
        Intent intent = new Intent(this, RegisterActivity.class);
        if (!TextUtils.isEmpty(mReferrer)) {
            intent.putExtra(ConstantValue.EXTRA_KEY_REFERRER, mReferrer);
        }
        startActivity(intent);
        finish();
    }

    private void startAnim() {
        mLoading.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.loading);
        LinearInterpolator li = new LinearInterpolator();
        animation.setInterpolator(li);
        mLoading.startAnimation(animation);
    }

    private void stopAnim() {
        mLoading.setVisibility(View.GONE);
        mLoading.clearAnimation();
    }
}
