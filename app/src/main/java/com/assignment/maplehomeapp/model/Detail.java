package com.assignment.maplehomeapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detail {
    @SerializedName("sourceElectrodes")
    private String sourceElectrodes;

    @SerializedName("Visit_Date")
    private String Visit_Date;

    @SerializedName("Configuration")
    private String Configuration;

    @SerializedName("Hold_Time")
    private String Hold_Time;

    @SerializedName("Pause_Time")
    private String Pause_Time;

    @SerializedName("Preset")
    private String Preset;

    @SerializedName("Min_Current")
    private String Min_Current;

    @SerializedName("Measurement_Type")
    private String Measurement_Type;

    @SerializedName("Title")
    private String Title;

    @SerializedName("Duration")
    private String Duration;

    @SerializedName("sessionid")
    private String sessionid;

    @SerializedName("Phase_Duration")
    private String Phase_Duration;

    @SerializedName("Cycle_Count")
    private String Cycle_Count;


    @SerializedName("Measurement_Iteration")
    private String Measurement_Iteration;

    @SerializedName("Pulse_Frequency")
    private String Pulse_Frequency;

    @SerializedName("Start_Time")
    private String Start_Time;

    @SerializedName("Instrument_Location")
    private String Instrument_Location;

    @SerializedName("sinkElectrodes")
    private String sinkElectrodes;

    @SerializedName("Max_Current")
    private String Max_Current;

    @SerializedName("Fade_In_Out")
    private String Fade_In_Out;

    public String getSourceElectrodes ()
    {
        return sourceElectrodes;
    }

    public void setSourceElectrodes (String sourceElectrodes)
    {
        this.sourceElectrodes = sourceElectrodes;
    }

    public String getVisit_Date ()
    {
        return Visit_Date;
    }

    public void setVisit_Date (String Visit_Date)
    {
        this.Visit_Date = Visit_Date;
    }

    public String getConfiguration ()
    {
        return Configuration;
    }

    public void setConfiguration (String Configuration)
    {
        this.Configuration = Configuration;
    }

    public String getHold_Time ()
    {
        return Hold_Time;
    }

    public void setHold_Time (String Hold_Time)
    {
        this.Hold_Time = Hold_Time;
    }

    public String getPause_Time ()
    {
        return Pause_Time;
    }

    public void setPause_Time (String Pause_Time)
    {
        this.Pause_Time = Pause_Time;
    }

    public String getPreset ()
    {
        return Preset;
    }

    public void setPreset (String Preset)
    {
        this.Preset = Preset;
    }

    public String getMin_Current ()
    {
        return Min_Current;
    }

    public void setMin_Current (String Min_Current)
    {
        this.Min_Current = Min_Current;
    }

    public String getMeasurement_Type ()
    {
        return Measurement_Type;
    }

    public void setMeasurement_Type (String Measurement_Type)
    {
        this.Measurement_Type = Measurement_Type;
    }

    public String getTitle ()
    {
        return Title;
    }

    public void setTitle (String Title)
    {
        this.Title = Title;
    }

    public String getDuration ()
    {
        return Duration;
    }

    public void setDuration (String Duration)
    {
        this.Duration = Duration;
    }

    public String getSessionid ()
    {
        return sessionid;
    }

    public void setSessionid (String sessionid)
    {
        this.sessionid = sessionid;
    }

    public String getPhase_Duration ()
    {
        return Phase_Duration;
    }

    public void setPhase_Duration (String Phase_Duration)
    {
        this.Phase_Duration = Phase_Duration;
    }

    public String getCycle_Count ()
    {
        return Cycle_Count;
    }

    public void setCycle_Count (String Cycle_Count)
    {
        this.Cycle_Count = Cycle_Count;
    }

    public String getMeasurement_Iteration ()
    {
        return Measurement_Iteration;
    }

    public void setMeasurement_Iteration (String Measurement_Iteration)
    {
        this.Measurement_Iteration = Measurement_Iteration;
    }

    public String getPulse_Frequency ()
    {
        return Pulse_Frequency;
    }

    public void setPulse_Frequency (String Pulse_Frequency)
    {
        this.Pulse_Frequency = Pulse_Frequency;
    }

    public String getStart_Time ()
    {
        return Start_Time;
    }

    public void setStart_Time (String Start_Time)
    {
        this.Start_Time = Start_Time;
    }

    public String getInstrument_Location ()
    {
        return Instrument_Location;
    }

    public void setInstrument_Location (String Instrument_Location)
    {
        this.Instrument_Location = Instrument_Location;
    }

    public String getSinkElectrodes ()
    {
        return sinkElectrodes;
    }

    public void setSinkElectrodes (String sinkElectrodes)
    {
        this.sinkElectrodes = sinkElectrodes;
    }

    public String getMax_Current ()
    {
        return Max_Current;
    }

    public void setMax_Current (String Max_Current)
    {
        this.Max_Current = Max_Current;
    }

    public String getFade_In_Out ()
    {
        return Fade_In_Out;
    }

    public void setFade_In_Out (String Fade_In_Out)
    {
        this.Fade_In_Out = Fade_In_Out;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [sourceElectrodes = "+sourceElectrodes+", Visit_Date = "+Visit_Date+", Configuration = "+Configuration+", Hold_Time = "+Hold_Time+", Pause_Time = "+Pause_Time+", Preset = "+Preset+", Min_Current = "+Min_Current+", Measurement_Type = "+Measurement_Type+", Title = "+Title+", Duration = "+Duration+", sessionid = "+sessionid+", Phase_Duration = "+Phase_Duration+", Cycle_Count = "+Cycle_Count+", Measurement_Iteration = "+Measurement_Iteration+", Pulse_Frequency = "+Pulse_Frequency+", Start_Time = "+Start_Time+", Instrument_Location = "+Instrument_Location+", sinkElectrodes = "+sinkElectrodes+", Max_Current = "+Max_Current+", Fade_In_Out = "+Fade_In_Out+"]";
    }
}



