package com.example.testapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.service.MusicService;
import com.example.utils.SpUtils;

public class SettingActivity extends Activity {

    private Button logoutBtn;
    private Button toSingleTop;
    private String settingActivityTag = "SettingActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();

        initAction();
    }

    private void initAction() {
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to main page
                try {
                    Intent musicServiceIntent = new Intent(SettingActivity.this, MusicService.class);
                    stopService(musicServiceIntent);
                } catch (Exception e) {
                    Log.i("settingActivityTag", "stop service failed");
                }
                SpUtils.remove(SettingActivity.this, getString(R.string.username));
                Intent loginIntent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });

        toSingleTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, testSingleTopActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        logoutBtn = findViewById(R.id.logout);
        toSingleTop = findViewById(R.id.toSingleTop);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.i(settingActivityTag, "Trigger new intent event");
        super.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        Log.i(settingActivityTag, "Trigger on resume event");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.i(settingActivityTag, "Trigger on start event");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.i(settingActivityTag, "Trigger on restart event");
        super.onRestart();
    }

    @Override
    protected void onPause() {
        Log.i(settingActivityTag, "Trigger on pause event");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(settingActivityTag, "Trigger on stop event");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(settingActivityTag, "Trigger destroy event");
        super.onDestroy();
    }
}
