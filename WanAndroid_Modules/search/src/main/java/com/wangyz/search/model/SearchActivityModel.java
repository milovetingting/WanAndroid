package com.wangyz.search.model;

import com.wangyz.common.bean.model.KeyWord;
import com.wangyz.search.base.BaseModel;
import com.wangyz.search.contract.Contract;

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
