package com.wangyz.wanandroid.base;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @param <V>
 * @author wangyz
 * Presenter的基类
 */
public abstract class BasePresenter<V> {

    protected WeakReference<V> mViewRef;

    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    protected void attachView(V view) {
        mViewRef = new WeakReference<V>(view);
    }

    protected V getView() {
        return mViewRef == null ? null : mViewRef.get();
    }

    protected boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    protected void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
        mCompositeDisposable.clear();
    }

}
