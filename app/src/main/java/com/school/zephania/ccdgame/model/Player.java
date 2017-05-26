package com.school.zephania.ccdgame.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.school.zephania.ccdgame.CCDgame;
import com.school.zephania.ccdgame.R;



/**
 * Created by zephania on 17-5-23.
 */

public class Player {
    private Bitmap head;
    int x;
    int y;
    String name= "binke";

    public Player(CCDgame ccd,int x,int y)
    {
        head=BitmapFactory.decodeResource(ccd.getResources(),R.drawable.headbinke);
        this.x=x;
        this.y=y;
    }
    public Player(CCDgame ccd,int x,int y,Bitmap head){
        this.head=head;
        this.x=x;
        this.y=y;
    }
    public void paint(Canvas canvas){
        //draw head
        Rect src = new Rect(80,0,head.getWidth(),head.getHeight());
        Rect dst = new Rect(x,y,x+(head.getWidth()-85)*3/5,y+head.getHeight()*3/5);
        canvas.drawBitmap(head,src,dst,null);

        Paint textpaint=new Paint();
        textpaint.setColor(Color.BLACK);
        textpaint.setStyle(Paint.Style.FILL);
        textpaint.setTextSize(40);
        textpaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(name,dst.centerX(),dst.bottom-15,textpaint);


    }

}