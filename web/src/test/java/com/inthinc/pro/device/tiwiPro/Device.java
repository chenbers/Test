package com.inthinc.pro.device.tiwiPro;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.inthinc.pro.dao.hessian.extension.HessianTCPProxyFactory;
import com.inthinc.pro.device.*;

@SuppressWarnings("unchecked")
public class Device {
	
	private ArrayList<byte[]> sendingQueue = new ArrayList<byte[]>();
	private ArrayList<byte[]> note_queue = new ArrayList<byte[]>();
    private ArrayList<Integer> speed_points = new ArrayList<Integer>();
    private ArrayList<Double[]> speed_loc = new ArrayList<Double[]>();
    
    private Boolean ignition_state = false;
    private Boolean power_state = false;
    private Boolean speeding = false;
    private Boolean rpm_violation = false;
    private Boolean seatbelt_violation = false;
    
    private Double lat, lng, last_lat, last_lng;
    
    private Distance_Calc calculator = new Distance_Calc();
    
    public HashMap<Integer, String> Settings;
    HashMap<Constants, Integer> attrs;

    private int heading=0;
    private int speed_limit, speed;
    private int WMP=17013, MSP = 50;
    private int trip_start, trip_stop, sats;
    private int odometer;
    private int note_count = 0;
    private int productVersion = 5;
    private int deviceDriverID;

    private final int[] dbErrors = { 302, 303, 402 };
    
    private long time, time_last;
    
    private MCMProxy mcmProxy;
    

	private Object reply;

    private String imei;
	private Double longitude;
	private Double latitude;
    
    
    public Device( String IMEI, String server, HashMap<Integer, String> settings, Integer version ){
       	set_IMEI(IMEI, server, settings, version);
        Settings = settings;
    }
    
    public Device( String IMEI, String server, HashMap<Integer, String> settings ){
    	this(IMEI, server, settings, 5);
    }
    
    public Device( String IMEI, String server ){
    	this(IMEI, server, Defaults.get_defaults(), 5);
    }

    public Device( String IMEI ){
    	this(IMEI, "QA", Defaults.get_defaults(), 5);
    }
    
    public void ackFwdCmds( List<HashMap<String, Object>> reply){
    	try{ assert(reply.size()<=5);
    	}catch(AssertionError e){
    		e.printStackTrace();
    		System.out.println("The server is sending more than the 5 fwdCmd limit!!!");
    	}
    	Integer fwdID, fwdCmd;
    	Object fwdData;
    	HashMap<String, Object> fwd = new HashMap<String, Object>();
    	Package_Note ackNote;
    	
    	if (!reply.isEmpty()){
    		Iterator<HashMap<String, Object>> itr = reply.iterator();
    		while (itr.hasNext()){
    			fwd = itr.next();
    			fwdID = (Integer) fwd.get("fwdID");
    			if (fwdID <= 100 ) continue;
    			fwdCmd = (Integer) fwd.get("fwdCmd");
    			fwdData = fwd.get("fwdData");
    			
    			ackNote = new Package_Note(Constants.NOTE_TYPE_STRIPPED_ACKNOWLEDGE_ID_WITH_DATA);
    			ackNote.AddAttrs(Constants.ATTR_TYPE_FWDCMD_ID, fwdID);
    			ackNote.AddAttrs(Constants.ATTR_TYPE_FWDCMD_ID, Constants.FWDCMD_RECEIVED);
    			byte[] packaged = ackNote.Package();
    	        note_queue.add(packaged);
    			
    			processCommand(fwdID, fwdCmd, fwdData);	
    		}
    	}
    }

	public void add_location(){
        attrs = new HashMap<Constants, Integer>();
        if (speeding){ 
            attrs.put( Constants.ATTR_TYPE_VIOLATION_FLAGS, Constants.VIOLATION_MASK_SPEEDING.getCode() );
        }
        
        else if (rpm_violation){ 
            attrs.put( Constants.ATTR_TYPE_VIOLATION_FLAGS, Constants.VIOLATION_MASK_RPM.getCode() );
        }
        
        else if (seatbelt_violation){ 
            attrs.put( Constants.ATTR_TYPE_VIOLATION_FLAGS, Constants.VIOLATION_MASK_SEATBELT.getCode() );
        }
        
        construct_note( Constants.NOTE_TYPE_LOCATION, attrs );
    }
	

