package com.inthinc.pro.automation.objects;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Level;

import com.inthinc.hos.model.HOSStatus;
import com.inthinc.pro.automation.deviceEnums.Ways_ATTRS;
import com.inthinc.pro.automation.deviceEnums.Ways_SAT_EVENT;
import com.inthinc.pro.automation.deviceEnums.WaysmartProps;
import com.inthinc.pro.automation.device_emulation.DeviceBase;
import com.inthinc.pro.automation.device_emulation.Package_Waysmart_Note;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.interfaces.DeviceProperties;
import com.inthinc.pro.automation.models.MCMProxyObject;
import com.inthinc.pro.automation.models.NoteBC;
import com.inthinc.pro.automation.models.NoteBC.Direction;
import com.inthinc.pro.automation.models.NoteWS;
import com.inthinc.pro.automation.utils.MasterTest;
import com.inthinc.pro.automation.utils.RandomValues;
import com.inthinc.pro.model.configurator.ProductType;



public class WaysmartDevice extends DeviceBase {
    private Addresses server;
    
    private int driverID;
    private String employeeID;
    
    private int accountID;

    private int companyID;

    private String vehicleID;
    private long waysOdometer;
    private int state;
    
    protected final static ProductType productVersion = ProductType.WAYSMART;
	
		
	private HOSStatus hosStatus;
	
	public WaysmartDevice(String IMEI, String MCM, Direction comMethod){
		this(IMEI, MCM, Addresses.QA, comMethod);
	}
    public WaysmartDevice(String IMEI, String MCM, Addresses server, Direction comMethod){
		this(IMEI, MCM, server, comMethod, WaysmartProps.STATIC.getDefaultProps());
	}

	public WaysmartDevice(String IMEI, String MCM, Addresses server, Direction comMethod, HashMap<WaysmartProps, String> settings) {
		super(IMEI, server, settings, productVersion);
		this.mcmID = MCM;
		this.server = server;
		this.waysDirection = comMethod;
		setState(RandomValues.newOne().getStateByName("Utah", ProductType.WAYSMART));
	}
	
	@Override
	public WaysmartDevice add_location() {
	    addLocationNote(7, speed, hosStatus);
        return this;
		
	}
	
	/**
	 * 116 {SAT_EVENT_NEWDRIVER_HOSRULE, {ATTR_driverStr, ATTR_mcmRuleset}}
	 **/
	public WaysmartDevice addHosStateChange(String driverStr, HOSStatus status){
	    this.employeeID = driverStr;
	    NoteWS note = constructWSNote(Ways_SAT_EVENT.SAT_EVENT_NEWDRIVER_HOSRULE);
	    note.addAttr(Ways_ATTRS.ATTR_DRIVERSTR, driverStr);
	    note.addAttr(Ways_ATTRS.ATTR_MCMRULESET, status.getCode());
	    addNote(note);
	    
//	    Package_Waysmart_Note note = construct_note();
//	    note.setHosStatus(status);
//	    note.setDriverID(driverStr);
//	    note.setSpeed(speed);
//	    sendNote(note);
	    return this;
	}
	
	
	public void addIgnitionOffNote(){
        MasterTest.print(sendNote(construct_note(Ways_SAT_EVENT.SAT_EVENT_IGNITION_OFF)), Level.DEBUG);
    }
	
	public void addIgnitionOnNote(){
	    MasterTest.print(sendNote(construct_note(Ways_SAT_EVENT.SAT_EVENT_IGNITION_ON)), Level.DEBUG);
	}
	
	public WaysmartDevice addInstallEvent(String vehicleID, int accountID){
	    this.setVehicleID(vehicleID);
	    this.setAccountID(accountID);
	    this.setCompanyID(accountID);
	    Direction temp = waysDirection;
	    waysDirection = Direction.sat;
	    Package_Waysmart_Note note = construct_note(Ways_SAT_EVENT.SAT_EVENT_INSTALL);
	    note.setVehicleID(vehicleID);
	    note.setCompanyID(accountID);
	    note.setAccountID(accountID);
	    MasterTest.print(sendNote(note));
	    waysDirection = temp;
	    return this;
	}
	
	public WaysmartDevice addLocationNote(int driverID, int speed, HOSStatus hosStatus) {
	    NoteBC note = constructBCNote(Ways_SAT_EVENT.SAT_EVENT_LOCATION);
//	    note.addAttr(Ways_ATTRS.ATTR_DRIVERID, driverID);
//	    note.addAttr(Ways_ATTRS.ATTR_LINKID, 0);
//	    note.addAttr(Ways_ATTRS.ATTR_CONNECTTYPE, waysDirection);
	    addNote(note);
	    
//        Package_Waysmart_Note note = construct_note(Ways_SAT_EVENT.SAT_EVENT_LOCATION, comMethod);
////        note.setDriverID(driverID);
//        note.setSpeed(speed);
//        note.setHosStatus(hosStatus);
//        logger.info(sendNote(note));
        return this;
    }
	
