package com.wangyz.project.contract;

import com.wangyz.common.bean.db.Article;
import com.wangyz.common.bean.model.Collect;
import com.wangyz.common.bean.db.ProjectCategory;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author wangyz
 * @time 2019/1/17 15:34
 * @description Contract
 */
public class Contract {

    /**
     * ProjectFragmentModel
     */
    public interface ProjectFragmentModel {

        /**
         * 加载项目类别
         *
         * @return
         */
        Observable<List<ProjectCategory>> loadProject();

        /**
         * 刷新项目类别
         *
         * @return
         */
        Observable<List<ProjectCategory>> refreshProject();

    }

    /**
     * ProjectFragmentView
     */
    public interface ProjectFragmentView extends com.wangyz.common.contract.Contract.BaseView {

        /**
         * 加载项目类别
         *
         * @param list
         */
        void onLoadProject(List<ProjectCategory> list);

        /**
         * 刷新项目类别
         *
         * @param list
         */
        void onRefreshProject(List<ProjectCategory> list);

    }

    /**
     * ProjectFragmentPresenter
     */
    public interface ProjectFragmentPresenter {

        /**
         * 加载项目类别
         */
        void loadProject();

        /**
         * 刷新项目类别
         */
        void refreshProject();

    }

    /**
     * ProjectArticleFragmentModel
     */
    public interface ProjectArticleFragmentModel {

        /**
         * 加载文章列表
         *
         * @param categoryId
         * @param page
         * @return
         */
        Observable<List<Article>> load(int categoryId, int page);

        /**
         * 刷新文章列表
         *
         * @param categoryId
         * @param page
         * @return
         */
        Observable<List<Article>> refresh(int categoryId, int page);

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
     * ProjectArticleFragmentView
     */
    public interface ProjectArticleFragmentView extends com.wangyz.common.contract.Contract.BaseView {

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
     * ProjectArticleFragmentPresenter
     */
    public interface ProjectArticleFragmentPresenter {

        /**
         * 加载文章列表
         *
         * @param categoryId
         * @param page
         */
        void load(int categoryId, int page);

        /**
         * 刷新文章列表
         *
         * @param categoryId
         * @param page
         */
        void refresh(int categoryId, int page);

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
