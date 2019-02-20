package com.wangyz.user.view;

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

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.wangyz.common.ConstantValue;
import com.wangyz.common.base.BaseFragment;
import com.wangyz.common.bean.event.Event;
import com.wangyz.common.bean.model.Logout;
import com.wangyz.common.util.LoginUtil;
import com.wangyz.user.R;
import com.wangyz.user.contract.Contract;
import com.wangyz.user.presenter.MenuFragmentPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author wangyz
 * @time 2019/1/17 15:51
 * @description MenuFragment
 */
@Route(path = ConstantValue.ROUTE_USER)
public class MenuFragment extends BaseFragment<Contract.MenuFragmentView, MenuFragmentPresenter> implements Contract.MenuFragmentView, View.OnClickListener {

    TextView mUsername;

    Button mLogin;

    Button mLogout;

    Button mCollect;

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
        return R.layout.user_fragment_menu;
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getContext().getApplicationContext();

        mUsername = rootView.findViewById(R.id.username);
        mLogin = rootView.findViewById(R.id.login);
        mLogout = rootView.findViewById(R.id.logout);
        mCollect = rootView.findViewById(R.id.collect);
        mSetting = rootView.findViewById(R.id.settings);

        mLogin.setOnClickListener(this);
        mLogout.setOnClickListener(this);
        mCollect.setOnClickListener(this);
        mSetting.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        Intent intent;
        int id = v.getId();
        if (id == R.id.login) {
            intent = new Intent(mContext, LoginActivity.class);
            mContext.startActivity(intent);
        } else if (id == R.id.logout) {
            mPresenter.logout();
        } else if (id == R.id.collect) {
            intent = new Intent(mContext, CollectActivity.class);
            mContext.startActivity(intent);
        } else if (id == R.id.settings) {
            intent = new Intent(mContext, SettingActivity.class);
            mContext.startActivity(intent);
        }
    }
}
