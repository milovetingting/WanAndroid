package com.wangyz.wanandroid.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @param <V>
 * @param <P>
 * @author wangyz
 * Fragment的基类
 */
public abstract class BaseFragment<V, P extends BasePresenter<V>> extends Fragment {

    protected View rootView;

    protected Context context;

    protected P mPresenter;

    protected Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getContentViewId(), container, false);
        }
        unbinder = ButterKnife.bind(this, rootView);
        context = getActivity();
        init(savedInstanceState);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mPresenter.detachView();
    }

    /**
     * 设置布局
     *
     * @return
     */
    protected abstract int getContentViewId();

    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    protected abstract void init(Bundle savedInstanceState);

    /**
     * 创建Presenter
     *
     * @return
     */
    protected abstract P createPresenter();

}
