package com.symbol.music_beta;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Created by MKJ467 on 7/8/2015.
 */
public class ListenForHeadphones extends Service {

    private MediaPlayer mp;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Started from onStartCommand", Toast.LENGTH_LONG).show();
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(receiver, filter);
        Toast.makeText(this, "Headphone BroadcastReceiver Registered", Toast.LENGTH_LONG).show();
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                int state = intent.getIntExtra("state", -1);
                switch (state) {
                    case 0:
                        Toast.makeText(context, "Headphones have been unplugged", Toast.LENGTH_LONG).show();
                        if(mp != null){
                            mp.stop();
                            mp = null;
                        }
                        break;
                    case 1:
                        Toast.makeText(context, "Headphones have been plugged in", Toast.LENGTH_LONG).show();
                        if(mp == null){
                            //get random song to play from music library
                            mp = MediaPlayer.create(context, R.raw.sample);
                            mp.start();
                        }
                        break;
                }
            }
        }
    };

}
