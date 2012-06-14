package com.inthinc.pro.dao;

import java.util.List;

import org.joda.time.Interval;

import com.inthinc.pro.model.aggregation.DriverForgivenEventTotal;
import com.inthinc.pro.model.event.Event;

/**
 * 
 * @author mstrong
 * 
 * This DeventAggregationDAO provides an api to retrieve aggregated data
 * centered around events
 *
 */
public interface EventAggregationDAO {
    
    /**
     * 
     * @param groupIds A list of drivers groupIDs
     * @param interval The date range to filter on for the events.
     * 
     * @return
     */
    public List<DriverForgivenEventTotal> findDriverForgivenEventTotalsByGroups(List<Integer> groupIds,Interval interval);
    
    /**
     * Returns the last event that was sent in for devices currently assigned to vehicles for the groups.
     * 
     * @param groupIds
     * @param interval
     * @return
     */
    public List<Event> findRecentEventByDevice(List<Integer> groupIds,Interval interval);

}
