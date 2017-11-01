package com.pompom.pomji;

import android.content.SharedPreferences;
import android.graphics.Point;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

class User{
    private String name;
    private int money;
    private ArrayList pet;
    public void setName(String n) {
        name = n;
    }
    public String getName(){
        return name;
    }
}

class Animal{
    protected String name;
    protected int hunger;
    protected int weight;
    protected int sleepiness;
    protected boolean sleep;
    protected boolean sick;
    public Animal(){
        hunger = 100;
        sleepiness = 100;
        sleep = false;
        sick = false;
    }
    protected void Sleep(){
        sleep = true;
    }
    protected void Eat(){

    }
}

class theChoosenPom extends Animal{
    public theChoosenPom(){
        super();
    }
}

public class main extends AppCompatActivity {
    private ImageView pom;
    private TextView userName;

    private int frameWidth;
    private int pomWidth;

    private float pomX;

    private Handler handler = new Handler();
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences shared = getSharedPreferences("my_ref",MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();

        User u = new User();
        u.setName(shared.getString("userName","None"));

        pom = (ImageView)findViewById(R.id.pom);
        userName = (TextView)findViewById(R.id.userName);

        userName.setText(u.getName());

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        changePos();
                    }
                });
            }
        },0,80);
    }

    public void changePos(){
        FrameLayout frame = (FrameLayout) findViewById(R.id.home);
        frameWidth = frame.getWidth();
        pomWidth = pom.getWidth();
        if (pomX+pomWidth>=frameWidth){
            pom.setScaleX(1f);
            pomX -= 10;
        }else if(pomX<=0){
            pom.setScaleX(-1f);
            pomX += 10;
        }else{
            if(pom.getScaleX()==1) {
                pomX -= 10;
            }else{
                pomX += 10;
            }
        }

        pom.setX(pomX);
    }

    public boolean onTouchEvent(MotionEvent me){
//        if(me.getAction()==MotionEvent.ACTION_DOWN){
//            count.setText(String.valueOf(1));
//        }
        return true;
    }
}
