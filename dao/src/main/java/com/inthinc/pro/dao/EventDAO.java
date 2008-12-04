package com.inthinc.pro.dao;

import java.util.Date;
import java.util.List;

import com.inthinc.pro.model.Event;


public interface EventDAO  extends GenericDAO<Event, Integer>
{

    /**
     * getMostRecentEvents -- get a list of the most recent events for a group 
     * 
     * @param groupID
     * @param eventCnt  -- max events to retrieve
     * @return
     */
    List<Event> getMostRecentEvents(Integer groupID, Integer eventCnt);


    /**
     * getMostRecentWarnings -- get a list of the most recent warnings for a group 
     * 
     * @param groupID
     * @param eventCnt  -- max events to retrieve
     * @return
     */
    List<Event> getMostRecentWarnings(Integer groupID, Integer eventCnt);

    List<Event> getViolationEventsForDriver(Integer driverID, Date startDate, Date endDate);

    List<Event> getWarningEventsForDriver(Integer driverID, Date startDate, Date endDate);

    List<Event> getEventsForDriver(Integer driverID, Date startDate, Date endDate, List<Integer> eventTypes);

}
