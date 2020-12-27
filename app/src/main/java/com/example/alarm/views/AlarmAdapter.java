package com.example.alarm.views;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alarm.R;
import com.example.alarm.model.Alarm;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmViewHolder> {

    private Cursor cursor;
    private AlarmClickHandler alarmClickHandler;

    public AlarmAdapter(AlarmClickHandler alarmClickHandler) {
        this.alarmClickHandler = alarmClickHandler;
        setHasStableIds(true);
    }

    @Override
    public AlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).
                inflate(viewType, parent, false);
           /* TODO : faire en sorte de construire une vue
                     à l'aide de l'indentifiant viewType.
                     (http://developer.android.com/training/material/lists-cards.html) */
        return new AlarmViewHolder(view, alarmClickHandler);
    }

    @Override
    public void onViewRecycled(AlarmViewHolder viewHolder) {
        super.onViewRecycled(viewHolder);
        viewHolder.clearData();
    }

    @Override
    public void onBindViewHolder(AlarmViewHolder viewHolder, int position) {
        boolean hasMoved = cursor.moveToPosition(position);/* TODO : déplacer le curseur à la bonne position */
        if (!hasMoved) return;
        Alarm alarm = new Alarm(cursor); /* TODO : créer une alarme à partir du curseur. */;
        viewHolder.bindAlarm(alarm);
    }

    @Override
    public int getItemCount() {
        /* TODO : retourner 0 si cusor est nul, sinon le nombre d'éléments dedans le curseur. */
        if (cursor == null)
            return 0;
        return cursor.getCount();
    }

    @Override
    public int getItemViewType(int position) {
        /* TODO : retourner R.layout.alarm_row */
        return R.layout.alarm_row;
    }

    public void swapCursor(Cursor cursor) {
        if (this.cursor == cursor) {
            return;
        }
        if (this.cursor != null) {
            this.cursor.close();
        }
        this.cursor = cursor;
        notifyDataSetChanged();
    }
}


