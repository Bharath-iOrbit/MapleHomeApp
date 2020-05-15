package com.assignment.maplehomeapp.model;

public class StimulationPending {
    String stimulationID;
    String date;
    String duration;
    String current;
    String cycles;
    String pulse;
    String electrodeCnf;
    String status;
    String completed;

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


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
}
