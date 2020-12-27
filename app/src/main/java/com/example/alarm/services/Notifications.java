package com.example.alarm.services;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.alarm.R;
import com.example.alarm.model.Alarm;
import com.example.alarm.views.AlarmActivity;
import com.example.alarm.views.FireActivity;

public class Notifications {

    public static final int FIRE_NOTIFICATION_ID = 0;

    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("MyAlarmChannel", "Alarm", importance);
            channel.setDescription("Alarm");
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void showFireNotification(Context context) {
        createNotificationChannel(context);
        Resources resources = context.getResources();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, "MyAlarmChannel")
                .setContentTitle(resources.getString(R.string.app_name))
                .setSmallIcon(R.drawable.ic_alarm_white_18dp)
                .setOngoing(true)
                .setAutoCancel(false)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setLocalOnly(true)
                .setPriority(NotificationCompat.PRIORITY_MAX);

        Intent stopIntent = new Intent(context, FireService.class).setAction(FireService.ACTION_STOP);/* TODO : créer un intent qui déclenche l'action ACTION_STOP du service. */
        PendingIntent stopPendingIntent =  PendingIntent.getService(context, 0,
                stopIntent, PendingIntent.FLAG_UPDATE_CURRENT);/* TODO : créer le PendingIntent. */
        notification.addAction(R.drawable.ic_alarm_off_white_18dp,
                        resources.getString(R.string.alarm_stop),
                        stopPendingIntent);
        Intent fullScreenIntent = new Intent(context, FireActivity.class);
        fullScreenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_USER_ACTION);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setFullScreenIntent(pendingIntent, true);

        notificationManager.cancel(FIRE_NOTIFICATION_ID);
        notificationManager.notify(FIRE_NOTIFICATION_ID, notification.build());
    }

    public static void hideFireNotification(Context context) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(FIRE_NOTIFICATION_ID);
        updateNextAlarmNotification(context);
    }
    public static void updateNextAlarmNotification(Context context) {
        Alarm nextAlarm = Alarm.nextAlarm(context.getContentResolver());
        Intent intent = new Intent(context, FireService.class).setAction(FireService.ACTION_FIRE);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (nextAlarm == null) {
            Log.d("c", "OK je suis entrer");
            PendingIntent operation = PendingIntent.getService(context, 0, intent,
                                                               PendingIntent.FLAG_NO_CREATE);
            alarmManager.cancel(operation);
            return;
        }

        PendingIntent operation = PendingIntent.getService(context, 0, 
                                       intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long timeInMillis = nextAlarm.timeInMillis();
        PendingIntent viewIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, AlarmActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager.AlarmClockInfo info = new AlarmManager.AlarmClockInfo(timeInMillis, viewIntent);
        alarmManager.setAlarmClock(info, operation);
    }
}