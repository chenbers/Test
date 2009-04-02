package com.inthinc.pro.reports.service;

import java.util.Date;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.reports.ReportCriteria;

public interface ReportCriteriaService
{
    ReportCriteria getTrendChartReportCriteria(Integer groupID, Duration duration);
    ReportCriteria getOverallScoreReportCriteria(Integer groupID, Duration duration);
    ReportCriteria getMpgReportCriteria(Integer groupID,Duration duration);
    ReportCriteria getDriverReportCriteria(Integer groupID,Duration duration);
    ReportCriteria getVehicleReportCriteria(Integer groupID,Duration duration);
    ReportCriteria getIdlingReportCriteria(Integer groupID,Date startDate,Date endDate);
    ReportCriteria getDevicesReportCriteria(Integer groupID);
    ReportCriteria getEventsReportCriteria(Integer groupID);
    ReportCriteria getRedFlagsReportCriteria(Integer groupID);
    ReportCriteria getWarningsReportCriteria(Integer groupID);
    ReportCriteria getEmergencyReportCriteria(Integer groupID);
}
