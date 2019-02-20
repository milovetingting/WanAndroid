package com.wangyz.wx.model;

import android.annotation.TargetApi;
import android.os.Build;

import com.wangyz.wx.base.BaseModel;
import com.wangyz.common.bean.db.Author;
import com.wangyz.wx.contract.Contract;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * @author wangyz
 * @time 2019/1/21 17:22
 * @description WxFragmentModel
 */
public class WxFragmentModel extends BaseModel implements Contract.WxFragmentModel {

    @Override
    public Observable<List<Author>> loadWx() {
        Observable<List<Author>> loadFromLocal = Observable.create(emitter -> {
            List<Author> list = LitePal.findAll(Author.class);
            if (list != null && list.size() > 0) {
                emitter.onNext(list);
            } else {
                emitter.onComplete();
            }
        });
        Observable<List<Author>> loadFromNet = doLoadWxFromNet();
        return Observable.concat(loadFromLocal, loadFromNet);
    }

    @Override
    public Observable<List<Author>> refreshWx() {
        return doLoadWxFromNet();
    }

    @TargetApi(Build.VERSION_CODES.N)
    private Observable<List<Author>> doLoadWxFromNet() {
        return mApi.loadWx().filter(a -> a.getErrorCode() == 0).map(a -> {
            List<Author> list = new ArrayList<>();
            a.getData().stream().forEach(d -> {
                Author author = new Author();
                author.authorId = d.getId();
                author.author = d.getName();
                list.add(author);
            });
            if (list.size() > 0) {
                LitePal.deleteAll(Author.class);
            }
            LitePal.saveAll(list);
            return list;
        });
    }
}
