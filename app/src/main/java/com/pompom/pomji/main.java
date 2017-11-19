package com.pompom.pomji;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
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
    private Sharknapom pom;
    @Expose
    private int money=3000;

    public void setMoney(int money){
        this.money = money;
    }

    public int getMoney(){
        return money;
    }

    public void setName(String n) {
        name = n;
    }

    public String getName() {
        return name;
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
        hunger = 30;
        energy = 30;
        clean = 30;
        fun = 30;
        sleep = false;
        sick = true;
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

    abstract boolean Sick(boolean change, ImageView img);

    abstract boolean Dirty(boolean change, ImageView img, int c);

    abstract boolean Hungry(boolean change, ImageView img, int h);

    abstract boolean Sleepy(boolean change, ImageView img, int j);


    protected boolean getSick() {
        return sick;
    }

    protected void setSick(boolean s) {
        sick = s;
    }

    public boolean Clean(boolean change, ImageView img, int k) {
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

    public boolean Sleep(boolean change, ImageView img) {
        if (change) {
            img.setImageResource(R.drawable.sharknapomsleeping1);
            return false;
        } else {
            img.setImageResource(R.drawable.sharknapomsleeping1_1);
            return true;
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

    public boolean Sick(boolean change, ImageView img) {

        if (change) {
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

    public boolean Hungry(boolean change, ImageView img, int h) {
        if (h <= 50 && h > 30) {
            if (change) {
                img.setImageResource(R.drawable.sharknapomhungry1);
                return false;
            } else {
                img.setImageResource(R.drawable.sharknapomhungry1_1);
                return true;
            }
        } else {
            if (change) {
                img.setImageResource(R.drawable.sharknapomhungry1);
                return false;
            } else {
                img.setImageResource(R.drawable.sharknapomhungry1_1);
                return true;
            }
        }
    }

    public boolean Sleepy(boolean change, ImageView img, int j) {
        if (j <= 50 && j > 30) {
            if (change) {
                img.setImageResource(R.drawable.sharknapomsleepy);
                return false;
            } else {
                img.setImageResource(R.drawable.sharknapomsleepy1_1);
                return true;
            }
        } else {
            if (change) {
                img.setImageResource(R.drawable.sharknapomsleepy);
                return false;
            } else {
                img.setImageResource(R.drawable.sharknapomsleepy1_1);
                return true;
            }
        }
    }

}


//class Udinopom extends Pom {
//    public Udinopom() {
//        super();
//    }
//    public boolean Hungry(boolean change, ImageView img, int h) {
//        return true;
//    }
//    public boolean Dirty(boolean change, ImageView img, int c) {
//        return true;
//    }
//    public boolean Sleepy(boolean change, ImageView img,int s){ return true;}
//
//    public boolean Sick(boolean change, ImageView img, boolean s) {
//        return true;
//    }
//
//    public boolean Move(boolean change, ImageView img) {
//        if (change) {
//            img.setImageResource(R.drawable.sharknapomwalk);
//            return false;
//        } else {
//            img.setImageResource(R.drawable.udinopom);
//            return true;
//        }
//    }
//}

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
    private boolean cleans = false;
    private boolean sleepy = false;
//    private int coin;
    private int sum = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences shared = getSharedPreferences("my_ref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = shared.edit();
        final Gson gson = new Gson();
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
        Button sleepButton = (Button) findViewById(R.id.sleepButton);
        Button inventoryButton = (Button) findViewById(R.id.inventoryButton);
        final TextView money = (TextView) findViewById(R.id.Money);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.button);

        inventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                if (!sleepy) {
                    Intent intent = new Intent(main.this, Inventory.class);
                    startActivity(intent);
                }
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                Intent intent = new Intent(main.this, PlayWithPom.class);
                startActivity(intent);
            }
        });
        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                Intent intent = new Intent(main.this, Shop.class);
                startActivity(intent);
            }
        });
        cleanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                if (!sleepy) cleans = true;
            }
        });
        sleepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConstraintLayout cl = (ConstraintLayout) findViewById(R.id.all);
                mp.start();
                if (sleepy) {
                    sleepy = false;
                    cl.setBackgroundResource(R.drawable.bg_main);
                } else {
                    sleepy = true;
                    cl.setBackgroundResource(R.drawable.bg_main_night);
                }
            }
        });


        if (first) {
            user = new User();
            user.setName(shared.getString("userName", "None"));
            user.buyPom();
            String json = gson.toJson(user);
            editor.putString("User", json);
//            editor.putInt("coin", 3000);
            editor.putBoolean("first", false);
            editor.commit();
        } else {
            String json = shared.getString("User", "");
            user = gson.fromJson(json, User.class);
//            coin = shared.getInt("coin", 0);
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
                        String json = shared.getString("User", "");
                        User user2 = gson.fromJson(json, User.class);
                        money.setText(String.valueOf(user2.getMoney()));
                    }
                });
            }
        }, 0, 500);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                int energy = data.getIntExtra("Energy", 0);
                user.getPom().setEnergy(user.getPom().getEnergy() + energy);
            }
        }
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

                            if (sleepy) {
                                change = (user.getPom()).Sleep(change, pom);
                                sum += 1;
                                if (sum == 500) {
                                    user.getPom().setEnergy(user.getPom().getEnergy() + 1);
                                    sum = 0;
                                }
                                if (user.getPom().getEnergy() > 100) {
                                    user.getPom().setEnergy(100);
                                } else {
                                    json = gson.toJson(user);
                                    editor.putString("User", json);
                                    editor.commit();
                                }

                            } else {

                                if (cleans) {
                                    change = (user.getPom()).Clean(change, pom, user.getPom().getClean());
                                    user.getPom().setClean(user.getPom().getClean() + 2);
                                    if (user.getPom().getClean() > 100) {
                                        user.getPom().setClean(100);
                                        cleans = false;
                                    } else {
                                        json = gson.toJson(user);
                                        editor.putString("User", json);
                                        editor.commit();
                                    }
                                } else if (user.getPom().getSick()) {
                                    change = (user.getPom()).Sick(change, pom);
                                } else if (user.getPom().getHunger() <= 50) {
                                    change = (user.getPom()).Hungry(change, pom, user.getPom().getHunger());
                                } else if (user.getPom().getClean() <= 50) {
                                    change = (user.getPom()).Dirty(change, pom, user.getPom().getClean());
                                } else if (user.getPom().getEnergy() <= 50) {
                                    change = (user.getPom()).Sleepy(change, pom, user.getPom().getEnergy());
                                } else {
                                    change = (user.getPom()).Move(change, pom);
                                    changePos();
                                }
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
        if (me.getAction() == MotionEvent.ACTION_DOWN && !cleans &&
                !user.getPom().getSick() && user.getPom().getHunger() > 50 && !sleepy) {
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
