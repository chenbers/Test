package com.inthinc.pro.reports.hos;

import java.util.List;
import java.util.Locale;

import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

import com.inthinc.hos.model.HOSRec;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.hos.model.RuleViolationTypes;
import com.inthinc.hos.model.ViolationsData;
import com.inthinc.hos.violations.ShiftViolations;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.model.ViolationsDetailRaw;

public class DrivingTimeViolationsDetailReportCriteria extends ViolationsDetailReportCriteria {

    public DrivingTimeViolationsDetailReportCriteria(Locale locale) {
        super(ReportType.DRIVING_TIME_VIOLATIONS_DETAIL_REPORT, locale);
        this.setIncludeInactiveDrivers(ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS);
        this.setIncludeZeroMilesDrivers(ReportCriteria.DEFAULT_INCLUDE_ZERO_MILES_DRIVERS);
    }

    @Override
    protected void addDriverViolations(Interval interval, GroupHierarchy accountGroupHierarchy, List<ViolationsDetailRaw> violationDetailList, Driver driver, DateTimeZone driverTimeZone,
            RuleSetType driverDOTType, List<HOSRec> recListForViolationsCalc) {
        List<ViolationsData> shiftViolations = new ShiftViolations().getHosViolationsInTimeFrame(interval, driverTimeZone.toTimeZone(), driverDOTType, RuleSetType.SLB_INTERNAL,
                recListForViolationsCalc);
        addViolations(violationDetailList, driver, shiftViolations, accountGroupHierarchy);
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
