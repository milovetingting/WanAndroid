package com.wangyz.knowledge.contract;

import com.wangyz.common.bean.db.Article;
import com.wangyz.common.bean.model.Collect;
import com.wangyz.common.bean.model.TreeInfo;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author wangyz
 * @time 2019/1/17 15:34
 * @description Contract
 */
public class Contract {

    /**
     * TreeFragmentModel
     */
    public interface TreeFragmentModel {

        /**
         * 加载体系
         *
         * @return
         */
        Observable<List<TreeInfo>> load();

        /**
         * 刷新体系
         *
         * @return
         */
        Observable<List<TreeInfo>> refresh();

    }

    /**
     * TreeFragmentView
     */
    public interface TreeFragmentView extends com.wangyz.common.contract.Contract.BaseView {

        /**
         * 加载体系
         *
         * @param list
         */
        void onLoad(List<TreeInfo> list);

        /**
         * 刷新体系
         *
         * @param list
         */
        void onRefresh(List<TreeInfo> list);

    }

    /**
     * TreeFragmentPresenter
     */
    public interface TreeFragmentPresenter {

        /**
         * 加载体系
         */
        void load();

        /**
         * 刷新体系
         */
        void refresh();

    }

    /**
     * TreeArticleFragmentModel
     */
    public interface TreeArticleFragmentModel {

        /**
         * 加载体系文件列表
         *
         * @param treeType
         * @param page
         * @return
         */
        Observable<List<Article>> load(int treeType, int page);

        /**
         * 刷新体系文章列表
         *
         * @param treeType
         * @param page
         * @return
         */
        Observable<List<Article>> refresh(int treeType, int page);

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
     * TreeArticleFragmentView
     */
    public interface TreeArticleFragmentView extends com.wangyz.common.contract.Contract.BaseView {

        /**
         * 加载体系文章列表
         *
         * @param list
         */
        void onLoad(List<Article> list);

        /**
         * 刷新体系文章列表
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
     * TreeArticleFragmentPresenter
     */
    public interface TreeArticleFragmentPresenter {

        /**
         * 加载体系文件列表
         *
         * @param treeType
         * @param page
         */
        void load(int treeType, int page);

        /**
         * 刷新体系文章列表
         *
         * @param treeType
         * @param page
         */
        void refresh(int treeType, int page);

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
