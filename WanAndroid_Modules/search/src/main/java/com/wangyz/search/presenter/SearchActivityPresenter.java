package com.wangyz.search.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.wangyz.common.base.BasePresenter;
import com.wangyz.common.bean.model.KeyWord;
import com.wangyz.search.contract.Contract;
import com.wangyz.search.model.SearchActivityModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wangyz
 * @time 2019/1/28 16:45
 * @description SearchActivityPresenter
 */
public class SearchActivityPresenter extends BasePresenter<Contract.SearchActivityView> implements Contract.SearchActivityPresenter {

    private Contract.SearchActivityModel mModel;

    public SearchActivityPresenter() {
        mModel = new SearchActivityModel();
    }

    @Override
    public void loadKeyWord() {
        if (isViewAttached()) {
            getView().onLoading();
        } else {
            return;
        }
        mModel.loadKeyWord().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<KeyWord>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.i();
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(KeyWord result) {
                LogUtils.i();
                if (isViewAttached()) {
                    getView().onLoadKeyWord(result);
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
}
