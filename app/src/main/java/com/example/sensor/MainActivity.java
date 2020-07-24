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
import com.example.sensing.CellularInfo;
import com.example.sensing.sensor;

import static com.example.sensing.sensor.gSensorValues;
import static com.example.sensing.sensor.tt;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private sensor sensor ;
    private CellInfo cellInfo         ;
    private CellularInfo cellularInfo;
    public  TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensor =  new sensor(this,text);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor.startService(sensorManager);

        cellInfo = new CellInfo();
        TelephonyManager telephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        cellInfo.startService(telephony);

        cellularInfo = new CellularInfo();
//        TelephonyManager telephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        cellularInfo.startService(telephony);
//        Log.i("ggff", String.valueOf("ACCELEROMETER"+gSensorValues[0]+","+gSensorValues[1]+","+gSensorValues[2]));
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Log.i("ggff", String.valueOf("ACCELEROMETER"+gSensorValues[0]+","+gSensorValues[1]+","+gSensorValues[2]));
//            }
//        }).start();
//        text = (TextView)findViewById(R.id.textView);
//        text.setText("gg");

    }
}