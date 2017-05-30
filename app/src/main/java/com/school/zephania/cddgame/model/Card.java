package com.school.zephania.cddgame.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by Zephania on 2017/5/27.
 */

public class Card {

    Bitmap cardmap;
    ArrayList<Card> handcards=new ArrayList<>();
    void setImage(Bitmap cardmap){
        this.cardmap=cardmap;
    }
    void paint(Canvas canvas, int x, int y, boolean isAI){
        Rect rsc = new Rect(x,y,cardmap.getWidth(),cardmap.getHeight());
        Rect dst = new Rect(x,y,cardmap.getWidth()*3/4+x,cardmap.getHeight()*3/4+y);
        canvas.drawBitmap(cardmap,rsc,dst,null);
    }
}
