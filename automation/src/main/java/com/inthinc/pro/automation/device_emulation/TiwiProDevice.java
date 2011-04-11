package com.inthinc.pro.automation.device_emulation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.automation.device_emulation.TiwiGenerals.FwdCmdStatus;
import com.inthinc.pro.automation.device_emulation.TiwiGenerals.ViolationFlags;
import com.inthinc.pro.automation.utils.CreateHessian;
import com.inthinc.pro.automation.utils.StackToString;


public class TiwiProDevice extends Base{
	
	private final static Logger logger = Logger.getLogger(TiwiProDevice.class);
	
	private final static Integer productVersion = 5;
	private HashMap<TiwiAttrs, Integer> attrs;
	private int trip_start, trip_stop;
	private CreateHessian getHessian;
	

	public TiwiProDevice(String IMEI, String server, Map<Integer, String> map) {
		super(IMEI, server, map, productVersion);
	}
	
	public TiwiProDevice(String IMEI, String server) {
		this(IMEI, server, TiwiPro_Defaults.get_defaults());
	}
	
	public TiwiProDevice(String IMEI) {
		this(IMEI, "QA");
	}

	
	@Override
	public void add_location() {
        attrs = new HashMap<TiwiAttrs, Integer>();
        if (speeding){ 
            attrs.put( TiwiAttrs.ATTR_TYPE_VIOLATION_FLAGS, ViolationFlags.VIOLATION_MASK_SPEEDING.getCode() );
        }
        
        else if (rpm_violation){ 
            attrs.put( TiwiAttrs.ATTR_TYPE_VIOLATION_FLAGS, ViolationFlags.VIOLATION_MASK_RPM.getCode() );
        }
        
        else if (seatbelt_violation){ 
            attrs.put( TiwiAttrs.ATTR_TYPE_VIOLATION_FLAGS, ViolationFlags.VIOLATION_MASK_SEATBELT.getCode() );
        }
        
        construct_note( TiwiNoteTypes.NOTE_TYPE_LOCATION, attrs );
    }
	
	public void add_note( Package_tiwiPro_Note note ){
        
        byte[] packaged = note.Package();
        note_queue.add(packaged);
        check_queue();
    }
	
    public void construct_note(TiwiNoteTypes type){
    	if (productVersion==5){
    		attrs = new HashMap<TiwiAttrs,Integer>();
    		construct_note(type, attrs);
    	}    	
    }
    
	public void construct_note(TiwiNoteTypes type, HashMap<TiwiAttrs, Integer> attrs){
		try{attrs.put(TiwiAttrs.ATTR_TYPE_SPEED_LIMIT, speed_limit.intValue());}
		catch (Exception e){
			logger.debug(StackToString.toString(e));
			e.printStackTrace();
		}
		Package_tiwiPro_Note note = new Package_tiwiPro_Note(type, time, sats, heading,
				1, latitude, longitude, speed, odometer, attrs);

    	clear_internal_settings();
    	add_note(note);
    }
	


	@Override
	public void createAckNote(Map<String, Object> reply) {
		Package_tiwiPro_Note ackNote = new Package_tiwiPro_Note(TiwiNoteTypes.NOTE_TYPE_STRIPPED_ACKNOWLEDGE_ID_WITH_DATA);
		ackNote.AddAttrs(TiwiAttrs.ATTR_TYPE_FWDCMD_ID, (Integer)reply.get("fwdID"));
		ackNote.AddAttrs(TiwiAttrs.ATTR_TYPE_FWDCMD_STATUS, FwdCmdStatus.FWDCMD_RECEIVED);
		byte[] packaged = ackNote.Package();
	    note_queue.add(packaged);
		processCommand(reply);	
	}
	
	
	@Override
	protected Integer get_note_count() {
		return get_setting_int(TiwiProps.PROPERTY_SET_MSGS_PER_NOTIFICATION);
	}
	
    public String get_setting(TiwiProps settingID){
		return Settings.get( settingID.getCode() );
	}
	public Integer get_setting_int(TiwiProps settingID) {
		return Integer.parseInt(get_setting(settingID));
	}

