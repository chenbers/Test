package com.inthinc.pro.service.reports.impl;

import com.inthinc.pro.service.reports.facade.ReportsFacade;

/**
 * Base class with common utility for Report services.
 */
public class BaseReportServiceImpl {

    protected ReportsFacade reportsFacade;

    public BaseReportServiceImpl(ReportsFacade reportsFacade) {
        this.reportsFacade = reportsFacade;
    }

    public ReportsFacade getReportsFacade() {
        return reportsFacade;
    }
    
}