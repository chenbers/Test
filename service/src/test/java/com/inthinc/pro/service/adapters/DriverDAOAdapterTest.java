package com.inthinc.pro.service.adapters;

import static mockit.Mockit.tearDownMocks;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import mockit.Cascading;
import mockit.Expectations;
import mockit.Mocked;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.dao.report.DriverReportDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.model.aggregation.Trend;
import com.inthinc.pro.model.event.Event;

/**
 * Test for the DriverDAOAdapter class
 * 
 * @author dcueva
 */
public class DriverDAOAdapterTest {

	private final Integer GROUP_ID = new Integer(1);
	private final Integer PERSON_ID = new Integer(2);
	private final Integer DRIVER_ID = new Integer(3);
	private final Driver driver = new Driver();
	
	@Mocked(methods = {"getGroupID"})
	private DriverDAOAdapter adapterSUT; 
	
	@Mocked private DriverDAO driverDAOMock;
	@Mocked private DriverReportDAO driverReportDAOMock;
	@Mocked private EventDAO eventDAOMock;
	
	@Before
	public void beforeMethod() {
		adapterSUT.setDriverDAO(driverDAOMock);
	}
	
	@After
	public void afterMethod(){
		tearDownMocks();
	}
	
	@Test
	public void testGetAll(){
		final List<Driver> driverList = new ArrayList<Driver>();
		driverList.add(driver);
		
		new Expectations(){{
			adapterSUT.getGroupID(); returns(GROUP_ID);
			driverDAOMock.getAllDrivers(GROUP_ID); returns(driverList);
		}};
		assertEquals(adapterSUT.getAll(), driverList);
	}	
	
	@Test
	public void testGetDAO(){
		assertEquals(adapterSUT.getDAO(), driverDAOMock);
	}		

	@Test
	public void testGetResourceCreationID(){
		driver.setPersonID(PERSON_ID);
		
		assertEquals(adapterSUT.getResourceCreationID(driver), PERSON_ID);
	}	
	
	@Test
	public void testGetResourceID(){
		driver.setDriverID(DRIVER_ID);
		
		assertEquals(adapterSUT.getResourceID(driver), DRIVER_ID);
	}
	
	@Test
	public void testGetScore(){
		adapterSUT.setDriverReportDAO(driverReportDAOMock);
		final Score score = new Score();
		
		new Expectations(){{
			driverReportDAOMock.getScore(DRIVER_ID, Duration.DAYS); returns(score);
		}};
		assertEquals(adapterSUT.getScore(DRIVER_ID, Duration.DAYS), score);
		
	}
	
	@Test
	public void testGetSpeedingEvents(@Mocked @Cascading final DateTime dateTimeMock){
		adapterSUT.setEventDAO(eventDAOMock);
		final List<Event> eventList = new ArrayList<Event>();
		
		new Expectations(){{
			new DateTime();
	        eventDAOMock.getViolationEventsForDriver(DRIVER_ID, dateTimeMock.minusMonths(1).toDate(), dateTimeMock.toDate(), 1);
	        returns(eventList);
		}};
		assertEquals(adapterSUT.getSpeedingEvents(DRIVER_ID), eventList);
		
	}
	
	@Test
	public void testGetTrend(){
		adapterSUT.setDriverReportDAO(driverReportDAOMock);
		final List<Trend> trendList = new ArrayList<Trend>();
		
		new Expectations(){{
			driverReportDAOMock.getTrend(DRIVER_ID, Duration.TWELVE); returns(trendList);
		}};
		assertEquals(adapterSUT.getTrend(DRIVER_ID, Duration.TWELVE), trendList);
	}
	
	
}
