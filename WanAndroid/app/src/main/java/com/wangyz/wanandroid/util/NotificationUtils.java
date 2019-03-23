package com.wangyz.wanandroid.util;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.blankj.utilcode.util.Utils;
import com.wangyz.wanandroid.R;

import static android.app.Notification.VISIBILITY_SECRET;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * @author 木米小雨 https://www.jianshu.com/p/fc127d9a8f44
 * @time 2019/3/22 15:01
 * @description NotificationUtils
 */
public class NotificationUtils {

    private static NotificationManager manager;

    private static NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) Utils.getApp().getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static Notification.Builder getNotificationBuilder(String title, String content, String channelId) {
        //大于8.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //id随便指定
            NotificationChannel channel = new NotificationChannel(channelId, Utils.getApp().getPackageName(), NotificationManager.IMPORTANCE_DEFAULT);
            //闪光
            channel.enableLights(true);
            //锁屏显示通知
            channel.setLockscreenVisibility(VISIBILITY_SECRET);
            //指定闪光是的灯光颜色
            channel.setLightColor(Color.GREEN);
            //允许震动
            channel.enableVibration(true);
            channel.setSound(null, null);
            //设置可以绕过，请勿打扰模式
            channel.setBypassDnd(true);
            //震动的模式，震3次，第一次100，第二次100，第三次200毫秒
            channel.setVibrationPattern(new long[]{100, 100, 200});
            //通知管理者创建的渠道
            getManager().createNotificationChannel(channel);

        }
        return new Notification.Builder(Utils.getApp()).setAutoCancel(true).setChannelId(channelId)
                .setContentTitle(title)
                .setContentText(content).setSmallIcon(R.drawable.icon);
    }

    @TargetApi(Build.VERSION_CODES.O)
    public static void showNotification(String title, String content, int manageId, String channelId, int progress, int maxProgress) {
        final Notification.Builder builder = getNotificationBuilder(title, content, channelId);
        builder.setOnlyAlertOnce(true);
        builder.setDefaults(Notification.FLAG_ONLY_ALERT_ONCE);
        builder.setProgress(maxProgress, progress, false);
        builder.setWhen(System.currentTimeMillis());
        getManager().notify(manageId, builder.build());
    }

    public static void cancleNotification(int manageId) {
        getManager().cancel(manageId);
    }

}
