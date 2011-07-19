package com.inthinc.pro.automation.device_emulation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.hos.model.HOSStatus;
import com.inthinc.pro.automation.device_emulation.Package_Waysmart_Note.Direction;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.enums.DeviceProperties;
import com.inthinc.pro.automation.enums.Ways_SAT_EVENT;
import com.inthinc.pro.automation.enums.WaysmartProps;
import com.inthinc.pro.automation.utils.CreateHessian;
import com.inthinc.pro.automation.utils.RandomValues;
import com.inthinc.pro.model.configurator.ProductType;



public class WaysmartDevice extends Base {
    private String mcm;
    private Addresses server;
    
    private int driverID;
    private int accountID;
    private int companyID;
    private int vehicleID;
    
    private long waysOdometer;
    
    private int state;
	
	private final static Logger logger = Logger.getLogger(WaysmartDevice.class);
	
		
	protected final static ProductType productVersion = ProductType.WAYSMART;
	private CreateHessian hessian;
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
		state = RandomValues.newOne().getStateByName("Utah", ProductType.WAYSMART);
	}
	
	public WaysmartDevice logInDriver(int driverID){
	    this.driverID = driverID;
	    return this;
	}
	
	
	@Override
	public WaysmartDevice add_location() {
		Package_Waysmart_Note note = construct_note(Ways_SAT_EVENT.SAT_EVENT_LOCATION, Direction.wifi);
		note.setDriverID(driverID);
		note.setSpeed(speed);
		note.setHosStatus(hosStatus);
		logger.info(sendNote(note));
        return this;
		
	}
	
	public WaysmartDevice setBaseOdometer(long odometer){
	    waysOdometer = odometer;
	    return this;
	}
	
	@Override
    public WaysmartDevice add_note(NoteBuilder note) {
        logger.info(note.sendNote());
        return null;
    }

	@Override
    protected Base construct_note() {
        return null;
    }
	
	
	public WaysmartDevice installEvent(int vehicleID, int accountID, int companyID){
	    this.vehicleID = vehicleID;
	    this.accountID = accountID;
	    this.companyID = companyID;
	    
	    Package_Waysmart_Note note = construct_note(Ways_SAT_EVENT.SAT_EVENT_INSTALL, Direction.sat);
	    note.setVehicleID(vehicleID);
	    note.setCompanyID(companyID);
	    note.setAccountID(accountID);
	    logger.info(sendNote(note));
	    return this;
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
		return null;
	}
	
	@Override
	public Integer processCommand(Map<String, Object> reply) {
		return null;
	}

	@Override
	public WaysmartDevice set_ignition(Integer time_delta) {
	    ignition_state = !ignition_state;
        Long newTime = (Long) (time + time_delta);
        set_time(newTime);
        Package_Waysmart_Note note;
        if (ignition_state) {
            note = construct_note(Ways_SAT_EVENT.SAT_EVENT_IGNITION_ON, Direction.wifi);
        } else {
            note = construct_note(Ways_SAT_EVENT.SAT_EVENT_IGNITION_OFF, Direction.wifi);
        }
        note.setTime(time);
        logger.info(sendNote(note));
        return this;
		
	}
	
	private String sendNote(Package_Waysmart_Note note){
	    note.setLatitude(latitude);
	    note.setLongitude(longitude);
        note.setOdometer(waysOdometer+=odometer);
        clear_internal_settings();
	    return note.sendNote();
	}

	protected WaysmartDevice set_IMEI( String imei, Addresses server, HashMap<Integer, String> settings ){
		logger.debug("IMEI: "+imei+", Server: " + server);
		this.server = server;
		hessian = new CreateHessian();
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
        hessian = new CreateHessian();
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
