package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.aggregation.DriverForgivenEvent;
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
     *
     * @return
     */
    public List<DriverForgivenEvent> findDriverForgivenEventsByGroups(List<Integer> groupIds,Interval interval);


    /**
     * @param groupIds A list of drivers groupIDs
     * @param interval The date range to filter on for the events.
     * @param includeInactiveDrivers 
     * @param includeZeroMilesDrivers
     * @return
     */
    public List<DriverForgivenEventTotal> findDriverForgivenEventTotalsByGroups(List<Integer> groupIds,Interval interval, boolean includeInactiveDrivers, boolean includeZeroMilesDrivers);

    /**
     * @param groupIds A list of drivers groupIDs
     * @param interval The date range to filter on for the events.
     * @param includeInactiveDrivers
     * @param includeZeroMilesDrivers
     * @return
     */
    public List<DriverForgivenEvent> findDriverForgivenEventsByGroups(List<Integer> groupIds,Interval interval, boolean includeInactiveDrivers, boolean includeZeroMilesDrivers);


    /**
     * Returns the last event that was sent in for devices currently assigned to vehicles for the groups.
     * Deprecated because of performance issues with doing it this way i.e. querying the note shards directly.
     * Leaving though for now to verify that the new method for this (findLastEventForVehicles) returns the same results.  
     * 
     * @param groupIds
     * @param interval
     * @return
     */
    @Deprecated
    public List<LastReportedEvent> findRecentEventByDevice(List<Integer> groupIds,Interval interval);

    
    /**
     * Returns the last event that was sent in for vehicles for the groups. (used in the vehicle non-comm report)
     * 
     * @param groupIds
     * @param interval
     * @return
     */
    public List<LastReportedEvent> findLastEventForVehicles(List<Integer> groupIds,Interval interval, boolean dontIncludeUnassignedDevice, boolean activeInterval);

}
