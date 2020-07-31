package com.example.sensing;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

import androidx.core.app.ActivityCompat;

public class CellInfo {

    public
    public void startService(TelephonyManager telephony) {

        if (telephony.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {
            GsmCellLocation location = (GsmCellLocation) telephony.getCellLocation();
            if (location != null) {
//                msg.setText("LAC: " + location.getLac() + " CID: " + location.getCid());
                Log.i("cellinfo","LAC: " + location.getLac() + " CID: " + location.getCid());
            }
        }

    }

}
