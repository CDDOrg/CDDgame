package com.school.zephania.cddgame.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.school.zephania.cddgame.R;

import java.util.ArrayList;

import static com.school.zephania.cddgame.model.God.sContext;


/**
 * Created by Zephania on 2017/5/27.
 */

public class Card {
    private int NUM;   //牌的编号，【0，51】，用于设置图片资源和比较大小，编号越大牌越大
    private int number; //牌实际大小【3，15】，用于检测顺子
    private int suit;  //牌的花色，【0，3 】，用于检测同花，按照方块、梅花、红桃、黑桃顺序

    private Bitmap cardmap;
    private ArrayList<Card> handcards=new ArrayList<>();

    public Card(int NUM){
        this.NUM = NUM;
        number = NUM/4 + 3;
        suit = NUM % 4;

        setImage(BitmapFactory.decodeResource(sContext.getResources(), R.drawable.card00+NUM));//设定牌的图片
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

    void setImage(Bitmap cardmap){
        this.cardmap=cardmap;
    }
    void paint(Canvas canvas, int x, int y, boolean isAI){
        Rect rsc = new Rect(x,y,cardmap.getWidth(),cardmap.getHeight());
        Rect dst = new Rect(x,y,cardmap.getWidth()*3/4+x,cardmap.getHeight()*3/4+y);
        canvas.drawBitmap(cardmap,rsc,dst,null);
    }

}

