package com.school.zephania.ccdgame.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by zephania on 17-5-23.
 */

public class GameView extends View implements View.OnTouchListener {

    public GameView(Context context){
        super(context);
        this.setOnTouchListener(this);
    }


    @Override
    protected void  onDraw(Canvas canvas){

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
