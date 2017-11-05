package com.pompom.pomji;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import java.util.Timer;
import java.util.TimerTask;

class User {
    @Expose
    private String name;
    @Expose
    private int money = 0;
    @Expose
    private Sharknapom pom;

    public void setName(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }

    public void setMoney(int m) {
        money = m;
    }

    public void buyPom() {
        pom = new Sharknapom();
    }

    public Pom getPom() {
        return pom;
    }
}

class Pom {
    protected String namepom;
    protected int hunger;
    protected int weight;
    protected int sleepiness;
    protected int clean;
    protected boolean sleep;
    protected boolean sick;

    public Pom() {
        hunger = 100;
        sleepiness = 100;
        clean = 100;
        sleep = false;
        sick = false;
    }

    protected int getClean() {
        return clean;
    }

    protected void setClean(int c) {
        clean = c;
    }

    protected void Sleep() {
        sleep = true;
    }

    protected void Eat() {

    }

}

abstract class Bar extends AppCompatActivity {
    protected int value = 100;

    abstract void Decrease();
}

class FoodBar extends Bar {
    public void Decrease() {
    }
}

class SleepBar extends Bar {
    public void Decrease() {
    }

}

class FunBar extends Bar {
    public void Decrease() {
    }

}

class Sharknapom extends Pom {
    public Sharknapom() {
        super();
    }
}


class Udinopom extends Pom {
    public Udinopom() {
        super();
    }
}

public class main extends AppCompatActivity {
    private TextView userName;
    private ImageView pom;
    private ProgressBar cleanBar;

    private float pomX;

    private int frameWidth, pomWidth;

    private Timer timer = new Timer();
    private Handler handler = new Handler();
    private boolean change = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences shared = getSharedPreferences("my_ref", MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        Gson gson = new Gson();
        boolean first = shared.getBoolean("first", true);

        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();

        final User user;
        if (first) {
            user = new User();
            user.setName(shared.getString("userName", "None"));
            user.buyPom();
            String json = gson.toJson(user);
            prefsEditor.putString("User", json);
            editor.putBoolean("first", false);
            editor.commit();
            prefsEditor.commit();

        } else {
            String json = appSharedPrefs.getString("User", "");
            user = gson.fromJson(json, User.class);
        }

        pom = (ImageView) findViewById(R.id.pom);

        userName = (TextView) findViewById(R.id.userName);
        cleanBar = (ProgressBar) findViewById(R.id.clean);
        userName.setText(user.getName());


        Intent intent = new Intent(this, TimerService.class);
        startService(intent);

        checkDirty(user.getPom(),cleanBar);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (user.getPom().getClean() >= 50) {
                            if (change) {
                                pom.setImageResource(R.drawable.sharknapomwalk);
                                change = false;
                            } else {
                                pom.setImageResource(R.drawable.sharknapom);
                                change = true;
                            }
                            changePos();
                        } else if(user.getPom().getClean()>=30) {
                            if (change) {
                                pom.setImageResource(R.drawable.sharknapomdirty50_1);
                                change = false;
                            } else {
                                pom.setImageResource(R.drawable.sharknapomdirty50_2);
                                change = true;
                            }

                        }else{
                            if (change) {
                                pom.setImageResource(R.drawable.sharknapomdirty30_1);
                                change = false;
                            } else {
                                pom.setImageResource(R.drawable.sharknapomdirty30_2);
                                change = true;
                            }
                        }
                    }
                });
            }
        }, 0, 100);
    }

    public void checkDirty(final Pom s, final ProgressBar cleanBar) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SharedPreferences shared = getSharedPreferences("my_ref", MODE_PRIVATE);
                Log.v("clean", "" + s.getClean());
                if (shared.getInt("time", 0) % 1 == 0 && s.getClean() > 0) {
                    cleanBar.setProgress(s.getClean());
                    s.setClean(s.getClean() - 1);
                }
            }
        }, 0, 1000);
    }

    public void changePos() {
        FrameLayout frame = (FrameLayout) findViewById(R.id.home);
        frameWidth = frame.getWidth();
        pomWidth = pom.getWidth();
        if (pomX + pomWidth >= frameWidth) {
            pom.setScaleX(1f);
            pomX -= 10;
        } else if (pomX <= 0) {
            pom.setScaleX(-1f);
            pomX += 10;
        } else {
            if (pom.getScaleX() == 1) {
                pomX -= 10;
            } else {
                pomX += 10;
            }
        }
        pom.setX(pomX);
    }

    public boolean onTouchEvent(MotionEvent me) {
//        if(me.getAction()==MotionEvent.ACTION_DOWN){
//            count.setText(String.valueOf(1));
//        }
        return true;
    }
}
