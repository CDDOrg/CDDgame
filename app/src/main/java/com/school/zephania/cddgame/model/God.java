package com.school.zephania.cddgame.model;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.school.zephania.cddgame.R;
import com.school.zephania.cddgame.view.GameView;
import java.util.Random;

import static android.os.SystemClock.*;


/**
 * Created by zephania on 17-5-23.
 */

public class God extends Activity {
    //游戏逻辑相关
    private GameView gameView;
    public static Context sContext;
    private int state=-1;
    private int currentPlayer;//当前出牌的玩家
    private int maxSendCardPlayer = -1;//出了场上最大的牌的玩家


    private AIDataModel[] AIs = new AIDataModel[4]; //三个AI
    private Player player = new Player();  //一个玩家
    public static enum turnType {UP,DOWN,LEFT,RIGHT};
    public static boolean isPlayerTurn=true;
    public static turnType turn=turnType.DOWN;
    public static boolean finish=false;
    private Player[] players= new Player[4];

    private TypeNumCouple maxTypeNumCouple;//当前场上最大的牌组

    //绘图相关

    private Rect BackSrc,BackDst;
    private Bitmap gameBackGround;
    private Rect chupaiSrc,chupaiDst;
    private Bitmap chupai,chupai2;
    private Rect passSrc,passDst;
    private Bitmap pass;
    public static int mWidth,mHeight;
    public static int cardMargin=50;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sContext=this;
        for (int i=0;i<players.length;i++){
            players[i] = new Player();
        }
        for(int i=0;i<3;i++){
            AIs[i] = new AIDataModel();
        }

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        GameView gameview =new GameView(this,this);
        maxTypeNumCouple = new TypeNumCouple();
        setContentView(gameview);

