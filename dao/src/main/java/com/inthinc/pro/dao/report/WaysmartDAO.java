package com.inthinc.pro.dao.report;

import java.util.List;

import org.joda.time.Interval;

import com.inthinc.pro.model.hos.TenHoursViolationRecord;

public interface WaysmartDAO {

    List<TenHoursViolationRecord> getTenHoursViolations(Integer driverID, Interval queryInterval);
    
}
