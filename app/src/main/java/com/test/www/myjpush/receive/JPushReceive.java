package com.test.www.myjpush.receive;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.test.www.myjpush.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by admin on 2016/6/30.
 */
public class JPushReceive extends BroadcastReceiver {
    private String TAG = "JPushReceive";
    public static final String TYPE = "type"; //这个type是为了Notification更新信息的，这个不明白的朋友可以去搜搜，很多

    private NotificationManager notificationManager;



    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            System.out.println("===收到了Notification ID是：" + bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID));
        }else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String mTest = bundle.getString(JPushInterface.EXTRA_EXTRA);
            System.out.println("===收到了自定义消息。消息内容是：" + message);
            System.out.println("===收到了自定义消息内容===：" + mTest);
            // 自定义消息不会展示在通知栏，完全要开发者写代码去处理
            processCustomMessage(context,bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            System.out.println("===收到了通知");
            // 在这里可以做些统计，或者做些其他工作
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            System.out.println("===用户点击打开了通知");
            // 在这里可以自己写代码去定义用户点击后的行为
            /*Intent i = new Intent(context, TestActivity.class);  //自定义打开的界面
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);*/
        } else if(JPushInterface.ACTION_NOTIFICATION_CLICK_ACTION.equals(intent.getAction())){ //
            System.out.println("===用户点击打开了通知栏ACTION_NOTIFICATION_CLICK_ACTION");
        } else {
            Log.d(TAG, "===Unhandled intent - " + intent.getAction());
        }



        int type = intent.getIntExtra(TYPE, -1);
        Log.i("========","===type==="+type);
        if (type != -1) {
            Log.i("========","处理取消事件");
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(type);
        }

        if (intent.getAction().equals("notification_clicked")) {
            String mTitle = intent.getStringExtra("jPush_message");
            Log.i("========","处理点击事件: " + mTitle);
            //处理点击事件
        }

        if (intent.getAction().equals("notification_cancelled")) {
            Log.i("========","处理点击事件点击删除");
            //处理滑动清除和点击删除事件
        }
    }


    /**
     * 自定义消息处理
     * 这里测试的推送数据为：{"id": 1,"os": "android","title":"什么过勒是哪个了方","osv": "5.1"}
     *
     * 也可以MyReceive中的内容写到本类中
     * @param context
     * @param bundle
     */
    private void processCustomMessage(Context context, Bundle bundle) {
        Log.i("========","processCustomMessage");
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        int mtype = 0;
        String mTitle = "";
        try {
            JSONObject mJson = new JSONObject(message);
            mtype = Integer.parseInt(mJson.getString("id"));
            mTitle = mJson.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intentClick = new Intent(context, MyReceive.class);
        intentClick.setAction("notification_clicked");
        intentClick.putExtra("jPush_message",mTitle);
        intentClick.putExtra(MyReceive.TYPE, mtype);
        PendingIntent pendingIntentClick = PendingIntent.getBroadcast(context, UUID.randomUUID().hashCode(), intentClick, PendingIntent.FLAG_CANCEL_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("赵涌在线")
                .setContentText(mTitle)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntentClick);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(mtype /* ID of notification */, notificationBuilder.build());  //这就是那个type，相同的update，不同add
    }
}
