package com.inthinc.pro.backing;

import static org.easymock.classextension.EasyMock.createMock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.easymock.classextension.EasyMock;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.inthinc.pro.dao.CrashReportDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.model.CrashReport;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventSubCategory;
import com.inthinc.pro.model.event.NoteType;


public class CrashReportBeanTest extends  BaseBeanTest{
	
	private Trip crashReportTrip;

	private CrashReport crashReport;

	private CrashReportBean crashReportBean;
	
	List<Event> crashEvents = new ArrayList<Event>();
	
	Properties prop = new Properties();

	@Before
	public void setUp() throws Exception {
		
		crashReportBean = (CrashReportBean)applicationContext.getBean("crashReportBean");
		crashReportBean.setCrashReport(getCrashReport());		
		crashReport = crashReportBean.getCrashReport();
		crashReportTrip = null;		
	}

	@After
	public void tearDown() throws Exception {	
	}	
	
	public CrashReport getCrashReport() {
		CrashReport crashReport = new CrashReport();
		crashReport.setDriverID(1627);
		crashReport.setDate(new Date(1386355907000L));
		crashReport.setVehicleID(5042);
		crashReport.setLat(0.0);
		crashReport.setLng(0.0);
		return crashReport;		
	}
	
	//Test of getCrashReportTrip() method for Valid CrashReport Value
	@Test
	public void testGetCrashReportTripNotNullCrashReport() {
		CrashReportDAO mockCrashReportDAO = createMock(CrashReportDAO.class);
		crashReportTrip = mockCrashReportDAO.getTrip(crashReport);	
		ReflectionTestUtils.setField(crashReportBean, "crashReportTrip", crashReportTrip);
		crashReportBean.getCrashReportTrip();
	}
	
	//Test of getCrashReportTrip() method for Valid CrashReportTrip Value	
	@Test
	public void testGetCrashReportTripNotNullCrashReportTrip() {
		CrashReportDAO mockCrashReportDAO = createMock(CrashReportDAO.class);
		crashReportTrip = mockCrashReportDAO.getTrip(crashReport);	
		ReflectionTestUtils.setField(crashReportBean, "crashReportTrip", crashReportTrip);
		crashReportBean.getCrashReportTrip();
	}
	
	//Test of getCrashReportTrip() method for Null CrashReport Value
	@Test
	public void testGetCrashReportTripNullCrashReport() {
		CrashReport crashReport = null;
		CrashReportDAO mockCrashReportDAO = createMock(CrashReportDAO.class);
		crashReportTrip = mockCrashReportDAO.getTrip(crashReport);	
		ReflectionTestUtils.setField(crashReportBean, "crashReportTrip", crashReportTrip);
		crashReportBean.getCrashReportTrip();
	}
	
	//Test of getCrashReportTrip() method for Null CrashReportTrip Value
	@Test
	public void testGetCrashReportTripNullCrashReportTrip() {
		crashReportBean.getCrashReportTrip();
	}
	
	//Test of getCrashReportTrip() method for Null CrashReport and Null CrashReportTrip Value
	@Test
	public void testGetCrashReportTripNullCrashReportNullCrashReportTrip() {
		crashReportBean.getCrashReportTrip();
		crashReport = null;
		crashReportBean.getCrashReportTrip();		
	}
	
	//Test of getCrashReportTrip() method for Valid CrashReport and Valid CrashReportTrip Value
	@Test
	public void testGetCrashReportTripNotNullCrashReportNotNullCrashReportTrip() {
		CrashReportDAO mockCrashReportDAO = createMock(CrashReportDAO.class);
		crashReportTrip = mockCrashReportDAO.getTrip(crashReport);	
		ReflectionTestUtils.setField(crashReportBean, "crashReportTrip", crashReportTrip);
		crashReportBean.getCrashReportTrip();
	}	
	
	//Test of getCrashReportTrip() method for InValid CrashEvents and Valid CrashReportTrip Value
	@Test
	public void testGetCrashReportTripNullCrashEvent() {		
		crashReportTrip = getCrashReportTrip(crashReport);	
		ReflectionTestUtils.setField(crashReportBean, "crashReportTrip", crashReportTrip);
		crashReportBean.getCrashReportTrip();
	}
	
