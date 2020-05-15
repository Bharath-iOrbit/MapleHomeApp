package com.assignment.maplehomeapp.interfaces;

import android.util.Log;

import com.assignment.maplehomeapp.connection.UartService;
import com.assignment.maplehomeapp.utils.CommonDataArea;
import com.assignment.maplehomeapp.utils.LogWriter;

import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.util.Stack;

public class MapleInterface {
    static int HEADER_LEN = 12;
    static int BLE_PACK_LEN = 20;
    static final int RESP_STIM_START = 960;
    static final int RESP_STIM_STOP= 961;
    static final int RESP_STIM_PROG = 962;
    static final int RESP_STIM_POW_CHANGE = 963;
    static final int RESP_DEVICE_INFO = 9652;

    static final int CMD_STIM_START = 60;
    static final int CMD_STIM_STOP = 61;
    static final int CMD_DEVICE_INFO = 5;
    static final int CMD_STIM_POW_CHANGE = 63; //wrong in doc
    static final int CMD_KEEP_ALIVE = 0;
    static final int CMD_KEEP_ALIVE_CHANGE = 90;

    static boolean CMD_MODE_ASCII =true;

    float stimCurrent;
    int stimFreq;
    int pulsePhase;
    int holdTime;
    int fadeTime;
    int delayTime;
    int repitionCount;
    String electrodeSttings;
    String stimulationCommand="";

    enum RecvState{
        Header,
        Payload,
        Process,
    } ;
    RecvState  recvState = RecvState.Header;

   public enum MapleInterfaceMode {
        Production,
        Simulation
    } ;
    MapleInterfaceMode interfaceMode;

    public void Init(MapleInterfaceMode mode)
    {
        interfaceMode = mode;
        if(mode ==  MapleInterfaceMode.Simulation){
            Thread simulator = new Thread(new LocalSimulator());
            simulator.start();
        }
    }

    public void setElectrodes(String electrode){
        electrodeSttings = electrode;
    }
    public void setStimulationCurrent(float curMa){
        stimCurrent = curMa;
    }
    public void setStimulationFrequency(int frequency){
        stimFreq = frequency;
    }
    public void setPulsePhaseDuration(int pulsePhase){
        this.pulsePhase = pulsePhase;
    }
    public void setHoldTime(int holdTime){
        this.holdTime =holdTime;
    }
    public void setFadeTime(int fadeTime){
        this.fadeTime = fadeTime;
    }
    public void setDelayTime(int delayTime){
        this.delayTime = delayTime;
    }
    public void setRepititionCount(int repition){
        this.repitionCount = repition;
    }
    byte[] EncodeInt32MSB(int value)
    {
        byte[] buf = new byte[4];
        buf[0] = (byte)(value >> 24 & 0xFF);
        buf[1] = (byte)(value >> 16 & 0xFF);
        buf[2] = (byte)(value >> 8 & 0xFF);
        buf[3] = (byte)(value & 0xFF);
        return buf;
    }

