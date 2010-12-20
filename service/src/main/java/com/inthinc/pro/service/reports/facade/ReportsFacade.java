package com.inthinc.pro.service.reports.facade;

import java.util.List;
import java.util.Locale;

import org.joda.time.Interval;

import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.reports.ifta.model.MileageByVehicle;
import com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus;
import com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup;
import com.inthinc.pro.reports.ifta.model.StateMileageFuelByVehicle;
import com.inthinc.pro.reports.performance.model.TenHoursViolation;

/**
 * Facade to ReportCriteriaService.
 */
public interface ReportsFacade {

    List<TenHoursViolation> getTenHourViolations(Integer groupID, Interval interval);
   
    @Deprecated
    //to delete
    List<StateMileageByVehicleRoadStatus> getStateMileageByVehicleRoadStatus(Integer groupID, Interval interval, boolean dotOnly);
    
    List<StateMileageByVehicleRoadStatus> getStateMileageByVehicleRoadStatus(List<Integer> groupIDList, Interval interval, boolean dotOnly);
    
    /**
     * @deprecated Use {@link ReportsFacade#getMileageByVehicle(List, Interval, boolean, Locale, MeasurementType)} instead.
     */
    @Deprecated
    List<MileageByVehicle> getMileageByVehicle(Integer groupID, Interval interval, boolean dotOnly, Locale locale, MeasurementType type);
    List<MileageByVehicle> getMileageByVehicle(List<Integer> idList, Interval interval, boolean dotOnly, Locale locale, MeasurementType type);

    @Deprecated
    //to delete
    List<StateMileageCompareByGroup> getStateMileageGroupComparison(Integer groupID, Interval interval, boolean dotOnly, Locale locale, MeasurementType type);

    List<StateMileageCompareByGroup> getStateMileageByVehicleStateComparison(List<Integer> groupList, Interval interval, boolean dotOnly, Locale locale, MeasurementType type);
    
    List<MileageByVehicle> getStateMileageByVehicle(Integer groupID, Interval interval, boolean dotOnly, Locale locale, MeasurementType type);

    @Deprecated
    //to delete
    List<MileageByVehicle> getStateMileageByVehicleByMonth(Integer groupID, Interval interval, boolean dotOnly, Locale locale, MeasurementType type);
    
    List<MileageByVehicle> getStateMileageByVehicleByMonth(List<Integer> groupIDList, Interval interval, boolean dotOnly, Locale locale, MeasurementType type);
    
    List<StateMileageFuelByVehicle> getStateMileageFuelByVehicle(List<Integer> groupIDList, Interval interval, boolean dotOnly, Locale locale, MeasurementType measurementType);
}
