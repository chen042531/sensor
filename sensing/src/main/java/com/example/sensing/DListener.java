package com.example.sensing;

import android.hardware.SensorEventListener;
import android.util.Log;

public class DListener implements DataListener{
    sensor observerable;

    public DListener(sensor observerable){
        this.observerable = observerable;
    }
    @Override
    public void onDataReceived() {
//        Log.i("ssss","dddd");
//        observerable.getAccData();

    }
}
