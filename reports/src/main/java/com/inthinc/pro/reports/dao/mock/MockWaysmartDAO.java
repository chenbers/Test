package com.inthinc.pro.reports.dao.mock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.assets.AssetWarrantyRecord;
import com.inthinc.pro.model.performance.DriverHoursRecord;
import com.inthinc.pro.model.performance.TenHoursViolationRecord;
import com.inthinc.pro.model.performance.VehicleUsageRecord;
import com.inthinc.pro.reports.dao.WaysmartDAO;

public class MockWaysmartDAO implements WaysmartDAO {
     
    private Map<Integer, List<TenHoursViolationRecord>> tenHoursViolationMap; 
    private Map<Integer, List<DriverHoursRecord>> driverHoursMap;
    private Map<Integer, List<VehicleUsageRecord>> vehicleUsageMap;
    private Map<Integer, List<AssetWarrantyRecord>> warrantyMap;
    
    /** 
     * {@inheritDoc}
     * @see com.inthinc.pro.reports.dao.WaysmartDAO#getTenHoursViolations(java.lang.Integer, org.joda.time.Interval)
     */
    /** 
     * {@inheritDoc}
     * @see com.inthinc.pro.reports.dao.WaysmartDAO#getVehicleUsage(java.lang.Integer, org.joda.time.Interval)
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

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.reports.dao.WaysmartDAO#getWarrantyList(java.lang.Integer, boolean)
     */
    @Override
    public List<AssetWarrantyRecord> getWarrantyList(Integer groupID, boolean expiredOnly) {
 
        List<AssetWarrantyRecord> list = new ArrayList<AssetWarrantyRecord>();
        
        if( 1505 == groupID){
            if(!expiredOnly){
                list.add(this.createWarranty("1505","11538072", "300034013838130", createDate(2010,9,2,0,0), createDate(2012,9,2,0,0), false));
                list.add(this.createWarranty("1505","10867408", "300034012081910", createDate(2009,10,1,0,0), createDate(2011,10,1,0,0), false));
            }
            list.add(this.createWarranty("1505","10996110", "300034012408260", createDate(2008,9,17,0,0), createDate(2010,9,17,0,0), true));
        }
        
        if( 1506 == groupID){
            if(!expiredOnly)
                list.add(this.createWarranty("1506","11495882", null, createDate(2010,7,23,0,0), createDate(2012,7,23,0,0), false));
            list.add(this.createWarranty("1506","11074882", "300034012673250", createDate(2008,2,12,0,0), createDate(2010,2,12,0,0), true));
        }
        
        return list;
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
        rec.setDateTime(new DateTime(createDate(2010, 8, id+1, 0, 0)));
        rec.setVehicleID(11+id*3);
        rec.setVehicleName("Stub Vehicle");
        rec.setHoursThisDay((10+id));
        rec.setDriverID(driverId);
        
        list.add(rec);
        return list;
    }
    
    /* returns the mocked data set for DriverHours report. */
    private List<DriverHoursRecord> getDriverHoursData(Integer driverID) {
        if (this.driverHoursMap == null) {
            driverHoursMap = new HashMap<Integer, List<DriverHoursRecord>>();
            driverHoursMap.put(2905, this.createDriverHoursList(2905, 4.4, 1.3, 1.4));
            driverHoursMap.put(2906, this.createDriverHoursList(2906, 3.1, 3.8, 1.4));
            driverHoursMap.put(2907, this.createDriverHoursList(2907, 0.0, 4.0, 1.6));
            driverHoursMap.put(5555, this.createDriverHoursList(5555, 2.0, 3.0, 4.0, 5.0));
            driverHoursMap.put(5583, this.createDriverHoursList(5583, 9.5, 0.0, 3.0, 6.0));
        }
        return this.driverHoursMap.get(driverID);
    }

    private List<DriverHoursRecord> createDriverHoursList(Integer driverId, Double... d) {
        List<DriverHoursRecord> list = new ArrayList<DriverHoursRecord>();
        
        for (int i = 0; i < d.length; i++) {
            DriverHoursRecord rec = new DriverHoursRecord();
            rec.setDay(new DateTime(createDate(2010, 8, i+1, 0, 0)));
            rec.setDriverID(driverId);
            rec.setHoursThisDay(d[i]);
            list.add(rec);
        }
        return list;
    }

    private List<VehicleUsageRecord> getVehicleUsageData(Integer driverID) {
        if (this.vehicleUsageMap == null) {
            vehicleUsageMap = new HashMap<Integer, List<VehicleUsageRecord>>();
            List<VehicleUsageRecord> driver1 = new ArrayList<VehicleUsageRecord>();
            driver1.add(new VehicleUsageRecord("2906", "100", createDate(2010, 1, 1, 0, 0), 
                    "Zone1", null, createDate(2010, 1, 1, 2, 0), 5000, null, null, null, null));
            driver1.add(new VehicleUsageRecord("2906", "100", createDate(2010, 1, 1, 0, 0) , 
                    "Zone2", createDate(2010, 1, 1, 2, 30), createDate(2010, 1, 1, 2, 30), 5009, 9, 2, 3, 4));
            driver1.add(new VehicleUsageRecord("2906", "100", createDate(2010, 1, 1, 0, 0) , 
                    "Zone3", createDate(2010, 1, 1, 2, 40), createDate(2010, 1, 1, 2, 40), 5027, 18, 5, 6, 7));

            List<VehicleUsageRecord> driver2 = new ArrayList<VehicleUsageRecord>();
            driver2.add(new VehicleUsageRecord("2905", "100", createDate(2010, 1, 1, 0, 0), 
                    "Zone4", null, createDate(2010, 1, 1, 5, 0), 5027, null, null, null, null));
            driver2.add(new VehicleUsageRecord("2905", "100", createDate(2010, 1, 1, 0, 0) , 
                    "Zone5", createDate(2010, 1, 1, 6, 0), createDate(2010, 1, 1, 6, 0), 5087, 60, 10, 20, 30));
            vehicleUsageMap.put(2906, driver1);
            vehicleUsageMap.put(2905, driver2);
        }
        return this.vehicleUsageMap.get(driverID);
    }

    // just create a date
    private Date createDate(int year, int month, int day, int hour, int min) {
        Calendar c = Calendar.getInstance();
        c.set(year, month-1, day, hour, min, 0);
        return c.getTime();
    }
    
    /* Creates a AssetWarrantyRecord bean */
    private AssetWarrantyRecord createWarranty(String group, String vehicle, String imei, Date started, Date ended, boolean expired) {
        AssetWarrantyRecord rec = new AssetWarrantyRecord();
        rec.setGroupId(group);
        rec.setVehicleName(vehicle);
        rec.setImei(imei);
        rec.setStartDate(started);
        rec.setEndDate(ended);
        rec.setExpired(expired);
        return rec;
    }
}
