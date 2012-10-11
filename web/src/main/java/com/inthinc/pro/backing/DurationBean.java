package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.inthinc.pro.backing.listener.DurationChangeListener;
import com.inthinc.pro.model.Duration;

public class DurationBean
{
    private Duration            duration            = Duration.DAYS;
    private String              styleClass30Days;
    private String              styleClass3Months;
    private String              styleClass6Months;
    private String              styleClass12Months;
    private final static String ON                  = "on";
    private final static String OFF                 = "";
    private Integer             tableSize           = 0;
    private Integer             tableRowCount;
    private Integer             tableRowStart       = 1;
    private Integer             tableRowEnd;
    
    private List<DurationChangeListener> durationChangeListenerList = new ArrayList<DurationChangeListener>();

    public DurationBean()
    {

    }

    public Duration getDuration()
    {
        return duration;
        }

    public void setDuration(Duration duration)
    {
        this.duration = duration;
        for(DurationChangeListener durationChangeListener: durationChangeListenerList)
        {
            durationChangeListener.onDurationChange(duration);
        }
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

    public void setDurationChangeListenerList(List<DurationChangeListener> durationChangeListenerList)
    {
        this.durationChangeListenerList = durationChangeListenerList;
    }

    public List<DurationChangeListener> getDurationChangeListenerList()
    {
        return durationChangeListenerList;
    }
    
    public void addDurationChangeListener(DurationChangeListener durationChangeListener)
    {
        if(durationChangeListenerList == null)
        {
            durationChangeListenerList = new ArrayList<DurationChangeListener>();
        }
        durationChangeListenerList.add(durationChangeListener);
        
    }
    
}
