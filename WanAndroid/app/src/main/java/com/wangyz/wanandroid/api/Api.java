package com.wangyz.wanandroid.api;

import com.wangyz.wanandroid.ConstantValue;
import com.wangyz.wanandroid.bean.model.Article;
import com.wangyz.wanandroid.bean.model.Author;
import com.wangyz.wanandroid.bean.model.Banner;
import com.wangyz.wanandroid.bean.model.Collect;
import com.wangyz.wanandroid.bean.model.CollectInfo;
import com.wangyz.wanandroid.bean.model.KeyWord;
import com.wangyz.wanandroid.bean.model.Login;
import com.wangyz.wanandroid.bean.model.Logout;
import com.wangyz.wanandroid.bean.model.ProjectCategory;
import com.wangyz.wanandroid.bean.model.Register;
import com.wangyz.wanandroid.bean.model.SearchResult;
import com.wangyz.wanandroid.bean.model.Tree;
import com.wangyz.wanandroid.bean.model.Update;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author wangyz
 * @time 2019/1/17 16:11
 * @description Api
 */
public interface Api {

    /**
     * 检查更新
     *
     * @return
     */
    @GET(ConstantValue.URL_UPDATE)
    Observable<Update> checkUpdate();

    /**
     * 下载文件
     *
     * @param url
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

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
     * 注册
     *
     * @param username
     * @param password
     * @param repassword
     * @return
     */
    @POST(ConstantValue.URL_REGISTER)
    Observable<Register> register(@Query("username") String username, @Query("password") String password, @Query("repassword") String repassword);

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    @POST(ConstantValue.URL_LOGIN)
    Observable<Login> login(@Query("username") String username, @Query("password") String password);

    /**
     * 退出登录
     *
     * @return
     */
    @GET(ConstantValue.URL_LOGOUT)
    Observable<Logout> logout();

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
     * 获取收藏文章列表
     *
     * @param page
     * @return
     */
    @GET(ConstantValue.URL_COLLECT_LIST)
    Observable<CollectInfo> loadCollect(@Path("page") int page);

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
