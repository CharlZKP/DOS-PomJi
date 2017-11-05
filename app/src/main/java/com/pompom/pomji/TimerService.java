package com.pompom.pomji;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by DOS on 11/4/2017.
 */

public class TimerService extends IntentService {
    public TimerService(){
        super("Timer Service");
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.v("timer","Timer service has started.");
    }
    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        super.onStartCommand(intent,flags,startId);

        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(Intent intent){
        int finaltime=0;
        SharedPreferences shared = getSharedPreferences("my_ref",MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        for (int i=1;i<=Integer.MAX_VALUE;i++){
            Log.v("timer","i(intent is null)="+shared.getInt("time",0));
            try {
                Thread.sleep(1000);
            }catch (Exception e){

            }
            finaltime=1+shared.getInt("time",0);
            editor.putInt("time",finaltime);
            editor.commit();
        }
    }
}
