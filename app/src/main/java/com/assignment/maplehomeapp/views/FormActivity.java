package com.assignment.maplehomeapp.views;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.assignment.maplehomeapp.R;
import com.assignment.maplehomeapp.interfaces.MapleInterface;
import com.assignment.maplehomeapp.utils.CommonDataArea;

public class FormActivity extends Activity {

    EditText Sf,Sc,Pd,Ht,dt,Rc,ft,Electrodes;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);
        b1 = (Button)findViewById(R.id.stimulate);
        Electrodes = (EditText)findViewById(R.id.setElectrodes);
        Sf = (EditText)findViewById(R.id.setStimulationFrequency);
        Sc = (EditText)findViewById(R.id.setStimulationCurrent);
        Pd = (EditText) findViewById(R.id.setPulsePhaseDuration);
        Ht = (EditText) findViewById(R.id.setHoldTime);
        dt = (EditText) findViewById(R.id.setDelayTime);
        ft = (EditText) findViewById(R.id.setFadeTime);
        Rc = (EditText) findViewById(R.id.setRepititionCount);




        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Electrode = Electrodes.getText().toString();
                String Stimulation_Frequency = Sf.getText().toString();
                int stim_freq = Integer.parseInt(Stimulation_Frequency);
                String Stimulation_Current = Sc.getText().toString();
                int stim_current = Integer.parseInt(Stimulation_Current);
                String Pulse_PhaseDuration = Pd.getText().toString();
                int phase_duration = Integer.parseInt(Pulse_PhaseDuration);
                String Hold_Time = Ht.getText().toString();
                int holdtime = Integer.parseInt(Hold_Time);
                String Delay_Time = dt.getText().toString();
                int delaytime = Integer.parseInt(Delay_Time);
                String Fade_Time = ft.getText().toString();
                int fadetime = Integer.parseInt(Fade_Time);
                String Repitition_Count = Rc.getText().toString();
                int repcount = Integer.parseInt(Repitition_Count);
                System.out.println("here");

                MapleInterface maple = new MapleInterface();
                maple.Init(MapleInterface.MapleInterfaceMode.Simulation);

                maple.setElectrodes(Electrode);
                maple.setStimulationFrequency(stim_freq);
                maple.setStimulationCurrent(stim_current);
                maple.setPulsePhaseDuration(phase_duration);
                maple.setHoldTime(holdtime);
                maple.setDelayTime(delaytime);
                maple.setFadeTime(fadetime);
                maple.setRepititionCount(repcount);
                maple.startStimulation();



            }


        });

    }



}
