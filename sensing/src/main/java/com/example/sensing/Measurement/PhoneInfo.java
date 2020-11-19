package com.example.sensing.Measurement;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Objects;

import static android.content.Context.ACTIVITY_SERVICE;

public class PhoneInfo extends BroadcastReceiver {


    public static double electricity;
    public static int health = 0;
    public static int icon_small = 0;
    public static int level = 0;
    public static int lastLevel = -1;
    public static double lastElect = -1;
    public static int plugged = 0;
    public static boolean present = false;
    public static int scale = 0;
    public static int status = 0;
    public static String technology = "0";
    public static String tmp;
    public static double temperature = 0.0;
    public static int voltage = 0;
    public static String screen_state = "on";

    public static double ram_uti;
    public Context mContext;
    private ActivityManager actManager;

    public PhoneInfo(Context mContext) {
        this.mContext = mContext;
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        mContext.registerReceiver(this, intentFilter);

//        this.sensorManager = (SensorManager)mContext.getSystemService(SENSOR_SERVICE);
    }
    @Override
    public void onReceive(Context context, Intent intent) {

        health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
        icon_small = intent.getIntExtra(BatteryManager.EXTRA_ICON_SMALL, 0);
        level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
        present = Objects.requireNonNull(intent.getExtras()).getBoolean(BatteryManager.EXTRA_PRESENT);
        scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
        status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);
        technology = intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
        temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) * 0.1;
        tmp = String.valueOf(intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) * 0.1);
        electricity = level / (double)scale * 100;
        voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 10);
        Log.i("onBattery", String.valueOf(electricity));
        Log.i("onBattery", String.valueOf(temperature));

        String action = intent.getAction();
        if (Intent.ACTION_SCREEN_ON.equals(action)) {
            screen_state = "on";
        }
        else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
            screen_state = "off";
        }
        Log.i("onBattery", String.valueOf(screen_state));
    }





    public void ram() {
//        http://androidbiancheng.blogspot.com/2012/12/memoryinfo.html

        ActivityManager activityManager = (ActivityManager)mContext.getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);

        String info =
                "available memory : " + String.valueOf(memoryInfo.availMem) + "\n"
                        + "currently be in a low memory : " + String.valueOf(memoryInfo.lowMemory) + "\n"
                        + "threshold low memory : " + String.valueOf(memoryInfo.threshold) + "\n"
                        + "total memory accessible by the kernel : " + String.valueOf(memoryInfo.totalMem);
        Log.i("cpu", info);



    }
//    https://stackoverflow.com/questions/49854637/hardwarepropertiesmanager-is-not-working
//HardwarePropertiesManager hardware = (HardwarePropertiesManager) getSystemService(Context.HARDWARE_PROPERTIES_SERVICE);
////        float[] cpuTemperature = hardware.getDeviceTemperatures(HardwarePropertiesManager.DEVICE_TEMPERATURE_CPU, HardwarePropertiesManager.TEMPERATURE_CURRENT);
//        hardware.getCpuUsages();
    public static String cpuInfo() throws IOException {
        String[] DATA = {"/system/bin/cat", "/proc/cpuinfo"};
        ProcessBuilder processBuilder = new ProcessBuilder(DATA);
        Process process = processBuilder.start();
        InputStream inputStream = process.getInputStream();
        byte[] byteArry = new byte[1024];
        String output = "";
        while (inputStream.read(byteArry) != -1) {
            output = output + new String(byteArry);
        }
        inputStream.close();
        return output;

    }
//    https://stackoverflow.com/questions/2467579/how-to-get-cpu-usage-statistics-on-android


    // Ref: http://www.linuxhowtos.org/System/procstat.htm
}