	public void add_note( Package_Note note ){
        
        byte[] packaged = note.Package();
        note_queue.add(packaged);
        check_queue();
    }
    
    
    private void initiate_device(String server ){
        ignition_state = false;
        
        speed_limit = Integer.parseInt(Settings.get( Constants.PROPERTY_SPEED_LIMIT.getCode()));
        note_queue = new ArrayList<byte[]>();
        speed_points = new ArrayList<Integer>();
        speed_loc = new ArrayList<Double[]>();
        clear_internal_settings();
        
        get_time();
        set_url(server);
        set_server();
        


        set_satelites( 8 );
        set_location( 0.0, 0.0 );
        set_WMP( 17014 );
        set_MSP( 50 );
        set_vehicle_speed();
        
        ignition_state = false;
        power_state = false;
        speeding = false;
        rpm_violation = false;
        seatbelt_violation = false;
        speeding = false;
    }
    


    
    public void construct_note(Constants type, HashMap<Constants, Integer> attrs){
    	    	
    	Package_Note note = new Package_Note(type, time, sats, heading,
    			1, latitude, longitude, speed, odometer, attrs);
    	clear_internal_settings();
    	add_note(note);
    }
    public void construct_note(Constants type){
    	attrs = new HashMap<Constants,Integer>();
    	try{attrs.put(Constants.ATTR_TYPE_SPEED_LIMIT, speed_limit);}
    	catch (Exception e){
    		e.printStackTrace();
    	}
    	construct_note(type, attrs);
    }
    
    public void change_IMEI( String imei, String server, HashMap<Integer, String> settings, Integer version){
        set_IMEI( imei, server, settings, version );
    }
    
    public void change_IMEI( String imei, String server, HashMap<Integer, String> settings){
        set_IMEI( imei, server, settings, 5 );
    }
    
    public void change_IMEI( String imei, String server){
        set_IMEI( imei, server, Defaults.get_defaults(), 5 );
    }
    
    public void change_IMEI( String imei, Integer version){
        set_IMEI( imei, "QA", Defaults.get_defaults(), version );
    }
    
    public void change_IMEI( String imei){
        set_IMEI( imei, "QA", Defaults.get_defaults(), 5 );
    }
    
    private void check_queue(){
        
        if ( note_queue.size() >= Integer.parseInt( Settings.get( Constants.PROPERTY_SET_MSGS_PER_NOTIFICATION.getCode() ) ) ){
        	send_note();
        }
    
    }
    
    private void clear_internal_settings(){
        
        odometer = 0;
        speed = 0;
        try{
            last_lat = lat;
            last_lng = lng;
            
        } catch(NullPointerException e){
            last_lat = 0.0;
            last_lng = 0.0;
        }
    }
    

    private void configurate_device() {
    	dump_settings();
    	get_changes();		
	}
    

	private void dump_settings() {
		if (WMP >= 17013){
			reply = dbErrors[0];
			while (dbErrors.toString().compareTo(reply.toString())==0){
				reply = mcmProxy.dumpSet(imei, productVersion, Settings);
			}
			if (reply.getClass()==int.class){
				System.out.println(reply);
			}
		}
	}
	

	private void get_changes() {
		if (WMP >= 17013){
			reply = dbErrors[0];
			while (dbErrors.toString().compareTo(reply.toString())==0){
				reply = mcmProxy.reqSet( imei );
			}
			if (reply.getClass()==int.class || Integer.parseInt(reply.toString())!= 304){
				System.out.println(reply);
			}
			else if (reply instanceof HashMap<?,?>){
				set_settings((HashMap<Integer, String>)reply);
			}
		}
	}

	
    public void get_time(){
        
        time = System.currentTimeMillis() / 1000;
        time_last = time;
    }
    
	public int getDeviceDriverID() {
		return deviceDriverID;
	}
	
	private void heading(){
		Integer direction = calculator.get_heading(last_lat, last_lng, latitude, longitude);
//		if (productVersion==5){
			Integer[] headers = {0,45,90,135,180,225,270,315,360};
//		}
//		else if (productVersion==2){
//			Integer[] headers = {0,45,90,135,180,225,270,315,360};
//		}
		
		for (int heading=0;heading<headers.length;heading++){
			if (direction < headers[heading+1]){
				Integer deltaA = Math.abs( headers[heading]-direction);
				Integer deltaB = Math.abs( headers[heading+1]-direction);
				Integer winner = Math.min(deltaA,deltaB);
				if (winner == deltaA){
					direction = heading;
					break;
				}
				else if(winner == deltaB){
					direction = heading+1;
					break;
				}
			}
		}
		if (direction==9)direction=0;
		this.heading = direction;
	}
    
