package com.assignment.maplehomeapp.remote;

import com.google.gson.annotations.SerializedName;

public  class LoginResponse {

    public LoginResponse(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginResponse(String patientID) {
        this.patientID = patientID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("status")
    private String status;

    @SerializedName("sessionId")
    private String sessionID;

    @SerializedName("customerId")
    private String customerID;

    @SerializedName("userId")
    private String userID;

    @SerializedName("patientId")
    private String patientID;

    public LoginResponse() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @SerializedName("error")
    private String error;


    public LoginResponse(String status, String sessionID, String customerID, String userID, String patientID, String error) {
        this.status = status;
        this.sessionID = sessionID;
        this.customerID = customerID;
        this.userID = userID;
        this.patientID = patientID;
        this.error=error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

}
