package com.inthinc.pro.automation.objects;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.caucho.hessian.client.HessianRuntimeException;
import com.inthinc.pro.automation.deviceEnums.DeviceAttrs;
import com.inthinc.pro.automation.deviceEnums.DeviceForwardCommands;
import com.inthinc.pro.automation.deviceEnums.DeviceNoteTypes;
import com.inthinc.pro.automation.deviceEnums.DeviceProps;
import com.inthinc.pro.automation.deviceEnums.Heading;
import com.inthinc.pro.automation.deviceEnums.TiwiGenerals.FwdCmdStatus;
import com.inthinc.pro.automation.device_emulation.DeviceBase;
import com.inthinc.pro.automation.device_emulation.NoteManager.DeviceNote;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.models.DeviceAttributes;
import com.inthinc.pro.automation.models.GeoPoint;
import com.inthinc.pro.automation.models.MCMProxyObject;
import com.inthinc.pro.automation.models.MapSection;
import com.inthinc.pro.automation.models.TiwiNote;
import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.automation.utils.AutomationFileHandler;
import com.inthinc.pro.automation.utils.MasterTest;
import com.inthinc.pro.automation.utils.SHA1Checksum;
import com.inthinc.pro.automation.utils.StackToString;
import com.inthinc.pro.model.configurator.ProductType;

public class TiwiProDevice extends DeviceBase {

    private final static Logger logger = Logger.getLogger(TiwiProDevice.class);

