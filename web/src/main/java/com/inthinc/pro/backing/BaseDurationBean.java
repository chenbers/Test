package com.inthinc.pro.backing;

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

}
