package com.inthinc.device.devices;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import android.util.Log;

import com.caucho.hessian.client.HessianRuntimeException;
import com.inthinc.device.emulation.enums.DeviceEnums.FwdCmdStatus;
import com.inthinc.device.emulation.enums.ConstDDB;
import com.inthinc.device.emulation.enums.DeviceForwardCommands;
import com.inthinc.device.emulation.enums.DeviceNoteTypes;
import com.inthinc.device.emulation.enums.DeviceProps;
import com.inthinc.device.emulation.enums.EventAttr;
import com.inthinc.device.emulation.enums.Locales;
import com.inthinc.device.emulation.enums.MapSection;
import com.inthinc.device.emulation.interfaces.DownloadService;
import com.inthinc.device.emulation.notes.TiwiNote;
import com.inthinc.device.emulation.utils.AutomationFileHandler;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.emulation.utils.GeoPoint.Heading;
import com.inthinc.device.emulation.utils.MCMProxyObject;
import com.inthinc.device.hessian.tcp.AutomationHessianFactory;
import com.inthinc.device.hessian.tcp.HessianException;
import com.inthinc.device.objects.AutomationDeviceEvents;
import com.inthinc.device.objects.ZoneManager;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.enums.DownloadServers;
import com.inthinc.pro.automation.enums.ProductType;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.pro.automation.objects.AutomationCalendar.WebDateFormat;
import com.inthinc.pro.automation.utils.AutomationThread;
import com.inthinc.pro.automation.utils.SHA1Checksum;

public class TiwiProDevice extends DeviceBase {

    private final static Logger logger = Logger.getLogger(TiwiProDevice.class);

    private AutomationCalendar trip_start, trip_stop;

    private ZoneManager zones;

    private int baseVer = 6;
    
    private static final String defaultIMEI = "500000000000000";
    

    public TiwiProDevice() {
        this(defaultIMEI);
    }

    public TiwiProDevice(String IMEI) {
        this(IMEI, Addresses.QA);
    }
    
    public TiwiProDevice(ProductType type){
        this(defaultIMEI, type, Addresses.QA);
    }
    
    public TiwiProDevice(String IMEI, ProductType type){
        this(IMEI, type, Addresses.QA);
    }
    
    public TiwiProDevice(Addresses server){
        this(defaultIMEI, server);
    }

    public TiwiProDevice(String IMEI, Addresses server) {
        this(IMEI, server, DeviceProps.getTiwiDefaults());
    }
    
    public TiwiProDevice(ProductType type, Addresses server){
        this(defaultIMEI, type, server);
    }
    
    public TiwiProDevice(String IMEI, ProductType type, Addresses server){
        this(IMEI, type, server, DeviceProps.getTiwiDefaults());
    }

    public TiwiProDevice(String IMEI, Addresses server,
            Map<DeviceProps, String> map) {
        super(IMEI, ProductType.TIWIPRO_R74, map, server);
    }
    
    public TiwiProDevice(String IMEI, ProductType type, Addresses server,
            Map<DeviceProps, String> map) {
        super(IMEI, type, map, server);
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
            Log.d("Exception %s", e);
            return false;
        }

