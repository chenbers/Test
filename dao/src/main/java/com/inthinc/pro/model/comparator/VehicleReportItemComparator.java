package com.inthinc.pro.model.comparator;

import java.util.Comparator;

import com.inthinc.pro.model.VehicleReportItem;

public class VehicleReportItemComparator implements Comparator<VehicleReportItem>
{
    @Override
    public int compare(VehicleReportItem item1, VehicleReportItem item2)
    {
        return item1.getVehicle().getName().toLowerCase().compareTo(item2.getVehicle().getName().toLowerCase());
    }
}
