package com.example.alarm.model;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Alarm {
    public static final long INVALID_ID = -1;

    public long id;
    public int hour;
    public int minutes;
    public boolean enabled;

    public Alarm(int hour, int minutes, boolean enabled) {
        this.id = INVALID_ID;
        this.hour = hour;
        this.minutes = minutes;
        this.enabled = enabled;
    }

    public String text() {
        return String.format("%d:%02d", hour, minutes);
    }

    public Alarm(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndex(AlarmContract.AlarmsColumns._ID));
        hour = cursor.getInt(cursor.getColumnIndex(AlarmContract.AlarmsColumns.HOUR));
        minutes = cursor.getInt(cursor.getColumnIndex(AlarmContract.AlarmsColumns.MINUTES));
        enabled = cursor.getInt(cursor.getColumnIndex(AlarmContract.AlarmsColumns.ENABLED))== 1;
    }

    public static Alarm getAlarm(ContentResolver cr, long alarmId) {
        Uri uri = getUri(alarmId);
        Alarm alarm = null;
        Cursor cursor = cr.query(uri, null, null, null, null);
        boolean hasMoved = cursor.moveToFirst();  /* TODO : se déplacer sur le premier élément (moveToFirst). */
        if (hasMoved) alarm = new Alarm(cursor) ;/* TODO : créer une instance de l'alarme avec le curseur. */
        cursor.close();
        return alarm;
    }

    public static Alarm addAlarm(ContentResolver resolver, Alarm alarm) {
        ContentValues values = createContentValues(alarm);
        Uri uri = resolver.insert(AlarmContract.ALARMS_CONTENT_URI, values);
        alarm.id = ContentUris.parseId(uri);
        return alarm;
    }

    public static boolean updateAlarm(ContentResolver resolver, Alarm alarm) {
        if (alarm.id == Alarm.INVALID_ID) return false;
        ContentValues values =  createContentValues(alarm);/* TODO */
        Uri uri =  Alarm.getUri(alarm.id); /* TODO */
        long rowsUpdated = resolver.update(uri, values, null, null);  /* TODO */
        return rowsUpdated == 1;
    }

    public static boolean deleteAlarm(ContentResolver resolver, long alarmId) {
        if (alarmId == INVALID_ID) return false;
        Uri uri = getUri(alarmId); /* TODO */
        int deletedRows = resolver.delete(uri, null, null); /* TODO */
        return deletedRows == 1;
    }

    private static Uri getUri(long alarmId) {
        return ContentUris.withAppendedId(AlarmContract.ALARMS_CONTENT_URI, alarmId);
    }

    private static ContentValues createContentValues(Alarm alarm) {
        ContentValues values = new ContentValues();
        if (alarm.id != INVALID_ID) {
            values.put(AlarmContract.AlarmsColumns._ID, alarm.id);
        }
        values.put(AlarmContract.AlarmsColumns.HOUR, alarm.hour);
        values.put(AlarmContract.AlarmsColumns.MINUTES, alarm.minutes);
        values.put(AlarmContract.AlarmsColumns.ENABLED, alarm.enabled ? 1 : 0);
        return values;
    }

    public static List<Alarm> getAlarms(ContentResolver resolver) {
        final List<Alarm> result = new ArrayList<>();
    /* TODO : - Faire une requête et récupérer toutes les lignes de la table.
              - Construire une instance de Alarm associée à chaque ligne de la table.
              - Placer ces instances dans la liste result.  */
        Cursor cursor = resolver.query(AlarmContract.ALARMS_CONTENT_URI,null, null,null,null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                result.add(new Alarm(cursor));
            }
            while (cursor.moveToNext());
        }
        return result;
    }

    public long timeInMillis() {
        Calendar currentTime = Calendar.getInstance();
        Calendar nextTime = Calendar.getInstance();
        nextTime.set(Calendar.SECOND, 0);
        nextTime.set(Calendar.HOUR_OF_DAY, hour);
        nextTime.set(Calendar.MINUTE, minutes);
        if (nextTime.getTimeInMillis() <= currentTime.getTimeInMillis()) {
            nextTime.add(Calendar.DAY_OF_YEAR, 1);
        }
        return nextTime.getTimeInMillis();
    }

    public static Alarm nextAlarm(ContentResolver resolver) {
        List<Alarm> alarms = getAlarms(resolver);
        /* TODO : retourner l'alarme active qui a le plus petit timeInMillis. */


        Alarm nextAlarm = null;

        for (Alarm alarm : alarms) {
            if (alarm.enabled) {
                if (nextAlarm == null || alarm.timeInMillis() < nextAlarm.timeInMillis())
                    nextAlarm = alarm;
            }
        }
        return nextAlarm;
    }

}
