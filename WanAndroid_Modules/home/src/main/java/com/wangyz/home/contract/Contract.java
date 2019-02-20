package com.wangyz.home.contract;

import com.wangyz.common.bean.db.Article;
import com.wangyz.common.bean.db.Banner;
import com.wangyz.common.bean.model.Collect;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author wangyz
 * @time 2019/1/17 15:34
 * @description Contract
 */
public class Contract {

    /**
     * MainFragmentModel
     */
    public interface HomeFragmentModel {

        /**
         * 获取Banner信息
         *
         * @return
         */
        Observable<List<Banner>> loadBanner();

        /**
         * 刷新Banner信息
         *
         * @return
         */
        Observable<List<Banner>> refreshBanner();

        /**
         * 加载文章列表
         *
         * @param page
         * @return
         */
        Observable<List<Article>> load(int page);

        /**
         * 刷新文章列表
         *
         * @param page
         * @return
         */
        Observable<List<Article>> refresh(int page);

        /**
         * 收藏文章
         *
         * @param articleId
         * @return
         */
        Observable<Collect> collect(int articleId);

        /**
         * 取消收藏文章
         *
         * @param articleId
         * @return
         */
        Observable<Collect> unCollect(int articleId);

    }

    /**
     * MainFragmentView
     */
    public interface HomeFragmentView extends com.wangyz.common.contract.Contract.BaseView {

        /**
         * 获取Banner信息
         *
         * @param list
         */
        void onLoadBanner(List<Banner> list);

        /**
         * 刷新Banner信息
         *
         * @param list
         */
        void onRefreshBanner(List<Banner> list);

        /**
         * 加载文章列表
         *
         * @param list
         */
        void onLoad(List<Article> list);

        /**
         * 刷新文章列表
         *
         * @param list
         */
        void onRefresh(List<Article> list);

        /**
         * 收藏文章
         *
         * @param result
         * @param articleId
         */
        void onCollect(Collect result, int articleId);

        /**
         * 取消收藏文章
         *
         * @param result
         * @param articleId
         */
        void onUnCollect(Collect result, int articleId);

    }

    /**
     * MainFragmentPresenter
     */
    public interface HomeFragmentPresenter {

        /**
         * 获取Banner信息
         */
        void loadBanner();

        /**
         * 刷新Banner
         */
        void refreshBanner();

        /**
         * 加载文章列表
         *
         * @param page
         */
        void load(int page);

        /**
         * 刷新文章列表
         *
         * @param page
         */
        void refresh(int page);

        /**
         * 收藏文章
         *
         * @param articleId
         */
        void collect(int articleId);

        /**
         * 取消收藏文章
         *
         * @param articleId
         */
        void unCollect(int articleId);

    }

}
