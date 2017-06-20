package com.school.zephania.cddgame.model;

import java.util.ArrayList;

/**
 * Created by user0308 on 6/20/17.
 */

public class Couple {
    public int counts=0;
    public ArrayList<Card> number = new ArrayList<>();

    public Couple(){}

    public void addCard(Card card){
        number.add(card);
    }

    public void printInfo(){
        System.out.println("count is " + counts);
        printCard();
    }

    public void printCard(){
        for(Card tmp:number){
            tmp.printInfo();
        }
    }

    public int getBigger(int NUM){
        for(Card tmp:number){
            if(tmp.getNUM()>NUM){
                return tmp.getNUM();
            }
        }
        return -1;
    }

    public Card getMaxCard(){
        return number.get(counts-1);
    }
}
