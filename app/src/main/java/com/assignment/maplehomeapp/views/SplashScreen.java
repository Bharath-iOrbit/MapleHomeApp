package com.assignment.maplehomeapp.views;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.assignment.maplehomeapp.MainActivity;
import com.assignment.maplehomeapp.R;
import com.assignment.maplehomeapp.connection.UartService;

public class SplashScreen extends AppCompatActivity {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private UartService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        prefs = getSharedPreferences(
                getPackageName(), Context.MODE_PRIVATE);
        editor = prefs.edit();



        new Handler().postDelayed(new Runnable() {


            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void run() {
                // This method will be executed once the timer is over

                if (!prefs.getBoolean("isLogin", false)) {
                    Intent i = new Intent(SplashScreen.this, SignupActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(SplashScreen.this, DashboardActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 2000);


    }

}
