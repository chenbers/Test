package com.inthinc.pro.reports.performance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.performance.TenHoursViolationRecord;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.dao.WaysmartDAO;
import com.inthinc.pro.reports.performance.model.TenHoursViolation;


public class TenHoursViolationReportCriteria extends ReportCriteria {
    private static final String START_DATE_PARAM = "startDate";
    private static final String END_DATE_PARAM = "endDate";
    protected DateTimeFormatter dateTimeFormatter; 
    
    protected DriverDAO driverDAO;
    protected WaysmartDAO waysmartDAO;

	class TenHoursViolationComparator implements Comparator<TenHoursViolation> {

		@Override
		public int compare(TenHoursViolation o1, TenHoursViolation o2) {
			int groupNamesComparison = o1.getGroupName().compareTo(o2.getGroupName());
			
			// If Group Names are equal, then we compare the Driver Names
			if (groupNamesComparison == 0)
				return o1.getDriverName().compareTo(o2.getDriverName());
			else
				return groupNamesComparison;
		}}    
    
    /**
     * Constructor
     * @param locale Local settings of the user - internationalization 
     */
    public TenHoursViolationReportCriteria(Locale locale) 
    {
        super(ReportType.TEN_HOUR_DAY_VIOLATIONS, "", locale);
        dateTimeFormatter = DateTimeFormat.forPattern(ReportCriteria.DATE_FORMAT).withLocale(locale);
    }

    void initDataSet(GroupHierarchy groupHierarchy, Interval interval, 
            Map<Driver, List<TenHoursViolationRecord>> recordMap)
    {
        
        List<TenHoursViolation> violationList = new ArrayList<TenHoursViolation>();
        
        for (Entry<Driver, List<TenHoursViolationRecord>> entry : recordMap.entrySet()) {
            Driver driver = entry.getKey();
            String driverGroupName = groupHierarchy.getShortGroupName(driver.getGroupID(), SLASH_GROUP_SEPERATOR);
            for (TenHoursViolationRecord rec : entry.getValue()) {
                TenHoursViolation bean = new TenHoursViolation();
                bean.setGroupName(driverGroupName);
                bean.setDate(dateTimeFormatter.print(rec.getDateTime()));
                bean.setDriverName(driver.getPerson().getFullName());
                bean.setEmployeeID(driver.getPerson().getEmpid());
                bean.setVehicleName(rec.getVehicleName());
                bean.setHoursThisDay(rec.getHoursThisDay().doubleValue());
                
                violationList.add(bean);
            }
        }
		Collections.sort(violationList, new TenHoursViolationComparator());        
        setMainDataset(violationList);
    }
    
    /**
     * Retrieve all report data and pass them to a Map.
     * 
     * @param groupeID ID of the group chosen by the user
     * @param interval Interval chosen by the user 
     */
    public void init(GroupHierarchy groupHierarchy, Integer groupID, Interval interval)
    {
        addParameter(TenHoursViolationReportCriteria.START_DATE_PARAM, dateTimeFormatter.print(interval.getStart()));
        addParameter(TenHoursViolationReportCriteria.END_DATE_PARAM,   dateTimeFormatter.print(interval.getEnd()));
        Map<Driver, List<TenHoursViolationRecord>> violationRecordMap = new HashMap<Driver, List<TenHoursViolationRecord>> ();

        List<Driver> driverList = driverDAO.getAllDrivers(groupID);
        for (Driver driver : driverList) {
            List<TenHoursViolationRecord> violationList = waysmartDAO.getTenHoursViolations(driver, interval);
            if (!violationList.isEmpty()) {
                violationRecordMap.put(driver, violationList);
            }
        }

        initDataSet(groupHierarchy, interval, violationRecordMap);
    }

    /**
     * Getter for driverDAO property. 
     */
    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    /**
     * Setter for driverDAO property. 
     */
    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    public WaysmartDAO getWaysmartDAO() {
        return waysmartDAO;
    }

    public void setWaysmartDAO(WaysmartDAO waysmartDao) {
        this.waysmartDAO = waysmartDao;
    }
    
}
