package com.wangyz.wanandroid.model;

import android.annotation.TargetApi;
import android.os.Build;

import com.wangyz.wanandroid.base.BaseModel;
import com.wangyz.wanandroid.bean.db.Collect;
import com.wangyz.wanandroid.bean.model.AddCollect;
import com.wangyz.wanandroid.contract.Contract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * @author wangyz
 * @time 2019/1/28 14:18
 * @description CollectActivityModel
 */
public class CollectActivityModel extends BaseModel implements Contract.CollectActivityModel {

    public CollectActivityModel() {
        setCookie(false);
    }

    @Override
    public Observable<List<Collect>> load(int page) {
        return doLoadArticleFromNet(page);
    }

    @Override
    public Observable<List<Collect>> refresh(int page) {
        return doLoadArticleFromNet(page);
    }

    @Override
    public Observable<com.wangyz.wanandroid.bean.model.Collect> unCollect(int articleId, int originId) {
        return mApi.unCollect(articleId, originId);
    }

    @Override
    public Observable<AddCollect> addCollect(String title, String author, String link) {
        return mApi.addCollect(title, author, link);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private Observable<List<Collect>> doLoadArticleFromNet(int page) {
        return mApi.loadCollect(page).filter(a -> a.getErrorCode() == 0).map(a -> {
            List<Collect> list = new ArrayList<>();
            a.getData().getDatas().stream().forEach(d -> {
                Collect m = new Collect();
                m.articleId = d.getId();
                m.originId = d.getOriginId();
                m.author = d.getAuthor();
                m.title = d.getTitle();
                m.category = d.getChapterName();
                m.time = d.getPublishTime();
                m.link = d.getLink();
                list.add(m);
            });
            return list;
        });
    }
}
