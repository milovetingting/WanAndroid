package com.wangyz.user.api;

import com.wangyz.common.ConstantValue;
import com.wangyz.common.bean.model.Collect;
import com.wangyz.common.bean.model.CollectInfo;
import com.wangyz.common.bean.model.Login;
import com.wangyz.common.bean.model.Logout;
import com.wangyz.common.bean.model.Register;

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

}