    int DecodeInt32(byte[] buf)
    {
        int val = (int)(buf[0] << 24);
        val = val + (int)(buf[1] << 16);
        val = val + (int)(buf[2] << 8);
        val = val + (int)(buf[3]);
        return val;
    }
    int bytesRecvdPayload=0;
    MapleHeader head;
    public void ProcessRcvdBytes(byte [] buffer){
        try {
            int lengthBuf = buffer.length;
            if ((recvState == RecvState.Header) && (lengthBuf >= HEADER_LEN) && (buffer[0] == 0xff)) {
                if ((buffer[0] == 0xff) && (buffer[1] == 0xff) && (buffer[2] == 0xff) && (buffer[3] == 0xff)) {
                    head = new MapleHeader();
                    bytesRecvdPayload = 0;

                    byte[] cmd = new byte[4];
                    System.arraycopy(buffer, 4, cmd, 0, 4);
                    head.command = DecodeInt32(cmd);

                    byte[] len = new byte[4];
                    System.arraycopy(buffer, 8, len, 0, 4);
                    head.length = DecodeInt32(cmd);

                    if (head.length == 0) {
                        ProcessRecvdCommand();
                    } else {
                        if (buffer.length > HEADER_LEN) {
                            int remLen = buffer.length - HEADER_LEN;
                            if (remLen >= head.length) {
                                remLen = head.length; //even if remLen > head.length , just read length only
                                System.arraycopy(buffer, HEADER_LEN, head.payload, 0, remLen);
                                recvState = RecvState.Process;
                                ProcessRecvdCommand();
                            } else {
                                System.arraycopy(buffer, HEADER_LEN, head.payload, 0, remLen);
                                recvState = RecvState.Payload;
                                bytesRecvdPayload += remLen;
                            }
                        } else {
                            recvState = RecvState.Payload;
                        }
                    }
                }
            } else if (recvState == RecvState.Payload) {
                int remLen = head.length - bytesRecvdPayload;
                if (remLen > buffer.length) {
                    System.arraycopy(buffer, 0, head.payload, bytesRecvdPayload, buffer.length);
                    bytesRecvdPayload += buffer.length;
                } else {
                    System.arraycopy(buffer, 0, head.payload, bytesRecvdPayload, remLen);
                    bytesRecvdPayload += remLen;
                }
                if (bytesRecvdPayload >= head.length) {
                    recvState = RecvState.Process;
                    ProcessRecvdCommand();
                }
            } else if ((recvState == RecvState.Header) && (buffer[0] != 0xff)) {
                String resp = new String(buffer);
                String[] parts = resp.split("-");
                if (parts.length >= 2) {
                    head = new MapleHeader();
                    head.command = Integer.parseInt(parts[0]);
                    head.payLoadStr = parts[1];
                    head.length = parts[1].length();
                    ProcessRecvdCommand();
                }
            }
        }catch(Exception exp){
            Log.e("Process BLE Dat",exp.getMessage());
        }
    }

    String commandBuf="";
    public void ProcessRcvdBAscii(byte [] buffer)
    {
    String commandPart = new String(buffer);
    commandBuf += commandPart;
    int loc=0;
    String toProcess="";
    while(commandBuf.length()>0) {
        loc = commandBuf.indexOf("\n");
        if (loc < 0) return;
        if (loc == 0) {
            commandBuf = commandBuf.substring(1);
            continue;
        }
        toProcess = commandBuf.substring(0, loc);
        if (commandBuf.length() > (loc + 1)) commandBuf = commandBuf.substring(loc + 1);
        else commandBuf = "";

        String[] parts = toProcess.split("#");
        if (parts.length >= 2) {
            head = new MapleHeader();
            head.command = Integer.parseInt(parts[0]);
            head.payLoadStr = parts[1];
            head.length = parts[1].length();
            ProcessRecvdCommand();
        }
    }
    }

    synchronized  void SendAsciiCommand(int commandID, String commandParams)
    {

        try {

            Thread.sleep(100);
            String commmandBufLoc = "\r";
            commmandBufLoc += commandID;
            commmandBufLoc += '#';
            commmandBufLoc += commandParams;
            commmandBufLoc += '$';

            if(interfaceMode==MapleInterfaceMode.Simulation){
                simCommandQueue.push(commmandBufLoc);
                return;
            }
            byte[] cmdbytes = commmandBufLoc.getBytes();

            int start = 0;
            int sendLen = 20, sendPtr = 0;
            int commandLen = cmdbytes.length;
            boolean headerSent = false;
            while (sendPtr < cmdbytes.length) {

                if (commandLen > BLE_PACK_LEN) sendLen = BLE_PACK_LEN;
                else sendLen = commandLen;

                byte[] max = new byte[sendLen];
                System.arraycopy(cmdbytes, sendPtr, max, 0, sendLen);
                sendPtr += sendLen;
                commandLen -= sendLen;
                Thread.sleep(100);
                CommonDataArea.uartService.writeRXCharacteristic(max);
            }
        }
        catch(Exception exp){

        }
    }
    void ProcessRecvdCommand(){
        ProcessCommand procCommand = new ProcessCommand();
        procCommand.header = head;

        new Thread(procCommand).start();


    }

