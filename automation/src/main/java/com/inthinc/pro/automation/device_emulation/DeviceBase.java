package com.inthinc.pro.automation.device_emulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.log4j.Level;

import com.inthinc.pro.automation.deviceEnums.DeviceForwardCommands;
import com.inthinc.pro.automation.deviceEnums.DeviceProps;
import com.inthinc.pro.automation.device_emulation.NoteManager.DeviceNote;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.enums.Locales;
import com.inthinc.pro.automation.models.GeoPoint;
import com.inthinc.pro.automation.models.MCMProxyObject;
import com.inthinc.pro.automation.models.MapSection;
import com.inthinc.pro.automation.models.NoteBC;
import com.inthinc.pro.automation.models.NoteBC.Direction;
import com.inthinc.pro.automation.models.NoteWS;
import com.inthinc.pro.automation.models.TiwiNote;
import com.inthinc.pro.automation.objects.TripTracker;
import com.inthinc.pro.automation.resources.DeviceStatistics;
import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.automation.utils.AutomationCalendar.WebDateFormat;
import com.inthinc.pro.automation.utils.AutomationThread;
import com.inthinc.pro.automation.utils.MasterTest;
import com.inthinc.pro.automation.utils.SHA1Checksum;
import com.inthinc.pro.automation.utils.StackToString;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.rally.RallyWebServices;
import com.inthinc.pro.rally.TestCaseResult;
import com.inthinc.pro.rally.TestCaseResult.Verdicts;

public abstract class DeviceBase {
    
    protected final NoteManager notes ;
    protected final ArrayList<Integer> speed_points;
    protected final ArrayList<GeoPoint> speed_loc;
    protected final Distance_Calc calculator;
    protected final TripTracker tripTracker;
    protected MCMProxyObject mcmProxy;
    protected Object reply;

    protected String lastDownload;
    
    protected Addresses portal;
    protected Map<Integer, MapSection> sbsModule;

    protected DeviceState state;
    protected int note_count = 4;
    
    private DeviceBase(String IMEI, ProductType version, Map<DeviceProps, String> settings){
        state = new DeviceState(IMEI, version);
        tripTracker = new TripTracker(state);
        state.setSettings(settings);
        sbsModule = new HashMap<Integer, MapSection>();
        speed_points = new ArrayList<Integer>();
        speed_loc = new ArrayList<GeoPoint>();
        notes = new NoteManager();
        calculator = new Distance_Calc();
        state.setMapRev(0);
    }

    public DeviceBase(String IMEI, Addresses server, Map<DeviceProps, String> map, ProductType version) {
        this(IMEI, version, map);
    	portal = server;
        initiate_device();
    }
    
    private DeviceBase ackFwdCmds(List<HashMap<String, Object>> reply) {
        testFwdCmdLimit(reply.size());
        
        HashMap<String, Object> fwd = new HashMap<String, Object>();

        if (!reply.isEmpty()) {
            Iterator<HashMap<String, Object>> itr = reply.iterator();
            while (itr.hasNext()) {
                fwd = itr.next();
//                if (fwd.get("fwdID").equals(100)||fwd.get("fwdID").equals(1))
//                    continue;
                fwd.put("cmd", DeviceForwardCommands.valueOf((Integer) fwd.get("cmd")));
                createAckNote(fwd);
            }
        }
        return this;
    }

    private void testFwdCmdLimit(int numOfCommands) {
        String testCase = "TC875";
        String testSet = "TS96";
        String byID = "FormattedID";
        
        
        
        if (numOfCommands > 5 ){
            TestCaseResult tcr = new TestCaseResult(RallyWebServices.username, RallyWebServices.password, RallyWebServices.INTHINC);
            AutomationCalendar today = AutomationCalendar.now(WebDateFormat.RALLY_DATE_FORMAT);

            tcr.setBuildNumber(today.toString(WebDateFormat.NOTE_DATE_TIME));
            tcr.setTestCase(new NameValuePair(byID, testCase));
            tcr.setTestSet(new NameValuePair(byID, testSet));
            tcr.setVerdict(Verdicts.FAIL);
            tcr.send_test_case_results();
        }
    }


    protected abstract DeviceBase add_location();

    protected DeviceBase addNote(DeviceNote note){
        notes.addNote(note);
        return this;
    }

