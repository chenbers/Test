package com.inthinc.pro.automation.objects;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.caucho.hessian.client.HessianRuntimeException;
import com.inthinc.pro.automation.deviceEnums.TiwiAttrs;
import com.inthinc.pro.automation.deviceEnums.TiwiFwdCmds;
import com.inthinc.pro.automation.deviceEnums.TiwiGenerals.FwdCmdStatus;
import com.inthinc.pro.automation.deviceEnums.TiwiGenerals.ViolationFlags;
import com.inthinc.pro.automation.deviceEnums.TiwiNoteTypes;
import com.inthinc.pro.automation.deviceEnums.TiwiProps;
import com.inthinc.pro.automation.device_emulation.DeviceBase;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.interfaces.DeviceProperties;
import com.inthinc.pro.automation.models.MCMProxyObject;
import com.inthinc.pro.automation.models.MapSection;
import com.inthinc.pro.automation.models.TiwiNote;
import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.automation.utils.AutomationFileHandler;
import com.inthinc.pro.automation.utils.SHA1Checksum;
import com.inthinc.pro.automation.utils.StackToString;
import com.inthinc.pro.model.configurator.ProductType;

public class TiwiProDevice extends DeviceBase {

    private final static Logger logger = Logger.getLogger(TiwiProDevice.class);

    private Map<TiwiAttrs, Integer> attrs;
    private AutomationCalendar trip_start, trip_stop;

    private ZoneManager zones;

    private int baseVer = 6;
    

    public TiwiProDevice(String IMEI) {
        this(IMEI, Addresses.QA);
    }

    public TiwiProDevice(String IMEI, Addresses server) {
        this(IMEI, server, TiwiProps.STATIC.getDefaultProps());
    }

    public TiwiProDevice(String IMEI, Addresses server, Map<TiwiProps, String> map) {
        super(IMEI, server, map, ProductType.TIWIPRO_R74);
    }

    @Override
    public TiwiProDevice add_location() {
        timeSinceLastLoc = 0;
        attrs = new HashMap<TiwiAttrs, Integer>();
        if (speeding) {
            attrs.put(TiwiAttrs.TYPE_VIOLATION_FLAGS, ViolationFlags.VIOLATION_MASK_SPEEDING.getValue());
        }

        else if (rpm_violation) {
            attrs.put(TiwiAttrs.TYPE_VIOLATION_FLAGS, ViolationFlags.VIOLATION_MASK_RPM.getValue());
        }

        else if (seatbelt_violation) {
            attrs.put(TiwiAttrs.TYPE_VIOLATION_FLAGS, ViolationFlags.VIOLATION_MASK_SEATBELT.getValue());
        }

        construct_note(TiwiNoteTypes.NOTE_TYPE_LOCATION, attrs);
        return this;
    }

    public TiwiProDevice add_lowBattery() {
        attrs = new HashMap<TiwiAttrs, Integer>();
        attrs.put(TiwiAttrs.TYPE_PERCENTAGE_OF_POINTS_THAT_PASSED_THE_FILTER_, 0);
        construct_note(TiwiNoteTypes.NOTE_TYPE_LOW_BATTERY, attrs);
        return this;
    }

    public TiwiProDevice add_noDriver() {
        construct_note(TiwiNoteTypes.NOTE_TYPE_NO_DRIVER);
        return this;
    }

    public TiwiProDevice add_note_event(Integer deltaX, Integer deltaY, Integer deltaZ) {
        attrs = new HashMap<TiwiAttrs, Integer>();
        attrs.put(TiwiAttrs.TYPE_DELTA_VX, deltaX);
        attrs.put(TiwiAttrs.TYPE_DELTA_VY, deltaY);
        attrs.put(TiwiAttrs.TYPE_DELTA_VZ, deltaZ);
        construct_note(TiwiNoteTypes.NOTE_TYPE_NOTE_EVENT, attrs);
        return this;
    }

    public TiwiProDevice add_seatBelt(Integer topSpeed, Integer avgSpeed, Integer distance) {
        attrs = new HashMap<TiwiAttrs, Integer>();
        attrs.put(TiwiAttrs.TYPE_AVG_RPM, 500);
        attrs.put(TiwiAttrs.TYPE_VIOLATION_FLAGS, 2);
        attrs.put(TiwiAttrs.TYPE_PERCENTAGE_OF_TIME_SPEED_FROM_GPS_USED, 50);
        attrs.put(TiwiAttrs.TYPE_TOP_SPEED, topSpeed);
        attrs.put(TiwiAttrs.TYPE_AVG_SPEED, avgSpeed);
        attrs.put(TiwiAttrs.TYPE_DISTANCE, distance);
        construct_note(TiwiNoteTypes.NOTE_TYPE_SEATBELT, attrs);

        return this;
    }

