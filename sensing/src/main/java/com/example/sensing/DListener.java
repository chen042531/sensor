package com.example.sensing;

import android.hardware.SensorEventListener;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class DListener implements DataListener{
    sensor observerable;
    View tv;
    public DListener(sensor observerable){
        this.observerable = observerable;
    }
    public DListener(sensor observerable, View tv1){
        this.observerable = observerable;
        this.tv = tv1;
    }
    @Override
    public void onDataReceived() {
//        Log.i("ssss","dddd");
//        observerable.getAccData();

    }
}
