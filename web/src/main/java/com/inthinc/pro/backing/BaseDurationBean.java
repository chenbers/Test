package com.inthinc.pro.backing;

import java.util.Calendar;
import java.util.Date;

import com.inthinc.pro.model.Duration;

public class BaseDurationBean extends BaseBean
{
    private Duration            duration           = Duration.DAYS;
    private String              styleClass30Days;
    private String              styleClass3Months;
    private String              styleClass6Months;
    private String              styleClass12Months;
    private final static String ON                 = "on";
    private final static String OFF                 = "";

    public BaseDurationBean()
    {

    }

    public Duration getDuration()
    {
        return duration;
    }

    public void setDuration(Duration duration)
    {
        this.duration = duration;
    }

    public String getStyleClass30Days()
    {
        return (duration.equals(Duration.DAYS)) ? ON : OFF;
    }

    public String getStyleClass3Months()
    {
        return (duration.equals(Duration.THREE)) ? ON : OFF;
    }

    public String getStyleClass6Months()
    {
        return (duration.equals(Duration.SIX)) ? ON : OFF;
    }

    public String getStyleClass12Months()
    {
        return (duration.equals(Duration.TWELVE)) ? ON : OFF;
    }

    public Date getStartDate()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, duration.getNumberOfDays() * -1);
        return calendar.getTime();
    }
    
    public Date getEndDate()
    {
        return new Date();
    }
}
