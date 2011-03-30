package com.inthinc.pro.automation.device_emulation.waysmart;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.automation.device_emulation.deviceBase.Base;
import com.inthinc.pro.automation.device_emulation.waysmart.enums.Ways_SETTINGS;
import com.inthinc.pro.automation.utils.CreateHessian;



public class WaysmartDevice extends Base {
	
	private final static Logger logger = Logger.getLogger(WaysmartDevice.class);
	
	protected final static Integer productVersion = 2;
	private CreateHessian hessian;

	public WaysmartDevice(String IMEI, String server, HashMap<Integer, String> settings) {
		super(IMEI, server, settings, productVersion);
	}
	
	public WaysmartDevice(String IMEI, String server){
		this(IMEI, server, Waysmart_Defaults.get_defaults());
	}
	
	public WaysmartDevice(String IMEI){
		this(IMEI, "QA");
	}

	@Override
	public void add_location() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add_note() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void construct_note() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void createAckNote(Map<String, Object> reply) {
		// TODO Auto-generated method stub
		
	}
	
	

	@Override
	protected Integer get_note_count() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String get_setting() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String get_setting(Ways_SETTINGS setting) {
		return Settings.get(setting.getCode());
	}

	@Override
	public Integer get_setting_int() {
		// TODO Auto-generated method stub
		return null;
	}
	private Integer get_setting_int(Ways_SETTINGS setting) {
		return setting.getCode();
	}

	@Override
	public Integer processCommand(Map<String, Object> reply) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void set_ignition(Integer timeDelta) {
		// TODO Auto-generated method stub
		
	}
	
	protected void set_IMEI( String imei, String server, HashMap<Integer, String> settings ){
		logger.debug("IMEI: "+imei+", Server: " + server);
		hessian = new CreateHessian();
        super.set_IMEI(imei, server, settings, productVersion);
        Settings.put(Ways_SETTINGS.MCM_ID.getCode(), imei);
        imei = imei.replaceAll("MCM", "WW");
        Settings.put(Ways_SETTINGS.WITNESS_ID.getCode(), imei);
    }

	@Override
	protected void set_power() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void set_server(String server) {
		mcmProxy = hessian.getMcmProxy(server);
		String url, port;
		url = hessian.getUrl(false);
		port = hessian.getPort(false).toString();
		Settings.put(get_setting_int(Ways_SETTINGS.SERVER_IP), url+":"+port);
	}

	@Override
	public void set_speed_limit(Integer limit) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void was_speeding() {
		// TODO Auto-generated method stub
		
	}
}
