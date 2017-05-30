package com.example.cddgame.Controller;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.cddgame.R;
import com.example.cddgame.model.Card;
import com.example.cddgame.model.Player;
import com.example.cddgame.view.GameView;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by zephania on 17-5-23.
 */

public class God extends AppCompatActivity{
    //游戏逻辑相关
    private GameView gameView;
    public static Context sContext;
    private int state=-1;
    private Player[] players= new Player[4];
    //绘图相关
    private Rect BackSrc,BackDst;
    private Bitmap gameBackGround;
    private int mWidth,mHeight;
    private int cardMargin=50;
//   public God(Context cdd){
//        Log.d("txt","God constuction function");
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sContext=this;
        for (int i=0;i<players.length;i++){
            players[i] = new Player();
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cddgame);
    }
    private void init(){
        distributeCards();
        viewInit();

        //为绘制图像使用的简陋发牌。
        // player[2].setHandCards(cards.subList(0,12));

    }
    private void viewInit(){
        //获取屏幕宽高
        DisplayMetrics dm;
        dm = sContext.getResources().getDisplayMetrics();
        mWidth = dm.widthPixels;// 宽度
        mHeight = dm.heightPixels;// 高度

        gameBackGround=BitmapFactory.decodeResource(sContext.getResources(), R.drawable.backgroundgaming);
        //设定背景区域
        BackSrc= new Rect(0,0,gameBackGround.getWidth(),gameBackGround.getHeight());
        BackDst= new Rect(0,0,mWidth,mHeight);

        //设定头像区域
        players[0].setView(10,mHeight/4);
        players[1].setView(mWidth*5/20,10);
        players[2].setView(mWidth*18/20,mHeight/4);
        players[3].setView(mWidth*5/20,mHeight*3/4);
//        //初始化卡牌图片
//        for (int i=0;i<52;i++)
//            cards.get(i).setImage(BitmapFactory.decodeResource(.getResources(),R.drawable.card1+i));
    }

    public void distributeCards(){//发牌
        int[] cards = new int[52];
        for (int i=0;i<cards.length;i++){
            cards[i]=i;
        }
        shuffle(cards);

        for (int i=0;i<cards.length;i++){
            for (int j=0;j<players.length;j++){
                players[j].addCard(new Card(cards[i]));
            }
        }
    }
    private void shuffle(int[] cards){//洗牌
        Random random = new Random();
        for (int i=0;i<50;i++){
            int a = Math.abs(random.nextInt())%52;
            int b = Math.abs(random.nextInt())%52;
            int temp = cards[a];
            cards[a] = cards[b];
            cards[b] = temp;
        }
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
            players[i].paint(canvas);
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

    private void gaming(){}
}

