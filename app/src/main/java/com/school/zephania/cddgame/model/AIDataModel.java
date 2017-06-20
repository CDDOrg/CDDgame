package com.school.zephania.cddgame.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by user0308 on 6/5/17.
 */

public class AIDataModel {
    private ArrayList<Card> handCards;//手牌
    //private HandCards handcards;

    private int maxCardNumber;       //相同牌型时，该值越大牌越大
    private CardType currentType = CardType.Null;      //当前选牌的牌型


    ArrayList<Card> single = new ArrayList<>();//单张
    ArrayList<Card> pair = new ArrayList<>();//一对
    ArrayList<Card> three = new ArrayList<>();//三张
    ArrayList<Card> four = new ArrayList<>();//四张
    ArrayList<Card> DiamondList = new ArrayList<>();
    ArrayList<Card> ClubList = new ArrayList<>();
    ArrayList<Card> HeartList = new ArrayList<>();
    ArrayList<Card> SpadeList = new ArrayList<>();

    ArrayList<Card> flush = new ArrayList<>();
    Couple[] counts = new Couple[13];

    public AIDataModel(){
        handCards = new ArrayList<>();
        for(int i=0;i<13;i++){
            counts[i] = new Couple();
        }
    }

    public void init(){
        single.clear();
        pair.clear();
        three.clear();
        four.clear();
        DiamondList.clear();
        ClubList.clear();
        HeartList.clear();
        SpadeList.clear();
        flush.clear();
        counts=null;
        counts = new Couple[13];
        for(int i=0;i<13;i++){
            counts[i] = new Couple();
        }
    }

    public void addCard(Card card){//往手牌中添牌
        handCards.add(card);
        //sort(handCards);
    }

//    private void sort(ArrayList<Card> cards){
//        for (int i=1;i<cards.size();i++){
//            for (int j=i;j>0&&cards.get(j-1).getNumber()<cards.get(j).getNumber();j--){
//                Card temp = cards.get(j-1);
//                cards.set(j-1, cards.get(j));
//                cards.set(j, temp);
//            }
//        }
//    }
//    private void calCardType() {//计算牌型
//        if (selectedCards.size() == 1){
//            currentType = CardType.Signal;
//            maxCardNumber = selectedCards.get(0).getNUM();
//        }else if (selectedCards.size() == 2){
//            if (isEqual(0,1)){
//                currentType = CardType.Pair;
//                maxCardNumber = selectedCards.get(0).getNUM();
//            }
//        }else if (selectedCards.size() == 3){
//            if (isEqual(0,1) && isEqual(1,2)){
//                currentType = CardType.Set;
//                maxCardNumber = selectedCards.get(0).getNUM();
//            }
//        }else if (selectedCards.size() == 5){
//            if (checkFlush()){
//                checkStraughtFlush();
//                currentType = CardType.FIve;
//            }else if (checkStraight() || checkFullHouse() || checkFourKind()){
//                currentType = CardType.FIve;
//            }
//        }
//    }
//    private boolean isEqual(int i, int j){//检测两牌牌面大小是否相等
//        return selectedCards.get(i).getNumber() == selectedCards.get(j).getNumber();
//    }
//    private boolean checkStraight(){//检测顺子
//        if (selectedCards.get(4).getNumber() == 15){//这里将A和2的牌面加上13处理以使得A,2为最大
//            //当包含‘2’时，要检测A，2，3，4，5和2，3，4，5，6的特殊情况
//            if (selectedCards.get(0).getNumber() == 3 &&
//                    selectedCards.get(1).getNumber() == 4 &&
//                    selectedCards.get(2).getNumber() == 5 &&
//                    (selectedCards.get(3).getNumber() == 6||
//                            selectedCards.get(3).getNumber() == 14)){
//                maxCardNumber = selectedCards.get(4).getNUM() + 52 * 0;
//                return true;
//            }
//            return false;
//        }else {
//            for (int i=0;i<selectedCards.size()-1;i++){
//                if (selectedCards.get(i+1).getNumber() - selectedCards.get(i).getNumber() != 1){
//                    return false;
//                }
//            }
//            maxCardNumber = selectedCards.get(4).getNUM() + 52 * 0;
//            return true;
//        }
//    }
//    private boolean checkFlush(){//检测同花
//        for (int i=0;i<selectedCards.size()-1;i++){
//            if (selectedCards.get(i+1).getSuit() != selectedCards.get(i).getSuit() ){
//                return false;
//            }
//        }
//        maxCardNumber = selectedCards.get(4).getNUM() + 52 * 1;
//        return true;
//    }
//    private boolean checkFullHouse(){//检测三带二
//        if (isEqual(0,1)){//如果前两张牌相等，那么检测第三张，有两种情况，前三后二，前二后三
//            if (isEqual(0,2) && isEqual(3,4)){
//                maxCardNumber = selectedCards.get(0).getNUM() + 52 * 2;
//                return true;
//            }else if (isEqual(2,3) && isEqual(3,4)){
//                maxCardNumber = selectedCards.get(5).getNUM() + 52 * 2;
//                return true;
//            }else {
//                return false;
//            }
//        }else {
//            return false;
//        }
//    }
//    private boolean checkFourKind(){
//        if (isEqual(1,2) && isEqual(2,3)){
//            if (isEqual(0,1) || isEqual(3,4)){
//                maxCardNumber = selectedCards.get(1).getNUM() + 52 * 3;
//                return true;
//            }else {
//                return false;
//            }
//        }else {
//            return false;
//        }
//    }
//    private boolean checkStraughtFlush(){
//        if (checkStraight() && checkFlush()){
//            maxCardNumber = selectedCards.get(4).getNUM() + 52 * 4;
//            return true;
//        }
//        return false;
//    }


