package com.inthinc.pro.dao.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.joda.time.Interval;

import com.inthinc.pro.dao.FuelStopDAO;
import com.inthinc.pro.model.hos.FuelStopRecord;

public class FuelStopMockDAO implements FuelStopDAO {
    //Mock for dev
    Map<Long,FuelStopRecord> fuelStops;
    
    public FuelStopMockDAO() {
        super();
        fuelStops = new HashMap<Long,FuelStopRecord>();
        //Mock for now
//        Long fuelStopID, Integer driverID, Integer noteID, Integer vehicleID, String vehicleName, Boolean vehicleIsDOT, Long odometerBefore, 
        //Long odometerAfter, Date dateTime, TimeZone timeZone, String location,
//        Float lat, Float lng, String trailerID, Boolean edited, Integer editUserID, String editUserName, 
        //Boolean deleted, Integer changedCnt, Float truckGallons, Float trailerGallons,
//        Date dateLastUpdated
        FuelStopRecord fuelStop = new FuelStopRecord(1l, 2, 3, 2,
                "Shooting Star", true, 100L, 105L, new Date(), TimeZone.getDefault(), "Salt Lake City",
                new Float(57.0000),new Float(-111.0000), "trailer4", false, 5,"testUser", 
                false,  0, new Float(10.0), new Float(12.0), new Date());
        fuelStops.put(1L,fuelStop);
        fuelStop = new FuelStopRecord(2l, 3,4,2,
                "Shooting Star", true, 100L, 105L, new Date(), TimeZone.getDefault(), "Salt Lake City",
                new Float(57.0000),new Float(-111.0000), "trailer5", false, 5,"testUser", 
                false,  0, new Float(10.0), new Float(12.0), new Date());
        fuelStops.put(2L,fuelStop);
    }

    @Override
    public FuelStopRecord findByID(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long create(Long id, FuelStopRecord entity) {
        fuelStops.put(id, entity);
        return id;
    }

    @Override
    public Integer update(FuelStopRecord entity) {
        fuelStops.put(entity.getFuelStopID(), entity);
        return 1;
    }

    @Override
    public Integer deleteByID(Long id) {
        fuelStops.remove(id);
        return 1;
    }

    @Override
    public List<FuelStopRecord> getFuelStopRecords(Integer vehicleID, Interval interval) {
        return new ArrayList<FuelStopRecord>(fuelStops.values());
    }

}
