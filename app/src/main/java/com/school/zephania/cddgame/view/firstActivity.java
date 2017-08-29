package com.school.zephania.cddgame.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.school.zephania.cddgame.R;
import com.school.zephania.cddgame.model.God;

public class firstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        setFullScreen();
        setContentView(R.layout.activity_first);
        Button button1 = (Button) findViewById(R.id.button_1);
        Button button2 = (Button) findViewById(R.id.button_2);
        //Button button4 = (Button) findViewById(R.id.button_4);

        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(firstActivity.this,God.class);
                startActivity(intent);
                finish();
            }
        });

        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(firstActivity.this,GuideActivity.class);
                startActivity(intent);
                finish();
            }
        });
     }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            Log.d("firstActivity","Back have been pressed!");
            AlertDialog.Builder waitAC = new AlertDialog.Builder(firstActivity.this);
            waitAC.setTitle("确定要退出游戏吗？");
            waitAC.setMessage("您将有可能丢失当前游戏信息！");
            waitAC.setCancelable(true);
            waitAC.setPositiveButton("确定",new DialogInterface.
                    OnClickListener(){
                @Override
                public void onClick(DialogInterface waitACK,int which){
                    finish();
                }
            });
            waitAC.setNegativeButton("取消",new DialogInterface.
                    OnClickListener(){
                @Override
                public void onClick(DialogInterface waitACK,int which){}
            });
            waitAC.show();
        }
        return false;
    }

    @Override
    protected void onResume() {
        /**
         * 设置为横屏
         */
        if(getRequestedOrientation()!=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }

    protected void hideActionBar() {
        // Hide UI
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }
    /**
     * set the activity display in full screen
     */

    protected void setFullScreen() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}


