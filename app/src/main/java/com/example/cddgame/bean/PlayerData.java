package com.example.cddgame.bean;

/**
 * Created by user0308 on 5/30/17.
 */

public class PlayerData {
    private String username = null;
    private int competition = 0;
    private int win = 0;
    private int credit = 0;

    public PlayerData(String username,int competition,int win,int credit){
        this.username = username;
        this.competition = competition;
        this.win = win;
        this.credit = credit;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCompetition(int competition) {
        this.competition = competition;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getUsername() {
        return username;
    }

    public int getCompetition() {
        return competition;
    }

    public int getWin() {
        return win;
    }

    public int getCredit() {
        return credit;
    }
}
