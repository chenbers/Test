package com.inthinc.pro.reports.performance;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.map.ReportAddressLookupBean;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverStopReport;
import com.inthinc.pro.model.DriverStops;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.NoAddressFoundException;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.reports.GroupListReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.util.DateTimeUtil;
import com.inthinc.pro.reports.util.MessageUtil;

public class TeamStopsReportCriteria  extends GroupListReportCriteria {
    private ReportAddressLookupBean reportAddressLookupBean;

    public TeamStopsReportCriteria(Locale locale) {
        super(ReportType.TEAM_STOPS_REPORT, locale);
    }
    
    public void init(GroupHierarchy accountGroupHierarchy, Integer driverID, TimeFrame timeFrame, DateTimeZone timeZone, DriverStopReport driverStopReport)
    {
        if (driverStopReport == null) {
            Driver driver = getDriverDAO().findByID(driverID);
            String driverName = driver.getPerson().getFullName();
            String teamName = getFullGroupName(accountGroupHierarchy, driver.getGroupID());
            List<DriverStops> driverStops = getDriverStopsInInterval(driverID, timeFrame, timeZone, driverName);

            driverStopReport = new DriverStopReport(teamName, driverID, driverName, timeFrame, driverStops);
        }
        
        fillInDriverStopAddresses(getLocale(), driverStopReport);
        setTimeFrame(timeFrame);
        List<DriverStopReport> teamStopsCriteriaList = new ArrayList<DriverStopReport>();
        teamStopsCriteriaList.add(driverStopReport);
        setMainDataset(teamStopsCriteriaList);
    }
    public void init(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, TimeFrame timeFrame, DateTimeZone timeZone){
        init(accountGroupHierarchy, groupIDList, timeFrame, timeZone, INACTIVE_DRIVERS_DEFAULT, ZERO_MILES_DRIVERS_DEFAULT);
    }
    public void init(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, TimeFrame timeFrame, DateTimeZone timeZone, boolean inactiveDrivers, boolean zeroMilesDrivers)
    {
        setTimeFrame(timeFrame);
        List<DriverStopReport> teamStopsCriteriaList = new ArrayList<DriverStopReport>();
        List<Group> reportGroupList = getReportGroupList(groupIDList, accountGroupHierarchy);
        List<Driver> reportDriverList = getReportDriverList(reportGroupList);
        for (Driver driver : reportDriverList) {
            String driverName = driver.getPerson().getFullName();
            String teamName = getFullGroupName(accountGroupHierarchy, driver.getGroupID());
            List<DriverStops> driverStops = getDriverStopsInInterval(driver.getDriverID(), timeFrame, timeZone, driverName);
            if (driverStops == null || driverStops.size() == 0){
                continue;
            } else {
                DriverStopReport driverStopReport = new DriverStopReport(teamName, driver.getDriverID(), driverName, timeFrame, driverStops);
                fillInDriverStopAddresses(getLocale(), driverStopReport);
                teamStopsCriteriaList.add(driverStopReport);
            }
        }
        setMainDataset(teamStopsCriteriaList);
    }

    private List<DriverStops> getDriverStopsInInterval(Integer driverID, TimeFrame timeFrame, DateTimeZone timeZone, String driverName) {
        List<DriverStops> driverStops = new ArrayList<DriverStops>();
        
        List<Interval> intervalList = DateTimeUtil.getDayIntervalList(timeFrame.getInterval(timeZone), timeZone);
        intervalList.remove(intervalList.size()-1);
        
        for (Interval dayInterval : intervalList) {
System.out.println(" day interval: " + dayInterval);
            List<DriverStops> dayDriverStops = getDriverDAO().getStops(driverID, driverName, dayInterval);
            driverStops.addAll(dayDriverStops);
        }
        return driverStops;
    }
    
    private void fillInDriverStopAddresses(Locale locale, DriverStopReport driverStopReport) {
        AddressLookup addressLookup = (reportAddressLookupBean == null) ? null : reportAddressLookupBean.getAddressLookup();
        String noAddressFound = MessageUtil.getMessageString("report.no_address_found", locale);
        if (addressLookup != null) {
            for (DriverStops driverStop : driverStopReport.getDriverStops()) {
                driverStop.setAddress(noAddressFound);
                try {
                    if (driverStop.getLat() != null && driverStop.getLng() != null)
                        driverStop.setAddress(addressLookup.getAddress(driverStop.getLat(), driverStop.getLng()));
                } catch (NoAddressFoundException e) {
                }
            }
        }
    }    


    public ReportAddressLookupBean getReportAddressLookupBean() {
        return reportAddressLookupBean;
    }

    public void setReportAddressLookupBean(ReportAddressLookupBean reportAddressLookupBean) {
        this.reportAddressLookupBean = reportAddressLookupBean;
    }


}
