package com.inthinc.pro.reports.service;

import java.util.List;
import java.util.Locale;

import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

import com.inthinc.pro.model.DriverStopReport;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.ReportSchedule;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.reports.ReportCriteria;

public interface ReportCriteriaService
{
    ReportCriteria getTrendChartReportCriteria(Integer groupID, Duration duration, Locale locale, GroupHierarchy gh);
    ReportCriteria getOverallScoreReportCriteria(Integer groupID, Duration duration, Locale locale, GroupHierarchy gh);
    ReportCriteria getMpgReportCriteria(Integer groupID,Duration duration, Locale locale, GroupHierarchy gh);
    ReportCriteria getDriverReportCriteria(Integer groupID, Duration duration, Locale locale, Boolean initDataSet);
    ReportCriteria getVehicleReportCriteria(Integer groupID,Duration duration, Locale locale, Boolean initDataSet);
    ReportCriteria getIdlingReportCriteria(Integer groupID, Interval interval, Locale locale, Boolean initDataSet);
	ReportCriteria getIdlingVehicleReportCriteria(Integer groupID, Interval interval, Locale locale,Boolean initDataSet);
    ReportCriteria getDevicesReportCriteria(Integer groupID, Locale locale, Boolean initDataSet);
    ReportCriteria getEventsReportCriteria(Integer groupID, Locale locale);
    ReportCriteria getRedFlagsReportCriteria(Integer groupID, Locale locale);
    ReportCriteria getWarningsReportCriteria(Integer groupID, Locale locale);
    ReportCriteria getEmergencyReportCriteria(Integer groupID, Locale locale);
    ReportCriteria getZoneAlertsReportCriteria(Integer groupID, Locale locale);
    ReportCriteria getCrashHistoryReportCriteria(Integer groupID, Locale locale);
    ReportCriteria getSpeedPercentageReportCriteria(Integer groupID,Duration duration, Locale locale, GroupHierarchy gh);
    ReportCriteria getIdlePercentageReportCriteria(Integer groupID, Duration duration, Locale locale, GroupHierarchy gh);
    ReportCriteria getTeamStatisticsReportCriteria(Integer groupID, TimeFrame timeFrame, DateTimeZone timeZone, Locale locale, Boolean initDataSet, GroupHierarchy gh);
    ReportCriteria getTeamStopsReportCriteria(GroupHierarchy accountGroupHierarchy, Integer driverID, TimeFrame timeFrame, DateTimeZone timeZone, Locale locale, DriverStopReport driverStopReport);
    ReportCriteria getTeamStopsReportCriteriaByGroup(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, TimeFrame timeFrame, DateTimeZone timeZone, Locale locale);
    ReportCriteria getSeatbeltClicksReportCriteria(GroupHierarchy accountGroupHierarchy, Integer groupID, TimeFrame timeFrame, Locale locale,DateTimeZone timeZone);
    ReportCriteria getSeatbeltClicksReportCriteria(GroupHierarchy accountGroupHierarchy, Integer groupID, TimeFrame timeFrame, Locale locale, DateTimeZone timeZone, boolean includeInactiveDrivers, boolean includeZeroMilesDrivers);

