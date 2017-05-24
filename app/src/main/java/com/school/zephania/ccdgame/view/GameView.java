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

import com.school.zephania.ccdgame.R;
import com.school.zephania.ccdgame.model.God;

/**
 * Created by zephania on 17-5-23.
 */

public class GameView extends View implements View.OnTouchListener {
    private God god;
    private Bitmap gameBackGround;
    private int mWidth,mHeight;
    public GameView(Context context){
        super(context);
        God god = new God();

        gameBackGround=BitmapFactory.decodeResource(getResources(), R.drawable.backgroundgaming);
        this.setOnTouchListener(this);

    }




    @Override
    protected void  onDraw(Canvas canvas){
        Log.d("width","height:"+mWidth);
        Rect src = new Rect(0,0,gameBackGround.getWidth(),gameBackGround.getHeight());
        Rect dst = new Rect(0,0,mWidth,mHeight);
        canvas.drawBitmap(gameBackGround,src,dst,new Paint());

    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
    }
}
