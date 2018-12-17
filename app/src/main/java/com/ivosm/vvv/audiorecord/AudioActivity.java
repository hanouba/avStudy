package com.ivosm.vvv.audiorecord;

import android.Manifest;
import android.media.AudioRecord;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ivosm.vvv.MainActivity;
import com.ivosm.vvv.R;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AudioActivity extends AppCompatActivity implements View.OnClickListener {
    private Button start,pause;
    AudioRecorder audioRecorder;
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
        start = findViewById(R.id.bt_start_record);
        pause = findViewById(R.id.bt_pause_record);
        audioRecorder = AudioRecorder.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_start_record:

                new QMUIDialog.MessageDialogBuilder(this)
                        .setTitle("QMUI对话框标题")
                        .setMessage("这是QMUI框架对话框的内容")
                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();


                            }
                        })
                        .addAction("确定", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                                if (audioRecorder.getStatus() == AudioRecorder.Status.STATUS_NO_READY) {
                                    String fileName  = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
                                    audioRecorder.createDefaultAudio(fileName);
                                    audioRecorder.startRecord(null);
                                }
                            }
                        })
                        .show();


                break;
            case R.id.bt_pause_record:
                try {
                    if (audioRecorder.getStatus() == AudioRecorder.Status.STATUS_START) {
                        //暂停录音
                        audioRecorder.pauseRecord();
                        pause.setText("继续录音");
                        break;

                    } else {
                        audioRecorder.startRecord(null);
                        pause.setText("暂停录音");
                    }
                } catch (IllegalStateException e) {
                    Toast.makeText(AudioActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (audioRecorder.getStatus() == AudioRecorder.Status.STATUS_START) {
            audioRecorder.pauseRecord();
            pause.setText("继续录音");
        }
    }

    @Override
    protected void onDestroy() {
        audioRecorder.release();
        super.onDestroy();

    }
}
