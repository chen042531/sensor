package com.example.sensing.Measurement;


import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Context.SENSOR_SERVICE;

public class PhoneState  extends PhoneStateListener {
    public static String phoneState = "IDLE"; //IDLE, OFFHOOK, RINGING
    public static String callState = "IDLE"; //Callout, ""Callin"", RINGING
    public static String callID = "null";
    public static String startCallTime, endCallTime;

    public static int callNum = 0, callExcessNum = 0;
    public static long callStartAt = 0, callHoldingTime = 0, excessLife = 0;
    public static double avgCallHoldTime = 0, avgExcessLife = 0;
    public static double ttlCallHoldTime = 0, ttlExcessLife = 0;
    public static boolean FirstCallCell = false;

    SimpleDateFormat sdf;
    Date LogDate;
    private TelephonyManager teleManger;

    private static Date LogTime;
    private static SimpleDateFormat logTimeSdf = new SimpleDateFormat("HH:mm:ss:SSS");

//    private JsonParser JsonParser = null;

    private WeakReference<Context> serviceContext;

    public PhoneState (Context context) {
        serviceContext = new WeakReference<>(context);
    }
//
//    public void setJsonParser(JsonParser json) {
//        JsonParser = json;
//    }

    @Override
    public void onCallStateChanged(final int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);
        //Writing file in new thread 2018/10/07
        new Thread(new Runnable() {
            @Override
            public void run() {
                LogTime = new Date();
                LogDate = new Date();
                sdf = new SimpleDateFormat("yyyyMMddHHmm");
                logTimeSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
                switch (state) {
                    case TelephonyManager.CALL_STATE_IDLE:
                        // OFFHOOK: At least one call exists that is dialing, active, or on hold, and no calls are ringing or waiting.
                        if (phoneState.equals("OFFHOOK")) {
                            callHoldingTime = System.currentTimeMillis() - callStartAt;
                            ttlCallHoldTime = ttlCallHoldTime + callHoldingTime;
                        }
                        phoneState = "IDLE";
                        phoneState = "IDLE";
                        callState = "IDLE";
                        FirstCallCell = false;
                        endCallTime = logTimeSdf.format(LogDate);
//                        FileMaker.write(JsonParser.phoneStateToJson(serviceContext.get()));
                        break;

                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        if (phoneState.equals("IDLE")) {
                            callState = "Callout";
                        }
                        if (phoneState.equals("RINGING")) {
                            callState = "Callin";
                        }
                        callNum = callNum + 1;
                        callStartAt = System.currentTimeMillis();
                        FirstCallCell = true;
                        phoneState = "OFFHOOK";
                        startCallTime = logTimeSdf.format(LogDate);

//                        FileMaker.write(JsonParser.phoneStateToJson(serviceContext.get()));
                        if (ContextCompat.checkSelfPermission(serviceContext.get(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                            callID = teleManger.getDeviceId().concat((sdf.format(LogDate)));//hank add callID 2016/09/12
                        }
                        break;

                    case TelephonyManager.CALL_STATE_RINGING:
                        phoneState = "RINGING";
                        callState = "RINGING";
                        break;
                }
            }
        }).start();

    }

    public static void calculateAvg() {
        if (callNum != 0) {
            avgCallHoldTime = ttlCallHoldTime / callNum;
        }
        if (callExcessNum != 0) {
            avgExcessLife = ttlExcessLife / callExcessNum;
        }
    }

    private void initBfRun() {
        avgCallHoldTime = 0;
        avgExcessLife = 0;
        callNum = 0;
        callExcessNum = 0;
        callStartAt = 0;
        callHoldingTime = 0;
        excessLife = 0;
        ttlCallHoldTime = 0;
        ttlExcessLife = 0;
        FirstCallCell = false;
    }

    public void startService(TelephonyManager tm1) {
        initBfRun();
        teleManger = tm1;
        teleManger.listen(this, LISTEN_CALL_STATE); // idle, ringing, offhook
    }

    public void stopService(TelephonyManager tm1) {
        teleManger = tm1;
        teleManger.listen(this,LISTEN_NONE);
    }



}
