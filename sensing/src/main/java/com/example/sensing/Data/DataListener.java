package com.example.sensing.Data;

import android.view.View;

import com.example.sensing.Measurement.sensor;

public class DataListener implements DataListenerInterface {
    sensor observerable;
    View tv;
    public DataListener(sensor observerable){
        this.observerable = observerable;
    }
    public DataListener(sensor observerable, View tv1){
        this.observerable = observerable;
        this.tv = tv1;
    }
    @Override
    public void onDataReceived() {


    }
}
