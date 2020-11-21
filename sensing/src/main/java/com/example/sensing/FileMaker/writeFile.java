package com.example.sensing.FileMaker;

import android.content.Context;
import android.util.Log;

import com.example.sensing.Data.SGData;

import org.json.JSONException;
import org.json.JSONObject;

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
    public writeFile(Context mContext, SGData sgData) throws IOException {
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
    public String servingCellInfoToJson(Context context) {
        if (RunIntentService.neighborCellList.size() == 0) {
            return "";
        }

        JSONObject obj = new JSONObject();

        try {
            commonData(obj, context);
            gpsData(obj);
            obj.put("Event", "ServingCell"); // new event!
            //------------------------results
            JSONObject cellInfo = new JSONObject();
            if (SignalStrength.cellInfoType.equals("LTE")) {
                //Log.d("CellInfo", "[LTE]"+RunIntentService.AtCellID+":"+RunIntentService.AtCellPCI+":"+RunIntentService.AtCellRSRP);

                cellInfo.put("CellInfoType", RunIntentService.cellInfoType);
                cellInfo.put("CellID", RunIntentService.lteCellID);
                cellInfo.put("CellMCC", RunIntentService.lteCellMCC); //country code
                cellInfo.put("CellMNC", RunIntentService.lteCellMNC);

                cellInfo.put("CellPCI", RunIntentService.lteCellPCI); //physical cell id
                cellInfo.put("CellTAC", RunIntentService.lteCellTAC); //

                cellInfo.put("RSSI", SignalStrength.lteCellRSSI);
                cellInfo.put("SINR", "null");
                cellInfo.put("RSRQ", RunIntentService.lteCellRSRQ);
                cellInfo.put("RSRP", RunIntentService.lteCellRSRP);

            } else if (SignalStrength.cellInfoType.equals("Wcdma")) {
                //Log.d("CellInfo", "[WCDMA]"+RunIntentService.WcdmaAtCellID+":"+RunIntentService.WcdmaAtCellSignalStrength);

                cellInfo.put("CellInfoType", RunIntentService.cellInfoType);
                cellInfo.put("CellID", RunIntentService.wcdmaAtCellID);
                cellInfo.put("CellMCC", RunIntentService.wcdmaAtCellMCC);
                cellInfo.put("CellMNC", RunIntentService.wcdmaAtCellMNC);
                cellInfo.put("CellPSC", RunIntentService.wcdmaAtCellPsc);
                cellInfo.put("CellLAC", RunIntentService.wcdmaAtCellLac);
                cellInfo.put("SignalStrength", RunIntentService.wcdmaAtCellSignalStrength);
            }

            obj.put("ServingCellInfo", cellInfo);
            servingCellInfo = obj.toString(2);
            return obj.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
