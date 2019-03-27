package com.wangyz.wanandroid.strategy;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.wangyz.wanandroid.R;
import com.wangyz.wanandroid.bean.model.Option;

/**
 * @author wangyz
 * @time 2019/3/27 14:22
 * @description LinkStrategy
 */
public class LinkStrategy implements OptionStrategy {
    @Override
    public void handleOption(Option option) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) Utils.getApp().getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Copy", option.link);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        ToastUtils.showShort(R.string.copy_finish);
    }
}
