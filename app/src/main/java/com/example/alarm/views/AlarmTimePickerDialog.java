package com.example.alarm.views;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.TimePicker;

import com.example.alarm.model.Alarm;

import java.util.Calendar;

public class AlarmTimePickerDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private static final String TAG = "alarm_time_picker_dialog";

    public static void show(FragmentManager fragmentManager, long id) {
        Log.d("onShow", ""+id);
        DialogFragment newFragment = AlarmTimePickerDialog.newInstance(id);
        newFragment.show(fragmentManager, TAG);
    }

    private static AlarmTimePickerDialog newInstance(long id) {
        AlarmTimePickerDialog alarmTimePickerDialog = new AlarmTimePickerDialog();
        Bundle args = new Bundle();
        args.putLong("id", id);
        alarmTimePickerDialog.setArguments(args);
        return alarmTimePickerDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), this, hour, minute, true);
    }

    private long id() {
        return getArguments().getLong("id", 0);
    }

    @Override
    public void onTimeSet(TimePicker view, int hour, int minutes) {
        long id = id();
        ContentResolver resolver = getActivity().getContentResolver();
        if (id == -1) {
            Alarm alarm = new Alarm(hour, minutes, true);
            /* TODO : ajouter l'alarme à la base de données */
            Alarm.addAlarm(resolver, alarm);
            return;
        }
        Alarm alarm = Alarm.getAlarm(resolver, id) ;/* TODO : récupérer l'alarme dans la BD. */
        alarm.hour = hour;
        alarm.minutes = minutes;
        Alarm.updateAlarm(resolver, alarm);
        /* TODO : mettre à jour l'alarme dans la BD. */
    }
}
