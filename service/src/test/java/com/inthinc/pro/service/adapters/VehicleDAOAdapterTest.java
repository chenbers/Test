package com.inthinc.pro.service.adapters;

import static mockit.Mockit.tearDownMocks;
import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import mockit.Expectations;
import mockit.Mocked;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.report.VehicleReportDAO;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.model.aggregation.Trend;
import com.inthinc.pro.model.event.Event;

/**
 * Test for the VehicleDAOAdapter class
 * 
 * @author dcueva
 */
public class VehicleDAOAdapterTest {

	private final Integer GROUP_ID = new Integer(1);
	private final Integer VEHICLE_ID = new Integer(2);
	private final Integer DEVICE_ID = new Integer(3);
	private final Integer DRIVER_ID = new Integer(4);
	private final Vehicle vehicle = new Vehicle();
	private final String VIN = "VIN";

	@Mocked(methods = {"getGroupID"})	
	private VehicleDAOAdapter adapterSUT; 
	
	@Mocked private VehicleDAO vehicleDAOMock;
	@Mocked private EventDAO eventDAOMock;
	@Mocked private VehicleReportDAO vehicleReportDAOMock;   
	
	
	@Before
	public void beforeMethod() {
		adapterSUT.setVehicleDAO(vehicleDAOMock);
	}
	
	@After
	public void afterMethod(){
		tearDownMocks();
	}
	
	@Test
	public void testGetAll(){
		final List<Vehicle> vehicleList = new ArrayList<Vehicle>();
		vehicleList.add(vehicle);
		
		new Expectations(){{
			adapterSUT.getGroupID(); returns(GROUP_ID);
			vehicleDAOMock.getVehiclesInGroupHierarchy(GROUP_ID); returns(vehicleList);
		}};
		assertEquals(adapterSUT.getAll(), vehicleList);
	}	
	
	@Test
	public void testGetDAO(){
		assertEquals(adapterSUT.getDAO(), vehicleDAOMock);
	}		

	@Test
	public void testGetResourceCreationID(){
		vehicle.setGroupID(GROUP_ID);
		
		assertEquals(adapterSUT.getResourceCreationID(vehicle), GROUP_ID);
	}	
	
	@Test
	public void testGetResourceID(){
		vehicle.setVehicleID(VEHICLE_ID);
		
		assertEquals(adapterSUT.getResourceID(vehicle), VEHICLE_ID);
	}

	@Test
	public void testAssignDevice(){
		new Expectations(){{
			vehicleDAOMock.setVehicleDevice(VEHICLE_ID, DEVICE_ID);
			vehicleDAOMock.findByID(VEHICLE_ID); returns(vehicle);
		}};
		assertEquals(adapterSUT.assignDevice(VEHICLE_ID, DEVICE_ID), vehicle);
	}
	
	@Test
	public void testAssignDriver(){
		new Expectations(){{
			vehicleDAOMock.setVehicleDriver(VEHICLE_ID, DRIVER_ID);
			vehicleDAOMock.findByID(VEHICLE_ID); returns(vehicle);
		}};
		assertEquals(adapterSUT.assignDriver(VEHICLE_ID, DRIVER_ID), vehicle);
	}	
	
	@Test
	public void testFindByVIN(){
		new Expectations(){{
			vehicleDAOMock.findByVIN(VIN); returns(vehicle);
		}};
		assertEquals(adapterSUT.findByVIN(VIN), vehicle);
	}

	@Test
	public void testGetEvents(){
		adapterSUT.setEventDAO(eventDAOMock);
		final List<Event> eventList = new ArrayList<Event>();
		eventList.add(new Event());
		
		
		new Expectations(){{
			eventDAOMock.getEventsForVehicle(VEHICLE_ID, (Date) any, (Date) any, null, 0); returns(eventList);
		}};
		assertEquals(adapterSUT.getEvents(VEHICLE_ID, new Date(1L), new Date(1L)), eventList);
	}

	@Test
	public void testGetLastLocation(){
		final LastLocation lastLocation = new LastLocation();
		
		new Expectations(){{
			vehicleDAOMock.getLastLocation(VEHICLE_ID); returns(lastLocation);
		}};
		assertEquals(adapterSUT.getLastLocation(VEHICLE_ID), lastLocation);
	}	

	@Test
	public void testGetScore(){
		adapterSUT.setVehicleReportDAO(vehicleReportDAOMock);
		final Score score = new Score();
		
		new Expectations(){{
			vehicleReportDAOMock.getScore(VEHICLE_ID, Duration.DAYS); returns(score);
		}};
		assertEquals(adapterSUT.getScore(VEHICLE_ID, Duration.DAYS), score);
	}	
	
	@Test
	public void testGetTrend(){
		adapterSUT.setVehicleReportDAO(vehicleReportDAOMock);
		final List<Trend> trendList = new ArrayList<Trend>();
		trendList.add(new Trend());
		
		new Expectations(){{
			vehicleReportDAOMock.getTrend(VEHICLE_ID, Duration.DAYS); returns(trendList);
		}};
		assertEquals(adapterSUT.getTrend(VEHICLE_ID, Duration.DAYS), trendList);
	}	

	@Test
	public void testGetTrips(){
		final List<Trip> tripList = new ArrayList<Trip>();
		tripList.add(new Trip());
		
		new Expectations(){{
			vehicleDAOMock.getTrips(VEHICLE_ID, (Date) any, (Date) any); returns(tripList);
		}};
		assertEquals(adapterSUT.getTrips(VEHICLE_ID, new Date(1L), new Date(1L)), tripList);
	}	
}
