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

import com.inthinc.pro.automation.device_emulation.NoteManager.DeviceNote;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.enums.Locales;
import com.inthinc.pro.automation.interfaces.DeviceProperties;
import com.inthinc.pro.automation.models.MCMProxyObject;
import com.inthinc.pro.automation.models.MapSection;
import com.inthinc.pro.automation.models.NoteBC;
import com.inthinc.pro.automation.models.NoteBC.Direction;
import com.inthinc.pro.automation.models.NoteWS;
import com.inthinc.pro.automation.models.TiwiNote;
import com.inthinc.pro.automation.resources.DeviceStatistics;
import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.automation.utils.AutomationCalendar.WebDateFormat;
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
    protected final ArrayList<Double[]> speed_loc;
    protected final String imei;
    protected final Distance_Calc calculator;
    protected final AutomationCalendar time;
    protected final AutomationCalendar time_last;
    protected final ProductType productVersion;


    protected Boolean ignition_state = false;
    protected Boolean power_state = false;
    protected Boolean speeding = false;
    protected Boolean rpm_violation = false;
    protected Boolean seatbelt_violation = false;

    protected Double latitude;
    protected Double longitude;
    protected Double last_lat, last_lng;
    protected Double speed_limit = 75.0;


    protected Map<DeviceProperties, String> Settings;

    protected int heading = 0;
    protected int speed = 0;
    protected int WMP = 17013, MSP = 50;
    protected int sats = 9;
    protected int odometer = 0;

    protected int note_count = 4;
    protected int deviceDriverID;
    protected int timeSinceLastLoc = 0;
    protected int timeBetweenNotes = 15;


    protected MCMProxyObject mcmProxy;

    protected Object reply;

    protected String mcmID;
    protected String lastDownload;
    
    protected Addresses portal;
    protected Map<Integer, MapSection> sbsModule;

    protected Direction waysDirection = Direction.wifi;

    
    private DeviceBase(String IMEI, ProductType version){
        this.imei = IMEI;
        sbsModule = new HashMap<Integer, MapSection>();
        speed_points = new ArrayList<Integer>();
        speed_loc = new ArrayList<Double[]>();
        notes = new NoteManager();
        time = new AutomationCalendar();
        time_last = new AutomationCalendar();
        productVersion = version;
        calculator = new Distance_Calc();
    }

    public DeviceBase(String IMEI, Addresses server, Map<? extends DeviceProperties, String> map, ProductType version) {
        this(IMEI, version);
    	portal = server;
        Settings = new HashMap<DeviceProperties, String>();
        set_IMEI(map);
        
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
        check_queue();
        return this;
    }

    protected DeviceBase check_queue() {
        if (notes.size() >= get_note_count()) {
            send_note();
        }
        return this;
    }

    protected DeviceBase clear_internal_settings() {

        odometer = 0;
        try {
            last_lat = latitude;
            last_lng = longitude;

        } catch (NullPointerException e) {
            last_lat = 0.0;
            last_lng = 0.0;
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
        while (notes.hasNext())
            send_note();
    }
    
    protected Map<Integer, String> oursToThiers(){
        Map<Integer, String> map = new HashMap<Integer, String>();
        Iterator<?> itr = Settings.keySet().iterator();
        while (itr.hasNext()){
            DeviceProperties next = (DeviceProperties) itr.next();
            map.put(next.getValue(), Settings.get(next));
        }
        
        return map;
    }
    
    public boolean getAudioFile(String fileName, int fileVersion, Locales locale){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hardwareVersion", this.WMP);
        map.put("fileVersion", fileVersion);
        map.put("locale", locale.toString());
        map.put("productVersion", this.productVersion.getVersion());
        return writeTiwiFile(fileName, mcmProxy.audioUpdate(this.imei, map));
    }
    
    
    protected boolean writeTiwiFile(String fileName, Map<String, Object> reply){
        MasterTest.print(reply, Level.DEBUG);
        if (!fileName.startsWith("target")){
            String resourceFile = "target/test/resources/" + imei + "/" + "downloads/";
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

    public int getOdometer() {
        return odometer;
    }

    public void setOdometer(int odometer) {
        this.odometer = odometer;
    }
    
    protected abstract HashMap<DeviceProperties, String> theirsToOurs(HashMap<?, ?> reply);
    
    protected abstract Integer get_note_count();

    public String get_setting(DeviceProperties propertyID){
        return Settings.get(propertyID);
    }

    public int getDeviceDriverID() {
        return deviceDriverID;
    }

    public DeviceBase increment_time(Integer increment) {
        setLastTime(time);
        time.addToSeconds(increment);
        return this;
    }
    
    protected DeviceBase setLastTime(AutomationCalendar time){
        time_last.setDate(time);
        return this;
    }

    private DeviceBase initiate_device() {
        ignition_state = false;
        set_time(new AutomationCalendar());
        clear_internal_settings();
        
        set_server(portal);

        set_satelites(8);
        set_location(0.0, 0.0);
        set_WMP(17014);
        set_MSP(50);
        set_vehicle_speed();

        ignition_state = false;
        power_state = false;
        speeding = false;
        rpm_violation = false;
        seatbelt_violation = false;
        speeding = false;
        return this;
    }

    private DeviceBase is_speeding() {
        Double[] point = { latitude, longitude };
        if (speed > speed_limit && !speeding) {
            speeding = true;
            speed_loc.add(point);
            speed_points.add(speed);
        } else if (speed > speed_limit && speeding) {
            speed_loc.add(point);
            speed_points.add(speed);
        } else if (speed < speed_limit && speeding) {
            speeding = false;
            speed_loc.add(point);
            speed_points.add(speed);
            was_speeding();
        }
        return this;
    }

    public DeviceBase power_off_device(Integer time_delta) {
        if (power_state) {
            increment_time(time_delta);
            set_power();
        } else {
            MasterTest.print("The device is already off.");
        }
        MasterTest.print("Last note created at: " + time.epochSecondsInt());
        return this;
    }

    public DeviceBase power_on_device() {
        power_on_device(time);
        return this;
    }

    public DeviceBase power_on_device(AutomationCalendar time_now) {
        assert (latitude != 0.0 && longitude != 0.0);
        if (!power_state) {
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
                String sendingImei = imei;
                Class<?> noteClass = DeviceNote.class;
                try{
                    if (!waysDirection.equals(Direction.sat)){
                        sendingImei = mcmID;
                    }
                    if (sendingQueue.containsKey(NoteBC.class)){
                        noteClass = NoteBC.class;
                        reply = mcmProxy.notebc(sendingImei, waysDirection.getValue(), sendingQueue.get(noteClass), true);
                    } else if (sendingQueue.containsKey(NoteWS.class)){
                        noteClass = NoteWS.class;
                        reply = mcmProxy.notews(sendingImei, waysDirection.getValue(), sendingQueue.get(noteClass), true);
                    } else if (sendingQueue.containsKey(TiwiNote.class)){
                        noteClass = TiwiNote.class;
                        reply = mcmProxy.note(imei, sendingQueue.get(noteClass), true);
                    }
                    sendingQueue.remove(noteClass); 
                } catch (Exception e) {
                    MasterTest.print("Error from Note with IMEI: " + imei + "  " + StackToString.toString(e) + 
                            "\nCurrent Note Count is " + DeviceStatistics.getHessianCalls()+
                            "\nCurrent time is: " + System.currentTimeMillis() +
                            "\nNotes Started at: " + DeviceStatistics.getStart().epochTime(), Level.ERROR);
                    loop ++;
                    if (loop == 30){
                        MasterTest.print("Unable to send note!!!!!" + StackToString.toString(e), Level.FATAL);
                        break;
                    }
                }

                if (reply instanceof ArrayList<?>) {
                    ackFwdCmds((List<HashMap<String, Object>>) reply);
                }
                MasterTest.print("Reply from Server: " + reply, Level.DEBUG);
            }
        }
        return this;
    }

    private DeviceBase set_heading() {
        Integer direction = calculator.get_heading(last_lat, last_lng, latitude, longitude);
        // if (productVersion==5){
        Integer[] headers = { 0, 45, 90, 135, 180, 225, 270, 315, 360 };
        // }
        // else if (productVersion==2){
        // Integer[] headers = {0,45,90,135,180,225,270,315,360};
        // }

        for (int heading = 0; heading < headers.length - 1; heading++) {
            if (direction < headers[heading + 1]) {
                Integer deltaA = Math.abs(headers[heading] - direction);
                Integer deltaB = Math.abs(headers[heading + 1] - direction);
                Integer winner = Math.min(deltaA, deltaB);
                if (winner == deltaA) {
                    direction = heading;
                    break;
                } else if (winner == deltaB) {
                    direction = heading + 1;
                    break;
                }
            }
        }
        if (direction == 9)
            direction = 0;
        this.heading = direction;
        return this;
    }

    protected abstract DeviceBase set_ignition(Integer time_delta);//

    @SuppressWarnings("unchecked")
    protected DeviceBase set_IMEI(Map<? extends DeviceProperties, String> map) {
        this.Settings = (Map<DeviceProperties, String>) map;
        initiate_device();
        return this;
    }

    public DeviceBase set_location(double lat, double lng) {
        update_location(lat, lng, 0);
//        speed=0;
//        odometer=0;
        return this;
    }

    public DeviceBase set_MSP(Integer version) {
        MSP = version;
        return this;
    }

    public DeviceBase set_MSP(Object version) {
        MSP = (Integer) version;
        return this;
    }

    private DeviceBase set_odometer() {
        Double miles = calculator.calc_distance(last_lat, last_lng, latitude, longitude);
        int tenths_of_mile = (int) (miles * 100);
        odometer = tenths_of_mile;
        return this;
    }

    protected abstract DeviceBase set_power();

    public DeviceBase set_satelites(Integer satelites) {
        sats = satelites;
        return this;
    }

    protected abstract DeviceBase set_server(Addresses server);

    public DeviceBase set_settings(HashMap<?, String> changes) {

        Iterator<?> itr = changes.keySet().iterator();
        while (itr.hasNext()) {
            DeviceProperties next = (DeviceProperties) itr.next();
            Settings.put(next, changes.get(next));
        }
        dump_settings();
        return this;
    }

    public DeviceBase set_settings(DeviceProperties key, String value) {
        HashMap<DeviceProperties, String> change = new HashMap<DeviceProperties, String>();
        change.put(key, value);
        set_settings(change);
        return this;
    }
    
    public DeviceBase set_settings(DeviceProperties key, Integer value){
        return set_settings(key, value.toString());
    }

    public abstract DeviceBase set_speed_limit(Integer limit);


    public DeviceBase set_time(AutomationCalendar time_now) {
        time.setDate(time_now);
        setLastTime(time);
        MasterTest.print("Time = "+time, Level.DEBUG);
        return this;
    }
    

    private DeviceBase set_vehicle_speed() {
        Long timeDelta = (time.getDelta(time_last));
        Double speed =((odometer / timeDelta.doubleValue()) * 36.0);
        this.speed=speed.intValue();
        is_speeding();
        return this;
    }

    public DeviceBase set_WMP(Integer version) {
        WMP = version;
        return this;
    }

    public DeviceBase set_WMP(Object version) {
        WMP = (Integer) version;
        return this;
    }

    public DeviceBase setDeviceDriverID(int deviceDriverID) {
        this.deviceDriverID = deviceDriverID;
        return this;
    }

    public DeviceBase turn_key_off(Integer time_delta) {
        if (ignition_state) {
            set_ignition(time_delta);
        } else {
            MasterTest.print("Vehicle was already turned off");
        }
        return this;
    }

    public DeviceBase turn_key_on(Integer time_delta) {
        if (!ignition_state) {
            set_ignition(time_delta);
        } else {
            MasterTest.print("Vehicle was already turned on");
        }
        return this;
    }

    public DeviceBase update_location(double lat, double lng, Integer time_delta) {
        timeSinceLastLoc += time_delta;
        try {
            if (last_lat != latitude)
                last_lat = latitude;
            else if (last_lat == null)
                last_lat = lat;
        } catch (Exception e) {
            last_lat = lat;
        }
        try {
            if (last_lng != longitude)
                last_lng = longitude;
            else if (last_lng == null)
                last_lng = lat;
        } catch (Exception e) {
            last_lng = lng;
        }
        latitude = lat;
        longitude = lng;
        increment_time(time_delta);

        set_heading();

        if ((latitude != last_lat || longitude != last_lng) && (last_lat != 0.0 && last_lng != 0.0)) {
            set_odometer();
        }
        if (time_delta!=0){
            set_vehicle_speed();
        }
        if (timeSinceLastLoc >= timeBetweenNotes) {
            add_location();
        }
        return this;
    }
    
    public DeviceBase last_location(double lat, double lng, Integer time_delta){
        update_location(lat, lng, time_delta);
        speed = 0;
        return this;
    }

    protected abstract DeviceBase was_speeding();


}
