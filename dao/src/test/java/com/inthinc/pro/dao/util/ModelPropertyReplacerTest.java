package com.inthinc.pro.dao.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.RedFlagAlert;
import com.inthinc.pro.model.ReferenceEntity;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.Zone;

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
	
	@Test
	public void notASimpleClassTest(){
		assertTrue(!notASimpleClass(Person.class,String.class));
		assertTrue(notASimpleClass(Person.class,Driver.class));
		assertTrue(notASimpleClass(Driver.class,Person.class));
		Integer array[] = {1,2,3,4};
		assertTrue(!notASimpleClass(Person.class,array.getClass()));
		Date date = new Date();
		assertTrue(!notASimpleClass(Person.class,date.getClass()));
		try {
			URL url = new URL("http://hello.com");
			assertTrue(!notASimpleClass(Person.class,url.getClass()));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Time time = new Time(1L);
		assertTrue(!notASimpleClass(Person.class,time.getClass()));
		State state = new State();
		assertTrue(!notASimpleClass(Person.class,state.getClass()));
		RedFlagAlert rfa = new RedFlagAlert();
		Zone zone = new Zone();
		assertTrue(!notASimpleClass(rfa.getClass(),zone.getClass()));
		List<String> list = new ArrayList<String>();
		assertTrue(!notASimpleClass(Person.class,list.getClass()));
		Locale locale = Locale.getDefault();
		assertTrue(!notASimpleClass(Person.class,locale.getClass()));
		assertTrue(notASimpleClass(Person.class,Driver.class));
	}
	//Copy of 
    private static boolean notASimpleClass(Class<?> itemClass, Class<?> propertyClass){
    	return !BeanUtils.isSimpleProperty(propertyClass) && !propertyClass.isEnum() && !Collection.class.isAssignableFrom(propertyClass) && !Map.class.isAssignableFrom(propertyClass)
        && !propertyClass.isArray() && !Date.class.isAssignableFrom(propertyClass) && !TimeZone.class.isAssignableFrom(propertyClass) 
        && !ReferenceEntity.class.isAssignableFrom(propertyClass)
        && !(SimpleType.valueOf(itemClass) != null && SimpleType.valueOf(itemClass).getSimpleTypes().contains(propertyClass))
        && !Locale.class.isAssignableFrom(propertyClass);
    }
    //from ModelPropertyReplace
	@Test
	public void personTest(){
		Integer [] roleArray = {1,2};
		List<Integer> roles = new ArrayList<Integer>(Arrays.asList(roleArray));
		Integer [] replaceRoleArray = {1,2,3};
		List<Integer> replaceRoles = new ArrayList<Integer>(Arrays.asList(replaceRoleArray));

		//tests non-simple nested objects
		Person controlPerson = new Person();
		Driver controlDriver = new Driver();
		User controlUser = new User();
		
		Driver originalDriver = new Driver();
		Person originalPerson = new Person();
		User originalUser = new User();

		Driver replaceDriver = new Driver();
		Person replacePerson = new Person();
		User replaceUser = new User();
		
		controlPerson.setPersonID(100);
		controlPerson.setFirst("firstname");
		
		controlPerson.setDriver(controlDriver);
		controlDriver.setDriverID(1);
		controlDriver.setPerson(controlPerson);
		controlDriver.setDot(RuleSetType.US_OIL);
		
		controlUser.setPerson(controlPerson);
		controlUser.setRoles(replaceRoles);
		
		originalDriver.setDot(RuleSetType.US_OIL);
		originalDriver.setDriverID(1);
		originalDriver.setPerson(originalPerson);
		
		originalPerson.setDriver(originalDriver);
		originalPerson.setPersonID(100);
		originalPerson.setFirst("oldname");
		
		originalUser.setPerson(originalPerson);
		originalUser.setRoles(roles);

		replacePerson.setPersonID(100);
		replacePerson.setFirst("firstname");
		
		replaceDriver.setPerson(replacePerson);
		replaceDriver.setDot(RuleSetType.US_OIL);
		replaceDriver.setDriverID(1);
		replaceDriver.setDot(null);
		
		replaceUser.setPerson(replacePerson);
		replaceUser.setRoles(replaceRoles);
		
		ModelPropertyReplacer.compareAndReplace(originalPerson, replacePerson);
		
		assertEquals(controlPerson.getFirst(),replacePerson.getFirst());
		assertEquals(controlDriver.getDot(),originalDriver.getDot());
		assertEquals(controlUser.getRoles(),replaceUser.getRoles());
	}
	
}
