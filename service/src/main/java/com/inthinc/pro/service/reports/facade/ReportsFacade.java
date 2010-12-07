package com.inthinc.pro.service.reports.facade;

import java.util.List;

import org.joda.time.Interval;

import com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus;
import com.inthinc.pro.reports.performance.model.TenHoursViolation;

/**
 * Facade to ReportCriteriaService.
 */
public interface ReportsFacade {

    public static final int DAYS_BACK = 6;	
	
    public List<TenHoursViolation> getTenHourViolations(Integer groupID, Interval interval);
    
    public List<StateMileageByVehicleRoadStatus> getStateMileageByVehicleRoadStatus(Integer groupID, Interval interval, boolean dotOnly);

}
