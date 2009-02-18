package com.inthinc.pro.backing;

import org.richfaces.event.DataScrollerEvent;

/*
 * Properties to manage table stats. example: "Showing 1 to 10 of 83 records."
 */
public class TableStatsBean
{
    private Integer tableSize     = 0;
    private Integer tableRowCount = 0;
    private Integer tableRowStart;
    private Integer tableRowEnd   = 0;

    public Integer getTableRowStart()
    {
        if(tableRowStart == null && tableSize > 0)
            tableRowStart = 1;
        else if(tableRowStart == null && tableSize == 0)
            tableRowStart = 0;

        return tableRowStart;
    }

    public void setTableRowStart(Integer tableRowStart)
    {
        this.tableRowStart = tableRowStart;
    }

    public Integer getTableRowEnd()
    {
        tableRowEnd = tableRowEnd == 0 ? tableRowCount : tableRowEnd;

        if (tableSize < tableRowCount)
            tableRowEnd = tableSize;

        return tableRowEnd;
    }

    public void setTableRowEnd(Integer tableRowEnd)
    {
        this.tableRowEnd = tableRowEnd;
    }

    public Integer getTableRowCount()
    {
        if (tableRowEnd == null)
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
        if(tableSize > 0)
            tableRowStart = 1;
        
        this.tableSize = tableSize;
    }

    public void scrollerListener(DataScrollerEvent se)
    {
        tableRowStart = se.getPage() * tableRowCount - (tableRowCount - 1);
        tableRowEnd = se.getPage() * tableRowCount;

        if (tableRowEnd > getTableSize())
            tableRowEnd = getTableSize();
    }

}
