package com.wangyz.knowledge.api;

import com.wangyz.common.ConstantValue;
import com.wangyz.common.bean.model.Article;
import com.wangyz.common.bean.model.Collect;
import com.wangyz.common.bean.model.Tree;

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
     * 加载体系
     *
     * @return
     */
    @GET(ConstantValue.URL_TREE)
    Observable<Tree> loadTree();

    /**
     * 加载体系文章列表
     *
     * @param cid
     * @param page
     * @return
     */
    @GET(ConstantValue.URL_TREE_ARTICLE)
    Observable<Article> loadTreeArticle(@Path("page") int page, @Query("cid") int cid);

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
