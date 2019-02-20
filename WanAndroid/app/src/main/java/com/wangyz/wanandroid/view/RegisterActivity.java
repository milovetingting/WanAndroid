package com.wangyz.wanandroid.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.wangyz.wanandroid.R;
import com.wangyz.wanandroid.base.BaseActivity;
import com.wangyz.wanandroid.bean.model.Register;
import com.wangyz.wanandroid.contract.Contract;
import com.wangyz.wanandroid.presenter.RegisterActivityPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author wangyz
 * @time 2019/1/24 10:27
 * @description RegisterActivity
 */
public class RegisterActivity extends BaseActivity<Contract.RegisterActivityView, RegisterActivityPresenter> implements Contract.RegisterActivityView {

    @BindView(R.id.back)
    ImageView mBack;

    @BindView(R.id.username)
    EditText mUsername;

    @BindView(R.id.password)
    EditText mPassword;

    @BindView(R.id.repassword)
    EditText mRepassword;

    @BindView(R.id.register)
    Button mRegister;

    private Context mContext;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();
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
                ToastUtils.showShort(mContext.getString(R.string.register_success));
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

    @OnClick(R.id.back)
    public void back() {
        finish();
    }

    @OnClick(R.id.register)
    public void register() {
        if (TextUtils.isEmpty(mUsername.getText()) || TextUtils.isEmpty(mPassword.getText()) || TextUtils.isEmpty(mRepassword.getText())) {
            ToastUtils.showShort(mContext.getString(R.string.complete_info));
            return;
        }
        if (!TextUtils.equals(mPassword.getText(), mRepassword.getText())) {
            ToastUtils.showShort(mContext.getString(R.string.password_not_match));
            return;
        }
        mPresenter.register(mUsername.getText().toString(), mPassword.getText().toString());
    }
}
