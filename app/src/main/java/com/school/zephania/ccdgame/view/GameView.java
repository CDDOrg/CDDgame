package com.school.zephania.ccdgame.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Debug;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.school.zephania.ccdgame.model.God;
import com.school.zephania.ccdgame.model.Player;

/**
 * Created by zephania on 17-5-23.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {
    private God god;
    private SurfaceHolder holder;
    private boolean threadFlag=true;
    private Canvas canvas;

    Thread gameThread = new Thread() {
        @Override
        public void run() {
            holder=getHolder();
            while(threadFlag)
            {
                god.gameLogic();
                try {
                    canvas = holder.lockCanvas();
                    myDraw(canvas);
                } finally {
                    holder.unlockCanvasAndPost(canvas);
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    };

    public GameView(Context context){
        super(context);
        god = new God(context);
        this.setOnTouchListener(this);
        this.getHolder().addCallback(this);
    }

    protected void  myDraw(Canvas canvas){
            god.paint(canvas);
    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        threadFlag=true;
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        threadFlag=false;
        boolean retry = true;
        while (retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }
}
