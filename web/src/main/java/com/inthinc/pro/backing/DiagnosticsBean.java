package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.reports.ReportCriteria;

public class DiagnosticsBean extends BaseEventsBean
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(DiagnosticsBean.class);

    @Override
    protected List<Event> getEventsForGroup(Integer groupID)
    {
    	List<Event> warnings = getEventDAO().getWarningEventsForGroup(groupID, 7,showExcludedEvents);
    	List<Integer> eventTypes = new ArrayList<Integer>();
    	eventTypes.add(EventMapper.TIWIPRO_EVENT_NO_DRIVER);
    	List<Event> noDriverWarnings = getEventDAO().getEventsForGroupFromVehicles(groupID, eventTypes, 7);
    	warnings.addAll(noDriverWarnings);
    	Collections.sort(warnings);
    	
        return warnings;
    }

    // TablePrefOptions interface

    @Override
    public TableType getTableType()
    {
        return TableType.DIAGNOSTICS;
    }

    public String showAllFromRecentAction()
    {
        setCategoryFilter(null);
        setEventFilter(null);

        clearData();
        return "go_diagnostics";
    }
    @Override
    protected ReportCriteria getReportCriteria()
    {
        return getReportCriteriaService().getWarningsReportCriteria(getUser().getGroupID(), getLocale());
    }

}
