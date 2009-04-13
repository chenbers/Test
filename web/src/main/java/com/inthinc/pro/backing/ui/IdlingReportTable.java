package com.inthinc.pro.backing.ui;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.richfaces.component.html.HtmlDataTable;
import org.richfaces.component.html.HtmlDatascroller;

public class IdlingReportTable
{
    private static final Logger logger = Logger.getLogger(IdlingReportTable.class);

    private HtmlDataTable table = new HtmlDataTable();
    private HtmlDatascroller idlingReportScroller;

    public void actionListener(ActionEvent event)
    {
        HtmlDataTable table = getTable();
        logger.debug("idlingreporttable");
        if (table != null)
        {
            Object row = table.getRowKey();
            if (row instanceof Integer)
            {

            }
            else
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

    public HtmlDatascroller getIdlingReportScroller()
    {
        return idlingReportScroller;
    }

    public void setIdlingReportScroller(HtmlDatascroller idlingReportScroller)
    {
        this.idlingReportScroller = idlingReportScroller;
    }

    public void idlingReportChangeListener(ValueChangeEvent event)
    {

    }
}
