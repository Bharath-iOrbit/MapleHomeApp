package com.assignment.maplehomeapp.utils;

import android.widget.EditText;

import com.assignment.maplehomeapp.connection.UartService;
import com.assignment.maplehomeapp.interfaces.MapleInterface;
import com.assignment.maplehomeapp.model.DetailEntity;
import com.assignment.maplehomeapp.model.StimulationHistory;
import com.assignment.maplehomeapp.model.StimulationSettings;

import java.util.List;

public class CommonDataArea {
    public static UartService uartService;
    public static boolean isDeviceConnected =false;
    public static MapleInterface maple;
    public static int selectedSessionId=0;
    public static List<DetailEntity> mDataset;
    public static Parcelable stimParcel;
    public static StimulationSettings stimSettings;
    public static String blueToothName="";
    public static String sessionID="";

    public  static boolean DEBUG_MODE = false;
    public static boolean isBlueToothConnected=false;
    public static EditText passwdEdt;
    public static EditText userName;
    public static String patientID;
    public static int currSessionID;
    public static String prescriptionID;
    public static int prescriptionStatus;
    public static boolean AUDIO;
    public static StimulationHistory mDatasetHist;
    public static float histPercentage;

    public enum StimState{

        Onset,
        Hold,
        Offset,
        Delay,
        off
    }
    public enum StimStatus{
        Running,
        Stopped,
        Error
    }
    public static StimState state;
    public static StimStatus status;
    public static  int remStimCycles =0;
    public static int curDacVal =0;
    public static float batteryPercent=100;
    public static float measuredStimVolt=10;
    public static float measuredStimCurrent =0.05f;

    public static String deviceMacID;
    public static String deviceFirmwareVer;
    public static String deviceHardwareVer;
    public static String deviceName;
}


