package com.inthinc.pro.device;


public class Test_Notes {
	

	public static void main(String args[]){
		Long time = System.currentTimeMillis();
		Device testTiwi = new Device("999456789012345");
		testTiwi.power_on_device(time);
		testTiwi.set_location(40.43182,-111.802998);
		testTiwi.add_location();
		testTiwi.set_location(40.431816,-111.803862, 5);
		testTiwi.add_location();
		testTiwi.power_off_device(time+5);
	}
}
