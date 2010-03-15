package com.inthinc.pro.reports.service;

import java.util.Locale;

import org.joda.time.Interval;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.reports.ReportCriteria;

public interface ReportCriteriaService
{
    ReportCriteria getTrendChartReportCriteria(Integer groupID, Duration duration, Locale locale);
    ReportCriteria getOverallScoreReportCriteria(Integer groupID, Duration duration, Locale locale);
    ReportCriteria getMpgReportCriteria(Integer groupID,Duration duration, Locale locale);
    ReportCriteria getDriverReportCriteria(Integer groupID,Locale locale);
    ReportCriteria getVehicleReportCriteria(Integer groupID,Locale locale);
//    ReportCriteria getIdlingReportCriteria(Integer groupID,Date startDate,Date endDate, Locale locale);
    ReportCriteria getIdlingReportCriteria(Integer groupID, Interval interval, Locale locale);
    ReportCriteria getDevicesReportCriteria(Integer groupID, Locale locale);
    ReportCriteria getEventsReportCriteria(Integer groupID, Locale locale);
    ReportCriteria getRedFlagsReportCriteria(Integer groupID, Locale locale);
    ReportCriteria getWarningsReportCriteria(Integer groupID, Locale locale);
    ReportCriteria getEmergencyReportCriteria(Integer groupID, Locale locale);
    ReportCriteria getZoneAlertsReportCriteria(Integer groupID, Locale locale);
    ReportCriteria getCrashHistoryReportCriteria(Integer groupID, Locale locale);
    ReportCriteria getSpeedPercentageReportCriteria(Integer groupID,Duration duration, Locale locale);
    ReportCriteria getIdlePercentageReportCriteria(Integer groupID, Duration duration, Locale locale);

}
