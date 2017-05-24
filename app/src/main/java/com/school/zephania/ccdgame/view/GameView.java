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
import android.view.WindowManager;

import com.school.zephania.ccdgame.CCDgame;
import com.school.zephania.ccdgame.R;
import com.school.zephania.ccdgame.model.God;
import com.school.zephania.ccdgame.model.Player;

/**
 * Created by zephania on 17-5-23.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {
    God god;
    Bitmap gameBackGround;
    int mWidth,mHeight;
    CCDgame ccd;
    SurfaceHolder holder;
    boolean threadFlag=true;
    Canvas canvas;

    Thread gameThread = new Thread() {
        @Override
        public void run() {
            holder=getHolder();
            while(threadFlag)
            {
                //god.gameLogic(); //后台逻辑
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
    //players are temp for test
    Player[] player= new Player[4];

    public GameView(Context context,CCDgame ccd){
        super(context);
        God god = new God();
        gameBackGround=BitmapFactory.decodeResource(getResources(), R.drawable.backgroundgaming);
        this.setOnTouchListener(this);
        this.getHolder().addCallback(this);



        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        src= new Rect(0,0,gameBackGround.getWidth(),gameBackGround.getHeight());
        dst= new Rect(0,0,width,height);
        mHeight=height;
        mWidth=width;

        player[0] = new Player(ccd,10,mHeight/4);
        player[1] = new Player(ccd,mWidth*5/20,10);
        player[2] = new Player(ccd,mWidth*18/20,mHeight/4);
        player[3] = new Player(ccd,mWidth*5/20,mHeight*3/4);
    }

    Rect src;
    Rect dst;


    protected void  myDraw(Canvas canvas){

        canvas.drawBitmap(gameBackGround,src,dst,null);
        for(int i=0;i<4;i++){
            player[i].paint(canvas);
        }
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
