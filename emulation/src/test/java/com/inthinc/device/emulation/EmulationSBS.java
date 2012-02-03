package com.inthinc.device.emulation;

import android.util.Log;

import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.emulation.utils.GeoPoint.Heading;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.sbs.Sbs;
import com.inthinc.sbs.SpeedLimit;

public class EmulationSBS {
	
	public static void main(String[] args){
		Sbs sbs = new Sbs("FKE00001", 7, Addresses.QA);
		GeoPoint point = new GeoPoint(44.228503,-106.671517);
		SpeedLimit limit = sbs.getSpeedLimit(point, Heading.WEST);
		sbs.getThreadManager().shutdown();
		Log.i(limit.speedLimit + "");
	}

}
