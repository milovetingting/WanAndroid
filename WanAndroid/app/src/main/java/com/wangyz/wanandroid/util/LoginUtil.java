package com.wangyz.wanandroid.util;

import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;

/**
 * @author wangyz
 * @time 2019/1/24 16:24
 * @description LoginUtil
 */
public class LoginUtil {

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    public static String getLoginUser() {
        String cookies = SPUtils.getInstance("cookie").getString("user");
        if (!TextUtils.isEmpty(cookies)) {
            for (String cookie : cookies.split(";")) {
                if (TextUtils.equals("loginUserName", cookie.split("=")[0])) {
                    if (!TextUtils.isEmpty(cookie.split("=")[1])) {
                        return cookie.split("=")[1];
                    }
                    break;
                }
            }
        }
        return "";
    }

    /**
     * 清空登录信息
     */
    public static void clearLoginInfo() {
        SPUtils.getInstance("cookie").put("user", "");
    }

    /**
     * 是否已经登录
     *
     * @return
     */
    public static boolean isLogin() {
        return !TextUtils.isEmpty(getLoginUser());
    }

}
