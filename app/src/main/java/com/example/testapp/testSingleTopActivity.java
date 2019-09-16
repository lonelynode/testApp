package com.example.testapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

public class testSingleTopActivity extends Activity {

    private String singleTopTag = "SingleTopActivity";
    private Button toSingleTop;
    private Button toMain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_single_top);

        initView();
        initAction();
    }

    private void initView() {
        toSingleTop = findViewById(R.id.toSingleTop);
        toMain = findViewById(R.id.toMain);
    }

    private void initAction() {
        toSingleTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(testSingleTopActivity.this, testSingleTopActivity.class);
                startActivity(intent);
            }
        });

        toMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(testSingleTopActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.i(singleTopTag, "Trigger new intent event");
        super.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        Log.i(singleTopTag, "Trigger on resume event");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.i(singleTopTag, "Trigger on start event");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.i(singleTopTag, "Trigger on restart event");
        super.onRestart();
    }

    @Override
    protected void onPause() {
        Log.i(singleTopTag, "Trigger on pause event");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(singleTopTag, "Trigger on stop event");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(singleTopTag, "Trigger destroy event");
        super.onDestroy();
    }
}
