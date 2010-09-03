package com.inthinc.pro.backing.model;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

import com.inthinc.pro.util.MessageUtil;

public class ReportParams {
    Date startDate;
    Date endDate;
    String badDates;
    Integer groupID;
    Integer driverID;
    Locale locale;
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
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
        try {
            setBadDates(null);
            if (startDate == null) {
                setBadDates(MessageUtil.getMessageString("noStartDate",getLocale()));
                return null;
            }
            if (this.endDate == null) {
                setBadDates(MessageUtil.getMessageString("noEndDate",getLocale()));
                return null;
            }

            DateMidnight start = new DateMidnight(new DateTime(startDate.getTime(), dateTimeZone), dateTimeZone);
            DateTime end = new DateMidnight(endDate.getTime(), dateTimeZone).toDateTime().plusDays(1).minus(1);
            return new Interval(start, end);
        }
        catch (Exception e) {
            setBadDates(MessageUtil.getMessageString("endDateBeforeStartDate",getLocale()));
            return null;
        }
    }

    public String getBadDates() {
        return badDates;
    }
    public void setBadDates(String badDates) {
        this.badDates = badDates;
    }


}
