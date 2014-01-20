package com.inthinc.pro.hos;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.LocalDate;

import com.inthinc.hos.model.RuleSetType;
import com.inthinc.hos.rules.RuleSetFactory;
import com.inthinc.hos.util.DateUtil;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.hos.HOSRecord;

public class HOSBase {
    
    private HOSDAO hosDAO;
    
    private static final Logger logger = Logger.getLogger(HOSBase.class);

    public HOSBase(HOSDAO hosDAO) {
        this.hosDAO = hosDAO;
    }
    
    protected List<HOSRecord> fetchHosRecordList(DateTime currentDate, Driver driver) {
        RuleSetType driverRuleSetType = driver.getDot();
        if (driverRuleSetType == null || driverRuleSetType == RuleSetType.NON_DOT)
            return null;
        
        DateTimeZone driverDateTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
        Interval interval = getDaysBackInterval(currentDate, driverDateTimeZone, RuleSetFactory.getDaysBackForRuleSetType(driverRuleSetType));
        logger.debug("fetchHosRecordList interval: " + DateUtil.getDisplayInterval(interval, driverDateTimeZone) + " driverID: " + driver.getDriverID());
        List<HOSRecord> hosRecordList = hosDAO.getHOSRecords(driver.getDriverID(), interval, true);
        Collections.sort(hosRecordList);
        
        return hosRecordList;
    }
    
    protected Interval getDaysBackInterval(DateTime endDateTime, DateTimeZone dateTimeZone, int daysBack)
    {
        LocalDate localDate = new LocalDate(new DateMidnight(endDateTime, dateTimeZone));
        DateTime startDate = localDate.toDateTimeAtStartOfDay(dateTimeZone).minusDays(daysBack);

        return new Interval(startDate, endDateTime.plusSeconds(15));

    }


    public HOSDAO getHosDAO() {
        return hosDAO;
    }

    public void setHosDAO(HOSDAO hosDAO) {
        this.hosDAO = hosDAO;
    }

}
