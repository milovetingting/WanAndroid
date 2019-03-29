package com.wangyz.wanandroid.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.wangyz.wanandroid.R;
import com.wangyz.wanandroid.base.BaseFragment;
import com.wangyz.wanandroid.bean.event.Event;
import com.wangyz.wanandroid.bean.model.Logout;
import com.wangyz.wanandroid.contract.Contract;
import com.wangyz.wanandroid.presenter.MenuFragmentPresenter;
import com.wangyz.wanandroid.util.LoginUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author wangyz
 * @time 2019/1/17 15:51
 * @description MenuFragment
 */
public class MenuFragment extends BaseFragment<Contract.MenuFragmentView, MenuFragmentPresenter> implements Contract.MenuFragmentView {

    @BindView(R.id.username)
    TextView mUsername;

    @BindView(R.id.login)
    Button mLogin;

    @BindView(R.id.logout)
    Button mLogout;

    @BindView(R.id.collect)
    Button mCollect;

    @BindView(R.id.settings)
    Button mSetting;

    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_menu;
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getContext().getApplicationContext();
        mLogin.setVisibility(View.VISIBLE);
        mLogout.setVisibility(View.GONE);
        String loginUser = LoginUtil.getLoginUser();
        if (!TextUtils.isEmpty(loginUser)) {
            mLogin.setVisibility(View.GONE);
            mLogout.setVisibility(View.VISIBLE);
            mUsername.setText(loginUser);
            mCollect.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected MenuFragmentPresenter createPresenter() {
        return new MenuFragmentPresenter();
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
        LogUtils.i();
    }

    @OnClick(R.id.login)
    public void login() {
        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    @OnClick(R.id.logout)
    public void logout() {
        mPresenter.logout();
    }

    @OnClick(R.id.collect)
    public void goCollectList() {
        Intent intent = new Intent(mContext, CollectActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    @OnClick(R.id.settings)
    public void setting() {
        Intent intent = new Intent(mContext, SettingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    @Override
    public void onLogout(Logout result) {
        if (result != null) {
            if (result.getErrorCode() == 0) {
                LoginUtil.clearLoginInfo();
                mUsername.setText("");
                mLogin.setVisibility(View.VISIBLE);
                mLogout.setVisibility(View.GONE);
                mCollect.setVisibility(View.GONE);

                Event event = new Event();
                event.target = Event.TARGET_HOME;
                event.type = Event.TYPE_LOGOUT;
                EventBus.getDefault().post(event);

                Event treeEvent = new Event();
                treeEvent.target = Event.TARGET_TREE;
                treeEvent.type = Event.TYPE_LOGIN;
                treeEvent.data = mUsername.getText().toString();
                EventBus.getDefault().post(treeEvent);

                Event projectEvent = new Event();
                projectEvent.target = Event.TARGET_PROJECT;
                projectEvent.type = Event.TYPE_LOGIN;
                projectEvent.data = mUsername.getText().toString();
                EventBus.getDefault().post(projectEvent);

                Event wxEvent = new Event();
                wxEvent.target = Event.TARGET_WX;
                wxEvent.type = Event.TYPE_LOGIN;
                wxEvent.data = mUsername.getText().toString();
                EventBus.getDefault().post(wxEvent);

            } else {
                ToastUtils.showShort(result.getErrorMsg());
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_MENU) {
            if (event.type == Event.TYPE_LOGIN) {
                mLogin.setVisibility(View.GONE);
                mLogout.setVisibility(View.VISIBLE);
                mUsername.setText(event.data);
                mCollect.setVisibility(View.VISIBLE);
            }
        }
    }
}
