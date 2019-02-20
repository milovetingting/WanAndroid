package com.wangyz.wanandroid.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.wangyz.wanandroid.base.BasePresenter;
import com.wangyz.wanandroid.bean.model.Register;
import com.wangyz.wanandroid.contract.Contract;
import com.wangyz.wanandroid.model.RegisterActivityModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wangyz
 * @time 2019/1/24 10:27
 * @description RegisterActivityPresenter
 */
public class RegisterActivityPresenter extends BasePresenter<Contract.RegisterActivityView> implements Contract.RegisterActivityPresenter {

    private Contract.RegisterActivityModel mModel;

    public RegisterActivityPresenter() {
        mModel = new RegisterActivityModel();
    }

    @Override
    public void register(String username, String password) {
        if (isViewAttached()) {
            getView().onLoading();
        } else {
            return;
        }
        mModel.register(username, password).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Register>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.i();
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(Register result) {
                LogUtils.i();
                if (isViewAttached()) {
                    getView().onLoadSuccess();
                    getView().onRegister(result);
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
