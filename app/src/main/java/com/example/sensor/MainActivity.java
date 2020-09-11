package com.example.sensor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.widget.TextView;

import com.example.sensing.CellInfo;
import com.example.sensing.DListener;
import com.example.sensing.DataListener;
import com.example.sensing.GPSInfo;
import com.example.sensing.WiFiInfo;
import com.example.sensing.sensor;

import static com.example.sensing.sensor.gSensorValues;
import static com.example.sensing.sensor.tt;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private sensor sr ;
    private GPSInfo gps;
    private WiFiInfo wifiinfo;
    private CellInfo cellInfo         ;
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
        gps.startGPS(this);
//        Thread.sleep(8000);
        gps.getGPS(this);


//        wifiinfo = new WiFiInfo(this);
//        wifiinfo.getWiFiInfo();

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
