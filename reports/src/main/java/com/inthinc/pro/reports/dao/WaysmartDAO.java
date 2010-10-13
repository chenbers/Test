package com.inthinc.pro.reports.dao;

import java.util.List;

import org.joda.time.Interval;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.performance.DriverHoursRecord;
import com.inthinc.pro.model.performance.TenHoursViolationRecord;
import com.inthinc.pro.model.performance.VehicleUsageRecord;

public interface WaysmartDAO {

    /**
     * Returns Driver violations of the Ten Hours per Day rule.
     * @param driverID the driver ID
     * @param queryInterval the period 
     * @return a list of TenHoursViolationRecord for the specified driver and period
     */
    List<TenHoursViolationRecord> getTenHoursViolations(Driver driver, Interval queryInterval);
    /**
     * Returns the Driver hours per day.
     * @param driverID the driver ID
     * @param queryInterval the period
     * @return a list of DriverHoursRecord for the specified driver and period
     */
    List<DriverHoursRecord> getDriverHours(Driver driver, Interval queryInterval);
    
    List<VehicleUsageRecord> getVehicleUsage(Integer driverID, Interval interval);
    
}
