package com.assignment.maplehomeapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Prescriptions {

    public int getPrescriptionID() {
        return prescriptionID;
    }

    public void setPrescriptionID(int prescriptionID) {
        this.prescriptionID = prescriptionID;
    }

    public String getPrescriptionuuid() {
        return prescriptionuuid;
    }

    public void setPrescriptionuuid(String prescriptionuuid) {
        this.prescriptionuuid = prescriptionuuid;
    }

    @PrimaryKey(autoGenerate = true)
    public int prescriptionID;
    @ColumnInfo(name ="therapistid")
    private String therapistid;
    @ColumnInfo(name ="numrecords")
    private String numrecords;
    @ColumnInfo(name ="lastupdatenumber")
    private String lastupdatenumber;
    @ColumnInfo(name ="prescriptionuuid")
    private String prescriptionuuid;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @ColumnInfo(name ="status")
    private int status;

    public String getPrescriptionstatus() {
        return prescriptionstatus;
    }

    public void setPrescriptionstatus(String prescriptionstatus) {
        this.prescriptionstatus = prescriptionstatus;
    }

    @ColumnInfo(name ="prescriptionstatus")
    private String prescriptionstatus;
    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @ColumnInfo(name ="createddate")
    private String createdDate;

    @ColumnInfo(name ="patientid")
    String patientid = "";

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @ColumnInfo(name ="notes")
    String notes = "";

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

    public String getLastupdatenumber() {
        return lastupdatenumber;
    }

    public void setLastupdatenumber(String lastupdatenumber) {
        this.lastupdatenumber = lastupdatenumber;
    }



    public String getPatientid() {
        return patientid;
    }

    public void setPatientid(String patientid) {
        this.patientid = patientid;
    }
}
