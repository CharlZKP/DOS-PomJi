package com.pompom.pomji;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
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




        Intent notiIntent = new Intent(this,main.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notiIntent, 0);

        for (long i=1;i<=Long.MAX_VALUE;i++){
            Gson gson = new Gson();

            SharedPreferences shared = getSharedPreferences("my_ref",MODE_PRIVATE);
            SharedPreferences.Editor editor = shared.edit();
            String json = shared.getString("User", "");
            User user = gson.fromJson(json, User.class);

            Log.v("timer","timer="+shared.getInt("time",0));
            Log.v("clean",""+user.getPom().getClean());
            try {
                Thread.sleep(1000);
            }catch (Exception e){

            }

            finaltime=1+shared.getInt("time",0);
            if(user.getPom().getClean()>0 && finaltime%144==0){
                user.getPom().setClean(user.getPom().getClean()-1);
                json = gson.toJson(user);
                editor.putString("User", json);
                editor.commit();
            }
            if(user.getPom().getEnergy()>0 && finaltime%432==0){
                user.getPom().setEnergy(user.getPom().getEnergy()-1);
                json = gson.toJson(user);
                editor.putString("User", json);
                editor.commit();
            }
            if (user.getPom().getHunger()>0 && finaltime%108==0){
                user.getPom().setHunger(user.getPom().getHunger()-1);
                json = gson.toJson(user);
                editor.putString("User", json);
                editor.commit();
            }
            if (user.getPom().getFun()>0 & finaltime%864==0){
                user.getPom().setFun(user.getPom().getFun()-1);
                json = gson.toJson(user);
                editor.putString("User", json);
                editor.commit();
            }
            if(finaltime%259200==0 && !user.getPom().getSick()){
                Random rand = new Random();
                int s = rand.nextInt(1);
                if(s==0){
                    user.getPom().setSick(true);
                }
                json = gson.toJson(user);
                editor.putString("User", json);
                editor.commit();
            }
            if(user.getPom().getHunger()==30){
                Notification notification =
                        new NotificationCompat.Builder(this)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("Pom Alert!!")
                                .setContentText("้ป้อนข้าวด้วย หิวจะตายแล้ว -3-")
                                .setAutoCancel(true)
                                .setContentIntent(pendingIntent)
                                .build();
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(0, notification);

            }
            if(user.getPom().getSick()){
                Notification notification =
                        new NotificationCompat.Builder(this)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("Pom Alert!!")
                                .setContentText("ตัวร้อนจี๋เลย สงสัยจะเป็นไข้")
                                .setAutoCancel(true)
                                .setContentIntent(pendingIntent)
                                .build();
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(1, notification);

            }
            if(user.getPom().getClean()==30){
                Notification notification =
                        new NotificationCompat.Builder(this)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("Pom Alert!!")
                                .setContentText("เหม็น~ อาบน้ำด้วยกัน <3")
                                .setAutoCancel(true)
                                .setContentIntent(pendingIntent)
                                .build();
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(2, notification);

            }
            if(user.getPom().getEnergy()==30){
                Notification notification =
                        new NotificationCompat.Builder(this)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("Pom Alert!!")
                                .setContentText("ง่วงแล้ว พาเข้านอนหน่อยสิ Zzz")
                                .setAutoCancel(true)
                                .setContentIntent(pendingIntent)
                                .build();
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(3, notification);

            }

            if(user.getPom().getFun()==30){
                Notification notification =
                        new NotificationCompat.Builder(this)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("Pom Alert!!")
                                .setContentText("เบื่อจัง อยากไปเดินเล่น")
                                .setAutoCancel(true)
                                .setContentIntent(pendingIntent)
                                .build();
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(4, notification);

            }
            editor.putInt("time", finaltime);
            editor.commit();
        }
    }
}
