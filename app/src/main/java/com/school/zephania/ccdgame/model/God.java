package com.school.zephania.ccdgame.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import com.school.zephania.ccdgame.R;
import java.util.ArrayList;

import static com.school.zephania.ccdgame.CDDgame.cdd;


/**
 * Created by zephania on 17-5-23.
 */

public class God {
    private int state=-1;
    private ArrayList<Card> cards=new ArrayList<>();
    public God(Context cdd){
        Log.d("txt","God constuction function");
    }


    private Rect BackSrc,BackDst;
    private Bitmap gameBackGround;
    private int mWidth,mHeight;
    private Player[] player= new Player[4];
    private int cardMargin=50;

    private void viewInit(){
        //获取屏幕宽高
        DisplayMetrics dm;
        dm = cdd.getResources().getDisplayMetrics();
        mWidth = dm.widthPixels;// 宽度
        mHeight = dm.heightPixels;// 高度

        gameBackGround=BitmapFactory.decodeResource(cdd.getResources(),R.drawable.backgroundgaming);
        //设定背景区域
        BackSrc= new Rect(0,0,gameBackGround.getWidth(),gameBackGround.getHeight());
        BackDst= new Rect(0,0,mWidth,mHeight);

        //设定头像区域
        player[0].setView(10,mHeight/4);
        player[1].setView(mWidth*5/20,10);
        player[2].setView(mWidth*18/20,mHeight/4);
        player[3].setView(mWidth*5/20,mHeight*3/4);
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
        Log.d("txt","paint");
        //画牌
       // for (int i=0;i<player[2].getHandCard().size();i++)
           // player[2].getHandCard().get(i).paint(canvas,mWidth/4+300+i*cardMargin,mHeight*3/4,false);
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

        //为绘制图像使用的简陋发牌。
        player[2].setHandCards(cards.subList(0,12));

    }
    void gaming(){

    }
}

