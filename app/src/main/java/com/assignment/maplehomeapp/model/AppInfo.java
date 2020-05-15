package com.assignment.maplehomeapp.model;

import com.google.gson.annotations.SerializedName;

public class AppInfo {

    @SerializedName("AppVersion")
    private String AppVersion;

    @SerializedName("AppInstanceName")
    private String AppInstanceName;

    @SerializedName("SystemNumber")
    private String SystemNumber;

    @SerializedName("AppUUID")
    private String AppUUID;

    @SerializedName("AppOSVersion")
    private String AppOSVersion;

    public String getAppVersion ()
    {
        return AppVersion;
    }

    public void setAppVersion (String AppVersion)
    {
        this.AppVersion = AppVersion;
    }

    public String getAppInstanceName ()
    {
        return AppInstanceName;
    }

    public void setAppInstanceName (String AppInstanceName)
    {
        this.AppInstanceName = AppInstanceName;
    }

    public String getSystemNumber ()
    {
        return SystemNumber;
    }

    public void setSystemNumber (String SystemNumber)
    {
        this.SystemNumber = SystemNumber;
    }

    public String getAppUUID ()
    {
        return AppUUID;
    }

    public void setAppUUID (String AppUUID)
    {
        this.AppUUID = AppUUID;
    }

    public String getAppOSVersion ()
    {
        return AppOSVersion;
    }

    public void setAppOSVersion (String AppOSVersion)
    {
        this.AppOSVersion = AppOSVersion;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [AppVersion = "+AppVersion+", AppInstanceName = "+AppInstanceName+", SystemNumber = "+SystemNumber+", AppUUID = "+AppUUID+", AppOSVersion = "+AppOSVersion+"]";
    }
}
