package com.inthinc.pro.reports.hos;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

import com.inthinc.hos.model.HOSRec;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.hos.model.ViolationsData;
import com.inthinc.hos.violations.DailyViolations;
import com.inthinc.hos.violations.ShiftViolations;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.converter.Converter;
import com.inthinc.pro.reports.hos.model.GroupHierarchy;
import com.inthinc.pro.reports.hos.model.Violation;
import com.inthinc.pro.reports.hos.model.ViolationsDetail;
import com.inthinc.pro.reports.hos.model.ViolationsDetailRaw;
import com.inthinc.pro.reports.tabular.Result;
import com.inthinc.pro.reports.tabular.Tabular;
import com.inthinc.pro.reports.util.MessageUtil;

public class HosViolationsDetailReportCriteria extends ViolationsDetailReportCriteria implements Tabular {

    
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


    @Override
    public List<String> getColumnHeaders() {
        ResourceBundle resourceBundle = null;
        String bundleName = ReportType.HOS_VIOLATIONS_DETAIL_REPORT.getResourceBundle();
        if (bundleName != null)
            resourceBundle = MessageUtil.getBundle(getLocale(), bundleName);
        else resourceBundle = MessageUtil.getBundle(getLocale());
        
        List<String> columnHeaders = new ArrayList<String>();
        for (int i = 1; i <= 7; i++)
            columnHeaders.add(MessageUtil.getBundleString(resourceBundle, "column."+i+".tabular"));
        
        return columnHeaders;
    }


    @Override
    public List<List<Result>> getTableRows() {
        ResourceBundle resourceBundle = null;
        String bundleName = ReportType.HOS_VIOLATIONS_DETAIL_REPORT.getResourceBundle();
        if (bundleName != null)
            resourceBundle = MessageUtil.getBundle(getLocale(), bundleName);
        else resourceBundle = MessageUtil.getBundle(getLocale());

        List<ViolationsDetail> dataList = (List<ViolationsDetail>)getMainDataset();
        if (dataList == null)
            return null;
        
        List<List<Result>>records = new ArrayList<List<Result>>();
        
        for (ViolationsDetail detail : dataList) {
            for (Violation violation : detail.getViolationsList()) {
            List<Result> row = new ArrayList<Result>();
                row.add(new Result(detail.getTimeStr(), detail.getNotificationTime()));
                row.add(new Result(detail.getDriverName(), detail.getDriverName()));
                row.add(new Result(detail.getVehicleId(), detail.getVehicleId()));
                row.add(new Result(detail.getGroupName(), detail.getGroupName()));
                String violationTypeStr = MessageUtil.getBundleString(resourceBundle, "Violation."+violation.getType().getName()); 
                row.add(new Result(violationTypeStr, violationTypeStr));
                row.add(new Result(Converter.convertMinutes(violation.getMinutes()), violation.getMinutes()));
                String ruleSetTypeStr = MessageUtil.getBundleString(resourceBundle, detail.getRuleType().getName()); 
                row.add(new Result(ruleSetTypeStr,ruleSetTypeStr));
                
                records.add(row);
            }
        }

        return records;
        
        
    }

}
