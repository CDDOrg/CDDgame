package com.school.zephania.cddgame.model;

import android.support.annotation.NonNull;

/**给定一个牌的数组，该类保存该组牌的牌型和最大的牌
 * Created by Yuan Qiang on 2017/6/7.
 */

public class TypeNumCouple {
    private CardType cardType ;
    private int NUM;

    public TypeNumCouple(){
        cardType = CardType.Null;
        NUM = -1;
    }
    public TypeNumCouple(CardType cardType, int NUM) {
        this.cardType = cardType;
        this.NUM = NUM;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public int getNUM() {
        return NUM;
    }

    public void setNUM(int NUM) {
        this.NUM = NUM;
    }

    public boolean isBigger(TypeNumCouple couple){//比较两组牌的大小
        if (cardType == CardType.Null){
            return false;
        }
        if (couple.getCardType() == CardType.Null){
            return true;  //任何牌组都大于无牌型的牌组
        }
        if (cardType == couple.cardType && NUM > couple.getNUM()){
            return true;
        }else {
            return false;
        }
    }

    public void reset(){
        cardType = CardType.Null;
        NUM = -1;
    }
}
