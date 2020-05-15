package com.assignment.maplehomeapp.views;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.assignment.maplehomeapp.R;
import com.assignment.maplehomeapp.connection.UartService;
import com.assignment.maplehomeapp.interfaces.MapleInterface;
import com.assignment.maplehomeapp.interfaces.StimulationSessionsDao;
import com.assignment.maplehomeapp.interfaces.UserDao;
import com.assignment.maplehomeapp.model.StimulationHistory;
import com.assignment.maplehomeapp.model.database.AppDataBase;
import com.assignment.maplehomeapp.remote.Communicator;
import com.assignment.maplehomeapp.utils.CommonDataArea;
import com.assignment.maplehomeapp.utils.CommonFunctionArea;
import com.assignment.maplehomeapp.utils.Popup;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView esSessionImg, esHistoryImg,esMeasurementImg, logOutBtn;
    TextView userName;
    private Toolbar toolbar;
    String patientID = "";
    String patientName = "";
    Communicator communicator;
    private SharedPreferences prefs;
    Button connectButton;
    RelativeLayout esSessionRl, esHistoryRl,esMeasurementRL;

    private BluetoothDevice mDevice = null;
    private BluetoothAdapter mBtAdapter = null;
    private UartService mService = null;

    private static final int REQUEST_SELECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final int UART_PROFILE_READY = 10;
    public static final String TAG = "nRFUART";
    private static final int UART_PROFILE_CONNECTED = 20;
    private static final int UART_PROFILE_DISCONNECTED = 21;
    private static final int STATE_OFF = 10;
    private int mState = UART_PROFILE_DISCONNECTED;
    private ArrayAdapter<String> listAdapter;
    String bluetoothName = "";
    private Handler timerHandler;
    private Runnable updater;
    boolean isBack = true;
    private static final int MY_PERMISSIONS_REQUEST_CODE = 123;
    private Context mContext;
    private Activity mActivity;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_temp);
        prefs = getSharedPreferences(
                getPackageName(), Context.MODE_PRIVATE);
        boolean debug = prefs.getBoolean("liveDebug", true);
        mContext = getApplicationContext();
        mActivity = DashboardActivity.this;
        CommonDataArea.maple = new MapleInterface();
        if (debug)
            CommonDataArea.maple.Init(MapleInterface.MapleInterfaceMode.Production);
        else
            CommonDataArea.maple.Init(MapleInterface.MapleInterfaceMode.Simulation);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        }
        if (!CommonFunctionArea.isNetworkAvailable(DashboardActivity.this))
            new Popup(DashboardActivity.this).singleChoice();
        communicator = new Communicator(this);
        bluetoothName = prefs.getString("bluetoothAddress", "");
        CommonDataArea.AUDIO = prefs.getBoolean("audioCheck", true);
        patientID = prefs.getString("patientID", "");
        CommonDataArea.patientID = patientID;
        service_init();
        communicator.checkPrescriptionUpdate(CommonDataArea.patientID);
        communicator.uploadAppDeviceInfo();

        //    communicator.checkPrescriptionDelete(CommonDataArea.patientID);
        patientName = prefs.getString("patientname", "");
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        esSessionImg = (ImageView) findViewById(R.id.es_session_imgt);
        esHistoryImg = (ImageView) findViewById(R.id.es_history_img);
        esMeasurementImg =(ImageView) findViewById(R.id.es_measurement_imgt);
        userName = (TextView) findViewById(R.id.user_name);
        connectButton = (Button) findViewById(R.id.connectDevice);
        logOutBtn = (ImageView) findViewById(R.id.logout_btn);
        esHistoryRl = (RelativeLayout) findViewById(R.id.es_history_rl);
        esSessionRl = (RelativeLayout) findViewById(R.id.es_session_rl);
        esMeasurementRL=(RelativeLayout)findViewById(R.id.es_measurement_rl);
        userName.setText(patientName);

        esHistoryRl.setOnClickListener(this);
        esSessionRl.setOnClickListener(this);
        connectButton.setOnClickListener(this);
        logOutBtn.setOnClickListener(this);
//        voidingDiaryImg = (ImageView) findViewById(R.id.voiding_diary_img);
//        kegelImg = (ImageView) findViewById(R.id.kegel_img);
//        meetPFTImg = (ImageView) findViewById(R.id.pft_img);
//        syncDataImg = (ImageView) findViewById(R.id.sync_data_img);
        esSessionImg.setOnClickListener(this);
        esHistoryImg.setOnClickListener(this);
        esMeasurementImg.setOnClickListener(this);
