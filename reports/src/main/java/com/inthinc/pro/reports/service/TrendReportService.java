package com.inthinc.pro.reports.service;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.reports.ReportCriteria;

public interface TrendReportService
{
    ReportCriteria getReportCriteria(Integer groupID, Duration duration);
}
