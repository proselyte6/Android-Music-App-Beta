package com.symbol.music_beta;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class MainActivity extends Activity {
    private RadioGroup options;
    private Button playlistEdit;
    private Button saveSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        options = (RadioGroup) findViewById(R.id.options);
        playlistEdit = (Button) findViewById(R.id.playlistEdit);
        saveSettings = (Button) findViewById(R.id.saveSettings);
    }
    public void editPlaylists(View v) {}

    public void changeSettings(View v) {}

    }
