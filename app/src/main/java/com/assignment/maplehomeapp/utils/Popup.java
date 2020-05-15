package com.assignment.maplehomeapp.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.assignment.maplehomeapp.MainActivity;
import com.assignment.maplehomeapp.R;
import com.assignment.maplehomeapp.interfaces.StimulationSessionsDao;
import com.assignment.maplehomeapp.model.database.AppDataBase;
import com.assignment.maplehomeapp.views.DashboardActivity;
import com.assignment.maplehomeapp.views.LoginActivity;
import com.assignment.maplehomeapp.views.SignupActivity;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class Popup {
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    public Popup(Context context) {
        this.context = context;
    }

    public void logout(final Activity activity) {
         final AppDataBase appDataBase= AppDataBase.getInstance(context);
         final StimulationSessionsDao stimulationSessionsDao= appDataBase.stimulationSessionsDao();

        sharedPreferences = context.getSharedPreferences(
                context.getPackageName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Logout");
        alertDialogBuilder.setMessage("Do you want to logout");
        alertDialogBuilder.setNegativeButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        editor.clear();
                        editor.apply();
                        stimulationSessionsDao.dropDetailEntryTable();
                        stimulationSessionsDao.stimulationHistoryTable();
                        Intent intent1 = new Intent(context, SignupActivity.class);
                        context.startActivity(intent1);
                        activity.finish();
                    }
                });
        alertDialogBuilder.setPositiveButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public AlertDialog connectDevice(String message, Activity activity) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        return alertDialog;
    }

    public void singleChoice()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Please check your internet connection")
                .setTitle("Connection")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();


    }


}
