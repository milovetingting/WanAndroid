package com.wangyz.wanandroid.interceptor;

import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.wangyz.wanandroid.ConstantValue;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author wangyz
 * @time 2019/1/24 9:19
 * @description ReadCookieInterceptor
 */
public class ReadCookieInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        Request.Builder builder = chain.request().newBuilder();
        long expire = SPUtils.getInstance(ConstantValue.CONFIG_COOKIE).getLong(ConstantValue.CONFIG_COOKIE_EXPIRE, 0);
        if (expire > System.currentTimeMillis()) {
            String cookies = SPUtils.getInstance(ConstantValue.CONFIG_COOKIE).getString(ConstantValue.KEY_USER);
            if (!TextUtils.isEmpty(cookies)) {
                for (String cookie : cookies.split(";")) {
                    builder.addHeader("Cookie", cookie);
                }
                return chain.proceed(builder.build());
            }
        }
        return response;
    }
}
