package com.wangyz.wanandroid;

/**
 * @author wangyz
 * @time 2019/1/17 16:12
 * @description ConstantValue
 */
public class ConstantValue {

    /**
     * TAG
     */
    public static final String TAG = "WanAndroid";

    /**
     * github
     */
    public static final String URL_BASE_GITHUB = "https://raw.githubusercontent.com";

    /**
     * 检查更新的url
     */
    public static final String URL_UPDATE = "/milovetingting/WanAndroid/master/WanAndroid/update.json";

    /**
     * 版本更新的APK地址
     */
    public static final String URL_UPDATE_APK = "/milovetingting/WanAndroid/master/WanAndroid/app/release/app-release.apk";

    /**
     * 保存的更新APK名称
     */
    public static final String UPDATE_NAME = "update.apk";

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
     * 取消收藏文章的url(包含自己录入的内容)
     */
    public static final String URL_UNCOLLECT_INCLUDE_ADD = "/lg/uncollect/{id}/json";

    /**
     * 收藏文章列表的url
     */
    public static final String URL_COLLECT_LIST = "/lg/collect/list/{page}/json";

    /**
     * 添加站外收藏
     */
    public static final String URL_ADD_COLLECT = "/lg/collect/add/json";

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
     * key-cookie-username
     */
    public static final String KEY_USER_COOKIE = "loginUserName";

    /**
     * 设置文件的保存名称
     */
    public static final String CONFIG_SETTINGS = "settings";

    /**
     * Cookie文件的保存名称
     */
    public static final String CONFIG_COOKIE = "cookie";

    /**
     * Cookie过期时间
     */
    public static final String CONFIG_COOKIE_EXPIRE = "cookie_expire";

    /**
     * 最大有效期(ms),默认距离上次30天
     */
    public static final long TIME_MAX_EXPIRE = 30 * 24 * 3600 * 1000L;

    /**
     * 搜索结果正则表达式
     */
    public static final String REGEX = "<em class='highlight'>(.+)</em>";

    /**
     * 超时时间
     */
    public static final int DEFAULT_TIMEOUT = 15;

    /**
     * extra-key
     */
    public static final String EXTRA_KEY_REFERRER = "referrer";

    /**
     * extra-value
     */
    public static final String EXTRA_VALUE_COLLECT = "collect";

}
