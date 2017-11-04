package com.pompom.pomji;

import android.content.Intent;
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
import android.widget.Toast;

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
    public void setMoney(int m){money = m;}
}

class Pom {
    protected String name;
    protected int hunger;
    protected int weight;
    protected int sleepiness;
    protected boolean sleep;
    protected boolean sick;

    public Pom() {
        hunger = 100;
        sleepiness = 100;
        sleep = false;
        sick = false;
    }

    protected void Sleep() {
        sleep = true;
    }

    protected void Eat() {

    }



}

abstract class Bar{
    protected int value = 100;
    abstract void Decrease();
}

class FoodBar extends Bar{
    public void Decrease(){}
}

class SleepBar extends Bar{
    public void Decrease(){}

}

class FunBar extends Bar{
    public void Decrease(){}

}



class Udinopom extends Pom{
    public Udinopom(){
        super();
    }
}

public class main extends AppCompatActivity {
    private TextView userName;
    private ImageView pom;

    private float pomX;

    private int frameWidth,pomWidth;

    private Timer timer = new Timer();
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences shared = getSharedPreferences("my_ref",MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();

        User u = new User();
        //final Pom p = new Udinopom();

        u.setName(shared.getString("userName","None"));
        u.setMoney(shared.getInt("money",0));

        pom = (ImageView)findViewById(R.id.pom);

        userName = (TextView)findViewById(R.id.userName);
        userName.setText(u.getName());

        MessageReceiver receiver = new MessageReceiver(new Message());


        Intent intent = new Intent(this,TimerService.class);
        intent.putExtra("time",100);
        intent.putExtra("receiver",receiver);
        startService(intent);

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

    public void changePos() {
        FrameLayout frame = (FrameLayout)findViewById(R.id.home) ;
        frameWidth = frame.getWidth();
        pomWidth = pom.getWidth();
        if(pomX+pomWidth>=frameWidth){
            pom.setScaleX(1f);
            pomX-=10;
        }else if(pomX<=0){
            pom.setScaleX(-1f);
            pomX+=10;
        }else{
            if(pom.getScaleX()==1){
                pomX-=10;
            }else{
                pomX+=10;
            }
        }
        pom.setX(pomX);
    }

    public class Message{
        public void displayMessage(int resultCode, Bundle resultData){
            String message = resultData.getString("message");
            Toast.makeText(main.this,resultCode+" "+message,Toast.LENGTH_SHORT).show();
        }
    }

    public boolean onTouchEvent(MotionEvent me){
//        if(me.getAction()==MotionEvent.ACTION_DOWN){
//            count.setText(String.valueOf(1));
//        }
        return true;
    }
}