	@Override
	public Integer processCommand(Map<String, Object> reply) {
		logger.debug(reply);
    	if (reply.get("fwdCmd") == null)return 1;
    	TiwiFwdCmds fwdCmd = TiwiFwdCmds.valueOf(reply.get("fwdCmd"));
    	HashMap<Integer, String> changes = new HashMap<Integer, String>();
    	Package_tiwiPro_Note ackNote = new Package_tiwiPro_Note(TiwiNoteTypes.NOTE_TYPE_STRIPPED_ACKNOWLEDGE_ID_WITH_DATA);
    	
		if (fwdCmd==TiwiFwdCmds.FWD_CMD_ASSIGN_DRIVER){
			String[] values = reply.get("fwdData").toString().split(" ");
			setDeviceDriverID(Integer.parseInt(values[0]));
		}
		else if (fwdCmd==TiwiFwdCmds.FWD_CMD_DUMP_CONFIGURATION) dump_settings();
		else if (fwdCmd==TiwiFwdCmds.FWD_CMD_UPDATE_CONFIGURATION) get_changes();
		else if (fwdCmd==TiwiFwdCmds.FWD_CMD_DOWNLOAD_NEW_WITNESSII_FIRMWARE) set_MSP(reply.get("fwdData"));
		else if (fwdCmd==TiwiFwdCmds.FWD_CMD_DOWNLOAD_NEW_FIRMWARE) set_WMP( reply.get("fwdData"));
		else if (fwdCmd==TiwiFwdCmds.FWD_CMD_SET_SPEED_BUFFER_VALUES){
			changes.put(TiwiProps.PROPERTY_VARIABLE_SPEED_LIMITS.getCode(),(String) reply.get("fwdData"));
		}
		
		ackNote.AddAttrs(TiwiAttrs.ATTR_TYPE_FWDCMD_ID, reply.get("fwdId"));
		ackNote.AddAttrs(TiwiAttrs.ATTR_TYPE_FWDCMD_STATUS, FwdCmdStatus.FWDCMD_FLASH_SUCCESS);
		byte[] packaged = ackNote.Package();
        note_queue.add(packaged);
		
		if (!changes.isEmpty()) set_settings(changes);
		return 1;
	}

	public void set_ignition( Integer time_delta){
    	ignition_state = !ignition_state;
    	Integer newTime = (int)(time + time_delta);
    	set_time(newTime);
    	if (ignition_state){
    		construct_note(TiwiNoteTypes.NOTE_TYPE_IGNITION_ON);
    		trip_start = newTime;
    	}
    	else if(!ignition_state){
    		trip_stop=newTime;
    		Integer tripTime = trip_stop - trip_start;
    		attrs = new HashMap<TiwiAttrs, Integer>();
    		attrs.put(TiwiAttrs.ATTR_TYPE_TRIP_DURATION, tripTime);
    		attrs.put(TiwiAttrs.ATTR_TYPE_PERCENTAGE_OF_POINTS_THAT_PASSED_THE_FILTER_, 980);
    		construct_note(TiwiNoteTypes.NOTE_TYPE_IGNITION_OFF, attrs);
    	}
    }
	
	@Override
	protected void set_power(){
        
        attrs = new HashMap<TiwiAttrs, Integer>();
        
        power_state = !power_state; // Change the power state between on and off
        if (power_state){
            attrs.put(TiwiAttrs.ATTR_TYPE_FIRMWARE_VERSION, WMP);
            attrs.put(TiwiAttrs.ATTR_TYPE_DMM_VERSION, MSP);
            attrs.put(TiwiAttrs.ATTR_TYPE_GPS_LOCK_TIME, 10);
            construct_note( TiwiNoteTypes.NOTE_TYPE_POWER_ON, attrs );
            check_queue();
            
        } else if (!power_state){
            attrs.put(TiwiAttrs.ATTR_TYPE_LOW_POWER_MODE_TIMEOUT, Integer.parseInt(Settings.get(TiwiProps.PROPERTY_LOW_POWER_MODE_SECONDS.getCode())));
            construct_note( TiwiNoteTypes.NOTE_TYPE_LOW_POWER_MODE, attrs );
            check_queue();
            if (!note_queue.isEmpty()) send_note();
        }
    }
	
	@Override
	protected void set_server(String server){
		getHessian = new CreateHessian();
		logger.info("MCM Server is " + server);
        mcmProxy = getHessian.getMcmProxy(server) ;
		Settings.put(TiwiProps.PROPERTY_SERVER_PORT.getCode(), getHessian.getPort(false).toString());
    	Settings.put(TiwiProps.PROPERTY_SERVER_URL.getCode(), getHessian.getUrl(false));
    }
	
	public void set_settings( TiwiProps key, String value){
    	set_settings( key.getCode(), value);
    }


	@Override
	public void set_speed_limit(Integer limit) {
	    Settings.put(get_setting_int(TiwiProps.PROPERTY_SPEED_LIMIT), limit.toString());    	
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
		attrs = new HashMap<TiwiAttrs, Integer>();
		attrs.put(TiwiAttrs.ATTR_TYPE_DISTANCE, distance);
		attrs.put(TiwiAttrs.ATTR_TYPE_TOP_SPEED, topSpeed);
		attrs.put(TiwiAttrs.ATTR_TYPE_AVG_SPEED, avgSpeed);
		attrs.put(TiwiAttrs.ATTR_TYPE_SPEED_ID, 9999);
		attrs.put(TiwiAttrs.ATTR_TYPE_VIOLATION_FLAGS, 1);
		
		construct_note(TiwiNoteTypes.NOTE_TYPE_SPEEDING_EX3, attrs);
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