//        voidingDiaryImg.setOnClickListener(this);
//        kegelImg.setOnClickListener(this);
//        meetPFTImg.setOnClickListener(this);
//        syncDataImg.setOnClickListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.es_session_imgt:
            case R.id.es_session_rl:
                intent = new Intent(DashboardActivity.this, PrescriptionActivity.class);
                startActivity(intent);
                break;
            case R.id.es_measurement_imgt:
            case R.id.es_measurement_rl:
                intent = new Intent(DashboardActivity.this, ESMeasurementActivity.class);
                startActivity(intent);
                break;


            case R.id.es_history_img:
            case R.id.es_history_rl:
                intent = new Intent(DashboardActivity.this, ESHistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.connectDevice:
                service_init();
                connectBluetooth();
                break;

            case R.id.logout_btn:
                new Popup(DashboardActivity.this).logout(DashboardActivity.this);



                break;
            // case R.id.voiding_diary_img:
//                intent = new Intent(DashboardActivity.this, VoidingDiaryActivity.class);
//                startActivity(intent);
//                break;
        }

    }


    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
        try {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(UARTStatusChangeReceiver);
        } catch (Exception ignore) {
            Log.e(TAG, ignore.toString());
        }
        unbindService(mServiceConnection);
        mService.stopSelf();
        mService = null;
    }

    public void checkPrescriptionUpdate() {
        AppDataBase appDataBase = AppDataBase.getInstance(DashboardActivity.this);
        StimulationSessionsDao stimulationSessionsDao = appDataBase.stimulationSessionsDao();
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");

        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        uploadStimulationHistory();
        if (!mBtAdapter.isEnabled()) {
            Log.i(TAG, "onResume - BT not enabled yet");
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
        if (!CommonDataArea.isBlueToothConnected)
            connectBLEDevice();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_SELECT_DEVICE:
                //When the DeviceListActivity return, with the selected device address
                if (resultCode == Activity.RESULT_OK && data != null) {
                    String deviceAddress = data.getStringExtra(BluetoothDevice.EXTRA_DEVICE);
                    mDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(deviceAddress);
                    Log.d(TAG, "... onActivityResultdevice.address==" + mDevice + "mserviceValue" + mService);
                    //  ((TextView) findViewById(R.id.deviceName)).setText(mDevice.getName() + " - connecting");
                    mService.connect(deviceAddress, new Popup(DashboardActivity.this).connectDevice("Connecting to " + deviceAddress, DashboardActivity.this));
                    CommonDataArea.uartService = mService;
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "Bluetooth has turned on ", Toast.LENGTH_SHORT).show();
                } else {
                    // User did not enable Bluetooth or an error occurred
                    Log.d(TAG, "BT not enabled");
                    Toast.makeText(this, "Problem in BT Turning ON ", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                Log.e(TAG, "wrong request code");
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void connectBluetooth() {
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        if (!mBtAdapter.isEnabled()) {
            Log.i(TAG, "onClick - BT not enabled yet");
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        } else {

            Intent newIntent = new Intent(DashboardActivity.this, DeviceListActivity.class);
            startActivityForResult(newIntent, REQUEST_SELECT_DEVICE);

        }
    }

    private void service_init() {
        Intent bindIntent = new Intent(this, UartService.class);
        bindService(bindIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(UARTStatusChangeReceiver, makeGattUpdateIntentFilter());
    }

    public void connectBLEDevice() {

        timerHandler = new Handler();
        updater = new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void run() {
                if (!CommonDataArea.isBlueToothConnected)
                    if (mService != null && !bluetoothName.equalsIgnoreCase(""))
                        mService.connect(bluetoothName, new Popup(DashboardActivity.this).connectDevice("Connecting to " + bluetoothName, DashboardActivity.this));

                timerHandler.postDelayed(updater, 10000);
            }
        };
        timerHandler.post(updater);

    }


    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UartService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(UartService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(UartService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(UartService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(UartService.DEVICE_DOES_NOT_SUPPORT_UART);
        return intentFilter;
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        public void onServiceConnected(ComponentName className, IBinder rawBinder) {
            mService = ((UartService.LocalBinder) rawBinder).getService();
            CommonDataArea.uartService = mService;
            Log.d(TAG, "onServiceConnected mService= " + mService);

            if (!mService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            } else {
                if (!bluetoothName.equalsIgnoreCase("")) {
                    mService.connect(bluetoothName, new Popup(DashboardActivity.this).connectDevice("Connecting to " + bluetoothName, DashboardActivity.this));
                }
            }
        }

        public void onServiceDisconnected(ComponentName classname) {
            ////     mService.disconnect(mDevice);
            mService = null;
        }
    };

    private final BroadcastReceiver UARTStatusChangeReceiver = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            final Intent mIntent = intent;
            //*********************//
            if (action.equals(UartService.ACTION_GATT_CONNECTED)) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                        try {
                            mDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(bluetoothName);

                        } catch (Exception e) {

                        }

                        Log.d(TAG, "UART_CONNECT_MSG");
                        if (mDevice != null) {
                            connectButton.setBackground(getResources().getDrawable(R.drawable.bluetooth));
                            ((TextView) findViewById(R.id.device_name)).setText(mDevice.getName());
                            CommonDataArea.blueToothName = mDevice.getName();
                            CommonDataArea.isBlueToothConnected = true;
                        }
                        mState = UART_PROFILE_CONNECTED;
                    }
                });
            }
            if (action.equals(UartService.ACTION_GATT_DISCONNECTED)) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                        Log.d(TAG, "UART_DISCONNECT_MSG");
                        CommonDataArea.isBlueToothConnected = false;
                        ((TextView) findViewById(R.id.device_name)).setText("Not Connected");
                        mState = UART_PROFILE_DISCONNECTED;
                        mService.close();
                        //setUiState();
                    }
                });
            }
            //*********************//
            if (action.equals(UartService.ACTION_GATT_SERVICES_DISCOVERED)) {

            }
            //*********************//
            if (action.equals(UartService.ACTION_DATA_AVAILABLE)) {
               /* final byte[] txValue = intent.getByteArrayExtra(UartService.EXTRA_DATA);
                runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            String text = new String(txValue, "UTF-8");
                            String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                            listAdapter.add("[" + currentDateTimeString + "] RX: " + text);
                            //messageListView.smoothScrollToPosition(listAdapter.getCount() - 1);
                        } catch (Exception e) {
                            Log.e(TAG, e.toString());
                        }
                    }
                });*/
            }
            //*********************//
            if (action.equals(UartService.DEVICE_DOES_NOT_SUPPORT_UART)) {
                CommonDataArea.isBlueToothConnected = false;

                showMessage("Device doesn't support UART. Disconnecting");
                // mService.disconnect();
                // mService.reConnect();
            }
        }
    };

    public void uploadStimulationHistory() {
        try {
            final String sessionid = prefs.getString("sessionId", "");
            AppDataBase appDataBase = AppDataBase.getInstance(DashboardActivity.this);
            StimulationSessionsDao stimulationSessionsDao = appDataBase.stimulationSessionsDao();
            List<StimulationHistory> stimulationHistories = stimulationSessionsDao.getAllSessionHistory();

            if (stimulationHistories != null) {
                if (stimulationHistories.size() > 0) {
                    communicator.uploadAppDeviceInfo();
                    for (StimulationHistory stimulationHistory : stimulationHistories) {
                        stimulationHistory.setPatientUUID(CommonDataArea.patientID);

                        new Communicator(DashboardActivity.this).uploadStimulationprescription(stimulationHistory);
                    }
                }
            }
        } catch (Exception exp) {

        }
    }

    @Override
    public void onBackPressed() {

        if (!isBack) {
            super.onBackPressed();

        }
    }

    protected void checkPermission() {
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION)
                + ContextCompat.checkSelfPermission(
                mActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                + ContextCompat.checkSelfPermission(
                mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Do something, when permissions not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    mActivity, Manifest.permission.CAMERA)
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    mActivity, Manifest.permission.READ_CONTACTS)
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // If we should give explanation of requested permissions

                // Show an alert dialog here with request explanation
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setMessage("All permissions are required to do the task.");
                builder.setTitle("Please grant those permissions");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(
                                mActivity,
                                new String[]{
                                        Manifest.permission.ACCESS_COARSE_LOCATION,
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                },
                                MY_PERMISSIONS_REQUEST_CODE
                        );
                    }
                });
                builder.setNeutralButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                // Directly request for required permissions, without explanation
                ActivityCompat.requestPermissions(
                        mActivity,
                        new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        MY_PERMISSIONS_REQUEST_CODE
                );
            }
        } else {
            // Do something, when permissions are already granted
            //  Toast.makeText(mContext, "Permissions already granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CODE: {
                // When request is cancelled, the results array are empty
                if (
                        (grantResults.length > 0) &&
                                (grantResults[0]
                                        + grantResults[1]
                                        + grantResults[2]
                                        == PackageManager.PERMISSION_GRANTED
                                )
                ) {
                    // Permissions are granted
                    //Toast.makeText(mContext, "Permissions granted.", Toast.LENGTH_SHORT).show();
                } else {
                    // Permissions are denied
                    // Toast.makeText(mContext,"Permissions denied.",Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//
//            case R.id.settings:
//                Intent intent = new Intent(DashboardActivity.this, SettingsActivity.class);
//                startActivity(intent);
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.settings, menu);
//        return true;
//
//    }
}
