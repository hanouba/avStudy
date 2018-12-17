package com.ivosm.vvv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;

public class PaintViewActivity extends AppCompatActivity {
    private SurfaceView surfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint_view);
        surfaceView = findViewById(R.id.sface_view);

    }
}
