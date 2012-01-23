package com.inthinc.device.emulation.utils;

import java.io.IOException;

import org.apache.log4j.Level;

import com.inthinc.device.devices.WaysmartDevice.Direction;
import com.inthinc.device.emulation.enums.DeviceEnums.WSHOSState;
import com.inthinc.device.emulation.enums.DeviceNoteTypes;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.pro.automation.utils.MasterTest;

public class Package_Waysmart_Note {


    private final CommandLine sendNote;

    /**
     * @param type
     * @param method
     * @param server
     * @param mcm
     * @param imei
     */
    public Package_Waysmart_Note(DeviceNoteTypes type, Direction method, Addresses server, String mcm, String imei) {

        sendNote = new CommandLine();
        sendNote.setWorkingDirectory("src/main/resources/waysNote");

        sendNote.setArgs("type", type.getIndex());
        sendNote.setArgs("direction", method);
        sendNote.setArgs("sat_server", server.getMCMUrl());
        sendNote.setArgs("sat_port", server.getSatPort());
        sendNote.setArgs("wifi_server", "http://" + server.getMCMUrl() + ":" + server.getWaysPort());
        sendNote.setArgs("mcm", mcm);
        sendNote.setArgs("imei", imei);
    }

    public void setLatitude(double lat) {
        sendNote.setArgs("latitude", lat);
    }

    public void setLongitude(double lng) {
        sendNote.setArgs("longitude", lng);
    }

    public void setVehicleID(String vehicleID) {
        sendNote.setArgs("vehicle_id", vehicleID);
    }

    public void setDriverID(String driverID) {
        sendNote.setArgs("driver_id", driverID);
    }

    public void setOdometer(int odometer) {
        sendNote.setArgs("odometer", odometer);
    }
    
    public void setOdometer(long odometer) {
        sendNote.setArgs("odometer", odometer);
    }
    
    public void setSpeed(int speed){
        sendNote.setArgs("speed", speed);
    }
    
    public void setTime(AutomationCalendar time){
        sendNote.setArgs("time", time.epochSecondsInt());
    }
    
    public void setLocation(String location){
        sendNote.setArgs("location", location);
    }
    
    public void setSpeedBucketDistances(int _1_30, int _31_40, int _41_54, int _55_64, int _65_80){
        String buckets = _1_30 + "," + _31_40 + "," + _41_54 + "," + _55_64 + "," + _65_80;
        sendNote.setArgs("speed_bucket_distance", buckets);
    }

    public void setAccountID(int accountID) {
        sendNote.setArgs("account_id", accountID);
    }

    public void setCompanyID(int companyID) {
        sendNote.setArgs("company_id", companyID);
    }

    public void setHosStatus(WSHOSState status) {
        sendNote.setArgs("hos_state", status.getIndex());
    }
    

    public String sendNote() {
        try {
            sendNote.runCommandWithArgs("send_note.exe");
            MasterTest.print(sendNote.getCommandError(), Level.ERROR);
            return sendNote.getCommandOutput();
        } catch (IOException e) {
        	MasterTest.print(e, Level.FATAL);
        } 
        return sendNote.getCommandError();
    }
}
