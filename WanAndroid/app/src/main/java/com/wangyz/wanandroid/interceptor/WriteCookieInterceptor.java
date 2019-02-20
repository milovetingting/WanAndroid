package com.wangyz.wanandroid.interceptor;

import android.annotation.TargetApi;
import android.os.Build;

import com.blankj.utilcode.util.SPUtils;
import com.wangyz.wanandroid.ConstantValue;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author wangyz
 * @time 2019/1/24 9:00
 * @description SaveCookieInterceptor
 */
public class WriteCookieInterceptor implements Interceptor {

    private boolean mSaveCookie;

    public WriteCookieInterceptor(boolean saveCookie) {
        this.mSaveCookie = saveCookie;
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (mSaveCookie) {
            List<String> headers = response.headers("Set-Cookie");
            if (!headers.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                headers.stream().forEach(h -> {
                    sb.append(h).append(";");
                });
                SPUtils.getInstance(ConstantValue.CONFIG_COOKIE).put(ConstantValue.KEY_USER, sb.toString());
            }
        }
        return response;
    }
}
