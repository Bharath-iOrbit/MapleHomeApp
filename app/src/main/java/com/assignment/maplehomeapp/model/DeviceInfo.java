package com.assignment.maplehomeapp.model;

import com.google.gson.annotations.SerializedName;

public class DeviceInfo {

    @SerializedName("ModelNo")
    private String ModelNo;

    @SerializedName("DevUUID")
    private String DevUUID;

    @SerializedName("DevMacID")
    private String DevMacID;

    @SerializedName("FirmwareVersion")
    private String FirmwareVersion;

    public String getModelNo ()
    {
        return ModelNo;
    }

    public void setModelNo (String ModelNo)
    {
        this.ModelNo = ModelNo;
    }

    public String getDevUUID ()
    {
        return DevUUID;
    }

    public void setDevUUID (String DevUUID)
    {
        this.DevUUID = DevUUID;
    }

    public String getDevMacID ()
    {
        return DevMacID;
    }

    public void setDevMacID (String DevMacID)
    {
        this.DevMacID = DevMacID;
    }

    public String getFirmwareVersion ()
    {
        return FirmwareVersion;
    }

    public void setFirmwareVersion (String FirmwareVersion)
    {
        this.FirmwareVersion = FirmwareVersion;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ModelNo = "+ModelNo+", DevUUID = "+DevUUID+", DevMacID = "+DevMacID+", FirmwareVersion = "+FirmwareVersion+"]";
    }
}
