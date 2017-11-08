package com.pompom.pomji;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.util.Random;

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
        Gson gson = new Gson();

        SharedPreferences shared = getSharedPreferences("my_ref",MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();

        String json = shared.getString("User", "");
        User user = gson.fromJson(json, User.class);

        for (long i=1;i<=Long.MAX_VALUE;i++){
            Log.v("timer","i(intent is null)="+shared.getInt("time",0));
            Log.v("clean",""+user.getPom().getClean());
            try {
                Thread.sleep(1000);
            }catch (Exception e){

            }

            finaltime=1+shared.getInt("time",0);
            if(user.getPom().getClean()>0 && finaltime%1==0){
                user.getPom().setClean(user.getPom().getClean()-1);
            }
            if(user.getPom().getEnergy()>0 && finaltime%1==0){
                user.getPom().setEnergy(user.getPom().getEnergy()-1);
            }
            if (user.getPom().getHunger()>0 && finaltime%1==0){
                user.getPom().setHunger(user.getPom().getHunger()-1);
            }
            if (user.getPom().getFun()>0 & finaltime%1==0){
                user.getPom().setFun(user.getPom().getFun()-1);
            }
            if(finaltime%259200==0 && !user.getPom().getSick()){
                Random rand = new Random();
                int s = rand.nextInt(1);
                if(s==0){
                    user.getPom().setSick(true);
                }
            }
            json = gson.toJson(user);
            editor.putString("User", json);
            editor.putInt("time",finaltime);


            editor.commit();
        }
    }
}
