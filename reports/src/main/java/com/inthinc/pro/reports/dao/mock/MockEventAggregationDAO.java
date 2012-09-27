package com.inthinc.pro.reports.dao.mock;

import java.util.Collections;
import java.util.List;

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
        // TODO Auto-generated method stub
        return Collections.emptyList();
    }

}
