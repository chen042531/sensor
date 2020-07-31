package com.example.sensing;

import android.content.Context;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import static android.content.Context.SENSOR_SERVICE;

public class WiFiInfo {
    WifiManager wifi;
    public Context mContext;
    private static ConnectivityManager connManager;
    private static NetworkInfo mWifi;
    public static String servingBSSID = "null",
            servingChan = "null",
            servingFreq = "null",
            servingIP = "null",
            servingLevel = "null",
            servingMAC = "null",
            servingSSID = "null",
            servingSpeed = "null";

    public static boolean wifiState = false;

    public static String allWiFiInfo;
    public static String servingWifiInfo;
    public WiFiInfo(Context mContext) {
        this.mContext = mContext;
        this.wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
    }
    public void getWiFiInfo() {
        WifiInfo wifiInfo = wifi.getConnectionInfo();
        servingSSID = wifiInfo.getSSID(); //wifi id
        servingBSSID = wifiInfo.getBSSID(); // wifi mac ip
        Log.i("wifi",servingSSID+"  ,  "+servingBSSID);
    }



}
