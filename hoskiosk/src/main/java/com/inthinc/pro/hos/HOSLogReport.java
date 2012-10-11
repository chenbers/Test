package com.inthinc.pro.hos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.inthinc.pro.backing.ui.DateRange;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.service.ReportCriteriaService;

public class HOSLogReport extends HOSBase{

    private GroupHierarchy groupHierarchy;
    public HOSLogReport(HOSDAO hosDAO, GroupHierarchy groupHierarchy) {
        super(hosDAO);
        this.groupHierarchy = groupHierarchy;
    }

    public List<ReportCriteria>  generateReport(ReportCriteriaService reportCriteriaService, Driver driver, DateRange dateRange) {
        
        List<ReportCriteria> reportCriteriaList = new ArrayList<ReportCriteria>();
        reportCriteriaList.addAll(reportCriteriaService.getHosDailyDriverLogReportCriteria(groupHierarchy, driver.getDriverID(), 
                        dateRange.getInterval(), driver.getPerson().getLocale(), 
                        driver.getPerson().getMeasurementType() == MeasurementType.METRIC));
        for (ReportCriteria reportCriteria : reportCriteriaList) {
            reportCriteria.setReportDate(new Date(), driver.getPerson().getTimeZone());
            reportCriteria.setLocale(driver.getPerson().getLocale());
            reportCriteria.setUseMetric((driver.getPerson().getMeasurementType() != null && driver.getPerson().getMeasurementType().equals(MeasurementType.METRIC)));
            reportCriteria.setMeasurementType(driver.getPerson().getMeasurementType());
            reportCriteria.setFuelEfficiencyType(driver.getPerson().getFuelEfficiencyType());

        }
        return reportCriteriaList;

        
    }
}
