package com.example.notification;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.example.receiver.MusicReceiver;
import com.example.testapp.BuildConfig;
import com.example.testapp.R;

public class NotificationUtil {

    private static Context mContext;

    public NotificationUtil(Context context) {
        this.mContext = context;
    }

    public Notification genMusicNotification() {
        final Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.music_icon);
        NotificationManager notificationManager = (NotificationManager) mContext
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = getNotificationByHandlerVersion(notificationManager, bitmap, "11");
        notification.flags |= Notification.FLAG_NO_CLEAR;
        notification.defaults = Notification.DEFAULT_SOUND;
        int requestCode = (int) System.currentTimeMillis();
//        notificationManager.notify(requestCode, notification);
        return notification;
    }

    private Notification getNotificationByHandlerVersion(NotificationManager notificationManager, Bitmap bitmap, String message) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return getNotificationFromAndroidO(notificationManager, bitmap, message);
        } else {
            return getNotification(bitmap, message);
        }
    }

    // Handler notification manager for Android O, needing setting auto channel to true;
    @SuppressLint("WrongConstant")
    @TargetApi(Build.VERSION_CODES.O)
    public void createNotificationChannel(NotificationManager notificationManager) {
        NotificationChannel sailsChannel = new NotificationChannel(BuildConfig.APPLICATION_ID, BuildConfig.APPLICATION_ID, NotificationManager.IMPORTANCE_DEFAULT);
        //是否绕过请勿打扰模式
        sailsChannel.canBypassDnd();
        //闪光灯
        sailsChannel.enableLights(true);
        //锁屏显示通知
//		sailsChannel.setLockscreenVisibility(NotificationManager.);
        //桌面launcher的消息角标
        sailsChannel.canShowBadge();
        //是否允许震动
        sailsChannel.enableVibration(true);
        //获取系统通知响铃声音的配置
        sailsChannel.getAudioAttributes();
        //获取通知取到组
        sailsChannel.getGroup();
        //设置可绕过  请勿打扰模式
        sailsChannel.setBypassDnd(true);
        //设置震动模式
        sailsChannel.setVibrationPattern(new long[]{100, 100, 200});
        //是否会有灯光
        sailsChannel.shouldShowLights();
        notificationManager.createNotificationChannel(sailsChannel);
    }

    @TargetApi(Build.VERSION_CODES.O)
    public Notification getNotificationFromAndroidO(NotificationManager notificationManager, Bitmap bitmap, String message) {
        createNotificationChannel(notificationManager);
        Notification.Builder builder = new Notification.Builder(mContext, BuildConfig.APPLICATION_ID);
        builder.setContentTitle("TEST");
        builder.setContentText(message);
        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.drawable.music_icon);
//        builder.setStyle(new Notification.BigTextStyle()
//                .setBigContentTitle("TEST")
//                .bigText(message));

        // Set notification views; Bind action
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.notification);
        Intent pauseIntent = new Intent("com.example.testapp.pauseMusic");
        PendingIntent pauseBtnPI = PendingIntent.getBroadcast(mContext, 0, pauseIntent, 0);
        Intent stopIntent = new Intent("com.example.testapp.stopMusic");
        PendingIntent stopBtnPI = PendingIntent.getBroadcast(mContext, 0, stopIntent, 0);
        remoteViews.setOnClickPendingIntent(R.id.pauseMusic, pauseBtnPI);
        remoteViews.setOnClickPendingIntent(R.id.nextMusic, stopBtnPI);
        builder.setCustomContentView(remoteViews);
        builder.setContent(remoteViews);

        builder.setLargeIcon(bitmap);
        return builder.build();
    }

    public Notification getNotification(Bitmap bitmap, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
        builder.setContentTitle("TEST");
        builder.setContentText(message);
        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.drawable.music_icon);
        builder.setPriority(1);
//        builder.setStyle(new NotificationCompat.BigTextStyle()
//                .setBigContentTitle("TEST")
//                .bigText(message));
        // Set notification views; Bind action
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.notification);
        Intent pauseIntent = new Intent("com.example.testapp.pauseMusic");
//        Intent pauseIntent = new Intent(mContext, MusicReceiver.class);
//        pauseIntent.setAction("com.example.testapp.pauseMusic");
        PendingIntent pauseBtnPI = PendingIntent.getBroadcast(mContext, 0, pauseIntent, 0);
        Intent stopIntent = new Intent("com.example.testapp.stopMusic");
//        Intent stopIntent = new Intent(mContext, MusicReceiver.class);
//?        stopIntent.setAction("com.example.testapp.stopMusic");
        PendingIntent stopBtnPI = PendingIntent.getBroadcast(mContext, 0, stopIntent, 0);
        remoteViews.setOnClickPendingIntent(R.id.pauseMusic, pauseBtnPI);
        remoteViews.setOnClickPendingIntent(R.id.nextMusic, stopBtnPI);
        builder.setCustomContentView(remoteViews);
        builder.setContent(remoteViews);

        builder.setLargeIcon(bitmap);
        return builder.build();
    }
}
