package com.assignment.maplehomeapp.remote;

import android.os.Handler;

import com.squareup.otto.Bus;

class BusProvider {
    private static final Bus BUS = new Bus();

    public static Bus getInstance(){
        return BUS;
    }

    public BusProvider(){}
}