        return true;
    }
    


    public boolean compareAudio(int fileNumber, Locales locale) {
        String destPath = "target/test/resources/audioFiles/";
        String svnPath = destPath + "svnVersion/";
        String hessianPath = destPath + "hessianVersion/";
        String fileName = String.format("%02d.pcm", fileNumber); 
        Log.d(fileName);
        
        String svnFile = svnPath + "/" + fileName; 
        String hessianFile = hessianPath + "/" + fileName;
        
        try {
            getAudioFile(hessianFile, fileNumber, locale);
            
            String url = "https://svn.iwiglobal.com/iwi/map_image/trunk/audio/"
                    + locale.getFolder(); 
            File dest = new File(svnFile);

            if (!AutomationFileHandler.downloadSvnDirectory(url, fileName, dest)) {
                Log.i("SVN File not found");
                return false;
            }
            boolean result = AutomationFileHandler.filesEqual(svnFile, hessianFile);
            if (!result){
                saveBadAudio(destPath, locale, fileNumber, svnFile, hessianFile);
            }
            return result;
        } catch (HessianException e){
            Log.wtf("%s", e);
            return false;
        }
    }
    


    @Override
    public TiwiProDevice createAckNote(Map<String, Object> reply) {
    	Log.d("Forward Command from Server: " + reply);
        if (((Integer) reply.get("fwdID")) > 100) {
            TiwiNote ackNote = new TiwiNote(
                    DeviceNoteTypes.STRIPPED_ACKNOWLEDGE_ID_WITH_DATA, tripTracker.currentLocation());
            ackNote.addAttr(EventAttr.FWDCMD_ID, (Integer) reply.get("fwdID"));
            ackNote.addAttr(EventAttr.FWDCMD_STATUS,
                    FwdCmdStatus.FWDCMD_RECEIVED);
            notes.addNote(ackNote);
            addNote(ackNote);
            Log.d("ackNote %s", ackNote);
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
        Log.d("hessian %s", hessian);
        Log.d("svn     %s", svn);
        return hessian.equals(svn);
    }

    @Override
    protected Integer get_note_count() {
        return Integer.parseInt(state
                .get_setting(DeviceProps.SET_MSGS_PER_NOTIFICATION_T));
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
            svnUrl += state.getProductVersion().getTiwiVersion() + "/";
            
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
                DeviceNoteTypes.STRIPPED_ACKNOWLEDGE_ID_WITH_DATA, tripTracker.currentLocation());

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
            changes.put(DeviceProps.VARIABLE_SPEED_LIMITS_T,
                    reply.get("fwdData").toString());
        } else if (fwdCmd == DeviceForwardCommands.DOWNLOAD_NEW_MAPS) {
            // checkSbsSubscribed();
        }

        if (reply.get("fwdID").equals(100) || reply.get("fwdID").equals(1))
            return 1;

        ackNote.addAttr(EventAttr.FWDCMD_ID, reply.get("fwdID"));
        ackNote.addAttr(EventAttr.FWDCMD_STATUS,
                FwdCmdStatus.FWDCMD_FLASH_SUCCESS);
        Log.d("ackNote %s", ackNote);
        notes.addNote(ackNote);
        // check_queue();

        if (!changes.isEmpty())
            set_settings(changes);
        return 1;
    }
    

    private void saveBadAudio(String base, Locales locale, int fileNumber, String svnFile, String hessianFile) {
        File temp = new File(base + "/badAudio"); 
        temp.mkdir();
        AutomationCalendar now = new AutomationCalendar(WebDateFormat.FILE_NAME);
        String suffix = String.format("_%s_%s_%02d.pcm", locale, now, fileNumber);
        try {
            FileUtils.copyFile(new File(svnFile), new File(temp, "svn" + suffix));
            FileUtils.copyFile(new File(hessianFile), new File(temp, "hessian" + suffix));
        } catch (IOException e) {
            Log.wtf("%s", e);
        }
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
        	state.setLowPowerTimeout(get_setting_int(DeviceProps.LOW_POWER_MODE_SECONDS_T));
        	AutomationDeviceEvents.powerOff(this);
            flushNotes();
        }
        return this;
    }

    @Override
    public TiwiProDevice set_server(Addresses server) {
        mcmProxy = new MCMProxyObject(server);
        state.setSetting(DeviceProps.SERVER_PORT_T, server.getMCMPort()
                .toString());
        state.setSetting(DeviceProps.SERVER_URL_T, server.getMCMUrl());
        return this;
    }

    public TiwiProDevice set_settings(DeviceProps key, String value) {
        set_settings(key, value);
        return this;
    }

    @Override
    public TiwiProDevice set_speed_limit(Integer limit) {
        state.setSetting(DeviceProps.SPEED_LIMIT_T, limit.toString());
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
    
    public int uploadFirmware(int version){
        try {
            getFirmwareFromSVN(version);
            
            DownloadServers server = DownloadServers.valueOf(this.server.name());
            DownloadService upload = (DownloadService) new AutomationHessianFactory().createProxy(DownloadService.class, server.getAddress(), server.getPort());
            File svnFile = new File(getLastDownload());
            FileInputStream fis = new FileInputStream(svnFile);
            byte[] file = IOUtils.toByteArray(fis);
            
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("dlTypeID", ConstDDB.DDB_DLTYPE_TIWIPRO_FIRMWARE.getIndex());
            map.put("filename", svnFile.getName().replace("-svn", ""));
            map.put("version", version);
            map.put("min_hw_version", 0);
            map.put("max_hw_version", 2147483647);
            map.put("productMask", 1<<(state.getProductVersion().getIndex()-1));
            map.put("status", 1);
            map.put("file", file);
            
            Map<String, Object> result = upload.createDownload(map);
            int dlID = (Integer) result.get("dlID");
            AutomationThread.pause(10);
            Log.i("%s", firmwareCompare(version));
            return dlID;
        } catch (IOException e){
            Log.wtf("%s", e);
        }
        return 0;
    }

    
}