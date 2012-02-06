package com.inthinc.device.devices;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import android.util.Log;

import com.caucho.hessian.client.HessianRuntimeException;
import com.inthinc.device.emulation.enums.DeviceEnums.FwdCmdStatus;
import com.inthinc.device.emulation.enums.DeviceForwardCommands;
import com.inthinc.device.emulation.enums.DeviceNoteTypes;
import com.inthinc.device.emulation.enums.DeviceProps;
import com.inthinc.device.emulation.enums.EventAttr;
import com.inthinc.device.emulation.enums.MapSection;
import com.inthinc.device.emulation.notes.TiwiNote;
import com.inthinc.device.emulation.utils.AutomationFileHandler;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.emulation.utils.GeoPoint.Heading;
import com.inthinc.device.emulation.utils.MCMProxyObject;
import com.inthinc.device.objects.AutomationDeviceEvents;
import com.inthinc.device.objects.ZoneManager;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.enums.ProductType;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.pro.automation.utils.SHA1Checksum;

public class TiwiProDevice extends DeviceBase {

    private final static Logger logger = Logger.getLogger(TiwiProDevice.class);

    private AutomationCalendar trip_start, trip_stop;

    private ZoneManager zones;

    private int baseVer = 6;

    public TiwiProDevice(String IMEI) {
        this(IMEI, Addresses.QA);
    }

    public TiwiProDevice(String IMEI, Addresses server) {
        this(IMEI, server, DeviceProps.getTiwiDefaults());
    }

    public TiwiProDevice(String IMEI, Addresses server,
            Map<DeviceProps, String> map) {
        super(IMEI, ProductType.TIWIPRO_R74, map, server);
    }

    @Override
    protected void ackFwdCmds(String[] reply) {
        throw new IllegalAccessError("This is only for Waysmarts");
    }


    public boolean checkSbsEdit(int fileHash, int currentVersion) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("b", baseVer);
        map.put("f", fileHash);
        map.put("cv", currentVersion);
        list.add(map);
        List<Map<String, Object>> reply = mcmProxy.checkSbsEdit(
                state.getImei(), list);
        logger.debug(reply);

