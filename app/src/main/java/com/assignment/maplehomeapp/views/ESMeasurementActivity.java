package com.assignment.maplehomeapp.views;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.assignment.maplehomeapp.R;
import com.assignment.maplehomeapp.utils.Popup;

import java.util.ArrayList;
import java.util.List;

public class ESMeasurementActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Spinner spinner_location,spinner_type;
    private TextView textview_increment_decrement,textview_increment,textview_decrement;
    private TextView textview_increment_decrement_contraction,textview_increment_contraction,textview_decrement_contraction;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esmeasurement);

        toolbar = findViewById(R.id.pres_toolbar);
////        textview_increment_decrement=(TextView)findViewById(R.id.textview_increment_decrement);
////        textview_increment=(TextView)findViewById(R.id.textview_increment);
////        textview_decrement=(TextView)findViewById(R.id.textview_decrement);
////        textview_increment_decrement_contraction=(TextView)findViewById(R.id.textview_increment_decrement_contraction);
////        textview_increment_contraction=(TextView)findViewById(R.id.textview_increment_contraction);
////        textview_decrement_contraction=(TextView)findViewById(R.id.textview_decrement_contraction);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//
        toolbar.setTitle("Measurement Settings");
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.blue));
        toolbar.inflateMenu(R.menu.menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
////        spinner_location =findViewById(R.id.spinner_location);
//
//        // Spinner Drop down elements
//        List<String> location = new ArrayList<String>();
//        location.add("Vaginal");
//        location.add("Anal");
//
//        // Creating adapter for spinner
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, location);
//
//        // Drop down layout style - list view with radio button
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // attaching data adapter to spinner
//        spinner_location.setAdapter(dataAdapter);
        spinner_type =findViewById(R.id.spinner_type);
//
        // Spinner Drop down elements
        List<String> type = new ArrayList<String>();
        type.add("Rest");
        type.add("MVC");
        type.add("Endurance");
        type.add("Training");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdaptertype = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, type);
//
        // Drop down layout style - list view with radio button
        dataAdaptertype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
        // attaching data adapter to spinner
        spinner_type.setAdapter(dataAdaptertype);





//        textview_increment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for(int position=0;position<textview_increment_decrement.getText().length();position++){
//
//                    counter = Integer.parseInt((String) textview_increment_decrement.getText());
//                    counter +=1;
//                    String stringVal = Integer.toString(counter);
//                    textview_increment_decrement.setText(stringVal);
//                }
//            }
//        });
//        textview_decrement.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for(int position=0;position<textview_increment_decrement.getText().length();position++){
//
//                    counter = Integer.parseInt((String) textview_increment_decrement.getText());
//                    counter -=1;
//                    String stringVal = Integer.toString(counter);
//                    textview_increment_decrement.setText(stringVal);
//                }
//            }
//        });
//        textview_increment_contraction.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for(int position=0;position<textview_increment_decrement_contraction.getText().length();position++){
//
//                    counter = Integer.parseInt((String) textview_increment_decrement_contraction.getText());
//                    counter +=1;
//                    String stringVal = Integer.toString(counter);
//                    textview_increment_decrement_contraction.setText(stringVal);
//                }
//            }
//        });
//        textview_decrement_contraction.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for(int position=0;position<textview_increment_decrement_contraction.getText().length();position++){
//
//                    counter = Integer.parseInt((String) textview_increment_decrement_contraction.getText());
//                    counter -=1;
//                    String stringVal = Integer.toString(counter);
//                    textview_increment_decrement_contraction.setText(stringVal);
//                }
//            }
//        });



    }
}
