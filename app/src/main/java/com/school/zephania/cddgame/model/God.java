package com.school.zephania.cddgame.model;

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

import com.school.zephania.cddgame.R;
import com.school.zephania.cddgame.view.GameView;

import java.util.Random;


/**
 * Created by zephania on 17-5-23.
 */

public class God extends AppCompatActivity {
    //游戏逻辑相关
    private GameView gameView;
    public static Context sContext;
    private int state=-1;
    private Player[] players= new Player[4];
    //绘图相关
    private Rect BackSrc,BackDst;
    private Bitmap gameBackGround;
    public static int mWidth,mHeight;
    public static int cardMargin=50;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sContext=this;
        for (int i=0;i<players.length;i++){
            players[i] = new Player();
        }

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        GameView gameview =new GameView(this,this);
        setContentView(gameview);

        //setContentView(R.layout.activity_cddgame);//暂时解决gameview调用God的函数的问题。先不加载布局文件，用加载view的方式解决。

    }
    private void init(){
        distributeCards();
        viewInit();
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
        players[3].setView(10,mHeight/4);
        players[2].setView(mWidth*5/20,10);
        players[1].setView(mWidth*18/20,mHeight/4);
        players[0].setView(mWidth*5/20,mHeight*3/4);
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
        //画牌
        players[0].paintCards(canvas, Player.Mode.DOWN);
        players[1].paintCards(canvas, Player.Mode.RIGHT);
        players[2].paintCards(canvas, Player.Mode.UP);
        players[3].paintCards(canvas, Player.Mode.LEFT);
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

