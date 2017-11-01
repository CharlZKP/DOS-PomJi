package com.pompom.pomji;

import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
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
    private TextView count;
    private ImageView pet;
    private TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences shared = getSharedPreferences("my_ref",MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();

        User u = new User();
        u.setName(shared.getString("userName","None"));

        count = (TextView)findViewById(R.id.count);
        pet = (ImageView)findViewById(R.id.pet);
        userName = (TextView)findViewById(R.id.userName);

        userName.setText(u.getName());
    }

    public boolean onTouchEvent(MotionEvent me){
        if(me.getAction()==MotionEvent.ACTION_DOWN){
            count.setText(String.valueOf(1));
        }
        return true;
    }
}
