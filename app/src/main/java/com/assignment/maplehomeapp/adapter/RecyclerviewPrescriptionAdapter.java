package com.assignment.maplehomeapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.maplehomeapp.R;
import com.assignment.maplehomeapp.interfaces.StimulationSessionsDao;
import com.assignment.maplehomeapp.model.Prescriptions;
import com.assignment.maplehomeapp.model.database.AppDataBase;
import com.assignment.maplehomeapp.utils.CommonDataArea;
import com.assignment.maplehomeapp.views.ESSessionActivity;
import com.assignment.maplehomeapp.views.PrescriptionActivity;

import java.util.List;

public class RecyclerviewPrescriptionAdapter extends RecyclerView.Adapter<RecyclerviewPrescriptionAdapter.ViewHolder> {
    private final AppDataBase appDataBase;
    private final StimulationSessionsDao stimulationSessionsDao;
    Context context;
    List<Prescriptions> prescription;

    public RecyclerviewPrescriptionAdapter(Context context, List<Prescriptions> prescription) {
        this.context = context;
        this.prescription = prescription;
        appDataBase = AppDataBase.getInstance(context);
        stimulationSessionsDao = appDataBase.stimulationSessionsDao();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.prescription_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (prescription != null) {
            if (prescription.size() > 0) {
//                if (stimulationSessionsDao.getAll(CommonDataArea.patientID, prescription.get(position).getPrescriptionuuid()).size() > 0) {
                if (Integer.parseInt(prescription.get(position).getPrescriptionstatus())==1||stimulationSessionsDao.getAll(CommonDataArea.patientID, prescription.get(position).getPrescriptionuuid()).size() == 0) {
                    CommonDataArea.prescriptionStatus = 3;
                    holder.status.setText("Completed");
                    holder.status.setTextColor(context.getColor(R.color.green));
                    holder.progressBar.setProgressDrawable(ContextCompat.getDrawable(context, R.drawable.circular_progress_completed));

                } else if (prescription.get(position).getStatus() == 1) {
                    CommonDataArea.prescriptionStatus = prescription.get(position).getStatus();

                    holder.status.setText("Active");
                    holder.status.setTextColor(context.getColor(R.color.blue));
                    holder.progressBar.setProgressDrawable(ContextCompat.getDrawable(context, R.drawable.circular_progress_pending));

                } else if (prescription.get(position).getStatus() == 2) {
                    CommonDataArea.prescriptionStatus =prescription.get(position).getStatus();

                    holder.status.setText("Cancelled");
                    holder.status.setTextColor(context.getColor(R.color.red));
                    holder.progressBar.setProgressDrawable(ContextCompat.getDrawable(context, R.drawable.circular_progress_failed));

                }
                float a=Integer.parseInt(prescription.get(position).getNumrecords());
                float b=(stimulationSessionsDao.getAll(CommonDataArea.patientID, prescription.get(position).getPrescriptionuuid()).size());
                  float  ab = ((a - b) / a) * 100;

                float sessionCompleted = (Integer.parseInt(prescription.get(position).getNumrecords()) - (stimulationSessionsDao.getAll(CommonDataArea.patientID, prescription.get(position).getPrescriptionuuid()).size()) )/ 100;
                holder.progressBar.setProgress(Math.round(ab));
                holder.prescriptioID.setText("Prescription ID    : " + prescription.get(position).getPrescriptionuuid());
                holder.prescriptionText.setText("Prescription Note : " + prescription.get(position).getNotes());
                holder.therapistID.setText("Therapist Name        : " + prescription.get(position).getTherapistid());
                holder.numberOfrecords.setText("Number Of Records  : " + prescription.get(position).getNumrecords());
                holder.createdDate.setText("Created Date  : " + prescription.get(position).getCreatedDate());
                holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Integer.parseInt(prescription.get(position).getPrescriptionstatus())!=1&&prescription.get(position).getStatus()!=2) {
                            if(stimulationSessionsDao.getAll(CommonDataArea.patientID, prescription.get(position).getPrescriptionuuid()).size() != 0) {
                                CommonDataArea.prescriptionID = prescription.get(position).getPrescriptionuuid();
                                CommonDataArea.prescriptionStatus = prescription.get(position).getStatus();
                                Intent intent = new Intent(context, ESSessionActivity.class);
                                context.startActivity(intent);
                            }
                        }
                    }
                });
            }
            //}
        }

    }

    @Override
    public int getItemCount() {
        return prescription.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView prescriptioID, prescriptionText, therapistID, numberOfrecords, createdDate, status;
        LinearLayout linearLayout;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            prescriptioID = (TextView) itemView.findViewById(R.id.pres_id);
            status = (TextView) itemView.findViewById(R.id.session_status);
            prescriptionText = (TextView) itemView.findViewById(R.id.pres_note);
            therapistID = (TextView) itemView.findViewById(R.id.therapist_id);
            numberOfrecords = (TextView) itemView.findViewById(R.id.num_records);
            createdDate = (TextView) itemView.findViewById(R.id.pres_date);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.row_linear);
            progressBar = (ProgressBar) itemView.findViewById(R.id.session_completed);

        }
    }
}
