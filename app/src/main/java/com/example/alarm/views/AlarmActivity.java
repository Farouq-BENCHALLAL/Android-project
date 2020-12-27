package com.example.alarm.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.alarm.R;
import android.support.v4.app.Fragment;

public class AlarmActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_activity);
        setTitle(R.string.app_name);
        Fragment mainFragment = new AlarmFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mainFragment)
                .commit();
    }
}
