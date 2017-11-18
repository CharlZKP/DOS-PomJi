package com.pompom.pomji;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ImageView imgLogo = (ImageView) findViewById(R.id.logo);
        ObjectAnimator logo = ObjectAnimator.ofFloat(imgLogo,"translationY",-1500,0);
        logo.setDuration(1300);
        ObjectAnimator logo2 = ObjectAnimator.ofFloat(imgLogo,"rotation",0,360);
        logo2.setDuration(1300);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(logo,logo2);
        animatorSet.start();
        Thread t = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                    Intent intent = new Intent(getApplicationContext(), Intro.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }
}
