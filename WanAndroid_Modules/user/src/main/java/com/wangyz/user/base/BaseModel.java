package com.wangyz.user.base;

import com.wangyz.common.ConstantValue;
import com.wangyz.common.interceptor.ReadCookieInterceptor;
import com.wangyz.common.interceptor.WriteCookieInterceptor;
import com.wangyz.user.api.Api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author wangyz
 * @time 2019/1/22 8:50
 * @description BaseModel
 */
public class BaseModel {

    protected Retrofit mRetrofit;

    protected Api mApi;

    public BaseModel() {
        mRetrofit = new Retrofit.Builder().baseUrl(ConstantValue.URL_BASE).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build();
        mApi = mRetrofit.create(Api.class);
    }

    /**
     * 设置是否保存cookie
     *
     * @param saveCookie
     */
    public void setCookie(boolean saveCookie) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new ReadCookieInterceptor());
        builder.addInterceptor(new WriteCookieInterceptor(saveCookie));
        mRetrofit = new Retrofit.Builder().client(builder.build()).baseUrl(ConstantValue.URL_BASE).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build();
        mApi = mRetrofit.create(Api.class);
    }

}
