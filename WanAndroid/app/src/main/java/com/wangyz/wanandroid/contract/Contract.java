package com.wangyz.wanandroid.contract;

import com.wangyz.wanandroid.bean.db.Article;
import com.wangyz.wanandroid.bean.db.Author;
import com.wangyz.wanandroid.bean.db.Banner;
import com.wangyz.wanandroid.bean.db.ProjectCategory;
import com.wangyz.wanandroid.bean.model.AddCollect;
import com.wangyz.wanandroid.bean.model.Collect;
import com.wangyz.wanandroid.bean.model.KeyWord;
import com.wangyz.wanandroid.bean.model.Login;
import com.wangyz.wanandroid.bean.model.Logout;
import com.wangyz.wanandroid.bean.model.Register;
import com.wangyz.wanandroid.bean.model.SearchResult;
import com.wangyz.wanandroid.bean.model.TreeInfo;
import com.wangyz.wanandroid.bean.model.Update;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * @author wangyz
 * @time 2019/1/17 15:34
 * @description Contract
 */
public class Contract {

    /**
     * LoadingView
     */
    public interface BaseView {

        /**
         * 加载中
         */
        void onLoading();

        /**
         * 加载成功
         */
        void onLoadSuccess();

        /**
         * 加载失败
         */
        void onLoadFailed();

    }

    /**
     * MainActivityModel
     */
    public interface MainActivityModel {

        /**
         * 检查更新
         *
         * @return
         */
        Observable<Update> checkUpdate();

        /**
         * 下载文件
         *
         * @return
         */
        Observable<ResponseBody> download();

    }

    /**
     * MainActivityView
     */
    public interface MainActivityView extends BaseView {

        /**
         * 检查更新
         *
         * @param update
         */
        void onCheckUpdate(Update update);

        /**
         * 下载文件
         */
        void onDownload();

    }

    /**
     * MainActivityPresenter
     */
    public interface MainActivityPresenter {

        /**
         * 检查更新
         */
        void checkUpdate();

        /**
         * 下载文件
         */
        void download();

    }

    /**
     * MainFragmentModel
     */
    public interface MainFragmentModel {

        /**
         * 获取Banner信息
         *
         * @return
         */
        Observable<List<Banner>> loadBanner();

        /**
         * 刷新Banner信息
         *
         * @return
         */
        Observable<List<Banner>> refreshBanner();

        /**
         * 加载文章列表
         *
         * @param page
         * @return
         */
        Observable<List<Article>> load(int page);

        /**
         * 刷新文章列表
         *
         * @param page
         * @return
         */
        Observable<List<Article>> refresh(int page);

        /**
         * 收藏文章
         *
         * @param articleId
         * @return
         */
        Observable<Collect> collect(int articleId);

        /**
         * 取消收藏文章
         *
         * @param articleId
         * @return
         */
        Observable<Collect> unCollect(int articleId);

    }

    /**
     * MainFragmentView
     */
    public interface MainFragmentView extends BaseView {

        /**
         * 获取Banner信息
         *
         * @param list
         */
        void onLoadBanner(List<Banner> list);

        /**
         * 刷新Banner信息
         *
         * @param list
         */
        void onRefreshBanner(List<Banner> list);

        /**
         * 加载文章列表
         *
         * @param list
         */
        void onLoad(List<Article> list);

        /**
         * 刷新文章列表
         *
         * @param list
         */
        void onRefresh(List<Article> list);

        /**
         * 收藏文章
         *
         * @param result
         * @param articleId
         */
        void onCollect(Collect result, int articleId);

        /**
         * 取消收藏文章
         *
         * @param result
         * @param articleId
         */
        void onUnCollect(Collect result, int articleId);

    }

    /**
     * MainFragmentPresenter
     */
    public interface MainFragmentPresenter {

        /**
         * 获取Banner信息
         */
        void loadBanner();

        /**
         * 刷新Banner
         */
        void refreshBanner();

        /**
         * 加载文章列表
         *
         * @param page
         */
        void load(int page);

        /**
         * 刷新文章列表
         *
         * @param page
         */
        void refresh(int page);

        /**
         * 收藏文章
         *
         * @param articleId
         */
        void collect(int articleId);

        /**
         * 取消收藏文章
         *
         * @param articleId
         */
        void unCollect(int articleId);

    }

    /**
     * TreeFragmentModel
     */
    public interface TreeFragmentModel {

        /**
         * 加载体系
         *
         * @return
         */
        Observable<List<TreeInfo>> load();

        /**
         * 刷新体系
         *
         * @return
         */
        Observable<List<TreeInfo>> refresh();

    }

    /**
     * TreeFragmentView
     */
    public interface TreeFragmentView extends BaseView {

