package com.example.alarm.model;

import android.net.Uri;
import android.provider.BaseColumns;

public class AlarmContract {
    public static final String AUTHORITY = "com.example.alarm";
    public static final String ALARMS_TABLE_NAME  = "alarms";
    public static final Uri ALARMS_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + ALARMS_TABLE_NAME);

    public interface AlarmsColumns extends BaseColumns {
        String HOUR = "hour";
        String MINUTES = "minutes";
        String ENABLED = "enabled";
    }
}
