package com.inthinc.pro.reports.hos;

import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

import com.inthinc.hos.model.HOSRec;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.hos.model.ViolationsData;
import com.inthinc.hos.violations.DailyViolations;
import com.inthinc.hos.violations.ShiftViolations;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.model.GroupHierarchy;
import com.inthinc.pro.reports.hos.model.ViolationsDetail;
import com.inthinc.pro.reports.hos.model.ViolationsDetailRaw;

public class HosViolationsDetailReportCriteria extends ViolationsDetailReportCriteria {

    private static final Logger logger = Logger.getLogger(HosViolationsDetailReportCriteria.class);
    
    public HosViolationsDetailReportCriteria(Locale locale) 
    {
        super(ReportType.HOS_VIOLATIONS_DETAIL_REPORT, locale);
    }

    
    @Override
    protected void addDriverViolations(Interval interval, GroupHierarchy groupHierarchy, List<ViolationsDetailRaw> violationDetailList, 
            Driver driver, DateTimeZone driverTimeZone,
            RuleSetType driverDOTType, List<HOSRec> recListForViolationsCalc) {
        List<ViolationsData> dailyViolations = new DailyViolations().getDailyHosViolationsForReport(interval,
                driverTimeZone.toTimeZone(),
                driverDOTType, 
                recListForViolationsCalc);
        addViolations(violationDetailList, driver, dailyViolations, groupHierarchy);

        List<ViolationsData> shiftViolations = new ShiftViolations().getHosViolationsInTimeFrame(
                interval, driverTimeZone.toTimeZone(),
                driverDOTType, 
                null,  
                recListForViolationsCalc);
        addViolations(violationDetailList, driver, shiftViolations, groupHierarchy);
    }

}
