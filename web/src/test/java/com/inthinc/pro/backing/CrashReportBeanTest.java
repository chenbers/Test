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
		crashReport.setLat(0.001);
		crashReport.setLng(0.001);
		return crashReport;		
	}
	
	public Trip getTrip() {
		Trip crashReportTrip = new Trip();
		crashReportTrip.setDriverID(1);
		return crashReportTrip;
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
		
		assertEquals(crashReportTrip, crashReportDAO.getTrip(crashReport));
		crashReportTrip = getTrip();
				
		DateTime crashTime = new DateTime(crashReport.getDate());
		List<Event> driverEvents = eventDAO.getEventsForDriver(78278, crashTime.minusHours(3).toDate(), crashTime.plusHours(3).toDate(), getCrashEventTypeList(), 1);
		assertNotNull(driverEvents);
				
		assertTrue(driverEvents.isEmpty());
		System.out.println("crashReport lat/lng:" +crashReport.getLat()+","+crashReport.getLng());
		crashReport = populateAddresses(driverEvents);
		System.out.println("crashReport lat/lng:" +crashReport.getLat()+","+crashReport.getLng());
		
		crashReportTrip = getCrashReportTrip();
		System.out.println("crashReportTrip lat/lng:" +crashReportTrip.getStartLat()+","+crashReportTrip.getStartLng());
		
		crashReport = getValidCrashReport();
		System.out.println("crashReport lat/lng:" +crashReport.getLat()+","+crashReport.getLng());
	}   
	
	public CrashReport populateAddresses(List<Event> crashEvents) {
    	Event e = new Event();
    	crashEvents.add(e);
    	DateTime crashTime = new DateTime(crashReport.getDate());
    	long absTimeDiff;
    	long diff=0;
    	Event validEvent = null;
    	crashEvents.get(0).setLatitude(44.0001); crashEvents.get(0).setLongitude(114.0001);
    	for (Event event : crashEvents) {
    		
    		// Check if event lat/lng are valid
    		if ((event.getLatitude() > 0.005 || event.getLatitude() < -0.005) && (event.getLongitude() > 0.005 || event.getLongitude() < -0.005)){
    			DateTime eventTime = new DateTime(event.getTime());
    			// to find closest event with current crashReport
    			absTimeDiff = Math.abs(eventTime.getMillis() - crashTime.getMillis());
    			if(diff==0 || (absTimeDiff <= diff)){
    				diff = absTimeDiff;
    				validEvent = event;
    			}
			}
		}
    	// Replace invalid crashReport lat/lng with closest event from cachedNote
    	if(validEvent!=null){
			crashReport.setLat(validEvent.getLatitude() + 0.00001);
			crashReport.setLng(validEvent.getLongitude());
		}
		return crashReport;    	
    }
    
	public CrashReport getValidCrashReport(){
		if(crashReportTrip!=null && ((crashReportTrip.getStartLat() > 0.005 || crashReportTrip.getStartLat() < -0.005) && (crashReportTrip.getStartLng() > 0.005 || crashReportTrip.getStartLng() < -0.005))){
			crashReport.setLat(crashReportTrip.getStartLat()+ 0.00005);
			crashReport.setLng(crashReportTrip.getStartLng());
		}
		return crashReport;
	}
    
    public Trip getCrashReportTrip(){
		Trip crashReportTrip = new Trip();
		crashReportTrip.setDriverID(1);
		crashReportTrip.setStartLat(40.000);
		crashReportTrip.setStartLng(111.001);
		return crashReportTrip;
	}

    
}
