package com.school.zephania.cddgame.view;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.school.zephania.cddgame.R;
import com.school.zephania.cddgame.model.God;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener{


    private int counter;//创建int变量counter
    private ImageButton button1,button2,button3,button4,button5;
    private Button button_push;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        setFullScreen();
        setContentView(R.layout.activity_first);
        setContentView(R.layout.activity_guide);
        //初始化变量的值
        counter = 0;
        //关联两个button和TextView变量
        button1 = (ImageButton) findViewById(R.id.radiobutton1);
        button2 = (ImageButton) findViewById(R.id.radiobutton2);
        button3 = (ImageButton) findViewById(R.id.radiobutton3);
        button4 = (ImageButton) findViewById(R.id.radiobutton4);
        button5 = (ImageButton) findViewById(R.id.radiobutton5);
        button_push = (Button) findViewById(R.id.push);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button_push.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
            switch(v.getId()){
                case R.id.radiobutton1:
                {
                    clear();
                    button1.setImageResource(R.drawable.nike);
                    counter=1;
                    break;
                }
                case R.id.radiobutton2:
                {
                    clear();
                    button2.setImageResource(R.drawable.nike);
                    counter=2;
                    break;
                }
                case R.id.radiobutton3:
                {
                    clear();
                    button3.setImageResource(R.drawable.nike);
                    counter=3;
                    break;
                }
                case R.id.radiobutton4:
                {
                    clear();
                    button4.setImageResource(R.drawable.nike);
                    counter=4;
                    break;
                }
                case R.id.radiobutton5:
                {
                    clear();
                    button5.setImageResource(R.drawable.nike);
                    counter=5;
                    break;
                }
                case R.id.push:
                {
                    if(counter <= 0||counter > 5)
                    {
                        Toast.makeText(GuideActivity.this,"你必须至少选择一个角色",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Intent intent = new Intent(GuideActivity.this,God.class);
                        intent.putExtra("chooseResult",counter);
                        startActivity(intent);
                        finish();
                        break;
                    }

                }
            }
    }
    public void clear(){
        button1.setImageResource(R.drawable.radio);
        button2.setImageResource(R.drawable.radio);
        button3.setImageResource(R.drawable.radio);
        button4.setImageResource(R.drawable.radio);
        button5.setImageResource(R.drawable.radio);
    }
    @Override
    protected void onResume() {
        /**
         * 设置为横屏
         */
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
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