package com.assignment.maplehomeapp.views;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.assignment.maplehomeapp.R;
import com.assignment.maplehomeapp.interfaces.MapleInterface;
import com.assignment.maplehomeapp.interfaces.StimulationSessionsDao;
import com.assignment.maplehomeapp.model.DetailEntity;
import com.assignment.maplehomeapp.model.StimulationHistory;
import com.assignment.maplehomeapp.model.StimulationSettings;
import com.assignment.maplehomeapp.model.database.AppDataBase;
import com.assignment.maplehomeapp.utils.CommonDataArea;
import com.assignment.maplehomeapp.utils.CommonFunctionArea;
import com.assignment.maplehomeapp.utils.Parcelable;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class StimulationControlScreen extends AppCompatActivity implements View.OnClickListener {
    private static final int REP_DELAY = 50;
    TextView bluetoothTxt, powewrTxt, stimStatus, remainingCycles, measuredStimCurrent, measuredVoltage, current_txt;
    private BroadcastReceiver pReceiver;
    GraphView graph;
    private final Handler mHandler = new Handler();
    private final Handler updateHandler = new Handler();
    private Runnable mTimer;
    ImageButton add_Btn, minus_Btn;
    private double graphLastXValue = 5d;
    private LineGraphSeries<DataPoint> mSeries;
    RelativeLayout circleBind;
    LinearLayout status_update, elcCnfLayout, graphLayout;
    CircleView circleView;
    boolean isStop = false;
    Button playButton, pauseButton, stopButton, navButton;
    ImageButton back_arrow;
    String electrdConf = "";
    private boolean isPlay = false;
    private Parcelable stimulationPendings;
    double deltaYInc;
    double maxCurrent;
    int fadeTimeSamples;
    int holdTimeSamples;
    int delayTimeSamples;
    int totalSamples;
    static int SAMPLE_TIME = 100; //milliseconds
    static int CONVERT_SEC_TO_MILLIS = 1000; //milliseconds
    boolean isShow = true;
    private float x1, x2;

    boolean isStarted = false;
    static final int MIN_DISTANCE = 150;
    StimulationSettings stim;
    double curStimCurrent = 0;
    private Handler repeatUpdateHandler = new Handler();
    private boolean mAutoIncrement = false;
    private boolean mAutoDecrement = false;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.test_stim);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setTitle(getResources().getString(R.string.stim_control));
            }

            stim = CommonDataArea.stimSettings;
            curStimCurrent = stim.stimCurrent / 10;
            playButton = (Button) findViewById(R.id.play_btn);
            playButton.setOnClickListener(this);
            pauseButton = (Button) findViewById(R.id.pause_btn);
            back_arrow = (ImageButton) findViewById(R.id.back_arrow_btn);
            stopButton = (Button) findViewById(R.id.stop_btn);
            pauseButton.setOnClickListener(this);
            stopButton.setOnClickListener(this);
            back_arrow.setOnClickListener(this);
            //navButton.setOnClickListener(this);

            electrdConf = stim.selectedElectrodes;
            circleView = new CircleView(StimulationControlScreen.this);
            status_update = (LinearLayout) findViewById(R.id.status_update);
            elcCnfLayout = (LinearLayout) findViewById(R.id.elec_cnf);
            graphLayout = (LinearLayout) findViewById(R.id.graph_layout);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            circleView.setLayoutParams(layoutParams);
            circleBind = (RelativeLayout) findViewById(R.id.circle_layout);
            circleBind.addView(circleView);
            pReceiver = new BatteryBroadcastReceiver();
            bluetoothTxt = (TextView) findViewById(R.id.bluetooth_txt);
            powewrTxt = (TextView) findViewById(R.id.power_txt);
            stimStatus = (TextView) findViewById(R.id.stim_stutus_txt);
            remainingCycles = (TextView) findViewById(R.id.rem_cycles_txt);
            measuredStimCurrent = (TextView) findViewById(R.id.measured_stim_current_txt);
            measuredVoltage = (TextView) findViewById(R.id.measured_voltage_txt);
            current_txt = (TextView) findViewById(R.id.curr_txt);
            add_Btn = (ImageButton) findViewById(R.id.plus_btn);
            minus_Btn = (ImageButton) findViewById(R.id.minus_btn);
            add_Btn.setOnClickListener(this);
            minus_Btn.setOnClickListener(this);
            IntentFilter filter = new IntentFilter();
            current_txt.setText("Current\n" + curStimCurrent + " mA");
            filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
            filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
            filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
            filter.addAction(BluetoothDevice.ACTION_CLASS_CHANGED);
            filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
            add_Btn.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mAutoIncrement = true;
                    mAutoDecrement = false;
                    repeatUpdateHandler.post(new RptUpdater());
                    return false;
                }
            });

            minus_Btn.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mAutoDecrement = true;
                    mAutoIncrement = false;
                    repeatUpdateHandler.post(new RptUpdater());
                    return false;
                }
            });
            add_Btn.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
                            && mAutoIncrement) {
                        mAutoIncrement = false;
                    }
                    return false;
                }
            });
            minus_Btn.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
                            && mAutoDecrement) {
                        mAutoDecrement = false;
                    }
                    return false;
                }
            });
            this.registerReceiver(mReceiver, filter);
            if (CommonDataArea.isBlueToothConnected) {
                bluetoothTxt.setText("Connected to" + CommonDataArea.blueToothName);
            }
        } catch (Exception exp) {
            Log.d("exp", exp.getMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    boolean StartStimulation() {
        try {
//            if (!CommonDataArea.isBlueToothConnected) {
//                bluetoothTxt.setText("Diconnected from:" + CommonDataArea.blueToothName);
//                Toast.makeText(this, "Device not connected", Toast.LENGTH_LONG);
//                return false;
//            }
            //stimulationPendings = (Parcelable) getIntent().getParcelableExtra("data");
            stimulationPendings = (Parcelable) CommonDataArea.stimParcel;

            MapleInterface maple = CommonDataArea.maple;

            // List<DetailEntity> dataSet = CommonDataArea.mDataset;
            // int position = CommonDataArea.selectedSessionId;

            maple.setElectrodes(stim.selectedElectrodes);
            maple.setStimulationFrequency(stim.stimulationFreq);
            maple.setStimulationCurrent((float) curStimCurrent);
            maple.setPulsePhaseDuration(stim.pulsePhase);
            maple.setHoldTime(stim.holdTime);
            maple.setDelayTime(stim.delayTime);
            maple.setFadeTime(stim.fadeInOut);
            maple.setRepititionCount(stim.repCount);
            maple.startStimulation();

            //Screen update on every 100 milliseconds
            fadeTimeSamples = stim.fadeInOut * CONVERT_SEC_TO_MILLIS / SAMPLE_TIME; //converting to milli secon
            holdTimeSamples = stim.holdTime * CONVERT_SEC_TO_MILLIS / SAMPLE_TIME;
            delayTimeSamples = stim.delayTime * CONVERT_SEC_TO_MILLIS / SAMPLE_TIME;
            totalSamples = fadeTimeSamples * 2 + holdTimeSamples + delayTimeSamples;
            maxCurrent = (double) stim.stimCurrent;
            deltaYInc = maxCurrent / fadeTimeSamples;
            graph = (GraphView) findViewById(R.id.graph);
            double maxY = stim.stimCurrent * 1.5;
            initGraph(graph, maxY);
            return true;
        } catch (Exception exp) {
            return false;
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                if (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1)
                        == BluetoothAdapter.STATE_OFF) {
                    Log.d("sdfds", "sfds");
                }

            }
        }


    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void initGraph(GraphView graph, double mayY) {
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(4);
        graph.getViewport().setDrawBorder(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(1);
        graph.getViewport().setMaxY(mayY);
        graph.getGridLabelRenderer().setLabelVerticalWidth(50);
        graph.getGridLabelRenderer().setLabelHorizontalHeight(50);
        graph.getViewport().setMaxXAxisSize(5);
        graph.getViewport().setMaxYAxisSize(5);
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Time(s)");
        graph.getGridLabelRenderer().setVerticalAxisTitle("Current i");
        // first mSeries is a line
        mSeries = new LineGraphSeries<>();
        mSeries.setDrawDataPoints(true);
        mSeries.setDrawBackground(true);
        graph.addSeries(mSeries);
    }

    double yVal = 0;
    int xVal = 0;
    CommonDataArea.StimState lastState;

    @Override
    protected void onResume() {

        lastState = CommonDataArea.StimState.off;
        registerReceiver(pReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        DrawGraph drawGraph = new DrawGraph();
        UpdateStimulationDetails updateStimulationDetails = new UpdateStimulationDetails();
        mHandler.postDelayed(drawGraph, 2000);
        updateHandler.postDelayed(updateStimulationDetails, 1000);
        super.onResume();
    }

    public class UpdateStimulationDetails implements Runnable {

        @Override
        public void run() {
            stimStatus.setText("Stimulation Status \n" + CommonDataArea.status);
            remainingCycles.setText("Remaining Cycles \n" + CommonDataArea.remStimCycles);
            measuredStimCurrent.setText("Stimulation Current \n" + CommonDataArea.measuredStimCurrent);
            measuredVoltage.setText("Stimulation Voltage \n" + CommonDataArea.measuredStimVolt);
            updateHandler.postDelayed(this, 5000);

        }
    }

    int respFreq = 0;

    public class DrawGraph implements Runnable {

        @Override
        public void run() {
            try {


                graphLastXValue += 0.25d;

                int fadein = holdTimeSamples;
                int holdPhase = (holdTimeSamples + fadeTimeSamples);
                int fadeOut = (2 * holdTimeSamples + fadeTimeSamples);
                int delayPhase = fadeOut + delayTimeSamples;

                if (lastState != CommonDataArea.state) {
                    lastState = CommonDataArea.state;
                    if (lastState == CommonDataArea.StimState.Onset) {
                        if (CommonDataArea.AUDIO)
                            new CommonFunctionArea().playTone(StimulationControlScreen.this, R.raw.beep09);
                        xVal = 0;
                    } else if (lastState == CommonDataArea.StimState.Hold) {
                        xVal = fadeTimeSamples;
                    } else if (lastState == CommonDataArea.StimState.Offset) {
                        xVal = holdPhase;
                    } else if (lastState == CommonDataArea.StimState.Delay) {
                        if (CommonDataArea.AUDIO)
                            new CommonFunctionArea().playTone(StimulationControlScreen.this, R.raw.beep07);
                        xVal = fadeOut;
                    }
                }
                if (CommonDataArea.status == CommonDataArea.StimStatus.Running) {
                    if (CommonDataArea.DEBUG_MODE) {
                        if (xVal < fadeTimeSamples) {
                            yVal += deltaYInc;
                            mSeries.appendData(new DataPoint(graphLastXValue, yVal), true, 22);

                        } else if (xVal < holdPhase) {
                            yVal = maxCurrent;
                            mSeries.appendData(new DataPoint(graphLastXValue, yVal), true, 22);
                        } else if (xVal < fadeOut) {
                            yVal -= deltaYInc;
                            mSeries.appendData(new DataPoint(graphLastXValue, yVal), true, 22);
                        } else if (xVal < delayPhase) {
                            yVal = 0;
                            mSeries.appendData(new DataPoint(graphLastXValue, yVal), true, 22);
                        } else if (xVal >= delayPhase) {
                            xVal = 0;
                        }
                    } else {
                        //Voltage Measured Across Current Limit Resistor = RefVOltage *(CurrentDacVal /DAC MAXVAL )
                        //Current Convertion is 100 mv per 1 ma
                        //Current = Voltage Measured Across Current Limit Resistor/100

                        yVal = ((((float) CommonDataArea.curDacVal / 4096f) * 2.9) * 1000) / 100;
                        mSeries.appendData(new DataPoint(graphLastXValue, yVal), true, 22);
                    }
                    xVal++;
                }
                ++respFreq;
                if (respFreq % 3 == 0) CommonDataArea.maple.KeepAlive();
                mHandler.postDelayed(this, SAMPLE_TIME);
            } catch (Exception e) {

            }
        }
    }

    double mLastRandom = 2;
    Random mRand = new Random();

    private double getRandom() {
        return mLastRandom += mRand.nextDouble() * 0.5 - 0.25;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_btn:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (StartStimulation()) {
                        if (CommonDataArea.AUDIO)
                            new CommonFunctionArea().playTone(StimulationControlScreen.this, R.raw.startstop);
                        isStop = true;
                        isStarted = true;
                        playButton.setBackgroundColor(getResources().getColor(R.color.dimgreen));
                        pauseButton.setBackgroundColor(getResources().getColor(R.color.pause));
                        back_arrow.setVisibility(View.GONE);
                        playButton.setEnabled(false);
                        isPlay = true;
                    }
                }

                break;
            case R.id.pause_btn:
                if (isPlay) {
                    if (CommonDataArea.AUDIO)
                        new CommonFunctionArea().playTone(StimulationControlScreen.this, R.raw.startstop);
                    playButton.setEnabled(true);
                    playButton.setBackgroundColor(getResources().getColor(R.color.green));
                    pauseButton.setBackgroundColor(getResources().getColor(R.color.dimpause));
                    isPlay = false;
                    stim.repCount = CommonDataArea.remStimCycles;
                    CommonDataArea.maple.stopStimulation();
                }
                break;
            case R.id.stop_btn:
                if (CommonDataArea.AUDIO)
                    new CommonFunctionArea().playTone(StimulationControlScreen.this, R.raw.startstop);
                isStop = true;
                isPlay = false;
                playButton.setBackgroundColor(getResources().getColor(R.color.green));
                pauseButton.setBackgroundColor(getResources().getColor(R.color.pause));
                playButton.setEnabled(true);
                pauseButton.setEnabled(true);
                back_arrow.setVisibility(View.VISIBLE);
                CommonDataArea.maple.stopStimulation();
                break;
            case R.id.plus_btn:
                increaseCurrent();
                break;
            case R.id.minus_btn:
                decreaseCurrent();
                break;

            case R.id.back_arrow_btn:
                finish();
        }
    }


    private class BatteryBroadcastReceiver extends BroadcastReceiver {
        private final static String BATTERY_LEVEL = "level";

        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BATTERY_LEVEL, 0);
            powewrTxt.setText("Power status " + level);
        }
    }

    @Override
    protected void onPause() {
        mHandler.removeCallbacks(mTimer);
        if (isStarted)
            insertStimulationHistory();
        super.onPause();
    }

    public class CircleView extends View {

        public CircleView(Context context) {
            super(context);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint p = new Paint();
            p.setColor(Color.BLACK);


            // DashPathEffect dashPath = new DashPathEffect(new float[]{6, 6}, (float) 2.0);
            p.setPathEffect(null);
            // p.setPathEffect(dashPath);
            p.setStyle(Paint.Style.STROKE);
            p.setStrokeWidth(2.5f);
            p.setAntiAlias(true);
            int temp = 0;
            Paint paint = new Paint();
            paint.setStrokeWidth(5.5f);
            paint.setColor(Color.BLACK);
            // canvas.drawLine(getWidth() / 2,
//                    getHeight() / 2, getWidth() / 2 - 285, getHeight() / 2 - 285, paint);
//
//            canvas.drawLine(getWidth() / 2,
//                    getHeight() / 2, getWidth() / 2 + 285 / 2, getHeight() / 2 - 285 / 2, paint);
//
//            canvas.drawLine(getWidth() / 2,
//                    getHeight() / 2, getWidth() / 2 - 285 / 2, getHeight() / 2 + 285 / 2, paint);
//
//            canvas.drawLine(getWidth() / 2,
//                    getHeight() / 2, getWidth() / 2 + 285 / 2, getHeight() / 2 + 285 / 2, paint);
            if (electrdConf == null) return;

            for (int i = 0; i < 6; i++) {

                Paint dots = new Paint();
                dots.setColor(Color.GRAY);
                dots.setStyle(Paint.Style.FILL);
                dots.setStrokeWidth(25.5f);
                if (i == 5) {
                    p.setStrokeWidth(3.5f);
                }

                if (!electrdConf.equalsIgnoreCase("")) {
                    canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, 60 + (i * 45), p);

                    dots.setColor(getColorElc(String.valueOf(electrdConf.charAt(temp + 12))));
                    canvas.drawCircle(canvas.getWidth() / 2, (canvas.getHeight() / 2) + 60 + (i * 45), 18, dots);


                    dots.setColor(getColorElc(String.valueOf(electrdConf.charAt(temp + 6))));
                    canvas.drawCircle((canvas.getWidth() / 2) + 60 + (i * 45), canvas.getHeight() / 2, 18, dots);

                    dots.setColor(getColorElc(String.valueOf(electrdConf.charAt(temp + 18))));
                    canvas.drawCircle((canvas.getWidth() / 2) - 60 - (i * 45), canvas.getHeight() / 2, 18, dots);


                    dots.setColor(getColorElc(String.valueOf(electrdConf.charAt(temp))));
                    canvas.drawCircle(canvas.getWidth() / 2, (canvas.getHeight() / 2) - 60 - (i * 45), 18, dots);
                    temp++;
                }

            }


            invalidate();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void navigateViews() {
        if (isShow) {
            isShow = false;
            elcCnfLayout.setVisibility(View.GONE);
            graphLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 2.45f));


        } else {
            isShow = true;
            elcCnfLayout.setVisibility(View.VISIBLE);
            graphLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.4f));

        }

    }


    public int getColorElc(String electrodConf) {
        int c = Color.GRAY;
        if (electrodConf.equalsIgnoreCase("I")) {
            c = Color.GREEN;
        } else if (electrodConf.equalsIgnoreCase("O")) {
            c = Color.BLUE;
        } else if (electrodConf.equalsIgnoreCase("0")) {
            c = Color.GRAY;
        }
        return c;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:

                navigateViews();

                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        if (isPlay) {
            Toast.makeText(this, "Can't press back button", Toast.LENGTH_SHORT).show();
        } else {

            super.onBackPressed();
        }
    }

    public void insertStimulationHistory() {

        StimulationHistory stimulationHistory = new StimulationHistory();
        stimulationHistory.setAppUUID(new CommonFunctionArea().getDeviceIMEI(StimulationControlScreen.this));
        stimulationHistory.setPrescriptionid(String.valueOf(CommonDataArea.mDataset.get(0).getPrescriptionid()));
        // stimulationHistory.setStimulationUUID(String.valueOf(CommonDataArea.mDataset.get(0).getStmulationSessionID()));
        //  stimulationHistory.setStmulationHistoryID(CommonDataArea.mDataset.get(0).StmulationSessionID);
        stimulationHistory.setPracticeUUID("dsad");
        stimulationHistory.setPatientUUID(CommonDataArea.patientID);
        stimulationHistory.setDevUUID("");
        stimulationHistory.setDuration(String.valueOf(CommonDataArea.mDataset.get(0).getDuration()));
        stimulationHistory.setPhysicianUUID("sa");
        stimulationHistory.setVisitId("saFad");
        stimulationHistory.setStimulationType("dfda");
        stimulationHistory.setStimDuration(CommonDataArea.mDataset.get(0).getDuration());
        stimulationHistory.setDateOfStimulation(new CommonFunctionArea().getDateTime());
        stimulationHistory.setCycle_Executed(String.valueOf(CommonDataArea.remStimCycles));
        stimulationHistory.setSimCurrentSet("casd");
        stimulationHistory.setSimCurrentGenerated(String.valueOf(CommonDataArea.measuredStimCurrent));
        stimulationHistory.setStatus(String.valueOf(50));
        stimulationHistory.setSessionID(String.valueOf(CommonDataArea.mDataset.get(0).getSessionid()));
        stimulationHistory.setIsUpdated(false);
        stimulationHistory.setTotalCycleCount(CommonDataArea.mDataset.get(0).getCycle_Count());
        AppDataBase appDataBase = AppDataBase.getInstance(StimulationControlScreen.this);
        StimulationSessionsDao stimulationSessionsDao = appDataBase.stimulationSessionsDao();

        stimulationSessionsDao.insertHistory(stimulationHistory);
        stimulationSessionsDao.delete(CommonDataArea.currSessionID);

    }


    public void increaseCurrent() {
        if (curStimCurrent < stim.stimCurrent) {
            curStimCurrent += 0.5;
            current_txt.setText("Current\n" + new CommonFunctionArea().roundTwoDecimals(curStimCurrent) + " mA");
            CommonDataArea.maple.SetStimCurrent((float) curStimCurrent);
        }
    }

    public void decreaseCurrent() {
        if (curStimCurrent > 0) {
            curStimCurrent -= 0.5;
            current_txt.setText("Current\n" + new CommonFunctionArea().roundTwoDecimals(curStimCurrent) + " mA");
            CommonDataArea.maple.SetStimCurrent((float) curStimCurrent);
        }
    }

    class RptUpdater implements Runnable {
        public void run() {
            if (mAutoIncrement) {
                increaseCurrent();
                repeatUpdateHandler.postDelayed(new RptUpdater(), REP_DELAY);
            } else if (mAutoDecrement) {
                decreaseCurrent();
                repeatUpdateHandler.postDelayed(new RptUpdater(), REP_DELAY);
            }
        }
    }

}
