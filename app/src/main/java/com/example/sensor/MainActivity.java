package com.example.sensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.widget.TextView;

import com.example.sensing.CellInfo;
import com.example.sensing.DataListener;
import com.example.sensing.WiFiInfo;
import com.example.sensing.sensor;

import static com.example.sensing.sensor.gSensorValues;
import static com.example.sensing.sensor.tt;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private sensor sr ;
    private WiFiInfo wifiinfo;
    private CellInfo cellInfo         ;
    public  TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sr =  new sensor(this);
//        sr.print();
//        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sr.startService(new DataListener(){

            @Override
            public void onDataReceived() {
//                Log.i("ssss","dddd");
            }
        });

//        wifiinfo = new WiFiInfo(this);
//        wifiinfo.getWiFiInfo();


    }
}