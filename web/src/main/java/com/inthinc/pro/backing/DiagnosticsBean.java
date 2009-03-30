package com.inthinc.pro.backing;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.reports.ReportCriteria;

public class DiagnosticsBean extends BaseEventsBean
{
    private static final Logger logger = Logger.getLogger(DiagnosticsBean.class);

    @Override
    protected List<Event> getEventsForGroup(Integer groupID)
    {
        return getEventDAO().getWarningEventsForGroup(groupID, 7);
    }

    // TablePrefOptions interface

    @Override
    public TableType getTableType()
    {
        return TableType.DIAGNOSTICS;
    }

    public String showAllFromRecentAction()
    {
        // setSearchText(null);
        setCategoryFilter(null);
        setEventFilter(null);

        refreshAction();
        return "go_diagnostics";
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
        ReportCriteria reportCriteria = getReportCriteriaService().getWarningsReportCriteria(getUser().getGroupID());
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.setMainDataset(getTableData());
        return reportCriteria;
    }

}
