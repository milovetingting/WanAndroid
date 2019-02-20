package com.wangyz.home.model;

import android.annotation.TargetApi;
import android.os.Build;

import com.blankj.utilcode.util.NetworkUtils;
import com.wangyz.common.ConstantValue;
import com.wangyz.home.base.BaseModel;
import com.wangyz.common.bean.db.Article;
import com.wangyz.common.bean.db.Banner;
import com.wangyz.common.bean.model.Collect;
import com.wangyz.home.contract.Contract;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * @author wangyz
 * @time 2019/1/17 15:37
 * @description MainFragmentModel
 */
public class HomeFragmentModel extends BaseModel implements Contract.HomeFragmentModel {

    public HomeFragmentModel() {
        setCookie(false);
    }

    @Override
    public Observable<List<Banner>> loadBanner() {
        Observable<List<Banner>> loadFromLocal = Observable.create(emitter -> {
            List<Banner> list = LitePal.findAll(Banner.class);
            emitter.onNext(list);
            emitter.onComplete();
        });
        if (NetworkUtils.isConnected()) {
            Observable<List<Banner>> loadFromNet = doLoadBannerFromNet();
            return Observable.concat(loadFromLocal, loadFromNet);
        } else {
            return loadFromLocal;
        }
    }

    @Override
    public Observable<List<Banner>> refreshBanner() {
        return doLoadBannerFromNet();
    }

    @Override
    public Observable<List<Article>> load(int page) {
        Observable<List<Article>> loadFromLocal = Observable.create(emitter -> {
            List<Article> list = LitePal.where("type=?", Article.TYPE_HOME + "").order("time desc").offset(page * ConstantValue.PAGE_SIZE).limit(ConstantValue.PAGE_SIZE).find(Article.class);
            emitter.onNext(list);
            emitter.onComplete();
        });
        if (NetworkUtils.isConnected()) {
            Observable<List<Article>> loadFromNet = doLoadArticleFromNet(page);
            return Observable.concat(loadFromLocal, loadFromNet);
        } else {
            return loadFromLocal;
        }
    }

    @Override
    public Observable<List<Article>> refresh(int page) {
        return doLoadArticleFromNet(page);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private Observable<List<Banner>> doLoadBannerFromNet() {
        return mApi.loadBanner().filter(b -> b.getErrorCode() == 0).map(b -> {
            List<Banner> list = new ArrayList<>();
            b.getData().stream().forEach(d -> {
                Banner banner = new Banner();
                banner.title = d.getTitle();
                banner.imagePath = d.getImagePath();
                banner.url = d.getUrl();
                list.add(banner);
            });
            if (list.size() > 0) {
                LitePal.deleteAll(Banner.class);
            }
            LitePal.saveAll(list);
            return list;
        });
    }

    @TargetApi(Build.VERSION_CODES.N)
    private Observable<List<Article>> doLoadArticleFromNet(int page) {
        return mApi.loadArticle(page).filter(a -> a.getErrorCode() == 0).map(a -> {
            List<Article> all = LitePal.where("type=?", Article.TYPE_HOME + "").find(Article.class);
            List<Article> list = new ArrayList<>();
            a.getData().getDatas().stream().forEach(d -> {
                long count = all.stream().filter(m -> m.articleId == d.getId()).count();
                if (count <= 0) {
                    Article article = new Article();
                    article.type = Article.TYPE_HOME;
                    article.articleId = d.getId();
                    article.title = d.getTitle();
                    article.author = d.getAuthor();
                    article.category = d.getSuperChapterName() + "/" + d.getChapterName();
                    article.time = d.getPublishTime();
                    article.link = d.getLink();
                    article.collect = d.isCollect();
                    list.add(article);
                } else {
                    all.stream().filter(m -> m.articleId == d.getId()).forEach(m -> {
                        if (m.time != d.getPublishTime() || m.collect != d.isCollect()) {
                            m.title = d.getTitle();
                            m.author = d.getAuthor();
                            m.category = d.getSuperChapterName() + "/" + d.getChapterName();
                            m.time = d.getPublishTime();
                            m.link = d.getLink();
                            m.collect = d.isCollect();
                            if (!m.collect) {
                                m.setToDefault("collect");
                            }
                            m.update(m.id);
                        }
                    });
                }
            });
            LitePal.saveAll(list);
            List<Article> result = LitePal.where("type=?", Article.TYPE_HOME + "").order("time desc").offset(page * ConstantValue.PAGE_SIZE).limit(ConstantValue.PAGE_SIZE).find(Article.class);
            return result;
        });
    }

    @Override
    public Observable<Collect> collect(int articleId) {
        return mApi.collect(articleId);
    }

    @Override
    public Observable<Collect> unCollect(int articleId) {
        return mApi.unCollect(articleId);
    }
}
