package com.inthinc.pro.automation.objects;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.hos.model.HOSStatus;
import com.inthinc.pro.automation.deviceEnums.Ways_SAT_EVENT;
import com.inthinc.pro.automation.deviceEnums.WaysmartProps;
import com.inthinc.pro.automation.device_emulation.Base;
import com.inthinc.pro.automation.device_emulation.NoteBuilder;
import com.inthinc.pro.automation.device_emulation.Package_Waysmart_Note;
import com.inthinc.pro.automation.device_emulation.Package_Waysmart_Note.Direction;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.interfaces.DeviceProperties;
import com.inthinc.pro.automation.utils.AutomationHessianFactory;
import com.inthinc.pro.automation.utils.RandomValues;
import com.inthinc.pro.model.configurator.ProductType;



public class WaysmartDevice extends Base {
    private String mcm;
    private Addresses server;
    
    private String driverID;
    
    private int accountID;

    private int companyID;

    private String vehicleID;
    private long waysOdometer;
    private int state;
    
    private final static Logger logger = Logger.getLogger(WaysmartDevice.class);
    
    protected final static ProductType productVersion = ProductType.WAYSMART;
	
	private AutomationHessianFactory hessian;
	
		
	private HOSStatus hosStatus;
	public WaysmartDevice(String IMEI, String MCM){
		this(IMEI, MCM, Addresses.QA);
	}
    public WaysmartDevice(String IMEI, String MCM, Addresses server){
		this(IMEI, MCM, server, WaysmartProps.STATIC.getDefaultProps());
	}

	public WaysmartDevice(String IMEI, String MCM, Addresses server, HashMap<WaysmartProps, String> settings) {
		super(IMEI, server, settings, productVersion);
		this.mcm = MCM;
		this.server = server;
		setState(RandomValues.newOne().getStateByName("Utah", ProductType.WAYSMART));
	}
	
	@Override
	public WaysmartDevice add_location() {
	    addLocationNote(driverID, speed, hosStatus, Direction.wifi);
        return this;
		
	}
	
	@Override
    public WaysmartDevice add_note(NoteBuilder note) {
        logger.info(note.sendNote());
        return null;
    }
	
	public WaysmartDevice addHosStateChange(String driverID, int speed, HOSStatus status, Direction comMethod){
	    this.driverID = driverID;
	    Package_Waysmart_Note note = construct_note(Ways_SAT_EVENT.SAT_EVENT_NEWDRIVER_HOSRULE, comMethod);
	    note.setHosStatus(status);
	    note.setDriverID(driverID);
	    note.setSpeed(speed);
	    sendNote(note);
	    return this;
	}
	
	
	public void addIgnitionOffNote(Direction direction){
        logger.debug(sendNote(construct_note(Ways_SAT_EVENT.SAT_EVENT_IGNITION_OFF, direction)));
    }
	
	public void addIgnitionOnNote(Direction direction){
	    logger.debug(sendNote(construct_note(Ways_SAT_EVENT.SAT_EVENT_IGNITION_ON, direction)));
	}
	
	public WaysmartDevice addInstallEvent(String vehicleID, int accountID){
	    this.setVehicleID(vehicleID);
	    this.setAccountID(accountID);
	    this.setCompanyID(accountID);
	    
	    Package_Waysmart_Note note = construct_note(Ways_SAT_EVENT.SAT_EVENT_INSTALL, Direction.sat);
	    note.setVehicleID(vehicleID);
	    note.setCompanyID(accountID);
	    note.setAccountID(accountID);
	    logger.info(sendNote(note));
	    return this;
	}
	
	public WaysmartDevice addLocationNote(String driverID, int speed, HOSStatus hosStatus, Direction comMethod) {
        Package_Waysmart_Note note = construct_note(Ways_SAT_EVENT.SAT_EVENT_LOCATION, comMethod);
        note.setDriverID(driverID);
        note.setSpeed(speed);
        note.setHosStatus(hosStatus);
        logger.info(sendNote(note));
        return this;
    }
	
	@Override
    protected Base construct_note() {
        return null;
    }

	protected Package_Waysmart_Note construct_note( Ways_SAT_EVENT type, Direction method){
	    return new Package_Waysmart_Note(type, method, server, mcm, imei);
	}
	
	
	@Override
	protected WaysmartDevice createAckNote(Map<String, Object> reply) {
        return this;
		
	}
	
	

	@Override
	protected Integer get_note_count() {
		throw new IllegalAccessError("This method is only available for Tiwi's");
	}

	public int getAccountID() {
        return accountID;
    }

	public int getCompanyID() {
        return companyID;
    }
	
	public String getDriverID() {
        return driverID;
    }

	public int getState() {
        return state;
    }
	
	public String getVehicleID() {
        return vehicleID;
    }
	
	public WaysmartDevice logInDriver(String driverID){
	    this.driverID = driverID;
	    return this;
	}
	
	public WaysmartDevice logInOccupant(String occupantsDriverID) {
        Package_Waysmart_Note note = construct_note(Ways_SAT_EVENT.SAT_EVENT_HOS_CHANGE_STATE_NO_GPS_LOCK, Direction.wifi);
        note.setDriverID(occupantsDriverID);
        note.setHosStatus(HOSStatus.ON_DUTY_OCCUPANT);
        logger.info(sendNote(note));
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
        logger.info(note.sendNote());
	    return this;
	}

	@Override
	protected WaysmartDevice set_ignition(Integer time_delta) {
	    ignition_state = !ignition_state;
        Long newTime = (Long) (time + time_delta);
        set_time(newTime);
        if (ignition_state) {
            addIgnitionOnNote(Direction.wifi);
        } else {
            addIgnitionOffNote(Direction.wifi);
        }
        return this;
		
	}


	protected WaysmartDevice set_IMEI( String imei, Addresses server, HashMap<Integer, String> settings ){
		logger.debug("IMEI: "+imei+", Server: " + server);
		this.server = server;
		hessian = new AutomationHessianFactory();
        super.set_IMEI(imei, server, settings, productVersion);
        Settings.put(WaysmartProps.MCM_ID, imei);
        imei = imei.replaceAll("MCM", "WW");
        Settings.put(WaysmartProps.WITNESS_ID, imei);
        return this;
    }

	 @Override
	protected WaysmartDevice set_power() {
        power_state = !power_state; // Change the power state between on and off
        
        Package_Waysmart_Note note;
        if (power_state) {
            note = construct_note(Ways_SAT_EVENT.SAT_EVENT_POWER_ON, Direction.wifi);
        } else {
            note = construct_note(Ways_SAT_EVENT.SAT_EVENT_LOW_POWER_MODE, Direction.wifi);
        }
        logger.info(sendNote(note));
        return this;
		
	}

    @Override
	protected WaysmartDevice set_server(Addresses server) {
        hessian = new AutomationHessianFactory();
		mcmProxy = hessian.getMcmProxy(server);
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

    public void setDriverID(String driverID) {
        this.driverID = driverID;
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
