package com.inthinc.pro.backing.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.reports.ReportType;

public class JsfFunctions
{
    private static final Logger logger = Logger.getLogger(JsfFunctions.class);
    
    //Not used... Just added this so that it is here.
    public static ReportType getReportType(String name)
    {
        logger.debug("Report Type: " + name);
        return ReportType.toReportType(name);
    }
    
    public static List<ReportType> getReportTypes()
    {
        List<ReportType> reportTypeList = new ArrayList<ReportType>();
        reportTypeList.add(ReportType.OVERALL_SCORE);
        reportTypeList.add(ReportType.TREND);
        reportTypeList.add(ReportType.MPG_GROUP);
        return reportTypeList;
    }

}
