package com.example.alarm.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.alarm.R;
import com.example.alarm.model.Alarm;

public class AlarmViewHolder extends RecyclerView.ViewHolder {
    private final TextView timeTextView;
    private final Switch enabledSwitch;
    private final ImageButton deleteButton;
    private Alarm alarm;

    public AlarmViewHolder(View view, final AlarmClickHandler alarmClickHandler) {
        super(view);
        timeTextView = view.findViewById(R.id.time_text_view); /* TODO : récupérer l'élément d'id R.id.time_text_view dans la vue. */
        enabledSwitch = view.findViewById(R.id.enabled_switch); /* TODO : récupérer l'élément d'id R.id.activated_switch dans la vue. */
        deleteButton = view.findViewById(R.id.delete_button); /* TODO : récupérer l'élément d'id R.id.delete_button dans la vue. */
        timeTextView.setOnClickListener(v -> alarmClickHandler.onTimeClicked(alarm));
        enabledSwitch.setOnCheckedChangeListener((buttonView,isChecked)-> alarmClickHandler.setAlarmEnabled(alarm, isChecked));
        deleteButton.setOnClickListener(v -> alarmClickHandler.onDeleteClicked(alarm));
    }

    public void bindAlarm(Alarm alarm) {
        this.alarm = alarm;
        /* TODO : affecter la chaîne retournée par alarm.text() à timeTextView. */
        timeTextView.setText(alarm.text());
        /* TODO : faire correspondre l'état de enabledSwitch à alarm.enabled. */
        enabledSwitch.setChecked(alarm.enabled);
    }

    public void clearData() {
        this.alarm = null;
    }
}

