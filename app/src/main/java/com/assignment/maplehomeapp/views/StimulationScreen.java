package com.assignment.maplehomeapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.assignment.maplehomeapp.R;
import com.assignment.maplehomeapp.adapter.RecycleViewEsSeessionAdapter;
import com.assignment.maplehomeapp.model.StimulationPending;

import java.util.ArrayList;

public class StimulationScreen extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<StimulationPending> stimulationPendings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stimulation_screen);
        recyclerView = (RecyclerView) findViewById(R.id.rv_stimulation_his);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        getStimulationList();
        // specify an adapter (see also next example)
      //
        //  mAdapter = new RecycleViewEsSeessionAdapter(stimulationPendings,StimulationScreen.this);
        recyclerView.setAdapter(mAdapter);

    }

    public void getStimulationList() {
        stimulationPendings = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            StimulationPending stimulationPending = new StimulationPending();
            stimulationPending.setStimulationID("Session " + i);
            stimulationPending.setDate("22/12/2019");
            stimulationPending.setDuration("10 Min");
            stimulationPending.setCurrent("10V");
            stimulationPending.setCycles("5");
            stimulationPending.setPulse("3");
            stimulationPending.setElectrodeCnf("elc cnf");
            stimulationPendings.add(stimulationPending);
        }
    }
}
