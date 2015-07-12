package com.symbol.music_beta;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
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

    private ArrayList<String> songPaths;
    public boolean isRunning = false;

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
                switch(state){
                    case 1:
                        Toast.makeText(context, "Headphones have been plugged in", Toast.LENGTH_LONG).show();
                        MusicTask m = new MusicTask();
                        m.execute();
                        isRunning = true;
                        break;
                    case 0:
                        Toast.makeText(context, "Headphones have been unplugged", Toast.LENGTH_LONG).show();
                        isRunning = false;
                        break;
                }
            }
        }
    };


    class MusicTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

       @Override
        protected Void doInBackground(Void ...params) {

            MediaPlayer mp = new MediaPlayer();
           while(true){//keep playing songs until headphones are unplugged
               if(!mp.isPlaying()){
                   playSong(mp);
               }
               if(!isRunning){
                   mp.stop();
                   mp.release();
                   break;
               }
           }
           return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }

    }
    public void playSong(MediaPlayer mp){
        try{
            Random rand = new Random();
            int randSong = rand.nextInt(songPaths.size()-7)+7;//skip preloaded crap
            mp.setDataSource(songPaths.get(randSong));
            mp.prepare();
            mp.start();
        }catch(IOException e){}
    }
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