    private DeviceAttributes attrs;
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
        super(IMEI, server, map, ProductType.TIWIPRO_R74);
    }

    @Override
    protected void ackFwdCmds(String[] reply) {
        throw new IllegalAccessError("This is only for Waysmarts");
    }

    public TiwiProDevice add_lowBattery() {
        attrs = new DeviceAttributes();
        attrs.addAttribute(
                DeviceAttrs.PERCENTAGE_OF_POINTS_THAT_PASSED_THE_FILTER_, 0);
        construct_note(DeviceNoteTypes.LOW_BATTERY, attrs);
        return this;
    }

    public TiwiProDevice add_noDriver() {
        construct_note(DeviceNoteTypes.NO_DRIVER);
        return this;
    }

    public TiwiProDevice add_stats() {
        attrs = new DeviceAttributes();
        attrs.addAttribute(DeviceAttrs.BASE_VER, 0);
        attrs.addAttribute(DeviceAttrs.EMU_HASH_1, -1517168504);
        attrs.addAttribute(DeviceAttrs.EMU_HASH_2, 154129909);
        attrs.addAttribute(DeviceAttrs.EMU_HASH_3, 1825195881);
        attrs.addAttribute(DeviceAttrs.EMU_HASH_4, 1627500918);
        attrs.addAttribute(DeviceAttrs.TOTAL_AGPS_BYTES, 60000);
        construct_note(DeviceNoteTypes.STATS, attrs);
        return this;
    }

    public TiwiProDevice addIdlingNote(int lowIdleTime, int highIdleTime) {
        attrs = new DeviceAttributes();
        attrs.addAttribute(DeviceAttrs.LOW_IDLE, lowIdleTime);
        attrs.addAttribute(DeviceAttrs.HIGH_IDLE, highIdleTime);

        construct_note(DeviceNoteTypes.IDLING, attrs);
        increment_time(lowIdleTime + highIdleTime);
        return this;
    }

    public void addIgnitionOffNote(int tripDuration,
            int percentPointsPassedFilter) {
        attrs = new DeviceAttributes();
        attrs.addAttribute(DeviceAttrs.TRIP_DURATION, tripDuration);
        attrs.addAttribute(
                DeviceAttrs.PERCENTAGE_OF_POINTS_THAT_PASSED_THE_FILTER_,
                percentPointsPassedFilter);
        construct_note(DeviceNoteTypes.IGNITION_OFF, attrs);
    }

    public void addIgnitionOnNote() {
        construct_note(DeviceNoteTypes.IGNITION_ON);
    }


    public void addPowerOffNote(int lowPowerModeSeconds) {
        attrs.addAttribute(DeviceAttrs.LOW_POWER_MODE_TIMEOUT,
                lowPowerModeSeconds);
        construct_note(DeviceNoteTypes.LOW_POWER_MODE, attrs);
        flushNotes();
    }

    public TiwiProDevice addPowerOnNote(int WMP, int MSP, int gpsLockTime) {
        attrs.addAttribute(DeviceAttrs.FIRMWARE_VERSION, WMP);
        attrs.addAttribute(DeviceAttrs.DMM_VERSION, MSP);
        attrs.addAttribute(DeviceAttrs.GPS_LOCK_TIME, gpsLockTime);
        construct_note(DeviceNoteTypes.POWER_ON, attrs);
        return this;
    }



    public void addTamperingNote(int percentPassedFilter) {
        attrs = new DeviceAttributes();
        attrs.addAttribute(
                DeviceAttrs.PERCENTAGE_OF_POINTS_THAT_PASSED_THE_FILTER_,
                percentPassedFilter);
        attrs.addAttribute(DeviceAttrs.BACKUP_BATTERY, 6748);

        construct_note(DeviceNoteTypes.UNPLUGGED, attrs);
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
            logger.debug(StackToString.toString(e));
            return false;
        }

        return true;
    }

    @Override
    public TiwiProDevice construct_note() {
        return this;
    }

    public TiwiProDevice construct_note(DeviceNoteTypes type) {
        attrs = new DeviceAttributes();
        construct_note(type, attrs);
        return this;
    }

    public TiwiProDevice construct_note(DeviceNoteTypes type,
            DeviceAttributes attrs) {
        TiwiNote note = new TiwiNote(type, state, tripTracker.currentLocation());
        note.addAttrs(attrs);
        try {
            note.addAttr(DeviceAttrs.SPEED_LIMIT, state.getSpeed_limit()
                    .intValue());
        } catch (Exception e) {
            logger.debug(StackToString.toString(e));
        }
        MasterTest.print(note.toString(), Level.DEBUG);
        state.setOdometer(0);
        addNote(note);

        return this;
    }

    public DeviceNote constructNote(DeviceNoteTypes type) {
        DeviceNote note = super.constructNote(type);
        state.setOdometer(0);
        return note;
    }

    @Override
    public TiwiProDevice createAckNote(Map<String, Object> reply) {
        MasterTest.print("Forward Command from Server: " + reply, Level.INFO);
        if (((Integer) reply.get("fwdID")) > 100) {
            TiwiNote ackNote = new TiwiNote(
                    DeviceNoteTypes.STRIPPED_ACKNOWLEDGE_ID_WITH_DATA);
            ackNote.addAttr(DeviceAttrs.FWDCMD_ID, (Integer) reply.get("fwdID"));
            ackNote.addAttr(DeviceAttrs.FWDCMD_STATUS,
                    FwdCmdStatus.FWDCMD_RECEIVED);
            notes.addNote(ackNote);
            addNote(ackNote);
            MasterTest.print(ackNote, Level.DEBUG);
        }
        processCommand(reply);
        return this;
    }

    public TiwiProDevice enter_zone(Integer zoneID) {

        attrs = new DeviceAttributes();
        attrs.addAttribute(DeviceAttrs.ZONE_ID, zoneID);
        construct_note(DeviceNoteTypes.WSZONES_ARRIVAL_EX, attrs);
        return this;
    }

    public boolean firmwareCompare(int versionNumber) {
        updateFirmware(versionNumber);
        String hessian = SHA1Checksum.getSHA1Checksum(lastDownload);
        getFirmwareFromSVN(versionNumber);
        String svn = SHA1Checksum.getSHA1Checksum(lastDownload);
        System.out.println(hessian);
        System.out.println(svn);
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
            e.printStackTrace();
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
            map.put("hardwareVersion", state.getProductVersion().getVersion());
            map.put("fileVersion", 1);
            map.put("formatVersion", 1);
            Map<String, Object> reply = mcmProxy.zoneUpdate(state.getImei(),
                    map);
            zones = new ZoneManager((byte[]) reply.get("f"));
        } catch (Exception e) {
            logger.debug(StackToString.toString(e));
            return false;
        }

        return true;
    }

    public TiwiProDevice leave_zone(Integer zoneID) {
        attrs = new DeviceAttributes();
        attrs.addAttribute(DeviceAttrs.ZONE_ID, zoneID);
        construct_note(DeviceNoteTypes.WSZONES_DEPARTURE_EX, attrs);
        return this;
    }
    
    public TiwiProDevice rf_kill(){
        
        construct_note(DeviceNoteTypes.SAT_EVENT_RF_KILL);
        return this;
    }

    public TiwiProDevice logout_driver(Integer RFID, Integer tripQuality,
            Integer MPG, Integer MPGOdometer) {
        attrs = new DeviceAttributes();
        attrs.addAttribute(DeviceAttrs.LOGOUT_TYPE, 4);
        attrs.addAttribute(
                DeviceAttrs.PERCENTAGE_OF_POINTS_THAT_PASSED_THE_FILTER_,
                tripQuality);
        attrs.addAttribute(DeviceAttrs.MPG, MPG);
        attrs.addAttribute(DeviceAttrs.MPG_DISTANCE, MPGOdometer);
        attrs.addAttribute(DeviceAttrs.RFID0, -536362939);
        attrs.addAttribute(DeviceAttrs.RFID1, 1415806888);
        construct_note(DeviceNoteTypes.STATS, attrs);
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
            get_changes();
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

        ackNote.addAttr(DeviceAttrs.FWDCMD_ID, reply.get("fwdID"));
        ackNote.addAttr(DeviceAttrs.FWDCMD_STATUS,
                FwdCmdStatus.FWDCMD_FLASH_SUCCESS);
        MasterTest.print(ackNote, Level.DEBUG);
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
            construct_note(DeviceNoteTypes.IGNITION_ON);
            trip_start = state.getTime().copy();
        } else {
            trip_stop = state.getTime().copy();
            Long tripTime = trip_stop.getDelta(trip_start) / 1000;
            addIgnitionOffNote(tripTime.intValue(), 980);
        }
        return this;
    }

    @Override
    protected TiwiProDevice set_power() {

        attrs = new DeviceAttributes();

        state.setPower_state(!state.getPower_state()); // Change the power state between on and off
        if (state.getPower_state()) {
            addPowerOnNote(state.getWMP(), state.getMSP(), 10);

        } else if (!state.getPower_state()) {
            addPowerOffNote(get_setting_int(DeviceProps.TIWI_LOW_POWER_MODE_SECONDS));
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

        addTamperingNote(850);

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
        updateMap.put("productVersion", state.getProductVersion().getVersion());
        Map<String, Object> reply = mcmProxy.tiwiproUpdate(state.getImei(),
                updateMap);
        String fileName = state.getProductVersion().name().toLowerCase()
                .replace("_", ".")
                + "." + versionNumber + "-hessian.dwl";
        return writeTiwiFile(fileName, reply);
    }
}