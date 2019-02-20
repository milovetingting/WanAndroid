package com.wangyz.wanandroid.model;

import com.wangyz.wanandroid.base.BaseModel;
import com.wangyz.wanandroid.bean.model.KeyWord;
import com.wangyz.wanandroid.contract.Contract;

import io.reactivex.Observable;

/**
 * @author wangyz
 * @time 2019/1/28 16:42
 * @description SearchActivityModel
 */
public class SearchActivityModel extends BaseModel implements Contract.SearchActivityModel {
    @Override
    public Observable<KeyWord> loadKeyWord() {
        return mApi.loadHotKey();
    }
}
