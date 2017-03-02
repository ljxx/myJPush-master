package com.test.www.myjpush.receive;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * ========================================
 * <p/>
 * 版 权：蓝吉星讯 版权所有 （C） 2017
 * <p/>
 * 作 者：yanglixiang
 * <p/>
 * 版 本：1.0
 * <p/>
 * 创建日期：2017/3/2  下午1:45
 * <p/>
 * 描 述：
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public class MyReceive extends BroadcastReceiver {
    public static String TYPE = "type"; //这个type是为了Notification更新信息的，这个不明白的朋友可以去搜搜，很多

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        int type = intent.getIntExtra(TYPE, -1);
        Log.i("=====MyReceive===","===type==="+type);
        if (type != -1) {
            Log.i("=====MyReceive===","处理取消事件");
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(type);
        }

        if (action.equals("notification_clicked")) {
            String mTitle = intent.getStringExtra("jPush_message");
            Log.i("=====MyReceive===","处理点击事件： " + mTitle);
            //处理点击事件
        }

        if (action.equals("notification_cancelled")) {
            Log.i("======MyReceive==","处理点击事件点击删除");
            //处理滑动清除和点击删除事件
        }
    }
}
