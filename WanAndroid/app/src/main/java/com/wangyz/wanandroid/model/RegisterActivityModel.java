package com.wangyz.wanandroid.model;

import com.wangyz.wanandroid.base.BaseModel;
import com.wangyz.wanandroid.bean.model.Register;
import com.wangyz.wanandroid.contract.Contract;

import io.reactivex.Observable;

/**
 * @author wangyz
 * @time 2019/1/24 10:26
 * @description RegisterActivityModel
 */
public class RegisterActivityModel extends BaseModel implements Contract.RegisterActivityModel {
    
    @Override
    public Observable<Register> register(String username, String password) {
        return mApi.register(username, password, password);
    }
}