        //setContentView(R.layout.activity_cddgame);//暂时解决gameview调用God的函数的问题。先不加载布局文件，用加载view的方式解决。

    }
    private void init(){
        distributeCards();
        //print player card info
        //player.printCardInfo();
        currentPlayer=0;
        viewInit();
    }
    private void viewInit(){
        //获取屏幕宽高
        DisplayMetrics dm;
        dm = sContext.getResources().getDisplayMetrics();
        mWidth = dm.widthPixels;// 宽度
        mHeight = dm.heightPixels;// 高度
        //背景相关
        gameBackGround=BitmapFactory.decodeResource(sContext.getResources(), R.drawable.backgroundgaming);
        //设定背景区域
        BackSrc= new Rect(0,0,gameBackGround.getWidth(),gameBackGround.getHeight());
        BackDst= new Rect(0,0,mWidth,mHeight);
        //按钮相关
        chupai=BitmapFactory.decodeResource(sContext.getResources(),R.drawable.chupai);
        chupai2=BitmapFactory.decodeResource(sContext.getResources(),R.drawable.chupai2);
        chupaiSrc= new Rect(0,0,chupai.getWidth(),chupai.getHeight());
        chupaiDst= new Rect(mWidth*8/20,mHeight*4/6-30,mWidth*8/20+chupai.getWidth()/2,chupai.getHeight()/2+mHeight*4/6-30);
        pass=BitmapFactory.decodeResource(sContext.getResources(),R.drawable.pass);
        passSrc= new Rect(0,0,pass.getWidth(),pass.getHeight());
        passDst= new Rect(mWidth*10/20,mHeight*4/6-30,mWidth*10/20+chupai.getWidth()/2,chupai.getHeight()/2+mHeight*4/6-30);
        //设定头像区域
        AIs[2].setView(10,mHeight/4);
        AIs[1].setView(mWidth*5/20,10);
        AIs[0].setView(mWidth*18/20,mHeight/4);
       // players[0].setView(mWidth*5/20,mHeight*3/4);
        player.setView(mWidth*5/20,mHeight*3/4);
        //player.setView(mWidth*5/20,mHeight*3/4);
    }

    public void distributeCards(){//发牌
        int[] cards = new int[52];
        for (int i=0;i<cards.length;i++){
            cards[i]=i;
        }
        shuffle(cards);

        for (int i=0;i<cards.length;i+=4){
            for (int j=0;j<players.length;j++){
                players[j].addCard(new Card(cards[i]));
            }
        }

        for(int i=0;i<cards.length;i+=4){
            for (int j=0;j<4;j++){
                if(j==0){
                    player.addCard(new Card(cards[i]));
                }else {
                    AIs[j - 1].addCard(new Card(cards[i]));
                }
            }
        }
        for(int i=0;i<3;i++){
            AIs[i].init();
            AIs[i].deal();
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
        player.paint(canvas);
        for (int i=0;i<3;i++)
        {
            AIs[i].paint(canvas);
        }
        //画牌
        player.paintCards(canvas, Player.Mode.DOWN);

         AIs[0].paintCards(canvas, Player.Mode.RIGHT);
         AIs[1].paintCards(canvas, Player.Mode.UP);
         AIs[2].paintCards(canvas, Player.Mode.LEFT);
        //画按钮
        if(isPlayerTurn){
            if(player.getSendCardTag())
                canvas.drawBitmap(chupai, chupaiSrc, chupaiDst, null);
            else
                canvas.drawBitmap(chupai2, chupaiSrc, chupaiDst, null);
            canvas.drawBitmap(pass, passSrc, passDst, null);
        }

//        if (currentPlayer == 0) {
//            if(players[0].getSendCardTag())
//                canvas.drawBitmap(chupai, chupaiSrc, chupaiDst, null);
//            else
//                canvas.drawBitmap(chupai2, chupaiSrc, chupaiDst, null);
//            canvas.drawBitmap(pass, passSrc, passDst, null);
//        }
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

    private void gaming(){
        if(isPlayerTurn){
            if(player.getLast().isEqual(maxTypeNumCouple))//如果没人大的起自己的牌就可以随便出
                maxTypeNumCouple.reset();
            return;
        }
        switch (turn){
            case RIGHT:
                if(finish){
                    break;
                }
                Log.d("AI","before ai[]"+0+"出牌, maxTypeNumCouple is "+ (maxTypeNumCouple.getNUM()/4+3));
                maxTypeNumCouple = AIs[0].sendCards(maxTypeNumCouple);
                Log.d("AI","AI"+0+"出牌: "+maxTypeNumCouple.getCardType()+"  "+maxTypeNumCouple.getNUM()+""+" 牌面是 " + (maxTypeNumCouple.getNUM()/4+3));
                break;
            case UP:
                if(finish){
                    break;
                }
                Log.d("AI","before ai[]"+1+"出牌, maxTypeNumCouple is "+ (maxTypeNumCouple.getNUM()/4+3));
                maxTypeNumCouple = AIs[1].sendCards(maxTypeNumCouple);
                Log.d("AI","AI"+1+"出牌: "+maxTypeNumCouple.getCardType()+"  "+maxTypeNumCouple.getNUM()+""+" 牌面是 " + (maxTypeNumCouple.getNUM()/4+3));
                break;
            case LEFT:
                if(finish){
                    break;
                }
                Log.d("AI","before ai[]"+2+"出牌, maxTypeNumCouple is "+ (maxTypeNumCouple.getNUM()/4+3));
                maxTypeNumCouple = AIs[2].sendCards(maxTypeNumCouple);
                Log.d("AI","AI"+2+"出牌: "+maxTypeNumCouple.getCardType()+"  "+maxTypeNumCouple.getNUM()+""+" 牌面是 " + (maxTypeNumCouple.getNUM()/4+3));
                break;
        }
    }//游戏运行时逻辑
    private void dealWithChupai(){
        //maxTypeNumCouple = players[0].sendCards(new TypeNumCouple());
        maxTypeNumCouple = player.sendCards();//调用玩家的出牌函数,返回玩家出的牌中最大的牌
        //Log.d("AI","玩家出牌了,maxTypeNumCouple is " + (maxTypeNumCouple.getNUM()/4+3));
        Log.d("AI", "人类玩家出牌："+maxTypeNumCouple.getCardType()+"  "+maxTypeNumCouple.getNUM()+" 牌面是 " + (maxTypeNumCouple.getNUM()/4+3));
        player.setSendCardTag(false);//设置玩家不能出牌
        isPlayerTurn = false;
    }//出牌的响应函数
    private void dealWithPass(){
  //      turn();
        player.cancelSelected();
        isPlayerTurn = false;
        turn=turnType.RIGHT;
    }//pass的响应函数
    private void turn(){//下一位玩家出牌
        currentPlayer = (currentPlayer+1)%4;
        if (currentPlayer == maxSendCardPlayer){
            //如果当前出牌的玩家正是出最大牌的玩家，那么开启新的一轮
            maxTypeNumCouple.reset();
        }
    }

    public static boolean inRect(int x, int y, int rectX, int rectY, int rectW,
                                 int rectH) {
        if (x < rectX || x > rectX + rectW || y < rectY || y > rectY + rectH) {
            return false;
        }
        return true;
    }

    public void onTouch(View v, MotionEvent event){
        Log.d("AI","玩家点击屏幕了");
        if(!isPlayerTurn) {
            Log.d("AI", "此时is not player turn");
            return;
        }
        Log.d("AI", "此时is player turn");
        int x=(int)event.getX();
        int y=(int)event.getY();
        Log.d("AI","player能不能出牌" + player.getSendCardTag());
        if (chupaiDst.contains(x,y)&&player.getSendCardTag()) {
            Log.d("AI","人类玩家出牌");
            dealWithChupai();
        }
        if (passDst.contains(x,y)) {
            Log.d("AI","pass了");
            dealWithPass();
        }
        player.onTouch(v,event, maxTypeNumCouple);
    }
}

