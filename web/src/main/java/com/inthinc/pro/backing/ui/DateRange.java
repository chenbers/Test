package com.inthinc.pro.backing.ui;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

import com.inthinc.pro.util.MessageUtil;

public class DateRange implements Cloneable {
    Date startDate;
    Date endDate;
    Interval interval;
    String badDates;
    Locale locale;
    TimeZone timeZone;

    private static final TimeZone defaultTimeZone = TimeZone.getTimeZone("GMT");
    
    public DateRange(Locale locale, TimeZone timeZone, Date startDate, Date endDate) {
        this.locale = locale;
        this.timeZone = timeZone;
        this.startDate = startDate;
        this.endDate = endDate;
        updateInterval();
    }

    
    public DateRange(Locale locale) {
        this(locale, defaultTimeZone);

        
    }
    public DateRange(Locale locale, TimeZone timeZone) {
        this.locale = locale;
        this.timeZone = timeZone;
        DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(getTimeZone());
        setStartDate(new DateMidnight(new DateTime().minusWeeks(1), dateTimeZone).toDate());
        setEndDate(new DateMidnight(new DateTime(), dateTimeZone).toDateTime().plusDays(1).minus(1).toDate());
        updateInterval();
    }

    
    public TimeZone getTimeZone() {
        if (timeZone == null)
            return defaultTimeZone;
        return timeZone;
    }
    public Locale getLocale() {
        return locale;
    }
    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
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
    public Interval getInterval() {
        return interval;
    }
    public void setInterval(Interval interval) {
        this.interval = interval;
    }
    public String getBadDates() {
        return badDates;
    }
    public void setBadDates(String badDates) {
        this.badDates = badDates;
    }
    public boolean isMoreThanOneYear(){
        DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(getTimeZone());
        DateTime start = new DateTime(startDate.getTime(), dateTimeZone);
        DateTime end = new DateMidnight(endDate.getTime(), dateTimeZone).toDateTime().plusDays(1).minus(1);
        
        return end.minusYears(1).isAfter(start.getMillis());
    }

    public void updateInterval()
    {
        try {
            setBadDates(null);
            setInterval(null);
            if (startDate == null) {
                setBadDates(MessageUtil.getMessageString("dateRange_noStartDate",getLocale()));
                return;
            }
            if (this.endDate == null) {
                setBadDates(MessageUtil.getMessageString("dateRange_noEndDate",getLocale()));
                return;
            }

            DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(getTimeZone());
            DateMidnight start = new DateMidnight(new DateTime(startDate.getTime(), dateTimeZone), dateTimeZone);
            DateTime end = new DateMidnight(endDate.getTime(), dateTimeZone).toDateTime().plusDays(1).minus(1);
            interval =  new Interval(start, end);
            
        }
        catch (Exception e) {
            setBadDates(MessageUtil.getMessageString("dateRange_endDateBeforeStartDate",getLocale()));
            setInterval(null);
        }
    }
    public DateRange clone() 
    {
        try {
            return (DateRange)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
        
    }

}
