package com.pompom.pomji;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class PlayWithPom extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;

    TextView steps;

    float count=0;

    boolean running = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_with_pom);
        steps = (TextView)findViewById(R.id.steps);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

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
        SharedPreferences shared = getSharedPreferences("my_ref",MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putFloat("step",count);
        editor.commit();
        running = false;
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(running){

            SharedPreferences shared = getSharedPreferences("my_ref",MODE_PRIVATE);
            SharedPreferences.Editor editor = shared.edit();
            if(shared.getFloat("step",0)==0){
                editor.putFloat("step",0);
                editor.commit();
            }
            steps.setText(String.valueOf(sensorEvent.values[0]-shared.getFloat("step",0)));
            count = sensorEvent.values[0];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
