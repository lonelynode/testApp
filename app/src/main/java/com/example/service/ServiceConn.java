package com.example.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

public class ServiceConn implements ServiceConnection {
    private String serviceTag;

    public  ServiceConn(String serviceTag) {
        this.serviceTag = serviceTag;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        Log.i(serviceTag, "trigger service connected");
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.i(serviceTag, "trigger service disconnected");

    }

    @Override
    public void onBindingDied(ComponentName name) {
        Log.i(serviceTag, "trigger service on bind died");
    }


}
