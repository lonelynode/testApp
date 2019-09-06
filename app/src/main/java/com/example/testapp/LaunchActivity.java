package com.example.testapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.module.AppPermissionCheck;
import com.example.utils.SpUtils;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action0;
import rx.functions.Action1;

public class LaunchActivity extends Activity {

    private String launchActivityTag = "LaunchActivity";
    private final AppPermissionCheck appPermissionCheck = new AppPermissionCheck();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // check permission
        checkPermission();
    }

    private void handleLaunchApp() {
        Intent launchIntent;
//        if (checkUserProfile()) {
//            // Go to main page
//            launchIntent = new Intent(this, MainActivity.class);
//        } else {
//            // Go to login page
//            launchIntent = new Intent(this, LoginActivity.class);
//        }
        launchIntent = new Intent(this, MainActivity.class);
        startActivity(launchIntent);
    }

    private void checkPermission() {
        final RxPermissions rxPermissions = new RxPermissions(LaunchActivity.this);
        rxPermissions.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE).doOnCompleted(new Action0() {
            @Override
            public void call() {
                if (appPermissionCheck.isNoRejectedPermission()) {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            handleLaunchApp();
                        }
                    }, 1000);
                } else {
                    showNoPermissionDialog();
                }
            }
        }).subscribe(new Action1<Permission>() {
            @Override
            public void call(Permission permission) {
                if (permission.granted) {
                    // `permission.name` is granted !
                    Log.i(launchActivityTag, "call: granted");
                } else if (permission.shouldShowRequestPermissionRationale) {
                    // Denied permission without ask never again
                    Log.i(launchActivityTag, "call: shouldShowRequestPermissionRationale");
                    appPermissionCheck.setNoRejectedPermission(false);
                } else {
                    // Denied permission with ask never again
                    // Need to go to the settings
                    Log.i(launchActivityTag, "call: else");
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.i(launchActivityTag, "call: " + throwable.getMessage());
            }
        });
    }

    private void showNoPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LaunchActivity.this);
        builder.setTitle("APP EXIT");
        builder.setMessage("App will EXIT for no enough permission!");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.i(launchActivityTag, "Trigger new intent event");
        super.onNewIntent(intent);
    }

    private boolean checkUserProfile() {
        return !"".equals(SpUtils.get(this, getString(R.string.username), ""));
    }

    @Override
    protected void onResume() {
        Log.i(launchActivityTag, "Trigger on resume event");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.i(launchActivityTag, "Trigger on start event");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.i(launchActivityTag, "Trigger on restart event");
        super.onRestart();
    }

    @Override
    protected void onPause() {
        Log.i(launchActivityTag, "Trigger on pause event");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(launchActivityTag, "Trigger on stop event");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(launchActivityTag, "Trigger destroy event");
        super.onDestroy();
    }
}
