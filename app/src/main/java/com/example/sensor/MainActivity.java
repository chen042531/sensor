package com.example.sensor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.sensing.Measurement.CellularInfo;
import com.example.sensing.Data.DataListener;
import com.example.sensing.Measurement.GPSInfo;
import com.example.sensing.Measurement.PhoneState;
import com.example.sensing.Measurement.WiFiInfo;
import com.example.sensing.Measurement.SensorInfo;
import com.example.sensing.SensingGO;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private SensorInfo sr ;
    private GPSInfo gps;
    private WiFiInfo wifiinfo;
    private CellularInfo cell;
    private PhoneState phone;
    public  TextView text;
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
//        startService(new Intent(this, background.class));
        sr =  new SensorInfo(this);
//        sr.print();
//        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        final TextView tv1 = (TextView)findViewById(R.id.textView2);

        sr.startService(new DataListener(sr,tv1){
            @Override
            public void onDataReceived() {
//                Log.i("ssss","dddd");
                Log.i("mainsensor", "sensor: "+sr.getData());
//                String s = String.valueOf(sr.getData()[1]);
//                tv1.setText(s);
            }
        });

        gps = new GPSInfo(this);
        Log.i("gps_enable", String.valueOf(GPSInfo.checkGPSalive()));
        gps.startGPS(this);
        gps.getGPS(this);

        new SensingGO(this,"haha");

        wifiinfo = new WiFiInfo(this);
        wifiinfo.getWiFiInfo();
//
//
//        cell = new CellularInfo(this);
//        cell.startService();
//
//        phone = new PhoneState(this);
//        Log.i("phone",phone.cpuUti());


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
