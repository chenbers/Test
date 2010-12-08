package com.inthinc.pro.device;

import static org.junit.Assert.*;

import org.junit.Test;

import com.inthinc.pro.device.tiwiPro.TiwiProDevice;



public class Test_Notes {
	
	public static void main(String args[]){
		Long time = System.currentTimeMillis();
		TiwiProDevice testTiwi = new TiwiProDevice("999456789012345");
		testTiwi.set_location(40.43182,-111.802998);
		testTiwi.power_on_device(time);
		testTiwi.set_ignition(30);
		testTiwi.add_location();
		testTiwi.set_location(40.431816,-111.803862, 5);
		testTiwi.add_location();
		testTiwi.set_ignition(30);
		testTiwi.power_off_device(5);
	}
	
    @Test
    public void dummy()
    {
        assertEquals("", 1,1);
    }

}