    public void increment_time( Integer increment ){
        
        time_last = time;
        time += increment;
    }
    
    private void is_speeding() {
    	Double[] point = {latitude, longitude};
		if (speed > speed_limit && !speeding){
			speeding = true;
			speed_loc.add(point);
			speed_points.add(speed);
		}
		else if(speed>speed_limit && speeding){
			speed_loc.add(point);
			speed_points.add(speed);
		}
		else if(speed<speed_limit && speeding){
			speeding = false;
			speed_loc.add(point);
			speed_points.add(speed);
			was_speeding();
		}
	}
   

	public void power_on_device(Integer time_now){
        
    	set_time( time_now );
        set_power();
        configurate_device();
        time_now += 30;
        set_time( time_now );
    }

	public void power_off_device(){
        
        set_power();
    }
	
    private void processCommand(Integer fwdID, Integer fwdCmd, Object fwdData) {
    	HashMap<Integer, String> changes = new HashMap<Integer, String>();
    	Package_Note ackNote = new Package_Note(Constants.NOTE_TYPE_STRIPPED_ACKNOWLEDGE_ID_WITH_DATA);
    	
		if (fwdCmd==Constants.FWD_CMD_ASSIGN_DRIVER.getCode()){
			String[] values = fwdData.toString().split(" ");
			setDeviceDriverID(Integer.parseInt(values[0]));
		}
		else if (fwdCmd==Constants.FWD_CMD_DUMP_CONFIGURATION.getCode()) dump_settings();
		else if (fwdCmd==Constants.FWD_CMD_UPDATE_CONFIGURATION.getCode()) get_changes();
		else if (fwdCmd==Constants.FWD_CMD_DOWNLOAD_NEW_WITNESSII_FIRMWARE.getCode()) set_MSP((Integer) fwdData);
		else if (fwdCmd==Constants.FWD_CMD_DOWNLOAD_NEW_FIRMWARE.getCode()) set_WMP((Integer) fwdData);
		else if (fwdCmd==Constants.FWD_CMD_SET_SPEED_BUFFER_VALUES.getCode()){
			changes.put(Constants.PROPERTY_VARIABLE_SPEED_LIMITS.getCode(),(String) fwdData);
		}
		
		ackNote.AddAttrs(Constants.ATTR_TYPE_FWDCMD_ID, fwdID);
		ackNote.AddAttrs(Constants.ATTR_TYPE_FWDCMD_STATUS, Constants.FWDCMD_FLASH_SUCCESS);
		byte[] packaged = ackNote.Package();
        note_queue.add(packaged);
		
		set_settings(changes);
	}
    
	private void send_note() {
		
		assert(note_queue.getClass()==new ArrayList<byte[]>().getClass());
		assert(imei.getClass() == "".getClass());
		
		while (!note_queue.isEmpty()){
			for (int i=0; i < note_count; i++){
				sendingQueue.add(note_queue.remove(0));
			}
			reply = dbErrors[0];
			while (dbErrors.toString().compareTo(reply.toString())==0){
				reply = mcmProxy.note(imei, sendingQueue);
			}
			if (reply instanceof Integer){
				System.out.println(reply.toString());
			}
			else if (reply instanceof ArrayList<?>){
				ackFwdCmds((List<HashMap<String, Object>>) reply);
			}
		}
	}
	

	public void setDeviceDriverID(int deviceDriverID) {
		this.deviceDriverID = deviceDriverID;
	}
    
    private void set_IMEI( String imei, String server, HashMap<Integer, String> settings, Integer version ){
        
        this.imei = imei;
        set_version(version);
        set_settings(settings);        
        initiate_device( server );
    }


