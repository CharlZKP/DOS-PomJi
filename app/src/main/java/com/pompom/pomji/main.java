package com.pompom.pomji;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.Expose;

import java.util.Timer;
import java.util.TimerTask;

class User {
    @Expose
    private String name;
    @Expose
    private int money = 999;
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

    public int getMoney() {
        return money;
    }

    public void buyPom() {
        pom = new Sharknapom();
    }

    public Pom getPom() {
        return pom;
    }
}

abstract class Pom {
    private String namepom;
    private int weight;
    protected int hunger;
    protected int energy;
    protected int clean;
    protected int fun;
    protected boolean sleep;
    protected boolean sick;


    Pom() {
        hunger = 100;
        energy = 100;
        clean = 30;
        fun = 100;
        sleep = false;
        sick = false;
    }

    protected void setName(String name) {
        namepom = name;
    }

    protected int getClean() {
        return clean;
    }

    protected void setClean(int c) {
        clean = c;
    }

    protected int getFun() {
        return fun;
    }

    protected void setFun(int f) {
        fun = f;
    }

    protected void setEnergy(int e) {
        energy = e;
    }

    protected int getEnergy() {
        return energy;
    }

    protected void setHunger(int h) {
        hunger = h;
    }

    protected int getHunger() {
        return hunger;
    }

    abstract boolean Move(boolean change, ImageView img);

    abstract boolean Sick(boolean change, ImageView img, boolean s);

    abstract boolean Dirty(boolean change, ImageView img, int c);

    protected boolean getSick() {
        return sick;
    }

    protected void setSick(boolean s) {
        sick = s;
    }

    protected void Sleep() {
        sleep = true;
    }

    protected void Eat() {

    }
    public boolean Clean(boolean change,ImageView img,int k){
        if (k <= 50 && k > 30) {
            if (change) {
                img.setImageResource(R.drawable.sharknapomclean1);
                return false;
            } else {
                img.setImageResource(R.drawable.sharknapomclean2);
                return true;
            }
        } else {
            if (change) {
                img.setImageResource(R.drawable.sharknapomclean1_1);
                return false;
            } else {
                img.setImageResource(R.drawable.sharknapomclean2_1);
                return true;
            }
        }
    }

}

class Sharknapom extends Pom {
    private boolean change = false;

    Sharknapom() {
        super();
    }



    public boolean Move(boolean change, ImageView img) {
        if (change) {
            img.setImageResource(R.drawable.sharknapomwalk);
            return false;
        } else {
            img.setImageResource(R.drawable.sharknapom);
            return true;
        }
    }

    public boolean Sick(boolean change, ImageView img, boolean s) {

        if (s) {
            img.setImageResource(R.drawable.sharknapom_1sick_1);
            return false;
        } else {
            img.setImageResource(R.drawable.sharknapom_1sick_2);
            return true;
        }
    }

    public boolean Dirty(boolean change, ImageView img, int c) {
        if (c <= 50 && c > 30) {
            if (change) {
                img.setImageResource(R.drawable.sharknapomdirty50_1);
                return false;
            } else {
                img.setImageResource(R.drawable.sharknapomdirty50_2);
                return true;
            }
        } else {
            if (change) {
                img.setImageResource(R.drawable.sharknapomdirty30_1);
                return false;
            } else {
                img.setImageResource(R.drawable.sharknapomdirty30_2);
                return true;
            }
        }
    }

    public boolean Sick(boolean change, ImageView img) {
        return true;
    }
}

class Udinopom extends Pom {
    public Udinopom() {
        super();
    }

    public boolean Dirty(boolean change, ImageView img, int c) {
        return true;
    }

    public boolean Sick(boolean change, ImageView img, boolean s) {
        return true;
    }

    public boolean Move(boolean change, ImageView img) {
        if (change) {
            img.setImageResource(R.drawable.sharknapomwalk);
            return false;
        } else {
            img.setImageResource(R.drawable.udinopom);
            return true;
        }
    }
}

abstract class Bar {
    protected ProgressBar bar;

    Bar(ProgressBar b) {
        bar = b;
    }

    abstract void decrease(final SharedPreferences shared);
}

class FunBar extends Bar {
    FunBar(ProgressBar b) {
        super(b);
    }

    public void decrease(final SharedPreferences shared) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Gson gson = new Gson();
                String json = shared.getString("User", "");
                User user = gson.fromJson(json, User.class);
                bar.setProgress(user.getPom().getFun());
            }
        }, 0, 1000);
    }
}

class EnergyBar extends Bar {
    EnergyBar(ProgressBar b) {
        super(b);
    }

    public void decrease(final SharedPreferences shared) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Gson gson = new Gson();
                String json = shared.getString("User", "");
                User user = gson.fromJson(json, User.class);
                bar.setProgress(user.getPom().getEnergy());
            }
        }, 0, 1000);
    }
}

class HungerBar extends Bar {
    HungerBar(ProgressBar b) {
        super(b);
    }

    public void decrease(final SharedPreferences shared) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Gson gson = new Gson();
                String json = shared.getString("User", "");
                User user = gson.fromJson(json, User.class);
                bar.setProgress(user.getPom().getHunger());
            }
        }, 0, 1000);
    }
}

