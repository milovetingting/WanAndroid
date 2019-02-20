package com.wangyz.project.api;


import com.wangyz.common.ConstantValue;
import com.wangyz.common.bean.model.Article;
import com.wangyz.common.bean.model.Collect;
import com.wangyz.common.bean.model.ProjectCategory;

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
     * 加载项目分类
     *
     * @return
     */
    @GET(ConstantValue.URL_PROJECT_CATEGORY)
    Observable<ProjectCategory> loadProjectCategory();

    /**
     * 加载项目文章列表
     *
     * @param page
     * @param cid
     * @return
     */
    @GET(ConstantValue.URL_PROJECT_ARTICLE)
    Observable<Article> loadProjectArticle(@Path("page") int page, @Query("cid") int cid);

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
