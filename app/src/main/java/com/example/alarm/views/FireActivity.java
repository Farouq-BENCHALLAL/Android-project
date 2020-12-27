package com.example.alarm.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.example.alarm.R;
import com.example.alarm.services.FireService;

public class FireActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);

        getSupportActionBar().hide();

        setContentView(R.layout.fire_activity);
        View view = findViewById(R.id.clock);

        view.setOnClickListener(v -> onClick());
    }

    private void onClick() {
        final Intent intent = new Intent(getBaseContext(), FireService.class).setAction(FireService.ACTION_STOP);
          /* TODO : créer un intent vers le service
                    pour stopper la sonnerie. */;
        startService(intent);
        finish();
    }

    private class BroadcastReceiver extends android.content.BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            /* TODO : appeler la méthode finish de l'activité. */
            finish();
        }
    }

}