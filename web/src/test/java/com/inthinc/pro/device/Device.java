package com.inthinc.pro.device;

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

import org.junit.runner.notification.StoppedByUserException;

import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.exceptions.GenericHessianException;
import com.inthinc.pro.dao.hessian.extension.HessianTCPProxyFactory;
import com.inthinc.pro.device.tiwiPro.TiwiPro_Defaults;
import com.inthinc.pro.device.tiwiPro.Package_tiwiPro_Note;
import com.inthinc.pro.device.waysmart.Package_Waysmart_Note;
import com.inthinc.pro.device.tiwiPro.TiwiPro;

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
        
    private Double latitude, longitude, last_lat, last_lng;
    private Double speed_limit;
    
    private Distance_Calc calculator = new Distance_Calc();
    
    public HashMap<Integer, String> Settings;
    HashMap<TiwiPro, Integer> attrs;

    private int heading=0;
    private int speed;
    private int WMP=17013, MSP = 50;
    private int trip_start, trip_stop, sats;
    private int odometer;
    private int note_count = 4;
    private int productVersion = 5;
    private int deviceDriverID;

    private final int[] dbErrors = { 302, 303, 402 };
    
    private long time, time_last;
    
    private MCMProxy mcmProxy;
    

	private Object reply;

    private String imei;
    
    
    public Device( String IMEI, String server, HashMap<Integer, String> settings, Integer version ){
    	Settings = new HashMap<Integer, String>();
       	set_IMEI(IMEI, server, settings, version);
    }
    
    public Device( String IMEI, String server, HashMap<Integer, String> settings ){
    	this(IMEI, server, settings, 5);
    }
    
    public Device( String IMEI, String server ){
    	this(IMEI, server, TiwiPro_Defaults.get_defaults(), 5);
    }

    public Device( String IMEI ){
    	this(IMEI, "QA", TiwiPro_Defaults.get_defaults(), 5);
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
    	
    	if (!reply.isEmpty()){
    		Iterator<HashMap<String, Object>> itr = reply.iterator();
    		while (itr.hasNext()){
    			fwd = itr.next();
    			fwdID = (Integer) fwd.get("fwdID");
    			if (fwdID <= 100 ) continue;
    			fwdCmd = (Integer) fwd.get("fwdCmd");
    			fwdData = fwd.get("fwdData");
    			
    			if (productVersion==5){
    				Package_tiwiPro_Note ackNote = new Package_tiwiPro_Note(TiwiPro.NOTE_TYPE_STRIPPED_ACKNOWLEDGE_ID_WITH_DATA);
	    			ackNote.AddAttrs(TiwiPro.ATTR_TYPE_FWDCMD_ID, fwdID);
	    			ackNote.AddAttrs(TiwiPro.ATTR_TYPE_FWDCMD_STATUS, TiwiPro.FWDCMD_RECEIVED);
	    			byte[] packaged = ackNote.Package();
	    	        note_queue.add(packaged);
    			}
//    			else if (productVersion==2){
//    				Package_Waysmart_Note ackNote = new Package_Waysmart_Note(Waysmart..getCode());
//        			byte[] packaged = ackNote.Package();
//        	        note_queue.add(packaged);
//    			}
    			
    			processCommand(fwdID, fwdCmd, fwdData);	
    		}
    	}
    }

	public void add_location(){
        attrs = new HashMap<TiwiPro, Integer>();
        if (speeding){ 
            attrs.put( TiwiPro.ATTR_TYPE_VIOLATION_FLAGS, TiwiPro.VIOLATION_MASK_SPEEDING.getCode() );
        }
        
        else if (rpm_violation){ 
            attrs.put( TiwiPro.ATTR_TYPE_VIOLATION_FLAGS, TiwiPro.VIOLATION_MASK_RPM.getCode() );
        }
        
        else if (seatbelt_violation){ 
            attrs.put( TiwiPro.ATTR_TYPE_VIOLATION_FLAGS, TiwiPro.VIOLATION_MASK_SEATBELT.getCode() );
        }
        
        construct_note( TiwiPro.NOTE_TYPE_LOCATION, attrs );
    }
	

	public void add_note( Package_tiwiPro_Note note ){
        
        byte[] packaged = note.Package();
        note_queue.add(packaged);
        check_queue();
    }
    
    
    private void initiate_device(String server ){
        ignition_state = false;

        note_queue = new ArrayList<byte[]>();
        speed_points = new ArrayList<Integer>();
        speed_loc = new ArrayList<Double[]>();
        clear_internal_settings();
        
        get_time();
        set_url(server);

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
    


    
    public void construct_note(TiwiPro type, HashMap<TiwiPro, Integer> attrs){
    	try{attrs.put(TiwiPro.ATTR_TYPE_SPEED_LIMIT, speed_limit.intValue());}
    	catch (Exception e){
//    		e.printStackTrace();
    	}
    	Package_tiwiPro_Note note = new Package_tiwiPro_Note(type, time, sats, heading,
    			1, latitude, longitude, speed, odometer, attrs);
    	clear_internal_settings();
    	add_note(note);
    }
    
    public void construct_note(TiwiPro type){
    	attrs = new HashMap<TiwiPro,Integer>();
    	construct_note(type, attrs);
    }
    
    public void change_IMEI( String imei, String server, HashMap<Integer, String> settings, Integer version){
        set_IMEI( imei, server, settings, version );
    }
    
    public void change_IMEI( String imei, String server, HashMap<Integer, String> settings){
        set_IMEI( imei, server, settings, 5 );
    }
    
    public void change_IMEI( String imei, String server){
        set_IMEI( imei, server, TiwiPro_Defaults.get_defaults(), 5 );
    }
    
    public void change_IMEI( String imei, Integer version){
        set_IMEI( imei, "QA", TiwiPro_Defaults.get_defaults(), version );
    }
    
    public void change_IMEI( String imei){
        set_IMEI( imei, "QA", TiwiPro_Defaults.get_defaults(), 5 );
    }
    
    private void check_queue(){
        note_count = get_setting_int( TiwiPro.PROPERTY_SET_MSGS_PER_NOTIFICATION );
        if ( note_queue.size() >= note_count ){
        	send_note();
        }
    
    }
    


	private void clear_internal_settings(){
        
        odometer = 0;
        speed = 0;
        try{
            last_lat = latitude;
            last_lng = longitude;
            
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
			while (check_error(reply)){
				try{
					reply = mcmProxy.dumpSet(imei, productVersion, Settings);
				}catch(GenericHessianException e){
					reply = 0;
				}
			}
			if (reply instanceof Integer && (Integer)reply != 0){
				System.out.println(reply);
			}
		}
	}
	
	private Boolean check_error(Object reply){
		if (reply == null)return false;
		try{
			reply = (Integer)reply;
		}catch(ClassCastException e){
			return false;
		}
		for (int i=0; i<dbErrors.length;i++){
			if (dbErrors[i] == (Integer)reply)return true;
		}
		return false;
	}
	

	private void get_changes() {
		if (WMP >= 17013){
			reply = dbErrors[0];
			while (check_error(reply)){
				try{
					reply = mcmProxy.reqSet( imei );
				}catch(EmptyResultSetException e){
					reply = 304;
				}
			}
			if (reply instanceof Integer && (Integer)reply!= 304){
				System.out.println(reply + " We failed to get any changes");
			}
			else if (reply instanceof HashMap<?,?>){
				set_settings((HashMap<Integer, String>)reply);
			}
		}
	}
	
	public String get_setting(TiwiPro settingID){
		return Settings.get( settingID.getCode() );
	}    
	private Integer get_setting_int(TiwiPro settingID) {
		return Integer.parseInt(get_setting(settingID));
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
		
		for (int heading=0;heading<headers.length-1;heading++){
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
    

	public void power_off_device(Integer time_now){
		if (power_state){
			set_power();	
		}
		else{
			System.out.println("The device is already off.");
		}
    }public void power_off_device(Long time_now){   
    	power_off_device((int)(time_now/1000));
    }

	public void power_on_device(Integer time_now){
        if (!power_state){
			set_time( time_now );
		    set_power();
		    configurate_device();
		    time_now += 30;
		    set_time( time_now );
        }else{
        	System.out.println("The device is already on.");
        }
    }public void power_on_device(Long time_now){
        power_on_device((int)(time_now / 1000));
    }

	
    private Integer processCommand(Integer fwdID, Integer fwdCmd, Object fwdData) {
    	if (fwdCmd == null)return 1;
    	HashMap<Integer, String> changes = new HashMap<Integer, String>();
    	Package_tiwiPro_Note ackNote = new Package_tiwiPro_Note(TiwiPro.NOTE_TYPE_STRIPPED_ACKNOWLEDGE_ID_WITH_DATA);
    	
		if (fwdCmd==TiwiPro.FWD_CMD_ASSIGN_DRIVER.getCode()){
			String[] values = fwdData.toString().split(" ");
			setDeviceDriverID(Integer.parseInt(values[0]));
		}
		else if (fwdCmd==TiwiPro.FWD_CMD_DUMP_CONFIGURATION.getCode()) dump_settings();
		else if (fwdCmd==TiwiPro.FWD_CMD_UPDATE_CONFIGURATION.getCode()) get_changes();
		else if (fwdCmd==TiwiPro.FWD_CMD_DOWNLOAD_NEW_WITNESSII_FIRMWARE.getCode()) set_MSP((Integer) fwdData);
		else if (fwdCmd==TiwiPro.FWD_CMD_DOWNLOAD_NEW_FIRMWARE.getCode()) set_WMP((Integer) fwdData);
		else if (fwdCmd==TiwiPro.FWD_CMD_SET_SPEED_BUFFER_VALUES.getCode()){
			changes.put(TiwiPro.PROPERTY_VARIABLE_SPEED_LIMITS.getCode(),(String) fwdData);
		}
		
		ackNote.AddAttrs(TiwiPro.ATTR_TYPE_FWDCMD_ID, fwdID);
		ackNote.AddAttrs(TiwiPro.ATTR_TYPE_FWDCMD_STATUS, TiwiPro.FWDCMD_FLASH_SUCCESS);
		byte[] packaged = ackNote.Package();
        note_queue.add(packaged);
		
		set_settings(changes);
		return 1;
	}
    
	private void send_note() {
		
		assert(note_queue.getClass()==new ArrayList<byte[]>().getClass());
		assert(imei.getClass() == "".getClass());
		while (note_queue.size() != 0){
			for (int i=0; i < note_count; i++){
				if (note_queue.size()==0) break;
				sendingQueue.add(note_queue.remove(0));
			}
			reply = dbErrors[0];
			while (check_error(reply)){
				reply = mcmProxy.note(imei, sendingQueue);
			}
			if (reply instanceof Integer){
				System.out.println(reply.toString() + " We failed to send a note");
				throw new StoppedByUserException();
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
        this.Settings = settings;
        set_version(version);
        initiate_device( server );
    }


    public void set_ignition( Integer time_delta){
    	ignition_state = !ignition_state;
    	Integer newTime = (int)(time + time_delta);
    	set_time(newTime);
    	if (ignition_state){
    		construct_note(TiwiPro.NOTE_TYPE_IGNITION_ON);
    		trip_start = newTime;
    	}
    	else if(!ignition_state){
    		trip_stop=newTime;
    		Integer tripTime = trip_stop - trip_start;
    		attrs = new HashMap<TiwiPro, Integer>();
    		attrs.put(TiwiPro.ATTR_TYPE_TRIP_DURATION, tripTime);
    		attrs.put(TiwiPro.ATTR_TYPE_PERCENTAGE_OF_POINTS_THAT_PASSED_THE_FILTER_, 980);
    		construct_note(TiwiPro.NOTE_TYPE_IGNITION_OFF, attrs);
    	}
    }
	
    
    public void set_location(double lat, double lng, Integer time_delta) {
		try{
			if (last_lat != latitude)last_lat = latitude;
			else if (last_lat == null)last_lat = lat;
		}catch(Exception e){
			last_lat = lat;
		}try{
			if (last_lng != longitude)last_lng = longitude;
			else if (last_lng == null)last_lng = lat;
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
    public void set_location(double lat, double lng){
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
        
        attrs = new HashMap<TiwiPro, Integer>();
        
        power_state = !power_state; // Change the power state between on and off
        if (power_state){
            attrs.put(TiwiPro.ATTR_TYPE_FIRMWARE_VERSION, WMP);
            attrs.put(TiwiPro.ATTR_TYPE_DMM_VERSION, MSP);
            attrs.put(TiwiPro.ATTR_TYPE_GPS_LOCK_TIME, 10);
            construct_note( TiwiPro.NOTE_TYPE_POWER_ON, attrs );
            check_queue();
            
        } else if (!power_state){
            attrs.put(TiwiPro.ATTR_TYPE_LOW_POWER_MODE_TIMEOUT, Integer.parseInt(Settings.get(TiwiPro.PROPERTY_LOW_POWER_MODE_SECONDS.getCode())));
            construct_note( TiwiPro.NOTE_TYPE_LOW_POWER_MODE, attrs );
            check_queue();
            if (note_queue.isEmpty()) send_note();
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
			        Settings.get(TiwiPro.PROPERTY_SERVER_URL.getCode()), 
			        Integer.parseInt(Settings.get(TiwiPro.PROPERTY_SERVER_PORT.getCode())));
        	}else if (productVersion==2){
        		mcmProxy = (MCMProxy)factory.create( MCMProxy.class, 
			        Settings.get(TiwiPro.PROPERTY_SERVER_URL.getCode()), 
			        Integer.parseInt(Settings.get(TiwiPro.PROPERTY_SERVER_PORT.getCode())));	
        	}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
    }

    public void set_speed_limit( Integer limit ){
    	set_settings(TiwiPro.PROPERTY_SPEED_LIMIT, limit.toString());    	
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
    private void set_settings( TiwiPro key, String value){
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
        if (productVersion==5){
        	Settings.put(TiwiPro.PROPERTY_SERVER_PORT.getCode(), port);
        	Settings.put(TiwiPro.PROPERTY_SERVER_URL.getCode(), url);
        }
//        else if (productVersion==2){
//        	Settings.put(key, url+":"+port);
//        }
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
		try{
			is_speeding();
		}catch(Exception e){
//			e.printStackTrace();
		}
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
		attrs = new HashMap<TiwiPro, Integer>();
		attrs.put(TiwiPro.ATTR_TYPE_DISTANCE, distance);
		attrs.put(TiwiPro.ATTR_TYPE_TOP_SPEED, topSpeed);
		attrs.put(TiwiPro.ATTR_TYPE_AVG_SPEED, avgSpeed);
		attrs.put(TiwiPro.ATTR_TYPE_SPEED_ID, 9999);
		attrs.put(TiwiPro.ATTR_TYPE_VIOLATION_FLAGS, 1);
		
		construct_note(TiwiPro.NOTE_TYPE_SPEEDING_EX3, attrs);
	}

	
}
