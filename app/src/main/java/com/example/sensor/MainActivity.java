package com.example.sensor;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.sensing.sensor;

import static com.example.sensing.sensor.gSensorValues;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private sensor sr ;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sr =  new sensor();
        sr.print();
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sr.startService(sensorManager);
        Log.i("ggff", String.valueOf("ACCELEROMETER"+gSensorValues[0]+","+gSensorValues[1]+","+gSensorValues[2]));
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("ggff", String.valueOf("ACCELEROMETER"+gSensorValues[0]+","+gSensorValues[1]+","+gSensorValues[2]));
            }
        }).start();
        text = (TextView)findViewById(R.id.textView);

    }
}