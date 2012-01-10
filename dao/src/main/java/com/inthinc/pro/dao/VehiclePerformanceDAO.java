package com.inthinc.pro.dao;

import java.util.List;

import org.joda.time.Interval;

import com.inthinc.pro.model.aggregation.VehiclePerformance;

public interface VehiclePerformanceDAO {
    List<VehiclePerformance> getVehiclePerformance(Integer driverID, Interval interval);
}