        return false;
    }

    public boolean checkSbsSubscribed() {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("b", baseVer);

            List<Map<String, Object>> reply = mcmProxy.checkSbsSubscribed(
                    state.getImei(), map);
            for (Map<String, Object> section : reply) {
                try {
                    int f = (Integer) section.get("f");
                    int v = (Integer) section.get("v");
                    MapSection mapSection = new MapSection(getSbsBase(f));
                    if (v != 0) {
                        mapSection.setEdit(getSbsEdit(f, 0, v));
                    }
                } catch (HessianRuntimeException e) {
                    continue;
                }
            }
        } catch (Exception e) {
            Log.d("%s", e);
            return false;
        }

        return true;
    }


    @Override
    public TiwiProDevice createAckNote(Map<String, Object> reply) {
    	Log.i("Forward Command from Server: " + reply);
        if (((Integer) reply.get("fwdID")) > 100) {
            TiwiNote ackNote = new TiwiNote(
                    DeviceNoteTypes.STRIPPED_ACKNOWLEDGE_ID_WITH_DATA);
            ackNote.addAttr(EventAttr.FWDCMD_ID, (Integer) reply.get("fwdID"));
            ackNote.addAttr(EventAttr.FWDCMD_STATUS,
                    FwdCmdStatus.FWDCMD_RECEIVED);
            notes.addNote(ackNote);
            addNote(ackNote);
            Log.d("%s", ackNote);
        }
        processCommand(reply);
        return this;
    }

    public TiwiProDevice enter_zone(Integer zoneID) {
    	state.setZoneID(zoneID);
    	addEvent(AutomationDeviceEvents.enterZone(state, tripTracker.currentLocation()));
        return this;
    }

    public boolean firmwareCompare(int versionNumber) {
        updateFirmware(versionNumber);
        String hessian = SHA1Checksum.getSHA1Checksum(lastDownload);
        getFirmwareFromSVN(versionNumber);
        String svn = SHA1Checksum.getSHA1Checksum(lastDownload);
        Log.d("%s", hessian);
        Log.d("%s", svn);
        return hessian.equals(svn);
    }

    @Override
    protected Integer get_note_count() {
        return Integer.parseInt(state
                .get_setting(DeviceProps.TIWI_SET_MSGS_PER_NOTIFICATION));
    }

    public String get_setting(DeviceProps settingID) {
        return state.get_setting(settingID);
    }

    public Integer get_setting_int(DeviceProps settingID) {
        Double valueD = Double.parseDouble(state.get_setting(settingID));
        Integer valueI = valueD.intValue();
        return valueI;
    }

    public int getBaseVer() {
        return baseVer;
    }

    public boolean getFirmwareFromSVN(int versionNumber) {
        try {
            String svnUrl = "https://svn.iwiglobal.com/iwi/release/tiwi/pro/wmp";
            if (state.getProductVersion().equals(ProductType.TIWIPRO_R74)) {
                svnUrl += "R74/";
            } else {
                svnUrl += "/";
            }
            String directory = "target/test/resources/" + state.getImei()
                    + "/downloads/";
            String fileName = state.getProductVersion().name().toLowerCase()
                    .replace("_", ".")
                    + "." + versionNumber + "-svn.dwl";
            File file = new File(directory);
            file.mkdirs();
            file = new File(lastDownload = directory + fileName);
            file.createNewFile();
            return AutomationFileHandler.downloadSvnDirectory(svnUrl,
                    fileName.replace("-svn", ""), file);
        } catch (IOException e) {
            Log.e("%s", e);
        }
        return false;
    }

    public ZoneManager getLoadedZones() {
        return zones;
    }

    public Map<String, Object> getSbsBase(int fileHash) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("f", fileHash);
        map.put("b", baseVer);
        Map<String, Object> reply = mcmProxy.getSbsBase(state.getImei(), map);
        return reply;
    }

    public Map<String, Object> getSbsEdit(int fileHash, int currentVersion,
            int newVersion) {
        if (currentVersion == newVersion) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("b", baseVer);
        map.put("f", fileHash);
        map.put("cv", currentVersion);
        map.put("nv", newVersion);

        Map<String, Object> reply = mcmProxy.getSbsEdit(state.getImei(), map);
        return reply;
    }

    public boolean getZones() {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("hardwareVersion", state.getProductVersion().getIndex());
            map.put("fileVersion", 1);
            map.put("formatVersion", 1);
            Map<String, Object> reply = mcmProxy.zoneUpdate(state.getImei(),
                    map);
//            zones = new ZoneManager((byte[]) reply.get("f"));
        } catch (Exception e) {
        	Log.e("%s", e);
            return false;
        }

        return true;
    }

    public TiwiProDevice leave_zone(Integer zoneID) {
    	state.setZoneID(zoneID);
    	addEvent(AutomationDeviceEvents.leaveZone(state, tripTracker.currentLocation()));
        return this;
    }
    

    public TiwiProDevice logout_driver(Integer RFID, Integer tripQuality,
            Integer MPG, Integer MPGOdometer) {
    	AutomationDeviceEvents.logout(this);
        return this;
    }

    public TiwiProDevice nonTripNote(AutomationCalendar time, int sats,
            Heading heading, GeoPoint location, int speed, int odometer) {
        tripTracker.fakeLocationNote(location, time, sats, heading, speed,
                odometer);
        return this;
    }

    @Override
    protected Integer processCommand(Map<String, Object> reply) {
        logger.debug(reply);
        if (reply.get("cmd") == null)
            return 1;
        DeviceForwardCommands fwdCmd = (DeviceForwardCommands) reply.get("cmd");
        if (fwdCmd.equals(DeviceForwardCommands.NO_COMMAND)) {
            return 1;
        }
        HashMap<DeviceProps, String> changes = new HashMap<DeviceProps, String>();
        TiwiNote ackNote = new TiwiNote(
                DeviceNoteTypes.STRIPPED_ACKNOWLEDGE_ID_WITH_DATA);

        if (fwdCmd == DeviceForwardCommands.ASSIGN_DRIVER) {
            String[] values = reply.get("data").toString().split(" ");
            state.setWMP(Integer.parseInt(values[0]));
        } else if (fwdCmd == DeviceForwardCommands.DUMP_CONFIGURATION)
            dump_settings();
        else if (fwdCmd == DeviceForwardCommands.CALL_REQ_SET)
            requestSettings();
        else if (fwdCmd == DeviceForwardCommands.DOWNLOAD_NEW_WITNESSII_FIRMWARE)
            set_MSP(reply.get("data"));
        else if (fwdCmd == DeviceForwardCommands.DOWNLOAD_NEW_FIRMWARE)
            state.setWMP((Integer) reply.get("data"));
        else if (fwdCmd == DeviceForwardCommands.SET_SPEED_BUFFER_VALUES) {
            changes.put(DeviceProps.TIWI_VARIABLE_SPEED_LIMITS,
                    reply.get("fwdData").toString());
        } else if (fwdCmd == DeviceForwardCommands.DOWNLOAD_NEW_MAPS) {
            // checkSbsSubscribed();
        }

        if (reply.get("fwdID").equals(100) || reply.get("fwdID").equals(1))
            return 1;

        ackNote.addAttr(EventAttr.FWDCMD_ID, reply.get("fwdID"));
        ackNote.addAttr(EventAttr.FWDCMD_STATUS,
                FwdCmdStatus.FWDCMD_FLASH_SUCCESS);
        Log.d("%s", ackNote);
        notes.addNote(ackNote);
        // check_queue();

        if (!changes.isEmpty())
            set_settings(changes);
        return 1;
    }

    protected TiwiProDevice set_ignition(Integer time_delta) {
        state.setIgnition_state(!state.getIgnition_state());
        state.incrementTime(time_delta);

        if (state.getIgnition_state()) {
            AutomationDeviceEvents.ignitionOn(this);
            trip_start = state.getTime().copy();
        } else {
            trip_stop = state.getTime().copy();
            Long tripTime = trip_stop.getDelta(trip_start) / 1000;
            state.setTripDuration(tripTime);
            AutomationDeviceEvents.ignitionOff(this);
        }
        return this;
    }

    @Override
    protected TiwiProDevice set_power() {

        state.setPower_state(!state.getPower_state()); // Change the power state between on and off
        if (state.getPower_state()) {
        	AutomationDeviceEvents.powerOn(this);
        } else if (!state.getPower_state()) {
        	state.setLowPowerTimeout(get_setting_int(DeviceProps.TIWI_LOW_POWER_MODE_SECONDS));
        	AutomationDeviceEvents.powerOff(this);
            flushNotes();
        }
        return this;
    }

    @Override
    protected TiwiProDevice set_server(Addresses server) {
        mcmProxy = new MCMProxyObject(server);
        state.setSetting(DeviceProps.TIWI_SERVER_PORT, server.getMCMPort()
                .toString());
        state.setSetting(DeviceProps.TIWI_SERVER_URL, server.getMCMUrl());
        return this;
    }

    public TiwiProDevice set_settings(DeviceProps key, String value) {
        set_settings(key, value);
        return this;
    }

    @Override
    public TiwiProDevice set_speed_limit(Integer limit) {
        state.setSetting(DeviceProps.TIWI_SPEED_LIMIT, limit.toString());
        return this;
    }

    public void setBaseVer(int baseVer) {
        this.baseVer = baseVer;
    }

    public TiwiProDevice tampering(Integer timeDelta) {
        state.setPower_state(false);
        state.setIgnition_state(false);
        increment_time(timeDelta);
        AutomationDeviceEvents.tampering(this);

        power_on_device(state.getTime());
        turn_key_on(10);
        return this;
    }

    protected HashMap<DeviceProps, String> theirsToOurs(HashMap<?, ?> reply) {
        HashMap<DeviceProps, String> map = new HashMap<DeviceProps, String>();
        Iterator<?> itr = reply.keySet().iterator();
        while (itr.hasNext()) {
            Integer next = (Integer) itr.next();
            String value = reply.get(next).toString();
            map.put(DeviceProps.valueOf(next), (String) value);
        }
        return null;
    }

    public boolean updateFirmware(int versionNumber) {
        Map<String, Object> updateMap = new HashMap<String, Object>();
        updateMap.put("hardwareVersion", state.getWMP());
        updateMap.put("fileVersion", versionNumber);
        updateMap.put("productVersion", state.getProductVersion().getIndex());
        Map<String, Object> reply = mcmProxy.tiwiproUpdate(state.getImei(),
                updateMap);
        String fileName = state.getProductVersion().name().toLowerCase()
                .replace("_", ".")
                + "." + versionNumber + "-hessian.dwl";
        return writeTiwiFile(fileName, reply);
    }
}