package com.school.zephania.cddgame.debug;

import com.school.zephania.cddgame.model.AIDataModel;
import com.school.zephania.cddgame.model.Card;
import com.school.zephania.cddgame.model.CardType;
import com.school.zephania.cddgame.model.TypeNumCouple;

/**
 * Created by user0308 on 6/20/17.
 */

public class TestAIMain {
    public static void main(String[] args){
        TypeNumCouple exampleTNC = new TypeNumCouple(CardType.FIve,33);
        AIDataModel ai = new AIDataModel();
        //发13张牌
        ai.addCard(new Card(0));//方块1
        ai.addCard(new Card(4));//方块2
        ai.addCard(new Card(8));//方块3
        ai.addCard(new Card(12));//方块4
        ai.addCard(new Card(16));//方块5
        ai.addCard(new Card(20));//方块6
        ai.addCard(new Card(24));//方块7
        ai.addCard(new Card(28));//方块8
        ai.addCard(new Card(9));//梅花3
        ai.addCard(new Card(10));//红桃3
        ai.addCard(new Card(11));//黑桃3
        ai.addCard(new Card(2));//梅花1
        ai.addCard(new Card(13));//梅花4


        ai.printHandCards();
        System.out.println("\n");
        ai.deal();
        ai.sendCards(new TypeNumCouple(CardType.FIve,26));
        //ai.showInfo();
        System.out.println("\n");
        ai.printHandCards();
        System.out.println("\n");

        ai.init();
        ai.deal();
        ai.sendCards(new TypeNumCouple(CardType.FIve,26));
        //ai.sendCards(exampleTNC);
        System.out.println("\n");
        ai.printHandCards();
    }
}
