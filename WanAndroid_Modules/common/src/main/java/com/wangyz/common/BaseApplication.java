package com.wangyz.common;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.Utils;

import org.litepal.LitePal;

/**
 * @author wangyz
 * @time 2019/2/12 11:35
 * @description BaseApplication
 */
public class BaseApplication extends Application {

    private boolean isDebugARouter = true;

    @Override
    public void onCreate() {
        super.onCreate();

        if (isDebugARouter) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);

        Utils.init(this);

        LitePal.initialize(this);
    }
}
