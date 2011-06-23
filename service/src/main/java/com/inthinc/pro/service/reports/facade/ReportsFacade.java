package com.inthinc.pro.service.reports.facade;

import java.util.List;
import java.util.Locale;

import org.joda.time.Interval;

import com.inthinc.hos.model.ViolationsData;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.hos.model.DotHoursRemaining;
import com.inthinc.pro.reports.hos.model.DriverDOTLog;
import com.inthinc.pro.reports.hos.model.DrivingTimeViolationsSummary;
import com.inthinc.pro.reports.hos.model.HosViolationsSummary;
import com.inthinc.pro.reports.hos.model.HosZeroMiles;
import com.inthinc.pro.reports.hos.model.NonDOTViolationsSummary;
import com.inthinc.pro.reports.hos.model.ViolationsDetail;
import com.inthinc.pro.reports.hos.model.ViolationsDetailRaw;
import com.inthinc.pro.reports.ifta.model.MileageByVehicle;
import com.inthinc.pro.reports.ifta.model.StateMileageByMonth;
import com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus;
import com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup;
import com.inthinc.pro.reports.ifta.model.StateMileageFuelByVehicle;
import com.inthinc.pro.reports.performance.model.DriverHours;
import com.inthinc.pro.reports.performance.model.TenHoursViolation;

/**
 * Facade to ReportCriteriaService.
 */
public interface ReportsFacade {

    List<TenHoursViolation> getTenHourViolations(Integer groupID, Interval interval, Locale locale);

    List<DriverHours> getDriverHours(Integer groupID, Interval interval, Locale locale);
   
    List<StateMileageByVehicleRoadStatus> getStateMileageByVehicleRoadStatus(List<Integer> groupIDList, Interval interval, boolean dotOnly, Locale locale, MeasurementType type);
    
    List<MileageByVehicle> getMileageByVehicle(List<Integer> idList, Interval interval, boolean dotOnly, Locale locale, MeasurementType type);

    List<StateMileageCompareByGroup> getStateMileageGroupComparison(List<Integer> groupList, Interval interval, boolean dotOnly, Locale locale, MeasurementType type);
    
    List<MileageByVehicle> getStateMileageByVehicle(List<Integer> groupIDList, Interval interval, boolean dotOnly, Locale locale, MeasurementType type);

    List<StateMileageByMonth> getStateMileageByMonth(List<Integer> groupIDList, Interval interval, boolean dotOnly, Locale locale, MeasurementType type);
    
    List<StateMileageFuelByVehicle> getStateMileageFuelByVehicle(List<Integer> groupIDList, Interval interval, boolean dotOnly, Locale locale, MeasurementType measurementType);
    
    //HOS
    
    List<DotHoursRemaining> getDotHoursRemaining(List<Integer> groupIDList, Locale locale);
    
    List<ViolationsData> getDrivingTimeViolationsDetail(Integer driverID, Interval interval, Locale locale);
    List<ViolationsData> getDrivingTimeViolationsDetail( List<Integer> groupIDList, Interval interval, Locale locale);
    
    List<DrivingTimeViolationsSummary> getDrivingTimeViolationsSummary( List<Integer> groupIDList, Interval interval, Locale locale);
    
//    List<HosDailyDriverLog> getHosDailyDriverLog(Integer driverID, Interval interval, Locale locale, MeasurementType type);
//    List<HosDailyDriverLog> getHosDailyDriverLog(List<Integer> groupIDList, Interval interval, Locale locale, MeasurementType type);
    
    List<DriverDOTLog> getHosDriverDOTLog(Integer driverID, Interval interval, Locale locale);
    
    List<HOSRecord> getHosEdits(List<Integer> groupIDList, Interval interval, Locale locale);
    
    List<ViolationsDetail> getHosViolationsDetail(Integer driverID, Interval interval, Locale locale);
    List<ViolationsDetail> getHosViolationsDetail(List<Integer> groupIDList, Interval interval, Locale locale);
    
    List<HosViolationsSummary> getHosViolationsSummary(List<Integer> groupIDList, Interval interval, Locale locale);
    
    List<HosZeroMiles> getHosZeroMiles(List<Integer> groupIDList, Interval interval, Locale locale);
    
    List<ViolationsDetailRaw> getNonDOTViolationsDetail(Integer driverID, Interval interval, Locale locale);
    List<ViolationsDetailRaw> getNonDOTViolationsDetail(List<Integer> groupIDList, Interval interval, Locale locale);
    
    List<NonDOTViolationsSummary> getNonDOTViolationsSummary(List<Integer> groupIDList, Interval interval, Locale locale);
}
