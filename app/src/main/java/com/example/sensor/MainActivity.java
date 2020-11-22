package com.example.sensor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.HardwarePropertiesManager;
import android.util.Log;
import android.widget.TextView;

import com.example.sensing.Data.SGData;
import com.example.sensing.FileMaker.writeFile;
import com.example.sensing.FileSender.sendData;
import com.example.sensing.Measurement.CellularInfo;
import com.example.sensing.Data.DataListener;
import com.example.sensing.Measurement.LocationInfo;
import com.example.sensing.Measurement.NetworkState;
import com.example.sensing.Measurement.PhoneInfo;
import com.example.sensing.Measurement.PhoneState;
import com.example.sensing.Measurement.WiFiInfo;
import com.example.sensing.Measurement.SensorInfo;
import com.example.sensing.SensingGO;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import static android.os.BatteryManager.EXTRA_PLUGGED;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private SensorInfo sr ;
    private LocationInfo gps;
    private WiFiInfo wifiinfo;
    private CellularInfo cell;
    private NetworkState networkstate;
    private PhoneState phoneState;
    private PhoneInfo phoneInfo;
    private CellularInfo cellularInfo;
    public  TextView text;

    public HardwarePropertiesManager hardwarePropertiesManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
        };

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

//
//        s.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//        startService(new Intent(this, background.class));
        sr =  new SensorInfo(this);
//        sr.print();
//        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        final TextView tv1 = (TextView)findViewById(R.id.textView2);
//        tv1.setText("jjjjjjjj");
        sr.startService(new DataListener(sr,tv1){
            @Override
            public void onDataReceived() {
//                Log.i("ssss","dddd");
                Log.i("mainsensor", "sensor: "+sr.getData());
                Log.i("mainsensor", "sensor_dddd: "+sr.magneticValues[0]);
                String s = String.valueOf(sr.magneticValues[0]);
                tv1.setText(s);
            }
        },1000);

        gps = new LocationInfo(this);
        Log.i("gps_enable", String.valueOf(LocationInfo.checkGPSalive()));
        gps.startGPS(this);
        gps.getGPS(this);

        new SensingGO(this,"haha");

        wifiinfo = new WiFiInfo(this);
        wifiinfo.getWiFiInfo();

        networkstate = new NetworkState(this);
        networkstate.networkstateTest();

        phoneInfo = new PhoneInfo(this);
        try {
            Log.i("cpu", String.valueOf(phoneInfo.cpuInfo()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        phoneInfo.ram();

        Log.i("cpu_vol", String.valueOf(phoneInfo.voltage));
        phoneState = new PhoneState(this);
        phoneState.startService();
        cellularInfo = new CellularInfo(this);
        cellularInfo.startService();

        SGData sdata = new SGData();
        try {
            Log.i("SGData",sdata.getSGData().toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            writeFile write_file = new writeFile(this);
            write_file.setUserID("user6");
            write_file.write(sdata);
            write_file.write(sdata);
            write_file.write(sdata);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendData s = new sendData(this);
        s.setUserID("user6");
        s.execute();

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
