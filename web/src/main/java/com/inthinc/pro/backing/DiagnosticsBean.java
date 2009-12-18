package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.Person;
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
    	return getEventDAO().getWarningEventsForGroup(groupID, getDaysBack(), showExcludedEvents);
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
