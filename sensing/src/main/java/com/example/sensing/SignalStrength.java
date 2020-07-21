package com.example.sensing;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.content.ContextCompat;
import android.telephony.CellInfo;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;
//
//import edu.nctu.wirelab.sensinggo.File.FileMaker;
//import edu.nctu.wirelab.sensinggo.File.JsonParser;

/**
 * 1. Judge if the signal strength comes from wcdma, or LTE
 * 2. Record the cellid, the holding time, and handover
 */
public class SignalStrength extends PhoneStateListener {
    private long curTestTime = 0, preTestTime = 0;
    private List<CellInfo> cellInfoList;
    private WeakReference<Context> serviceContext;

    //CellInfoType (LTE or Wcdma)
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

    private TelephonyManager teleManager;

    public SignalStrength(Context context) {
        serviceContext = new WeakReference<>(context);
    }
    //-------------------
//    private JsonParser JsonParser = null;
//
//    public void setJsonParser(JsonParser json) {
//        JsonParser = json;
//    }

    //-------------------
    public static void calculateAvg() {
        if (passCellNum != 0) {
            avgCellResidenceTime = ttlCellResidenceTime / passCellNum;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onSignalStrengthsChanged(final android.telephony.SignalStrength signalstrength) {

        curTestTime = System.currentTimeMillis();
        //Log.d(TagName, "curTestTime:" + curTestTime);
        if (ContextCompat.checkSelfPermission(serviceContext.get(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            cellInfoList = teleManager.getAllCellInfo();
        }

        if (cellInfoList != null) {
            for (CellInfo cellInfo : cellInfoList) {
                if (cellInfo.isRegistered()) {
                    /**
                     *  Make sure the cellInfo is from 3g or 4g phone
                     *  We do not consider the 2g case
                     */
                    if (cellInfo instanceof CellInfoLte) {
                        String oneCellInfo = cellInfo.toString();
                        String[] cellInfoParts = oneCellInfo.split(" ");
                        Log.d("cellInfoParts", ": " + cellInfoParts);
                        for (String str : cellInfoParts) {
                            String[] part = str.split("=");
                            if (part.length != 0 && part[0].compareTo("rsrq") == 0) {
                                lteCellRSRQ = Integer.valueOf(part[1]);
                            }
                        }
                        Log.d("012418: ", "LTE RSRQ " + lteCellRSRQ);



                        //Log.d(TagName, "LTE cellinfo");
                        cellInfoType = "LTE";

                        /* cast to CellInfoLte and call all the CellInfoLte methods you need */
                        // gets signal strength as dBm
                        lteCellRSRP = ((CellInfoLte) cellInfo).getCellSignalStrength().getDbm();

                        // Gets the LTE cell identity: (returns 28-bit Cell Identity, Integer.MAX_VALUE if unknown)
                        lteCellID = ((CellInfoLte) cellInfo).getCellIdentity().getCi();
                        Log.i("ffffffffffffffff", String.valueOf(lteCellID));
                        // Gets the LTE MCC: (returns 3-digit Mobile Country Code, 0..999, Integer.MAX_VALUE if unknown)
                        lteCellMCC = ((CellInfoLte) cellInfo).getCellIdentity().getMcc();

                        // Gets theLTE MNC: (returns 2 or 3-digit Mobile Network Code, 0..999, Integer.MAX_VALUE if unknown)
                        lteCellMNC = ((CellInfoLte) cellInfo).getCellIdentity().getMnc();

                        // Gets the LTE PCI: (returns Physical Cell Id 0..503, Integer.MAX_VALUE if unknown)
                        lteCellPCI = ((CellInfoLte) cellInfo).getCellIdentity().getPci();

                        // Gets the LTE TAC: (returns 16-bit Tracking Area Code, Integer.MAX_VALUE if unknown)
                        lteCellTAC = ((CellInfoLte) cellInfo).getCellIdentity().getTac();

                        /*Log.d(TagName, "SSL(CellInfoLte) RSRP:" + SIONAtCellRSRP +
                                " cellID:" + lteCellID +
                                " cellMcc:" + lteCellMCC +
                                " cellMnc:" + lteCellMNC +
                                " cellPci:" + lteCellPCI +
                                " cellTac:" + lteCellTAC);*/
                    } else if (cellInfo instanceof CellInfoWcdma) {
                        cellInfoType = "Wcdma";

                        wcdmaAtCellSignalStrength = ((CellInfoWcdma) cellInfo).getCellSignalStrength().getDbm();

                        wcdmaAtCellID = ((CellInfoWcdma) cellInfo).getCellIdentity().getCid();

                        wcdmaAtCellMCC = ((CellInfoWcdma) cellInfo).getCellIdentity().getMcc();

                        wcdmaAtCellMNC = ((CellInfoWcdma) cellInfo).getCellIdentity().getMnc();

                        wcdmaAtCellPsc = ((CellInfoWcdma) cellInfo).getCellIdentity().getPsc();

                        wcdmaAtCellLac = ((CellInfoWcdma) cellInfo).getCellIdentity().getLac();


                    } else {
                        // others, e.g. 2g
                        // it's not used in most of the country
                    }
                    cellTimeInterval = curTestTime - preTestTime;
                    preTestTime = curTestTime;

                    if (cellInfoType.equals("LTE") && lteCellID!=nowCellID ) {
                        handoverOccur();
                    } else if (cellInfoType.equals("Wcdma") && wcdmaAtCellID!=nowCellID) {
                        handoverOccur();
                    }
                }

            }
        }


    }

    private void handoverOccur() {
        //Log.d(TagName, "Handover");
        PreAtCellID = nowCellID;
        if (cellInfoType.equals("LTE") && lteCellID!=nowCellID) {
            nowCellID = lteCellID;
        } else if (cellInfoType.equals("Wcdma") && wcdmaAtCellID!=nowCellID) {
            nowCellID = wcdmaAtCellID;
        }

        passCellNum = passCellNum + 1;
        if (stayCellAt == 0) {
            stayCellAt = curTestTime;
        } else {
            cellHoldTime = curTestTime - stayCellAt;
            ttlCellResidenceTime = ttlCellResidenceTime + cellHoldTime;
            stayCellAt = curTestTime;

        }
        //Writing file in new thread 2018/10/07
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                FileMaker.write(JsonParser.HandoverInfoToJson(serviceContext.get()));
//            }
//        }).start();
    }

    private void initBfRun() {
        preTestTime = System.currentTimeMillis();

        lteCellID = 0;
        lteCellMCC = 0;
        lteCellMNC = 0;
        lteCellPCI = 0;
        lteCellTAC = 0;
        lteCellRSRP = 0;
        lteCellRSSI = 0;
        lteCellRSSNR = 0;
        cellTimeInterval = 0;
        cellTimeInterval = 0;

        avgCellHoldTime = 0;
        avgCellResidenceTime = 0;

        cellHoldTime = 0;
        cellInfoType = "LTE";

        wcdmaAtCellID = 0;
        wcdmaAtCellLac = 0;
        wcdmaAtCellMCC = 0;
        wcdmaAtCellMNC = 0;
        wcdmaAtCellPsc = 0;
        wcdmaAtCellSignalStrength = 0;

        nowCellID = 0;
        passCellNum = 0;
        stayCellAt = 0;
        ttlCellResidenceTime = 0;
    }

    public void startService(TelephonyManager teleManger1) {
        initBfRun();
        teleManager = teleManger1;
        teleManager.listen(this, SignalStrength.LISTEN_SIGNAL_STRENGTHS);
    }

    public void autoStartService(TelephonyManager teleManger1) {
        teleManager = teleManger1;
        teleManager.listen(this, SignalStrength.LISTEN_SIGNAL_STRENGTHS);
    }

    public void stopService(TelephonyManager teleManger1) {
        teleManager = teleManger1;
        teleManager.listen(this, SignalStrength.LISTEN_NONE);
    }
}

