package com.example.sensing.FileMaker;

import android.content.Context;
import android.util.Log;

import com.example.sensing.Data.SGData;

import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;

public class writeFile {
    FileOutputStream fop = null;
    File file;
    String content = "123123";
    public Context mContext;
    private String UserID = "user";
    private static FileWriter fileWriter;
//    String filePath = "/data/com.example.sensor/logs/fileName_test";
//    public void writeFile(String fileName,String writestr)
    public void setUserID(String userID){
        this.UserID = userID;
    }
    public writeFile(Context mContext) throws IOException {
        this.mContext = mContext;
//        fileWriter = new FileWriter(UserID+".json",true);
    }
    public boolean write(SGData sgData) throws IOException {

        try{
//            FileWriter fileWriter = new FileWriter("ff", true);
            FileOutputStream fout = new FileOutputStream(UserID+".json", true);
            try {
                Log.i("SGData",sgData.getSGData().toString());
                String sgDataString = sgData.getSGData().toString()+"\n";
                byte [] bytes = sgDataString.getBytes();
                fout.write(bytes);
                //            fout.close();
                return true;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            Log.d("writefile", "writefile");
//            fileWriter.write(sgData.getSGData().toString());
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            Log.d("writefile", "gg");
            return false;
        }

    }


}
