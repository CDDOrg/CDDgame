package com.school.zephania.cddgame.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.school.zephania.cddgame.R;
import java.util.ArrayList;

import static com.school.zephania.cddgame.model.Card.cardHeight;
import static com.school.zephania.cddgame.model.Card.cardWidth;
import static com.school.zephania.cddgame.model.God.cardMargin;
import static com.school.zephania.cddgame.model.God.mHeight;
import static com.school.zephania.cddgame.model.God.mWidth;
import static com.school.zephania.cddgame.model.God.turn;
import static com.school.zephania.cddgame.model.God.turnType.RIGHT;
import static java.lang.Thread.sleep;


/**
 * Created by zephania on 17-5-23.
 */

public class Player {
    private Bitmap head;
    private int x;
    private int y;
    private String name= "binke";
    private ArrayList<Card> handCards;

    private boolean sendCardTag = false; //能否出牌的标志，true为能出牌

    private TypeNumCouple last;   //玩家上一轮出的牌
    private TypeNumCouple couple;  //用于计算牌型
    private ArrayList<Card> selectedCards;
    private ArrayList<Card> sendCards;//出的牌
    private boolean sendState=false;//出牌状态
    @SuppressWarnings("unused")
    public enum Mode{RIGHT,LEFT,UP,DOWN};//绘拍模式

    public Player(){
        handCards = new ArrayList<>();
        selectedCards = new ArrayList<>();
        sendCards=new ArrayList<>();
        couple = new TypeNumCouple();
        last = new TypeNumCouple();
    }
    public void addCard(Card card){//往手牌中添牌
        handCards.add(card);
        sortHandCards(handCards);
    }

    public TypeNumCouple sendCards(){//出牌
        handCards.removeAll(selectedCards);
        getsendCard();//获取出牌-绘图用
        sendState=true;
        sendCardTag = false;
        selectedCards.removeAll(selectedCards);
        sortHandCards(handCards);

        last.setCardType(couple.getCardType());
        last.setNUM(couple.getNUM());

        couple.reset();

        return last;
    }

