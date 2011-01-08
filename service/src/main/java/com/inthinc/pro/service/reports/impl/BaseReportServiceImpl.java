package com.inthinc.pro.service.reports.impl;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.Interval;

import com.inthinc.pro.service.reports.facade.ReportsFacade;

/**
 * Base class with common utility for Report services.
 */
public class BaseReportServiceImpl {

    public static String DATE_FORMAT = "yyyyMMdd";
    static final Integer DAYS_BACK = -6;

    protected ReportsFacade reportsFacade;

    public BaseReportServiceImpl(ReportsFacade reportsFacade) {
        this.reportsFacade = reportsFacade;
    }

    public ReportsFacade getReportsFacade() {
        return reportsFacade;
    }
    
    /**
     * Create an Interval from the Date range.
     * If the Dates are null the default one is created.
     * @return the Date set to default days back
     */
    public Interval getInterval(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            Calendar start = getMidnight();
            start.add(Calendar.DAY_OF_YEAR, DAYS_BACK);
            startDate = start.getTime();
            endDate = getMidnight().getTime();
        }        
        return new Interval(startDate.getTime(), endDate.getTime());
    }
    
    private Calendar getMidnight() {
        Calendar date = Calendar.getInstance();
        // set time to midnight
       date.set(Calendar.HOUR_OF_DAY, 0);
       date.set(Calendar.MINUTE, 0);
       date.set(Calendar.SECOND, 0);
       date.set(Calendar.MILLISECOND, 0);
       return date;
    }

}