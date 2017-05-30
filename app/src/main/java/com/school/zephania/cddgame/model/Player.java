package com.school.zephania.cddgame.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.school.zephania.cddgame.R;
import java.util.ArrayList;


/**
 * Created by zephania on 17-5-23.
 */

public class Player {
    private Bitmap head;
    private int x;
    private int y;
    private String name= "binke";
    private ArrayList<Card> handCards;

    private int maxCardNumber;       //相同牌型时，该值越大牌越大
    private CardType currentType = CardType.Null;      //当前选牌的牌型
    private ArrayList<Card> selectedCards;

    public Player(){
        handCards = new ArrayList<>();
        selectedCards = new ArrayList<>();
    }
    public void addCard(Card card){//往手牌中添牌
        handCards.add(card);
        sort(handCards);
    }

    public void selectCard(Card card){//点击一张牌即选牌
        selectedCards.add(card);
        handCards.remove(card);
        sort(selectedCards);
        calCardType();
    }
    public void cancleCard(Card card){
        selectedCards.remove(card);
        handCards.add(card);
        sort(handCards);
        calCardType();
    }
    private void sort(ArrayList<Card> cards){
        for (int i=1;i<cards.size();i++){
            for (int j=i;j>0&&cards.get(j-1).getNumber()<cards.get(j).getNumber();j--){
                Card temp = cards.get(j-1);
                cards.set(j-1, cards.get(j));
                cards.set(j, temp);
            }
        }
    }
    private void calCardType() {//计算牌型
        if (selectedCards.size() == 1){
            currentType = CardType.Signal;
            maxCardNumber = selectedCards.get(0).getNUM();
        }else if (selectedCards.size() == 2){
            if (isEqual(0,1)){
                currentType = CardType.Pair;
                maxCardNumber = selectedCards.get(0).getNUM();
            }
        }else if (selectedCards.size() == 3){
            if (isEqual(0,1) && isEqual(1,2)){
                currentType = CardType.Set;
                maxCardNumber = selectedCards.get(0).getNUM();
            }
        }else if (selectedCards.size() == 5){
            if (checkFlush()){
                checkStraughtFlush();
                currentType = CardType.FIve;
            }else if (checkStraight() || checkFullHouse() || checkFourKind()){
                currentType = CardType.FIve;
            }
        }
    }
    private boolean isEqual(int i, int j){//检测两牌牌面大小是否相等
        return selectedCards.get(i).getNumber() == selectedCards.get(j).getNumber();
    }
    private boolean checkStraight(){//检测顺子
        if (selectedCards.get(4).getNumber() == 15){
            //当包含‘2’时，要检测A，2，3，4，5和2，3，4，5，6的特殊情况
            if (selectedCards.get(0).getNumber() == 3 &&
                    selectedCards.get(1).getNumber() == 4 &&
                    selectedCards.get(2).getNumber() == 5 &&
                    (selectedCards.get(3).getNumber() == 6||
                            selectedCards.get(3).getNumber() == 14)){
                maxCardNumber = selectedCards.get(4).getNUM() + 52 * 0;
                return true;
            }
            return false;
        }else {
            for (int i=0;i<selectedCards.size()-1;i++){
                if (selectedCards.get(i+1).getNumber() - selectedCards.get(i).getNumber() != 1){
                    return false;
                }
            }
            maxCardNumber = selectedCards.get(4).getNUM() + 52 * 0;
            return true;
        }
    }
    private boolean checkFlush(){//检测同花
        for (int i=0;i<selectedCards.size()-1;i++){
            if (selectedCards.get(i+1).getSuit() != selectedCards.get(i).getSuit() ){
                return false;
            }
        }
        maxCardNumber = selectedCards.get(4).getNUM() + 52 * 1;
        return true;
    }
    private boolean checkFullHouse(){//检测三带二
        if (isEqual(0,1)){//如果前两张牌相等，那么检测第三张，有两种情况，前三后二，前二后三
            if (isEqual(0,2) && isEqual(3,4)){
                maxCardNumber = selectedCards.get(0).getNUM() + 52 * 2;
                return true;
            }else if (isEqual(2,3) && isEqual(3,4)){
                maxCardNumber = selectedCards.get(5).getNUM() + 52 * 2;
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }
    private boolean checkFourKind(){
        if (isEqual(1,2) && isEqual(2,3)){
            if (isEqual(0,1) || isEqual(3,4)){
                maxCardNumber = selectedCards.get(1).getNUM() + 52 * 3;
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }
    private boolean checkStraughtFlush(){
        if (checkStraight() && checkFlush()){
            maxCardNumber = selectedCards.get(4).getNUM() + 52 * 4;
            return true;
        }
        return false;
    }

    public void setHandCards(ArrayList<Card> cards){
        handCards =cards;
    }
    public void setView(int x, int y) {
        head=BitmapFactory.decodeResource(God.sContext.getResources(), R.drawable.headbinke);
        this.x=x;
        this.y=y;
    }
    public void setView(int x,int y,Bitmap head){
        this.head=head;
        this.x=x;
        this.y=y;
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