	@Override
    protected DeviceBase construct_note() {
        return null;
    }
	
	protected NoteBC constructBCNote(Ways_SAT_EVENT type){
	    return new NoteBC(waysDirection, type, productVersion, time, heading, sats, latitude, longitude, speed, 
	            speed_limit.intValue(), state, odometer, 0, driverID);
	}
	
	protected NoteWS constructWSNote(Ways_SAT_EVENT type){
	    return new NoteWS(type, productVersion, time, heading, sats, latitude, longitude, speed, companyID, 0);
	}

	protected Package_Waysmart_Note construct_note( Ways_SAT_EVENT type){
	    return new Package_Waysmart_Note(type, waysDirection, server, mcmID, imei);
	}
	
	
	@Override
	protected WaysmartDevice createAckNote(Map<String, Object> reply) {
        return this;
		
	}
	
	@Override
	protected Integer get_note_count() {
		return note_count;
	}

	public int getAccountID() {
        return accountID;
    }

	public int getCompanyID() {
        return companyID;
    }
	
	public int getDriverID() {
        return driverID;
    }

	public int getState() {
        return state;
    }
	
	public String getVehicleID() {
        return vehicleID;
    }
	
	public WaysmartDevice logInDriver(String driverID){
	    this.employeeID = driverID;
	    return this;
	}
	
	public WaysmartDevice logInOccupant(String occupantsDriverID) {
        Package_Waysmart_Note note = construct_note(Ways_SAT_EVENT.SAT_EVENT_HOS_CHANGE_STATE_NO_GPS_LOCK);
        note.setDriverID(occupantsDriverID);
        note.setHosStatus(HOSStatus.ON_DUTY_OCCUPANT);
        MasterTest.print(sendNote(note));
        return this;
	}
	
	public WaysmartDevice nonTripNote(Double latitude, Double longitude, int odometer){
        this.latitude = latitude;
        this.longitude = longitude;
        this.waysOdometer = odometer;
        this.odometer = 0;
        return this;
    }

    
    @Override
	public Integer processCommand(Map<String, Object> reply) {
		return null;
	}

	private WaysmartDevice sendNote(Package_Waysmart_Note note){
	    note.setLatitude(latitude);
	    note.setLongitude(longitude);
        note.setOdometer(waysOdometer+=odometer);
        note.setTime(time);
        clear_internal_settings();
        MasterTest.print(note.sendNote());
	    return this;
	}

	@Override
	protected WaysmartDevice set_ignition(Integer time_delta) {
	    ignition_state = !ignition_state;
	    time.addToSeconds(time_delta);
        if (ignition_state) {
            addIgnitionOnNote();
        } else {
            addIgnitionOffNote();
        }
        return this;
		
	}


	protected WaysmartDevice set_IMEI( HashMap<WaysmartProps, String> settings ){
		MasterTest.print("IMEI: "+imei+", Server: " + portal, Level.DEBUG);
        super.set_IMEI(settings);
        Settings.put(WaysmartProps.MCM_ID, mcmID);
        Settings.put(WaysmartProps.WITNESS_ID, imei);
        return this;
    }

	 @Override
	protected WaysmartDevice set_power() {
        power_state = !power_state; // Change the power state between on and off
        
        Package_Waysmart_Note note;
        if (power_state) {
            note = construct_note(Ways_SAT_EVENT.SAT_EVENT_POWER_ON);
        } else {
            note = construct_note(Ways_SAT_EVENT.SAT_EVENT_LOW_POWER_MODE);
        }
        MasterTest.print(sendNote(note));
        return this;
		
	}

    @Override
	protected WaysmartDevice set_server(Addresses server) {
		mcmProxy = new MCMProxyObject(server);
		this.server = server;
		String url, port;
		url = server.getMCMUrl();
		port = server.getWaysPort().toString();
		Settings.put(WaysmartProps.SERVER_IP, url+":"+port);
		Settings.put(WaysmartProps.MAP_SERVER_URL, url);
		Settings.put(WaysmartProps.MAP_SERVER_PORT, server.getMCMPort().toString());
        return this;
	}

    @Override
	public WaysmartDevice set_speed_limit(Integer limit) {
		set_settings(WaysmartProps.SPEED_LIMIT, limit);
        return this;
		
	}

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public WaysmartDevice setBaseOdometer(long odometer){
	    waysOdometer = odometer;
	    return this;
	}

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    protected HashMap<DeviceProperties, String> theirsToOurs(HashMap<?, ?> reply){
        HashMap<WaysmartProps, String> map = new HashMap<WaysmartProps, String>();
        Iterator<?> itr = reply.keySet().iterator();
        while (itr.hasNext()){
            Integer next = (Integer) itr.next();
            String value = reply.get(next).toString();
            map.put(WaysmartProps.STATIC.valueOf(next), (String) value);
        }
        return null;
    }

    @Override
	protected WaysmartDevice was_speeding() {
        return this;
	}
}
