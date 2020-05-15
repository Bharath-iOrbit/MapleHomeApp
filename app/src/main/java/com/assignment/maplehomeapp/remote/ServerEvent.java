package com.assignment.maplehomeapp.remote;

import com.assignment.maplehomeapp.model.Records;
import com.assignment.maplehomeapp.model.StimulationHistory;
import com.assignment.maplehomeapp.model.StimulationPrescription;

import java.io.Serializable;
import java.util.List;

class ServerEvent implements Serializable {

    private LoginResponse loginResponse;

    public ServerEvent(List<StimulationPrescription> stimulationPrescriptions) {
        this.stimulationPrescriptions = stimulationPrescriptions;
    }

    private List<StimulationPrescription> stimulationPrescriptions;

    public ServerEvent(StimulationPrescription stimulationSessions) {
        this.stimulationSessions = stimulationSessions;
    }

    public StimulationPrescription getStimulationSessions() {
        return stimulationSessions;
    }

    public void setStimulationSessions(StimulationPrescription stimulationSessions) {
        this.stimulationSessions = stimulationSessions;
    }

    private StimulationPrescription stimulationSessions;

    public ServerEvent(StimulationHistory stimulationHistory) {
        this.stimulationHistory = stimulationHistory;
    }

    private StimulationHistory stimulationHistory;

    public ServerEvent(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
    }

    public LoginResponse getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
    }

}
