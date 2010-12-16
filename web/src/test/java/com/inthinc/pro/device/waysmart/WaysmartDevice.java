package com.inthinc.pro.device.waysmart;

import java.net.MalformedURLException;
import java.util.HashMap;

import com.inthinc.pro.dao.hessian.extension.HessianTCPProxyFactory;
import com.inthinc.pro.device.Device;
import com.inthinc.pro.device.MCMProxy;

public class WaysmartDevice extends Device {
	
	protected final static Integer productVersion = 2;

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
	protected void createAckNote(Integer fwdID, Integer fwdCmd, Object fwdData) {
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
	
	public String get_setting(Waysmart setting) {
		return Settings.get(setting.getCode());
	}

	@Override
	public Integer get_setting_int() {
		// TODO Auto-generated method stub
		return null;
	}
	private Integer get_setting_int(Waysmart setting) {
		return setting.getCode();
	}

	@Override
	public Integer processCommand(Integer fwdID, Integer fwdCmd, Object fwdData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void set_ignition(Integer timeDelta) {
		// TODO Auto-generated method stub
		
	}
	
	protected void set_IMEI( String imei, String server, HashMap<Integer, String> settings, Integer version ){
        super.set_IMEI(imei, server, settings, productVersion);
        Settings.put(Waysmart.MCM_ID.getCode(), imei);
        imei = imei.replaceAll("MCM", "WW");
        Settings.put(Waysmart.WITNESS_ID.getCode(), imei);
    }

	@Override
	protected void set_power() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void set_server() {
		HessianTCPProxyFactory factory = new HessianTCPProxyFactory();
		String[] server = get_setting(Waysmart.SERVER_IP).split(":");
		System.out.println(server[0]+":"+server[1]);
        try {
    		mcmProxy = (MCMProxy)factory.create( MCMProxy.class, server[0], Integer.parseInt(server[1]));	
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void set_speed_limit(Integer limit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set_url(String url, Integer port) {
		Settings.put(get_setting_int(Waysmart.SERVER_IP), url+":"+port);
		set_server();
	}


	@Override
	protected void was_speeding() {
		// TODO Auto-generated method stub
		
	}

}
