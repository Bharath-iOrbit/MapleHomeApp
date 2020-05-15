package com.assignment.maplehomeapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class RecordsEntity {
    @PrimaryKey(autoGenerate = true)
    public int StmulationSessionID;

   @ColumnInfo(name ="therapistid")
    String therapistid;
    @ColumnInfo(name ="numrecords")
    String numrecords;

    public int getStmulationSessionID() {
        return StmulationSessionID;
    }

    public void setStmulationSessionID(int stmulationSessionID) {
        StmulationSessionID = stmulationSessionID;
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
