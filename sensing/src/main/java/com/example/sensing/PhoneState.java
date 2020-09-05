package com.example.sensing;


import android.app.ActivityManager;
import android.content.Context;
import android.hardware.SensorManager;
import android.widget.TextView;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Context.SENSOR_SERVICE;

public class PhoneState  {
    public static double ram_uti;
    public Context mContext;
    private ActivityManager actManager;
    public PhoneState(Context mContext) {
        this.mContext = mContext;
        this.actManager = (ActivityManager)mContext.getSystemService(ACTIVITY_SERVICE);
    }
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
