package com.inthinc.pro.reports.performance;

import java.sql.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

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
import org.joda.time.format.DateTimeFormatter;

public class TeamStopsReportCriteria  extends GroupListReportCriteria {
    private ReportAddressLookupBean reportAddressLookupBean;

    public TeamStopsReportCriteria(Locale locale) {
        super(ReportType.TEAM_STOPS_REPORT, locale);
    }
    
    public void init(Boolean activeInterval, Interval interval, GroupHierarchy accountGroupHierarchy, Integer driverID, TimeFrame timeFrame, DateTimeZone timeZone, DriverStopReport driverStopReport)
    {
        if (driverStopReport == null) {
            Driver driver = getDriverDAO().findByID(driverID);
            String driverName = driver.getPerson().getFullName();
            String teamName = getFullGroupName(accountGroupHierarchy, driver.getGroupID());
            List<DriverStops> driverStops = getDriverStopsInInterval(driverID, timeFrame, timeZone, driverName, activeInterval, interval);

            driverStopReport = new DriverStopReport(teamName, driverID, driverName, timeFrame, driverStops);
        }
        
        fillInDriverStopAddresses(getLocale(), driverStopReport);
        setTimeFrame(timeFrame);
        List<DriverStopReport> teamStopsCriteriaList = new ArrayList<DriverStopReport>();
        teamStopsCriteriaList.add(driverStopReport);
        setMainDataset(teamStopsCriteriaList);
    }
    public void init(Interval interval, GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, TimeFrame timeFrame, DateTimeZone timeZone){
        init(interval, accountGroupHierarchy, groupIDList, timeFrame, timeZone, DEFAULT_EXCLUDE_INACTIVE_DRIVERS, DEFAULT_EXCLUDE_ZERO_MILES_DRIVERS);
    }
    public void init(Interval interval, GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, TimeFrame timeFrame, DateTimeZone timeZone, boolean inactiveDrivers, boolean zeroMilesDrivers)
    {

        if (timeFrame != null && !timeFrame.equals(TimeFrame.CUSTOM_RANGE))  {
        setTimeFrame(timeFrame);
        } else{

        setTimeFrame(timeFrame, interval);

        }

        List<DriverStopReport> teamStopsCriteriaList = new ArrayList<DriverStopReport>();
        List<Group> reportGroupList = getReportGroupList(groupIDList, accountGroupHierarchy);
        List<Driver> reportDriverList = getReportDriverList(reportGroupList);
        for (Driver driver : reportDriverList) {
            String driverName = driver.getPerson().getFullName();
            String teamName = getFullGroupName(accountGroupHierarchy, driver.getGroupID());
               //vf
            boolean activInterval;
            List<DriverStops> driverStops = new ArrayList<DriverStops>();
            if (timeFrame != null && !timeFrame.equals(TimeFrame.CUSTOM_RANGE))  {
                activInterval = false;
                driverStops = getDriverStopsInInterval(driver.getDriverID(), timeFrame, timeZone, driverName, activInterval, interval);

            }   else {
                activInterval = true;
                driverStops = getDriverStopsInInterval(driver.getDriverID(), timeFrame, timeZone, driverName, activInterval, interval);


            }
            if (driverStops == null || driverStops.size() == 0){
                continue;
            } else {
                if (timeFrame != null && !timeFrame.equals(TimeFrame.CUSTOM_RANGE))  {
                    DriverStopReport  driverStopReport = new DriverStopReport(teamName, driver.getDriverID(), driverName, timeFrame, driverStops);
                    fillInDriverStopAddresses(getLocale(), driverStopReport);
                    teamStopsCriteriaList.add(driverStopReport);
                }   else {
                    DriverStopReport  driverStopReport = new DriverStopReport(teamName, driver.getDriverID(), driverName, interval, driverStops);
                    fillInDriverStopAddresses(getLocale(), driverStopReport);
                    teamStopsCriteriaList.add(driverStopReport) ;
                }
               ;
            }
        }
        
        DateTime dateTime = new DateTime(timeZone);
        
        GregorianCalendar gc = dateTime.toGregorianCalendar();
        TimeZone zone = gc.getTimeZone();

        if (timeFrame != null && !timeFrame.equals(TimeFrame.CUSTOM_RANGE))  {
            addParameter(USER_TIME_ZONE, zone);

        }   else {

            addParameter(REPORT_START_DATE, interval.getStart().toDate());
            addParameter(REPORT_END_DATE, interval.getEnd().toDate());

        }

        setMainDataset(teamStopsCriteriaList);
    }

    private List<DriverStops> getDriverStopsInInterval(Integer driverID, TimeFrame timeFrame, DateTimeZone timeZone, String driverName, Boolean activeInterval, Interval interval) {
        List<DriverStops> driverStops = new ArrayList<DriverStops>();
        if (!activeInterval) {
            List<Interval> intervalList = DateTimeUtil.getDayIntervalList(timeFrame.getInterval(timeZone), timeZone);

            for (Interval dayInterval : intervalList) {
                //vf boolean
                List<DriverStops> dayDriverStops = getDriverDAO().getStops(driverID, driverName, dayInterval);
                driverStops.addAll(dayDriverStops);
            }
        }
        else{
            List<DriverStops> dayDriverStops = getDriverDAO().getStops(driverID, driverName, interval);
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
