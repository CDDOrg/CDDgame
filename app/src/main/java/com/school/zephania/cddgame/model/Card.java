package com.school.zephania.cddgame.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.school.zephania.cddgame.R;
import com.school.zephania.cddgame.view.CardResource;

import java.util.ArrayList;

import static com.school.zephania.cddgame.model.God.sContext;


/**
 * Created by Zephania on 2017/5/27.
 */

public class Card {
    private int NUM;   //牌的编号，【0，51】，用于设置图片资源和比较大小，编号越大牌越大
    private int number; //牌实际大小【3，15】，用于检测顺子
    private int suit;  //牌的花色，【0，3 】，用于检测同花，按照方块、梅花、红桃、黑桃顺序

    private boolean isSelected=false;
    private Bitmap cardmap;
    private ArrayList<Card> handcards=new ArrayList<>();

    public static int x;//牌的x
    public static int y;//牌的y
    public static int cardWidth;//牌的高度
    public static int cardHeight;//牌的高度

    public Card(int NUM){
        this.NUM = NUM;
        number = NUM/4 + 3;
        suit = NUM % 4;

        setImage(BitmapFactory.decodeResource(sContext.getResources(), CardResource.res[NUM]));//设定牌的图片
        cardWidth=cardmap.getWidth()*3/4;
        cardHeight=cardmap.getHeight()*3/4;
    }

    public int getNUM() {
        return NUM;
    }

    public int getNumber() {
        return number;
    }

    public int getSuit() {
        return suit;
    }
    public void changeSelected(){
        isSelected=!isSelected;
    }
    public boolean isSelected() {
        return isSelected;
    }

    void setImage(Bitmap cardmap){
        this.cardmap=cardmap;
    }
    void paint(Canvas canvas, int x, int y, boolean isAI){
        Rect rsc = new Rect(0,0,cardmap.getWidth(),cardmap.getHeight());
        Rect dst;
        if(isSelected) {
            y=y-25;
        }
        dst = new Rect(x,y,cardmap.getWidth()*3/4+x,cardmap.getHeight()*3/4+y);
        if (!isAI){
        canvas.drawBitmap(cardmap,rsc,dst,null);
        }
        else{
            Bitmap cardback=BitmapFactory.decodeResource(sContext.getResources(),R.drawable.cardback);
            canvas.drawBitmap(cardback,rsc,dst,null);
        }

    }

    public boolean compeareTo(Card card){//大于return true,小于return false
        if(this.number>card.getNumber()){//如果本牌牌面大于card
            return true;
        }else if(this.number<card.getNumber()){//如果本牌牌面小于card
            return false;
        }else {//牌面一样大
            return suit>card.getSuit();
        }
    }

    public void printInfo(){
        System.out.println("this card's NUM is " + NUM + " face is " + number + " suit is "+ suit);
        switch (suit){
            case 0:
                System.out.println(" it is 方块" + number);break;
            case 1:
                System.out.println(" it is 梅花" + number);break;
            case 2:
                System.out.println(" it is 红桃" + number);break;
            case 3:
                System.out.println(" it is 黑桃" + number);break;
        }
    }

}

