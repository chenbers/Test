package com.inthinc.pro.automation.device_emulation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.automation.enums.DeviceProperties;
import com.inthinc.pro.automation.enums.WaysmartProps;
import com.inthinc.pro.automation.utils.CreateHessian;



public class WaysmartDevice extends Base {
	
	private final static Logger logger = Logger.getLogger(WaysmartDevice.class);
	
	protected final static Integer productVersion = 2;
	private CreateHessian hessian;

	public WaysmartDevice(String IMEI, String server, HashMap<WaysmartProps, String> settings) {
		super(IMEI, server, settings, productVersion);
	}
	
	public WaysmartDevice(String IMEI, String server){
		this(IMEI, server, WaysmartProps.STATIC.getDefaultProps());
	}
	
	public WaysmartDevice(String IMEI){
		this(IMEI, "QA");
	}

	@Override
	public Base add_location() {
		// TODO Auto-generated method stub
        return this;
		
	}

	@Override
	public Base add_note() {
		// TODO Auto-generated method stub
        return this;
		
	}

	@Override
	protected Base construct_note() {
		// TODO Auto-generated method stub
        return this;
		
	}

	@Override
	protected Base createAckNote(Map<String, Object> reply) {
		// TODO Auto-generated method stub
        return this;
		
	}
	
	

	@Override
	protected Integer get_note_count() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer processCommand(Map<String, Object> reply) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Base set_ignition(Integer timeDelta) {
		// TODO Auto-generated method stub
        return this;
		
	}
	
	protected Base set_IMEI( String imei, String server, HashMap<Integer, String> settings ){
		logger.debug("IMEI: "+imei+", Server: " + server);
		hessian = new CreateHessian();
        super.set_IMEI(imei, server, settings, productVersion);
        Settings.put(WaysmartProps.MCM_ID, imei);
        imei = imei.replaceAll("MCM", "WW");
        Settings.put(WaysmartProps.WITNESS_ID, imei);
        return this;
    }

	@Override
	protected Base set_power() {
		// TODO Auto-generated method stub
        return this;
		
	}

	@Override
	protected Base set_server(String server) {
		mcmProxy = hessian.getMcmProxy(server);
		String url, port;
		url = hessian.getUrl(false);
		port = hessian.getPort(false).toString();
		Settings.put(WaysmartProps.SERVER_IP, url+":"+port);
        return this;
	}

	@Override
	public Base set_speed_limit(Integer limit) {
		// TODO Auto-generated method stub
        return this;
		
	}


	@Override
	protected Base was_speeding() {
		// TODO Auto-generated method stub
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
}
