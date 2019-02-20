package com.wangyz.search.api;

import com.wangyz.common.ConstantValue;
import com.wangyz.common.bean.model.Collect;
import com.wangyz.common.bean.model.KeyWord;
import com.wangyz.common.bean.model.SearchResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author wangyz
 * @time 2019/1/17 16:11
 * @description Api
 */
public interface Api {

    /**
     * 收藏文章
     *
     * @param id
     * @return
     */
    @POST(ConstantValue.URL_COLLECT)
    Observable<Collect> collect(@Path("id") int id);

    /**
     * 取消收藏文章
     *
     * @param id
     * @return
     */
    @POST(ConstantValue.URL_UNCOLLECT)
    Observable<Collect> unCollect(@Path("id") int id);

    /**
     * 获取搜索热词
     *
     * @return
     */
    @GET(ConstantValue.URL_HOT_KEY)
    Observable<KeyWord> loadHotKey();

    /**
     * 搜索
     *
     * @param page
     * @param kw
     * @return
     */
    @POST(ConstantValue.URL_SEARCH)
    Observable<SearchResult> search(@Path("page") int page, @Query("k") String kw);

}
