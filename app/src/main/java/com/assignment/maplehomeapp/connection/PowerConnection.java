package com.assignment.maplehomeapp.connection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PowerConnection {
    int level = 20;

    public int batteryLevel() {
        class BatteryBroadcastReceiver extends BroadcastReceiver {
            private final static String BATTERY_LEVEL = "level";

            @Override
            public void onReceive(Context context, Intent intent) {
                level = intent.getIntExtra(BATTERY_LEVEL, 0);

            }
        }
        return level;
    }
}
