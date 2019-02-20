package com.wangyz.search.contract;

import com.wangyz.common.bean.model.Collect;
import com.wangyz.common.bean.model.KeyWord;
import com.wangyz.common.bean.model.SearchResult;

import io.reactivex.Observable;

/**
 * @author wangyz
 * @time 2019/1/17 15:34
 * @description Contract
 */
public class Contract {

    /**
     * SearchActivityModel
     */
    public interface SearchActivityModel {

        /**
         * 加载热搜词
         *
         * @return
         */
        Observable<KeyWord> loadKeyWord();

    }

    /**
     * SearchActivityView
     */
    public interface SearchActivityView extends com.wangyz.common.contract.Contract.BaseView {

        /**
         * 加载热搜词
         *
         * @param result
         */
        void onLoadKeyWord(KeyWord result);

    }

    /**
     * SearchActivityPresenter
     */
    public interface SearchActivityPresenter {

        /**
         * 加载热搜词
         */
        void loadKeyWord();

    }

    /**
     * SearchResultActivityModel
     */
    public interface SearchResultActivityModel {

        /**
         * 加载搜索结果
         *
         * @param key
         * @param page
         * @return
         */
        Observable<SearchResult> search(String key, int page);

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
     * SearchResultActivityView
     */
    public interface SearchResultActivityView extends com.wangyz.common.contract.Contract.BaseView {

        /**
         * 加载搜索结果
         *
         * @param result
         */
        void onSearch(SearchResult result);

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
     * SearchResultActivityPresenter
     */
    public interface SearchResultActivityPresenter {

        /**
         * 加载搜索结果
         *
         * @param key
         */
        void search(String key, int page);

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
