package com.inthinc.pro.dao.util;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Vehicle;

public class ModelPropertyReplacerTest {
	private LatLng controlLatLng;
	private LatLng originalLatLng;
	private LatLng replacelLatLng;
	
	private LastLocation controlLastLoc;
	private LastLocation originalLastLoc;
	private LastLocation replaceLastLoc;
	@Before
	public void before(){
		originalLatLng = new LatLng(45.0000d, -111.11111d);

		originalLastLoc = new LastLocation();
		originalLastLoc.setDriverID(null);
		originalLastLoc.setLoc(originalLatLng);
		originalLastLoc.setTime(new Date());
		originalLastLoc.setVehicleID(new Integer(1));
		
		controlLastLoc = new LastLocation();
		controlLastLoc.setDriverID(null);
		controlLastLoc.setLoc(originalLatLng);
		controlLastLoc.setTime(originalLastLoc.getTime());
		controlLastLoc.setVehicleID(new Integer(1));

		replaceLastLoc = new LastLocation();
		replaceLastLoc.setDriverID(null);
		replaceLastLoc.setLoc(originalLatLng);
		replaceLastLoc.setTime(originalLastLoc.getTime());
		replaceLastLoc.setVehicleID(new Integer(1));
	}
	@Test
	public void simplestTest(){
		 
		controlLatLng = new LatLng(45.0000d, -111.11111d);
		//null properties
		replacelLatLng = null; 
		
		ModelPropertyReplacer.compareAndReplace(originalLatLng, replacelLatLng);
		assertEquals(controlLatLng,originalLatLng);
	}
	@Test
	public void nextSimplestTest(){
		 
		controlLatLng = new LatLng(56.0000d, -111.11111d);
		replacelLatLng = new LatLng(56.0000d, -111.11111d);; 
		
		ModelPropertyReplacer.compareAndReplace(originalLatLng, replacelLatLng);
		assertEquals(controlLatLng,replacelLatLng);
	}
	@Test
	public void lastLocationDateTest(){
		 
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} // so dates won't be the same

		replaceLastLoc.setTime(new Date());
		
		controlLastLoc.setTime(replaceLastLoc.getTime());
		
		ModelPropertyReplacer.compareAndReplace(originalLastLoc, replaceLastLoc);
		
		assertEquals(controlLastLoc.getDriverID(),originalLastLoc.getDriverID());
		assertEquals(controlLastLoc.getLoc(),originalLastLoc.getLoc());
		assertEquals(controlLastLoc.getTime(),originalLastLoc.getTime());
		assertEquals(controlLastLoc.getVehicleID(),originalLastLoc.getVehicleID());
	}
	@Test
	public void lastLocationLocationTest(){
		 
		replacelLatLng = new LatLng(56.0000d, -111.11111d);; 
		replaceLastLoc.setLoc(replacelLatLng);
		controlLastLoc.setLoc(replacelLatLng);
		
		ModelPropertyReplacer.compareAndReplace(originalLastLoc, replaceLastLoc);
		
		assertEquals(controlLastLoc.getDriverID(),originalLastLoc.getDriverID());
		assertEquals(controlLastLoc.getLoc(),originalLastLoc.getLoc());
		assertEquals(controlLastLoc.getTime(),originalLastLoc.getTime());
		assertEquals(controlLastLoc.getVehicleID(),originalLastLoc.getVehicleID());
	}
	@Test
	public void lastLocationDriverIDTest(){
		 
		replaceLastLoc.setDriverID(new Integer(1));
		controlLastLoc.setDriverID(new Integer(1));
		
		ModelPropertyReplacer.compareAndReplace(originalLastLoc, replaceLastLoc);
		
		assertEquals(controlLastLoc.getDriverID(),originalLastLoc.getDriverID());
		assertEquals(controlLastLoc.getLoc(),originalLastLoc.getLoc());
		assertEquals(controlLastLoc.getTime(),originalLastLoc.getTime());
		assertEquals(controlLastLoc.getVehicleID(),originalLastLoc.getVehicleID());
	}
	@Test
	public void lastLocationVehicleIDTest(){
		 
		replaceLastLoc.setVehicleID(null);
		
		ModelPropertyReplacer.compareAndReplace(originalLastLoc, replaceLastLoc);
		
		assertEquals(controlLastLoc.getDriverID(),originalLastLoc.getDriverID());
		assertEquals(controlLastLoc.getLoc(),originalLastLoc.getLoc());
		assertEquals(controlLastLoc.getTime(),originalLastLoc.getTime());
		assertEquals(controlLastLoc.getVehicleID(),originalLastLoc.getVehicleID());
	}
	@Test
	public void vehicleTest(){
		 
		Vehicle controlVehicle = new Vehicle();
		controlVehicle.setColor("red");
		
		Vehicle originalVehicle = new Vehicle();

		Vehicle replaceVehicle = new Vehicle();
		replaceVehicle.setColor("red");
		ModelPropertyReplacer.compareAndReplace(originalVehicle, replaceVehicle);
		
		assertEquals(controlVehicle.getColor(),originalVehicle.getColor());
	}
	
}
