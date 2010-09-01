package com.inthinc.pro.reports.service;

import java.util.List;
import java.util.Locale;

import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.reports.ReportCriteria;

public interface ReportCriteriaService
{
    ReportCriteria getTrendChartReportCriteria(Integer groupID, Duration duration, Locale locale);
    ReportCriteria getOverallScoreReportCriteria(Integer groupID, Duration duration, Locale locale);
    ReportCriteria getMpgReportCriteria(Integer groupID,Duration duration, Locale locale);
    ReportCriteria getDriverReportCriteria(Integer groupID, Duration duration, Locale locale, Boolean initDataSet);
    ReportCriteria getVehicleReportCriteria(Integer groupID,Duration duration, Locale locale, Boolean initDataSet);
    ReportCriteria getIdlingReportCriteria(Integer groupID, Interval interval, Locale locale, Boolean initDataSet);
    ReportCriteria getDevicesReportCriteria(Integer groupID, Locale locale, Boolean initDataSet);
    ReportCriteria getEventsReportCriteria(Integer groupID, Locale locale);
    ReportCriteria getRedFlagsReportCriteria(Integer groupID, Locale locale);
    ReportCriteria getWarningsReportCriteria(Integer groupID, Locale locale);
    ReportCriteria getEmergencyReportCriteria(Integer groupID, Locale locale);
    ReportCriteria getZoneAlertsReportCriteria(Integer groupID, Locale locale);
    ReportCriteria getCrashHistoryReportCriteria(Integer groupID, Locale locale);
    ReportCriteria getSpeedPercentageReportCriteria(Integer groupID,Duration duration, Locale locale);
    ReportCriteria getIdlePercentageReportCriteria(Integer groupID, Duration duration, Locale locale);
    ReportCriteria getTeamStatisticsReportCriteria(Integer groupID, TimeFrame timeFrame, DateTimeZone timeZone, Locale locale, Boolean initDataSet);
    ReportCriteria getTeamStopsReportCriteria(Integer driverID, TimeFrame timeFrame, DateTimeZone timeZone, Locale locale, Boolean initDataSet);
    
    
    // HOS
    List<ReportCriteria> getHosDailyDriverLogReportCriteria(Integer driverID, Interval interval, Locale locale, Boolean defaultUseMetric);
    ReportCriteria getHosViolationsSummaryReportCriteria(Integer groupID, Interval interval, Locale locale);
    ReportCriteria getHosViolationsDetailReportCriteria(Integer groupID, Interval interval, Locale locale);
    ReportCriteria getHosDriverDOTLogReportCriteria(Integer groupID, Interval interval, Locale locale);
    ReportCriteria getDotHoursRemainingReportCriteria(Integer groupID, Locale locale);
    ReportCriteria getHosZeroMilesReportCriteria(Integer groupID, Interval interval, Locale locale);
    ReportCriteria getPayrollDetailReportCriteria(Integer groupID, Interval interval, Locale locale);
    ReportCriteria getPayrollSignoffReportCriteria(Integer driverID, Interval interval, Locale locale);

}