    protected DeviceBase check_queue() {
        if (notes.size() >= get_note_count()) {
            send_note();
        }
        return this;
    }


    private DeviceBase configurate_device() {
    	if (portal==Addresses.TEEN_PROD){
    		return this;
    	}
        dump_settings();
        get_changes();
        return this;
    }
    
    public TripTracker getTripTracker(){
        return tripTracker;
    }

    protected abstract DeviceBase construct_note();

    protected abstract DeviceBase createAckNote(Map<String, Object> reply);

    public DeviceBase dump_settings() {
//    	if (portal==Addresses.TEEN_PROD){
//    		return this;
//    	}
//
//        if (WMP >= 17013) {
//            reply = null;
//            try {
//                MasterTest.print("dumping settings", Level.DEBUG);
//                reply = mcmProxy.dumpSet(imei, productVersion.getVersion(), oursToThiers());
//                MasterTest.print(reply, Level.DEBUG);
//            } catch (Exception e){
//                MasterTest.print("Error from DumpSet: " + StackToString.toString(e), Level.ERROR);
//                MasterTest.print("Current Note Count is " + DeviceStatistics.getHessianCalls(), Level.ERROR);
//                MasterTest.print("Current time is: " + System.currentTimeMillis(), Level.ERROR);
//                MasterTest.print("Notes Started at: " + DeviceStatistics.getStart().epochTime(), Level.ERROR);
//            }
//        }
        return this;
    }

    public void flushNotes() {
        while (notes.hasNext()){
            send_note();
        }
    }
    
    protected Map<Integer, String> oursToThiers(){
        Map<Integer, String> map = new HashMap<Integer, String>();
        Iterator<?> itr = state.getSettings().keySet().iterator();
        while (itr.hasNext()){
            DeviceProps next = (DeviceProps) itr.next();
            map.put(next.getValue(), state.getSettings().get(next));
        }
        
        return map;
    }
    
    public boolean getAudioFile(String fileName, int fileVersion, Locales locale){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hardwareVersion", state.getWMP());
        map.put("fileVersion", fileVersion);
        map.put("locale", locale.toString());
        map.put("productVersion", state.getProductVersion().getVersion());
        return writeTiwiFile(fileName, mcmProxy.audioUpdate(state.getImei(), map));
    }
    
    
    protected boolean writeTiwiFile(String fileName, Map<String, Object> reply){
        MasterTest.print(reply, Level.DEBUG);
        if (!fileName.startsWith("target")){
            String resourceFile = "target/test/resources/" + state.getImei() + "/" + "downloads/";
            lastDownload = resourceFile + fileName;
        } else {
            lastDownload = fileName;
        }
        try {
            File destination = new File(lastDownload);
            destination.getParentFile().mkdirs();
            destination.createNewFile();
            destination.deleteOnExit();
            FileOutputStream fw = new FileOutputStream(destination);
            fw.write((byte[]) reply.get("f"));
            fw.close();
            MasterTest.print("SHA1 Hash is: " + SHA1Checksum.getSHA1Checksum(lastDownload), Level.DEBUG);
            return true;
        } catch (FileNotFoundException e) {
            MasterTest.print(StackToString.toString(e), Level.ERROR);
        } catch (IOException e) {
            MasterTest.print(StackToString.toString(e), Level.ERROR);
        } catch (Exception e) {
            MasterTest.print(StackToString.toString(e), Level.ERROR);
        }
        return false;
    }

    protected DeviceBase get_changes() {
//    	if (portal==Addresses.TEEN_PROD){
//    		return this;
//    	}
//        if (WMP >= 17013) {
//            try {
//                reply = mcmProxy.reqSet(imei);
//            } catch (Exception e){
//                MasterTest.print("Error from ReqSet[289]: " + e.getCause(), Level.ERROR);
//                MasterTest.print("Current Note Count is " + DeviceStatistics.getHessianCalls(), Level.ERROR);
//                MasterTest.print("Current time is: " + System.currentTimeMillis(), Level.ERROR);
//                MasterTest.print("Notes Started at: " + DeviceStatistics.getStart().epochTime(), Level.ERROR);
//            }
//            if (reply instanceof HashMap<?, ?>) {
//                set_settings( theirsToOurs((HashMap<?, ?>) reply));
//            }
//        }
        return this;
    }
    
