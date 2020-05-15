package com.assignment.maplehomeapp.utils;

import android.os.Parcel;

public class Parcelable implements android.os.Parcelable {
  public   String date;
    public String duration;
    public String pulse_width;
    public String current;
    public String cycles;
    public String electrode_cnf;
    public String session_id;
    public String status;
    public String holdTime;
    public String delayTime;
    public String fadeTime;
    public String repCount;
    public String frequency;


    public Parcelable(String date, String duration, String current, String session_id, String status, String perCompleted) {
        this.date = date;
        this.duration = duration;
        this.current = current;
        this.session_id = session_id;
        this.status = status;
        this.perCompleted = perCompleted;
    }

    public String perCompleted;

    public Parcelable(String date, String status,String duration, String pulse_width, String current, String cycles, String electrode_cnf,String session_id) {
        this.date = date;
        this.duration = duration;
        this.pulse_width = pulse_width;
        this.current = current;
        this.cycles = cycles;
        this.electrode_cnf = electrode_cnf;
        this.session_id = session_id;
        this.status=status;
    }


    public Parcelable(String sessionId,String electrodes, String stim_frequency,String stim_Current, String phase_duration, String holdTime, String delayTime, String fadeTime,String repCount) {
        this.session_id = sessionId;
        this.electrode_cnf = electrodes;
        this.frequency = stim_frequency;
        this.current = stim_Current;
        this.duration = phase_duration;
        this.holdTime = holdTime;
        this.delayTime = delayTime;
        this.fadeTime = fadeTime;
        this.repCount = repCount;

    }

    protected Parcelable(Parcel in) {
        date = in.readString();
        duration = in.readString();
        pulse_width = in.readString();
        current = in.readString();
        cycles = in.readString();
        electrode_cnf = in.readString();
        session_id = in.readString();
        perCompleted=in.readString();
        status=in.readString();
        frequency=in.readString();
        current=in.readString();
        duration=in.readString();
        holdTime=in.readString();
        delayTime=in.readString();
        fadeTime=in.readString();
        repCount=in.readString();

    }

    public static final Creator<Parcelable> CREATOR = new Creator<Parcelable>() {
        @Override
        public Parcelable createFromParcel(Parcel in) {
            return new Parcelable(in);
        }

        @Override
        public Parcelable[] newArray(int size) {
            return new Parcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(duration);
        dest.writeString(pulse_width);
        dest.writeString(current);
        dest.writeString(cycles);
        dest.writeString(electrode_cnf);
        dest.writeString(session_id);
        dest.writeString(perCompleted);
        dest.writeString(status);
        dest.writeString(frequency);
        dest.writeString(current);
        dest.writeString(duration);
        dest.writeString(holdTime);
        dest.writeString(delayTime);
        dest.writeString(fadeTime);
        dest.writeString(repCount);
    }
}
