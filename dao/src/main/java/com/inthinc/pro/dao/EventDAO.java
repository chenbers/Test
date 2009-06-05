package com.inthinc.pro.dao;

import java.util.Date;
import java.util.List;

import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.SpeedingEvent;

public interface EventDAO extends GenericDAO<Event, Integer>
{

    /**
     * getMostRecentEvents -- get a list of the most recent events for a group
     * 
     * @param groupID
     * @param eventCnt
     *            -- max events to retrieve
     * @return
     */
    List<Event> getMostRecentEvents(Integer groupID, Integer eventCnt);

    /**
     * getMostRecentWarnings -- get a list of the most recent warnings for a group
     * 
     * @param groupID
     * @param eventCnt
     *            -- max events to retrieve
     * @return
     */
    List<Event> getMostRecentWarnings(Integer groupID, Integer eventCnt);
    
    List<Event> getMostRecentEmergencies(Integer groupID, Integer eventCnt);

    List<Event> getViolationEventsForDriver(Integer driverID, Date startDate, Date endDate);

    List<Event> getWarningEventsForDriver(Integer driverID, Date startDate, Date endDate);

    List<Event> getEventsForDriverByMiles(Integer driverID, Integer milesBack, List<Integer> eventTypes);

    List<Event> getEventsForDriver(Integer driverID, Date startDate, Date endDate, List<Integer> eventTypes);

    List<Event> getEventsForVehicle(Integer vehicleID, Date startDate, Date endDate, List<Integer> eventTypes);

    List<Event> getEventsForVehicleByMiles(Integer vehicleID, Integer milesBack, List<Integer> eventTypes);

    Integer forgive(Integer driverID, Long noteID);

    Integer unforgive(Integer driverID, Long noteID);

    List<Event> getViolationEventsForGroup(Integer groupID, Integer daysBack);

    List<Event> getWarningEventsForGroup(Integer groupID, Integer daysBack);

    List<Event> getEmergencyEventsForGroup(Integer groupID, Integer daysBack);

    List<Event> getViolationEventsForGroup(Integer groupID, Date startDate, Date endDate);
    
    List<Event> getZoneAlertsForGroup(Integer groupID, Integer daysBack);

    List<Event> getWarningEventsForGroup(Integer groupID, Date startDate, Date endDate);

    List<Event> getEmergencyEventsForGroup(Integer groupID, Date startDate, Date endDate); 
    
    <T> T getEventByType(Long noteID, Class<T> clazz);
}
