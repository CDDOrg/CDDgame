package com.school.zephania.ccdgame.model;

import android.graphics.Canvas;

/**
 * Created by zephania on 17-5-23.
 */

public class God {
    private int state;

    public void paint(Canvas canvas){
        switch(state){
            case 0:
                paintGame(canvas);
                break;
        }
    }
    private void paintGame(Canvas canvas){

    }

    public void gameLogic() {

    }
}

