package com.pompom.pomji;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;

public class PlayWithPom extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;

    TextView steps;

    float count = 0;

    boolean running = false;

    boolean change = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_with_pom);
        steps = (TextView) findViewById(R.id.steps);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        SharedPreferences shared = getSharedPreferences("my_ref", MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        Gson gson = new Gson();
        final User user;
        String json = shared.getString("User", "");
        user = gson.fromJson(json, User.class);

        ProgressBar funbar = (ProgressBar) findViewById(R.id.funbar);
        final ImageView pom = (ImageView) findViewById(R.id.pom);

        FunBar funBar = new FunBar(funbar);

        funBar.decrease(shared);

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        change = user.getPom().Move(change, pom);
                    }
                });
            }
        }, 0, 180);


    }

    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Sensor not Found!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences shared = getSharedPreferences("my_ref", MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        Gson gson = new Gson();
        String json = shared.getString("User", "");
        User user = gson.fromJson(json, User.class);
        user.getPom().setFun((int) count / 5);
        if(user.getPom().getFun()>100){
            user.getPom().setFun(100);
        }
        json = gson.toJson(user);
        editor.putString("User", json);
        editor.putFloat("step", count);
        editor.commit();
        running = false;
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (running) {

            SharedPreferences shared = getSharedPreferences("my_ref", MODE_PRIVATE);
            SharedPreferences.Editor editor = shared.edit();
            if (shared.getFloat("step", 0) == 0) {
                editor.putFloat("step", 0);
                editor.commit();
            }
            steps.setText(String.valueOf(sensorEvent.values[0] - shared.getFloat("step", 0)));
            count = sensorEvent.values[0];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
