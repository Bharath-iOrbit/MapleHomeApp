package com.assignment.maplehomeapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.maplehomeapp.R;
import com.assignment.maplehomeapp.interfaces.StimulationSessionsDao;
import com.assignment.maplehomeapp.model.DetailEntity;
import com.assignment.maplehomeapp.model.Prescriptions;
import com.assignment.maplehomeapp.model.StimulationHistory;
import com.assignment.maplehomeapp.model.StimulationPending;
import com.assignment.maplehomeapp.model.database.AppDataBase;
import com.assignment.maplehomeapp.utils.CommonDataArea;
import com.assignment.maplehomeapp.utils.Constants;
import com.assignment.maplehomeapp.utils.Parcelable;
import com.assignment.maplehomeapp.views.ESHistoryDetails;
import com.assignment.maplehomeapp.views.ESSessionDetails;
import com.assignment.maplehomeapp.views.StimulationControlScreen;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewEsHistoryAdapter extends RecyclerView.Adapter<RecycleViewEsHistoryAdapter.StimulationViewHolder> {
    private final StimulationSessionsDao stimulationSessionsDao;
    private final AppDataBase appDataBase;
    private List<StimulationHistory> mDataset;
    Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class StimulationViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView stimulationIDTxt, pres_txt, dateTxt, statusCnfTxt, progress_value;
        ProgressBar completedTxt;

        ImageView navArrowImage;

        public StimulationViewHolder(View v) {
            super(v);

            stimulationIDTxt = (TextView) v.findViewById(R.id.session_id_txt);
            pres_txt = (TextView) v.findViewById(R.id.pres_id_txt);
            dateTxt = (TextView) v.findViewById(R.id.date_txt);
            completedTxt = (ProgressBar) v.findViewById(R.id.completed_text);

            statusCnfTxt = (TextView) v.findViewById(R.id.status_txt);
            //  runBtn = (Button) v.findViewById(R.id.run_btn);
            navArrowImage = (ImageView) v.findViewById(R.id.nav_arrow_img);
            progress_value = (TextView) v.findViewById(R.id.progress_value);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecycleViewEsHistoryAdapter(List<StimulationHistory> myDataset, Context context) {
        this.context = context;
        mDataset = myDataset;
        appDataBase = AppDataBase.getInstance(context);
        stimulationSessionsDao = appDataBase.stimulationSessionsDao();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecycleViewEsHistoryAdapter.StimulationViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                int viewType) {
        // create a new view
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.es_session_history, parent, false);
        StimulationViewHolder viewHolder = new StimulationViewHolder(listItem);
        return viewHolder;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecycleViewEsHistoryAdapter.StimulationViewHolder holder, final int position) {
        if (mDataset != null) {
            if (mDataset.size() > 0) {
                //    List<Stimu> detailEntities=stimulationSessionsDao.getASession( mDataset.get(position).getSessionID());
                float a = Integer.parseInt(mDataset.get(position).getTotalCycleCount());
                float b = Integer.parseInt(mDataset.get(position).getCycle_Executed());
                float ab = ((a-b) / a) * 100;
                CommonDataArea.histPercentage = ab;
                // float sessionCycleStatus = (Integer.parseInt((stimulationSessionsDao.getASession(CommonDataArea.patientID, mDataset.get(position).getSessionID())).get(0).getCycle_Count()) - Integer.parseInt((mDataset.get(position).getCycle_Executed()))) / 100;
                holder.stimulationIDTxt.setText("Stim ID :" + mDataset.get(position).getSessionID());
                holder.dateTxt.setText("Stim Date :" + mDataset.get(position).getDateOfStimulation());
                holder.pres_txt.setText("Pres D :" + mDataset.get(position).getPrescriptionid());
                if (Math.round(ab) > 99) {
                    holder.statusCnfTxt.setTextColor(context.getColor(R.color.green));
                    holder.completedTxt.setProgressDrawable(ContextCompat.getDrawable(context, R.drawable.circular_progress_completed));

                    holder.statusCnfTxt.setText("Status :Success");
                } else {
                    holder.statusCnfTxt.setTextColor(context.getColor(R.color.red));
                    holder.completedTxt.setProgressDrawable(ContextCompat.getDrawable(context, R.drawable.circular_progress_failed));

                    holder.statusCnfTxt.setText("Status :Failed");
                }

//                if (mDataset.get(position).getStatus().equalsIgnoreCase("0"))
//                    holder.completedTxt.setProgressDrawable(ContextCompat.getDrawable(context, R.drawable.circular_progress_completed));
//                if (mDataset.get(position).getStatus().equalsIgnoreCase("1") || mDataset.get(position).getStatus().equalsIgnoreCase("100"))
//                    holder.completedTxt.setProgressDrawable(ContextCompat.getDrawable(context, R.drawable.circular_progress_completed));
//                if (mDataset.get(position).getStatus().equalsIgnoreCase("2"))
//                    holder.completedTxt.setProgressDrawable(ContextCompat.getDrawable(context, R.drawable.circular_progress_failed));
                holder.progress_value.setText(Math.round(ab) + "%");

                //  holder.progress_value.setText("Partial");
                holder.completedTxt.setProgress(Math.round(ab));
                // holder.completedTxt.setText(mDataset.get(position).getCompleted());
//        holder.runBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, StimulationControlScreen.class);
//                context.startActivity(intent);
//            }
//        });
            }
        }
        holder.navArrowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonDataArea.mDatasetHist=mDataset.get(position);
                Parcelable parcelable = new Parcelable(mDataset.get(position).getDateOfStimulation(), mDataset.get(position).getDuration(), mDataset.get(position).getSimCurrentGenerated(), String.valueOf(mDataset.get(position).getStimulationUUID()), mDataset.get(position).getStatus(), mDataset.get(position).getStatus());
                Intent intent = new Intent(context, ESHistoryDetails.class);
                intent.putExtra("hisData", parcelable);
                context.startActivity(intent);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