    public TiwiProDevice add_stats() {
        attrs = new HashMap<TiwiAttrs, Integer>();
        attrs.put(TiwiAttrs.BASE_VER, 0);
        attrs.put(TiwiAttrs.TYPE_EMU_HASH_1, -1517168504);
        attrs.put(TiwiAttrs.TYPE_EMU_HASH_2, 154129909);
        attrs.put(TiwiAttrs.TYPE_EMU_HASH_3, 1825195881);
        attrs.put(TiwiAttrs.TYPE_EMU_HASH_4, 1627500918);
        attrs.put(TiwiAttrs.TOTAL_AGPS_BYTES, 60000);
        construct_note(TiwiNoteTypes.NOTE_TYPE_STATS, attrs);
        return this;
    }

    public TiwiProDevice addIdlingNote(int lowIdleTime, int highIdleTime) {
        attrs = new HashMap<TiwiAttrs, Integer>();
        attrs.put(TiwiAttrs.TYPE_LOW_IDLE, lowIdleTime);
        attrs.put(TiwiAttrs.TYPE_HIGH_IDLE, highIdleTime);

        construct_note(TiwiNoteTypes.NOTE_TYPE_IDLING, attrs);
        increment_time(lowIdleTime + highIdleTime);
        return this;
    }

    public void addIgnitionOffNote(int tripDuration, int percentPointsPassedFilter) {
        attrs = new HashMap<TiwiAttrs, Integer>();
        attrs.put(TiwiAttrs.TYPE_TRIP_DURATION, tripDuration);
        attrs.put(TiwiAttrs.TYPE_PERCENTAGE_OF_POINTS_THAT_PASSED_THE_FILTER_, percentPointsPassedFilter);
        construct_note(TiwiNoteTypes.NOTE_TYPE_IGNITION_OFF, attrs);
    }

    public void addIgnitionOnNote() {
        construct_note(TiwiNoteTypes.NOTE_TYPE_IGNITION_ON);
    }

    public void addPowerOffNote(int lowPowerModeSeconds) {
        attrs.put(TiwiAttrs.TYPE_LOW_POWER_MODE_TIMEOUT, lowPowerModeSeconds);
        construct_note(TiwiNoteTypes.NOTE_TYPE_LOW_POWER_MODE, attrs);
        flushNotes();
    }

    public TiwiProDevice addPowerOnNote(int WMP, int MSP, int gpsLockTime) {
        attrs.put(TiwiAttrs.TYPE_FIRMWARE_VERSION, WMP);
        attrs.put(TiwiAttrs.TYPE_DMM_VERSION, MSP);
        attrs.put(TiwiAttrs.TYPE_GPS_LOCK_TIME, gpsLockTime);
        construct_note(TiwiNoteTypes.NOTE_TYPE_POWER_ON, attrs);
        return this;
    }

    public TiwiProDevice addSpeedingNote(Integer distance, Integer topSpeed, Integer avgSpeed) {
        attrs = new HashMap<TiwiAttrs, Integer>();
        attrs.put(TiwiAttrs.TYPE_DISTANCE, distance);
        attrs.put(TiwiAttrs.TYPE_TOP_SPEED, topSpeed);
        attrs.put(TiwiAttrs.TYPE_AVG_SPEED, avgSpeed);
        attrs.put(TiwiAttrs.TYPE_SPEED_ID, 9999);
        attrs.put(TiwiAttrs.TYPE_VIOLATION_FLAGS, 1);

        construct_note(TiwiNoteTypes.NOTE_TYPE_SPEEDING_EX3, attrs);
        return this;
    }

    public void addTamperingNote(int percentPassedFilter) {
        attrs = new HashMap<TiwiAttrs, Integer>();
        attrs.put(TiwiAttrs.TYPE_PERCENTAGE_OF_POINTS_THAT_PASSED_THE_FILTER_, percentPassedFilter);
        attrs.put(TiwiAttrs.TYPE_BACKUP_BATTERY, 6748);

        construct_note(TiwiNoteTypes.NOTE_TYPE_UNPLUGGED, attrs);
    }

