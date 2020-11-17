package com.example.sensing;

import android.Manifest;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.os.BatteryManager;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;

import static android.content.Context.ACTIVITY_SERVICE;

public class CellInfo  extends BroadcastReceiver {

//    getSystemService(Context.BATTERY_SERVICE)
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
    public  static String tmp;
    public static double temperature = 0.0;
    public static int voltage = 0;
public static String screen_state = "on";
    public void startService(TelephonyManager telephony) {

//        if (telephony.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {
//            GsmCellLocation location = (GsmCellLocation) telephony.getCellLocation();
//            if (location != null) {
////                msg.setText("LAC: " + location.getLac() + " CID: " + location.getCid());
//                Log.i("cellinfo","LAC: " + location.getLac() + " CID: " + location.getCid());
//            }
//        }

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
        icon_small = intent.getIntExtra(BatteryManager.EXTRA_ICON_SMALL, 0);
        level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
        present = intent.getExtras().getBoolean(BatteryManager.EXTRA_PRESENT);
        scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
        status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);
        technology = intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
        temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) * 0.1;
        tmp = String.valueOf(intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) * 0.1);
        electricity = level / (double)scale * 100;
        voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);



        //////////




        String action = intent.getAction();
        if (Intent.ACTION_SCREEN_ON.equals(action)) {
            screen_state = "on";
        }
        else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
            screen_state = "off";
        }
    }

    public static double ram_uti;
    public Context mContext;
    private ActivityManager actManager;

    public void ram() {
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        actManager.getMemoryInfo(memInfo);
        long totalMemory = memInfo.totalMem;
        long Free = memInfo.availMem;
        long Busy = totalMemory - Free;
        DecimalFormat df = new DecimalFormat("##.00");
        ram_uti = (double) Busy / (double) totalMemory;
        ram_uti = Double.parseDouble(df.format(ram_uti));
        /*Log.d("LINYIHAO", "Total memory: " + bytesToHuman(totalMemory)
                                + " Free: " + bytesToHuman(Free)
                                + " Busy: " + bytesToHuman(Busy));*/
    }
    public static String cpuUti() {
        double cpu_uti = readUsage();
        DecimalFormat df = new DecimalFormat("##.00");
        cpu_uti = Double.parseDouble(df.format(cpu_uti));
        return String.valueOf(cpu_uti);
    }
    public static String displayPhoneState() {

        double cpu_uti = readUsage();
        DecimalFormat df = new DecimalFormat("##.00");
        cpu_uti = Double.parseDouble(df.format(cpu_uti));
        return "\t\"CPU\": " + cpu_uti
                + "\n\t\"RAM\": " + ram_uti;
    }
    // Ref: http://www.linuxhowtos.org/System/procstat.htm
    private static float readUsage() {
        try {
            RandomAccessFile reader = new RandomAccessFile("/proc/stat", "r");
            String load = reader.readLine();

            String[] toks = load.split(" ");

            // Log.d("/proc", "/stat: " + load);
            // Log.d("0202***", "file tokens: " + "1: " + toks[1] + "," + " 2: " + toks[2] + ","
            //        + " 3: " + toks[3] + "," + " 4: " + toks[4] + "," + " 5: " + toks[5] + ","
            //        + " 6: " + toks[6] + " 7: " + toks[7] + "," + " 8: " + toks[8]);


            long idle1 = Long.parseLong(toks[5]);
            long cpu1 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[4])
                    + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

            try {
                Thread.sleep(360);
            } catch (Exception e) {
            }

            reader.seek(0);
            load = reader.readLine();
            reader.close();

            toks = load.split(" ");

            long idle2 = Long.parseLong(toks[5]);
            long cpu2 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[4])
                    + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

            if (((cpu2 + idle2) - (cpu1 + idle1)) <= 0)
                return (float) 0;
            else
                return (float) (cpu2 - cpu1) / ((cpu2 + idle2) - (cpu1 + idle1));

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return 0;
    }
}
