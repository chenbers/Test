package com.inthinc.pro.model.comparator;

import java.util.Comparator;

import com.inthinc.pro.model.DriverReportItem;

public class DriverReportItemComparator implements Comparator<DriverReportItem>
{
    @Override
    public int compare(DriverReportItem item1, DriverReportItem item2)
    {
        return item1.getEmployee().toLowerCase().compareTo(item2.getEmployee().toLowerCase());
    }
}
