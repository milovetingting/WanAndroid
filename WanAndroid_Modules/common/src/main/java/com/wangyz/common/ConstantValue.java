package com.wangyz.common;

/**
 * @author wangyz
 * @time 2019/1/17 16:12
 * @description ConstantValue
 */
public class ConstantValue {

    /**
     * 域名
     */
    public static final String URL_BASE = "https://www.wanandroid.com";

    /**
     * banner的url
     */
    public static final String URL_BANNER = "/banner/json";

    /**
     * article的url
     */
    public static final String URL_ARTICLE = "/article/list/{page}/json";

    /**
     * 体系的url
     */
    public static final String URL_TREE = "/tree/json";

    /**
     * 体系文章的url
     */
    public static final String URL_TREE_ARTICLE = "/article/list/{page}/json";

    /**
     * 项目分类的url
     */
    public static final String URL_PROJECT_CATEGORY = "/project/tree/json";

    /**
     * 项目文章的url
     */
    public static final String URL_PROJECT_ARTICLE = "/project/list/{page}/json";

    /**
     * 公众号的url
     */
    public static final String URL_WX = "/wxarticle/chapters/json";

    /**
     * 公众号文章的url
     */
    public static final String URL_WX_ARTICLE = "/wxarticle/list/{authorId}/{page}/json";

    /**
     * 注册的url
     */
    public static final String URL_REGISTER = "/user/register";

    /**
     * 登录的url
     */
    public static final String URL_LOGIN = "/user/login";

    /**
     * 退出登录的url
     */
    public static final String URL_LOGOUT = "/user/logout/json";

    /**
     * 收藏文章的url
     */
    public static final String URL_COLLECT = "/lg/collect/{id}/json";

    /**
     * 取消收藏文章的url
     */
    public static final String URL_UNCOLLECT = "/lg/uncollect_originId/{id}/json";

    /**
     * 收藏文章列表的url
     */
    public static final String URL_COLLECT_LIST = "/lg/collect/list/{page}/json";

    /**
     * 搜索热词的url
     */
    public static final String URL_HOT_KEY = "/hotkey/json";

    /**
     * 搜索的url
     */
    public static final String URL_SEARCH = "/article/query/{page}/json";

    /**
     * 反馈地址
     */
    public static final String URL_FEEDBACK = "https://github.com/milovetingting/WanAndroid/issues";

    /**
     * 每页数量
     */
    public static final int PAGE_SIZE = 20;

    /**
     * key-link
     */
    public static final String KEY_LINK = "link";

    /**
     * key-title
     */
    public static final String KEY_TITLE = "title";

    /**
     * key-keyword
     */
    public static final String KEY_KEYOWRD = "keyword";

    /**
     * 夜间模式
     */
    public static final String KEY_NIGHT_MODE = "night_mode";

    /**
     * key-user
     */
    public static final String KEY_USER = "user";

    /**
     * 设置文件的保存名称
     */
    public static final String CONFIG_SETTINGS = "settings";

    /**
     * Cookie文件的保存名称
     */
    public static final String CONFIG_COOKIE = "cookie";

    /**
     * 搜索结果正则表达式
     */
    public static final String REGEX = "<em class='highlight'>(.+)</em>";

    /**
     * 首页Fragment的路由
     */
    public static final String ROUTE_HOME = "/home/home";

    /**
     * 体系一级分类的路由
     */
    public static final String ROUTE_TREE_ROOT = "/tree/root";

    /**
     * 体系二级分类的路由
     */
    public static final String ROUTE_TREE_CHILD = "/tree/child";

    /**
     * 项目的路由
     */
    public static final String ROUTE_PROJECT = "/project/root";

    /**
     * 公众号的路由
     */
    public static final String ROUTE_WX = "/wx/root";

    /**
     * 用户的路由
     */
    public static final String ROUTE_USER = "/user/root";

    /**
     * 登录的路由
     */
    public static final String ROUTE_LOGIN = "/user/login";

    /**
     * 文章的路由
     */
    public static final String ROUTE_ARTICLE = "/article/view";

    /**
     * 搜索的路由
     */
    public static final String ROUTE_SEARCH = "/search/search";

}
