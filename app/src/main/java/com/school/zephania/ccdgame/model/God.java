package com.school.zephania.ccdgame.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.WindowManager;

import com.school.zephania.ccdgame.CCDgame;
import com.school.zephania.ccdgame.R;


/**
 * Created by zephania on 17-5-23.
 */

public class God {
    private int state;
    CCDgame ccd;
    public God(CCDgame ccd){
        this.ccd=ccd;
        int state=0;


    }


    Rect BackSrc,BackDst;
    Bitmap gameBackGround;
    Bitmap[] cardsmaps=new Bitmap[52];
    int mWidth,mHeight;
    Player[] player= new Player[4];



    private void viewInit(){
        gameBackGround= BitmapFactory.decodeResource(ccd.getResources(), R.drawable.backgroundgaming);
        WindowManager wm = (WindowManager) ccd.getBaseContext()
                .getSystemService(Context.WINDOW_SERVICE);

        mWidth = wm.getDefaultDisplay().getWidth();
        mHeight = wm.getDefaultDisplay().getHeight();
        BackSrc= new Rect(0,0,gameBackGround.getWidth(),gameBackGround.getHeight());
        BackDst= new Rect(0,0,mWidth,mHeight);

        player[0] = new Player(ccd,10,mHeight/4);
        player[1] = new Player(ccd,mWidth*5/20,10);
        player[2] = new Player(ccd,mWidth*18/20,mHeight/4);
        player[3] = new Player(ccd,mWidth*5/20,mHeight*3/4);

        //初始化卡牌图片
        for (int i=0;i<52;i++)
         cardsmaps[i]=BitmapFactory.decodeResource(ccd.getResources(),R.drawable.card1+i);

    }
    public void paint(Canvas canvas){
        switch(state){
            case 0:
                paintGame(canvas);
                break;
        }
    }
    private void paintGame(Canvas canvas){
        //画背景
        canvas.drawBitmap(gameBackGround,BackSrc,BackDst,null);
        //画人物
        for (int i=0;i<4;i++)
        {
            player[i].paint(canvas);
        }
        canvas.drawBitmap(cardsmaps[0],0,0,null);

        //画牌

    }


    public void gameLogic() {
        viewInit();
    }
}

