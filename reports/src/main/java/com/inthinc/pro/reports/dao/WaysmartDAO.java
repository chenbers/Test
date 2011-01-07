package com.inthinc.pro.reports.dao;

import java.util.List;

import org.joda.time.Interval;

import com.inthinc.pro.model.assets.AssetWarrantyRecord;
import com.inthinc.pro.model.performance.VehicleUsageRecord;

public interface WaysmartDAO {

    
    List<VehicleUsageRecord> getVehicleUsage(Integer driverID, Interval interval);
    
    /**
     * Returns the Warranty List records for specified group. 
     * @param groupID The group ID selected by user. 
     * @param expired The flag to filter for expired only
     * @return a list of Warranty List records
     */
    List<AssetWarrantyRecord> getWarrantyList(Integer groupID, boolean expiredOnly);
    
}
