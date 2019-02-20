package com.wangyz.wx.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.wangyz.common.base.BasePresenter;
import com.wangyz.common.bean.db.Article;
import com.wangyz.common.bean.model.Collect;
import com.wangyz.wx.contract.Contract;
import com.wangyz.wx.model.WxArticleFragmentModel;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wangyz
 * @time 2019/1/22 9:53
 * @description WxArticleFragmentPresenter
 */
public class WxArticleFragmentPresenter extends BasePresenter<Contract.WxArticleFragmentView> implements Contract.WxArticleFragmentPresenter {

    private Contract.WxArticleFragmentModel mModel;

    public WxArticleFragmentPresenter() {
        mModel = new WxArticleFragmentModel();
    }

    @Override
    public void load(int authorId, int page) {
        if (isViewAttached()) {
            getView().onLoading();
        } else {
            return;
        }
        mModel.load(authorId, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Article>>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.i();
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(List<Article> list) {
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
    public void refresh(int authorId, int page) {
        if (isViewAttached()) {
            getView().onLoading();
        } else {
            return;
        }
        mModel.refresh(authorId, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Article>>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.i();
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(List<Article> list) {
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

    @Override
    public void collect(int articleId) {
        if (isViewAttached()) {
            getView().onLoading();
        } else {
            return;
        }
        mModel.collect(articleId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Collect>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.i();
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(Collect result) {
                LogUtils.i();
                if (isViewAttached()) {
                    getView().onCollect(result, articleId);
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
    public void unCollect(int articleId) {
        if (isViewAttached()) {
            getView().onLoading();
        } else {
            return;
        }
        mModel.unCollect(articleId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Collect>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.i();
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(Collect result) {
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
