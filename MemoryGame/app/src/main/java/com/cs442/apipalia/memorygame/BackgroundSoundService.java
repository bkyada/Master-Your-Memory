package com.cs442.apipalia.memorygame;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class BackgroundSoundService extends Service {
    MediaPlayer player;
    public BackgroundSoundService() {
    }

    @Override
    public void onStart(Intent intent, int startId) {

        super.onStart(intent, startId);
        Log.e("dawd", "onStart: ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd  sdadsadsas as das dsadas ");

    }


    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(BackgroundSoundService.this, R.raw.backgroun);
        player.setLooping(true);
        player.setVolume(100, 100);
        Log.e("dawd", "onCreate: ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd  sdadsadsas as das dsadas ");

    }

    @Override
    public void onDestroy() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
        Log.e("dawd", "onDestroy: ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd  sdadsadsas as das dsadas ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        Log.e("dawd", "onStartCommand: ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd  sdadsadsas as das dsadas ");

        return START_NOT_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        Log.e("dawd", "onBind: ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd  sdadsadsas as das dsadas ");

        throw new UnsupportedOperationException("Not yet implemented");
    }

}
