package com.inthinc.pro.service.reports.impl;

import com.inthinc.pro.service.reports.facade.ReportsFacade;
import com.inthinc.pro.util.ReportsUtil;

/**
 * Base class with common utility for IFTA report services.
 */
public class BaseIFTAServiceImpl {

    public static String DATE_FORMAT = "yyyyMMdd";
    
    protected static final Integer DAYS_BACK = -6;

    protected ReportsFacade reportsFacade;
    protected ReportsUtil reportsUtil;

    public BaseIFTAServiceImpl(ReportsFacade reportsFacade, ReportsUtil reportsUtil) {
        this.reportsFacade = reportsFacade;
        this.reportsUtil = reportsUtil;
    }

    public ReportsUtil getReportsUtil() {
        return reportsUtil;
    }

    public ReportsFacade getReportsFacade() {
        return reportsFacade;
    }
}