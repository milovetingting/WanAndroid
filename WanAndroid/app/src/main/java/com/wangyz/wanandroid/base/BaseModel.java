package com.wangyz.wanandroid.base;

import com.wangyz.wanandroid.ConstantValue;
import com.wangyz.wanandroid.api.Api;
import com.wangyz.wanandroid.interceptor.DownloadInterceptor;
import com.wangyz.wanandroid.interceptor.ReadCookieInterceptor;
import com.wangyz.wanandroid.interceptor.WriteCookieInterceptor;

import java.util.concurrent.TimeUnit;

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
     * 设置Retrofit
     *
     * @param baseUrl BaseUrl
     */
    public void initRetrofit(String baseUrl) {
        mRetrofit = new Retrofit.Builder().baseUrl(baseUrl).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build();
        mApi = mRetrofit.create(Api.class);
    }

    /**
     * 设置下载相关
     */
    public void setDownload() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new DownloadInterceptor())
                .retryOnConnectionFailure(true)
                .connectTimeout(ConstantValue.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        mRetrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(ConstantValue.URL_BASE_GITHUB)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

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
