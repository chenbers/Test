package com.inthinc.pro.model.comparator;

import java.util.Comparator;

import com.inthinc.pro.model.IdlingReportItem;

public class IdlingReportItemComparator implements Comparator<IdlingReportItem>
{
    @Override
    public int compare(IdlingReportItem item1, IdlingReportItem item2)
    {
        return item1.getDriver().getPerson().getFullName().toLowerCase().compareTo(item2.getDriver().getPerson().getFullName().toLowerCase());
    }
}