    public NoteManager getNotes(){
        return notes;
    }

    public int getOdometer() {
        return state.getOdometer();
    }

    public void setOdometer(int odometer) {
        state.setOdometer(odometer);
    }
    
    protected abstract HashMap<DeviceProps, String> theirsToOurs(HashMap<?, ?> reply);
    
    protected abstract Integer get_note_count();

    public String get_setting(DeviceProps propertyID){
        return state.getSettings().get(propertyID);
    }


    public DeviceBase increment_time(Integer increment) {
        state.incrementTime(increment);
        return this;
    }
    
    private DeviceBase initiate_device() {
        state.setIgnition_state(false);
        state.setSats(8);
        state.setWMP(17014);
        state.setMSP(50);
        state.setSpeed(0);
        
        state.setIgnition_state(false);
        state.setPower_state(false);
        state.setSpeeding(false);
        state.setRpm_violation(false);
        state.setSeatbelt_violation(false);
        
//        clear_internal_settings();
        
        set_server(portal);

        return this;
    }

    private DeviceBase is_speeding() {
        GeoPoint point = tripTracker.currentLocation();
        if (state.getSpeed() > state.getSpeed_limit() && !state.getSpeeding()) {
            state.setSpeeding(true);
            speed_loc.add(point);
            speed_points.add(state.getSpeed());
        } else if (state.getSpeed() > state.getSpeed_limit() && state.getSpeeding()) {
            speed_loc.add(point);
            speed_points.add(state.getSpeed());
        } else if (state.getSpeed() < state.getSpeed_limit() && state.getSpeeding()) {
            state.setSpeeding(false);
            speed_loc.add(point);
            speed_points.add(state.getSpeed());
            was_speeding();
        }
        return this;
    }

    public DeviceBase power_off_device(Integer time_delta) {
        if (state.getPower_state()) {
            increment_time(time_delta);
            set_power();
        } else {
            MasterTest.print("The device is already off.");
        }
        MasterTest.print("Last note created at: " + state.getTime().epochSecondsInt(), Level.DEBUG);
        return this;
    }

    public DeviceBase power_on_device() {
        power_on_device(state.getTime());
        return this;
    }

    public DeviceBase power_on_device(AutomationCalendar time_now) {
        if (!state.getPower_state()) {
            set_time(time_now);
            set_power();
            configurate_device();
            time_now.addToSeconds(30);
            set_time(time_now);
        } else {
            MasterTest.print("The device is already on.");
        }
        return this;
    }


    protected abstract Integer processCommand(Map<String, Object> reply);

    @SuppressWarnings("unchecked")
    protected DeviceBase send_note() {
        while (notes.hasNext()) {
            Map<Class<? extends DeviceNote>, LinkedList<DeviceNote>> sendingQueue = notes.getNotes(note_count);
            reply = null;
            int loop = 0;
            while (!sendingQueue.isEmpty()) {
                String sendingImei = state.getImei();
                Class<?> noteClass = DeviceNote.class;
                try{
                    if (state.getProductVersion() == ProductType.WAYSMART && !state.getWaysDirection().equals(Direction.sat)){
                        sendingImei = state.getMcmID();
                    }
                    if (sendingQueue.containsKey(NoteBC.class)){
                        noteClass = NoteBC.class;
                        reply = mcmProxy.notebc(sendingImei, state.getWaysDirection(), sendingQueue.get(noteClass), true);
                    } else if (sendingQueue.containsKey(NoteWS.class)){
                        noteClass = NoteWS.class;
                        reply = mcmProxy.notews(sendingImei, state.getWaysDirection(), sendingQueue.get(noteClass), true);
                    } else if (sendingQueue.containsKey(TiwiNote.class)){
                        noteClass = TiwiNote.class;
                        reply = mcmProxy.note(sendingImei, sendingQueue.get(noteClass), true);
                    }
                    sendingQueue.remove(noteClass); 
                } catch (Exception e) {
                    MasterTest.print("Error from Note with IMEI: " + sendingImei + "  " + StackToString.toString(e) + 
                            "\n" + sendingQueue +
                            "\nCurrent Note Count is " + DeviceStatistics.getHessianCalls()+
                            "\nCurrent time is: " + System.currentTimeMillis() +
                            "\nNotes Started at: " + DeviceStatistics.getStart().epochTime(), Level.INFO);
                    loop ++;
                    AutomationThread.pause(1);
                    if (loop == 5){
                        MasterTest.print("Unable to send note!!!!!" + StackToString.toString(e), Level.FATAL);
                        break;
                    }
                }

                if (reply instanceof ArrayList<?>) {
                    ackFwdCmds((List<HashMap<String, Object>>) reply);
                } else {
                    MasterTest.print("Reply from Server: " + reply, Level.DEBUG);    
                }
                
            }
        }
        return this;
    }

    
    protected abstract DeviceBase set_ignition(Integer time_delta);//

    
    public DeviceBase set_MSP(Integer version) {
        state.setMSP(version);
        return this;
    }

