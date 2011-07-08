package com.inthinc.pro.automation.device_emulation;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.enums.Ways_SAT_EVENT;
//import com.inthinc.pro.automation.utils.CommandLine;
import com.inthinc.pro.automation.utils.StackToString;

public class Package_Waysmart_Note {

//    private final static Logger logger = Logger.getLogger(Package_Waysmart_Note.class);
//
//    private CommandLine sendNote;
//
//    public static enum Direction {
//        wifi,
//        sat
//    };
//
//    public Package_Waysmart_Note(Ways_SAT_EVENT type, Direction method, Addresses server, String mcm, String imei, Double lat, Double lng, Integer vehicleID, Integer driverID, Integer odometer,
//            Integer accountID, Integer companyID) {
//
//        sendNote = new CommandLine();
//
//        sendNote.setEnvironmentVar("type", type.getValue().toString());
//        sendNote.setEnvironmentVar("direction", method.toString());
//        sendNote.setEnvironmentVar("sat_server", server.getMCMUrl());
//        sendNote.setEnvironmentVar("sat_port", server.getSatPort().toString());
//        sendNote.setEnvironmentVar("wifi_server", "http://" + server.getMCMUrl() + ":" + server.getWaysPort());
//        sendNote.setEnvironmentVar("mcm", mcm);
//        sendNote.setEnvironmentVar("imei", imei);
//        sendNote.setEnvironmentVar("latitude", lat.toString());
//        sendNote.setEnvironmentVar("longitude", lng.toString());
//        sendNote.setEnvironmentVar("vehicle_id", vehicleID.toString());
//        sendNote.setEnvironmentVar("driver_id", driverID.toString());
//        sendNote.setEnvironmentVar("odometer", odometer.toString());
//        sendNote.setEnvironmentVar("account_id", accountID.toString());
//        sendNote.setEnvironmentVar("company_id", companyID.toString());
//        
//    }
//
//    public void sendNote() {
//        String fileName = "temp.bat";
//        try {
//            FileWriter batch = new FileWriter(fileName);
//            batch.write("waysNote\\send_note.exe "+sendNote.getEnvTokensAsString());
//            batch.close();
//            sendNote.runCommandWithArgs("waysNote\\send_note.exe");
////            sendNote.runCommand(fileName);
//            logger.info(sendNote.getCommandOutput());
//        } catch (IOException e) {
//            logger.debug(StackToString.toString(e));
//        }
//    }

}
