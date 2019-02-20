package com.wangyz.wx.contract;

import com.wangyz.common.bean.db.Article;
import com.wangyz.common.bean.db.Author;
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
     * WxFragmentModel
     */
    public interface WxFragmentModel {

        /**
         * 加载公众号
         *
         * @return
         */
        Observable<List<Author>> loadWx();

        /**
         * 刷新公众号
         *
         * @return
         */
        Observable<List<Author>> refreshWx();
    }

    /**
     * WxFragmentView
     */
    public interface WxFragmentView extends com.wangyz.common.contract.Contract.BaseView {

        /**
         * 加载公众号
         *
         * @param list
         */
        void onLoadWx(List<Author> list);

        /**
         * 刷新公众号
         *
         * @param list
         */
        void onRefreshWx(List<Author> list);

    }

    /**
     * WxFragmentPresenter
     */
    public interface WxFragmentPresenter {

        /**
         * 加载公众号
         */
        void loadWx();

        /**
         * 刷新公众号
         */
        void refreshWx();

    }

    /**
     * WxArticleFragmentModel
     */
    public interface WxArticleFragmentModel {

        /**
         * 加载文章列表
         *
         * @param authorId
         * @param page
         * @return
         */
        Observable<List<Article>> load(int authorId, int page);

        /**
         * 刷新文章列表
         *
         * @param authorId
         * @param page
         * @return
         */
        Observable<List<Article>> refresh(int authorId, int page);

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
     * WxArticleFragmentView
     */
    public interface WxArticleFragmentView extends com.wangyz.common.contract.Contract.BaseView {

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
     * WxArticleFragmentPresenter
     */
    public interface WxArticleFragmentPresenter {

        /**
         * 加载文章列表
         *
         * @param authorId
         * @param page
         */
        void load(int authorId, int page);

        /**
         * 刷新文章列表
         *
         * @param authorId
         * @param page
         */
        void refresh(int authorId, int page);

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
