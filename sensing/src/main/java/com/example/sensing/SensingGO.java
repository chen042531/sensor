package com.example.sensing;

import android.content.Context;
import android.content.Intent;

import com.example.sensing.BackgroundRunner.background;


public class SensingGO {
    public Context mContext;
    public SensingGO(Context mContext, String type) {

        this.mContext = mContext;
        mContext.startService(new Intent(mContext,background.class));
    }
//    public type(String type) {
//        this.mContext = mContext;
//    }

}
