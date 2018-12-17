package com.ivosm.vvv.wiget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.ivosm.vvv.R;

/**
 * 创建者 by ${HanSir} on 2018/11/22.
 * 版权所有  WELLTRANS.
 * 说明     用surfaceview 绘制一张图片
 */

public class SfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;

    private DrawThread mThread = null;

    public SfaceView(Context context) {
        this(context,null);
    }


    public SfaceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    public SfaceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //得到控制器
            surfaceHolder = getHolder();
            //对surfaceview进程操作
        surfaceHolder.addCallback(this);
            //得到画笔
        setZOrderOnTop(true);
        setZOrderMediaOverlay(true);
        mThread = new DrawThread(surfaceHolder);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.e("SfaceView","surfaceCreated");
        mThread.setRun(true);
        mThread.run();


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mThread.setRun(false);
    }

    public class DrawThread implements Runnable {
        private SurfaceHolder mHolder = null;
        private boolean isRun = false;
        public DrawThread(SurfaceHolder holder) {
            mHolder = holder;

        }

        public void setRun(boolean run) {
            isRun = run;
        }

        @Override
        public void run() {
            int count = 0;
            while (isRun) {
                {
                    Canvas canvas = null;
                    synchronized (mHolder) {
                        try {

                            canvas = mHolder.lockCanvas();
                            canvas.drawColor(Color.WHITE);
                            Paint p = new Paint();
                            p.setColor(Color.BLACK);

                            Rect r = new Rect(100, 50, 300, 250);
                            canvas.drawRect(r, p);
                            canvas.drawText("这是第" + (count++) + "秒", 100, 310, p);

                            Thread.sleep(1000);// 睡眠时间为1秒

                        } catch (Exception e) {

                            e.printStackTrace();

                        } finally {
                            if (null != canvas) {
                                mHolder.unlockCanvasAndPost(canvas);
                            }
                        }
                    }
                }
            }
        }


    }
}
