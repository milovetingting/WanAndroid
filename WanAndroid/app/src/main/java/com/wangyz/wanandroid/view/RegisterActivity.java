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

    @BindView(R.id.loading)
    ImageView mLoading;

    private Context mContext;

    private String mReferrer;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_register;
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
    protected RegisterActivityPresenter createPresenter() {
        return new RegisterActivityPresenter();
    }

    @Override
    public void onRegister(Register result) {
        LogUtils.i();
        stopAnim();
        if (result != null) {
            if (result.getErrorCode() == 0) {
                ToastUtils.showShort(mContext.getString(R.string.register_success));
                Intent intent = new Intent(mContext, LoginActivity.class);
                if (!TextUtils.isEmpty(mReferrer)) {
                    intent.putExtra(ConstantValue.EXTRA_KEY_REFERRER, mReferrer);
                }
                startActivity(intent);
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
        stopAnim();
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
        startAnim();
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
