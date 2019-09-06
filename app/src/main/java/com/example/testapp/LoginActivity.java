package com.example.testapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.utils.SpUtils;

public class LoginActivity extends Activity {

    private String loginActivityTag = "LoginActivity";

    private EditText usernameEDT;
    private EditText passwordEDT;
    private Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        // Init view
        initView();

        // Bind action
        initAction();
    }

    private void initAction() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEDT.getEditableText().toString();
                String password = passwordEDT.getEditableText().toString();
                if (!"user1".equals(username) || !"password".equals(password)) {
                    Toast.makeText(v.getContext(), "Invalid input", Toast.LENGTH_LONG).show();
                } else {
                    SpUtils.put(LoginActivity.this, getString(R.string.username), username);
                    // Go to main page
                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        });
    }

    private void initView() {
        usernameEDT = findViewById(R.id.username);
        passwordEDT = findViewById(R.id.password);
        loginBtn = findViewById(R.id.login);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.i(loginActivityTag, "Trigger new intent event");
        super.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        Log.i(loginActivityTag, "Trigger on resume event");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.i(loginActivityTag, "Trigger on start event");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.i(loginActivityTag, "Trigger on restart event");
        super.onRestart();
    }

    @Override
    protected void onPause() {
        Log.i(loginActivityTag, "Trigger on pause event");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(loginActivityTag, "Trigger on stop event");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(loginActivityTag, "Trigger destroy event");
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
