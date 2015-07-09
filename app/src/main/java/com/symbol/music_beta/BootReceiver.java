package com.symbol.music_beta;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Dave on 7/8/2015.
 */
public class BootReceiver extends BroadcastReceiver{
    @Override public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Boot Complete Received", Toast.LENGTH_LONG).show();
        context.startService(new Intent(context, ListenForHeadphones.class));
    }
}
