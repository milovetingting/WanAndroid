package com.wangyz.wanandroid.model;

import com.wangyz.wanandroid.ConstantValue;
import com.wangyz.wanandroid.base.BaseModel;
import com.wangyz.wanandroid.bean.model.Update;
import com.wangyz.wanandroid.contract.Contract;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * @author wangyz
 * @time 2019/1/17 15:36
 * @description MainActivityModel
 */
public class MainActivityModel extends BaseModel implements Contract.MainActivityModel {

    public MainActivityModel(String baseUrl) {
        initRetrofit(baseUrl);
    }

    @Override
    public Observable<Update> checkUpdate() {
        return mApi.checkUpdate();
    }

    @Override
    public Observable<ResponseBody> download() {
        setDownload();
        return mApi.download(ConstantValue.URL_UPDATE_APK);
    }
}
