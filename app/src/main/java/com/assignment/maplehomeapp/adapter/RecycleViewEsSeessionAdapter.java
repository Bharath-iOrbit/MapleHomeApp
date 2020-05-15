package com.assignment.maplehomeapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.maplehomeapp.R;
import com.assignment.maplehomeapp.connection.BluetoothConnection;
import com.assignment.maplehomeapp.connection.PowerConnection;
import com.assignment.maplehomeapp.model.DetailEntity;
import com.assignment.maplehomeapp.model.StimulationPending;
import com.assignment.maplehomeapp.model.StimulationSettings;
import com.assignment.maplehomeapp.utils.CommonDataArea;
import com.assignment.maplehomeapp.utils.LogWriter;
import com.assignment.maplehomeapp.utils.Parcelable;
import com.assignment.maplehomeapp.utils.Popup;
import com.assignment.maplehomeapp.views.ESSessionActivity;
import com.assignment.maplehomeapp.views.ESSessionDetails;
import com.assignment.maplehomeapp.views.StimulationControlScreen;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewEsSeessionAdapter extends RecyclerView.Adapter<RecycleViewEsSeessionAdapter.StimulationViewHolder> {
    private List<DetailEntity> mDataset;
    Context context;
    BluetoothConnection bluetoothConnection;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class StimulationViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView stimulationIDTxt, dateTxt, durationTxt, currentTxt, prescrption_id, prescription_txt, statusCnfTxt;
        Button runBtn;
        ImageView navArrowImage;
        View statusView;

        public StimulationViewHolder(View v) {
            super(v);

            stimulationIDTxt = (TextView) v.findViewById(R.id.session_id_txt);
            dateTxt = (TextView) v.findViewById(R.id.date_txt);
            //   durationTxt = (TextView) v.findViewById(R.id.duration_txt);
            // currentTxt = (TextView) v.findViewById(R.id.currnt_txt);

            statusCnfTxt = (TextView) v.findViewById(R.id.status_txt);
            //  prescrption_id = (TextView) v.findViewById(R.id.pres_id);
            // prescription_txt = (TextView) v.findViewById(R.id.pres_txt);
            runBtn = (Button) v.findViewById(R.id.run_btn);
            navArrowImage = (ImageView) v.findViewById(R.id.nav_arrow_img);
            statusView = (View) v.findViewById(R.id.status_bar);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecycleViewEsSeessionAdapter(List<DetailEntity> myDataset, Context context) {
        this.context = context;
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public StimulationViewHolder onCreateViewHolder(ViewGroup parent,
                                                    int viewType) {
        // create a new view
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.stimulate_row_view, parent, false);
        StimulationViewHolder viewHolder = new StimulationViewHolder(listItem);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(StimulationViewHolder holder, final int position) {
        CommonDataArea.currSessionID = mDataset.get(position).getStmulationSessionID();
        if (CommonDataArea.prescriptionStatus!=2)
            holder.statusView.setBackgroundColor(ContextCompat.getColor(context, R.color.deep_sky_blue));
        else
            holder.statusView.setBackgroundColor(ContextCompat.getColor(context, R.color.red));

        holder.stimulationIDTxt.setText("Session #" + mDataset.get(position).getSessionid());
        holder.dateTxt.setText("Date : " + mDataset.get(position).getCreated_Date());
        // holder.durationTxt.setText("Duration\n"+mDataset.get(position).getDuration());
//        holder.prescrption_id.setText("pres id : " + mDataset.get(position).getPrescriptionid());
        // holder.currentTxt.setText("Cu : "+mDataset.get(position).getMax_Current());
//        holder.prescrption_id.setText(mDataset.get(position).getMax_Current());
//        holder.prescription_txt.setText(mDataset.get(position).getMax_Current());

        holder.statusCnfTxt.setText("Status : Pending");
        holder.runBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onClick(View v) {
                if (CommonDataArea.prescriptionStatus != 2) {
                    InitStimulationParameters(position);

                    CommonDataArea.selectedSessionId = position;

                    CommonDataArea.mDataset = mDataset;
                    Intent intent = new Intent(context, StimulationControlScreen.class);

                    context.startActivity(intent);
                }
                else
                {
                    Toast.makeText(context, "Prescription Cancelled", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.navArrowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InitStimulationParameters(position);
                Parcelable parcelable = new Parcelable(mDataset.get(position).getCreated_Date(), mDataset.get(position).getStatus(), mDataset.get(position).getDuration(), mDataset.get(position).getPulse_Frequency(), mDataset.get(position).getMax_Current(), mDataset
                        .get(position).getCycle_Count(), mDataset.get(position).getSourceElectrodes(), String.valueOf(mDataset.get(position).getSessionid()));

                Intent intent = new Intent(context, ESSessionDetails.class);


                intent.putExtra("data", (android.os.Parcelable) parcelable);
                context.startActivity(intent);
            }
        });

    }

    void InitStimulationParameters(int position) {
        CommonDataArea.isDeviceConnected = true;
        CommonDataArea.stimSettings = new StimulationSettings();
        try {
            if (!CommonDataArea.DEBUG_MODE) {
                CommonDataArea.stimSettings.stimCurrent = Double.parseDouble(mDataset.get(position).getMax_Current());
                CommonDataArea.stimSettings.stimulationFreq = Integer.parseInt(mDataset.get(position).getPulse_Frequency());
                CommonDataArea.stimSettings.pulsePhase = Integer.parseInt(mDataset.get(position).getPhase_Duration());
                CommonDataArea.stimSettings.fadeInOut = Integer.parseInt(mDataset.get(position).getFade_In_Out());
                CommonDataArea.stimSettings.holdTime = Integer.parseInt(mDataset.get(position).getHold_Time());
                CommonDataArea.stimSettings.delayTime = Integer.parseInt(mDataset.get(position).getPause_Time());
                CommonDataArea.stimSettings.repCount = Integer.parseInt(mDataset.get(position).getCycle_Count());
                CommonDataArea.currSessionID = mDataset.get(position).getStmulationSessionID();
                String sourceElectrodes = mDataset.get(position).getSourceElectrodes().replace(",","");
                String sinkElectrodes = mDataset.get(position).getSinkElectrodes().replace(",","");
                String electrodes = "";
                for (int i = 0; i < 24; ++i) {
                    if (sourceElectrodes.charAt(i) == '1') {
                        electrodes += "I";

                    } else if (sinkElectrodes.charAt(i) == '1') {
                        electrodes += "O";

                    } else electrodes += "0";
                }
                CommonDataArea.stimSettings.selectedElectrodes = electrodes;
            }
        } catch (Exception exp) {
            LogWriter.writeLog("parse exception", exp.getMessage());
        }
        CommonDataArea.selectedSessionId = position;

        CommonDataArea.mDataset = mDataset;

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