    public void StopStimulationBin(){
        try {
            byte[] headarr = {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff};//, 0x00, 0x00, 0x00, 0x05, 0x00, 0x00, 0x00, 0x11 };
            byte[] cmd = EncodeInt32MSB(CMD_STIM_STOP);
            byte[] len = EncodeInt32MSB(0);


            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(headarr);
            outputStream.write(cmd);
            outputStream.write(len);


            byte command[] = outputStream.toByteArray();
            // uartService.writeRXCharacteristic(command);

            int start = 0;
            int sendLen = 20, sendPtr = 0;
            int commandLen = command.length;
            boolean headerSent = false;
            while (sendPtr < command.length) {
                if (!headerSent) //header length is 12 byte send it as one attribute for easy processing.
                {
                    byte[] header = new byte[HEADER_LEN];
                    System.arraycopy(command, 0, header, 0, HEADER_LEN);
                    sendPtr += HEADER_LEN;
                    commandLen -= HEADER_LEN;

                    Thread.sleep(10);
                    CommonDataArea.uartService.writeRXCharacteristic(header);
                    headerSent = true;
                } else {
                    if (commandLen > BLE_PACK_LEN) sendLen = BLE_PACK_LEN;
                    else sendLen = commandLen;

                    byte[] max = new byte[sendLen];
                    System.arraycopy(command, sendPtr, max, 0, sendLen);
                    sendPtr += sendLen;
                    commandLen -= sendLen;
                    Thread.sleep(10);
                    CommonDataArea.uartService.writeRXCharacteristic(max);


                }
            }
        }catch(Exception exp){

        }

    }

    public void RequestDeviceInfo(){
        if(!CMD_MODE_ASCII){
            //Implement later
        }
        else {
            SendAsciiCommand(CMD_DEVICE_INFO, "");
        }
    }
    public void SetStimCurrent(float currentVal){
        stimCurrent =currentVal;
        if(!CMD_MODE_ASCII) StopStimulationBin();
        else {
            String commandParam ="";
            commandParam+=currentVal;
            SendAsciiCommand(CMD_STIM_POW_CHANGE, commandParam);
        }
    }
    public void KeepAlive(){
        if(!CMD_MODE_ASCII) StopStimulationBin();
        else {
            SendAsciiCommand(CMD_KEEP_ALIVE, "0");
        }
    }
    public void stopStimulation(){
       if(!CMD_MODE_ASCII) StopStimulationBin();
       else
       {
           SendAsciiCommand(CMD_STIM_STOP,"0");
       }
    }
    public void startStimulationBin(){
        try {
            stimulationCommand="";
            float pulseTime = (float)(1/(float)stimFreq)*1000;
            int holdTimePulseCount =(int) (holdTime*1000/pulseTime);
            int fadeTimePulseCoune = (int) (fadeTime*1000/pulseTime);
            int delayTimePulseCoune = (int) (delayTime*1000/pulseTime);
            stimulationCommand += electrodeSttings;
            stimulationCommand += ",2,";
            stimulationCommand += stimCurrent;
            stimulationCommand += ",";
            stimulationCommand += stimFreq;
            stimulationCommand += ",";
            stimulationCommand += pulsePhase;
            stimulationCommand += ",";
            stimulationCommand += holdTimePulseCount;
            stimulationCommand += ",";
            stimulationCommand += fadeTimePulseCoune;
            stimulationCommand += ",";
            stimulationCommand += delayTimePulseCoune;
            stimulationCommand += ",";
            stimulationCommand += repitionCount;

            byte[] headarr = {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff};//, 0x00, 0x00, 0x00, 0x05, 0x00, 0x00, 0x00, 0x11 };
            byte[] cmd = EncodeInt32MSB(60);

            byte[] comdBody = stimulationCommand.getBytes();
            byte[] len = EncodeInt32MSB(comdBody.length);


            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(headarr);
            outputStream.write(cmd);
            outputStream.write(len);
            outputStream.write(comdBody);

            byte command[] = outputStream.toByteArray();
            // uartService.writeRXCharacteristic(command);

            int start =0;
            int sendLen =20,sendPtr=0;
            int commandLen = command.length;
            boolean headerSent = false;
            while(sendPtr<command.length) {
                if(!headerSent) //header length is 12 byte send it as one attribute for easy processing.
                {
                    byte [] header = new byte [HEADER_LEN];
                    System.arraycopy(command,0,header,0,HEADER_LEN);
                    sendPtr+=HEADER_LEN;
                    commandLen-=HEADER_LEN;

                    Thread.sleep(10);
                    CommonDataArea.uartService.writeRXCharacteristic(header);
                    headerSent= true;
                }else {
                    if (commandLen > BLE_PACK_LEN) sendLen = BLE_PACK_LEN;
                    else sendLen = commandLen;

                    byte[] max = new byte[sendLen];
                    System.arraycopy(command, sendPtr, max, 0, sendLen);
                    sendPtr += sendLen;
                    commandLen -= sendLen;
                    Thread.sleep(10);
                    CommonDataArea.uartService.writeRXCharacteristic(max);


                }
            }

            if(CommonDataArea.DEBUG_MODE){
                CommonDataArea.status = CommonDataArea.StimStatus.Running;
            }
            lastStausMesg = System.currentTimeMillis();
            Thread statusMon = new Thread(new StimulationStatusMonitor());
            statusMon.start();


        }catch(Exception exp){
            Log.e("BLE Exception",exp.getMessage());
        }

    }
    public void startStimulation(){
        if(!CMD_MODE_ASCII) startStimulationBin();
        else
        {
            stimulationCommand="";
            float pulseTime = (float)(1/(float)stimFreq)*1000;
            int holdTimePulseCount =(int) (holdTime*1000/pulseTime);
            int fadeTimePulseCoune = (int) (fadeTime*1000/pulseTime);
            int delayTimePulseCoune = (int) (delayTime*1000/pulseTime);
            stimulationCommand += electrodeSttings;
            stimulationCommand += ",2,";
            stimulationCommand += stimCurrent;
            stimulationCommand += ",";
            stimulationCommand += stimFreq;
            stimulationCommand += ",";
            stimulationCommand += pulsePhase;
            stimulationCommand += ",";
            stimulationCommand += holdTimePulseCount;
            stimulationCommand += ",";
            stimulationCommand += fadeTimePulseCoune;
            stimulationCommand += ",";
            stimulationCommand += delayTimePulseCoune;
            stimulationCommand += ",";
            stimulationCommand += repitionCount;
            SendAsciiCommand(CMD_STIM_START,stimulationCommand);
        }
    }

