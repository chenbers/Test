package com.inthinc.pro.automation.device_emulation;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.inthinc.hos.model.HOSStatus;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.enums.Ways_SAT_EVENT;
import com.inthinc.pro.automation.utils.CommandLine;
import com.inthinc.pro.automation.utils.StackToString;

public class Package_Waysmart_Note implements NoteBuilder {

    private final static Logger logger = Logger.getLogger(Package_Waysmart_Note.class);

    private CommandLine sendNote;

    public static enum Direction {
        wifi,
        sat
    };

    /**
     * @param type
     * @param method
     * @param server
     * @param mcm
     * @param imei
     */
    public Package_Waysmart_Note(Ways_SAT_EVENT type, Direction method, Addresses server, String mcm, String imei) {

        sendNote = new CommandLine();
        sendNote.setWorkingDirectory("src/main/resources/waysNote");

        sendNote.setArgs("type", type.getValue());
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

    public void setVehicleID(int vehicleID) {
        sendNote.setArgs("vehicle_id", vehicleID);
    }

    public void setDriverID(int driverID) {
        sendNote.setArgs("driver_id", driverID);
    }

    public void setOdometer(int odometer) {
        sendNote.setArgs("odometer", odometer);
    }
    
    public void setSpeed(int speed){
        sendNote.setArgs("speed", speed);
    }
    
    public void setTime(long time){
        sendNote.setArgs("time", time);
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

    public void setHosStatus(HOSStatus status) {
        sendNote.setArgs("hos_state", status.getCode());
    }
    
    

    public void sendNote() {
        try {

            sendNote.runCommandWithArgs("send_note.exe");
            logger.info(sendNote.getCommandOutput());
            logger.error(sendNote.getCommandError());
        } catch (IOException e) {
            logger.info(StackToString.toString(e));
        }
    }

    @Override
    public byte[] Package() {
        // TODO Auto-generated method stub
        return null;
    }

}
