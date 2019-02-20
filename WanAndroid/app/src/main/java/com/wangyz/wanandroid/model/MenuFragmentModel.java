package com.wangyz.wanandroid.model;

import com.wangyz.wanandroid.base.BaseModel;
import com.wangyz.wanandroid.bean.model.Logout;
import com.wangyz.wanandroid.contract.Contract;

import io.reactivex.Observable;

/**
 * @author wangyz
 * @time 2019/1/17 15:50
 * @description MenuFragmentModel
 */
public class MenuFragmentModel extends BaseModel implements Contract.MenuFragmentModel {

    public MenuFragmentModel() {
        setCookie(true);
    }

    @Override
    public Observable<Logout> logout() {
        return mApi.logout();
    }
}