        /**
         * 加载体系
         *
         * @param list
         */
        void onLoad(List<TreeInfo> list);

        /**
         * 刷新体系
         *
         * @param list
         */
        void onRefresh(List<TreeInfo> list);

    }

    /**
     * TreeFragmentPresenter
     */
    public interface TreeFragmentPresenter {

        /**
         * 加载体系
         */
        void load();

        /**
         * 刷新体系
         */
        void refresh();

    }

    /**
     * TreeArticleFragmentModel
     */
    public interface TreeArticleFragmentModel {

        /**
         * 加载体系文件列表
         *
         * @param treeType
         * @param page
         * @return
         */
        Observable<List<Article>> load(int treeType, int page);

        /**
         * 刷新体系文章列表
         *
         * @param treeType
         * @param page
         * @return
         */
        Observable<List<Article>> refresh(int treeType, int page);

        /**
         * 收藏文章
         *
         * @param articleId
         * @return
         */
        Observable<Collect> collect(int articleId);

        /**
         * 取消收藏文章
         *
         * @param articleId
         * @return
         */
        Observable<Collect> unCollect(int articleId);

    }

    /**
     * TreeArticleFragmentView
     */
    public interface TreeArticleFragmentView extends BaseView {

        /**
         * 加载体系文章列表
         *
         * @param list
         */
        void onLoad(List<Article> list);

        /**
         * 刷新体系文章列表
         *
         * @param list
         */
        void onRefresh(List<Article> list);

        /**
         * 收藏文章
         *
         * @param result
         * @param articleId
         */
        void onCollect(Collect result, int articleId);

        /**
         * 取消收藏文章
         *
         * @param result
         * @param articleId
         */
        void onUnCollect(Collect result, int articleId);

    }

    /**
     * TreeArticleFragmentPresenter
     */
    public interface TreeArticleFragmentPresenter {

        /**
         * 加载体系文件列表
         *
         * @param treeType
         * @param page
         */
        void load(int treeType, int page);

        /**
         * 刷新体系文章列表
         *
         * @param treeType
         * @param page
         */
        void refresh(int treeType, int page);

        /**
         * 收藏文章
         *
         * @param articleId
         */
        void collect(int articleId);

        /**
         * 取消收藏文章
         *
         * @param articleId
         */
        void unCollect(int articleId);

    }

    /**
     * ProjectFragmentModel
     */
    public interface ProjectFragmentModel {

        /**
         * 加载项目类别
         *
         * @return
         */
        Observable<List<ProjectCategory>> loadProject();

        /**
         * 刷新项目类别
         *
         * @return
         */
        Observable<List<ProjectCategory>> refreshProject();

    }

    /**
     * ProjectFragmentView
     */
    public interface ProjectFragmentView extends BaseView {

        /**
         * 加载项目类别
         *
         * @param list
         */
        void onLoadProject(List<ProjectCategory> list);

        /**
         * 刷新项目类别
         *
         * @param list
         */
        void onRefreshProject(List<ProjectCategory> list);

    }

    /**
     * ProjectFragmentPresenter
     */
    public interface ProjectFragmentPresenter {

        /**
         * 加载项目类别
         */
        void loadProject();

        /**
         * 刷新项目类别
         */
        void refreshProject();

    }

    /**
     * ProjectArticleFragmentModel
     */
    public interface ProjectArticleFragmentModel {

        /**
         * 加载文章列表
         *
         * @param categoryId
         * @param page
         * @return
         */
        Observable<List<Article>> load(int categoryId, int page);

        /**
         * 刷新文章列表
         *
         * @param categoryId
         * @param page
         * @return
         */
        Observable<List<Article>> refresh(int categoryId, int page);

        /**
         * 收藏文章
         *
         * @param articleId
         * @return
         */
        Observable<Collect> collect(int articleId);

        /**
         * 取消收藏文章
         *
         * @param articleId
         * @return
         */
        Observable<Collect> unCollect(int articleId);

    }

    /**
     * ProjectArticleFragmentView
     */
    public interface ProjectArticleFragmentView extends BaseView {

        /**
         * 加载文章列表
         *
         * @param list
         */
        void onLoad(List<Article> list);

        /**
         * 刷新文章列表
         *
         * @param list
         */
        void onRefresh(List<Article> list);

        /**
         * 收藏文章
         *
         * @param result
         * @param articleId
         */
        void onCollect(Collect result, int articleId);

        /**
         * 取消收藏文章
         *
         * @param result
         * @param articleId
         */
        void onUnCollect(Collect result, int articleId);

    }

    /**
     * ProjectArticleFragmentPresenter
     */
    public interface ProjectArticleFragmentPresenter {

