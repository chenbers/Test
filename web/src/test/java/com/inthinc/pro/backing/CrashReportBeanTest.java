package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.dao.CrashReportDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.model.CrashReport;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventSubCategory;
import com.inthinc.pro.model.event.NoteType;


public class CrashReportBeanTest extends  BaseBeanTest{
	
	private CrashReportDAO crashReportDAO;
	
	private EventDAO eventDAO;
	
	private Trip crashReportTrip;

	private CrashReport crashReport;

	private CrashReportBean crashReportBean;

	@Before
	public void setUp() throws Exception {
		
		crashReportBean = (CrashReportBean)applicationContext.getBean("crashReportBean");
		assertNotNull(crashReportBean);
				
		crashReportBean.setCrashReportDAO(getCrashReportDAO());
		crashReportDAO = crashReportBean.getCrashReportDAO();
		assertNotNull(crashReportDAO);
						
		crashReportBean.setCrashReport(getCrashReport());
		crashReport = crashReportBean.getCrashReport();
		assertNotNull(crashReport);
				
		crashReportBean.setEventDAO(getEventDAO());
		eventDAO = crashReportBean.getEventDAO();
		assertNotNull(eventDAO);
	}

	@After
	public void tearDown() throws Exception {	
	}

	public CrashReportDAO getCrashReportDAO() {
		crashReportDAO = (CrashReportDAO)applicationContext.getBean("crashReportDAO");
		return crashReportDAO;
	}
	
	public EventDAO getEventDAO() {
		eventDAO = (EventDAO)applicationContext.getBean("eventDAO");
		return eventDAO;
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
	
	public List<NoteType> getCrashEventTypeList() {

		// Crash Event Type List to retrieve it from eventDao
		List<NoteType> crashEventTypeList = new ArrayList<NoteType>();
		crashEventTypeList.add(NoteType.CRASH_DATA);
		crashEventTypeList.add(NoteType.FULLEVENT);
		crashEventTypeList.add(NoteType.SEATBELT);
		crashEventTypeList.addAll(EventSubCategory.SPEED
				.getNoteTypesInSubCategory());
		crashEventTypeList.add(NoteType.ACCELERATION);
		crashEventTypeList.add(NoteType.LOCATION);
		crashEventTypeList.add(NoteType.PARKING_BRAKE);
		crashEventTypeList.addAll(EventSubCategory.DRIVING_STYLE
				.getNoteTypesInSubCategory());
		crashEventTypeList.add(NoteType.DSS_MICROSLEEP);
		crashEventTypeList.add(NoteType.BACKING);

		return crashEventTypeList;
	}
	
	@Test
	public void testGetCrashReportTrip() {
		
		//search for Valid Trip associated with Crash
		crashReportTrip = getCrashReportTrip();
				
		//If no trip found for associated crash go for Note Events
		List<Event> driverEvents = getEventsForDriver();
		assertNotNull(driverEvents);
		System.out.println("crashReport lat/lng before using Note Events:" +crashReport.getLat()+","+crashReport.getLng());
		//Replace invalid crashReport lat/lng with valid Note Event lat/lng
		crashReportBean.populateAddresses(driverEvents);
		System.out.println("crashReport lat/lng after using Note Events:" +crashReport.getLat()+","+crashReport.getLng());
		
		//Check if there are invalid crashReport lat/lng with valid trip details 
		System.out.println("crashReportTrip lat/lng:" +crashReportTrip.getStartLat()+","+crashReportTrip.getStartLng());
		crashReport = getValidCrashReport();
		System.out.println("Invalid crashReport lat/lng changed with valid trip lat/lng:" +crashReport.getLat()+","+crashReport.getLng());
	}   
	
	public Trip getCrashReportTrip(){
		
		Trip crashReportTrip = new Trip();
		crashReportTrip.setDriverID(1);
		crashReportTrip.setStartLat(40.00004);
		crashReportTrip.setStartLng(111.001);
		return crashReportTrip;
	}
	
	public List<Event> getEventsForDriver(){
		
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
	
	public CrashReport getValidCrashReport(){
		
		if(crashReportTrip!=null && ((crashReportTrip.getStartLat() > 0.005 || crashReportTrip.getStartLat() < -0.005) && (crashReportTrip.getStartLng() > 0.005 || crashReportTrip.getStartLng() < -0.005))){
			crashReport.setLat(crashReportTrip.getStartLat()+ 0.00005);
			crashReport.setLng(crashReportTrip.getStartLng());
		}
		return crashReport;
	}
	
}
