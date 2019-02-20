package com.wangyz.knowledge.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.wangyz.common.bean.event.Event;
import com.wangyz.knowledge.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author wangyz
 * @time 2019/2/13 9:26
 * @description MainActivity
 */
public class MainActivity extends FragmentActivity {

    private FragmentManager mFragmentManager;

    private FragmentTransaction mFragmentTransaction;

    private TreeArticleFragment mTreeArticleFragment;

    private boolean mRoot = true;

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.knowledge_activity_main);
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        TreeFragment fragment = new TreeFragment();
        mFragmentTransaction.add(R.id.knowledge_main_container, fragment);
        mFragmentTransaction.commit();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_MAIN) {
            if (event.type == Event.TYPE_TREE_ARTICLE_FRAGMENT) {
                int mTreeType = Integer.valueOf(event.data);
                mTreeArticleFragment = new TreeArticleFragment();
                mTreeArticleFragment.setTreeType(mTreeType);
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.add(R.id.knowledge_main_container, mTreeArticleFragment);
                mFragmentTransaction.commit();
                mRoot = false;
            }
        }
    }

    @Override
    public boolean onKeyDown(int i, KeyEvent keyevent) {
        if (keyevent != null && keyevent.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (!mRoot) {
                mRoot = true;
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.remove(mTreeArticleFragment);
                mFragmentTransaction.commit();
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(i, keyevent);
    }
}