    @Override
    public TiwiProDevice construct_note() {
        return this;
    }

    public TiwiProDevice construct_note(TiwiNoteTypes type) {
        if (productVersion == ProductType.TIWIPRO_R74) {
            attrs = new HashMap<TiwiAttrs, Integer>();
            construct_note(type, attrs);
        }
        check_queue();
        return this;
    }

    public TiwiProDevice construct_note(TiwiNoteTypes type, Map<TiwiAttrs, Integer> attrs) {
        TiwiNote note = new TiwiNote(type, time, sats, heading, 1, latitude, longitude, speed, odometer);
        note.addAttrs(attrs);
        try {
            note.addAttr(TiwiAttrs.TYPE_SPEED_LIMIT, speed_limit.intValue());
        } catch (Exception e) {
            logger.debug(StackToString.toString(e));
        }
        logger.debug(note.toString());
        clear_internal_settings();
        addNote(note);
        return this;
    }

    @Override
    public TiwiProDevice createAckNote(Map<String, Object> reply) {
        if (((Integer)reply.get("fwdID")) > 100){
            TiwiNote ackNote = new TiwiNote(TiwiNoteTypes.NOTE_TYPE_STRIPPED_ACKNOWLEDGE_ID_WITH_DATA);
            ackNote.addAttr(TiwiAttrs.TYPE_FWDCMD_ID, (Integer) reply.get("fwdID"));
            ackNote.addAttr(TiwiAttrs.TYPE_FWDCMD_STATUS, FwdCmdStatus.FWDCMD_RECEIVED);
            notes.addNote(ackNote);
        }
        processCommand(reply);
        return this;
    }

    public TiwiProDevice enter_zone(Integer zoneID) {
        attrs = new HashMap<TiwiAttrs, Integer>();
        attrs.put(TiwiAttrs.TYPE_ZONE_ID, zoneID);
        construct_note(TiwiNoteTypes.NOTE_TYPE_WSZONES_ARRIVAL_EX, attrs);
        return this;
    }

    @Override
    protected Integer get_note_count() {
        return Integer.parseInt(Settings.get(TiwiProps.PROPERTY_SET_MSGS_PER_NOTIFICATION));
    }

    public String get_setting(TiwiProps settingID) {
        return Settings.get(settingID.getValue());
    }

    public Integer get_setting_int(TiwiProps settingID) {
        Double valueD = Double.parseDouble(Settings.get(settingID));
        Integer valueI = valueD.intValue();
        return valueI;
    }

    public TiwiProDevice leave_zone(Integer zoneID) {
        attrs = new HashMap<TiwiAttrs, Integer>();
        attrs.put(TiwiAttrs.TYPE_ZONE_ID, zoneID);
        construct_note(TiwiNoteTypes.NOTE_TYPE_WSZONES_DEPARTURE_EX, attrs);
        return this;
    }

    public TiwiProDevice logout_driver(Integer RFID, Integer tripQuality, Integer MPG, Integer MPGOdometer) {
        attrs = new HashMap<TiwiAttrs, Integer>();
        attrs.put(TiwiAttrs.LOGOUT_TYPE, 4);
        attrs.put(TiwiAttrs.TYPE_PERCENTAGE_OF_POINTS_THAT_PASSED_THE_FILTER_, tripQuality);
        attrs.put(TiwiAttrs.TYPE_MPG, MPG);
        attrs.put(TiwiAttrs.TYPE_MPG_DISTANCE, MPGOdometer);
        attrs.put(TiwiAttrs.TYPE_RFID0, -536362939);
        attrs.put(TiwiAttrs.TYPE_RFID1, 1415806888);
        construct_note(TiwiNoteTypes.NOTE_TYPE_STATS, attrs);
        return this;
    }

    public TiwiProDevice nonTripNote(AutomationCalendar time, int sats, int heading, Double latitude, Double longitude, int speed, int odometer) {
        this.time.setDate(time);
        this.sats = sats;
        this.heading = heading;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.odometer = odometer;
        return this;
    }

