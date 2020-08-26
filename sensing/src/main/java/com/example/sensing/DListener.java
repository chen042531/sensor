package com.example.sensing;

import android.hardware.SensorEventListener;
import android.util.Log;
import android.widget.TextView;

public class DListener implements DataListener{
    sensor observerable;
    TextView tv;
    public DListener(sensor observerable){
        this.observerable = observerable;
    }
    public DListener(sensor observerable, TextView tv1){
        this.observerable = observerable;
        this.tv = tv1;
    }
    @Override
    public void onDataReceived() {
//        Log.i("ssss","dddd");
//        observerable.getAccData();

    }
}
