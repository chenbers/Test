package com.inthinc.pro.backing.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.reports.Report;

public class JsfFunctions
{
    private static final Logger logger = Logger.getLogger(JsfFunctions.class);
    
    //Not used... Just added this so that it is here.
    public static Report getReportType(String name)
    {
        logger.debug("Report Type: " + name);
        return Report.toReport(name);
    }
    
    public static List<Report> getReportTypes()
    {
        List<Report> reportTypeList = new ArrayList<Report>();
        reportTypeList.add(Report.OVERALL_SCORE);
        reportTypeList.add(Report.TREND);
        reportTypeList.add(Report.MPG_GROUP);
        return reportTypeList;
    }

}
