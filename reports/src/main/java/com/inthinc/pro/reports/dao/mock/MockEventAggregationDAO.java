package com.inthinc.pro.reports.dao.mock;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.model.aggregation.DriverEventIndex;
import com.inthinc.pro.model.aggregation.DriverForgivenData;
import org.joda.time.Interval;

import com.inthinc.pro.dao.EventAggregationDAO;
import com.inthinc.pro.model.aggregation.DriverForgivenEventTotal;
import com.inthinc.pro.model.event.LastReportedEvent;

public class MockEventAggregationDAO implements EventAggregationDAO{
    
    @Override
    public List<DriverForgivenEventTotal> findDriverForgivenEventTotalsByGroups(List<Integer> groupIds, Interval interval) {
        return Collections.emptyList();
    }
    
    @Override
    public List<LastReportedEvent> findRecentEventByDevice(List<Integer> groupIds, Interval interval) {
        return Collections.emptyList();
    }

    @Override
    public List<DriverForgivenEventTotal> findDriverForgivenEventTotalsByGroups(List<Integer> groupIds, Interval interval, boolean includeInactiveDrivers, boolean includeZeroMilesDrivers) {
        return Collections.emptyList();
    }

    @Override
    public Map<DriverEventIndex, List<DriverForgivenData>> findDriverForgivenDataByGroups(List<Integer> groupIDs, Interval interval, boolean includeInactiveDrivers, boolean includeZeroMilesDrivers) {
        return Collections.emptyMap();
    }

    @Override
    public List<LastReportedEvent> findLastEventForVehicles(List<Integer> groupIds, Interval interval, boolean dontIncludeUnassignedDevice, boolean activeInterval) {
        return Collections.emptyList();
    }

}
