package com.example.alarm.views;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.example.alarm.model.Alarm;

public class AlarmClickHandler {
    public Fragment fragment;

    public AlarmClickHandler(Fragment fragment) {
        this.fragment = fragment;
    }

    public void onTimeClicked(Alarm alarm) {
        Log.d("Alarm", "onTimeClicked : " + alarm.text());
        AlarmTimePickerDialog.show(fragment.getFragmentManager(), alarm.id);
    }

    public void setAlarmEnabled(Alarm alarm, boolean enabled) {
        if (enabled == alarm.enabled) return;
        /* TODO : modifiez le flag enabled de alarm. */
        alarm.enabled = enabled ;
        /* TODO : mettre à jour l'alarme en utilisant la
                  méthode update de Alarm. */
        Alarm.updateAlarm(fragment.getContext().getContentResolver(),alarm);
    }

    public void onDeleteClicked(Alarm alarm) {
        /* TODO : supprimer en utilisant la méthode
                  delete de Alarm. */
        Alarm.deleteAlarm(fragment.getContext().getContentResolver(), alarm.id);
    }
}
