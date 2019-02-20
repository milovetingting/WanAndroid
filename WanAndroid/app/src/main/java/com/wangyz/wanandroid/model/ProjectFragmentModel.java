package com.wangyz.wanandroid.model;

import android.annotation.TargetApi;
import android.os.Build;

import com.wangyz.wanandroid.base.BaseModel;
import com.wangyz.wanandroid.bean.db.ProjectCategory;
import com.wangyz.wanandroid.contract.Contract;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * @author wangyz
 * @time 2019/1/22 14:26
 * @description ProjectFragmentModel
 */
public class ProjectFragmentModel extends BaseModel implements Contract.ProjectFragmentModel {

    @Override
    public Observable<List<ProjectCategory>> loadProject() {
        Observable<List<ProjectCategory>> loadFromLocal = Observable.create(emitter -> {
            List<ProjectCategory> list = LitePal.findAll(ProjectCategory.class);
            if (list != null && list.size() > 0) {
                emitter.onNext(list);
            } else {
                emitter.onComplete();
            }
        });
        Observable<List<ProjectCategory>> loadFromNet = doLoadProjectFromNet();
        return Observable.concat(loadFromLocal, loadFromNet);
    }

    @Override
    public Observable<List<ProjectCategory>> refreshProject() {
        return doLoadProjectFromNet();
    }

    @TargetApi(Build.VERSION_CODES.N)
    private Observable<List<ProjectCategory>> doLoadProjectFromNet() {
        return mApi.loadProjectCategory().filter(a -> a.getErrorCode() == 0).map(a -> {
            List<ProjectCategory> list = new ArrayList<>();
            a.getData().stream().forEach(d -> {
                ProjectCategory category = new ProjectCategory();
                category.categoryId = d.getId();
                category.name = d.getName();
                list.add(category);
            });
            if (list.size() > 0) {
                LitePal.deleteAll(ProjectCategory.class);
            }
            LitePal.saveAll(list);
            return list;
        });
    }
}