        /**
         * 加载文章列表
         *
         * @param categoryId
         * @param page
         */
        void load(int categoryId, int page);

        /**
         * 刷新文章列表
         *
         * @param categoryId
         * @param page
         */
        void refresh(int categoryId, int page);

        /**
         * 收藏文章
         *
         * @param articleId
         */
        void collect(int articleId);

        /**
         * 取消收藏文章
         *
         * @param articleId
         */
        void unCollect(int articleId);

    }

    /**
     * WxFragmentModel
     */
    public interface WxFragmentModel {

        /**
         * 加载公众号
         *
         * @return
         */
        Observable<List<Author>> loadWx();

        /**
         * 刷新公众号
         *
         * @return
         */
        Observable<List<Author>> refreshWx();
    }

    /**
     * WxFragmentView
     */
    public interface WxFragmentView extends BaseView {

        /**
         * 加载公众号
         *
         * @param list
         */
        void onLoadWx(List<Author> list);

        /**
         * 刷新公众号
         *
         * @param list
         */
        void onRefreshWx(List<Author> list);

    }

    /**
     * WxFragmentPresenter
     */
    public interface WxFragmentPresenter {

        /**
         * 加载公众号
         */
        void loadWx();

        /**
         * 刷新公众号
         */
        void refreshWx();

    }

    /**
     * WxArticleFragmentModel
     */
    public interface WxArticleFragmentModel {

        /**
         * 加载文章列表
         *
         * @param authorId
         * @param page
         * @return
         */
        Observable<List<Article>> load(int authorId, int page);

        /**
         * 刷新文章列表
         *
         * @param authorId
         * @param page
         * @return
         */
        Observable<List<Article>> refresh(int authorId, int page);

        /**
         * 收藏文章
         *
         * @param articleId
         * @return
         */
        Observable<Collect> collect(int articleId);

        /**
         * 取消收藏文章
         *
         * @param articleId
         * @return
         */
        Observable<Collect> unCollect(int articleId);

    }

    /**
     * WxArticleFragmentView
     */
    public interface WxArticleFragmentView extends BaseView {

        /**
         * 加载文章列表
         *
         * @param list
         */
        void onLoad(List<Article> list);

        /**
         * 刷新文章列表
         *
         * @param list
         */
        void onRefresh(List<Article> list);

        /**
         * 收藏文章
         *
         * @param result
         * @param articleId
         */
        void onCollect(Collect result, int articleId);

        /**
         * 取消收藏文章
         *
         * @param result
         * @param articleId
         */
        void onUnCollect(Collect result, int articleId);

    }

    /**
     * WxArticleFragmentPresenter
     */
    public interface WxArticleFragmentPresenter {

        /**
         * 加载文章列表
         *
         * @param authorId
         * @param page
         */
        void load(int authorId, int page);

        /**
         * 刷新文章列表
         *
         * @param authorId
         * @param page
         */
        void refresh(int authorId, int page);

        /**
         * 收藏文章
         *
         * @param articleId
         */
        void collect(int articleId);

        /**
         * 取消收藏文章
         *
         * @param articleId
         */
        void unCollect(int articleId);

    }

    /**
     * MenuFragmentModel
     */
    public interface MenuFragmentModel {

        /**
         * 退出登录
         *
         * @return
         */
        Observable<Logout> logout();

    }

    /**
     * MenuFragmentView
     */
    public interface MenuFragmentView extends BaseView {

        /**
         * 退出登录
         *
         * @param result
         */
        void onLogout(Logout result);

    }

    /**
     * MenuFragmentPresenter
     */
    public interface MenuFragmentPresenter {

        /**
         * 退出登录
         */
        void logout();

    }

    /**
     * ArticleActivityModel
     */
    public interface ArticleActivityModel {

    }

    /**
     * ArticleActivityView
     */
    public interface ArticleActivityView extends BaseView {

    }

    /**
     * ArticleActivityPresenter
     */
    public interface ArticleActivityPresenter {

    }

    /**
     * CollectActivityModel
     */
    public interface CollectActivityModel {
        /**
         * 加载文章列表
         *
         * @param page
         * @return
         */
        Observable<List<com.wangyz.wanandroid.bean.db.Collect>> load(int page);

        /**
         * 刷新文章列表
         *
         * @param page
         * @return
         */
        Observable<List<com.wangyz.wanandroid.bean.db.Collect>> refresh(int page);

        /**
         * 取消收藏文章
         *
         * @param articleId
         * @param originId
         * @return
         */
        Observable<Collect> unCollect(int articleId, int originId);

        /**
         * 添加站外收藏
         *
         * @param title  标题
         * @param author 作者
         * @param link   链接
         * @return
         */
        Observable<AddCollect> addCollect(String title, String author, String link);
    }

