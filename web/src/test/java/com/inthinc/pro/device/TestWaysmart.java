package com.inthinc.pro.device;


import com.inthinc.pro.device.waysmart.WaysmartDevice;

public class TestWaysmart {

	public static void main( String[] args){
		WaysmartDevice waysmart = new WaysmartDevice("MCM083735");
		waysmart.set_url("QA");
		waysmart.dump_settings();
	}
}
