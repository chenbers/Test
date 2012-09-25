package com.inthinc.pro.dao;

import java.util.List;

import org.joda.time.Interval;

import com.inthinc.pro.model.aggregation.DriverForgivenEventTotal;
import com.inthinc.pro.model.event.LastReportedEvent;

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
     * @param groupIds A list of drivers groupIDs
     * @param interval The date range to filter on for the events.
     * @param includeInactiveDrivers 
     * @param includeZeroMilesDrivers
     * @return
     */
    public List<DriverForgivenEventTotal> findDriverForgivenEventTotalsByGroups(List<Integer> groupIds,Interval interval, boolean includeInactiveDrivers, boolean includeZeroMilesDrivers);
    
    /**
     * Returns the last event that was sent in for devices currently assigned to vehicles for the groups.
     * 
     * @param groupIds
     * @param interval
     * @return
     */
    public List<LastReportedEvent> findRecentEventByDevice(List<Integer> groupIds,Interval interval);

}
