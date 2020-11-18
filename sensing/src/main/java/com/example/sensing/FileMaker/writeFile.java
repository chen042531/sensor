package com.example.sensing.FileMaker;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

public class writeFile {
    FileOutputStream fop = null;
    File file;
    String content = "123123";
    public Context mContext;
//    String filePath = "/data/com.example.sensor/logs/fileName_test";
//    public void writeFile(String fileName,String writestr)
    public writeFile(Context mContext) throws IOException {
        this.mContext = mContext;
        try{
//            FileWriter fileWriter = new FileWriter("ff", true);
            FileOutputStream fout = mContext.openFileOutput("example.json", MODE_PRIVATE);
            byte [] bytes = content.getBytes();
            fout.write(bytes);
//            fout.close();
            Log.d("writefile", "writefile");
        }
        catch(Exception e){
            e.printStackTrace();
            Log.d("writefile", "gg");
        }
    }
}
