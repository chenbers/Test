package com.inthinc.pro.reports.service;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.reports.ReportCriteria;

public interface ReportCriteriaService
{
    ReportCriteria getTrendChartReportCriteria(Integer groupID, Duration duration);
    ReportCriteria getOverallScoreReportCriteria(Integer groupID, Duration duration);
    ReportCriteria getMpgReportCriteria(Integer groupID,Duration duration);
    ReportCriteria getDriverReportCriteria(Integer group);
    ReportCriteria getVehicleReportCriteria(Integer group);
}
