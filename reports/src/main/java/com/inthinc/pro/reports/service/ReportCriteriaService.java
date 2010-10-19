package com.inthinc.pro.reports.service;

import java.util.List;
import java.util.Locale;

import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.MeasurementType;
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
    
    // DOT IFTA
    ReportCriteria getMileageByVehicleReportCriteria(List<Integer> groupIDList, Interval interval, Locale locale, MeasurementType measurementType, boolean dotOnly);
    ReportCriteria getStateMileageByVehicleReportCriteria(List<Integer> groupIDList, Interval interval, Locale locale, MeasurementType measurementType, boolean dotOnly);
    ReportCriteria getStateMileageByVehicleRoadStatusReportCriteria(List<Integer> groupIDList, Interval interval, Locale locale, boolean dotOnly);
	ReportCriteria getStateMileageFuelByVehicleReportCriteria(List<Integer> groupIDList, Interval interval, Locale locale, boolean dotOnly);

    // HOS
    List<ReportCriteria> getHosDailyDriverLogReportCriteria(GroupHierarchy accountGroupHierarchy, Integer driverID, Interval interval, Locale locale, Boolean defaultUseMetric);
    List<ReportCriteria> getHosDailyDriverLogReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale, Boolean defaultUseMetric);
    ReportCriteria getHosViolationsSummaryReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale);
    ReportCriteria getHosViolationsDetailReportCriteria(GroupHierarchy accountGroupHierarchy, Integer driverID, Interval interval, Locale locale);
    ReportCriteria getHosViolationsDetailReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale);
    ReportCriteria getHosDriverDOTLogReportCriteria(Integer driverID, Interval interval, Locale locale);
    ReportCriteria getDotHoursRemainingReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Locale locale);
    ReportCriteria getHosZeroMilesReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale);
    ReportCriteria getHosEditsReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale);

    // Performance
//    ReportCriteria getPayrollDetailReportCriteria(Integer groupID, Interval interval, Locale locale);
    ReportCriteria getPayrollDetailReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale);
    ReportCriteria getPayrollSignoffReportCriteria(GroupHierarchy accountGroupHierarchy, Integer driverID, Interval interval, Locale locale);
    ReportCriteria getPayrollSignoffReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale);
//    ReportCriteria getPayrollSummaryReportCriteria(Integer groupID, Interval interval, Locale locale);
    ReportCriteria getPayrollSummaryReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale);
    ReportCriteria getTenHoursDayViolationsCriteria(Integer groupID, Interval interval, Locale locale);
    ReportCriteria getDriverHoursReportCriteria(Integer groupID, Interval interval, Locale locale);
    ReportCriteria getVehicleUsageReportCriteria(Integer id, Interval interval, Locale locale, boolean group);
    
}
