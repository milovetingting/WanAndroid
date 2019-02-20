package com.wangyz.user.model;

import com.wangyz.common.bean.model.Logout;
import com.wangyz.user.base.BaseModel;

import io.reactivex.Observable;

/**
 * @author wangyz
 * @time 2019/1/17 15:50
 * @description MenuFragmentModel
 */
public class MenuFragmentModel extends BaseModel implements com.wangyz.user.contract.Contract.MenuFragmentModel {

    public MenuFragmentModel() {
        setCookie(true);
    }

    @Override
    public Observable<Logout> logout() {
        return mApi.logout();
    }
}
