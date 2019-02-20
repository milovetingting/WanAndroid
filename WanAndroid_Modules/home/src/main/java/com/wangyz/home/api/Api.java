package com.wangyz.home.api;

import com.wangyz.common.ConstantValue;
import com.wangyz.common.bean.model.Banner;
import com.wangyz.common.bean.model.Article;
import com.wangyz.common.bean.model.Collect;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @author wangyz
 * @time 2019/1/17 16:11
 * @description Api
 */
public interface Api {

    /**
     * 加载Banner信息
     *
     * @return
     */
    @GET(ConstantValue.URL_BANNER)
    Observable<Banner> loadBanner();

    /**
     * 加载首页文章列表
     *
     * @param page
     * @return
     */
    @GET(ConstantValue.URL_ARTICLE)
    Observable<Article> loadArticle(@Path("page") int page);

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

}
