package com.inthinc.pro.service.phonecontrol.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventCategory;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.service.phonecontrol.PhoneWatchdog;
import com.inthinc.pro.service.phonecontrol.dao.DriverPhoneDAO;

/**
 * Watchdog to enable phones in case the communication is lost.
 * 
 * @author dcueva
 */
@Component("phoneWatchdogImpl")
public class PhoneWatchdogImpl implements PhoneWatchdog {

	@Autowired
	private DriverPhoneDAO driverPhoneDAO;
	
	@Autowired 
	private EventDAO eventDAO;
	
	@Autowired
	PhoneControlMovementEventHandler phoneControl;
	
	private Integer secondsAgoEvents = new Integer(180); 
	  // Default value: check for events in the last 3 minutes (180 sec). 
	  // Can be modified in the Spring configuration
	
	private static List<NoteType> noteTypes = getNoteTypes();
	
//	Logger logger = Logger.getLogger(clazz);
	
	@Override
	public void enablePhonesWhenLostComm() {

		// Fetch IDs of drivers with disabled phones
		Set<Integer> driverIDSet = driverPhoneDAO.getDriversWithDisabledPhones();
		Date[] interval = getInterval();
		
		// Fetch events for each driver
		for(Integer driverID : driverIDSet) {
			
			List<Event> events = eventDAO.getEventsForDriver(driverID, interval[1], interval[0], noteTypes, EventDAO.INCLUDE_FORGIVEN);
			
			// Enable if no communication events
			if(events.size() == 0){
				phoneControl.handleDriverStoppedMoving(driverID);
				// TODO: this method calls the provider. eg. CellControl. Verify if this is correct, or we just need an internal Inthinc status update
			}
		}
	}

	/** 
	 * Retrieves all note types that we should check for.
	 * 
	 * @return List of all note types to check for.
	 */
	static List<NoteType> getNoteTypes() {
		List<NoteType> noteTypeList = new ArrayList<NoteType>();
		
		// TODO: verify if we really need to get all note types. 
		// It makes sense as we are just checking the device is alive, but it may be heavy in the DB. 
		for(EventCategory category : EventCategory.values()){
			noteTypeList.addAll(NoteType.getNoteTypesInCategory(category));
		}
		return noteTypeList;
	}

	/** 
	 * Returns a time interval between Now and secondsAgoEvents
	 * 
	 * @return time interval
	 */
	Date[] getInterval() {
		Date[] interval = new Date[2];
		
		Calendar calendar = Calendar.getInstance();
		interval[0] = calendar.getTime();
		calendar.add(Calendar.SECOND, -secondsAgoEvents);
		interval[1] = calendar.getTime();
		
		return interval;
	}

	/**
	 * Get the number of seconds ago to check for events, starting from the present time.
	 * 
	 * @return the secondsAgoEvents
	 */
	public Integer getSecondsAgoEvents() {
		return secondsAgoEvents;
	}

	/**
	 * Set the number of seconds ago to check for events, starting from the present time.
	 * 
	 * @param secondsAgoEvents the secondsAgoEvents to set
	 */
	public void setSecondsAgoEvents(Integer secondsAgoEvents) {
		this.secondsAgoEvents = secondsAgoEvents;
	}

	/**
	 * @return the driverPhoneDAO
	 */
	public DriverPhoneDAO getDriverPhoneDAO() {
		return driverPhoneDAO;
	}

	/**
	 * @param driverPhoneDAO the driverPhoneDAO to set
	 */
	public void setDriverPhoneDAO(DriverPhoneDAO driverPhoneDAO) {
		this.driverPhoneDAO = driverPhoneDAO;
	}

	/**
	 * @return the eventDAO
	 */
	public EventDAO getEventDAO() {
		return eventDAO;
	}

	/**
	 * @param eventDAO the eventDAO to set
	 */
	public void setEventDAO(EventDAO eventDAO) {
		this.eventDAO = eventDAO;
	}

	/**
	 * @return the phoneControl
	 */
	public PhoneControlMovementEventHandler getPhoneControl() {
		return phoneControl;
	}

	/**
	 * @param phoneControl the phoneControl to set
	 */
	public void setPhoneControl(PhoneControlMovementEventHandler phoneControl) {
		this.phoneControl = phoneControl;
	}

	
	

 
}
