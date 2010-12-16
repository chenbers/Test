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

    int DAYS_BACK = 6;

    List<TenHoursViolation> getTenHourViolations(Integer groupID, Interval interval);

    List<StateMileageByVehicleRoadStatus> getStateMileageByVehicleRoadStatus(List<Integer> groupIDList, Interval interval, boolean dotOnly);

    @Deprecated
    List<StateMileageByVehicleRoadStatus> getStateMileageByVehicleRoadStatus(Integer groupID, Interval interval, boolean dotOnly);

    List<MileageByVehicle> getMileageByVehicle(Integer groupID, Interval interval, boolean dotOnly, Locale locale, MeasurementType type);

    List<StateMileageCompareByGroup> getStateMileageByVehicleStateComparison(Integer groupID, Interval interval, boolean dotOnly);

    List<MileageByVehicle> getStateMileageByVehicle(Integer groupID, Interval interval, boolean dotOnly);

    List<MileageByVehicle> getStateMileageByVehicleByMonth(Integer groupID, Interval interval, boolean dotOnly);

    List<StateMileageFuelByVehicle> getStateMileageFuelByVehicle(List<Integer> groupIDList, Interval interval, boolean dotOnly);
}
