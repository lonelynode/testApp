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
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.service.MusicService;
import com.example.service.MusicService2;

public class MusicActivity extends Activity {

    private String MainActivityTAG = "MusicActivity";
    private Button playOrPauseMusic, stopMusic, endMusic;
    private Button previousMusic, nextMusic;

    private SeekBar mSeekBar;
    private TextView tv, tv2;
    private String filePath;
    private MusicService2 musicService;
    private TextView musicStatus;
    private TextView musicTime;

    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            musicService = ((MusicService2.MyBinder)iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            musicService = null;
        }
    };
    private void bindServiceConnection() {
        Intent intent = new Intent(MusicActivity.this, MusicService.class);
        startService(intent);
        bindService(intent, sc, this.BIND_AUTO_CREATE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filePath = Environment.getExternalStorageDirectory().getPath() + "/music/MARIAGE D'AMOUR2.m4a";

        initView();

        initAction();


        Log.d("hint", "ready to new MusicService");
        musicService = new MusicService2();
        Log.d("hint", "finish to new MusicService");
        bindServiceConnection();


        Log.d("hint", Environment.getExternalStorageDirectory().getAbsolutePath()+"/You.mp3");
//        playMusic();
    }

    private void initView() {
        playOrPauseMusic = findViewById(R.id.pauseMusic);
        stopMusic = findViewById(R.id.stopMusic);
        endMusic = findViewById(R.id.end);

        mSeekBar = findViewById(R.id.seekbar);
        previousMusic = findViewById(R.id.previousBtn);
        previousMusic = findViewById(R.id.nextBtn);
        musicStatus = findViewById(R.id.musicStatus);
        musicTime = findViewById(R.id.musicTime);

    }

    private void initAction() {
//        playMusic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Start service to play music
//                File file = new File(filePath);
//                if (file.exists()) {
//                    Intent musicServiceIntent = new Intent(MusicActivity.this, MusicService2.class);
//                    musicServiceIntent.putExtra("musicPath", filePath);
//                    musicServiceIntent.setAction("playMusic");
//                    bindService(musicServiceIntent, new ServiceConnection() {
//                        @Override
//                        public void onServiceConnected(ComponentName name, IBinder service) {
//                            iBinder = service;
//                        }
//
//                        @Override
//                        public void onServiceDisconnected(ComponentName name) {
//
//                        }
//                    }, 0);
//                } else {
//                    Toast.makeText(v.getContext(), "Target file not exists", Toast.LENGTH_LONG).show();
//                }
//            }
//        });


        mSeekBar.setProgress(musicService.mp.getCurrentPosition());
        mSeekBar.setMax(musicService.mp.getDuration());
        playOrPauseMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicService.playOrPause();
            }
        });

        stopMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicService.stop();
            }
        });

        endMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(runnable);
                unbindService(sc);
            }
        });

        previousMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicService.preMusic();
            }
        });

        nextMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicService.nextMusic();
            }
        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.i(MainActivityTAG, "Trigger new intent event");
        super.onNewIntent(intent);
    }



//    private void playMusic() {
//        // Start service to play music
//        File file = new File(filePath);
//        if (file.exists()) {
//            Intent musicServiceIntent = new Intent(MusicActivity.this, MusicService2.class);
//            musicServiceIntent.putExtra("musicPath", filePath);
//            musicServiceIntent.setAction("playMusic");
//            bindService(musicServiceIntent, new ServiceConnection() {
//                @Override
//                public void onServiceConnected(ComponentName name, IBinder service) {
//                    iBinder = service;
//                }
//
//                @Override
//                public void onServiceDisconnected(ComponentName name) {
//
//                }
//            }, 0);
//        } else {
//            Toast.makeText(this, "Target file not exists", Toast.LENGTH_LONG).show();
//        }
//    }

    public android.os.Handler handler = new android.os.Handler();
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(musicService.mp.isPlaying()) {
                musicStatus.setText(getResources().getString(R.string.music_playing));
                playOrPauseMusic.setText(getResources().getString(R.string.pauseMusic).toUpperCase());
            } else {
                musicStatus.setText(getResources().getString(R.string.music_pause));
                playOrPauseMusic.setText(getResources().getString(R.string.unPauseMusic).toUpperCase());
            }
            musicTime.setText(musicService.mp.getCurrentPosition() + "/"
                    + musicService.mp.getDuration());
            mSeekBar.setProgress(musicService.mp.getCurrentPosition());
            mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        musicService.mp.seekTo(seekBar.getProgress());
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            handler.postDelayed(runnable, 100);
        }
    };

    @Override
    protected void onResume() {
        Log.i(MainActivityTAG, "Trigger on resume event");
        super.onResume();
        if(musicService.mp.isPlaying()) {
//            musicStatus.setText(getResources().getString(R.string.playing));
        } else {
//            musicStatus.setText(getResources().getString(R.string.pause));
        }

        mSeekBar.setProgress(musicService.mp.getCurrentPosition());
        mSeekBar.setMax(musicService.mp.getDuration());
        handler.post(runnable);
        super.onResume();
        Log.d("hint", "handler post runnable");
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
        unbindService(sc);
        super.onDestroy();
    }
}
