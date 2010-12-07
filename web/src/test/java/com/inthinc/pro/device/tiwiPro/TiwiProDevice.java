package com.inthinc.pro.device.tiwiPro;

import java.util.Collections;
import java.util.HashMap;

import com.inthinc.pro.device.Device;
import com.inthinc.pro.dao.hessian.extension.HessianTCPProxyFactory;
import com.inthinc.pro.device.MCMProxy;
import java.net.MalformedURLException;

public class TiwiProDevice extends Device{
	private final static Integer productVersion = 5;
	private HashMap<com.inthinc.pro.device.tiwiPro.TiwiPro, Integer> attrs;
	private int trip_start, trip_stop;
	

	public TiwiProDevice(String IMEI, String server) {
		super(IMEI, server, TiwiPro_Defaults.get_defaults());
	}

	public TiwiProDevice(String IMEI) {
		this(IMEI, "QA");
	}

	public TiwiProDevice(String IMEI, String server, HashMap<Integer, String> settings) {
		super(IMEI, server, settings, productVersion);
	}
	
	@Override
	public void add_location() {
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
	
    public void construct_note(TiwiPro type){
    	if (productVersion==5){
    		attrs = new HashMap<TiwiPro,Integer>();
    		construct_note(type, attrs);
    	}    	
    }
    
	public void construct_note(TiwiPro type, HashMap<TiwiPro, Integer> attrs){
		try{attrs.put(TiwiPro.ATTR_TYPE_SPEED_LIMIT, speed_limit.intValue());}
		catch (Exception e){
		}
		Package_tiwiPro_Note note = new Package_tiwiPro_Note(type, time, sats, heading,
				1, latitude, longitude, speed, odometer, attrs);

    	clear_internal_settings();
    	add_note(note);
    }
	


	@Override
	public void createAckNote(Integer fwdID, Integer fwdCmd, Object fwdData) {
		Package_tiwiPro_Note ackNote = new Package_tiwiPro_Note(TiwiPro.NOTE_TYPE_STRIPPED_ACKNOWLEDGE_ID_WITH_DATA);
		ackNote.AddAttrs(TiwiPro.ATTR_TYPE_FWDCMD_ID, fwdID);
		ackNote.AddAttrs(TiwiPro.ATTR_TYPE_FWDCMD_STATUS, TiwiPro.FWDCMD_RECEIVED);
		byte[] packaged = ackNote.Package();
	    note_queue.add(packaged);
		processCommand(fwdID, fwdCmd, fwdData);	
	}
	
	@Override
	protected Integer get_note_count() {
		return get_setting_int(TiwiPro.PROPERTY_SET_MSGS_PER_NOTIFICATION);
	}
	
    public String get_setting(TiwiPro settingID){
		return Settings.get( settingID.getCode() );
	}
	public Integer get_setting_int(TiwiPro settingID) {
		return Integer.parseInt(get_setting(settingID));
	}

	@Override
	public Integer processCommand(Integer fwdID, Integer fwdCmd, Object fwdData) {
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
	
	@Override
	protected void set_power(){
        
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
            if (!note_queue.isEmpty()) send_note();
        }
    }
	
	@Override
	protected void set_server(){
        
        HessianTCPProxyFactory factory = new HessianTCPProxyFactory();
        try {
    		mcmProxy = (MCMProxy)factory.create( MCMProxy.class, 
		        Settings.get(TiwiPro.PROPERTY_SERVER_URL.getCode()), 
		        Integer.parseInt(Settings.get(TiwiPro.PROPERTY_SERVER_PORT.getCode())));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
    }
	
	public void set_settings( TiwiPro key, String value){
    	set_settings( key.getCode(), value);
    }


	@Override
	public void set_speed_limit(Integer limit) {
	    Settings.put(get_setting_int(TiwiPro.PROPERTY_SPEED_LIMIT), limit.toString());    	
	}
	
	@Override
	public void set_url( String url, Integer port ){
    	Settings.put(TiwiPro.PROPERTY_SERVER_PORT.getCode(), port.toString());
    	Settings.put(TiwiPro.PROPERTY_SERVER_URL.getCode(), url);
        set_server();
    }
	
    protected void was_speeding() {
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




	@Override
	public void add_note() {
	}

	@Override
	public void construct_note() {
	}

	

	@Override
	public String get_setting() {
		return null;
	}

	@Override
	public Integer get_setting_int() {
		return null;
	}
	
}
