package com.inthinc.pro.reports.hos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.pro.model.Group;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.model.GroupHierarchy;
import com.inthinc.pro.reports.hos.model.HosGroupUnitMileage;
import com.inthinc.pro.reports.hos.model.HosZeroMiles;

public class HosZeroMilesReportCriteria extends ReportCriteria {

    private static final Logger logger = Logger.getLogger(HosZeroMilesReportCriteria.class);
    protected DateTimeFormatter dateTimeFormatter;
    
    public HosZeroMilesReportCriteria(Locale locale) 
    {
        super(ReportType.HOS_ZERO_MILES, "", locale);
        dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy").withLocale(locale);

    }
    
    public void init(Integer groupID, Interval interval)
    {
/*        
        Group topGroup = groupDAO.findByID(groupID);
        List<Group> groupList = groupDAO.getGroupHierarchy(topGroup.getAccountID(), topGroup.getGroupID());
        List<Driver> driverList = driverDAO.getDrivers(groupID);
        Map<Driver, List<HOSRecord>> driverHOSRecordMap = new HashMap<Driver, List<HOSRecord>> ();
        
        if (driverList != null) {
            for (Driver driver : driverList) {
                if (driver.getDot() == null)
                    continue;
    //            Date hosLogQueryStartDate = DateUtil.getStartOfDayForDaysBack(parseDate(startDate), RuleSetFactory.getDaysBackForRuleSetType(driverDotRuleType), timeZone);
    //            Date hosLogQueryEndDate = DateUtil.getEndOfDayForDaysForward(parseDate(endDate), RuleSetFactory.getDaysForwardForRuleSetType(driverDotRuleType), timeZone);
                DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
                DateTime queryStart = new DateTime(interval.getStart(), dateTimeZone).minusDays(RuleSetFactory.getDaysBackForRuleSetType(driver.getDot()));
                DateTime queryEnd = new DateTime(interval.getEnd(), dateTimeZone).minusDays(RuleSetFactory.getDaysForwardForRuleSetType(driver.getDot()));
                // TODO:
    //            List<HOSRecord> hosRecordList = hosDAO.getHosRecords(driver.getDriverID(), new Interval(queryStart, queryEnd));
                List<HOSRecord> hosRecordList = new ArrayList<HOSRecord>();
                driverHOSRecordMap.put(driver, hosRecordList);
//                List<HOSRec> recListForViolationsCalc = getRecListFromLogList(hosRecordList, queryEnd.toDate(), !(driver.getDot().equals(RuleSetType.NON_DOT)));
                
//                driverHOSRecMap.put(driver, getRecListFromLogList(hosRecordList, queryEnd.toDate(), !(driver.getDot().equals(RuleSetType.NON_DOT))));
                
            }
        }
        
        List<HosGroupMileage> groupMileageList = new ArrayList<HosGroupMileage>();
        List<HosGroupMileage> groupNoDriverMileageList = new ArrayList<HosGroupMileage>();

        initDataSet(interval, topGroup, groupList, driverHOSRecordMap, groupMileageList, groupNoDriverMileageList);
*/            
    }
    
    void initDataSet(Interval interval, Group topGroup,  List<Group> groupList, 
            List<HosGroupUnitMileage> groupUnitNoDriverMileageList)
    {
        GroupHierarchy groupHierarchy = new GroupHierarchy(topGroup, groupList);
         
        List<HosZeroMiles> dataList = new ArrayList<HosZeroMiles>();
        for (HosGroupUnitMileage zeroMileage : groupUnitNoDriverMileageList) {
            
            dataList.add(new HosZeroMiles(groupHierarchy.getFullName(groupHierarchy.getGroup(zeroMileage.getGroupID())), zeroMileage.getUnitID(), zeroMileage.getDistance().doubleValue()));
            
        }
        Collections.sort(dataList);
        setMainDataset(dataList);
        
        addParameter("REPORT_START_DATE", dateTimeFormatter.print(interval.getStart()));
        addParameter("REPORT_END_DATE", dateTimeFormatter.print(interval.getEnd()));
        
//        setUseMetric(true);
        
    }

}
