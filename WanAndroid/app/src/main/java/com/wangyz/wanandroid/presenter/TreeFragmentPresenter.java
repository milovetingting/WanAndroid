package com.wangyz.wanandroid.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.wangyz.wanandroid.base.BasePresenter;
import com.wangyz.wanandroid.bean.model.TreeInfo;
import com.wangyz.wanandroid.contract.Contract;
import com.wangyz.wanandroid.model.TreeFragmentModel;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wangyz
 * @time 2019/1/23 9:42
 * @description TreeFragmentPresenter
 */
public class TreeFragmentPresenter extends BasePresenter<Contract.TreeFragmentView> implements Contract.TreeFragmentPresenter {

    private Contract.TreeFragmentModel mModel;

    public TreeFragmentPresenter() {
        mModel = new TreeFragmentModel();
    }

    @Override
    public void load() {
        if (isViewAttached()) {
            getView().onLoading();
        } else {
            return;
        }
        mModel.load().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<TreeInfo>>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.i();
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(List<TreeInfo> list) {
                LogUtils.i();
                if (isViewAttached()) {
                    getView().onLoad(list);
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
    public void refresh() {
        if (isViewAttached()) {
            getView().onLoading();
        } else {
            return;
        }
        mModel.refresh().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<TreeInfo>>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.i();
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(List<TreeInfo> list) {
                LogUtils.i();
                if (isViewAttached()) {
                    getView().onRefresh(list);
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
