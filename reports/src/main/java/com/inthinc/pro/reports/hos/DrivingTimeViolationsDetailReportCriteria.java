package com.inthinc.pro.reports.hos;

import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

import com.inthinc.hos.model.HOSRec;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.hos.model.RuleViolationTypes;
import com.inthinc.hos.model.ViolationsData;
import com.inthinc.hos.violations.ShiftViolations;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.model.GroupHierarchy;
import com.inthinc.pro.reports.hos.model.ViolationsDetailRaw;

public class DrivingTimeViolationsDetailReportCriteria extends ViolationsDetailReportCriteria {
    private static final Logger logger = Logger.getLogger(DrivingTimeViolationsDetailReportCriteria.class);

    public DrivingTimeViolationsDetailReportCriteria(Locale locale) {
        super(ReportType.DRIVING_TIME_VIOLATIONS_DETAIL_REPORT, locale);
    }

    @Override
    protected void addDriverViolations(Interval interval, GroupHierarchy groupHierarchy, List<ViolationsDetailRaw> violationDetailList, Driver driver, DateTimeZone driverTimeZone,
            RuleSetType driverDOTType, List<HOSRec> recListForViolationsCalc) {
        List<ViolationsData> shiftViolations = new ShiftViolations().getHosViolationsInTimeFrame(interval, driverTimeZone.toTimeZone(), driverDOTType, RuleSetType.SLB_INTERNAL,
                recListForViolationsCalc);
        addViolations(violationDetailList, driver, shiftViolations, groupHierarchy);
    }

    @Override
    protected boolean includeViolation(RuleViolationTypes type, Long minutes)
    {
        if (type == RuleViolationTypes.CUMMULATIVE_HOURS ||
            type == RuleViolationTypes.CUMMULATIVE_HOURS_14_DAYS ||
            type == RuleViolationTypes.CUMMULATIVE_HOURS_7_DAYS ||
            type == RuleViolationTypes.CUMMULATIVE_HOURS_8_DAYS)
                                    return false;
        return super.includeViolation(type, minutes);
        
    }
}
