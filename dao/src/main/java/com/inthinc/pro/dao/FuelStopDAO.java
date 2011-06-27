package com.inthinc.pro.dao;

import java.util.List;

import org.joda.time.Interval;

import com.inthinc.pro.model.hos.FuelStopRecord;

public interface FuelStopDAO extends GenericDAO<FuelStopRecord, Long> {
    List<FuelStopRecord> getFuelStopRecords(Integer vehicleID, Interval interval);
}
