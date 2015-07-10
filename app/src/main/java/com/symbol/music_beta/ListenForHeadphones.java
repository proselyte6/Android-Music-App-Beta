package com.symbol.music_beta;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by MKJ467 on 7/8/2015.
 */
public class ListenForHeadphones extends Service {

    private MediaPlayer mp;
    private ArrayList<String> songPaths;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Started from onStartCommand", Toast.LENGTH_LONG).show();
        songPaths = getSongPath();
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
                            //mp = MediaPlayer.create(context, R.raw.sample);
                            mp = new MediaPlayer();
                            try{
                                for(String s : songPaths){
                                    System.out.println(s);
                                }
                                Random rand = new Random();
                                int randSong = rand.nextInt(songPaths.size());
                                mp.setDataSource(songPaths.get(randSong));
                                mp.prepare();
                            }catch(IOException e){
                                System.out.println("IOEXCEPTION");
                            }
                            mp.start();
                        }
                        break;
                }
            }
        }
    };
    public ArrayList<String> getSongPath() {
        ArrayList<String> songPaths = new ArrayList<String>();
        Uri exContent = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String[] projection = new String[]{
                MediaStore.Audio.Media.DATA
        };
        Cursor cursor = getContentResolver().query(exContent, projection, null, null, MediaStore.Audio.Media.DISPLAY_NAME + " DESC");//table - columns - etc...
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            songPaths.add(cursor.getString(0));
            cursor.moveToNext();
        }
        return songPaths;
    }

}
