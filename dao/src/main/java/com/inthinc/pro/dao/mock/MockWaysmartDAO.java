package com.inthinc.pro.dao.mock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.Interval;

import com.inthinc.pro.dao.report.WaysmartDAO;
import com.inthinc.pro.model.performance.DriverHoursRecord;
import com.inthinc.pro.model.performance.TenHoursViolationRecord;
import com.inthinc.pro.model.performance.VehicleUsageRecord;

public class MockWaysmartDAO implements WaysmartDAO {
     
    private Map<Integer, List<TenHoursViolationRecord>> tenHoursViolationMap; 
    private Map<Integer, List<DriverHoursRecord>> driverHoursMap;
    private Map<Integer, List<VehicleUsageRecord>> vehicleUsageMap;
    
    /** 
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.WaysmartDAO#getTenHoursViolations(java.lang.Integer, org.joda.time.Interval)
     */
    @Override
    public List<TenHoursViolationRecord> getTenHoursViolations(Integer driverID, Interval interval) {
        Calendar startDate = Calendar.getInstance();
        startDate.set(interval.getStart().getYearOfEra(), interval.getStart().getMonthOfYear()-1, 
                interval.getStart().getDayOfMonth()+1, 0, 0, 0);
        
        Calendar endDate = Calendar.getInstance();
        endDate.set(interval.getEnd().getYearOfEra(), interval.getEnd().getMonthOfYear()-1, 
                interval.getEnd().getDayOfMonth(), 0, 0, 0);
        
        List<TenHoursViolationRecord> filter = new ArrayList<TenHoursViolationRecord>(); 
        List<TenHoursViolationRecord> list = getTenHoursViolationData(driverID);
        if (list != null) {
            for (TenHoursViolationRecord rec : list) {
                if (rec.getDate().after(startDate.getTime())
                        && rec.getDate().before(endDate.getTime())) {
                    filter.add(rec);
                }
            }
        }
        return filter;
    }

    /** 
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.WaysmartDAO#getDriverHours(java.lang.Integer, org.joda.time.Interval)
     */
    @Override
    public List<DriverHoursRecord> getDriverHours(Integer driverID, Interval interval) {
        Calendar startDate = Calendar.getInstance();
        startDate.set(interval.getStart().getYearOfEra(), interval.getStart().getMonthOfYear()-1, 
                interval.getStart().getDayOfMonth()+1,0,0,0);
        
        Calendar endDate = Calendar.getInstance();
        endDate.set(interval.getEnd().getYearOfEra(), interval.getEnd().getMonthOfYear()-1, 
                interval.getEnd().getDayOfMonth(),0,0,0);

        List<DriverHoursRecord> filter = new ArrayList<DriverHoursRecord>();
        List<DriverHoursRecord> list = this.getDriverHoursData(driverID);
        if (list != null) {
            for (DriverHoursRecord rec : list) {
                if (rec.getDate().after(startDate.getTime())
                        && rec.getDate().before(endDate.getTime())) {
                    filter.add(rec);
                }
            }
        }
        return filter;
    }

    /** 
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.report.WaysmartDAO#getVehicleUsage(java.lang.Integer, org.joda.time.Interval)
     */
    @Override
    public List<VehicleUsageRecord> getVehicleUsage(Integer driverID, Interval interval) {
        Calendar startDate = Calendar.getInstance();
        startDate.set(interval.getStart().getYearOfEra(), interval.getStart().getMonthOfYear()-1, 
                interval.getStart().getDayOfMonth()+1,0,0,0);
        
        Calendar endDate = Calendar.getInstance();
        endDate.set(interval.getEnd().getYearOfEra(), interval.getEnd().getMonthOfYear()-1, 
                interval.getEnd().getDayOfMonth(),0,0,0);

        List<VehicleUsageRecord> filter = new ArrayList<VehicleUsageRecord>();
        List<VehicleUsageRecord> list = this.getVehicleUsageData(driverID);
        if (list != null) {
            for (VehicleUsageRecord rec : list) {
                if (rec.getDate().after(startDate.getTime())
                        && rec.getDate().before(endDate.getTime())) {
                    filter.add(rec);
                }
            }
        }
        return filter;
    }
    
    /* returns the mocked data set for TenHoursViolation report */
    private List<TenHoursViolationRecord> getTenHoursViolationData(Integer driverID) {
        if (tenHoursViolationMap == null) {
            // create 3 records
            tenHoursViolationMap = new HashMap<Integer, List<TenHoursViolationRecord>>();    
            tenHoursViolationMap.put(2905,this.createRecords(2905, 1));
            tenHoursViolationMap.put(2906,this.createRecords(2906, 2));
            tenHoursViolationMap.put(2907,this.createRecords(2907, 3));
            tenHoursViolationMap.put(2907,this.createRecords(2907, 4));
            tenHoursViolationMap.put(5555,this.createRecords(5555, 4));
            tenHoursViolationMap.put(5583,this.createRecords(5583, 1));
        }
        return this.tenHoursViolationMap.get(driverID);
    }
    
    /* create new records for TenHoursViolation report */
    private List<TenHoursViolationRecord> createRecords(Integer driverId, Integer id) {
        List<TenHoursViolationRecord> list = new ArrayList<TenHoursViolationRecord>();
        TenHoursViolationRecord rec = new TenHoursViolationRecord();
        Calendar c = Calendar.getInstance();
        c.set(2010, 8, id+1, 0, 0, 0);
        rec.setDate(c.getTime());
        rec.setVehicleID(11+id*3);
        rec.setHoursThisDay((10+id));
        rec.setDriverID(driverId);
        list.add(rec);
        return list;
    }
    
    /* returns the mocked data set for DriverHours report. */
    private List<DriverHoursRecord> getDriverHoursData(Integer driverID) {
        if (this.driverHoursMap == null) {
            driverHoursMap = new HashMap<Integer, List<DriverHoursRecord>>();
            driverHoursMap.put(2905, this.createList(2905, 4.4, 1.3, 1.4));
            driverHoursMap.put(2906, this.createList(2906, 3.1, 3.8, 1.4));
            driverHoursMap.put(2907, this.createList(2907, 0.0, 4.0, 1.6));
            driverHoursMap.put(5555, this.createList(5555, 2.0, 3.0, 4.0, 5.0));
            driverHoursMap.put(5583, this.createList(5583, 9.5, 0.0, 3.0, 6.0));
        }
        return this.driverHoursMap.get(driverID);
    }

    private List<DriverHoursRecord> createList(Integer driverId, Double... d) {
        List<DriverHoursRecord> list = new ArrayList<DriverHoursRecord>();
        
        for (int i = 0; i < d.length; i++) {
            DriverHoursRecord rec = new DriverHoursRecord();
            Calendar c = Calendar.getInstance();
            c.set(2010, 8, i+1, 0, 0, 0);
            rec.setDate(c.getTime());
            rec.setDriverID(driverId);
            rec.setHoursThisDay(d[i]);
            list.add(rec);
        }
        return list;
    }

    private List<VehicleUsageRecord> getVehicleUsageData(Integer driverID) {
        if (this.vehicleUsageMap == null) {
            vehicleUsageMap = new HashMap<Integer, List<VehicleUsageRecord>>();
            // TODO Add mock data
        }
        return this.vehicleUsageMap.get(driverID);
    }

}
