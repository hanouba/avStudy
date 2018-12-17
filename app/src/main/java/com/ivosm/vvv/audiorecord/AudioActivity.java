package com.ivosm.vvv.audiorecord;

import android.Manifest;
import android.media.AudioRecord;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ivosm.vvv.R;

public class AudioActivity extends AppCompatActivity implements View.OnClickListener {
    private Button start,pause;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO,Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS}, 5);
        }

        init();
        start.setOnClickListener(this);
        pause.setOnClickListener(this);
    }

    private void init() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_start_record:

                break;
            case R.id.bt_pause_record:

                break;
        }
    }
}
