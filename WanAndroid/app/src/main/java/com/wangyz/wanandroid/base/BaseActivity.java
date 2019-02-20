package com.wangyz.wanandroid.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @param <V>
 * @param <P>
 * @author wangyz
 * Activity的基类
 */
public abstract class BaseActivity<V, P extends BasePresenter<V>> extends AppCompatActivity {

    protected P mPresenter;

    protected Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(getContentViewId());

        unbinder = ButterKnife.bind(this);

        mPresenter = createPresenter();

        mPresenter.attachView((V) this);

        init(savedInstanceState);
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
