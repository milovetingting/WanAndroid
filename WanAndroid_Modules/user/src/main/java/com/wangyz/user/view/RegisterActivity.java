package com.wangyz.user.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.wangyz.common.base.BaseActivity;
import com.wangyz.common.bean.model.Register;
import com.wangyz.user.R;
import com.wangyz.user.contract.Contract;
import com.wangyz.user.presenter.RegisterActivityPresenter;

/**
 * @author wangyz
 * @time 2019/1/24 10:27
 * @description RegisterActivity
 */
public class RegisterActivity extends BaseActivity<Contract.RegisterActivityView, RegisterActivityPresenter> implements Contract.RegisterActivityView, View.OnClickListener {

    ImageView mBack;

    EditText mUsername;

    EditText mPassword;

    EditText mRepassword;

    Button mRegister;

    private Context mContext;

    @Override
    protected int getContentViewId() {
        return R.layout.user_activity_register;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();

        mBack = findViewById(R.id.back);
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mRepassword = findViewById(R.id.repassword);
        mRegister = findViewById(R.id.register);

        mBack.setOnClickListener(this);
        mRegister.setOnClickListener(this);
    }

    @Override
    protected RegisterActivityPresenter createPresenter() {
        return new RegisterActivityPresenter();
    }

    @Override
    public void onRegister(Register result) {
        LogUtils.i();
        if (result != null) {
            if (result.getErrorCode() == 0) {
                ToastUtils.showShort(mContext.getString(R.string.common_register_success));
                Intent intent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(intent);
                finish();
            } else {
                ToastUtils.showShort(result.getErrorMsg());
            }
        }
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
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.back) {
            finish();
        } else if (id == R.id.register) {
            if (TextUtils.isEmpty(mUsername.getText()) || TextUtils.isEmpty(mPassword.getText()) || TextUtils.isEmpty(mRepassword.getText())) {
                ToastUtils.showShort(mContext.getString(R.string.common_complete_info));
                return;
            }
            if (!TextUtils.equals(mPassword.getText(), mRepassword.getText())) {
                ToastUtils.showShort(mContext.getString(R.string.common_password_not_match));
                return;
            }
            mPresenter.register(mUsername.getText().toString(), mPassword.getText().toString());
        }
    }
}