    @Override
    protected Integer processCommand(Map<String, Object> reply) {
        logger.debug(reply);
        if (reply.get("cmd") == null)
            return 1;
        TiwiFwdCmds fwdCmd = TiwiFwdCmds.valueOf(reply.get("cmd"));
        HashMap<TiwiProps, String> changes = new HashMap<TiwiProps, String>();
        TiwiNote ackNote = new TiwiNote(TiwiNoteTypes.NOTE_TYPE_STRIPPED_ACKNOWLEDGE_ID_WITH_DATA);

        if (fwdCmd == TiwiFwdCmds.ASSIGN_DRIVER) {
            String[] values = reply.get("data").toString().split(" ");
            setDeviceDriverID(Integer.parseInt(values[0]));
        } else if (fwdCmd == TiwiFwdCmds.DUMP_CONFIGURATION)
            dump_settings();
        else if (fwdCmd == TiwiFwdCmds.UPDATE_CONFIGURATION)
            get_changes();
        else if (fwdCmd == TiwiFwdCmds.DOWNLOAD_NEW_WITNESSII_FIRMWARE)
            set_MSP(reply.get("data"));
        else if (fwdCmd == TiwiFwdCmds.DOWNLOAD_NEW_FIRMWARE)
            set_WMP(reply.get("data"));
        else if (fwdCmd == TiwiFwdCmds.SET_SPEED_BUFFER_VALUES) {
            changes.put(TiwiProps.PROPERTY_VARIABLE_SPEED_LIMITS, reply.get("fwdData").toString());
        } else if (fwdCmd == TiwiFwdCmds.DOWNLOAD_NEW_MAPS){
//            checkSbsSubscribed();
        }
        

        if (reply.get("fwdID").equals(100)||reply.get("fwdID").equals(1))
            return 1;

        ackNote.addAttr(TiwiAttrs.TYPE_FWDCMD_ID, reply.get("fwdId"));
        ackNote.addAttr(TiwiAttrs.TYPE_FWDCMD_STATUS, FwdCmdStatus.FWDCMD_FLASH_SUCCESS);
        notes.addNote(ackNote);

        if (!changes.isEmpty())
            set_settings(changes);
        return 1;
    }

    protected TiwiProDevice set_ignition(Integer time_delta) {
        ignition_state = !ignition_state;
        
        set_time(time.addToSeconds(time_delta));
        if (ignition_state) {
            construct_note(TiwiNoteTypes.NOTE_TYPE_IGNITION_ON);
            trip_start = time.copy();
        } else if (!ignition_state) {
            trip_stop = time.copy();
            Long tripTime = trip_stop.getDelta(trip_start);
            addIgnitionOffNote(tripTime.intValue(), 980);
        }
        return this;
    }

    @Override
    protected TiwiProDevice set_power() {

        attrs = new HashMap<TiwiAttrs, Integer>();

        power_state = !power_state; // Change the power state between on and off
        if (power_state) {
            addPowerOnNote(WMP, MSP, 10);

        } else if (!power_state) {
            addPowerOffNote(get_setting_int(TiwiProps.PROPERTY_LOW_POWER_MODE_SECONDS));

            check_queue();
        }
        return this;
    }

    @Override
    protected TiwiProDevice set_server(Addresses server) {
        mcmProxy = new MCMProxyObject(server);
        Settings.put(TiwiProps.PROPERTY_SERVER_PORT, server.getMCMPort().toString());
        Settings.put(TiwiProps.PROPERTY_SERVER_URL, server.getMCMUrl());
        return this;
    }

    public TiwiProDevice set_settings(TiwiProps key, String value) {
        set_settings(key, value);
        return this;
    }

    @Override
    public TiwiProDevice set_speed_limit(Integer limit) {
        Settings.put(TiwiProps.PROPERTY_SPEED_LIMIT, limit.toString());
        return this;
    }

    public TiwiProDevice tampering(Integer timeDelta) {
        power_state = false;
        ignition_state = false;
        increment_time(timeDelta);

        addTamperingNote(850);

        power_on_device(time);
        turn_key_on(10);
        return this;
    }

    protected HashMap<DeviceProperties, String> theirsToOurs(HashMap<?, ?> reply) {
        HashMap<TiwiProps, String> map = new HashMap<TiwiProps, String>();
        Iterator<?> itr = reply.keySet().iterator();
        while (itr.hasNext()) {
            Integer next = (Integer) itr.next();
            String value = reply.get(next).toString();
            map.put(TiwiProps.STATIC.valueOf(next), (String) value);
        }
        return null;
    }

