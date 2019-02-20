package com.wangyz.user.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.wangyz.common.base.BasePresenter;
import com.wangyz.common.bean.model.Login;
import com.wangyz.user.contract.Contract;
import com.wangyz.user.model.LoginActivityModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wangyz
 * @time 2019/1/24 11:43
 * @description LoginActivityPresenter
 */
public class LoginActivityPresenter extends BasePresenter<Contract.LoginActivityView> implements Contract.LoginActivityPresenter {

    private Contract.LoginActivityModel mModel;

    public LoginActivityPresenter() {
        mModel = new LoginActivityModel();
    }

    @Override
    public void login(String username, String password) {
        if (isViewAttached()) {
            getView().onLoading();
        } else {
            return;
        }
        mModel.login(username, password).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Login>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.i();
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(Login result) {
                LogUtils.i();
                if (isViewAttached()) {
                    getView().onLoadSuccess();
                    getView().onLogin(result);
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