    class SimulateStimulationStates implements Runnable
    {
        public MapleHeader header;
        @Override
        public void run() {
            //Merly add event firing code here
            String command ="960,1";
            //Fireevent with command string
            float phaseTime = pulsePhase/1000;
            for(int i=0;i<repitionCount;++i){
                try {
                    command = "962," + repitionCount + ",1";
                    //Fireevent with command string
                    Thread.sleep((int) phaseTime * fadeTime);
                    command = "962," + repitionCount + ",2";
                    //Fireevent with command string
                    Thread.sleep((int) phaseTime * holdTime);
                    command = "962," + repitionCount + ",3";
                    //Fireevent with command string
                    Thread.sleep((int) phaseTime * fadeTime);
                    command = "962," + repitionCount + ",4";
                    //Fireevent with command string
                    Thread.sleep((int) phaseTime * delayTime);
                }catch(Exception exp){

                }

            }
        }
    }

    long lastStausMesg;
    class StimulationStatusMonitor implements Runnable
    {
        public MapleHeader header;
        @Override
        public void run() {
            try {
                while (true) {
                    long curTime = System.currentTimeMillis();
                    if ((curTime - lastStausMesg) > 5000) {
                       // CommonDataArea.status = CommonDataArea.StimStatus.Stopped;
                        return;
                    }
                    Thread.sleep(1000);
                }
            }catch(Exception exp){

            }
        }

    }
    //Event String format
    //CMD,param,....paramn\r\n;
    //CMD StimulationProgress 962,CycleNum,CycleState
    ////state 1 - Fade IN Start
    ////state 2 - Hold Start
    ////state 3 - Fade Out-Start
    ////state 4 - Delay
    ////state 5 - Cycle Completed
    //CMD StimulationStart 960,status
    //status 0 - OK
    //-30 = battery empty (≤ 5%)
    //-31 = connected to mains (measurement or
    //stimulation is not allowed when the Maple
    //instrument is connected to mains)
    //-34 = probe not connected
    //-36 = OCP/OVP error
    //CMD StimulationStop 961,status
    //status 0 - OK
    //-30 = battery empty (≤ 5%)
    //-31 = connected to mains (measurement or
    //stimulation is not allowed when the Maple
    //instrument is connected to mains)
    //-34 = probe not connected
    //-36 = OCP/OVP error
    class ProcessCommand implements Runnable
    {
        public MapleHeader header;
        @Override
        public void run() {
            try {
                lastStausMesg = System.currentTimeMillis();
                //Merly add event firing code here

                String[] tockens = null;
                String commandRecv = "Command =" + header.command;
                if (header.length > 0) {
                    commandRecv += ("-" + header.payLoadStr);
                }
                LogWriter.writeLog("CommandRecvd", commandRecv);
                Log.i("CommandRecvd", commandRecv);
                if ((header.payLoadStr != null) && (header.payLoadStr.length() > 0)) {
                    tockens = header.payLoadStr.split(",");
                }
                switch (header.command) {
                    //CMD -<CMD>-<PendCycles>,<StimState>,<SetStimCurrent>,<MeasuredStimVoltage>,<MeasuredStimCurrent>,<batPercent>
                    //StimState :
                    //state 1 - Fade IN Start
                    //state 2 - Hold Start
                    //state 3 - Fade Out-Start
                    //state 4 - Delay
                    //state 5 - Cycle Completed
                    //SetStimCurrent : DAC value for current control
                    //MeasuredStimVoltage : Stimulation Volage measured using ADC
                    //MeasuredStimCurrent : Stimulation current measured
                    case RESP_STIM_PROG:
                        if ((tockens != null) && tockens.length >= 6) {
                            int cyclesPending = Integer.parseInt(tockens[0]);
                            CommonDataArea.remStimCycles = cyclesPending;
                            int state = Integer.parseInt(tockens[1]);
                            int current = Integer.parseInt(tockens[2]);
                            CommonDataArea.curDacVal = current;
                            CommonDataArea.measuredStimVolt = Float.parseFloat(tockens[3]);
                            CommonDataArea.measuredStimCurrent = Float.parseFloat(tockens[4]);
                            String bat = tockens[5].trim();
                            CommonDataArea.batteryPercent = Integer.parseInt(bat);

                            if (state == 1) {
                                CommonDataArea.state = CommonDataArea.StimState.Onset;
                            } else if (state == 2) {
                                CommonDataArea.state = CommonDataArea.StimState.Hold;
                            } else if (state == 3) {
                                CommonDataArea.state = CommonDataArea.StimState.Offset;
                            } else if (state == 4) {
                                CommonDataArea.state = CommonDataArea.StimState.Delay;
                            } else if (state == 5) {
                                CommonDataArea.state = CommonDataArea.StimState.off;
                            }
                        }
                       // KeepAlive();
                        break;
                    case RESP_STIM_START:
                        CommonDataArea.status = CommonDataArea.StimStatus.Running;
                        break;
                    case RESP_STIM_STOP:
                        CommonDataArea.status = CommonDataArea.StimStatus.Stopped;
                        break;
                    //<CR><CMD>#<MAC>,<Status><FirmwareVer>,<ServeName>,<HardwareVer>$
                    case  RESP_DEVICE_INFO:
                        if ((tockens != null) && tockens.length >= 5) {
                            CommonDataArea.deviceMacID =  tockens[0];
                            int state = Integer.parseInt(tockens[1]);
                            CommonDataArea.deviceFirmwareVer = tockens[2];
                            CommonDataArea.deviceName = tockens[3];
                            CommonDataArea.deviceHardwareVer = tockens[4];
                        }
                        break;
                }
            }catch(Exception exp){
                LogWriter.writeLog("ProcessingDevCmd",exp.getMessage());
            }
        }
    }