    // DOT IFTA
    ReportCriteria getMileageByVehicleReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale, MeasurementType measurementType, boolean dotOnly);
    ReportCriteria getStateMileageByVehicleReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale, MeasurementType measurementType, boolean dotOnly);
    ReportCriteria getStateMileageByVehicleRoadStatusReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale, MeasurementType measurementType, boolean dotOnly);
	ReportCriteria getStateMileageFuelByVehicleReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale, MeasurementType measurementType, boolean dotOnly);
	ReportCriteria getStateMileageCompareByGroupReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale, MeasurementType measurementType, boolean isIfta);
    ReportCriteria getStateMileageByMonthReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale, MeasurementType measurementType, boolean dotOnly);

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

    ReportCriteria getDrivingTimeViolationsSummaryReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale);
    ReportCriteria getDrivingTimeViolationsDetailReportCriteria(GroupHierarchy accountGroupHierarchy, Integer driverID, Interval interval, Locale locale);
    ReportCriteria getDrivingTimeViolationsDetailReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale);
    ReportCriteria getNonDOTViolationsSummaryReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale);
    ReportCriteria getNonDOTViolationsDetailReportCriteria(GroupHierarchy accountGroupHierarchy, Integer driverID, Interval interval, Locale locale);
    ReportCriteria getNonDOTViolationsDetailReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale);

    
    // Performance
    ReportCriteria getPayrollDetailReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale);
    ReportCriteria getPayrollSignoffReportCriteria(GroupHierarchy accountGroupHierarchy, Integer driverID, Interval interval, Locale locale);
    ReportCriteria getPayrollSignoffReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale);
    ReportCriteria getPayrollSummaryReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale);
    ReportCriteria getPayrollCompensatedHoursReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale);
    ReportCriteria getTenHoursDayViolationsCriteria(GroupHierarchy accountGroupHierarchy, Integer groupID, Interval interval, Locale locale);
    ReportCriteria getDriverHoursReportCriteria(GroupHierarchy accountGroupHierarchy, Integer groupID, Interval interval, Locale locale);
    ReportCriteria getVehicleUsageReportCriteria(Integer id, Interval interval, Locale locale, boolean group);
    ReportCriteria getDriverPerformanceReportCriteria(GroupHierarchy accountGroupHierarchy, Integer groupID, Interval interval, Locale locale, Boolean ryg);
    List<ReportCriteria> getDriverPerformanceIndividualReportCriteria(GroupHierarchy accountGroupHierarchy, Integer groupID, List<Integer> driverID, Interval interval, Locale locale, Boolean ryg);
    ReportCriteria getDriverPerformanceKeyMetricsReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, TimeFrame timeFrame, Interval interval, Locale locale, MeasurementType measurementType);
    ReportCriteria getDriverPerformanceKeyMetricsReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, TimeFrame timeFrame, Interval interval, Locale locale, MeasurementType measurementType, boolean includeInactiveDrivers, boolean includeZeroMilesDrivers);
    ReportCriteria getDriverPerformanceKeyMetricsTimeFrameReportCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, TimeFrame timeFrame, Interval interval, Locale locale, MeasurementType measurementType);
    List<ReportCriteria> getDriverCoachingReportCriteriaByGroup(GroupHierarchy accountGroupHierarchy,Integer groupID,Interval interval,Locale locale,DateTimeZone timeZone);
    ReportCriteria getDriverCoachingReportCriteriaByDriver(GroupHierarchy accountGroupHierarchy,Integer driverID,Interval interval,Locale locale,DateTimeZone timeZone);
    ReportCriteria getDriverExcludedViolationCriteria(GroupHierarchy accountGroupHierarchy,Integer groupID,Interval interval,Locale locale,DateTimeZone timeZone);
    
    // Mileage
    ReportCriteria getStateMileageByVehicleReportCriteria(GroupHierarchy accountGroupHierarchy, Integer groupID, Interval interval, Locale locale, MeasurementType measurementType);
    
    //Communication
    ReportCriteria getNonCommReportCriteria(GroupHierarchy accountGroupHierarchy, Integer groupID, TimeFrame timeFrame, Locale locale,DateTimeZone timeZone);
    
    // Asset
    ReportCriteria getWarrantyListReportCriteria(GroupHierarchy accountGroupHierarchy, Integer groupID, Integer accountID, String accountName, Locale locale, boolean expiredOnly);

    // Forms
    ReportCriteria getDVIRPreTripReportCriteria(GroupHierarchy accountGroupHierarchy, Integer groupID, TimeFrame timeFrame, Locale locale, DateTimeZone timeZone);
    ReportCriteria getDVIRPostTripReportCriteria(GroupHierarchy accountGroupHierarchy, Integer groupID, TimeFrame timeFrame, Locale locale, DateTimeZone timeZone);
    // Prepare report criteria
    List<ReportCriteria> getReportCriteria(ReportSchedule reportSchedule, GroupHierarchy groupHierarchy, Person person);
 
}