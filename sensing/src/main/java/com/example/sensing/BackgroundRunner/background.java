package com.example.sensing.BackgroundRunner;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.sensing.Measurement.TrafficSnapshot;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class background  extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("haha_service_start","start");
        Log.d("haha_service_start","start");
        new TrafficSnapshot(this);
        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          Log.d("haha_service_start","ok");
                                      }
                                  }, 3000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("haha_service_start","ok");
                //    收集資料
                //主動
                //被動


                //    寫入資料
            }
        }, 3000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("haha_service_start","ok");
                //    上傳資料
            }
        }, 3000);

        Runnable helloRunnable = new Runnable() {
            public void run() {
                Log.d("haha_service_start","Hello world");

            }
        };

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(helloRunnable, 0, 3, TimeUnit.SECONDS);

        
        return  START_NOT_STICKY;
    }
}
