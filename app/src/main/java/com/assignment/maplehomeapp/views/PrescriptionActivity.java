package com.assignment.maplehomeapp.views;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.assignment.maplehomeapp.R;
import com.assignment.maplehomeapp.adapter.RecycleViewEsSeessionAdapter;
import com.assignment.maplehomeapp.adapter.RecyclerviewPrescriptionAdapter;
import com.assignment.maplehomeapp.interfaces.StimulationSessionsDao;
import com.assignment.maplehomeapp.model.DetailEntity;
import com.assignment.maplehomeapp.model.Prescriptions;
import com.assignment.maplehomeapp.model.database.AppDataBase;
import com.assignment.maplehomeapp.remote.Communicator;
import com.assignment.maplehomeapp.utils.CommonDataArea;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionActivity extends AppCompatActivity {
    RecyclerView prescriptionRv;
    private Toolbar toolbar;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerviewPrescriptionAdapter mAdapter;
    SwipeRefreshLayout pullToRefresh;
    private Communicator communicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);
        toolbar = findViewById(R.id.pres_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        toolbar.setTitle("Prescriptions");
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.blue));
        toolbar.inflateMenu(R.menu.menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        prescriptionRv = (RecyclerView) findViewById(R.id.rv_prescription);
        prescriptionRv.setHasFixedSize(true);
        pullToRefresh = (SwipeRefreshLayout) findViewById(R.id.pullToRefresh);
        layoutManager = new LinearLayoutManager(this);
        prescriptionRv.setLayoutManager(layoutManager);
        communicator = new Communicator(this);


    }

    public List<Prescriptions> getStimulationList() {
        List<Prescriptions> prescriptionsList = null;
        try {
            AppDataBase appDataBase = AppDataBase.getInstance(PrescriptionActivity.this);
            StimulationSessionsDao stimulationSessionsDao = appDataBase.stimulationSessionsDao();
            List<Prescriptions> presEntities = stimulationSessionsDao.getAllPresc(CommonDataArea.patientID);
            prescriptionsList = new ArrayList<>();
            for (int i = 0; i < presEntities.size(); i++) {

                prescriptionsList.add(presEntities.get(i));

            }

        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
        return prescriptionsList;

    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter = new RecyclerviewPrescriptionAdapter(this, getStimulationList());
        prescriptionRv.setAdapter(mAdapter);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            int Refreshcounter = 1; //Counting how many times user have refreshed the layout

            @Override
            public void onRefresh() {
                //Here you can update your data from internet or from local SQLite data
                communicator.checkPrescriptionUpdate(CommonDataArea.patientID);
                //   communicator.checkPrescriptionDelete(CommonDataArea.patientID);
                mAdapter = new RecyclerviewPrescriptionAdapter(PrescriptionActivity.this, getStimulationList());
                prescriptionRv.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                pullToRefresh.setRefreshing(false);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pres_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                finish();
                return true;
            case R.id.download:
                communicator.checkPrescriptionUpdate(CommonDataArea.patientID);
                mAdapter = new RecyclerviewPrescriptionAdapter(PrescriptionActivity.this, getStimulationList());
                prescriptionRv.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.settings:
                Intent intent = new Intent(PrescriptionActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
