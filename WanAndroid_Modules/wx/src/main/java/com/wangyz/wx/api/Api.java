package com.wangyz.wx.api;

import com.wangyz.common.ConstantValue;
import com.wangyz.common.bean.model.Article;
import com.wangyz.common.bean.model.Author;
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
     * 加载公众号列表
     *
     * @return
     */
    @GET(ConstantValue.URL_WX)
    Observable<Author> loadWx();

    /**
     * 加载公众号文章列表
     *
     * @param authorId
     * @param page
     * @return
     */
    @GET(ConstantValue.URL_WX_ARTICLE)
    Observable<Article> loadWxArticle(@Path("authorId") int authorId, @Path("page") int page);

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
