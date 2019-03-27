package com.wangyz.wanandroid.strategy;

import android.content.Intent;

import com.blankj.utilcode.util.Utils;
import com.wangyz.wanandroid.bean.model.Option;

/**
 * @author wangyz
 * @time 2019/3/27 14:21
 * @description ShareStrategy
 */
public class ShareStrategy implements OptionStrategy {
    @Override
    public void handleOption(Option option) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, option.title + "\n" + option.link);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Utils.getApp().startActivity(Intent.createChooser(intent, "Share"));
    }
}
