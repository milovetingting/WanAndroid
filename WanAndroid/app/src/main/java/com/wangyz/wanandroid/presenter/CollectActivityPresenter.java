package com.wangyz.wanandroid.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.wangyz.wanandroid.base.BasePresenter;
import com.wangyz.wanandroid.bean.db.Collect;
import com.wangyz.wanandroid.contract.Contract;
import com.wangyz.wanandroid.model.CollectActivityModel;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wangyz
 * @time 2019/1/28 14:18
 * @description CollectActivityPresenter
 */
public class CollectActivityPresenter extends BasePresenter<Contract.CollectActivityView> implements Contract.CollectActivityPresenter {

    private Contract.CollectActivityModel mModel;

    public CollectActivityPresenter() {
        mModel = new CollectActivityModel();
    }

    @Override
    public void load(int page) {
        if (isViewAttached()) {
            getView().onLoading();
        } else {
            return;
        }
        mModel.load(page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Collect>>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.i();
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(List<Collect> list) {
                LogUtils.i();
                if (isViewAttached()) {
                    getView().onLoad(list);
                    getView().onLoadSuccess();
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e(e.getMessage());
            }

            @Override
            public void onComplete() {
                LogUtils.i();
            }
        });
    }

    @Override
    public void refresh(int page) {
        if (isViewAttached()) {
            getView().onLoading();
        } else {
            return;
        }
        mModel.refresh(page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Collect>>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.i();
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(List<Collect> list) {
                LogUtils.i();
                if (isViewAttached()) {
                    getView().onRefresh(list);
                    getView().onLoadSuccess();
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e(e.getMessage());
            }

            @Override
            public void onComplete() {
                LogUtils.i();
            }
        });
    }

    @Override
    public void unCollect(int articleId) {
        if (isViewAttached()) {
            getView().onLoading();
        } else {
            return;
        }

        mModel.unCollect(articleId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<com.wangyz.wanandroid.bean.model.Collect>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.i();
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(com.wangyz.wanandroid.bean.model.Collect result) {
                LogUtils.i();
                if (isViewAttached()) {
                    getView().onUnCollect(result, articleId);
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
}
