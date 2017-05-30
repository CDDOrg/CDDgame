package com.school.zephania.cddgame.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.school.zephania.cddgame.R;
import java.util.ArrayList;
import java.util.List;

import static com.school.zephania.cddgame.CDDgame.cdd;


/**
 * Created by zephania on 17-5-23.
 */

public class Player {
    private Bitmap head;
    int x;
    int y;
    String name= "binke";
    ArrayList<Card> handCards;

    public void setHandCards(List cards){
        handCards =new ArrayList<>(cards);
    }
    public void setView( int x, int y)
    {
        head=BitmapFactory.decodeResource(cdd.getResources(),R.drawable.headbinke);
        this.x=x;
        this.y=y;
    }
    public void setView(int x,int y,Bitmap head){
        this.head=head;
        this.x=x;
        this.y=y;
    }
    public ArrayList<Card> getHandCard(){
        return handCards;
    }
    public void paint(Canvas canvas){
        //画头像
        Rect src = new Rect(80,0,head.getWidth(),head.getHeight());
        Rect dst = new Rect(x,y,x+(head.getWidth()-85)*3/5,y+head.getHeight()*3/5);
        canvas.drawBitmap(head,src,dst,null);
        //画名字
        Paint textpaint=new Paint();
        textpaint.setColor(Color.BLACK);
        textpaint.setStyle(Paint.Style.FILL);
        textpaint.setTextSize(40);
        textpaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(name,dst.centerX(),dst.bottom-15,textpaint);
    }

}