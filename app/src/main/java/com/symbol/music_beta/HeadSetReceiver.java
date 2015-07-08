package com.symbol.music_beta;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by MKJ467 on 7/8/2015.
 */
public class HeadSetReceiver extends BroadcastReceiver {
    @Override public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
            int state = intent.getIntExtra("state", -1);
            switch (state) {
                case 0:
                    Toast.makeText(context, "Headphones have been unplugged", Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    Toast.makeText(context, "Headphones have been plugged in", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
}

