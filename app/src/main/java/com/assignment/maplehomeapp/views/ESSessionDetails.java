package com.assignment.maplehomeapp.views;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.assignment.maplehomeapp.R;
import com.assignment.maplehomeapp.model.StimulationPending;
import com.assignment.maplehomeapp.utils.CommonDataArea;
import com.assignment.maplehomeapp.utils.Constants;
import com.assignment.maplehomeapp.utils.Parcelable;

import java.util.ArrayList;

public class ESSessionDetails extends AppCompatActivity {

    private Toolbar toolbar;
    Button runBtn;
    Parcelable stimulationPendings;
    Bundle bundle;
    TextView date, duration, current, cycles, pulsWidth, electrodeCnf, status, session,offset,delayTime;
    private int durationCal=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essession_details);
        //String id=getIntent().getStringExtra(Constants.ID);
        // bundle = new Bundle();
        // bundle = getIntent().getBundleExtra("bundle");
        stimulationPendings = (Parcelable) getIntent().getParcelableExtra("data");
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("Session Details");
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.blue));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        date = (TextView) findViewById(R.id.dateTxt);
        duration = (TextView) findViewById(R.id.duration_txt);
        current = (TextView) findViewById(R.id.current_txt);
        cycles = (TextView) findViewById(R.id.cycles_txt);
        pulsWidth = (TextView) findViewById(R.id.pulse_width);
        offset = (TextView) findViewById(R.id.offset);
        delayTime = (TextView) findViewById(R.id.delayTime);
        electrodeCnf = (TextView) findViewById(R.id.enf_txt);
        status = (TextView) findViewById(R.id.status_text);
        session = (TextView) findViewById(R.id.session_text);
        bindData();
        runBtn = (Button) findViewById(R.id.det_run_btn);
        runBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonDataArea.prescriptionStatus != 2) {
                    Intent intent = new Intent(ESSessionDetails.this, StimulationControlScreen.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(ESSessionDetails.this, "Prescription Cancelled", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void bindData() {
        if (CommonDataArea.prescriptionStatus != 2) {
            status.setText("PENDING");
            status.setBackgroundColor(getResources().getColor(R.color.light_blue));
        }
        else
        {
            status.setText("DELETED");
            status.setBackgroundColor(getResources().getColor(R.color.red));
        }
        try
        { durationCal=((((Integer.parseInt(CommonDataArea.mDataset.get(0).getFade_In_Out())*2))+Integer.parseInt(CommonDataArea.mDataset.get(0).getHold_Time())+Integer.parseInt(CommonDataArea.mDataset.get(0).getPause_Time()))*Integer.parseInt(CommonDataArea.mDataset.get(0).getCycle_Count()))/60;

        }catch (Exception e)
        {

        }
        date.setText(stimulationPendings.date);
        duration.setText(durationCal + " mins");
        current.setText(stimulationPendings.current + " ma");
        cycles.setText(stimulationPendings.cycles);
        pulsWidth.setText(CommonDataArea.mDataset.get(0).getHold_Time());
        electrodeCnf.setText(stimulationPendings.electrode_cnf);
        session.setText("Session #" + stimulationPendings.session_id);
        offset.setText(CommonDataArea.mDataset.get(0).getFade_In_Out());
        delayTime.setText(CommonDataArea.mDataset.get(0).getPause_Time());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