    public void set_ignition( Integer time_delta){
    	ignition_state = !ignition_state;
    	Integer newTime = (int)(time + time_delta);
    	set_time(newTime);
    	if (ignition_state){
    		construct_note(Constants.NOTE_TYPE_IGNITION_ON);
    		trip_start = newTime;
    	}
    	else if(!ignition_state){
    		trip_stop=newTime;
    		Integer tripTime = trip_stop - trip_start;
    		attrs = new HashMap<Constants, Integer>();
    		attrs.put(Constants.ATTR_TYPE_TRIP_DURATION, tripTime);
    		attrs.put(Constants.ATTR_TYPE_PERCENTAGE_OF_POINTS_THAT_PASSED_THE_FILTER_, 980);
    		construct_note(Constants.NOTE_TYPE_IGNITION_OFF, attrs);
    	}
    }
	
    
    private void set_location(double lat, double lng, Integer time_delta) {
		try{
			if (last_lat != latitude)last_lat = latitude;
		}catch(Exception e){
			last_lat = lat;
		}try{
			if (last_lng != longitude)last_lng = longitude;
		}catch(Exception e){
			last_lng = lng;
		}
		
		latitude = lat;longitude = lng;
		heading();
		Integer new_time = (int)(time + time_delta);
		set_time(new_time);
		
		if ( latitude != last_lat || longitude != last_lng){
			set_odometer();
			set_vehicle_speed();
		}
				
	}
	private void set_location(double lat, double lng){
    	set_location(lat, lng, 0);
    }
	

	public void set_MSP( Integer version ){
        
        MSP = version;
    }

	private void set_odometer() { 
		Double miles = calculator.calc_distance(last_lat, last_lng, latitude, longitude);
		int tenths_of_mile = (int)(miles*100);
		odometer = tenths_of_mile;
	}

    
    private void set_power(){
        
        attrs = new HashMap<Constants, Integer>();
        
        power_state = !power_state; // Change the power state between on and off
        if (power_state){
            attrs.put(Constants.ATTR_TYPE_FIRMWARE_VERSION, WMP);
            attrs.put(Constants.ATTR_TYPE_DMM_VERSION, MSP);
            attrs.put(Constants.ATTR_TYPE_GPS_LOCK_TIME, 10);
            construct_note( Constants.NOTE_TYPE_POWER_ON, attrs );
            check_queue();
            
        } else if (!power_state){
            attrs.put(Constants.ATTR_TYPE_LOW_POWER_MODE_TIMEOUT, Integer.parseInt(Settings.get(Constants.PROPERTY_LOW_POWER_MODE_SECONDS.getCode())));
            construct_note( Constants.NOTE_TYPE_LOW_POWER_MODE, attrs );
            check_queue();
            if (note_queue.size() != 0) send_note();
        }
        
    }
    

	public void set_satelites( Integer satelites ){
        
        sats = satelites;
    }

