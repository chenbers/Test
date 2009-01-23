package com.inthinc.pro.backing;

import java.util.Calendar;
import java.util.Date;

import org.richfaces.event.DataScrollerEvent;

import com.inthinc.pro.model.Duration;

public class BaseDurationBean extends BaseBean
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
    
    /*
     * Properties to manage table stats.
     * example: "Showing 1 to 10 of 83 records."
     */
    public Integer getTableRowStart()
    {
        tableRowStart = tableRowStart == null ? 1 : tableRowStart;
        
        return tableRowStart;
    }

    public void setTableRowStart(Integer tableRowStart)
    {
        this.tableRowStart = tableRowStart;
    }

    public Integer getTableRowEnd()
    {
        tableRowEnd = tableRowEnd == null ? tableRowCount : tableRowEnd;
        
        if(tableSize < tableRowCount)
            tableRowEnd = tableSize;
        
        return tableRowEnd;
    }

    public void setTableRowEnd(Integer tableRowEnd)
    {
        this.tableRowEnd = tableRowEnd;
    }

    public Integer getTableRowCount()
    {
        if(tableRowEnd == null)
            tableRowEnd = tableRowCount;
        
        return tableRowCount;
    }

    public void setTableRowCount(Integer tableRowCount)
    {
        this.tableRowCount = tableRowCount;
    }

    public Integer getTableSize()
    {
        return tableSize;
    }

    public void setTableSize(Integer tableSize)
    {
        this.tableSize = tableSize;
    }

    public void scrollerListener(DataScrollerEvent se)
    {
        tableRowStart = se.getPage() * tableRowCount - (tableRowCount - 1);
        tableRowEnd = se.getPage() * tableRowCount;   
        
        if(tableRowEnd > getTableSize())
            tableRowEnd = getTableSize(); 
    }
}
