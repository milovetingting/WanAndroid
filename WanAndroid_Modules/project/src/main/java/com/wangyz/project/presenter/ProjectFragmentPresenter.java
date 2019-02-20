package com.wangyz.project.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.wangyz.common.base.BasePresenter;
import com.wangyz.common.bean.db.ProjectCategory;
import com.wangyz.project.contract.Contract;
import com.wangyz.project.model.ProjectFragmentModel;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wangyz
 * @time 2019/1/22 14:40
 * @description ProjectFragmentPresenter
 */
public class ProjectFragmentPresenter extends BasePresenter<Contract.ProjectFragmentView> implements Contract.ProjectFragmentPresenter {

    private Contract.ProjectFragmentModel mModel;

    public ProjectFragmentPresenter() {
        mModel = new ProjectFragmentModel();
    }

    @Override
    public void loadProject() {
        if (isViewAttached()) {
            getView().onLoading();
        } else {
            return;
        }
        mModel.loadProject().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<ProjectCategory>>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.i();
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(List<ProjectCategory> list) {
                LogUtils.i();
                if (isViewAttached()) {
                    getView().onLoadProject(list);
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
    public void refreshProject() {
        if (isViewAttached()) {
            getView().onLoading();
        } else {
            return;
        }
        mModel.refreshProject().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<ProjectCategory>>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.i();
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(List<ProjectCategory> list) {
                LogUtils.i();
                if (isViewAttached()) {
                    getView().onRefreshProject(list);
                    getView().onLoadSuccess();
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e(e.getMessage());
                loadProject();
            }

            @Override
            public void onComplete() {
                LogUtils.i();
            }
        });
    }
}