    public DeviceBase set_MSP(Object version) {
        set_MSP((Integer) version);
        return this;
    }


    protected abstract DeviceBase set_power();

    public DeviceBase set_satelites(Integer satelites) {
        state.setSats(satelites);
        return this;
    }

    protected abstract DeviceBase set_server(Addresses server);

    public DeviceBase set_settings(Map<DeviceProps, String> changes) {

        Iterator<DeviceProps> itr = changes.keySet().iterator();
        while (itr.hasNext()) {
            DeviceProps next = itr.next();
            state.setSetting(next, changes.get(next));
        }
        dump_settings();
        return this;
    }

    public DeviceBase set_settings(DeviceProps key, String value) {
        Map<DeviceProps, String> change = new HashMap<DeviceProps, String>();
        change.put(key, value);
        set_settings(change);
        return this;
    }
    
    public DeviceBase set_settings(DeviceProps key, Integer value){
        return set_settings(key, value.toString());
    }

    public abstract DeviceBase set_speed_limit(Integer limit);


    public DeviceBase set_time(AutomationCalendar time_now) {
        state.getTime_last().setDate(state.getTime());
        state.getTime().setDate(time_now);
        MasterTest.print("Time = "+time_now, Level.DEBUG);
        return this;
    }
    


    public DeviceBase set_WMP(Integer version) {
        state.setWMP(version);
        return this;
    }

    public DeviceBase set_WMP(Object version) {
        set_WMP((Integer) version);
        return this;
    }

    public DeviceBase setDeviceDriverID(int deviceDriverID) {
        state.setDeviceDriverID(deviceDriverID);
        return this;
    }

    public DeviceBase turn_key_off(Integer time_delta) {
        if (state.getIgnition_state()) {
            set_ignition(time_delta);
        } else {
            MasterTest.print("Vehicle was already turned off");
        }
        return this;
    }

    public DeviceBase turn_key_on(Integer time_delta) {
        if (!state.getIgnition_state()) {
            set_ignition(time_delta);
        } else {
            MasterTest.print("Vehicle was already turned on");
        }
        return this;
    }
    
    public DeviceBase update_location(GeoPoint next, int value, boolean time){
        tripTracker.setNextLocation(next, value, time);
        add_location();
        is_speeding();
        return this;
    }
    
    public DeviceBase goToNextLocation(int value, boolean time){
        tripTracker.getNextLocation(value, time);
        add_location();
        is_speeding();
        return this;
    }
    

    public DeviceBase update_location(GeoPoint geoPoint, int i) {
        return update_location(geoPoint, i, true);
    }

    
    public DeviceBase last_location(GeoPoint last, int value, boolean time){
        update_location(last, value, time);
        update_location(last, 0, false);
        return this;
    }
    
    public DeviceBase last_location(GeoPoint last, int value){
        return last_location(last, value, true);
    }

    protected DeviceBase was_speeding() {
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
            GeoPoint last = speed_loc.get(i - 1);
            GeoPoint loc = speed_loc.get(i);
            speeding_distance += Math.abs(Distance_Calc.calc_distance(last, loc));
        }
        Integer distance = (int) (speeding_distance * 100);
        addSpeedingNote(distance, topSpeed, avgSpeed);
        return this;
    }
    

    protected abstract DeviceBase addSpeedingNote(Integer distance, Integer topSpeed, Integer avgSpeed);

    public void firstLocation(GeoPoint geoPoint) {
        tripTracker.addLocation(geoPoint);
    }

    public DeviceState getState() {
        return state;
    }
}
