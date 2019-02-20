package com.wangyz.search.model;

import com.wangyz.common.bean.model.Collect;
import com.wangyz.common.bean.model.SearchResult;
import com.wangyz.search.base.BaseModel;
import com.wangyz.search.contract.Contract;

import io.reactivex.Observable;

/**
 * @author wangyz
 * @time 2019/1/28 17:17
 * @description SearchResultActivityModel
 */
public class SearchResultActivityModel extends BaseModel implements Contract.SearchResultActivityModel {

    public SearchResultActivityModel() {
        setCookie(false);
    }

    @Override
    public Observable<SearchResult> search(String key, int page) {
        return mApi.search(page, key);
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
