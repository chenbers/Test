package com.inthinc.pro.automation.device_emulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.log4j.Logger;

import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.enums.DeviceProperties;
import com.inthinc.pro.automation.enums.Locales;
import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.automation.utils.AutomationCalendar.WebDateFormat;
import com.inthinc.pro.automation.utils.MD5Checksum;
import com.inthinc.pro.automation.utils.StackToString;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.exceptions.GenericHessianException;
import com.inthinc.pro.dao.hessian.exceptions.RemoteServerException;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.rally.RallyWebServices;
import com.inthinc.pro.rally.TestCaseResult;
import com.inthinc.pro.rally.TestCaseResult.Verdicts;

@SuppressWarnings("unchecked")
public abstract class Base {

    private final static Logger logger = Logger.getLogger(Base.class);

    private LinkedList<byte[]> sendingQueue = new LinkedList<byte[]>();
    protected LinkedList<byte[]> note_queue = new LinkedList<byte[]>();
    protected ArrayList<Integer> speed_points = new ArrayList<Integer>();
    protected ArrayList<Double[]> speed_loc = new ArrayList<Double[]>();

    protected Boolean ignition_state = false;
    protected Boolean power_state = false;
    protected Boolean speeding = false;
    protected Boolean rpm_violation = false;
    protected Boolean seatbelt_violation = false;

    protected Double latitude;
    protected Double longitude;
    protected Double last_lat, last_lng;
    protected Double speed_limit = 75.0;

    protected Distance_Calc calculator = new Distance_Calc();

    protected Map<DeviceProperties, String> Settings;

    protected int heading = 0;
    protected int speed = 0;
    protected int WMP = 17013, MSP = 50;
    protected int sats = 9;
    protected int odometer = 0;
    protected int note_count = 4;
    protected int productVersion = 5;
    protected int deviceDriverID;
    protected int timeSinceLastLoc = 0;
    protected int timeBetweenNotes = 15;

    protected final static int[] dbErrors = { 302, 303, 402 };

    protected long time;
    protected long time_last;

    protected MCMProxy mcmProxy;

    protected Object reply;

    protected String imei;
    
    protected Addresses portal;

    public Base(String IMEI, Addresses server, Map<?, String> map, ProductType version) {
    	portal = server;
        Settings = new HashMap<DeviceProperties, String>();
        set_IMEI(IMEI, server, map, version);
    }
    
    private Base ackFwdCmds(List<HashMap<String, Object>> reply) {

        testFwdCmdLimit(reply.size());
        
        HashMap<String, Object> fwd = new HashMap<String, Object>();

        if (!reply.isEmpty()) {
            Iterator<HashMap<String, Object>> itr = reply.iterator();
            while (itr.hasNext()) {
                fwd = itr.next();
                if (fwd.get("fwdID").equals(100)||fwd.get("fwdID").equals(1))
                    continue;
                createAckNote(fwd);
            }
        }
        return this;
    }

    private void testFwdCmdLimit(int numOfCommands) {
        String testCase = "TC875";
        String testSet = "TS96";
        String byID = "FormattedID";
        
        TestCaseResult tcr = new TestCaseResult(RallyWebServices.username, RallyWebServices.password, RallyWebServices.INTHINC);
        AutomationCalendar today = AutomationCalendar.now(WebDateFormat.RALLY_DATE_FORMAT);
        tcr.setBuildNumber(today.toString(WebDateFormat.NOTE_DATE_TIME));
        tcr.setTestCase(new NameValuePair(byID, testCase));
        tcr.setTestSet(new NameValuePair(byID, testSet));
        
        if (numOfCommands > 5 ){
            tcr.setVerdict(Verdicts.FAIL);
            tcr.send_test_case_results();
        } else if (numOfCommands == 5){
            tcr.setVerdict(Verdicts.PASS);
            tcr.updateTestCase(testCase, byID);
        }
    }


    protected abstract Base add_location();

    protected abstract Base add_note(NoteBuilder note);

    private Boolean check_error(Object reply) {
        if (reply == null)
            return false;
        try {
            reply = (Integer) reply;
        } catch (ClassCastException e) {
            return false;
        }
        for (int i = 0; i < dbErrors.length; i++) {
            if (dbErrors[i] == (Integer) reply)
                return true;
        }
        return false;
    }

    protected Base check_queue() {
        if (note_queue.size() >= get_note_count()) {
            send_note();
        }
        return this;
    }

