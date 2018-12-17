package com.ivosm.vvv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ivosm.vvv.audiorecord.AudioActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt1,bt2,bt3,bt4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt1 = findViewById(R.id.bt_1);
        bt2 = findViewById(R.id.bt_2);
        bt3 = findViewById(R.id.bt_3);
        bt4 = findViewById(R.id.bt_4);

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_1:
                Intent intent = new Intent(this,PaintViewActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_2:
                 intent = new Intent(this,AudioActivity.class);
                startActivity(intent);
                break;
        }
    }
}
