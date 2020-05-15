package com.assignment.maplehomeapp.remote;

import com.assignment.maplehomeapp.model.DeviceAppInfo;
import com.assignment.maplehomeapp.model.StimulationHistory;
import com.assignment.maplehomeapp.model.StimulationPrescription;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ILogin {


    @POST("loginPatient.php")
    Call<LoginResponse> getLogin(
            @Body LoginResponse loginResponse
    );

    @POST("GetPrescriptionForApp.php")
    Call<StimulationPrescription> checkPrescriptions(
            @Body StimulationPrescription loginResponse

    );

    @POST("GetStimulationPrescription.php")
    Call<String> checkPrescriptionsString(
            @Body StimulationPrescription loginResponse

    );

    @POST("uploadStimulationSessionHist.php")
    Call<StimulationHistory> uploadStimulation(
            @Body StimulationHistory stimulationHistories
    );
    @POST("getDeletedPrescription.php")
    Call<List<StimulationPrescription>> deleteStimulation(
            @Body StimulationPrescription StimulationPrescription
    );

    @POST("ConnectedSystemInfo.php")
    Call<DeviceAppInfo> uploadDeviceAppInfo(
            @Body DeviceAppInfo deviceAppInfo
    );
}
