package com.example.alarm.services;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.util.Log;

import java.io.IOException;

public class Ringtone {
    private Context context;
    private AudioManager audioManager;
    private MediaPlayer mediaPlayer;

    public Ringtone(Context context) {
        this.context = context;
    }

    public void start() {
        try {
            if (audioManager == null) {
                audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            }
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
            mediaPlayer.setVolume(1, 1);
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build();
            mediaPlayer.setAudioAttributes(attributes);
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) { Log.d("Alarm", e.getMessage()); }
    }

    public void stop() {
        if (mediaPlayer!=null) { mediaPlayer.stop(); mediaPlayer.release(); mediaPlayer = null; }
    }
}