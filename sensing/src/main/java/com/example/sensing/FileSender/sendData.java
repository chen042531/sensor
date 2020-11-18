package com.example.sensing.FileSender;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.util.Log;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
//import org.apache.http.util.EncodingUtils;
import static android.content.Context.SENSOR_SERVICE;

    public class sendData extends AsyncTask<Void, String, Integer> {
        private Session mSession;

    private String logPath;
    public Context mContext;
    String filePath = "/data/data/com.example.sensor/files";
    public sendData(Context mContext) {
        this.mContext = mContext;

    }
    public boolean connectSFTPServer(){
        try{
            FileInputStream fin = mContext.openFileInput("example.json");
            int length = fin.available();
            byte [] buffer = new byte[length];
            fin.read(buffer);
            String str = new String(buffer, StandardCharsets.UTF_8);
            Log.d("connectSFTPServer", str);
//            res = EncodingUtils.getString(buffer, "UTF-8");
            fin.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
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
            ChannelSftp sftpChannel = (ChannelSftp) mSession.openChannel("sftp");
            sftpChannel.connect();
            sftpChannel.put("/data/data/com.example.sensor/files/example.json","/home/yicchen/Desktop/example.json");
        } catch (JSchException | SftpException e) {
            e.printStackTrace();

            Log.d("connectSFTPServer", String.valueOf(e));
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
