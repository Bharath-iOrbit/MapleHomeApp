package com.assignment.maplehomeapp.remote;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.assignment.maplehomeapp.R;
import com.assignment.maplehomeapp.interfaces.StimulationSessionsDao;
import com.assignment.maplehomeapp.interfaces.UserDao;
import com.assignment.maplehomeapp.model.AppInfo;
import com.assignment.maplehomeapp.model.DetailEntity;
import com.assignment.maplehomeapp.model.DeviceAppInfo;
import com.assignment.maplehomeapp.model.DeviceInfo;
import com.assignment.maplehomeapp.model.Prescriptions;
import com.assignment.maplehomeapp.model.StimulationHistory;
import com.assignment.maplehomeapp.model.StimulationPrescription;
import com.assignment.maplehomeapp.model.UserDetails;
import com.assignment.maplehomeapp.model.database.AppDataBase;
import com.assignment.maplehomeapp.utils.CommonDataArea;
import com.assignment.maplehomeapp.utils.CommonFunctionArea;
import com.assignment.maplehomeapp.views.DashboardActivity;
import com.squareup.otto.Produce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Communicator {
    private final AppDataBase appDataBase;
    private final StimulationSessionsDao stimulationSessionsDao;
    Context context;
    Prescriptions prescriptions;
    String isValid = "";
    private static final String TAG = "Communicator";
    private static final String SERVER_URL = "http://178.128.165.237/php/maple/api/";
    HttpLoggingInterceptor logging;
    SharedPreferences prefs;
    final SharedPreferences.Editor editor;
    ILogin service;
    int isCalling = 0;

    public Communicator(Context context) {
        this.context = context;
        logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        prescriptions = new Prescriptions();

        prefs = context.getSharedPreferences(
                context.getPackageName(), Context.MODE_PRIVATE);
        final String sessionid = prefs.getString("sessionId", "");
        CommonDataArea.sessionID = sessionid;
        editor = prefs.edit();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Headers headers = request.headers().newBuilder().add("authorization", sessionid).build();
                request = request.newBuilder().headers(headers).build();
                return chain.proceed(request);
            }
        };
        httpClient.addInterceptor(interceptor);
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .client(httpClient.build())
                .build();

        service = retrofit.create(ILogin.class);
        appDataBase = AppDataBase.getInstance(context);
        stimulationSessionsDao = appDataBase.stimulationSessionsDao();
    }


    public String loginGet(final String username, final String password) {


        Call<com.assignment.maplehomeapp.remote.LoginResponse> call = service.getLogin(new com.assignment.maplehomeapp.remote.LoginResponse(username, password));

        call.enqueue(new Callback<com.assignment.maplehomeapp.remote.LoginResponse>() {
            @Override
            public void onResponse(Call<com.assignment.maplehomeapp.remote.LoginResponse> call, Response<com.assignment.maplehomeapp.remote.LoginResponse> response) {

                BusProvider.getInstance().post(new ServerEvent(response.body()));
                if (response.body().getPatientID() == null) {
                    if (CommonDataArea.passwdEdt != null) {
                        CommonDataArea.passwdEdt.setBackground(context.getDrawable(R.drawable.error_edit_text));
                        CommonDataArea.passwdEdt.setError("Incorrect password");
                    }
                    isValid = "";


                } else {
                    isValid = response.body().getPatientID();
                    UserDetails userDetails = new UserDetails();
                    userDetails.setUsername(username);
                    userDetails.setPassword(password);
                    userDetails.setCustomerID(response.body().getCustomerID());
                    userDetails.setPatientID(response.body().getPatientID());
                    userDetails.setSessionID(response.body().getSessionID());
                    userDetails.setStatus(response.body().getStatus());
                    userDetails.setUserID(1);
                    inserUserDetails(userDetails);
                    if (isCalling == 1)
                        new Communicator(context).checkPrescriptionUpdate(response.body().getPatientID());

                    Intent intent = new Intent(context, DashboardActivity.class);
                    intent.putExtra("patientid", response.body().getPatientID());
                    context.startActivity(intent);
                    editor.putBoolean("isLogin", true);
                    editor.putString("patientID", response.body().getPatientID());
                    editor.putString("patientname", username);
                    editor.putString("password", password);
                    editor.putString("sessionId", response.body().getSessionID());
                    editor.commit();
                }

            }

            @Override
            public void onFailure(Call<com.assignment.maplehomeapp.remote.LoginResponse> call, Throwable t) {
                BusProvider.getInstance().post(new ErrorEvent(-2, t.getMessage()));
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return isValid;
    }


    public void checkPrescriptionUpdate(final String patientID) {


        final String lastUpdate = prefs.getString("lastUpdate", "0");
        final String username = prefs.getString("patientname", "0");
        final String password = prefs.getString("password", "0");


        Call<StimulationPrescription> stimulationSessionsCall = service.checkPrescriptions(new StimulationPrescription(patientID, lastUpdate));
        stimulationSessionsCall.enqueue(new Callback<StimulationPrescription>() {
            @Override
            public void onResponse(Call<StimulationPrescription> call, Response<StimulationPrescription> response) {
                Log.i("Tag", "Request data " + response.raw());

                BusProvider.getInstance().post(new ServerEvent(response.body()));
                if (response.body().getSession().equalsIgnoreCase("Session Expired")) {
                    isCalling = 1;
                    new Communicator(context).loginGet(username, password);

                } else {
                    if (!response.body().getMessage().equalsIgnoreCase("Prescription unavailable")) {
                        insertSessionDetails(addStimulationData(response.body()), patientID);
                        String s = response.body().getLastupdatenumber();
                        editor.putString("lastUpdate", s);
                        editor.apply();
                        editor.commit();
                        checkPrescriptionUpdate(CommonDataArea.patientID);
                    }

                }

            }

            @Override
            public void onFailure(Call<StimulationPrescription> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("gh", "No Prescriptions");
            }
        });
    }


    public void uploadStimulationprescription(final StimulationHistory stimulationHistories) {


        Call<StimulationHistory> stimulationHistoryCall = service.uploadStimulation(stimulationHistories);
        stimulationHistoryCall.enqueue(new Callback<StimulationHistory>() {
            @Override
            public void onResponse(Call<StimulationHistory> call, Response<StimulationHistory> response) {
                Log.i("Tag", "Request data " + response.raw());

                BusProvider.getInstance().post(new ServerEvent(response.body()));
//                if (response.body().getResponse().equalsIgnoreCase("Session Expired")) {
//                    isCalling = 2;
//                    new Communicator(context).loginGet(username, password);
//                }
                if (response.body().getResponse().equalsIgnoreCase("1") || response.body().getResponse().equalsIgnoreCase("4")) {
                    stimulationSessionsDao.updateStimulationHistory(stimulationHistories.getStimulationUUID(), 1);

                }


            }

            @Override
            public void onFailure(Call<StimulationHistory> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("gh", "No Prescriptions");
            }
        });
    }


    public void uploadAppDeviceInfo() {
        DeviceAppInfo deviceAppInfo = new DeviceAppInfo();
        AppInfo appInfo = new AppInfo();
        deviceAppInfo.setDeviceConnected("No");
        deviceAppInfo.setPatientUUID(CommonDataArea.patientID);
        appInfo.setAppInstanceName("kjkl");
        appInfo.setAppOSVersion(CommonFunctionArea.deviceOS());
        appInfo.setAppUUID(CommonFunctionArea.getDeviceUUID(context));
        appInfo.setAppVersion(CommonFunctionArea.appVersion(context));
        appInfo.setSystemNumber("lj");
        deviceAppInfo.setAppInfo(appInfo);
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setDevMacID("jhgkh");
        deviceInfo.setDevUUID("lhuo");
        deviceInfo.setFirmwareVersion("adfda");
        deviceInfo.setModelNo("f");
        deviceAppInfo.setDeviceInfo(deviceInfo);

        final String username = prefs.getString("patientname", "0");
        final String password = prefs.getString("password", "0");

        Call<DeviceAppInfo> stimulationHistoryCall = service.uploadDeviceAppInfo(deviceAppInfo);
        stimulationHistoryCall.enqueue(new Callback<DeviceAppInfo>() {
            @Override
            public void onResponse(Call<DeviceAppInfo> call, Response<DeviceAppInfo> response) {
                Log.i("Tag", "Request data " + response.raw());
                if (response.body().getMessage().equalsIgnoreCase("Session Expired")) {
                    new Communicator(context).loginGet(username, password);

                }

            }

            @Override
            public void onFailure(Call<DeviceAppInfo> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("gh", "No Prescriptions");
            }
        });
    }


    @Produce
    public ServerEvent produceServerEvent(com.assignment.maplehomeapp.remote.LoginResponse loginResponse) {
        return new ServerEvent(loginResponse);
    }

    @Produce
    public ErrorEvent produceErrorEvent(int errorCode, String errorMsg) {
        return new ErrorEvent(errorCode, errorMsg);
    }

    public void inserUserDetails(UserDetails userDetails) {
        try {
            AppDataBase appDataBase = AppDataBase.getInstance(context);
            UserDao userDao = appDataBase.userDetailsDao();
            userDao.insertAll(userDetails);
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }


    }

    public ArrayList<DetailEntity> addStimulationData(StimulationPrescription stimulationPrescription) {
        DetailEntity detail;
        ArrayList<DetailEntity> details = new ArrayList<>();
        prescriptions.setLastupdatenumber(stimulationPrescription.getLastupdatenumber());
        prescriptions.setTherapistid(stimulationPrescription.getTherapistid());
        prescriptions.setNotes(stimulationPrescription.getNotes());
        prescriptions.setNumrecords(stimulationPrescription.getNumrecords());
        prescriptions.setPrescriptionstatus(stimulationPrescription.getPrescriptionstatus());
        prescriptions.setPrescriptionuuid(stimulationPrescription.getPrescriptionid());
        prescriptions.setCreatedDate(stimulationPrescription.getCreated_Date());
        prescriptions.setStatus(Integer.parseInt(stimulationPrescription.getStatus()));
        prescriptions.setPrescriptionstatus(stimulationPrescription.getPrescriptionstatus());
        if (stimulationPrescription.getDetails() != null) {
            if (stimulationPrescription.getDetails().length > 0) {
                for (int i = 0; i < stimulationPrescription.getDetails().length; i++) {


//                detailArrayList = stimulationPrescription.getRecords().get(i).getDetails();
//                for (int j = 0; j < detailArrayList.size(); j++) {

//                    detailList = detailArrayList.get(j);
//                    if (detailList != null) {
//                        for (int k = 0; k < detailList.size(); k++) {
                    detail = new DetailEntity();

                    //   prescriptions.setPatientid(p);
                    detail.setTherapistid(stimulationPrescription.getTherapistid());

                    detail.setNumrecords(stimulationPrescription.getNumrecords());
                    detail.setCreated_Date(stimulationPrescription.getCreated_Date());
                    detail.setPrescriptionid(stimulationPrescription.getPrescriptionid());

                    detail.setTitle(stimulationPrescription.getDetails()[i].getTitle());
                    detail.setSourceElectrodes(stimulationPrescription.getDetails()[i].getSourceElectrodes());
                    detail.setSessionid(stimulationPrescription.getDetails()[i].getSessionid());
                    detail.setSinkElectrodes(stimulationPrescription.getDetails()[i].getSinkElectrodes());
                    detail.setPhase_Duration(stimulationPrescription.getDetails()[i].getPhase_Duration());
                    detail.setMax_Current(stimulationPrescription.getDetails()[i].getMax_Current());
                    detail.setMeasurement_Iteration(stimulationPrescription.getDetails()[i].getMeasurement_Iteration());
                    detail.setMeasurement_Type(stimulationPrescription.getDetails()[i].getMeasurement_Type());
                    detail.setInstrument_Location(stimulationPrescription.getDetails()[i].getInstrument_Location());
                    detail.setPulse_Frequency(stimulationPrescription.getDetails()[i].getPulse_Frequency());
                    detail.setConfiguration(stimulationPrescription.getDetails()[i].getConfiguration());
                    detail.setMin_Current(stimulationPrescription.getDetails()[i].getMin_Current());
                    detail.setVisit_Date(stimulationPrescription.getDetails()[i].getVisit_Date());
                    detail.setStart_Time(stimulationPrescription.getDetails()[i].getStart_Time());
                    detail.setDuration(stimulationPrescription.getDetails()[i].getDuration());
                    detail.setPreset(stimulationPrescription.getDetails()[i].getPreset());
                    detail.setFade_In_Out(stimulationPrescription.getDetails()[i].getFade_In_Out());
                    detail.setHold_Time(stimulationPrescription.getDetails()[i].getHold_Time());
                    detail.setPause_Time(stimulationPrescription.getDetails()[i].getPause_Time());
                    detail.setCycle_Count(stimulationPrescription.getDetails()[i].getCycle_Count());
                    if (Integer.parseInt(stimulationPrescription.getPrescriptionstatus()) == 1)
                        detail.setStatus("3");
                    else
                        detail.setStatus("0");


                    details.add(detail);

                    //   }
                    //  }
                    //   }

                }
            }
        }
        return details;
    }

    public void insertSessionDetails(ArrayList<DetailEntity> recordsEntity, String patientid) {


        List<Prescriptions> prescriptionsList = stimulationSessionsDao.getAPresc(prescriptions.getPrescriptionuuid());
        if (!prescriptionsList.isEmpty())
            stimulationSessionsDao.updatePrescription(prescriptions.getPrescriptionuuid(), prescriptions.getStatus());
        else {
            prescriptions.setPatientid(patientid);
            for (int i = 0; i < recordsEntity.size(); i++) {
                recordsEntity.get(i).setPatientID(patientid);
                stimulationSessionsDao.insertAll(recordsEntity.get(i));
            }
            stimulationSessionsDao.insertAll(prescriptions);

        }

    }

    public void checkDeleteUpdates() {

    }
}
