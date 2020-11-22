package com.example.sensing.Data;

import android.location.GpsStatus;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.HashMap;

public class SGData {
//    AppicationType
//            ApplicationVersion
//    APPVersion
//            Account
//    SHANSERVER
//            equipmentId
//    Model
//            BatteryLevel
//    BatteryTemp
//            Time
//    TimeStamp
//    TimeZone(GMT)
//    DataVersion
//            TestType
//    Screen state
//    Cpu
//            Ram
//    CallID
//            NetworkType
//    ConnectionState
//            LatG
//    LngG
//            SpeedG
//    LatN
//            LngN
//    SpeedN
//            GPSTimeG
//    GPSTimeStampG
//    Event
//    ACCELEROMETER
//    LIGHT
//    ROTATION
//    PROXIMITY
//    MAGNETIC_FIELD

//    ServingCellInfo/NeighborCellInfo
        //CellInfoType
        //        CellID
        //    CellMCC
        //            CellMNC
        //    CellPCI
        //            CellTAC
        //    RSSI
        //            SINR
        //    RSRQ
        //            RSRP

//    handover
    //    FromCellID
    //        ToCellID
    //    CellResidenceTime
//    ServingWifi/NeighborWiFi
//SSID
//        level
//    Freq
//            Chan
//    cap


//    Event":"AppsInfo","LastTime":174145,"AppsInfo":[{"AppName":"edu.nctu.wirelab.sensinggo","TotalTx":204940383,"TotalRx":79512950,"DeltaTx":336,"DeltaRx":0}],"AllAppsTxBytes(MB)":"2870.36","AllAppsRxBytes(MB)":"24990.77"}
//    PhoneState
    public static String phoneState = "IDLE"; //IDLE, OFFHOOK, RINGING
    public static String callState = "IDLE"; //Callout, ""Callin"", RINGING
    public static String callID = "null";
    public static String startCallTime, endCallTime;

    public static int callNum = 0, callExcessNum = 0;
    public static long callStartAt = 0, callHoldingTime = 0, excessLife = 0;
    public static double avgCallHoldTime = 0, avgExcessLife = 0;
    public static double ttlCallHoldTime = 0, ttlExcessLife = 0;
    public static boolean FirstCallCell = false;

    // phoneInfo
    public static double electricity;
    public static int health = 0;
    public static int icon_small = 0;
    public static int level = 0;
    public static int lastLevel = -1;
    public static double lastElect = -1;
    public static int plugged = 0;
    public static boolean present = false;
    public static int scale = 0;
    public static int status = 0;
    public static String technology = "0";
    public static String tmp;
    public static double temperature = 0.0;
    public static int voltage = 0;
    public static String screen_state = "on";

    public static double ram_uti;

    //    LocationInfo
    public static LocationManager locationManager;
    public LocationListener locationListener;
    //G: GPS, N: Network, S: Satellite
    public static android.location.Location userlocationG = null, userlocationN = null;
    public static String speedG = null,speedN = null;
    public static Long updateTimeG=0L,updateTimeN=0L,updateTimeS;
    public static String updateTimeStampG ="unknown",updateTimeStampN ="unknown";
    public static GpsStatus gpsStatus = null;

    //    cellularinfo
    public static String cellInfoType;

    public static int PreAtCellID;

    //LTE info
    public static int lteCellRSRP, lteCellID, lteCellMCC, lteCellMNC, lteCellPCI, lteCellTAC, lteCellRSSI, lteCellRSRQ, lteCellRSSNR;
    public static long cellTimeInterval;

    //Wcdma info
    public static int wcdmaAtCellPsc, wcdmaAtCellLac, wcdmaAtCellID, wcdmaAtCellMCC, wcdmaAtCellMNC, wcdmaAtCellSignalStrength;

    public static int passCellNum = 0;
    public static int nowCellID = 0;
    public static long stayCellAt = 0, cellHoldTime = 0;
    public static double avgCellResidenceTime = 0, avgCellHoldTime, ttlCellResidenceTime = 0;


    //    sensorInfo
    public static float[] gSensorValues = new float[3]; // triaxial acceleration
    public static float[] magneticValues = new float[3];
    public static int lightValue;
    public static int sensorInterval;
    public static String proximityValue;
    public static float pressureValue,pvalue;
    public float[] rMatrix = new float[9];    //rotation matrix

    // orientation values, [0]: Azimuth, [1]: Pitch, [2]: Roll
    public static float[] orienValue = new float[3];
    // networkstate
    public String Network_type;



    private Class cls = this.getClass();

    public Object getSGData() throws IllegalAccessException, JSONException {
        JSONObject dataInfo = new JSONObject();
        for (Field field : cls.getDeclaredFields()){
            String field_name = field.getName();
            Log.i("SGData", field_name+": "+String.valueOf(field.get(this)));
            dataInfo.put(field_name,field.get(this));
        }
        return dataInfo;
    }
}
