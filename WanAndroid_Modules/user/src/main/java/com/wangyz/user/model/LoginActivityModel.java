package com.wangyz.user.model;

import com.wangyz.common.bean.model.Login;
import com.wangyz.user.base.BaseModel;

import io.reactivex.Observable;

/**
 * @author wangyz
 * @time 2019/1/24 11:41
 * @description LoginActivityModel
 */
public class LoginActivityModel extends BaseModel implements com.wangyz.user.contract.Contract.LoginActivityModel {

    public LoginActivityModel() {
        setCookie(true);
    }

    @Override
    public Observable<Login> login(String username, String password) {
        return mApi.login(username, password);
    }
}
