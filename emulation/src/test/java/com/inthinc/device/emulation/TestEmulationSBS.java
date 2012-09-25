package com.inthinc.device.emulation;


import com.inthinc.device.emulation.utils.EmulationSbs;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.emulation.utils.MCMProxyObject;
import com.inthinc.device.emulation.utils.GeoPoint.Heading;
import com.inthinc.pro.automation.enums.AutoSilos;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.utils.AutoServers;
import com.inthinc.sbs.SpeedLimit;

public class TestEmulationSBS {
	
	public static void main(String[] args){
	    AutoServers server = new AutoServers();
        server.setBySilo(AutoSilos.QA);
        MCMProxyObject proxy = new MCMProxyObject(server);
		EmulationSbs sbs = new EmulationSbs(proxy, "javadeviceindavidsaccount");
		
		GeoPoint point = new GeoPoint();
		SpeedLimit limit = sbs.getSpeedLimit(point, Heading.WEST);
		Log.info(limit.speedLimit + "");
	}

}
