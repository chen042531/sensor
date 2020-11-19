package com.example.sensing.Data;

import android.view.View;

import com.example.sensing.Measurement.SensorInfo;

public class DataListener implements DataListenerInterface {
    SensorInfo observerable;
    View tv;
    public DataListener(SensorInfo observerable){
        this.observerable = observerable;
    }
    public DataListener(SensorInfo observerable, View tv1){
        this.observerable = observerable;
        this.tv = tv1;
    }
    @Override
    public void onDataReceived() {


    }
}
