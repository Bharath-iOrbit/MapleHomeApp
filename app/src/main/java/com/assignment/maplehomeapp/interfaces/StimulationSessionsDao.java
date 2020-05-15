package com.assignment.maplehomeapp.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.assignment.maplehomeapp.model.DetailEntity;
import com.assignment.maplehomeapp.model.Prescriptions;
import com.assignment.maplehomeapp.model.StimulationHistory;

import java.util.List;

@Dao
public interface StimulationSessionsDao {


    @Query("SELECT * FROM DetailEntity WHERE patientid= :patientID and prescriptionid= :prescID")
    List<DetailEntity> getAll(String patientID,String prescID);

    @Query("SELECT * FROM Prescriptions WHERE patientid= :patientID order by prescriptionID desc")
    List<Prescriptions> getAllPresc(String patientID);

    @Query("SELECT * FROM stimulationhistory WHERE  SessionId= :sessionID")
    List<StimulationHistory> getASession(String sessionID);

    @Query("SELECT * FROM Prescriptions WHERE prescriptionuuid= :prescriptionID")
    List<Prescriptions> getAPresc(String prescriptionID);

    @Query("SELECT * FROM StimulationHistory where isUpdated=0")
    List<StimulationHistory> getAllSessionHistory();

    @Query("SELECT * FROM StimulationHistory order by StimulationUUID desc")
    List<StimulationHistory> getAllSessionHistoryList();


    @Insert
    void insertAll(DetailEntity... users);
    @Insert
    void insertAll(Prescriptions... prescriptions);

    @Query("UPDATE stimulationhistory SET isUpdated= :upload WHERE StimulationUUID = :id")
    void updateStimulationHistory(int id, int upload);

    @Query("UPDATE Prescriptions SET status= :status WHERE prescriptionuuid = :presID")
    void updatePrescription(String presID,int status);


    @Insert
    void insertHistory(StimulationHistory... stimulationHistories);



    @Query("DELETE  from DetailEntity  WHERE  StmulationSessionID= :id")
    void delete(int id);

    @Query("DELETE from DetailEntity")
    void dropDetailEntryTable();

    @Query("DELETE from StimulationHistory")
    void stimulationHistoryTable();
    @Query("DELETE from Prescriptions where prescriptionuuid= :prescriptionID")
    void deletePrescriptionTable(String prescriptionID);

    @Query("DELETE from DetailEntity where prescriptionid= :prescriptionID")
    void deleteSessions(String prescriptionID);

}
