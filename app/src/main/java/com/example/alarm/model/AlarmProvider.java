package com.example.alarm.model;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.alarm.services.Notifications;

public class AlarmProvider extends ContentProvider {

    private AlarmDatabaseHelper dbHelper;
    private static final int ALARMS = 1;
    private static final int ALARMS_ID = 2;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AlarmContract.AUTHORITY, AlarmContract.ALARMS_TABLE_NAME, ALARMS);
        uriMatcher.addURI(AlarmContract.AUTHORITY, AlarmContract.ALARMS_TABLE_NAME + "/#", ALARMS_ID);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new AlarmDatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(AlarmContract.ALARMS_TABLE_NAME);
        switch (uriMatcher.match(uri)) {
            case ALARMS:
                break;
            case ALARMS_ID:
                queryBuilder.appendWhere(AlarmContract.AlarmsColumns._ID + " = " + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        Cursor cursor = queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
        if (cursor != null) cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case ALARMS:
                count = db.delete(AlarmContract.ALARMS_TABLE_NAME, selection, selectionArgs);
                break;
            case ALARMS_ID:
                selection = ((selection != null && !selection.equals(""))
                        ? "(" +selection + ") AND "
                        : "") +
                        "(" + AlarmContract.AlarmsColumns._ID +" = " + uri.getLastPathSegment() + ")";
                count = db.delete(AlarmContract.ALARMS_TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Cannot delete from URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        Notifications.updateNextAlarmNotification(getContext());
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case ALARMS_ID:
                /* TODO */
                selection = ((selection != null && !selection.equals(""))
                        ? "(" +selection + ") AND "
                        : "") +
                        "(" + AlarmContract.AlarmsColumns._ID +" = " + uri.getLastPathSegment() + ")";
                count = db.update(AlarmContract.ALARMS_TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Cannot update URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        Notifications.updateNextAlarmNotification(getContext());
        return count;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowId;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case ALARMS:
                rowId = db.insert(AlarmContract.ALARMS_TABLE_NAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Cannot insert from URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(AlarmContract.ALARMS_CONTENT_URI, null);
        Uri uriResult = ContentUris.withAppendedId(AlarmContract.ALARMS_CONTENT_URI, rowId);
        Notifications.updateNextAlarmNotification(getContext());
        return uriResult;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ALARMS:
                return "vnd.android.cursor.dir/" + AlarmContract.ALARMS_TABLE_NAME;
            case ALARMS_ID:
                return "vnd.android.cursor.item/" + AlarmContract.ALARMS_TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }
}