	//Test of getCrashReportTrip() and populateAddresses() methods for Valid DriverID
	@Test
	public void testGetCrashReportTripCrashEventForDriver() throws SecurityException, NoSuchMethodException {
		crashEvents = getCrashEvents(crashReport.getDriverID());
		ReflectionTestUtils.setField(crashReportBean, "crashEvents", crashEvents);
		EventDAO mockEventDAO = createMock(EventDAO.class);
		crashReportBean.setEventDAO(mockEventDAO);
		DateTime crashTime = new DateTime(crashReport.getDate()); 
		EasyMock.expect(mockEventDAO.getEventsForDriver(1627, crashTime.minusHours(1).toDate(), crashTime.plusHours(1).toDate(), getCrashEventTypeList(), 1)).andReturn(crashEvents);
		EasyMock.replay(mockEventDAO);
		crashReportBean.getCrashReportTrip();		
	}
	
	//Test of getCrashReportTrip() and populateAddresses() methods for Valid VehicleID
	@Test
	public void testGetCrashReportTripCrashEventForVehicle() {
		crashReport.setDriverID(null);
		crashEvents = getCrashEvents(crashReport.getVehicleID());
		ReflectionTestUtils.setField(crashReportBean, "crashEvents", crashEvents);
		EventDAO mockEventDAO = createMock(EventDAO.class);
		crashReportBean.setEventDAO(mockEventDAO);
		DateTime crashTime = new DateTime(crashReport.getDate()); 
		EasyMock.expect(mockEventDAO.getEventsForVehicle(5042, crashTime.minusHours(1).toDate(), crashTime.plusHours(1).toDate(), getCrashEventTypeList(), 1)).andReturn(crashEvents);
		EasyMock.replay(mockEventDAO);		
		crashReportBean.getCrashReportTrip();		
	}
	
	
	// Hard code crashReportTrip value
	public Trip  getCrashReportTrip(CrashReport crashReport){
		Trip crashReportTrip = new Trip();
		crashReportTrip.setDriverID(1);
		crashReportTrip.setStartLat(40.00004);
		crashReportTrip.setStartLng(111.001);
		return crashReportTrip;			
	}
	
	// Crash Event Type List to retrieve it from eventDao
	public List<NoteType> getCrashEventTypeList() {		
		List<NoteType> crashEventTypeList = new ArrayList<NoteType>();
		crashEventTypeList.add(NoteType.CRASH_DATA);
		crashEventTypeList.add(NoteType.FULLEVENT);
		crashEventTypeList.add(NoteType.SEATBELT);
		crashEventTypeList.addAll(EventSubCategory.SPEED.getNoteTypesInSubCategory());
		crashEventTypeList.add(NoteType.ACCELERATION);
		crashEventTypeList.add(NoteType.LOCATION);
		crashEventTypeList.add(NoteType.PARKING_BRAKE);
		crashEventTypeList.addAll(EventSubCategory.DRIVING_STYLE.getNoteTypesInSubCategory());
		crashEventTypeList.add(NoteType.DSS_MICROSLEEP);
		crashEventTypeList.add(NoteType.BACKING);
		return crashEventTypeList;
	}

	// Hard coded CrashEvent values
	public List<Event> getCrashEvents(int ID){
		
		List<Event> crashEvents = new ArrayList<Event>();
		Event e1 = new Event();
		crashEvents.add(e1);
		crashEvents.get(0).setTime(new Date(1386355901520L));
		crashEvents.get(0).setLatitude(44.0001);
		crashEvents.get(0).setLongitude(-114.0001);
		
		Event e2 = new Event();
		crashEvents.add(e2);
		crashEvents.get(1).setTime(new Date(1386355907000L));
		crashEvents.get(1).setLatitude(0.0001);
		crashEvents.get(1).setLongitude(0.0001);
		
		Event e3 = new Event();
		crashEvents.add(e3);
		crashEvents.get(2).setTime(new Date(1386355907003L));
		crashEvents.get(2).setLatitude(42.0561);
		crashEvents.get(2).setLongitude(-110.7520);		
		
		Event e4 = new Event();
		crashEvents.add(e4);
		crashEvents.get(3).setTime(new Date(1386355907001L));
		crashEvents.get(3).setLatitude(56.1385);
		crashEvents.get(3).setLongitude(-124.2960);	
		
		return crashEvents;	
	}

}	
