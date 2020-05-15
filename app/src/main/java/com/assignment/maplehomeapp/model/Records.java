package com.assignment.maplehomeapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Records {
    @SerializedName("therapistid")
    @Expose
    private String therapistid;
    @SerializedName("numrecords")
    @Expose
    private String numrecords;

    public String getLastupdatenumber() {
        return lastupdatenumber;
    }

    public void setLastupdatenumber(String lastupdatenumber) {
        this.lastupdatenumber = lastupdatenumber;
    }

    @SerializedName("lastupdatenumber")
    @Expose
    private String lastupdatenumber;

//    @SerializedName("details")
//    @Expose
//    private List<List<Detail>> details = null;

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




    @SerializedName("Created_Date")
    @Expose
    private String createdDate;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("sourceElectrodes")
    @Expose
    private String sourceElectrodes;
    @SerializedName("sinkElectrodes")
    @Expose
    private String sinkElectrodes;
    @SerializedName("Phase_Duration")
    @Expose
    private String phaseDuration;
    @SerializedName("Max_Current")
    @Expose
    private String maxCurrent;
    @SerializedName("Measurement_Iteration")
    @Expose
    private String measurementIteration;
    @SerializedName("Measurement_Type")
    @Expose
    private String measurementType;
    @SerializedName("Instrument_Location")
    @Expose
    private String instrumentLocation;
    @SerializedName("Pulse_Frequency")
    @Expose
    private String pulseFrequency;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Configuration")
    @Expose
    private String configuration;
    @SerializedName("Min_Current")
    @Expose
    private String minCurrent;
    @SerializedName("Visit_Date")
    @Expose
    private String visitDate;
    @SerializedName("Start_Time")
    @Expose
    private String startTime;
    @SerializedName("Duration")
    @Expose
    private String duration;
    @SerializedName("Preset")
    @Expose
    private String preset;
    @SerializedName("Fade_In_Out")
    @Expose
    private String fadeInOut;
    @SerializedName("Hold_Time")
    @Expose
    private String holdTime;
    @SerializedName("Pause_Time")
    @Expose
    private String pauseTime;
    @SerializedName("Cycle_Count")
    @Expose
    private String cycleCount;

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
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

    public String getPhaseDuration() {
        return phaseDuration;
    }

    public void setPhaseDuration(String phaseDuration) {
        this.phaseDuration = phaseDuration;
    }

    public String getMaxCurrent() {
        return maxCurrent;
    }

    public void setMaxCurrent(String maxCurrent) {
        this.maxCurrent = maxCurrent;
    }

    public String getMeasurementIteration() {
        return measurementIteration;
    }

    public void setMeasurementIteration(String measurementIteration) {
        this.measurementIteration = measurementIteration;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(String measurementType) {
        this.measurementType = measurementType;
    }

    public String getInstrumentLocation() {
        return instrumentLocation;
    }

    public void setInstrumentLocation(String instrumentLocation) {
        this.instrumentLocation = instrumentLocation;
    }

    public String getPulseFrequency() {
        return pulseFrequency;
    }

    public void setPulseFrequency(String pulseFrequency) {
        this.pulseFrequency = pulseFrequency;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public String getMinCurrent() {
        return minCurrent;
    }

    public void setMinCurrent(String minCurrent) {
        this.minCurrent = minCurrent;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPreset() {
        return preset;
    }

    public void setPreset(String preset) {
        this.preset = preset;
    }

    public String getFadeInOut() {
        return fadeInOut;
    }

    public void setFadeInOut(String fadeInOut) {
        this.fadeInOut = fadeInOut;
    }

    public String getHoldTime() {
        return holdTime;
    }

    public void setHoldTime(String holdTime) {
        this.holdTime = holdTime;
    }

    public String getPauseTime() {
        return pauseTime;
    }

    public void setPauseTime(String pauseTime) {
        this.pauseTime = pauseTime;
    }

    public String getCycleCount() {
        return cycleCount;
    }

    public void setCycleCount(String cycleCount) {
        this.cycleCount = cycleCount;
    }



//    public List<List<Detail>> getDetails() {
//        return details;
//    }
//
//    public void setDetails(List<List<Detail>> details) {
//        this.details = details;
//    }
}
