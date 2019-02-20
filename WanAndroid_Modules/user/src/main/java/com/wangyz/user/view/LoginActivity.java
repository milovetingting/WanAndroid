package com.wangyz.user.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.wangyz.common.ConstantValue;
import com.wangyz.common.base.BaseActivity;
import com.wangyz.common.bean.event.Event;
import com.wangyz.common.bean.model.Login;
import com.wangyz.user.R;
import com.wangyz.user.contract.Contract;
import com.wangyz.user.presenter.LoginActivityPresenter;

import org.greenrobot.eventbus.EventBus;

/**
 * @author wangyz
 * @time 2019/1/24 11:45
 * @description LoginActivity
 */
@Route(path = ConstantValue.ROUTE_LOGIN)
public class LoginActivity extends BaseActivity<Contract.LoginActivityView, LoginActivityPresenter> implements Contract.LoginActivityView, View.OnClickListener {

    ImageView mBack;

    EditText mUsername;

    EditText mPassword;

    Button mLogin;

    Button mRegister;

    private Context mContext;

    @Override
    protected int getContentViewId() {
        return R.layout.user_activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();

        mBack = findViewById(R.id.back);
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mLogin = findViewById(R.id.login);
        mRegister = findViewById(R.id.go_register);

        mBack.setOnClickListener(this);
        mLogin.setOnClickListener(this);
        mRegister.setOnClickListener(this);

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
    }

    @Override
    public void onLogin(Login result) {
        LogUtils.i();
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
            } else {
                ToastUtils.showShort(result.getErrorMsg());
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int id = v.getId();
        if (id == R.id.back) {
            finish();
        } else if (id == R.id.login) {
            if (TextUtils.isEmpty(mUsername.getText()) || TextUtils.isEmpty(mPassword.getText())) {
                ToastUtils.showShort(mContext.getString(R.string.common_complete_info));
                return;
            }
            mPresenter.login(mUsername.getText().toString(), mPassword.getText().toString());
        } else if (id == R.id.go_register) {
            intent = new Intent(this, RegisterActivity.class);
            mContext.startActivity(intent);
            finish();
        }
    }
}