    /**
     * CollectActivityView
     */
    public interface CollectActivityView extends BaseView {
        /**
         * 加载文章列表
         *
         * @param list
         */
        void onLoad(List<com.wangyz.wanandroid.bean.db.Collect> list);

        /**
         * 刷新文章列表
         *
         * @param list
         */
        void onRefresh(List<com.wangyz.wanandroid.bean.db.Collect> list);

        /**
         * 取消收藏文章
         *
         * @param result
         * @param articleId
         */
        void onUnCollect(Collect result, int articleId);

        /**
         * 添加站外收藏
         *
         * @param result
         */
        void onAddCollect(AddCollect result);
    }

    /**
     * CollectActivityPresenter
     */
    public interface CollectActivityPresenter {
        /**
         * 加载文章列表
         *
         * @param page
         */
        void load(int page);

        /**
         * 刷新文章列表
         *
         * @param page
         */
        void refresh(int page);

        /**
         * 取消收藏文章
         *
         * @param articleId
         * @param originId
         */
        void unCollect(int articleId, int originId);

        /**
         * 添加站外收藏
         *
         * @param title
         * @param author
         * @param link
         */
        void addCollect(String title, String author, String link);
    }

    /**
     * RegisterActivityModel
     */
    public interface RegisterActivityModel {

        /**
         * 注册
         *
         * @param username
         * @param password
         * @return
         */
        Observable<Register> register(String username, String password);

    }

    /**
     * RegisterActivityView
     */
    public interface RegisterActivityView extends BaseView {

        /**
         * 注册
         *
         * @param result
         */
        void onRegister(Register result);

    }

    /**
     * RegisterActivityPresenter
     */
    public interface RegisterActivityPresenter {

        /**
         * 注册
         *
         * @param username
         * @param password
         */
        void register(String username, String password);

    }

    /**
     * LoginActivityModel
     */
    public interface LoginActivityModel {

        /**
         * 登录
         *
         * @param username
         * @param password
         * @return
         */
        Observable<Login> login(String username, String password);

    }

    /**
     * LoginActivityView
     */
    public interface LoginActivityView extends BaseView {

        /**
         * 登录
         *
         * @param result
         */
        void onLogin(Login result);

    }

    /**
     * LoginActivityPresenter
     */
    public interface LoginActivityPresenter {

        /**
         * 登录
         *
         * @param username
         * @param password
         */
        void login(String username, String password);

    }

    /**
     * SearchActivityModel
     */
    public interface SearchActivityModel {

        /**
         * 加载热搜词
         *
         * @return
         */
        Observable<KeyWord> loadKeyWord();

    }

    /**
     * SearchActivityView
     */
    public interface SearchActivityView extends BaseView {

        /**
         * 加载热搜词
         *
         * @param result
         */
        void onLoadKeyWord(KeyWord result);

    }

    /**
     * SearchActivityPresenter
     */
    public interface SearchActivityPresenter {

        /**
         * 加载热搜词
         */
        void loadKeyWord();

    }

    /**
     * SearchResultActivityModel
     */
    public interface SearchResultActivityModel {

        /**
         * 加载搜索结果
         *
         * @param key
         * @param page
         * @return
         */
        Observable<SearchResult> search(String key, int page);

        /**
         * 收藏文章
         *
         * @param articleId
         * @return
         */
        Observable<Collect> collect(int articleId);

        /**
         * 取消收藏文章
         *
         * @param articleId
         * @return
         */
        Observable<Collect> unCollect(int articleId);

    }

    /**
     * SearchResultActivityView
     */
    public interface SearchResultActivityView extends BaseView {

        /**
         * 加载搜索结果
         *
         * @param result
         */
        void onSearch(SearchResult result);

        /**
         * 收藏文章
         *
         * @param result
         * @param articleId
         */
        void onCollect(Collect result, int articleId);

        /**
         * 取消收藏文章
         *
         * @param result
         * @param articleId
         */
        void onUnCollect(Collect result, int articleId);

    }

    /**
     * SearchResultActivityPresenter
     */
    public interface SearchResultActivityPresenter {

        /**
         * 加载搜索结果
         *
         * @param key
         */
        void search(String key, int page);

        /**
         * 收藏文章
         *
         * @param articleId
         */
        void collect(int articleId);

        /**
         * 取消收藏文章
         *
         * @param articleId
         */
        void unCollect(int articleId);

    }

    /**
     * SettingActivityModel
     */
    public interface SettingActivityModel {

    }

    /**
     * SettingActivityView
     */
    public interface SettingActivityView extends BaseView {

    }

    /**
     * SettingActivityPresenter
     */
    public interface SettingActivityPresenter {

    }

}
