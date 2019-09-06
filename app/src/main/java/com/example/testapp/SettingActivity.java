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
    }

    private void initView() {
        logoutBtn = findViewById(R.id.logout);
    }
}
