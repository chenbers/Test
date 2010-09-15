package com.inthinc.pro.backing.model;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

import com.inthinc.pro.util.MessageUtil;

public class ReportParams implements Cloneable {
    Date startDate;
    Date endDate;
    Interval interval;
    String badDates;
    Integer groupID;
    Integer driverID;
    Locale locale;
    Boolean valid;

    // TODO: Figure out timezone 
    private static final TimeZone timeZone = TimeZone.getTimeZone("GMT");
    private static final DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(timeZone);
    
    
    public Locale getLocale() {
        return locale;
    }
    public void setLocale(Locale locale) {
        this.locale = locale;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
     //   initInterval();
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
     //   initInterval();
    }
    public Integer getGroupID() {
        return groupID;
    }
    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }
    public Integer getDriverID() {
        return driverID;
    }
    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
    }
    
    public Interval getInterval() {
        return interval;
    }
    public void setInterval(Interval interval) {
        this.interval = interval;
    }
    public void updateInterval()
    {
        try {
            setBadDates(null);
            if (startDate == null) {
                setBadDates(MessageUtil.getMessageString("noStartDate",getLocale()));
                interval = null;
            }
            if (this.endDate == null) {
                setBadDates(MessageUtil.getMessageString("noEndDate",getLocale()));
                interval = null;
            }

            DateMidnight start = new DateMidnight(new DateTime(startDate.getTime(), dateTimeZone), dateTimeZone);
            DateTime end = new DateMidnight(endDate.getTime(), dateTimeZone).toDateTime().plusDays(1).minus(1);
            interval =  new Interval(start, end);
        }
        catch (Exception e) {
            setBadDates(MessageUtil.getMessageString("endDateBeforeStartDate",getLocale()));
            interval = null;
        }
    }
    public Boolean getValid() {
System.out.println("getValid() " + (badDates == null));        
        return (badDates == null);
    }
    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getBadDates() {
        return badDates;
    }
    public void setBadDates(String badDates) {
        this.badDates = badDates;
    }
    public String getErrorMsg() {
        return badDates;
    }
    public ReportParams clone() 
    {
        try {
            return (ReportParams)super.clone();
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        
    }

}
