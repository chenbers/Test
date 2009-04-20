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
    private Integer Page = 1;

    public void reset(Integer rowCount, Integer size)
    {
        this.setPage(size > 0 ? 1 : 0);
        this.setTableRowCount(rowCount);
        this.setTableSize(size);
    }
    
    public void updateSize(Integer size)
    {
        this.tableSize = size;
        
        if(tableRowEnd > size)
            tableRowEnd = size;
        
        if(tableRowStart > size)
            tableRowStart = size;
    }
    
    public Integer getTableRowStart()
    {
    	if(tableRowStart == null){
    		
    		tableRowStart = tableSize > 0?1:0;
    	}

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
        if(tableSize > 0 && Page <= 1 )
        {
            tableRowStart = 1;
            tableRowEnd = tableRowCount;
           
        }
        else if (tableSize < 1)
        {
            Page = 0;
            tableRowStart = 0;
            tableRowEnd = tableRowCount;
       	
        }
        this.tableSize = tableSize;
    }

    public void scrollerListener(DataScrollerEvent se)
    {
        tableRowStart = se.getPage() * tableRowCount - (tableRowCount - 1);
        tableRowEnd = se.getPage() * tableRowCount;

        if (tableRowEnd > getTableSize())
            tableRowEnd = getTableSize();
    }

    public Integer getPage()
    {
        return Page;
    }

    public void setPage(Integer page)
    {
        Page = page;
    }
    


}
