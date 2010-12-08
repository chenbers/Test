package com.inthinc.pro.device;

import static org.junit.Assert.*;

import org.junit.Test;

import com.inthinc.pro.device.waysmart.WaysmartDevice;

public class TestWaysmart {

	public static void main( String[] args){
		WaysmartDevice waysmart = new WaysmartDevice("MCM100343");
		waysmart.set_url("Dev");
		waysmart.dump_settings();
	}
	
    @Test
    public void dummy()
    {
        assertEquals("", 1,1);
    }

}
