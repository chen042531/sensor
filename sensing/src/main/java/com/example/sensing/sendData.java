package com.example.sensing;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.util.Log;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.util.Properties;

import static android.content.Context.SENSOR_SERVICE;

public class sendData extends AsyncTask<Void, String, Integer> {
    private Session mSession;

    private String logPath;
    public Context mContext;
    public sendData(Context mContext) {
        this.mContext = mContext;

    }
    public boolean connectSFTPServer(){
        try {
            JSch jsch = new JSch();
            mSession = null;
            Log.d("hostohst", "140.113.216.51");
            mSession = jsch.getSession("yicchen", "140.113.216.51", 22); // port 22
            mSession.setPassword("chen66261034");
            Properties properties = new Properties();
            properties.setProperty("StrictHostKeyChecking", "no");
            mSession.setConfig(properties);
            mSession.connect();
        } catch (JSchException e) {
            e.printStackTrace();
            Log.d("connectSFTPServer", "140.113.216.51");
            return false;
        }
        return true;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        if(connectSFTPServer()){
            Log.d("connectSFTPServer", "yes");
            logPath = new String("/data/data/" + mContext.getPackageName()) + "/logs/";
            Log.d("connectSFTPServer", logPath);
        }
        else{
            Log.d("connectSFTPServer", "no");
        }
        return null;
    }
}
