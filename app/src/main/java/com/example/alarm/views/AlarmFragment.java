package com.example.alarm.views;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alarm.R;
import com.example.alarm.model.Alarm;
import com.example.alarm.model.AlarmContract;

import static com.example.alarm.R.id.add_alarm_button;

public class AlarmFragment extends Fragment {
    private AlarmAdapter alarmAdapter;

    public AlarmFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(0, null, new LoaderCallbacks());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alarm_fragment, container, false);

        AlarmClickHandler alarmClickHandler = new AlarmClickHandler(this);

        RecyclerView alarmRecyclerView = view.findViewById(R.id.alarm_recycler_view);
        /* TODO : récupérer le RecyclerView dans la vue. */
        alarmRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        alarmRecyclerView.setLayoutManager(layoutManager);

        alarmAdapter = new AlarmAdapter(alarmClickHandler);

        /* TODO : associer l'adapteur au RecyclerView alarmRecyclerView. */

        alarmRecyclerView.setAdapter(alarmAdapter);

        FloatingActionButton addAlarmButton = view.findViewById(add_alarm_button);
        /* TODO : récupérer l'élément d'id R.id.add_alarm_button. */

        addAlarmButton.setOnClickListener(
                /* TODO : faire en sorte d'afficher la boite de dialogue avec l'identifiant Alarm.INVALID_ID. */
                v-> AlarmTimePickerDialog.show(getFragmentManager(), Alarm.INVALID_ID)
        );

        return view;
    }

    private class LoaderCallbacks implements LoaderManager.LoaderCallbacks<Cursor> {
        @Override
        public Loader onCreateLoader(int id, Bundle args) {
            return new CursorLoader(getActivity(), AlarmContract.ALARMS_CONTENT_URI,
                    null, null, null, null);
        }

        @Override
        public void onLoadFinished(Loader loader, Cursor data) {
            /* TODO : utiliser la méthode swapCursor afin
                      de fournir le curseur à l'adaptateur. */
            alarmAdapter.swapCursor(data);
        }

        @Override
        public void onLoaderReset(Loader loader) {
            /* TODO : utiliser la méthode swapCursor afin
                      de supprimer le curseur de l'adaptateur
                      en lui passant une référence nulle. */
            alarmAdapter.swapCursor(null);
        }
    }

}