package com.assignment.maplehomeapp.views;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.assignment.maplehomeapp.R;
import com.assignment.maplehomeapp.adapter.RecycleViewEsHistoryAdapter;
import com.assignment.maplehomeapp.adapter.RecycleViewEsSeessionAdapter;
import com.assignment.maplehomeapp.interfaces.StimulationSessionsDao;
import com.assignment.maplehomeapp.model.DetailEntity;
import com.assignment.maplehomeapp.model.StimulationHistory;
import com.assignment.maplehomeapp.model.StimulationPending;
import com.assignment.maplehomeapp.model.database.AppDataBase;

import java.util.ArrayList;
import java.util.List;

public class ESHistoryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    List<StimulationHistory> stimulationPendings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eshistory);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("ES Session History");
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.blue));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        recyclerView = (RecyclerView) findViewById(R.id.rv_stimulation_his);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        getStimulationHistList();
        // specify an adapter (see also next example)
        mAdapter = new RecycleViewEsHistoryAdapter(stimulationPendings, ESHistoryActivity.this);
        recyclerView.setAdapter(mAdapter);
    }

    public void getStimulationList() {
        stimulationPendings = new ArrayList<>();

//        for (int i = 1; i <= 10; i++) {
//            StimulationPending stimulationPending = new StimulationPending();
//            stimulationPending.setStimulationID("#" + i);
//            stimulationPending.setDate("22/12/2019");
//            stimulationPending.setDuration(i + " mins");
//            stimulationPending.setCurrent(i + " ma");
//            if (i == 10)
//                stimulationPending.setStatus("Completed");
//            else
//                stimulationPending.setStatus("Pending");
//            stimulationPending.setCompleted(String.valueOf(i * 10));
//            stimulationPendings.add(stimulationPending);
//        }
    }

    public void getStimulationHistList() {
        AppDataBase appDataBase = AppDataBase.getInstance(ESHistoryActivity.this);
        StimulationSessionsDao stimulationSessionsDao = appDataBase.stimulationSessionsDao();
        stimulationPendings = stimulationSessionsDao.getAllSessionHistoryList();



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
