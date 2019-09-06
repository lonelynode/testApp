package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

public class MusicService2 extends Service {

    public final IBinder binder = new MyBinder();

    public class MyBinder extends Binder {
        public MusicService2 getService() {
            return MusicService2.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private String[] musicDir = new String[]{
            Environment.getExternalStorageDirectory().getAbsolutePath() + "/music/MARIAGE D'AMOUR.m4a",
            Environment.getExternalStorageDirectory().getAbsolutePath() + "/music/MARIAGE D'AMOUR2.m4a"};
    private int musicIndex = 1;

    public static MediaPlayer mp = new MediaPlayer();

    public MusicService2() {
        try {
            musicIndex = 1;
            mp.setDataSource(musicDir[musicIndex]);
            mp.prepare();
        } catch (Exception e) {
            Log.d("hint", "can't get to the song");
            e.printStackTrace();
        }
    }

    public void playOrPause() {
        if (mp.isPlaying()) {
            mp.pause();
        } else {
            mp.start();
        }
    }

    public void stop() {
        if (mp != null) {
            mp.stop();
            try {
                mp.prepare();
                mp.seekTo(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void nextMusic() {
        if (mp != null && musicIndex < 3) {
            mp.stop();
            try {
                mp.reset();
                mp.setDataSource(musicDir[musicIndex + 1]);
                musicIndex++;
                mp.prepare();
                mp.seekTo(0);
                mp.start();
            } catch (Exception e) {
                Log.d("hint", "can't jump next music");
                e.printStackTrace();
            }
        }
    }

    public void preMusic() {
        if (mp != null && musicIndex > 0) {
            mp.stop();
            try {
                mp.reset();
                mp.setDataSource(musicDir[musicIndex - 1]);
                musicIndex--;
                mp.prepare();
                mp.seekTo(0);
                mp.start();
            } catch (Exception e) {
                Log.d("hint", "can't jump pre music");
                e.printStackTrace();
            }
        }
    }
}

