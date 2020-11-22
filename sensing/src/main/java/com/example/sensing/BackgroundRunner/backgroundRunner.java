package com.example.sensing.BackgroundRunner;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.GpsStatus;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.sensing.Data.SGData;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class backgroundRunner extends Service {

    public static SGData sgData;
    private int mInterval = 5000; // 5 seconds by default, can be changed later
    private Handler mHandler;

    ///////
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("haha_service_start","start");
        Log.d("haha_service_start","start");
        notifyMsg();
        sgData = new SGData();
//        https://medium.com/@JamesQI/android-foreground-service-4131531fb7f8
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

//        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
//        executor.scheduleAtFixedRate(helloRunnable, 0, 3, TimeUnit.SECONDS);
        mHandler = new Handler();
        startRepeatingTask();
        
        return  START_REDELIVER_INTENT;
    }
    public void notifyMsg() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startMyOwnForeground();
        }
        else {
            startForeground(1, new Notification());
        }

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }
    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
//                updateStatus(); //this function can change value of mInterval.
                Log.d("haha_service_start_haha","Hello world");

            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private void startMyOwnForeground(){
        String NOTIFICATION_CHANNEL_ID = "com.example.simpleapp";
        String channelName = "My Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle("App is running")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();

        startForeground(2, notification);

    }
}
