package com.wangyz.wanandroid.interceptor;

import com.wangyz.wanandroid.custom.DownloadResponseBody;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author wangyz
 * @time 2019/3/22 15:52
 * @description DownloadInterceptor
 */
public class DownloadInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response.newBuilder().body(new DownloadResponseBody(response.body())).build();
    }
}
