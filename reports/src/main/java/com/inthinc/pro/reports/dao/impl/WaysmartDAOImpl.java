package com.inthinc.pro.reports.dao.impl;

import java.util.List;

import org.joda.time.Interval;

import com.inthinc.pro.dao.DriveTimeDAO;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.model.assets.AssetWarrantyRecord;
import com.inthinc.pro.model.performance.VehicleUsageRecord;
import com.inthinc.pro.reports.dao.WaysmartDAO;
import com.inthinc.pro.reports.dao.mock.MockWaysmartDAO;

public class WaysmartDAOImpl implements WaysmartDAO {

    private HOSDAO hosDAO;
    private static final long TEN_HOURS_IN_MINUTES = 600l;    
    
    private DriveTimeDAO driveTimeDAO;
    @Override
    public List<VehicleUsageRecord> getVehicleUsage(Integer driverID, Interval interval) {
        // TODO To be implemented
        return null;
    }

    public HOSDAO getHosDAO() {
        return hosDAO;
    }

    public void setHosDAO(HOSDAO hosDAO) {
        this.hosDAO = hosDAO;
    }

    public DriveTimeDAO getDriveTimeDAO() {
        return driveTimeDAO;
    }

    public void setDriveTimeDAO(DriveTimeDAO driveTimeDAO) {
        this.driveTimeDAO = driveTimeDAO;
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.reports.dao.WaysmartDAO#getWarrantyList(java.lang.Integer, boolean)
     */
    @Override
    public List<AssetWarrantyRecord> getWarrantyList(Integer groupID, boolean expiredOnly) {   
        // TODO To be implemented
        return new MockWaysmartDAO().getWarrantyList(groupID, expiredOnly);
    }


}
