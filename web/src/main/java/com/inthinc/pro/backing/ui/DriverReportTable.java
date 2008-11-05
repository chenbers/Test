package com.inthinc.pro.backing.ui;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.richfaces.component.html.HtmlDataTable;
import org.richfaces.component.html.HtmlDatascroller;

public class DriverReportTable
{
    private static final Logger logger = Logger.getLogger(TrendTable.class);

    private HtmlDataTable table = new HtmlDataTable();
    private HtmlDatascroller driverReportScroller;
    
    public void actionListener(ActionEvent event) {
        HtmlDataTable table = getTable();
        
        if (table != null)
        {
            Object row = table.getRowKey();
            if (row instanceof Integer)
            {               

            } else 
            {
                logger.debug("no valid row found in table");
            }                       
        }
    }
    
    public HtmlDataTable getTable()
    {
        return table;
    }

    public void setTable(HtmlDataTable table)
    {
        this.table = table;
    }
    
    
    public HtmlDatascroller getDriverReportScroller()
    {
        return driverReportScroller;
    }
    
    public void setDriverReportScroller(HtmlDatascroller driverReportScroller)
    {
//      this.tiwiScroller = tiwiScroller;
    }   
    
    public void trendChangeListener(ValueChangeEvent event)
    {
//      tiwiScroller.setPage("first");
    }
}
