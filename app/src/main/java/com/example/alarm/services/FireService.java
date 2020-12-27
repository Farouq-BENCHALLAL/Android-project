package com.example.alarm.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.alarm.views.FireActivity;

public class FireService extends Service {

    public static final String ACTION_FIRE = "ACTION_FIRE";
    public static final String ACTION_STOP = "ACTION_STOP";
    public Ringtone ringtone;
    public static final String ACTION_ALARM_DONE = "ACTION_ALARM_DONE";

    @Override
    public void onCreate() {
        this.ringtone = new Ringtone(getApplicationContext());
        LocalBroadcastManager
                .getInstance(getApplicationContext())
                .registerReceiver(new UpdateBroadcastReceiver(),
                        new IntentFilter(FireService.ACTION_ALARM_DONE));
    }

    @Override
    public IBinder onBind(Intent intent) { return null; }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        switch (intent.getAction()) {
            case ACTION_FIRE:
                onFire();
                break;
            case ACTION_STOP:
                onStop();
                break;
        }
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Notifications.hideFireNotification(this);
        ringtone.stop();
        LocalBroadcastManager
                .getInstance(getApplicationContext())
                .sendBroadcast(new Intent(ACTION_ALARM_DONE));
    }

    private void onFire() {
        Notifications.showFireNotification(this);
        ringtone.start();
    }

    private void onStop() { 
        stopSelf();
    }
}