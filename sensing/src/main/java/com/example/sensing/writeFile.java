package com.example.sensing;

import java.io.File;
import java.io.FileOutputStream;

public class writeFile {
    FileOutputStream fop = null;
    File file;
    String content = "123123";

//  try {
//        File sdcard = Environment.getExternalStorageDirectory();
//        file = new File(sdcard, context.getResources().getString(R.string.app_name)+"Log.txt"); //輸出檔案位置
//        Log.i("Write File:", file + "");
//        fop = new FileOutputStream(file);
//
//        if (!file.exists()) { // 如果檔案不存在，建立檔案
//            file.createNewFile();
//        }
//
//        byte[] contentInBytes = content.getBytes();// 取的字串內容bytes
//
//        fop.write(contentInBytes); //輸出
//        fop.flush();
//        fop.close();
//
//        Toast.makeText(context, "輸出完成", Toast.LENGTH_LONG).show();
//
//    } catch (IOException e) {
//        Log.i("Write E:", e + "");
//        e.printStackTrace();
//    } finally {
//        try {
//            if (fop != null) {
//                fop.close();
//            }
//        } catch (IOException e) {
//            Log.i("Write IOException", e + "");
//            e.printStackTrace();
//        }
//    }
//}
}