    public void selectCard(Card card){//点击一张牌即选牌
        selectedCards.add(card);
       // handCards.remove(card);
        sortSelectesCards(selectedCards);
        calCardType();
        Log.d("Player", "CardType = " + couple.getCardType());
    }
    public void cancleCard(Card card){
        selectedCards.remove(card);
        //handCards.add(card);
        sortSelectesCards(selectedCards);
        calCardType();
        Log.d("Player", "CardType = " + couple.getCardType());
    }
    private void sortSelectesCards(ArrayList<Card> cards){
        for (int i=1;i<cards.size();i++){
            for (int j=i;j>0&&cards.get(j-1).getNUM()>cards.get(j).getNUM();j--){
                Card temp = cards.get(j-1);
                cards.set(j-1, cards.get(j));
                cards.set(j, temp);
            }
        }
    }
    private void sortHandCards(ArrayList<Card> cards){
        for (int i=1;i<cards.size();i++){
            for (int j=i;j>0&&cards.get(j-1).getNUM()<cards.get(j).getNUM();j--){
                Card temp = cards.get(j-1);
                cards.set(j-1, cards.get(j));
                cards.set(j, temp);
            }
        }
    }
    private void calCardType() {//计算牌型
        if (selectedCards.size() == 1){
            couple.setCardType( CardType.Signal);
            couple.setNUM(selectedCards.get(0).getNUM()) ;
            return;
        }else if (selectedCards.size() == 2){
            if (isEqual(0,1)){
                couple.setCardType(CardType.Pair);
                couple.setNUM( selectedCards.get(1).getNUM());
                return;
            }
        }else if (selectedCards.size() == 3){
            if (isEqual(0,1) && isEqual(1,2)){
                couple.setCardType( CardType.Set);
                couple.setNUM(selectedCards.get(2).getNUM());
                return;
            }
        }else if (selectedCards.size() == 5){
            if (checkFlush()){
                checkStraughtFlush();
                couple.setCardType(CardType.FIve);
                return;
            }else if (checkStraight() || checkFullHouse() || checkFourKind()){
                couple.setCardType(CardType.FIve);
                return;
            }
        }
        couple.setCardType(CardType.Null);
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
                couple.setNUM(selectedCards.get(4).getNUM() + 52 * 0);
                return true;
            }
            return false;
        }else {
            for (int i=0;i<selectedCards.size()-1;i++){
                if (selectedCards.get(i+1).getNumber() - selectedCards.get(i).getNumber() != 1){
                    return false;
                }
            }
            couple.setNUM(selectedCards.get(4).getNUM() + 52 * 0);
            return true;
        }
    }
    private boolean checkFlush(){//检测同花
        for (int i=0;i<selectedCards.size()-1;i++){
            if (selectedCards.get(i+1).getSuit() != selectedCards.get(i).getSuit() ){
                return false;
            }
        }
        couple.setNUM(selectedCards.get(4).getNUM() + 52 * 1);
        return true;
    }
    private boolean checkFullHouse(){//检测三带二
        if (isEqual(0,1)){//如果前两张牌相等，那么检测第三张，有两种情况，前三后二，前二后三
            if (isEqual(0,2) && isEqual(3,4)){
                couple.setNUM(selectedCards.get(2).getNUM() + 52 * 2);
                return true;
            }else if (isEqual(2,3) && isEqual(3,4)){
                couple.setNUM(selectedCards.get(4).getNUM() + 52 * 2);
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
                couple.setNUM(selectedCards.get(1).getNUM() + 52 * 3);
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
            couple.setNUM(selectedCards.get(4).getNUM() + 52 * 4);
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
    boolean threadcontrol=false;//控制绘屏进程用
    void paintCards(Canvas canvas,Mode mode){
        switch (mode){
            case DOWN:
                for (int i=0;i<handCards.size();i++){
                    handCards.get(i).paint(canvas,x+200+cardMargin*i,y,false);
                }
                break;
            case UP:
                for (int i=0;i<handCards.size();i++){
                    handCards.get(i).paint(canvas,x+200+cardMargin/2*i,y,true);
                }
                break;
            case RIGHT:
                for (int i=0;i<handCards.size();i++){
                    handCards.get(i).paint(canvas,x-200,y+cardMargin/2*i,true);
                }
                break;
            case LEFT:
                for (int i=0;i<handCards.size();i++){
                    handCards.get(i).paint(canvas,x+200,y+cardMargin/2*i,true);
                }
                break;
        }
        if(threadcontrol){
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            turn= RIGHT;
            threadcontrol=false;
        }
        if(sendState){
            paintChupai(canvas);
            sendState=false;
            threadcontrol=true;
            sendCards.removeAll(sendCards);
        }

    }
    public void getsendCard(){
        sendCards=(ArrayList<Card>)selectedCards.clone();
    }
    public void paintChupai(Canvas canvas){
        int length=(sendCards.size()-1)*cardMargin+cardWidth;
        for (int i=0;i<sendCards.size();i++){
            sendCards.get(i).paint(canvas,(mWidth-length)/2+i*cardMargin,(mHeight-cardHeight)/2,false);
        }
    }
    void onTouch(View v, MotionEvent event, TypeNumCouple couple){
        int x = (int) event.getX();
        int y = (int) event.getY();
        int cardnum=handCards.size();
        int width=cardMargin;
        for (int i = 0; i <cardnum; i++) {
            if (God.inRect(x,y,this.x+200+cardMargin*i,this.y,width,handCards.get(i).cardHeight)||(i==cardnum-1&&God.inRect(x,y,this.x+200+cardMargin*i,this.y,cardWidth,handCards.get(i).cardHeight))) {
                if (handCards.get(i).isSelected()){
                    cancleCard(handCards.get(i));
                }else {
                    selectCard(handCards.get(i));
                }

                if (this.couple.isBigger(couple)){//如果选择的牌更小则不能出
                    sendCardTag = true;
                }else {
                    sendCardTag = false;
                }
                handCards.get(i).changeSelected();
                break;
            }
        }
    }

    public boolean isSendCardTag() {
        return sendCardTag;
    }

    public void setSendCardTag(boolean sendCardTag) {
        this.sendCardTag = sendCardTag;
    }
    public boolean getSendCardTag(){
        return sendCardTag;
    }

    public TypeNumCouple getLast() {
        return last;
    }
    public void cancelSelected() {
        for(Card card:handCards){
            cancleCard(card);
        }
    }
    public void printCardInfo(){
        for (int i = 0; i < handCards.size(); i++) {
            handCards.get(i).printInfo();
        }
    }
}
