package com.assignment.maplehomeapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class DetailEntity {

    public int getStmulationSessionID() {
        return StmulationSessionID;
    }

    public void setStmulationSessionID(int stmulationSessionID) {
        StmulationSessionID = stmulationSessionID;
    }

    @PrimaryKey(autoGenerate = true)
    public int StmulationSessionID;
    @ColumnInfo(name ="Created_Date")
    String Created_Date;
    @ColumnInfo(name ="sourceElectrodes")
    String sourceElectrodes;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @ColumnInfo(name ="status")
    String status;

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    @ColumnInfo(name ="patientid")
    String patientID;

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    @ColumnInfo(name ="sessionid")
    String sessionid;

    @ColumnInfo(name ="sinkElectrodes")
    String sinkElectrodes;
    @ColumnInfo(name ="Phase_Duration")
    String Phase_Duration;
    @ColumnInfo(name ="Max_Current")
    String Max_Current;
    @ColumnInfo(name ="Measurement_Iteration")
    String Measurement_Iteration;
    @ColumnInfo(name ="Measurement_Type")
    String Measurement_Type;
    @ColumnInfo(name ="Instrument_Location")
    String Instrument_Location;
    @ColumnInfo(name ="Pulse_Frequency")
    String Pulse_Frequency;
    @ColumnInfo(name ="Title")
    String Title;
    @ColumnInfo(name ="Configuration")
    String Configuration;
    @ColumnInfo(name ="Min_Current")
    String Min_Current;
    @ColumnInfo(name ="Visit_Date")
    String Visit_Date;
    @ColumnInfo(name ="Start_Time")
    String Start_Time;
    @ColumnInfo(name ="Duration")
    String Duration;
    @ColumnInfo(name ="Preset")
    String Preset;
    @ColumnInfo(name ="Fade_In_Out")
    String Fade_In_Out;
    @ColumnInfo(name ="Hold_Time")
    String Hold_Time;
    @ColumnInfo(name ="Pause_Time")
    String Pause_Time;
    @ColumnInfo(name ="Cycle_Count")
    String Cycle_Count;

    public String getPrescriptionid() {
        return prescriptionid;
    }

    public void setPrescriptionid(String prescriptionid) {
        this.prescriptionid = prescriptionid;
    }

    @ColumnInfo(name ="prescriptionid")
    String prescriptionid;

    @SerializedName("therapistid")
    @Expose
    private String therapistid;
    @SerializedName("numrecords")
    @Expose
    private String numrecords;

    public String getCreated_Date() {
        return Created_Date;
    }

    public void setCreated_Date(String created_Date) {
        Created_Date = created_Date;
    }

    public String getSourceElectrodes() {
        return sourceElectrodes;
    }

    public void setSourceElectrodes(String sourceElectrodes) {
        this.sourceElectrodes = sourceElectrodes;
    }

    public String getSinkElectrodes() {
        return sinkElectrodes;
    }

    public void setSinkElectrodes(String sinkElectrodes) {
        this.sinkElectrodes = sinkElectrodes;
    }

    public String getPhase_Duration() {
        return Phase_Duration;
    }

    public void setPhase_Duration(String phase_Duration) {
        Phase_Duration = phase_Duration;
    }

    public String getMax_Current() {
        return Max_Current;
    }

    public void setMax_Current(String max_Current) {
        Max_Current = max_Current;
    }

    public String getMeasurement_Iteration() {
        return Measurement_Iteration;
    }

    public void setMeasurement_Iteration(String measurement_Iteration) {
        Measurement_Iteration = measurement_Iteration;
    }

    public String getMeasurement_Type() {
        return Measurement_Type;
    }

    public void setMeasurement_Type(String measurement_Type) {
        Measurement_Type = measurement_Type;
    }

    public String getInstrument_Location() {
        return Instrument_Location;
    }

    public void setInstrument_Location(String instrument_Location) {
        Instrument_Location = instrument_Location;
    }

    public String getPulse_Frequency() {
        return Pulse_Frequency;
    }

    public void setPulse_Frequency(String pulse_Frequency) {
        Pulse_Frequency = pulse_Frequency;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getConfiguration() {
        return Configuration;
    }

    public void setConfiguration(String configuration) {
        Configuration = configuration;
    }

    public String getMin_Current() {
        return Min_Current;
    }

    public void setMin_Current(String min_Current) {
        Min_Current = min_Current;
    }

    public String getVisit_Date() {
        return Visit_Date;
    }

    public void setVisit_Date(String visit_Date) {
        Visit_Date = visit_Date;
    }

    public String getStart_Time() {
        return Start_Time;
    }

    public void setStart_Time(String start_Time) {
        Start_Time = start_Time;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getPreset() {
        return Preset;
    }

    public void setPreset(String preset) {
        Preset = preset;
    }

    public String getFade_In_Out() {
        return Fade_In_Out;
    }

    public void setFade_In_Out(String fade_In_Out) {
        Fade_In_Out = fade_In_Out;
    }

    public String getHold_Time() {
        return Hold_Time;
    }

    public void setHold_Time(String hold_Time) {
        Hold_Time = hold_Time;
    }

    public String getPause_Time() {
        return Pause_Time;
    }

    public void setPause_Time(String pause_Time) {
        Pause_Time = pause_Time;
    }

    public String getCycle_Count() {
        return Cycle_Count;
    }

    public void setCycle_Count(String cycle_Count) {
        Cycle_Count = cycle_Count;
    }

    public String getTherapistid() {
        return therapistid;
    }

    public void setTherapistid(String therapistid) {
        this.therapistid = therapistid;
    }

    public String getNumrecords() {
        return numrecords;
    }

    public void setNumrecords(String numrecords) {
        this.numrecords = numrecords;
    }
}
