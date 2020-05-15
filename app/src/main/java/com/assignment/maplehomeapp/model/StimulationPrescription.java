package com.assignment.maplehomeapp.model;

import android.icu.text.AlphabeticIndex;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StimulationPrescription {

    @SerializedName("therapistname")
    private String therapistid;
    @SerializedName("numrecords")
    private String numrecords;
    @SerializedName("lastupdatenumber")
    private String lastupdatenumber;
    @SerializedName("details")
    private Detail[] details;
    @SerializedName("Created_Date")
    private String Created_Date;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @SerializedName("status")
    private String status;

    public String getPrescriptionstatus() {
        return prescriptionstatus;
    }

    public void setPrescriptionstatus(String prescriptionstatus) {
        this.prescriptionstatus = prescriptionstatus;
    }

    @SerializedName("prescriptionstatus")
    private String prescriptionstatus;

    public StimulationPrescription(String patientid) {
        this.patientid = patientid;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @SerializedName("notes")
    private String notes;

    public String getPrescriptionid() {
        return prescriptionid;
    }

    public void setPrescriptionid(String prescriptionid) {
        this.prescriptionid = prescriptionid;
    }

    @SerializedName("prescriptionid")
    private String prescriptionid;

    @SerializedName("patientid")
    String patientid = "";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("message")
    String message = "";

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    @SerializedName("session")
    String session = "";

    public String getTherapistid ()
    {
        return therapistid;
    }

    public void setTherapistid (String therapistid)
    {
        this.therapistid = therapistid;
    }

    public String getNumrecords ()
    {
        return numrecords;
    }

    public void setNumrecords (String numrecords)
    {
        this.numrecords = numrecords;
    }

    public String getLastupdatenumber ()
    {
        return lastupdatenumber;
    }

    public void setLastupdatenumber (String lastupdatenumber)
    {
        this.lastupdatenumber = lastupdatenumber;
    }

    public Detail[] getDetails ()
    {
        return details;
    }

    public void setDetails (Detail[] details)
    {
        this.details = details;
    }

    public String getCreated_Date ()
    {
        return Created_Date;
    }

    public void setCreated_Date (String Created_Date)
    {
        this.Created_Date = Created_Date;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [therapistid = "+therapistid+", numrecords = "+numrecords+", lastupdatenumber = "+lastupdatenumber+", details = "+details+", Created_Date = "+Created_Date+"]";
    }

    public StimulationPrescription(String patientid, String lastUpdate) {
        this.patientid = patientid;
        this.lastupdatenumber = lastUpdate;
    }

    public StimulationPrescription() {
    }
}
