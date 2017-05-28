package com.school.zephania.ccdgame.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.WindowManager;
import com.school.zephania.ccdgame.R;
import java.util.ArrayList;


/**
 * Created by zephania on 17-5-23.
 */

public class God {
    private Context cdd;
    private int state;
    private ArrayList<Card> cards=new ArrayList<>();
    public God(Context cdd){
        this.cdd=cdd;
        int state=-1;
    }


    private Rect BackSrc,BackDst;
    private Bitmap gameBackGround;
    private int mWidth,mHeight;
    private Player[] player= new Player[4];



    private void viewInit(){
        gameBackGround= BitmapFactory.decodeResource(cdd.getResources(), R.drawable.backgroundgaming);
        WindowManager wm = (WindowManager) cdd.
                getSystemService(Context.WINDOW_SERVICE);

        mWidth = wm.getDefaultDisplay().getWidth();
        mHeight = wm.getDefaultDisplay().getHeight();
        BackSrc= new Rect(0,0,gameBackGround.getWidth(),gameBackGround.getHeight());
        BackDst= new Rect(0,0,mWidth,mHeight);

        player[0].setView(cdd,10,mHeight/4);
        player[1].setView(cdd,mWidth*5/20,10);
        player[2].setView(cdd,mWidth*18/20,mHeight/4);
        player[3].setView(cdd,mWidth*5/20,mHeight*3/4);
        //初始化卡牌图片
        for (int i=0;i<52;i++)
         cards.get(i).setImage(BitmapFactory.decodeResource(cdd.getResources(),R.drawable.card1+i));

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
        //画牌

    }


    public void gameLogic() {
        switch (state){
            case -1:
                init();
                state=0;
                break;
            case 0:
                gaming();
                break;

        }
    }
    void init(){
        player[0] = new Player();
        player[1] = new Player();
        player[2] = new Player();
        player[3] = new Player();
        for (int i=0;i<52;i++)
            cards.add(new Card());
        viewInit();
        player[0].setHandCards(cards.subList(0,12));

    }
    void gaming(){

    }
}

