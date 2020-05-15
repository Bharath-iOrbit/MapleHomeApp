package com.assignment.maplehomeapp.model;

public class StimulationSettings {
    public String selectedElectrodes;
    public double stimCurrent;
    public int stimulationFreq;
    public int pulsePhase;
    public int fadeInOut;
    public int holdTime;
    public int delayTime;
    public int repCount;
    public StimulationSettings(){
         stimCurrent=30.0;
        stimulationFreq=50;
        pulsePhase=250;
        fadeInOut=1;
        holdTime=2;
        delayTime=2;
        repCount=10;
    }
}