    protected Base clear_internal_settings() {

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

    private Base configurate_device() {
    	if (portal==Addresses.TEEN_PROD){
    		return this;
    	}
        dump_settings();
        get_changes();
        return this;
    }

    protected abstract Base construct_note();

    protected abstract Base createAckNote(Map<String, Object> reply);

    public Base dump_settings() {
    	if (portal==Addresses.TEEN_PROD){
    		return this;
    	}
        assert (imei.getClass() == "".getClass());

        if (WMP >= 17013) {
            reply = dbErrors[0];
            while (check_error(reply)) {
                try {
                    System.out.println("dumping settings");
                    reply = mcmProxy.dumpSet(imei, productVersion, oursToThiers());
                    System.out.println(reply);
                } catch (GenericHessianException e) {
                    reply = 0;
                } catch (RemoteServerException e) {
                    reply = 307;
                }
            }
            if (reply instanceof Integer && (Integer) reply != 0) {
                logger.debug("Reply from dumpSet: " + reply);
            }
        }
        return this;
    }
    
    private Map<Integer, String> oursToThiers(){
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
        map.put("productVersion", this.productVersion);
        
        Map<String, Object> reply = mcmProxy.audioUpdate(this.imei, map);
        logger.debug(reply);
        try {
            File destination = new File(fileName);
            destination.mkdirs();
            destination.mkdir();
            
            FileOutputStream fw = new FileOutputStream(destination);
            fw.write((byte[]) reply.get("f"));
            fw.close();
            logger.debug("MD5 Hash is: " + MD5Checksum.getMD5Checksum(fileName));
        } catch (FileNotFoundException e) {
            logger.debug(StackToString.toString(e));
        } catch (IOException e) {
            logger.debug(StackToString.toString(e));
        } catch (Exception e) {
            logger.debug(StackToString.toString(e));
        }
        
        return false;
    }

    protected Base get_changes() {
    	if (portal==Addresses.TEEN_PROD){
    		return this;
    	}
        if (WMP >= 17013) {
            reply = dbErrors[0];
            while (check_error(reply)) {
                try {
                    reply = mcmProxy.reqSet(imei);
                } catch (EmptyResultSetException e) {
                    reply = 304;
                }
            }
            if (reply instanceof Integer && (Integer) reply != 304) {
                System.out.println(reply + " We failed to get any changes");
            } else if (reply instanceof HashMap<?, ?>) {
                set_settings( theirsToOurs((HashMap<?, ?>) reply));
            }
        }
        return this;
    }
    
    protected abstract HashMap<DeviceProperties, String> theirsToOurs(HashMap<?, ?> reply);

    protected abstract Integer get_note_count();

    public String get_setting(DeviceProperties propertyID){
        return Settings.get(propertyID);
    }

    protected Base get_time() {

        time = System.currentTimeMillis() / 1000;
        time_last = time;
        return this;
    }

    public int getDeviceDriverID() {
        return deviceDriverID;
    }

    public Base increment_time(Integer increment) {
        time_last = time;
        time += increment;
        return this;
    }

    private Base initiate_device(Addresses server) {
        ignition_state = false;

        note_queue = new LinkedList<byte[]>();
        speed_points = new ArrayList<Integer>();
        speed_loc = new ArrayList<Double[]>();
        clear_internal_settings();

        get_time();
        set_server(server);

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

    private Base is_speeding() {
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

    public Base power_off_device(Integer time_delta) {
        if (power_state) {
            increment_time(time_delta);
            set_power();
        } else {
            logger.info("The device is already off.");
        }
        logger.info("Last note created at: " +time);
        return this;
    }

    public Base power_on_device() {
        power_on_device(time);
        return this;
    }

    public Base power_on_device(Long time_now) {
        assert (latitude != 0.0 && longitude != 0.0);
        if (!power_state) {
            set_time(time_now);
            set_power();
            configurate_device();
            time_now += 30;
            set_time(time_now);
        } else {
            logger.info("The device is already on.");
        }
        return this;
    }


    protected abstract Integer processCommand(Map<String, Object> reply);

    protected Base send_note() {
        while (!note_queue.isEmpty()) {
            sendingQueue = new LinkedList<byte[]>();
            for (int i = 0; i < note_count && !note_queue.isEmpty(); i++) {
                sendingQueue.offer(note_queue.poll());
            }
            reply = dbErrors[0];
            while (check_error(reply)) {
                reply = mcmProxy.note(imei, sendingQueue);
                try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
                if (check_error(reply)){
                    logger.info("Invalid Reply from server: "+reply);
                }
                logger.info("Reply from Server: "+reply);
            }
            if (reply instanceof ArrayList<?>) {
                ackFwdCmds((List<HashMap<String, Object>>) reply);
            }
        }
        return this;
    }

    private Base set_heading() {
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

    protected abstract Base set_ignition(Integer time_delta);//

    protected Base set_IMEI(String imei, Addresses server, Map<?, String> map, ProductType version) {

        this.imei = imei;
        this.Settings = (Map<DeviceProperties, String>) map;
        set_version(version.getVersion());
        initiate_device(server);
        return this;
    }

    public Base set_location(double lat, double lng) {
        update_location(lat, lng, 0);
//        speed=0;
//        odometer=0;
        return this;
    }

    public Base set_MSP(Integer version) {
        MSP = version;
        return this;
    }

    public Base set_MSP(Object version) {
        MSP = (Integer) version;
        return this;
    }

    private Base set_odometer() {
        Double miles = calculator.calc_distance(last_lat, last_lng, latitude, longitude);
        int tenths_of_mile = (int) (miles * 100);
        odometer = tenths_of_mile;
        return this;
    }

    protected abstract Base set_power();

    public Base set_satelites(Integer satelites) {
        sats = satelites;
        return this;
    }

    protected abstract Base set_server(Addresses server);

    public Base set_settings(HashMap<?, String> changes) {

        Iterator<?> itr = changes.keySet().iterator();
        while (itr.hasNext()) {
            DeviceProperties next = (DeviceProperties) itr.next();
            Settings.put(next, changes.get(next));
        }
        dump_settings();
        return this;
    }

    public Base set_settings(DeviceProperties key, String value) {
        HashMap<DeviceProperties, String> change = new HashMap<DeviceProperties, String>();
        change.put(key, value);
        set_settings(change);
        return this;
    }
    
    public Base set_settings(DeviceProperties key, Integer value){
        return set_settings(key, value.toString());
    }

    public abstract Base set_speed_limit(Integer limit);

    public Base set_time(Date time_now) {

        Calendar cal = new GregorianCalendar();
        cal.setTime(time_now);

        set_time((Long) (cal.getTimeInMillis() / 1000));
        return this;
    }

    public Base set_time(Long time_now) {

        time = time_now;
        time_last = time;
        logger.debug("Time = "+time);
        return this;
    }
    
    public Base set_time(Integer time_now){
        return set_time(time_now.longValue());
    }

    public Base set_time(String time_now) {

        Date date = new Date();
        SimpleDateFormat df = (SimpleDateFormat) SimpleDateFormat.getInstance();
        df.applyPattern("yyyy MMM dd HH:mm:ss");
        try {
            date = df.parse(time_now);
        } catch (ParseException e) {
            logger.info("Hello, We Failed to parse your time");
            time = System.currentTimeMillis() / 1000;
        }
        logger.debug(date);
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);

        set_time((Long) (cal.getTimeInMillis() / 1000));
        return this;
    }

    private Base set_vehicle_speed() {
        Long timeDelta = (time - time_last);
        Double speed =((odometer / timeDelta.doubleValue()) * 36.0);
        this.speed=speed.intValue();
        is_speeding();
        return this;
    }

    public Base set_version(Integer version) {
        assert (version == 1 || version == 2 || version == 3 || version == 5);
        productVersion = version;
        return this;
    }

    public Base set_WMP(Integer version) {
        WMP = version;
        return this;
    }

    public Base set_WMP(Object version) {
        WMP = (Integer) version;
        return this;
    }

    public Base setDeviceDriverID(int deviceDriverID) {
        this.deviceDriverID = deviceDriverID;
        return this;
    }

    public Base turn_key_off(Integer time_delta) {
        if (ignition_state) {
            set_ignition(time_delta);
        } else {
            logger.info("Vehicle was already turned off");
        }
        return this;
    }

    public Base turn_key_on(Integer time_delta) {
        if (!ignition_state) {
            set_ignition(time_delta);
        } else {
            logger.info("Vehicle was already turned on");
        }
        return this;
    }

    public Base update_location(double lat, double lng, Integer time_delta) {
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
    
    public Base last_location(double lat, double lng, Integer time_delta){
        update_location(lat, lng, time_delta);
        speed = 0;
        return this;
    }

    protected abstract Base was_speeding();


}
