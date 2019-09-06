package com.example.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

public class MusicReceiver extends BroadcastReceiver {

    private String MUSICTAG = "MusicReceiver";
    private MediaPlayer mediaPlayer;

    public MusicReceiver(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("com.example.testapp.pauseMusic".equals(intent.getAction())) {
            // pause music
            Log.i(MUSICTAG, "pause music");
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.start();
            }
        } else if ("com.example.testapp.stopMusic".equals(intent.getAction())) {
            // stop music
            Log.i(MUSICTAG, "stop music");
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.seekTo(0);
            }
        }
    }
}
