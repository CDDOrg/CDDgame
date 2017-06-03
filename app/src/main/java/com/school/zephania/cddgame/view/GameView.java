package com.school.zephania.cddgame.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.school.zephania.cddgame.model.God;

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
            while(threadFlag){
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

 /*   public GameView(Context context, AttributeSet attr) {
        super(context, attr);
        god = new God();
        this.setOnTouchListener(this);
        this.getHolder().addCallback(this);
    }//暂时解决gameview调用God的函数的问题。先不加载布局文件，用加载view的方式解决。*/

    public GameView(Context context, God god) {
        super(context);
        this.god=god;
        this.setOnTouchListener(this);
        this.getHolder().addCallback(this);
    }



    protected void  myDraw(Canvas canvas){
        god.paint(canvas);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if(event.getAction()!=MotionEvent.ACTION_UP)
        {
            return true;
        }
        System.out.println(event.getX() + "  " + event.getY()+"-"+(event.getAction()==MotionEvent.ACTION_UP));//测设用
        god.onTouch(v, event);
//		threadFlag=!threadFlag;
        return true;
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
