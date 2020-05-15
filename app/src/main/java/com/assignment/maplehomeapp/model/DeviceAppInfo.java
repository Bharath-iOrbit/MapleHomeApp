package com.assignment.maplehomeapp.model;

import com.google.gson.annotations.SerializedName;

public class DeviceAppInfo {
    @SerializedName("DeviceConnected")
    private String DeviceConnected;

    @SerializedName("DeviceInfo")
    private com.assignment.maplehomeapp.model.DeviceInfo DeviceInfo;

    @SerializedName("PatientUUID")
    private String PatientUUID;

    @SerializedName("AppInfo")
    private com.assignment.maplehomeapp.model.AppInfo AppInfo;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("message")
    String message = "";

    public String getDeviceConnected ()
    {
        return DeviceConnected;
    }

    public void setDeviceConnected (String DeviceConnected)
    {
        this.DeviceConnected = DeviceConnected;
    }

    public DeviceInfo getDeviceInfo ()
    {
        return DeviceInfo;
    }

    public void setDeviceInfo (DeviceInfo DeviceInfo)
    {
        this.DeviceInfo = DeviceInfo;
    }

    public String getPatientUUID ()
    {
        return PatientUUID;
    }

    public void setPatientUUID (String PatientUUID)
    {
        this.PatientUUID = PatientUUID;
    }

    public AppInfo getAppInfo ()
    {
        return AppInfo;
    }

    public void setAppInfo (AppInfo AppInfo)
    {
        this.AppInfo = AppInfo;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [DeviceConnected = "+DeviceConnected+", DeviceInfo = "+DeviceInfo+", PatientUUID = "+PatientUUID+", AppInfo = "+AppInfo+"]";
    }
}
