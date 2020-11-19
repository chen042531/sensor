package com.example.sensing.Measurement;

import android.content.Context;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.SENSOR_SERVICE;

public class NetworkState {
    public Context mContext;
    public ConnectivityManager connectivityManager;

    public String Network_type = null;
    public NetworkState(Context mContext) {
        this.mContext = mContext;
        this.connectivityManager = (ConnectivityManager)mContext.getSystemService(CONNECTIVITY_SERVICE);
    }
    public void networkstateTest(){
        Log.d("networkstateTest", String.valueOf(isNetworkAvailable())) ;
    }
//    http://shovachu-coding.blogspot.com/2013/09/androidby-connectivitymanager.html
    private Boolean isNetworkAvailable() {
//        NetworkInfo nwInfo = connectivityManager.getActiveNetworkInfo();
//        nwInfo.getTypeName();
//        Log.d("networkstateTest", String.valueOf( nwInfo.getTypeName())) ;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network nw = connectivityManager.getActiveNetwork();
            if (nw == null) return false;
            NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
            if (actNw != null) {
                if (actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Network_type = "MOBILE";
                    Log.d("networkstateTest", "mobile") ;
                } else if (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Network_type = "WIFI";
                    Log.d("networkstateTest", "wifi") ;
                }  else if (actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)){
                    Network_type = "ETHERNET";
                }
                else{
                    Network_type = null;
                }
                return actNw != null && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH));
            }
            else {
                NetworkInfo nwInfo = connectivityManager.getActiveNetworkInfo();
                if(nwInfo.getTypeName()!=null) {
                    Network_type = nwInfo.getTypeName();
                }
                else{
                    Network_type = "null";
                }
                return nwInfo != null && nwInfo.isConnected();
            }
        }
        return false;
    }

}
