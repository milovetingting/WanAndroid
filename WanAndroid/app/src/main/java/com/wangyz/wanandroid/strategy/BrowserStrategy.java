package com.wangyz.wanandroid.strategy;

import android.content.Intent;
import android.net.Uri;

import com.blankj.utilcode.util.Utils;
import com.wangyz.wanandroid.bean.model.Option;

/**
 * @author wangyz
 * @time 2019/3/27 15:53
 * @description BrowserStrategy
 */
public class BrowserStrategy implements OptionStrategy {
    @Override
    public void handleOption(Option option) {
        Uri uri = Uri.parse(option.link);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Utils.getApp().startActivity(intent);
    }
}