    private void set_server(){
            
        HessianTCPProxyFactory factory = new HessianTCPProxyFactory();
        try {
        	if (productVersion==5){        		
        		mcmProxy = (MCMProxy)factory.create( MCMProxy.class, 
			        Settings.get(Constants.PROPERTY_SERVER_URL.getCode()), 
			        Integer.parseInt(Settings.get(Constants.PROPERTY_SERVER_PORT.getCode())));
        	}else if (productVersion==2){
        		mcmProxy = (MCMProxy)factory.create( MCMProxy.class, 
			        Settings.get(Constants.PROPERTY_SERVER_URL.getCode()), 
			        Integer.parseInt(Settings.get(Constants.PROPERTY_SERVER_PORT.getCode())));	
        	}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
        
    }

    public void set_speed_limit( Integer limit ){
    	set_settings(Constants.PROPERTY_SPEED_LIMIT, limit.toString());    	
    }
    
  
    private void set_settings( HashMap<Integer, String> changes){

        Iterator<Integer> itr = changes.keySet().iterator();
        while (itr.hasNext()){
        	Integer next = itr.next();
            Settings.put(next, changes.get(next));
        }
        dump_settings();
    }
    private void set_settings( Integer key, String value){
    	HashMap<Integer, String> change = new HashMap<Integer, String>();
    	change.put(key, value);
    	set_settings(change);
    }
    private void set_settings( Constants key, String value){
    	set_settings( key.getCode(), value);
    }
    
    public void set_time( Date time_now ){
        
        Calendar cal = new GregorianCalendar();
        cal.setTime(time_now);
        
        time = cal.getTimeInMillis()/1000;
        time_last = time;
    }
      
    
    public void set_time( Integer time_now ){
        
        time = time_now;
        time_last = time;
    }
    
    public void set_time( String time_now ){
        
        Date date = new Date();
        SimpleDateFormat df = (SimpleDateFormat) SimpleDateFormat.getInstance();
        df.applyPattern("yyyy MMM dd HH:mm:ss");
        try{ date = df.parse(time_now); }
        catch (ParseException e){
            System.out.println("Hello, We Failed to parse your time");
            time = System.currentTimeMillis()/1000;
        }
        System.out.println(date);
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        
        time = cal.getTimeInMillis()/1000;
        time_last = time;
    }
    
    public void set_url( String url, String port ){
    	HashMap<Integer, String> changes = new HashMap<Integer, String>();
        
        if (productVersion==5){
        	changes.put(Constants.PROPERTY_SERVER_PORT.getCode(), port);
        	changes.put(Constants.PROPERTY_SERVER_URL.getCode(), url);
        }
//        else if (productVersion==2){
//        	changes.put(key, value);
//        }
        
        set_settings(changes);
        set_server();
    }
    
    
    public void set_url(String server){
        Addresses port = Addresses.QA_MCM_PORT;
        Addresses url = Addresses.QA_MCM;
        
        
        if (server.compareToIgnoreCase("dev")==0){
            url = Addresses.DEV_MCM_PORT;
            port = Addresses.DEV_MCM;
        }
        
        else if (server.compareToIgnoreCase("qa")==0){
            url = Addresses.QA_MCM;
            port = Addresses.QA_MCM_PORT;
        }
        
        else if (server.compareToIgnoreCase("qa2")==0){
            url = Addresses.QA2_MCM;
            port = Addresses.QA2_MCM_PORT;
        }
        
        else if (server.compareToIgnoreCase("prod")==0){
            url = Addresses.PROD_MCM;
            port = Addresses.PROD_MCM_PORT;
            
        }
        else if (server.compareToIgnoreCase("teen_qa")==0){
            url = Addresses.TEEN_MCM_QA;
            port = Addresses.TEEN_MCM_PORT_QA;
        }
        
//        else if (server.compareToIgnoreCase("teen_prod")==0){
//      	  url = Addresses.;
//            port = Addresses.;
//            
//        }
//        else if (server.compareToIgnoreCase("teen_dev")==0){
//      	  url = Addresses.;
//            port = Addresses.;

        set_url(url, port);

    }
    
	private void set_url(Addresses url, Addresses port) {
		set_url(url.getCode(),port.getCode());
	}

	private void set_vehicle_speed(){
		if (speeding){
			Integer timeDelta = (int)(time - time_last);
			Double distance = calculator.calc_distance(last_lat, last_lng, latitude, longitude);
			try{speed = (int)( (distance/timeDelta)*3600);
			}catch (Exception e){
				speed = 0;
			}
		}
		is_speeding();
	}
	
	public void set_version(Integer version){
		assert(version==1||version==2||version==3||version==5);
		productVersion = version;
	}
    
    

	public void set_WMP( Integer version ){
        
        WMP = version;
    }
    
    private void was_speeding() {
		Integer topSpeed = Collections.max(speed_points);
		Integer avgSpeed = 0;
		Double speeding_distance = 0.0;
		for (int i=0; i<speed_points.size();i++){
			avgSpeed += speed_points.get(i);
		}
		for (int i=1; i<speed_loc.size();i++){
			Double[] last = speed_loc.get(i-1);
			Double[] loc = speed_loc.get(i);
			speeding_distance += Math.abs(calculator.calc_distance(last[0],last[1],loc[0],loc[1]));
		}
		Integer distance = (int)(speeding_distance * 100);
		attrs = new HashMap<Constants, Integer>();
		attrs.put(Constants.ATTR_TYPE_DISTANCE, distance);
		attrs.put(Constants.ATTR_TYPE_TOP_SPEED, topSpeed);
		attrs.put(Constants.ATTR_TYPE_AVG_SPEED, avgSpeed);
		attrs.put(Constants.ATTR_TYPE_SPEED_ID, 9999);
		attrs.put(Constants.ATTR_TYPE_VIOLATION_FLAGS, 1);
		
		construct_note(Constants.NOTE_TYPE_SPEEDING_EX3, attrs);
	}

	
}
