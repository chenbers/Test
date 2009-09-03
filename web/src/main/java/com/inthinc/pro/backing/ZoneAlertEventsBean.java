package com.inthinc.pro.backing;

import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.TableType;

public class ZoneAlertEventsBean extends BaseEventsBean
{
    
    private static final Logger logger = Logger.getLogger(DiagnosticsBean.class);

    @Override
    protected List<Event> getEventsForGroup(Integer groupID)
    {
        return getEventDAO().getZoneAlertsForGroup(groupID, 7,showExcludedEvents);
    }

    // TablePrefOptions interface

    @Override
    public TableType getTableType()
    {
        return TableType.ZONE_ALERTS;
    }

    public String showAllFromRecentAction()
    {
        setCategoryFilter(null);
        setEventFilter(null);

        clearData();
        return "pretty:zoneAlerts";
    }

}
