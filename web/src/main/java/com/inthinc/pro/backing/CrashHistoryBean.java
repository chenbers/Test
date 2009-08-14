package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.richfaces.event.DataScrollerEvent;

import com.inthinc.pro.backing.ui.CrashHistoryReportItem;
import com.inthinc.pro.backing.ui.EventReportItem;
import com.inthinc.pro.backing.ui.RedFlagReportItem;
import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.RedFlagDAO;
import com.inthinc.pro.dao.TablePreferenceDAO;
import com.inthinc.pro.dao.ZoneDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventCategory;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.ZoneArrivalEvent;
import com.inthinc.pro.model.ZoneDepartureEvent;
import com.inthinc.pro.reports.ReportCriteria;

public class CrashHistoryBean extends BaseCrashBean 
{
    private static final Logger logger = Logger.getLogger(CrashHistoryBean.class);    

    @Override
    protected List<Event> getEventsForGroup(Integer groupID)
    {
        return getEventDAO().getWarningEventsForGroup(groupID, 0,showExcludedEvents);
    }

    // TablePrefOptions interface

    @Override
    public TableType getTableType()
    {
        return TableType.CRASH_HISTORY;
    }

    public String showAllFromRecentAction()
    {
//        setCategoryFilter(null);
//        setEventFilter(null);

        refreshAction();
        return "go_crashHistory";
    }

    public void exportReportToPdf()
    {
        getReportRenderer().exportSingleReportToPDF(getReportCriteria(), getFacesContext());
    }

    public void emailReport()
    {
        getReportRenderer().exportReportToEmail(getReportCriteria(), getEmailAddress());
    }

    public void exportReportToExcel()
    {
        getReportRenderer().exportReportToExcel(getReportCriteria(), getFacesContext());
    }

    private ReportCriteria getReportCriteria()
    {
        ReportCriteria reportCriteria = getReportCriteriaService().getCrashHistoryReportCriteria(getUser().getGroupID());
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.setMainDataset(getTableData());
        return reportCriteria;
    }

}
