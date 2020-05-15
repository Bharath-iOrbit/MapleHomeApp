package com.assignment.maplehomeapp.views;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.assignment.maplehomeapp.R;
import com.assignment.maplehomeapp.interfaces.MapleInterface;
import com.assignment.maplehomeapp.utils.CommonDataArea;

public class SettingsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    Switch audioSwitch;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Switch liveDebugSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        prefs = getSharedPreferences(
                getPackageName(), Context.MODE_PRIVATE);
        audioSwitch = (Switch) findViewById(R.id.audioSwitch);
        liveDebugSwitch = (Switch) findViewById(R.id.livedebugSwitch);
        audioSwitch.setTextOn("On");
        audioSwitch.setTextOff("Off");
        liveDebugSwitch.setTextOn("Live");
        liveDebugSwitch.setTextOff("Debug");
        toolbar = findViewById(R.id.settings_toolbar);

        editor = prefs.edit();
        audioSwitch.setChecked(prefs.getBoolean("audioCheck", true));
        liveDebugSwitch.setChecked(prefs.getBoolean("liveDebug", true));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("Settings");
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.blue));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        audioSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("audioCheck", isChecked);
                editor.commit();
                CommonDataArea.AUDIO = isChecked;
            }
        });
        liveDebugSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean("liveDebug", b);
                editor.commit();
                CommonDataArea.maple = new MapleInterface();
                if (b)
                    CommonDataArea.maple.Init(MapleInterface.MapleInterfaceMode.Production);
                else
                    CommonDataArea.maple.Init(MapleInterface.MapleInterfaceMode.Simulation);


            }
        });
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
