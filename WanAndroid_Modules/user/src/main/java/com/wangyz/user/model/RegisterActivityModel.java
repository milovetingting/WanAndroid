package com.wangyz.user.model;

import com.wangyz.common.bean.model.Register;
import com.wangyz.user.base.BaseModel;

import io.reactivex.Observable;

/**
 * @author wangyz
 * @time 2019/1/24 10:26
 * @description RegisterActivityModel
 */
public class RegisterActivityModel extends BaseModel implements com.wangyz.user.contract.Contract.RegisterActivityModel {

    @Override
    public Observable<Register> register(String username, String password) {
        return mApi.register(username, password, password);
    }
}