    protected TiwiProDevice was_speeding() {
        Integer topSpeed = 0;
        Integer avgSpeed = 0;
        Double avg = 0.0;
        Double speeding_distance = 0.0;
        for (int i = 0; i < speed_points.size(); i++) {
            int speed = speed_points.get(i);
            avg += speed;
            if (topSpeed < speed) {
                topSpeed = speed;
            }
        }

        avg = avg / (speed_points.size());
        avgSpeed = avg.intValue();
        for (int i = 1; i < speed_loc.size(); i++) {
            Double[] last = speed_loc.get(i - 1);
            Double[] loc = speed_loc.get(i);
            speeding_distance += Math.abs(calculator.calc_distance(last[0], last[1], loc[0], loc[1]));
        }
        Integer distance = (int) (speeding_distance * 100);
        addSpeedingNote(distance, topSpeed, avgSpeed);
        return this;
    }
    
    public boolean updateFirmware(int versionNumber){
        Map<String, Object> updateMap = new HashMap<String, Object>();
        updateMap.put("hardwareVersion", WMP);
        updateMap.put("fileVersion", versionNumber);
        updateMap.put("productVersion", productVersion.getVersion());
        Map<String, Object> reply = mcmProxy.tiwiproUpdate(imei, updateMap);
        String fileName = productVersion.name().toLowerCase().replace("_",".") + "." + versionNumber + "-hessian.dwl";
        return writeTiwiFile(fileName, reply);
    }
    
    public boolean getFirmwareFromSVN(int versionNumber){
        try {
            String svnUrl = "https://svn.iwiglobal.com/iwi/release/tiwi/pro/wmp";
            if (this.productVersion.equals(ProductType.TIWIPRO_R74)){
                svnUrl += "R74/";
            } else {
                svnUrl += "/";
            }
            String directory = "target/test/resources/" + imei + "/downloads/";
            String fileName = productVersion.name().toLowerCase().replace("_",".") + "." + versionNumber + "-svn.dwl";
            File file = new File(directory);
            file.mkdirs();
            file = new File(lastDownload = directory + fileName);
            file.createNewFile();
            return AutomationFileHandler.downloadSvnDirectory(svnUrl, fileName.replace("-svn", ""), file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
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
    
    public boolean checkSbsSubscribed() {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("b", baseVer);
            
            List<Map<String, Object>> reply = mcmProxy.checkSbsSubscribed(imei, map);
            for (Map<String, Object> section : reply){
                try {
                    int f = (Integer) section.get("f");
                    int v = (Integer) section.get("v");
                    MapSection mapSection = new MapSection(getSbsBase(f));
                    if (v!=0){
                        mapSection.setEdit(getSbsEdit(f, 0, v));
                    }
                } catch(HessianRuntimeException e){
                    continue;
                }
            }
        } catch (Exception e){
            logger.debug(StackToString.toString(e));
            return false;
        }
        
        return true;
    }
    
    public Map<String, Object> getSbsBase( int fileHash){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("f", fileHash);
        map.put("b", baseVer);
        Map<String, Object> reply = mcmProxy.getSbsBase(imei, map);
        return reply;
    }
    
    public Map<String, Object> getSbsEdit(int fileHash, int currentVersion, int newVersion){
        if (currentVersion == newVersion){
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("b", baseVer);
        map.put("f", fileHash);
        map.put("cv", currentVersion);
        map.put("nv", newVersion);
        
        Map<String, Object> reply = mcmProxy.getSbsEdit(imei, map);
        return reply;
    }
    
    public boolean checkSbsEdit(int fileHash, int currentVersion){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("b", baseVer);
        map.put("f", fileHash);
        map.put("cv", currentVersion);
        list.add(map);
        List<Map<String, Object>> reply = mcmProxy.checkSbsEdit(imei, list);
        logger.debug(reply);
        
        return false;
    }
    
    public boolean getZones(){
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("hardwareVersion", productVersion.getVersion());
            map.put("fileVersion", 1);
            map.put("formatVersion", 1);
            Map<String, Object> reply = mcmProxy.zoneUpdate(imei, map);
            zones = new ZoneManager((byte[]) reply.get("f"));
        } catch (Exception e){
            logger.debug(StackToString.toString(e));
            return false;
        }
        
        return true;
    }
    
    public ZoneManager getLoadedZones(){
        return zones;
    }
    

    public int getBaseVer() {
        return baseVer;
    }

    public void setBaseVer(int baseVer) {
        this.baseVer = baseVer;
    }

}