    public void deal(){
        //sortByNumber(handCards);//2,1,K,J,...4,3
        divided();//分成13堆,下标从0到12

        fillInSingle();
        fillInPair();
        fillInSet();
        fillInFour();
        fillInFlush();
        sortBySuit(handCards);//按花色分到各个ArrayList里了
    }


    //先把牌按花色分成四堆,再从小到大排列
    private void sortBySuit(ArrayList<Card> cards){//把一个ArrayList<card>拆分到四个ArrayList中

        sortByNumber(cards);//先按大小排好序

        for(int i=0;i<cards.size();i++){
            switch (cards.get(i).getSuit()) {
                case 0:DiamondList.add(cards.get(i));break;
                case 1:ClubList.add(cards.get(i));break;
                case 2:HeartList.add(cards.get(i));break;
                case 3:SpadeList.add(cards.get(i));break;
            }
        }
//        cards.clear();
//        cards.addAll(SpadeList);
//        cards.addAll(HeartList);
//        cards.addAll(ClubList);
//        cards.addAll(DiamondList);
    }

    private void sortByNumber(ArrayList<Card> cards){//2,1,K,J,...4,3
        for (int i=1;i<cards.size();i++){//sort by number
            for (int j=i;j>0&&cards.get(j).compeareTo(cards.get(j-1));j--){
                Card temp = cards.get(j-1);
                cards.set(j-1, cards.get(j));
                cards.set(j, temp);
            }
        }
    }

