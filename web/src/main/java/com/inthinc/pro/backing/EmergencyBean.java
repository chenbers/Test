package com.inthinc.pro.backing;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.reports.ReportCriteria;

public class EmergencyBean extends BaseEventsBean
{
    private static final Logger logger = Logger.getLogger(DiagnosticsBean.class);

    @Override
    protected List<Event> getEventsForGroup(Integer groupID)
    {
        return getEventDAO().getEmergencyEventsForGroup(groupID, 7,showExcludedEvents);
    }

    // TablePrefOptions interface

    @Override
    public TableType getTableType()
    {
        return TableType.EMERGENCY;
    }

    public String showAllFromRecentAction()
    {
        setCategoryFilter(null);
        setEventFilter(null);

        clearData();
        return "go_emergency";
    }
    @Override
    protected ReportCriteria getReportCriteria()
    {
        return getReportCriteriaService().getEmergencyReportCriteria(getUser().getGroupID());
    }
}
