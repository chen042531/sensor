package com.example.sensor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.util.TimingLogger;
import android.widget.TextView;

import com.example.sensing.CellInfo;
import com.example.sensing.CellularInfo;
import com.example.sensing.DListener;
import com.example.sensing.DataListener;
import com.example.sensing.GPSInfo;
import com.example.sensing.PhoneState;
import com.example.sensing.WiFiInfo;
import com.example.sensing.sensor;
import com.example.sensing.GPSInfo;
import com.example.sensing.sendData;
import com.example.sensing.writeFile;

import java.io.IOException;

import static com.example.sensing.sensor.gSensorValues;
import static com.example.sensing.sensor.tt;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private sensor sr ;
    private GPSInfo gps;
    private WiFiInfo wifiinfo;
    private CellularInfo cell;
    private PhoneState phone;
    private sendData s;
    private writeFile write_file;
    public  TextView text;
    private float start, end;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        sr =  new sensor(this);
//        sr.print();
//        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        final TextView tv1 = (TextView)findViewById(R.id.textView2);

        sr.startService(new DListener(sr,tv1){
            @Override
            public void onDataReceived() {
                Log.i("ssss","dddd");
                String s = String.valueOf(sr.getAccData());
                tv1.setText(s);
            }
        });

        gps = new GPSInfo(this);
        Log.i("gps_enable", String.valueOf(GPSInfo.checkGPSalive()));
        gps.startGPS(this);
        gps.getGPS(this);

        start = System.currentTimeMillis();

        wifiinfo = new WiFiInfo(this);
        wifiinfo.getWiFiInfo();
        Log.i("gps_enable", String.valueOf(GPSInfo.checkGPSalive()));
        Log.i("gps_enable", String.valueOf(GPSInfo.checkGPSalive()));
        end =  System.currentTimeMillis();
        Log.i("TimeTest", String.valueOf(start));
        Log.i("TimeTest", String.valueOf(end));
        Log.i("TimeTest", String.valueOf((System.currentTimeMillis()-start)/1000000)+ "mS\n");
//
//        cell = new CellularInfo(this);
//        cell.startService();

        phone = new PhoneState(this);
        Log.i("phone",phone.cpuUti());


        try {
            write_file = new writeFile(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        s = new sendData(this);

        s.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
