package com.wangyz.wanandroid.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.wangyz.wanandroid.base.BasePresenter;
import com.wangyz.wanandroid.bean.db.Author;
import com.wangyz.wanandroid.contract.Contract;
import com.wangyz.wanandroid.model.WxFragmentModel;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wangyz
 * @time 2019/1/21 17:22
 * @description WxFragmentPresenter
 */
public class WxFragmentPresenter extends BasePresenter<Contract.WxFragmentView> implements Contract.WxFragmentPresenter {

    private Contract.WxFragmentModel mModel;

    public WxFragmentPresenter() {
        mModel = new WxFragmentModel();
    }

    @Override
    public void loadWx() {
        if (isViewAttached()) {
            getView().onLoading();
        } else {
            return;
        }
        mModel.loadWx().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Author>>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.i();
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(List<Author> list) {
                LogUtils.i();
                if (isViewAttached()) {
                    getView().onLoadWx(list);
                    getView().onLoadSuccess();
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e(e.getMessage());
                if (isViewAttached()) {
                    getView().onLoadFailed();
                }
            }

            @Override
            public void onComplete() {
                LogUtils.i();
            }
        });
    }

    @Override
    public void refreshWx() {
        if (isViewAttached()) {
            getView().onLoading();
        } else {
            return;
        }
        mModel.refreshWx().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Author>>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.i();
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(List<Author> list) {
                LogUtils.i();
                if (isViewAttached()) {
                    getView().onRefreshWx(list);
                    getView().onLoadSuccess();
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e(e.getMessage());
                loadWx();
            }

            @Override
            public void onComplete() {
                LogUtils.i();
            }
        });
    }
}
