package com.wangyz.wanandroid.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.wangyz.wanandroid.ConstantValue;
import com.wangyz.wanandroid.base.BasePresenter;
import com.wangyz.wanandroid.bean.model.Update;
import com.wangyz.wanandroid.contract.Contract;
import com.wangyz.wanandroid.model.MainActivityModel;
import com.wangyz.wanandroid.util.FileUtil;

import java.io.File;
import java.io.InputStream;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wangyz
 * @time 2019/1/17 15:37
 * @description MainActivityPresenter
 */
public class MainActivityPresenter extends BasePresenter<Contract.MainActivityView> implements Contract.MainActivityPresenter {

    private Contract.MainActivityModel mModel;

    public MainActivityPresenter() {
        mModel = new MainActivityModel(ConstantValue.URL_BASE_GITHUB);
    }

    @Override
    public void checkUpdate() {
        if (isViewAttached()) {
            getView().onLoading();
        } else {
            return;
        }
        mModel.checkUpdate().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Update>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.i();
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(Update update) {
                LogUtils.i();
                if (isViewAttached()) {
                    getView().onLoadSuccess();
                    getView().onCheckUpdate(update);
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
    public void download() {
        if (isViewAttached()) {
            getView().onLoading();
        } else {
            return;
        }
        mModel.download().subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io()).map(responseBody -> responseBody.byteStream()).observeOn(Schedulers.computation()).doOnNext(inputStream -> FileUtil.writeFile(inputStream, new File(Utils.getApp().getExternalFilesDir(null).getAbsolutePath(), ConstantValue.UPDATE_NAME))).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InputStream>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.i();
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(InputStream inputStream) {
                        LogUtils.i();
                        if (isViewAttached()) {
                            getView().onLoadSuccess();
                            getView().onDownload();
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
}
