package com.assignment.maplehomeapp.views;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.assignment.maplehomeapp.R;
import com.assignment.maplehomeapp.utils.CommonDataArea;
import com.assignment.maplehomeapp.utils.Parcelable;

public class ESHistoryDetails extends AppCompatActivity {
    Button on_btn, off_btn;
    ImageView threedots;
    LinearLayout detailLayout;
    private Toolbar toolbar;
    private Parcelable stimulationPendings;
    TextView date, electrodecfg, perCompleted, duration, current, sessionId, det_prog_value;
    ProgressBar progressBarPercentage;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eshistory_details);
        toolbar = findViewById(R.id.my_toolbar);
        stimulationPendings = (Parcelable) getIntent().getParcelableExtra("hisData");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        toolbar.setTitle("Session Details");
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.blue));
        toolbar.inflateMenu(R.menu.menu);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        date = (TextView) findViewById(R.id.date_his);
        electrodecfg = (TextView) findViewById(R.id.ecg_his);
        perCompleted = (TextView) findViewById(R.id.comp_his);
        duration = (TextView) findViewById(R.id.duar_his);
        current = (TextView) findViewById(R.id.cur_his);
        det_prog_value = (TextView) findViewById(R.id.det_prog_value);
        sessionId = (TextView) findViewById(R.id.sessionid);
        progressBarPercentage = (ProgressBar) findViewById(R.id.percent_text);
        float a = Integer.parseInt(CommonDataArea.mDatasetHist.getTotalCycleCount());
        float b = Integer.parseInt(CommonDataArea.mDatasetHist.getCycle_Executed());
        float ab = ((a-b) / a) * 100;
        sessionId.setText("Session " + CommonDataArea.mDatasetHist.getSessionID());
        date.setText(stimulationPendings.date);
        electrodecfg.setText(CommonDataArea.mDatasetHist.getTotalCycleCount());
        duration.setText(CommonDataArea.mDatasetHist.getDuration()+" mins");
        current.setText(stimulationPendings.current+" ma");
        if (Math.round(ab) >99) {
            perCompleted.setText("Completed");
            progressBarPercentage.setProgressDrawable(getDrawable(R.drawable.circular_progress_completed));
        } else {
            progressBarPercentage.setProgressDrawable(getDrawable(R.drawable.circular_progress_failed));
            perCompleted.setText("Failed");
        }
        det_prog_value.setText(Math.round(ab)+"%");
        progressBarPercentage.setProgress(Math.round(CommonDataArea.histPercentage));

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
