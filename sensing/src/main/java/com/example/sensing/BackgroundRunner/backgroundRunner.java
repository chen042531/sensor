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
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.sensing.Data.DataListener;
import com.example.sensing.Data.SGData;
import com.example.sensing.Measurement.CellularInfo;
import com.example.sensing.Measurement.LocationInfo;
import com.example.sensing.Measurement.NetworkState;
import com.example.sensing.Measurement.PhoneInfo;
import com.example.sensing.Measurement.PhoneState;
import com.example.sensing.Measurement.SensorInfo;
import com.example.sensing.Measurement.WiFiInfo;

import org.json.JSONException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class backgroundRunner extends Service {

    public static SGData sgData;
    private int mInterval = 5000; // 5 seconds by default, can be changed later
    private Handler mHandler;





    private CellularInfo cellularInfo;
    private LocationInfo gps;
    private NetworkState networkState;
    private PhoneInfo phoneInfo;
    private PhoneState phoneState;
    private SensorInfo sensorInfo;
    private WiFiInfo wifiInfo;

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
//
        cellularInfo = new CellularInfo(this);
        cellularInfo.startService(new DataListener(cellularInfo){
            @Override
            public void onDataReceived() {
                sgData.cellInfoType = CellularInfo.cellInfoType;
                sgData.PreAtCellID = CellularInfo.PreAtCellID;
                //LTE
                sgData.lteCellRSRP = CellularInfo.lteCellRSRP;
                sgData.lteCellID = CellularInfo.lteCellID;
                sgData.lteCellMCC = CellularInfo.lteCellMCC;
                sgData.lteCellMNC = CellularInfo.lteCellMNC;
                sgData.lteCellPCI = CellularInfo.lteCellPCI;
                sgData.lteCellTAC = CellularInfo.lteCellTAC;
                sgData.lteCellRSSI = CellularInfo.lteCellRSSI;
                sgData.lteCellRSRQ = CellularInfo.lteCellRSRQ;
                sgData.lteCellRSSNR = CellularInfo.lteCellRSSNR;

                sgData.cellTimeInterval = CellularInfo.cellTimeInterval;

                //wcdma
                sgData.wcdmaAtCellPsc = CellularInfo.wcdmaAtCellPsc;
                sgData.wcdmaAtCellLac = CellularInfo.wcdmaAtCellLac;
                sgData.wcdmaAtCellID = CellularInfo.wcdmaAtCellID;
                sgData.wcdmaAtCellMCC = CellularInfo.wcdmaAtCellMCC;
                sgData.wcdmaAtCellMNC = CellularInfo.wcdmaAtCellMNC;
                sgData.wcdmaAtCellSignalStrength = CellularInfo.wcdmaAtCellSignalStrength;

                sgData.passCellNum = CellularInfo.passCellNum;
                sgData.nowCellID = CellularInfo.nowCellID;
                sgData.stayCellAt = CellularInfo.stayCellAt;
                sgData.cellHoldTime = CellularInfo.cellHoldTime;
                sgData.avgCellResidenceTime = CellularInfo.avgCellResidenceTime;
                sgData.avgCellHoldTime = CellularInfo.avgCellHoldTime;
                sgData.ttlCellResidenceTime = CellularInfo.ttlCellResidenceTime;



            }
        });
        gps = new LocationInfo(this);
        gps.startService(new DataListener(gps){
            @Override
            public void onDataReceived() {
//
                sgData.userlocationN = LocationInfo.userlocationN;
            }
        });
//
        networkState = new NetworkState(this);
        sgData.Network_type = networkState.getNetworkType();
//
        phoneInfo = new PhoneInfo(this);
        phoneInfo.startService(new DataListener(phoneInfo){
            @Override
            public void onDataReceived() {
                sgData.electricity = PhoneInfo.electricity;
                sgData.health = PhoneInfo.health ;
                sgData.level = PhoneInfo.level;
                sgData.lastLevel = PhoneInfo.lastLevel ;
                sgData.lastElect = PhoneInfo.lastElect;
                sgData.plugged = PhoneInfo.plugged ;
                sgData.present = PhoneInfo.present ;
                sgData.scale = PhoneInfo.scale ;
                sgData.status = PhoneInfo.status ;
                sgData.technology = PhoneInfo.technology;
                sgData.tmp = PhoneInfo.tmp ;
                sgData.temperature = PhoneInfo.temperature ;
                sgData.voltage = PhoneInfo.voltage ;
                sgData.screen_state = PhoneInfo.screen_state ;

            }
        });
//
        phoneState = new PhoneState(this);
        phoneState.startService(new DataListener(phoneState){
            @Override
            public void onDataReceived() {
                sgData.phoneState = PhoneState.phoneState;
                sgData.callState = PhoneState.callState;
                sgData.callID = PhoneState.callID;
                sgData.startCallTime = PhoneState.startCallTime;
                sgData.endCallTime = PhoneState.endCallTime;
                sgData.callNum = PhoneState.callNum;
                sgData.callExcessNum = PhoneState.callExcessNum;
                sgData.callStartAt = PhoneState.callStartAt;
                sgData.callHoldingTime =  PhoneState.callHoldingTime;
                sgData.excessLife =  PhoneState.callHoldingTime;
                sgData.avgCallHoldTime =  PhoneState.avgCallHoldTime;
                sgData.avgExcessLife =  PhoneState.avgExcessLife;
                sgData.ttlCallHoldTime =  PhoneState.ttlCallHoldTime;
                sgData.ttlExcessLife =  PhoneState.ttlCallHoldTime;
                sgData.FirstCallCell = PhoneState.FirstCallCell;
            }
        });
        sensorInfo =  new SensorInfo(this);
        sensorInfo.startService(new DataListener(sensorInfo){
            @Override
            public void onDataReceived() {
                sgData.gSensorValues = SensorInfo.gSensorValues;
                sgData.magneticValues = SensorInfo.magneticValues;
                sgData.orienValue = SensorInfo.orienValue;
                sgData.proximityValue = SensorInfo.proximityValue;
                sgData.lightValue = SensorInfo.lightValue;
                sgData.pressureValue = SensorInfo.pressureValue;

//                try {
//                    Log.i("background_sgData", sgData.getSGData().toString());
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        });


//        sgData.Network_type = networkState.getNetworkType();

        wifiInfo = new WiFiInfo(this);
        wifiInfo.getWiFiInfo();
        sgData.servingBSSID = WiFiInfo.servingBSSID;
        sgData.servingChan = WiFiInfo.servingChan;
        sgData.servingFreq = WiFiInfo.servingFreq;
        sgData.servingIP = WiFiInfo.servingIP;
        sgData.servingLevel = WiFiInfo.servingLevel;
        sgData.servingMAC = WiFiInfo.servingMAC;
        sgData.servingSSID = WiFiInfo.servingSSID;
        sgData.servingSpeed = WiFiInfo.servingSpeed;





////        https://medium.com/@JamesQI/android-foreground-service-4131531fb7f8
//        new Handler().postDelayed(new Runnable() {
//                                      @Override
//                                      public void run() {
//                                          Log.d("haha_service_start","ok");
//                                      }
//                                  }, 3000);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Log.d("haha_service_start","ok");
//                //    收集資料
//                //主動
//                //被動
//
//
//                //    寫入資料
//            }
//        }, 3000);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Log.d("haha_service_start","ok");
//                //    上傳資料
//            }
//        }, 3000);
//
//        Runnable helloRunnable = new Runnable() {
//            public void run() {
//                Log.d("haha_service_start","Hello world");
//
//            }
//        };

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
    Runnable sensingRunnable = new Runnable() {
        @Override
        public void run() {
            try {
//                updateStatus(); //this function can change value of mInterval.
                Log.d("haha_service_start_haha","Hello world");
                try {
                    Log.i("background_sgData", sgData.getSGData().toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler.postDelayed(sensingRunnable, mInterval);
            }
        }
    };

    void startRepeatingTask() {
        sensingRunnable.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(sensingRunnable);
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
