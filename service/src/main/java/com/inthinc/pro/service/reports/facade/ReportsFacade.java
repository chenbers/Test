package com.inthinc.pro.service.reports.facade;

import java.util.List;

import org.joda.time.Interval;

import com.inthinc.pro.reports.ifta.model.MileageByVehicle;
import com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus;
import com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup;
import com.inthinc.pro.reports.performance.model.TenHoursViolation;

/**
 * Facade to ReportCriteriaService.
 */
public interface ReportsFacade {

    int DAYS_BACK = 6;	

    List<TenHoursViolation> getTenHourViolations(Integer groupID, Interval interval);

    List<StateMileageByVehicleRoadStatus> getStateMileageByVehicleRoadStatus(Integer groupID, Interval interval, boolean dotOnly);

    List<MileageByVehicle> getMileageByVehicleReportCriteria(Integer groupID, Interval interval, boolean dotOnly);

    List<StateMileageCompareByGroup> getStateMileageByVehicleStateComparaison(Integer groupID, Interval interval, boolean dotOnly);
}
