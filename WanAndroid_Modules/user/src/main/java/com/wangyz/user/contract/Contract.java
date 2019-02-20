package com.wangyz.user.contract;

import com.wangyz.common.bean.model.Collect;
import com.wangyz.common.bean.model.Login;
import com.wangyz.common.bean.model.Logout;
import com.wangyz.common.bean.model.Register;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author wangyz
 * @time 2019/1/17 15:34
 * @description Contract
 */
public class Contract {

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
    public interface MenuFragmentView extends com.wangyz.common.contract.Contract.BaseView {

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
     * CollectActivityModel
     */
    public interface CollectActivityModel {
        /**
         * 加载文章列表
         *
         * @param page
         * @return
         */
        Observable<List<com.wangyz.common.bean.db.Collect>> load(int page);

        /**
         * 刷新文章列表
         *
         * @param page
         * @return
         */
        Observable<List<com.wangyz.common.bean.db.Collect>> refresh(int page);

        /**
         * 取消收藏文章
         *
         * @param articleId
         * @return
         */
        Observable<Collect> unCollect(int articleId);
    }

    /**
     * CollectActivityView
     */
    public interface CollectActivityView extends com.wangyz.common.contract.Contract.BaseView {
        /**
         * 加载文章列表
         *
         * @param list
         */
        void onLoad(List<com.wangyz.common.bean.db.Collect> list);

        /**
         * 刷新文章列表
         *
         * @param list
         */
        void onRefresh(List<com.wangyz.common.bean.db.Collect> list);

        /**
         * 取消收藏文章
         *
         * @param result
         * @param articleId
         */
        void onUnCollect(Collect result, int articleId);
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
         */
        void unCollect(int articleId);
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
    public interface RegisterActivityView extends com.wangyz.common.contract.Contract.BaseView {

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
    public interface LoginActivityView extends com.wangyz.common.contract.Contract.BaseView {

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
     * SettingActivityModel
     */
    public interface SettingActivityModel {

    }

    /**
     * SettingActivityView
     */
    public interface SettingActivityView extends com.wangyz.common.contract.Contract.BaseView {

    }

    /**
     * SettingActivityPresenter
     */
    public interface SettingActivityPresenter {

    }

}
