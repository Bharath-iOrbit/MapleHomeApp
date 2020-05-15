package com.assignment.maplehomeapp.remote;

import com.google.gson.annotations.SerializedName;

public class StimulationResponse {
@SerializedName("stimulationID")
    String stimulationID;
    @SerializedName("date")

    String date;
    @SerializedName("duration")

    String duration;
    @SerializedName("current")

    String current;
    @SerializedName("cycles")

    String cycles;
    @SerializedName("pulse")

    String pulse;
    @SerializedName("electrodeCnf")

    String electrodeCnf;
    @SerializedName("status")

    String status;
    @SerializedName("completed")

    String completed;

    public String getStimulationID() {
        return stimulationID;
    }

    public void setStimulationID(String stimulationID) {
        this.stimulationID = stimulationID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getCycles() {
        return cycles;
    }

    public void setCycles(String cycles) {
        this.cycles = cycles;
    }

    public String getPulse() {
        return pulse;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    public String getElectrodeCnf() {
        return electrodeCnf;
    }

    public void setElectrodeCnf(String electrodeCnf) {
        this.electrodeCnf = electrodeCnf;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }
}
