package com.inthinc.pro.dao.mock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.joda.time.Interval;

import com.inthinc.pro.dao.report.WaysmartDAO;
import com.inthinc.pro.model.hos.TenHoursViolationRecord;

public class MockWaysmartDAO implements WaysmartDAO {
    private static int counter; 
    public MockWaysmartDAO() {
        counter = 1;
    }

    @Override
    public List<TenHoursViolationRecord> getTenHoursViolations(Integer driverID, Interval queryInterval) {
        List<TenHoursViolationRecord> list = new ArrayList<TenHoursViolationRecord>();
        TenHoursViolationRecord rec = new TenHoursViolationRecord();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2010, 0, counter);
        rec.setDate(calendar.getTime());
        rec.setVehicleID(11+(counter-1)*3);
        rec.setHoursThisDay((12+(counter-1)*3));
        list.add(rec);
        counter++;
        return list;
    }

    public List<TenHoursViolationRecord> getTenHoursViolationMock(Integer driverID, Interval queryInterval) {
        List<TenHoursViolationRecord> list = new ArrayList<TenHoursViolationRecord>();
        int rnd = (int)Math.round(Math.random()*1.5);
        while (rnd-- > 0){
            TenHoursViolationRecord rec = new TenHoursViolationRecord();
            Calendar calendar = Calendar.getInstance();
            calendar.set(2010, 0, counter);
            rec.setDate(calendar.getTime());
            rec.setVehicleID(11+(counter-1)*3);
            rec.setHoursThisDay((12+(counter-1)*3));
            list.add(rec);
            counter++;
        }
        return list;
    }

}
