package com.example.sensing.Measurement;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.sensing.Data.DataListenerInterface;

public class LocationInfo implements GpsStatus.Listener,LocationListener {

    private final Context mContext;
    private static LocationManager locationManager;
    private LocationListener locationListener;
    //G: GPS, N: Network, S: Satellite
    public static android.location.Location userlocationG = null, userlocationN = null;
    public static String speedG = null,speedN = null;
    public static Long updateTimeG=0L,updateTimeN=0L,updateTimeS;
    public static String updateTimeStampG ="unknown",updateTimeStampN ="unknown";
    public static GpsStatus gpsStatus = null;
    public DataListenerInterface dataListenerInterface;


    public LocationInfo(Context mContext) {
        this.mContext = mContext;
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location.getProvider().equals(LocationManager.GPS_PROVIDER)){
            if (userlocationG == null) {
                userlocationG = new android.location.Location(location);
            }
            else {
                userlocationG.set(location);
            }
            updateTimeG = System.currentTimeMillis();

            // The time unit is year to millisecond
//            updateTimeStampG = RunIntentService.displayTimeMilli();
        }

        if(location.getProvider().equals(LocationManager.NETWORK_PROVIDER)){
            if (userlocationN == null) {
                userlocationN = new android.location.Location(location);
            }
            else {
                userlocationN.set(location);
            }
            updateTimeN = System.currentTimeMillis();
//            updateTimeStampN = RunIntentService.displayTimeMilli();
        }

        if (locationListener != null) {
            locationListener.onLocationChanged( location );
        }
        Log.i("gps_onLocationChanged", String.valueOf(userlocationG));
        Log.i("gps_onLocationChanged", String.valueOf(userlocationN));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        if (locationListener != null) {
            //Writing file in new thread 2018/10/07
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    FileMaker.write(JsonParser.ErrorInfoToJson("LOG","onStatusChanged():"+provider + " status: " + status));
//                }
//            }).start();
            locationListener.onStatusChanged( provider, status, extras);
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
        if (locationListener != null) {

            locationListener.onProviderEnabled(provider);
            Log.i("gps", String.valueOf(provider));
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        if (locationListener != null) {

            locationListener.onProviderEnabled(provider);

        }
    }

    @Override
    public void onGpsStatusChanged(int event) {
        switch (event) {
            case GpsStatus.GPS_EVENT_STARTED:
                break;
            case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                break;
            case GpsStatus.GPS_EVENT_FIRST_FIX:
                break;
            case GpsStatus.GPS_EVENT_STOPPED:
                break;
        }
    }
    public boolean isOpenGps() {
        // Get the location by GPS
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // Get the location by WLAN or mobile network. It's usually used at the place which is more hidden.
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        return gps || network;
    }
    public static int checkGPSalive() {
        //satelliteUpdate();
        long now = System.currentTimeMillis();
        if(updateTimeS!=null && (now-updateTimeS)>10000) {
            return 1;
        }
        if(updateTimeG!=null && (now-updateTimeG)>10000) {
            return 2;
        }
        if(updateTimeN!=null && (now-updateTimeN)>30000) {
            return 3;
        }
        return 0;
    }
    public void startGPS(Context context) {
//        dataListener = dListener;
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            this.getGPS(context);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, LocationInfo.this, Looper.getMainLooper());
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, LocationInfo.this, Looper.getMainLooper());
        }

    }


    public Location getGPS(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            userlocationG = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            userlocationN = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Log.i("gps", "String.valueOf(userlocationG)");
            Log.i("gps", String.valueOf(userlocationG));
            Log.i("gps", String.valueOf(userlocationN));

        }
        return userlocationN;
    }
    public void stopGPS() {
        locationManager.removeUpdates(LocationInfo.this);
    }
}