    public void sendCards(TypeNumCouple typeNumCouple){
        int tmp = -1;
        int index = -1;
        switch (typeNumCouple.getCardType()){
            case Null:
            case Signal://单张
                for(int i=0;i<counts.length;i++){
                    if(counts[i].counts==1){
                        //System.out.print(" "+ i);
                        tmp = counts[i].getBigger(typeNumCouple.getNUM());
                        if(tmp!=-1)
                            index = i;
                        break;
                    }
                }
                if(tmp==-1){
                    System.out.println("Pass");
                }
                else{
                    System.out.println("Single "+ tmp);//出了这张牌,还要将它从手牌中remove
                    handCards.remove(counts[index].number.get(0));
                    counts[index].counts-=1;
                }
                break;
            case Pair://对
                for(int i=0;i<counts.length;i++){
                    if(counts[i].counts==2){
                        //System.out.print(" "+ i);
                        tmp = counts[i].getBigger(typeNumCouple.getNUM());
                        if(tmp!=-1)
                            index = i;
                        break;
                    }
                }
                if(tmp==-1){
                    System.out.println("Pass");
                }
                else{
                    System.out.println("Pair "+ tmp);
                    handCards.remove(counts[index].number.get(0));
                    handCards.remove(counts[index].number.get(1));
                    counts[index].counts-=2;
                }
                break;
            case Set://单三张
                for(int i=0;i<counts.length;i++){
                    if(counts[i].counts==3){
                        //System.out.print(" "+ i);
                        tmp = counts[i].getBigger(typeNumCouple.getNUM());
                        if(tmp!=-1)
                            index = i;
                        break;
                    }
                }
                if(tmp==-1){
                    System.out.println("Pass");
                }
                else{
                    System.out.println("Three "+ tmp);
                    handCards.remove(counts[index].number.get(0));
                    handCards.remove(counts[index].number.get(1));
                    handCards.remove(counts[index].number.get(2));
                    counts[index].counts-=3;
                }
                break;
            case FIve:
                //将typeNumCouple.getNUM()拆解,得出牌的类型,最大值
                switch (typeNumCouple.getNUM()/52){
                    case 0://杂顺
                        //还要排除23456(最小),JQKA2最大
                        for(int i=0;i<flush.size();i+=5){
                            for(int j=i;j<i+5;j++){
                                if(flush.get(j).compeareTo(new Card(typeNumCouple.getNUM()%52))){//返回true则比它大
                                    //排除2,这张牌为2,且在五张中第一
                                    if(flush.get(j).getNumber()%13==2&&j%5==0){
                                        continue;//跳出五张的循环,不用
                                    }
                                    //比它大,且不为第一张的2
                                    //出了这五张牌,j,j+5,其实应该是hangcard.remove
                                    for(int x=0;x<5;x++){
                                        flush.get(i+x).printInfo();
                                        handCards.remove(flush.get(i+x));
                                    }
                                    /*handCards.remove(flush.get(j));
                                    handCards.remove(flush.get(j+1));
                                    handCards.remove(flush.get(j+2));
                                    handCards.remove(flush.get(j+3));
                                    handCards.remove(flush.get(j+4));*/
                                    return;//出完牌就结束这个函数
                                }
                            }
                        }

                    case 1://同花
                        switch (typeNumCouple.getNUM()%4){
                            case 0:
                                if(DiamondList.size()>=5){//方块中有同花
                                    for(int i=0;i<DiamondList.size();i++){
                                        if(DiamondList.get(i).compeareTo(new Card(typeNumCouple.getNUM()%52))){//true is bigger
                                            if(i<5){//在前五张中有比上家大的牌
                                                //出牌 DiamondList的前五张
                                                for(int j=0;j<5;j++){
                                                    DiamondList.get(j).printInfo();
                                                }
                                                return;//结束,不再继续找下去
                                            }else {
                                                //出牌,前四张加上i这张
                                                for (int j=0;j<4;j++){
                                                    DiamondList.get(j).printInfo();
                                                }
                                                DiamondList.get(i).printInfo();
                                                return;//结束,不再继续
                                            }
                                        }
                                    }
                                }
                            case 1:
                                if(ClubList.size()>=5){//梅花中有同花
                                    for(int i=0;i<ClubList.size();i++){
                                        if(ClubList.get(i).compeareTo(new Card(typeNumCouple.getNUM()%52))){//true is bigger
                                            if(i<5){//在前五张中有比上家大的牌
                                                //出牌 ClubList的前五张
                                                for(int j=0;j<5;j++){
                                                    ClubList.get(j).printInfo();
                                                }
                                                return;
                                            }else {
                                                //出牌,前四张加上i这张
                                                for (int j=0;j<4;j++){
                                                    ClubList.get(j).printInfo();
                                                }
                                                ClubList.get(i).printInfo();
                                                return;
                                            }
                                        }
                                    }
                                }
                            case 2:
                                if(HeartList.size()>=5){//红桃中有同花
                                    for(int i=0;i<HeartList.size();i++){
                                        if(HeartList.get(i).compeareTo(new Card(typeNumCouple.getNUM()%52))){//true is bigger
                                            if(i<5){//在前五张中有比上家大的牌
                                                //出牌 HeartList的前五张
                                                for(int j=0;j<5;j++){
                                                    HeartList.get(j).printInfo();
                                                }
                                                return;
                                            }else {
                                                //出牌,前四张加上i这张
                                                for (int j=0;j<4;j++){
                                                    HeartList.get(j).printInfo();
                                                }
                                                HeartList.get(i).printInfo();
                                                return;
                                            }
                                        }
                                    }
                                }
                            case 3:
                                if(SpadeList.size()>=5){//黑桃中有同花
                                    for(int i=0;i<SpadeList.size();i++){
                                        if(SpadeList.get(i).compeareTo(new Card(typeNumCouple.getNUM()%52))){//true is bigger
                                            if(i<5){//在前五张中有比上家大的牌
                                                //出牌 SpadeList的前五张
                                                for(int j=0;j<5;j++){
                                                    SpadeList.get(j).printInfo();
                                                }
                                                return;
                                            }else {
                                                //出牌,前四张加上i这张
                                                for (int j=0;j<4;j++){
                                                    SpadeList.get(j).printInfo();
                                                }
                                                SpadeList.get(i).printInfo();
                                                return;
                                            }
                                        }
                                    }
                                }
                        }
                    case 2://三带二
                        for(int i=0;i<counts.length;i++){
                            if(counts[i].counts==3){
                                if(counts[i].number.size()==0){
                                    //为空,没有三张
                                    continue;
                                }
                                int result = counts[i].getBigger(typeNumCouple.getNUM()%52);
                                if(result!=-1) {//找到比它大的
                                    for (int j = 0; j < counts.length; j++) {
                                        if (counts[j].counts == 2) {
                                            if (counts[j].number.size() > 0) {//有对
                                                //取前三张
                                                for (int x = 0; x < 3; x++) {
                                                    counts[i].number.get(x).printInfo();
                                                }
                                                //取前两张
                                                for (int x = 0; x < 2; x++) {
                                                    counts[j].number.get(x).printInfo();
                                                }
                                                return;
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    case 3://四带一
                        for(int i=0;i<counts.length;i++){
                            if(counts[i].counts==4) {
                                if (counts[i].number.size() == 0) {
                                    //为空,没有四张
                                    continue;
                                }
                                int result = counts[i].getBigger(typeNumCouple.getNUM() % 52);
                                if (result != -1) {//找到比它大的
                                    for (int j = 0; j < counts.length; j++) {
                                        if (counts[j].counts == 2) {
                                            if (counts[j].number.size() > 0) {//有对
                                                //取前三张
                                                for (int x = 0; x < 3; x++) {
                                                    counts[i].number.get(x).printInfo();
                                                }
                                                //取前两张
                                                for (int x = 0; x < 2; x++) {
                                                    counts[j].number.get(x).printInfo();
                                                }
                                                return;
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    case 4://同花顺

                    default:
                        System.out.println("Pass");

                }
        }
    }


    public void fillInSingle(){
        for(int i=0;i<counts.length;i++){//分解成单张
            if(counts[i].counts==1){
                //System.out.print(" "+ i);
                //counts[i].printCard();
                single.add(counts[i].number.get(0));
            }
        }
//        sortByNumber(handCards);//先把手牌从小到大排列
//        for(int i=0;i<handCards.size()-1;i++){
//            if(!handCards.get(i).numberEquals(handCards.get(i+1))){//
//                single.add(handCards.get(i));
//            }
//        }
    }

    public void fillInPair(){
        for(int i=0;i<counts.length;i++){
            if(counts[i].counts==2){
                //System.out.print(" "+ i);
                //counts[i].printCard();
                pair.add(counts[i].number.get(0));
                pair.add(counts[i].number.get(1));
            }
        }

    }

    public void fillInSet(){
        for(int i=0;i<counts.length;i++){
            if(counts[i].counts==3){
                //System.out.print(" "+ i);
                //counts[i].printCard();
                three.add(counts[i].number.get(0));
                three.add(counts[i].number.get(1));
                three.add(counts[i].number.get(2));
            }
        }
    }

    public void fillInFour(){
        for(int i=0;i<counts.length;i++){
            if(counts[i].counts==4){
                //System.out.print(" "+ i);
                //counts[i].printCard();
                four.add(counts[i].number.get(0));
                four.add(counts[i].number.get(1));
                four.add(counts[i].number.get(2));
                four.add(counts[i].number.get(3));
            }
        }
    }

    public void fillInFlush(){//已经按照2,3,4,5,,,JQKA排列,直接计算
        boolean hasZero = false;
        for(int i=0;i<counts.length-4;i++){
            for(int j=i;j<i+5;j++){
                if(counts[j].counts==0) {
                    hasZero = true;
                    break;//有为0的,没有组成顺子
                }
            }
            if(!hasZero){//组成顺子,为它设定规则:判断有无可能组成同花顺,否则最大的数字的所有牌中,取最大那张作为顺子一员,还要判断是否为同花顺
                //System.out.println("顺子 " + i + (i+1) + (i+2) + (i+3) + (i+4) );
                //若是2345678,则会存储23456,34567,45678三个顺
                flush.add(counts[i].number.get(0));
                flush.add(counts[i+1].number.get(0));
                flush.add(counts[i+2].number.get(0));
                flush.add(counts[i+3].number.get(0));
                flush.add(counts[i+4].getMaxCard());
                //还有一个问题,如果是同花顺中的梅花顺34567+方块4红桃4,不会检测出同花顺
            }
            hasZero = false;//重新初始化
        }
    }

    public void fillInStraightFlush(){

    }

    public void divided(){
        sortByNumber(handCards);//2,A,KQJ,4,3

        for(int i=0;i<handCards.size();i++){//现在是K,1,2,3,,,JQ,要改成2,3,4,JQKA
            counts[handCards.get(i).getNumber()%13].counts++;
            counts[handCards.get(i).getNumber()%13].addCard(handCards.get(i));
        }
        Couple k = counts[0];
        Couple a = counts[1];
        for(int i=2;i<counts.length;i++){
            counts[i-2] = counts[i];
        }
        counts[counts.length-2] = k;
        counts[counts.length-1] = a;
//        for (int i=0;i<counts.length;i++){
//            switch (counts[i]){
//                case 0:
//                case 1:
//                case 2:
//                case 3:
//                case 4:
//
//            }
//        }
    }

    public void showInfo(){
        System.out.println("this is AI" + " with " + handCards.size() + " cards");

        for(int i=0;i<handCards.size();i++){
            handCards.get(i).printInfo();
        }

        for (int i=0;i<counts.length;i++){
            counts[i].printInfo();
        }

        System.out.println("\nAI 有 方块");
        for(Card tmp:DiamondList){
            tmp.printInfo();
        }
        System.out.println("\nAI 有 梅花");
        for(Card tmp:ClubList){
            tmp.printInfo();
        }
        System.out.println("\nAI 有 红桃");
        for(Card tmp:HeartList){
            tmp.printInfo();
        }
        System.out.println("\nAI 有 黑桃");
        for(Card tmp:SpadeList){
            tmp.printInfo();
        }


    }

    public void printInfo(){
        for (Card tmp:flush){
            tmp.printInfo();
        }
    }

    public void printHandCards(){
        for(Card tmp:handCards){
            tmp.printInfo();
        }
    }
}
