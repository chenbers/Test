package com.inthinc.pro.service.reports.impl;

import java.util.Date;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.inthinc.pro.service.reports.facade.ReportsFacade;

/**
 * Base class with common utility for Report services.
 */
public class BaseReportServiceImpl {

    public static String DATE_FORMAT = "yyyyMMdd";
    static final Integer DAYS_BACK = 6;

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
            startDate = getMidnight(DAYS_BACK);
            endDate = getMidnight(0);
        }
        
        return new Interval(startDate.getTime(), getEndOfDay(endDate).getTime());
    }
    
    private Date getMidnight(int daysBack) {
       DateMidnight date = new DateMidnight(); 
       date.minusDays(daysBack);
       return date.toDate();
    }

    private Date getEndOfDay(Date date) {
        return new DateTime(new DateMidnight(date).plusDays(1)).minus(1).toDate();
    }
}