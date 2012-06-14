package com.inthinc.pro.reports.dao.mock;

import java.util.Collections;
import java.util.List;

import org.joda.time.Interval;

import com.inthinc.pro.dao.EventAggregationDAO;
import com.inthinc.pro.model.aggregation.DriverForgivenEventTotal;
import com.inthinc.pro.model.event.Event;

public class MockEventAggregationDAO implements EventAggregationDAO{
    
    @Override
    public List<DriverForgivenEventTotal> findDriverForgivenEventTotalsByGroups(List<Integer> groupIds, Interval interval) {
        return Collections.emptyList();
    }
    
    @Override
    public List<Event> findRecentEventByDevice(List<Integer> groupIds, Interval interval) {
        // TODO Auto-generated method stub
        return null;
    }

}
