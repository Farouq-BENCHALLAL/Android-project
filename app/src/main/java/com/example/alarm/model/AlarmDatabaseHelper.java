package com.example.alarm.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlarmDatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "alarms.db";

    public AlarmDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /* TODO : créer la table avec createAlarmsTable. */
        createAlarmsTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /* TODO : supprimer la table avec dropAlarmsTable. */
        dropAlarmsTable(db);

        /* TODO : recréer la table avec createAlarmsTable. */
        createAlarmsTable(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private static void createAlarmsTable(SQLiteDatabase db) {
        String SQLAlarmsTable =
                "CREATE TABLE alarms (" +
                        AlarmContract.AlarmsColumns._ID + " INTEGER PRIMARY KEY, "+
                        AlarmContract.AlarmsColumns.HOUR + " INTEGER NOT NULL, " +

                        AlarmContract.AlarmsColumns.MINUTES + " INTEGER NOT NULL, " +
                        AlarmContract.AlarmsColumns.ENABLED + " INTEGER NOT NULL " + " ) ";

        db.execSQL(SQLAlarmsTable);
    }

    private static void dropAlarmsTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS alarms;");
    }
}