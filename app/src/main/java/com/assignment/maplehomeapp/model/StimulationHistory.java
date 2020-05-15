package com.assignment.maplehomeapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@Entity
public class StimulationHistory {


//    @PrimaryKey(autoGenerate = true)
//    public int StmulationHistoryID;

    @SerializedName("prescriptionid")
    @ColumnInfo(name ="prescriptionid")
    String prescriptionid;

    @SerializedName("StimulationUUID")
    @ColumnInfo(name ="StimulationUUID")
    @PrimaryKey(autoGenerate = true)
    int StimulationUUID;

    @SerializedName("PracticeUUID")
    @ColumnInfo(name ="PracticeUUID")
    String PracticeUUID;

    @SerializedName("PatientUUID")
    @ColumnInfo(name ="PatientUUID")
    String PatientUUID;


    @SerializedName("DevUUID")
    @ColumnInfo(name ="DevUUID")
    String DevUUID;

    @SerializedName("AppUUID")
    @ColumnInfo(name ="AppUUID")
    String AppUUID;

    @SerializedName("Duration")
    @ColumnInfo(name ="Duration")
    String Duration;

    @SerializedName("PhysicianUUID")
    @ColumnInfo(name ="PhysicianUUID")
    String PhysicianUUID;

    @SerializedName("VisitId")
    @ColumnInfo(name ="VisitId")
    String VisitId;

    @SerializedName("StimulationType")
    @ColumnInfo(name ="StimulationType")
    String StimulationType;

    @SerializedName("StimDuration")
    @ColumnInfo(name ="StimDuration")
    String StimDuration;

    @SerializedName("DateOfStimulation")
    @ColumnInfo(name ="DateOfStimulation")
    String DateOfStimulation;

    @SerializedName("Cycle_Executed")
    @ColumnInfo(name ="Cycle_Executed")
    String Cycle_Executed;

    @SerializedName("SimCurrentSet_")
    @ColumnInfo(name ="SimCurrentSet_")
    String SimCurrentSet;

    @SerializedName("SimCurrentGenerated")
    @ColumnInfo(name ="SimCurrentGenerated")
    String SimCurrentGenerated;

    @SerializedName("Status")
    @ColumnInfo(name ="Status")
    String Status;


    @SerializedName("SessionId")
    @ColumnInfo(name = "SessionId")
    String sessionID;


    @ColumnInfo(name ="isUpdated")
    boolean isUpdated;

    public String getTotalCycleCount() {
        return totalCycleCount;
    }

    public void setTotalCycleCount(String totalCycleCount) {
        this.totalCycleCount = totalCycleCount;
    }

    @ColumnInfo(name ="totalCycleCount")
    String totalCycleCount;


    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @SerializedName("message")
    String response;
  



    public StimulationHistory() {
    }






//    public int getStmulationHistoryID() {
//        return StmulationHistoryID;
//    }
//
//    public void setStmulationHistoryID(int stmulationHistoryID) {
//        StmulationHistoryID = stmulationHistoryID;
//    }

    public String getPrescriptionid() {
        return prescriptionid;
    }

    public void setPrescriptionid(String prescriptionid) {
        this.prescriptionid = prescriptionid;
    }

    public int getStimulationUUID() {
        return StimulationUUID;
    }

    public void setStimulationUUID(int stimulationUUID) {
        StimulationUUID = stimulationUUID;
    }

    public String getPracticeUUID() {
        return PracticeUUID;
    }

    public void setPracticeUUID(String practiceUUID) {
        PracticeUUID = practiceUUID;
    }

    public String getPatientUUID() {
        return PatientUUID;
    }

    public void setPatientUUID(String patientUUID) {
        PatientUUID = patientUUID;
    }

    public String getDevUUID() {
        return DevUUID;
    }

    public void setDevUUID(String devUUID) {
        DevUUID = devUUID;
    }

    public String getAppUUID() {
        return AppUUID;
    }

    public void setAppUUID(String appUUID) {
        AppUUID = appUUID;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getPhysicianUUID() {
        return PhysicianUUID;
    }

    public void setPhysicianUUID(String physicianUUID) {
        PhysicianUUID = physicianUUID;
    }

    public String getVisitId() {
        return VisitId;
    }

    public void setVisitId(String visitId) {
        VisitId = visitId;
    }

    public String getStimulationType() {
        return StimulationType;
    }

    public void setStimulationType(String stimulationType) {
        StimulationType = stimulationType;
    }

    public String getStimDuration() {
        return StimDuration;
    }

    public void setStimDuration(String stimDuration) {
        StimDuration = stimDuration;
    }

    public String getDateOfStimulation() {
        return DateOfStimulation;
    }

    public void setDateOfStimulation(String dateOfStimulation) {
        DateOfStimulation = dateOfStimulation;
    }

    public String getCycle_Executed() {
        return Cycle_Executed;
    }

    public void setCycle_Executed(String cycle_Executed) {
        Cycle_Executed = cycle_Executed;
    }

    public String getSimCurrentSet() {
        return SimCurrentSet;
    }

    public void setSimCurrentSet(String simCurrentSet) {
        SimCurrentSet = simCurrentSet;
    }

    public String getSimCurrentGenerated() {
        return SimCurrentGenerated;
    }

    public void setSimCurrentGenerated(String simCurrentGenerated) {
        SimCurrentGenerated = simCurrentGenerated;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public boolean getIsUpdated() {
        return isUpdated;
    }

    public void setIsUpdated(boolean isUpdated) {
        this.isUpdated = isUpdated;
    }
}
