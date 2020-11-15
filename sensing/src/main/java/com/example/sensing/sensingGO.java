package com.example.sensing;

import android.content.Context;
import android.content.Intent;

import back.background;


public class sensingGO {
    public Context mContext;
    public sensingGO(Context mContext, String type) {

        this.mContext = mContext;
        mContext.startService(new Intent(mContext,background.class));
    }
//    public type(String type) {
//        this.mContext = mContext;
//    }
//    收集資料


//    寫入資料


//    上傳資料
}
