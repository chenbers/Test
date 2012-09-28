package com.inthinc.pro.hos;

import java.util.Collections;
import java.util.List;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.LocalDate;

import com.inthinc.hos.model.RuleSetType;
import com.inthinc.hos.rules.RuleSetFactory;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.hos.HOSRecord;

public class HOSBase {
    
    private HOSDAO hosDAO;
    
    public HOSBase(HOSDAO hosDAO) {
        this.hosDAO = hosDAO;
    }
    
    protected List<HOSRecord> fetchHosRecordList(DateTime currentDate, Driver driver) {
        RuleSetType driverRuleSetType = driver.getDot();
        if (driverRuleSetType == null || driverRuleSetType == RuleSetType.NON_DOT)
            return null;
        
        Interval interval = getDaysBackInterval(currentDate, DateTimeZone.forTimeZone(driver.getPerson().getTimeZone()), RuleSetFactory.getDaysBackForRuleSetType(driverRuleSetType)); 
        List<HOSRecord> hosRecordList = hosDAO.getHOSRecords(driver.getDriverID(), interval, true);
        Collections.sort(hosRecordList);
        
        return hosRecordList;
        
    }
    private Interval getDaysBackInterval(DateTime endDateTime, DateTimeZone dateTimeZone, int daysBack)
    {
        LocalDate localDate = new LocalDate(new DateMidnight(endDateTime, dateTimeZone));
        DateTime startDate = localDate.toDateTimeAtStartOfDay(dateTimeZone).minusDays(daysBack);

        return new Interval(startDate, endDateTime);

    }


    public HOSDAO getHosDAO() {
        return hosDAO;
    }

    public void setHosDAO(HOSDAO hosDAO) {
        this.hosDAO = hosDAO;
    }

}
