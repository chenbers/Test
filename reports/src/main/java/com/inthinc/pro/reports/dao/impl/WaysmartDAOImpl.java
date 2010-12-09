package com.inthinc.pro.reports.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

import com.inthinc.hos.adjusted.HOSAdjustedList;
import com.inthinc.hos.model.HOSRecAdjusted;
import com.inthinc.hos.model.HOSStatus;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.dao.util.HOSUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.assets.AssetWarrantyRecord;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.model.performance.DriverHoursRecord;
import com.inthinc.pro.model.performance.TenHoursViolationRecord;
import com.inthinc.pro.model.performance.VehicleUsageRecord;
import com.inthinc.pro.reports.dao.WaysmartDAO;
import com.inthinc.pro.reports.dao.mock.MockWaysmartDAO;
import com.inthinc.pro.reports.util.DateTimeUtil;

public class WaysmartDAOImpl implements WaysmartDAO {

    private HOSDAO hosDAO;
    private static final long TEN_HOURS_IN_MINUTES = 600l;    
    
    @Override
    public List<DriverHoursRecord> getDriverHours(Driver driver, Interval queryInterval) {
        DateTimeZone driverTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
        Interval expandedInterval = DateTimeUtil.getExpandedInterval(queryInterval, driverTimeZone, 1, 1); 

        List<HOSRecord> hosRecordList = hosDAO.getHOSRecords(driver.getDriverID(), expandedInterval, true);
        return getDriverHoursList(driver, queryInterval, driverTimeZone, hosRecordList);
    }

    private List<DriverHoursRecord> getDriverHoursList(Driver driver, Interval queryInterval, DateTimeZone driverTimeZone, List<HOSRecord> hosRecordList) {
        HOSAdjustedList adjustedList = HOSUtil.getAdjustedListFromLogList(hosRecordList);

        List<DateTime> dayList = DateTimeUtil.getDayList(queryInterval, driverTimeZone);
        
        List<DriverHoursRecord> driverHoursRecordList = new ArrayList<DriverHoursRecord>();
        
        
        DateTime currentTime = new DateTime();
        for (DateTime day : dayList) {
            List<HOSRecAdjusted> logListForDay = adjustedList.getAdjustedListForDay(day.toDate(), currentTime.toDate(), true);
            Long drivingMinutes = 0l;
            for (HOSRecAdjusted hosRec : logListForDay) {
                if (hosRec.getStatus() == HOSStatus.DRIVING)
                    drivingMinutes += hosRec.getTotalRealMinutes();
            }
            
            DriverHoursRecord driverHoursRecord = new DriverHoursRecord();
            driverHoursRecord.setDay(day);
            driverHoursRecord.setDriverID(driver.getDriverID());
            driverHoursRecord.setHoursThisDay(drivingMinutes.doubleValue()/60.0);
            
            driverHoursRecordList.add(driverHoursRecord);
        }
        
        return driverHoursRecordList;
    }
    
    // entry point for unit testing
    public List<DriverHoursRecord> getDriverHours(Driver driver, Interval queryInterval, List<HOSRecord> hosRecordList) {
        DateTimeZone driverTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
        return getDriverHoursList(driver, queryInterval, driverTimeZone, hosRecordList);
    }

    @Override
    public List<TenHoursViolationRecord> getTenHoursViolations(Driver driver, Interval queryInterval) {
        DateTimeZone driverTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
        Interval expandedInterval = DateTimeUtil.getExpandedInterval(queryInterval, driverTimeZone, 1, 1); 
        List<HOSRecord> hosRecordList = hosDAO.getHOSRecords(driver.getDriverID(), expandedInterval, true);

        return getTenHourViolationsList(driver, queryInterval, driverTimeZone, expandedInterval, hosRecordList, TEN_HOURS_IN_MINUTES);
    }

    // entry point for unit testing
    public List<TenHoursViolationRecord> getTenHoursViolations(Driver driver, Interval queryInterval, List<HOSRecord> hosRecordList, long violationMinutesLevel) {
        DateTimeZone driverTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
        Interval expandedInterval = DateTimeUtil.getExpandedInterval(queryInterval, driverTimeZone, 1, 1); 

        return getTenHourViolationsList(driver, queryInterval, driverTimeZone, expandedInterval, hosRecordList, violationMinutesLevel);
    }

    private List<TenHoursViolationRecord> getTenHourViolationsList(Driver driver, Interval queryInterval, 
                    DateTimeZone driverTimeZone, Interval expandedInterval, List<HOSRecord> hosRecordList, long violationMinutesLevel) {
        HOSAdjustedList adjustedList = HOSUtil.getAdjustedListFromLogList(hosRecordList);

        List<DateTime> dayList = DateTimeUtil.getDayList(queryInterval, driverTimeZone);
        
        List<TenHoursViolationRecord> tenHoursViolationRecordList = new ArrayList<TenHoursViolationRecord>();
        
        DateTime currentTime = new DateTime();
        for (DateTime day : dayList) {

            List<HOSRecAdjusted> logListForDay = adjustedList.getAdjustedListForDay(day.toDate(), currentTime.toDate(), true);
            Long drivingMinutes = 0l;
            Integer vehicleID = null;
            String vehicleName = null;
            for (HOSRecAdjusted hosRec : logListForDay) {
                if (hosRec.getStatus() == HOSStatus.DRIVING) {
                    drivingMinutes += hosRec.getTotalRealMinutes();
                    vehicleID = hosRec.getVehicleID();
                    if (vehicleID != null)
                        vehicleName = findVehicleName(hosRecordList, vehicleID);
                }
            }
            
            if (drivingMinutes > violationMinutesLevel) {
                TenHoursViolationRecord tenHoursViolationRecord = new TenHoursViolationRecord();
                tenHoursViolationRecord.setDateTime(day);
                tenHoursViolationRecord.setDriverID(driver.getDriverID());
                tenHoursViolationRecord.setHoursThisDay(drivingMinutes/60.0);
                tenHoursViolationRecord.setVehicleID(vehicleID);
                tenHoursViolationRecord.setVehicleName(vehicleName);
                tenHoursViolationRecordList.add(tenHoursViolationRecord);
            }
        }
        
        return tenHoursViolationRecordList;
    }

    private String findVehicleName(List<HOSRecord> hosRecordList, Integer vehicleID) {
        for (HOSRecord hosRecord : hosRecordList)
            if (hosRecord.getVehicleID() != null && hosRecord.getVehicleID().equals(vehicleID))
                return hosRecord.getVehicleName();
        return null;
    }


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