    enum SimulationStatus
    {
        idle,
        measurement,
        stimulation
    }
    SimulationStatus simulationStatus;
    Stack<String> simCommandQueue;
    //This class simulates measrement and stimulation responses expected from the
    //app
    class LocalSimulator implements Runnable {
        int cmdi;
        @Override
        public void run() {
            simCommandQueue = new Stack<String>();
            while(true) {
                try {
                    ParseExecCommand();
                    if (simulationStatus == SimulationStatus.stimulation) {
                        SimulateStimulationStatus();
                    }
                    Thread.sleep(100);
                } catch (Exception exp) {

                }
            }
        }
        void ParseExecCommand(){
            if(!simCommandQueue.isEmpty()){
                byte [] cmdBuf;
                String cmd = simCommandQueue.pop();
                String [] tockens = cmd.split("#");
                if((tockens==null)||(tockens.length==0)) return;
                String cmdPart = tockens[0].trim();
                cmdi = Integer.parseInt(cmdPart);
                switch(cmdi)
                {
                    case  CMD_STIM_START:
                        stimState = CommonDataArea.StimState.Onset;
                        remCyclesSim = repitionCount;
                        fadeInSim = fadeTime;
                        holdTimeSim=fadeTime+holdTime;
                        fadeOutTimeSim=holdTimeSim+fadeTime;
                        delayTimeSim = fadeOutTimeSim+delayTime;
                        fadeStepsSim = fadeTime*10;
                        deltaCurSim= (int)(((float)((float)stimCurrent/30f))*4096);
                        deltaCurSim= deltaCurSim/fadeStepsSim;
                        simulationStatus= SimulationStatus.stimulation;
                        cmd= RESP_STIM_START+ "#1\n";
                        cmdBuf = cmd.getBytes();
                        ProcessRcvdBAscii(cmdBuf);
                        break;
                    case  CMD_STIM_POW_CHANGE:
                        deltaCurSim= (int)(((float)((float)stimCurrent/30f))*4096);
                        deltaCurSim= deltaCurSim/fadeStepsSim;
                        break;
                    case  CMD_STIM_STOP:
                        simulationStatus= SimulationStatus.idle;
                        cmd= RESP_STIM_STOP+ "#1\n";
                        cmdBuf = cmd.getBytes();
                        ProcessRcvdBAscii(cmdBuf);
                        break;
                    //<CR><CMD>#<MAC>,<Status><FirmwareVer>,<ServeName>,<HardwareVer>$
                    case  CMD_DEVICE_INFO:
                        cmd= RESP_DEVICE_INFO+ "#00:00:00:a1:2b:cc,1,1.0.1,MapleHome-1,1.1\n";
                        cmdBuf = cmd.getBytes();
                        ProcessRcvdBAscii(cmdBuf);
                        break;
                }
            }
        }
        int stimulationTimer =0;
        int stimulationTimerSec;
        CommonDataArea.StimState stimState;
        int stimCurrentSim;
        int remCyclesSim;
        int fadeInSim, holdTimeSim, fadeOutTimeSim, delayTimeSim;
        int fadeStepsSim ;
        int deltaCurSim;
        int stepsSim =0;
        //CMD -<CMD>-<PendCycles>,<StimState>,<SetStimCurrent>,<MeasuredStimVoltage>,<MeasuredStimCurrent>,<batPercent>
        //StimState :
        //state 1 - Fade IN Start
        //state 2 - Hold Start
        //state 3 - Fade Out-Start
        //state 4 - Delay
        //state 5 - Cycle Completed
        //SetStimCurrent : DAC value for current control
        //MeasuredStimVoltage : Stimulation Volage measured using ADC
        //MeasuredStimCurrent : Stimulation current measured
        void SimulateStimulationStatus(){
            ++stimulationTimer;
            byte [] cmdBuf;
            String cmd="";
            deltaCurSim= (int)(((float)((float)stimCurrent/30f))*4096);
            deltaCurSim= deltaCurSim/fadeStepsSim;

            stimulationTimerSec = stimulationTimer/10;
            if(stimulationTimerSec<fadeInSim){
                stimCurrentSim=deltaCurSim*stepsSim;
                cmd =RESP_STIM_PROG+ "#"+remCyclesSim+",1,"+ stimCurrentSim+",10,10,10";
                ++stepsSim;
            }else if(stimulationTimerSec<holdTimeSim){
                stepsSim=fadeStepsSim;
                stimCurrentSim=deltaCurSim*stepsSim;
                cmd = RESP_STIM_PROG+"#"+remCyclesSim+",2,"+ stimCurrentSim+",10,10,10";
            }else if(stimulationTimerSec<fadeOutTimeSim){
                stimCurrentSim=deltaCurSim*stepsSim;
                cmd = RESP_STIM_PROG+"#"+remCyclesSim+",3,"+ stimCurrentSim+",10,10,10";
                --stepsSim;
            }else if(stimulationTimerSec<delayTimeSim){
                stimCurrentSim=0;
                cmd = RESP_STIM_PROG+"#"+remCyclesSim+",4,"+ stimCurrentSim+",10,10,10";
            }else if(stimulationTimerSec>delayTimeSim) {
                stimulationTimer=0;
                stepsSim=0;
                remCyclesSim--;
                if(remCyclesSim==0){
                    cmd = RESP_STIM_STOP+"#0";
                }
            }
            cmd+="\n";
            cmdBuf = cmd.getBytes();
            ProcessRcvdBAscii(cmdBuf);
        }
    }
}
