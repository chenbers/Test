package com.inthinc.pro.dao;

import java.util.Date;
import java.util.List;

import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;

public interface EventDAO extends GenericDAO<Event, Integer>
{
    public static final Integer EXCLUDE_FORGIVEN = 0;
    public static final Integer INCLUDE_FORGIVEN = 1;

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
     * @param eventCount
     *            -- max events to retrieve
     * @return
     */
    List<Event> getMostRecentWarnings(Integer groupID, Integer eventCount);
    
    List<Event> getMostRecentEmergencies(Integer groupID, Integer eventCount);

    List<Event> getViolationEventsForDriver(Integer driverID, Date startDate, Date endDate, Integer includeForgiven);

    List<Event> getWarningEventsForDriver(Integer driverID, Date startDate, Date endDate, Integer includeForgiven);

    List<Event> getEmergencyEventsForDriver(Integer driverID, Date startDate, Date endDate, Integer includeForgiven);

    List<Event> getEventsForDriver(Integer driverID, Date startDate, Date endDate, List<NoteType> noteTypes, Integer includeForgiven);

    List<Event> getEventsForVehicle(Integer vehicleID, Date startDate, Date endDate, List<NoteType> noteTypes, Integer includeForgiven);

    Integer forgive(Integer driverID, Long noteID);

    Integer unforgive(Integer driverID, Long noteID);

    List<Event> getEventsForGroupFromVehicles(Integer groupID, List<NoteType> eventTypes, Date startDate, Date endDate);

    List<Event> getEventsForGroupFromVehicles(Integer groupID, List<NoteType> noteTypes, Integer daysBack);
    
    Event getEventNearLocation(Integer driverID,Double latitude,Double longitude,Date startDate,Date endDate);
    
    <T> T getEventByType(Long noteID, Class<T> clazz);

    Event findByID(Long id);

    // pagination methods
	Integer  getEventCount(Integer groupID, Date startDate, Date endDate, Integer includeForgiven, List<NoteType> noteTypes, List<TableFilterField> filters);

	List<Event> getEventPage(Integer groupID, Date startDate, Date endDate, Integer includeForgiven, List<NoteType> noteTypes, PageParams pageParams);

}
