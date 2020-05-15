package com.assignment.maplehomeapp.connection;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ResourceBundle;
import java.util.Set;

public class BluetoothConnection {
    Context context;
    private static final int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter btAdapter;
    private SharedPreferences prefs;

    public BluetoothConnection(Context context) {
        this.context = context;
        btAdapter = BluetoothAdapter.getDefaultAdapter();
    }


    public boolean CheckBluetoothState() {
        // Checks for the Bluetooth support and then makes sure it is turned on
        // If it isn't turned on, request to turn it on
        // List paired devices
        prefs = context.getSharedPreferences(
                context.getPackageName(), Context.MODE_PRIVATE);
        if (btAdapter == null) {
            return false;
        } else {
            if (btAdapter.isEnabled()) {

                Set<BluetoothDevice> devices = btAdapter.getBondedDevices();
                for (BluetoothDevice device : devices) {
                    if (device.getAddress().equalsIgnoreCase(prefs.getString("bluetoothAddress", "")))
                        return true;

                }
            } else {
                //Prompt user to turn on Bluetooth
//                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                   context.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
        return false;
    }

}