class CleanBar extends Bar {
    CleanBar(ProgressBar b) {
        super(b);
    }

    public void decrease(final SharedPreferences shared) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Gson gson = new Gson();
                String json = shared.getString("User", "");
                User user = gson.fromJson(json, User.class);
                bar.setProgress(user.getPom().getClean());
            }
        }, 0, 1000);
    }
}

public class main extends AppCompatActivity {
    private TextView userName;
    private ImageView pom;

    private float pomX;
    private boolean touch = false;

    private int frameWidth, pomWidth;

    private Timer timer = new Timer();
    private Handler handler = new Handler();
    private boolean change = false;
    private User user;
    private Pom[] myPom = new Pom[3];
    private boolean cleans = false;
    private int coin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences shared = getSharedPreferences("my_ref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = shared.edit();
        Gson gson = new Gson();
        boolean first = shared.getBoolean("first", true);


        pom = (ImageView) findViewById(R.id.pom);
        userName = (TextView) findViewById(R.id.userName);
        ProgressBar cleanBar = (ProgressBar) findViewById(R.id.cleanbar);
        ProgressBar energyBar = (ProgressBar) findViewById(R.id.energybar);
        ProgressBar funBar = (ProgressBar) findViewById(R.id.funbar);
        ProgressBar hungerBar = (ProgressBar) findViewById(R.id.hungerbar);
        Button playButton = (Button) findViewById(R.id.playButton);
        Button shopButton = (Button) findViewById(R.id.shopButton);
        Button cleanButton = (Button) findViewById(R.id.cleanButton);
        Button inventoryButton = (Button) findViewById(R.id.inventoryButton);
        final TextView money = (TextView)findViewById(R.id.Money);

        inventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(main.this, Inventory.class);
                startActivity(intent);
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(main.this, PlayWithPom.class);
                startActivity(intent);
            }
        });
        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(main.this, Shop.class);
                startActivity(intent);
            }
        });
        cleanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cleans = true;
                editor.putBoolean("clean",cleans);
            }
        });




        if (first) {
            user = new User();
            user.setName(shared.getString("userName", "None"));
            user.buyPom();
            String json = gson.toJson(user);
            editor.putString("User", json);
            editor.putInt("coin",3000);
            editor.putBoolean("first", false);
            editor.putBoolean("clean", false);
            editor.commit();
        } else {
            String json = shared.getString("User", "");
            user = gson.fromJson(json, User.class);
            coin = shared.getInt("coin",0);
        }

        userName.setText(user.getName());

        Intent intent = new Intent(this, TimerService.class);
        startService(intent);

        CleanBar cb = new CleanBar(cleanBar);
        EnergyBar eb = new EnergyBar(energyBar);
        FunBar fb = new FunBar(funBar);
        HungerBar hb = new HungerBar(hungerBar);

        cb.decrease(shared);
        eb.decrease(shared);
        fb.decrease(shared);
        hb.decrease(shared);
        pomAnimation();
        Timer timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences shared = getSharedPreferences("my_ref", MODE_PRIVATE);
                        coin = shared.getInt("coin",0);
//                        Log.v("money",String.valueOf(coin));
                        money.setText(String.valueOf(coin));
                    }
                });
            }
        }, 0, 500);


    }

    public void pomAnimation() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!touch) {
                            Gson gson = new Gson();
                            SharedPreferences shared = getSharedPreferences("my_ref", MODE_PRIVATE);
                            SharedPreferences.Editor editor = shared.edit();
                            String json = shared.getString("User", "");
                            user = gson.fromJson(json, User.class);
                            if (cleans){
                                change = (user.getPom()).Clean(change, pom,user.getPom().getClean());
                                user.getPom().setClean(user.getPom().getClean()+2);
                                if (user.getPom().getClean() >= 100) {
                                    user.getPom().setClean(100);
                                    cleans = false;
//                                    editor.putBoolean("clean",cleans);
                                }
                                json = gson.toJson(user);
                                editor.putString("User", json);
                                editor.commit();
                            }else if (user.getPom().getSick()) {
                                change = (user.getPom()).Sick(change, pom, user.getPom().getSick());
                            } else if (user.getPom().getClean() <= 50) {
                                change = (user.getPom()).Dirty(change, pom, user.getPom().getClean());
                            } else {
                                change = (user.getPom()).Move(change, pom);
                                changePos();
                            }
                        }
                    }
                });
            }
        }, 0, 150);
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
        if (me.getAction() == MotionEvent.ACTION_DOWN && user.getPom().getClean() > 50 &&
                !user.getPom().getSick() && user.getPom().getHunger() > 50 && user.getPom().getEnergy() > 50) {
            touch = true;
            if (user.getPom().getFun() > 50) {
                pom.setImageResource(R.drawable.sharknapom_happy);
            } else {
                pom.setImageResource(R.drawable.sharknapom_bored);
            }
            final Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    t.cancel();
                    touch = false;
                }
            }, 800, 1);
        }
        return true;
    }
}
