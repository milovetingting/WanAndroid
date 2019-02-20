package com.wangyz.info.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;
import com.wangyz.common.ConstantValue;
import com.wangyz.common.base.BaseActivity;
import com.wangyz.common.bean.event.Event;
import com.wangyz.common.custom.BottomNavigationViewHelper;
import com.wangyz.info.R;
import com.wangyz.info.contract.Contract;
import com.wangyz.info.presenter.MainActivityPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author wangyz
 * @description MainActivity
 */
@RuntimePermissions
public class MainActivity extends BaseActivity<Contract.MainActivityView, MainActivityPresenter> implements Contract.MainActivityView, View.OnClickListener {

    private DrawerLayout mDrawerLayout;

    private ImageView mMenu;

    private TextView mTitle;

    private ImageView mSearch;

    private BottomNavigationView mBottomNavigationView;

    private FragmentManager mFragmentManager;

    private FragmentTransaction mTransaction;

    private Fragment mCurrentFragment;

    private Fragment mHomeFragment;

    private Fragment mTreeFragment;

    private Fragment mTreeArticleFragment;

    private Fragment mProjectFragment;

    private Fragment mWxFragment;

    private Fragment mMenuFragment;

    private int mTreeType;

    private boolean childFragment;

    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(com.wangyz.common.R.style.common_AppTheme);
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.app_activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();

        EventBus.getDefault().register(this);

        mDrawerLayout = findViewById(R.id.drawer);
        mMenu = findViewById(R.id.menu);
        mTitle = findViewById(R.id.title);
        mSearch = findViewById(R.id.search);
        mBottomNavigationView = findViewById(R.id.bottom);

        mMenu.setOnClickListener(this);
        mSearch.setOnClickListener(this);

        MainActivityPermissionsDispatcher.requestPermissionWithPermissionCheck(this);

        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);

        mBottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.menu_bottom_home:
                    mTitle.setText(getString(R.string.common_home));
                    if (mHomeFragment == null) {
                        mHomeFragment = (Fragment) ARouter.getInstance().build(ConstantValue.ROUTE_HOME).navigation();
                    }
                    switchFragment(R.id.layout_main, mHomeFragment);
                    return true;
                case R.id.menu_bottom_system:
                    mTitle.setText(getString(R.string.common_system));
                    if (mCurrentFragment == mTreeArticleFragment) {
                        return true;
                    }
                    if (childFragment) {
                        showTreeArticle();
                        return true;
                    } else {
                        if (mTreeFragment == null) {
                            mTreeFragment = (Fragment) ARouter.getInstance().build(ConstantValue.ROUTE_TREE_ROOT).navigation();
                        }
                        childFragment = false;
                        switchFragment(R.id.layout_main, mTreeFragment);
                    }
                    return true;
                case R.id.menu_bottom_project:
                    mTitle.setText(getString(R.string.common_project));
                    if (mProjectFragment == null) {
                        mProjectFragment = (Fragment) ARouter.getInstance().build(ConstantValue.ROUTE_PROJECT).navigation();
                    }
                    switchFragment(R.id.layout_main, mProjectFragment);
                    return true;
                case R.id.menu_bottom_wx:
                    mTitle.setText(getString(R.string.common_wx));
                    if (mWxFragment == null) {
                        mWxFragment = (Fragment) ARouter.getInstance().build(ConstantValue.ROUTE_WX).navigation();
                    }
                    switchFragment(R.id.layout_main, mWxFragment);
                    return true;
                default:
                    break;
            }
            return false;
        });

        mHomeFragment = (Fragment) ARouter.getInstance().build(ConstantValue.ROUTE_HOME).navigation();

        mMenuFragment = (Fragment) ARouter.getInstance().build(ConstantValue.ROUTE_USER).navigation();

        mFragmentManager = getSupportFragmentManager();
        mTransaction = mFragmentManager.beginTransaction();

        mTransaction.add(R.id.layout_main, mHomeFragment);
        mCurrentFragment = mHomeFragment;

        mTransaction.add(R.id.layout_menu, mMenuFragment);

        mTransaction.commit();
    }

    @Override
    protected MainActivityPresenter createPresenter() {
        return new MainActivityPresenter();
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

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void requestPermission() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    private void switchFragment(int containerId, Fragment fragment) {
        if (mCurrentFragment != fragment) {
            mTransaction = mFragmentManager.beginTransaction();
            if (fragment.isAdded()) {
                mTransaction.hide(mCurrentFragment).show(fragment).commit();
            } else {
                mTransaction.hide(mCurrentFragment).add(containerId, fragment).commit();
            }
            mCurrentFragment = fragment;
        }
    }

    private void showTreeArticle() {
        mTransaction = mFragmentManager.beginTransaction();
        if (mTreeArticleFragment.isAdded()) {
            mTransaction.hide(mCurrentFragment).show(mTreeArticleFragment).commit();
        } else {
            mTransaction.hide(mCurrentFragment).add(R.id.layout_main, mTreeArticleFragment).addToBackStack("tree").commit();
        }
        mCurrentFragment = mTreeArticleFragment;
        childFragment = true;
    }

    private void removeTreeArticle() {
        mTransaction = mFragmentManager.beginTransaction();
        if (mTreeArticleFragment.isAdded()) {
            mTransaction.remove(mTreeArticleFragment);
            mTransaction.commit();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_MAIN) {
            if (event.type == Event.TYPE_TREE_ARTICLE_FRAGMENT) {
                if (mTreeArticleFragment != null) {
                    removeTreeArticle();
                }
                mTreeType = Integer.valueOf(event.data);
                Bundle bundle = new Bundle();
                bundle.putInt("type", mTreeType);
                mTreeArticleFragment = (Fragment) ARouter.getInstance().build(ConstantValue.ROUTE_TREE_CHILD).withBundle("data", bundle).navigation();
                showTreeArticle();
            } else if (event.type == Event.TYPE_CHANGE_DAY_NIGHT_MODE) {
                recreate();
            }
        }
    }

    @Override
    public boolean onKeyDown(int i, KeyEvent keyevent) {
        if (keyevent != null && keyevent.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (mCurrentFragment == mTreeArticleFragment) {
                switchFragment(R.id.layout_main, mTreeFragment);
                childFragment = false;
            } else if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
                mDrawerLayout.closeDrawer(Gravity.START);
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(i, keyevent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu:
                if (!mDrawerLayout.isDrawerOpen(Gravity.START)) {
                    mDrawerLayout.openDrawer(Gravity.START);
                } else {
                    mDrawerLayout.closeDrawer(Gravity.START);
                }
                break;
            case R.id.search:
                ARouter.getInstance().build(ConstantValue.ROUTE_SEARCH).navigation();
                break;
            default:
                break;
        }
    }
}
