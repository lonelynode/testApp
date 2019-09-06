package com.example.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.notification.NotificationUtil;
import com.example.receiver.MusicReceiver;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MusicService extends Service {

    private String MusicServiceTag = "MusicService";

    private MediaPlayer mediaPlayer = new MediaPlayer();

    private MusicReceiver musicReceiver = new MusicReceiver(mediaPlayer);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(MusicServiceTag, "Service on bind");
        return null;
    }

    @Override
    public void onCreate() {
        Log.i(MusicServiceTag, "Service trigger on create");
        super.onCreate();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.testapp.pauseMusic");
        intentFilter.addAction("com.example.testapp.stopMusic");
        registerReceiver(musicReceiver, intentFilter);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(MusicServiceTag, "Service trigger on unbind");
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(MusicServiceTag, "Service trigger on start command");


        if ("playMusic".equals(intent.getAction())) {
            try {
                Log.i(MusicServiceTag, "Service on bind");
                String musicPath = Objects.requireNonNull(intent.getExtras()).getString("musicPath");
                mediaPlayer.setDataSource(musicPath);
//                mediaPlayer.start();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                // 通过异步的方式装载媒体资源
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        // 装载完毕回调
                        mediaPlayer.start();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }



            NotificationUtil notificationUtil = new NotificationUtil(this.getApplicationContext());
            Notification notification = notificationUtil.genMusicNotification();

            startForeground(112, notification);
        } else if (null != intent.getAction()) {
            if ("pauseMusic".equals(intent.getAction()) && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            } else {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onDestroy() {
        Log.i(MusicServiceTag, "Service trigger on destroy");
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
        super.onDestroy();
        stopForeground(true);
        unregisterReceiver(musicReceiver);
    }
}
