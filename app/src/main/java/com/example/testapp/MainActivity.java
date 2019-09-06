package com.example.testapp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.notification.NotificationUtil;
import com.example.service.MusicService;
import com.example.service.MusicService2;
import com.example.service.ServiceConn;

import java.io.File;

public class MainActivity extends Activity {

    private String MainActivityTAG = "MainActivity";
    private ServiceConnection serviceConn;
    private Button toSettingBtn;
    private Button playMusic;
    private Button toDetailBtn;
    private Button stopMusicBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initAction();
    }

    private void initView() {
        toSettingBtn = findViewById(R.id.showMore);
        playMusic = findViewById(R.id.playMusic);
        stopMusicBtn = findViewById(R.id.stopMusic);
        toDetailBtn = findViewById(R.id.toDetail);
    }

    private void initAction() {
        serviceConn = new ServiceConn(MainActivityTAG);
        toSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to setting page
                Intent mainIntent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(mainIntent);
            }
        });

        playMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start service to play music
                String filePath = Environment.getExternalStorageDirectory().getPath() + "/music/MARIAGE D'AMOUR.m4a";
                File file = new File(filePath);
                if (file.exists()) {
                    Intent musicServiceIntent = new Intent(MainActivity.this, MusicService.class);
                    musicServiceIntent.putExtra("musicPath", filePath);
                    musicServiceIntent.setAction("playMusic");
                    startService(musicServiceIntent);
                } else {
                    Toast.makeText(v.getContext(), "Target file not exists", Toast.LENGTH_LONG).show();
                }
            }
        });

        stopMusicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent musicServiceIntent = new Intent(MainActivity.this, MusicService.class);
                stopService(musicServiceIntent);
            }
        });

        toDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MusicActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.i(MainActivityTAG, "Trigger new intent event");
        super.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        Log.i(MainActivityTAG, "Trigger on resume event");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.i(MainActivityTAG, "Trigger on start event");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.i(MainActivityTAG, "Trigger on restart event");
        super.onRestart();
    }

    @Override
    protected void onPause() {
        Log.i(MainActivityTAG, "Trigger on pause event");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(MainActivityTAG, "Trigger on stop event");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(MainActivityTAG, "Trigger destroy event");
        super.onDestroy();
    }
}
