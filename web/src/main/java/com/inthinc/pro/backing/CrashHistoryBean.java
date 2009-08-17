package com.inthinc.pro.backing;

import java.util.Date;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.TableType;

import com.inthinc.pro.reports.ReportCriteria;

public class CrashHistoryBean extends BaseCrashBean 
{
    private static final Logger logger = Logger.getLogger(CrashHistoryBean.class);    

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
