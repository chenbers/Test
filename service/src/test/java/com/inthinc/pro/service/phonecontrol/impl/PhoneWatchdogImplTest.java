package com.inthinc.pro.service.phonecontrol.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Mockit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.service.phonecontrol.dao.DriverPhoneDAO;

/**
 * Unit test for PhoneWatchdogImpl
 * 
 * @author dcueva
 *
 */
public class PhoneWatchdogImplTest {

	private static final Integer DRIVER_ID[] = {new Integer(6286), new Integer(1689)};
	private static final Integer SECONDS_AGO = 100; 
	
	@Mocked
	private EventDAO eventDAO;
	
	@Mocked
	private DriverPhoneDAO driverPhoneDAO;
	
	@Mocked(methods = "<clinit>", inverse = true) 
	private PhoneControlMovementEventHandler phoneControl;
	  // Exclude static initializations from being mocked
	  // https://code.google.com/p/jmockit/issues/detail?id=74
	
	private PhoneWatchdogImpl watchdogSUT = new PhoneWatchdogImpl();
	
	@Before
	public void setUp() {
		watchdogSUT.setEventDAO(eventDAO);
		watchdogSUT.setDriverPhoneDAO(driverPhoneDAO);
		watchdogSUT.setPhoneControl(phoneControl);
		watchdogSUT.setSecondsAgoEvents(SECONDS_AGO);
	}
	
	
	@After
	public void tearDown() {
		Mockit.tearDownMocks();
	}

	
	@Test
	public void testGetInterval(){
		
		// Run the actual method
		Date[] returnedInterval = watchdogSUT.getInterval();

		// Calculate the second date
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(returnedInterval[0]);
		calendar.add(Calendar.SECOND, -SECONDS_AGO);
		Date prevDate =  calendar.getTime();
			
		// Compare it with the one returned from the method
		assertEquals(prevDate.compareTo(returnedInterval[1]), 0);
	}	
	
	/** 
	 * Happy path:
	 * One driver with disabled cell phone and No events.
	 * Result: cell phone enabled. 
	 */
	@Test
	public void testEnablePhonesWhenLostCommNoEvents(){
		expectDriversAndEvents(1, 0);
		watchdogSUT.enablePhonesWhenLostComm();
	}

	/** 
	 * One driver with disabled cell phone and One event.
	 * Result: cell phone NOT enabled. 
	 */
	@Test
	public void testEnablePhonesWhenLostCommOneEvent(){
		expectDriversAndEvents(1, 1);
		watchdogSUT.enablePhonesWhenLostComm();		
	}

	/**
	 * One driver with disabled cell phone and One event.
	 * A second driver with disabled cell phone and No events.
	 * Result: cell phone NOT enabled for first, but enabled for second. 
	 */
	@Test
	public void testEnablePhonesWhenLostCommZeroAndOneEvents(){
		int[] numEvents = {1, 0};
		expectDriversAndEvents(2, numEvents);
		watchdogSUT.enablePhonesWhenLostComm();			
	}

	@SuppressWarnings("unchecked")	
	private void expectDriversAndEvents(final int numDrivers, final int[] numEvents){
		new Expectations() {{
			driverPhoneDAO.getDriversWithDisabledPhones(); returns(getDriverIDList(numDrivers));
			for (int i=0; i<numDrivers; i++){
				eventDAO.getEventsForDriver(DRIVER_ID[i], (Date) any, (Date) any, (List<NoteType>) any, EventDAO.INCLUDE_FORGIVEN);
					returns(getEventList(numEvents[i]));
				if (numEvents[i] == 0) {
					phoneControl.handleDriverStoppedMoving(DRIVER_ID[i]);				
				}
			}
		}};		
	}

	/**
	 * Convenience overloaded method to use when same number of events for all drivers
	 * 
	 * @param numDrivers number of drivers
	 * @param numEvents number of events
	 */
	private void expectDriversAndEvents(final int numDrivers, final int numEvents){
		int[] nEvents = new int[numDrivers];
		for (int i=0; i<numDrivers; i++) nEvents[i] = numEvents;
		expectDriversAndEvents(numDrivers, nEvents);
	}
	
	/**
	 * Returns 0, 1 or 2 drivers
	 * @param numDrivers numbers of drivers to return
	 * 
	 * @return list of drivers
	 */
	private List<Integer> getDriverIDList(int numDrivers) {
		List<Integer> driverIDList = new ArrayList<Integer>();
		
		for (int i=0; i<numDrivers; i++){
			driverIDList.add(DRIVER_ID[i]);
		}
		return driverIDList;
	}
		

	/**
	 * Returns 0 or 1 event
	 * 
	 * @param numEvents Number of events to return
	 * @return list of events
	 */
	private List<Event> getEventList(final int numEvents) {	
		List<Event> eventList = new ArrayList<Event>();

		for (int i=0; i<numEvents; i++){
			eventList.add(new Event());
		}				
		
		return eventList;
	}

}
