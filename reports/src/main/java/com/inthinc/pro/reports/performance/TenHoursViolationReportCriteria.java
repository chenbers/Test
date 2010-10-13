package com.inthinc.pro.reports.performance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.performance.TenHoursViolationRecord;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.dao.WaysmartDAO;
import com.inthinc.pro.reports.hos.model.GroupHierarchyForReports;
import com.inthinc.pro.reports.performance.model.TenHoursViolation;
import com.inthinc.pro.reports.util.DateTimeUtil;


public class TenHoursViolationReportCriteria extends ReportCriteria {
    private static final String START_DATE_PARAM = "startDate";
    private static final String END_DATE_PARAM = "endDate";
    private static final String LOGO_PARAM = "logoImageURL";
    protected DateTimeFormatter dateTimeFormatter; 
    
    protected AccountDAO accountDAO;
    protected GroupDAO groupDAO;
    protected DriverDAO driverDAO;
    protected WaysmartDAO waysmartDao;

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
        dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy").withLocale(locale);
    }

    void initDataSet(Group topGroup, List<Group> groupList, Interval interval, 
            Map<Driver, List<TenHoursViolationRecord>> recordMap)
    {
        GroupHierarchyForReports groupHierarchy = new GroupHierarchyForReports(topGroup, groupList);  
        
        List<TenHoursViolation> violationList = new ArrayList<TenHoursViolation>();
        
        for (Entry<Driver, List<TenHoursViolationRecord>> entry : recordMap.entrySet()) {
            Driver driver = entry.getKey();
            String driverGroupName = groupHierarchy.getFullName(driver.getGroupID());
            for (TenHoursViolationRecord rec : entry.getValue()) {
                TenHoursViolation bean = new TenHoursViolation();
                bean.setGroupName(driverGroupName);
                bean.setDate(rec.getDate());
                bean.setDriverName(driver.getPerson().getFullNameLastFirst());
                bean.setEmployeeID(driver.getPerson().getEmpid());
                bean.setVehicleID(rec.getVehicleID().toString());
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
    public void init(Integer groupID, Interval interval)
    {
        Group topGroup = groupDAO.findByID(groupID);
       // Account account = accountDAO.findByID(topGroup.getAccountID());
        List<Driver> driverList = driverDAO.getAllDrivers(groupID);
        List<Group> groupList = groupDAO.getGroupHierarchy(topGroup.getAccountID(), topGroup.getGroupID());
        Map<Driver, List<TenHoursViolationRecord>> violationRecordMap = new HashMap<Driver, List<TenHoursViolationRecord>> ();
        for (Driver driver : driverList) {
            DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
            Interval queryInterval = DateTimeUtil.getExpandedInterval(interval, dateTimeZone, 1, 1);
            List<TenHoursViolationRecord> violationList = waysmartDao.getTenHoursViolations(driver.getDriverID(), queryInterval);
            if (!violationList.isEmpty()) {
                violationRecordMap.put(driver, violationList);
            }
        }
        addParameter(TenHoursViolationReportCriteria.START_DATE_PARAM, dateTimeFormatter.print(interval.getStart()));
        addParameter(TenHoursViolationReportCriteria.END_DATE_PARAM,   dateTimeFormatter.print(interval.getEnd()));
       // addParameter(TenHoursViolationReportCriteria.LOGO_PARAM, TenHoursViolationReportCriteria.LOGO_URL);

        initDataSet(topGroup, groupList, interval, violationRecordMap);
    }

    /**
     * Getter for accountDAO property. 
     */
    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    /**
     * Setter for accountDAO property. 
     */
    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
    
    /**
     * Getter for groupDAO property. 
     */
    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    /**
     * Setter for groupDAO property. 
     */
    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
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

    public WaysmartDAO getWaysmartDao() {
        return waysmartDao;
    }

    public void setWaysmartDao(WaysmartDAO waysmartDao) {
        this.waysmartDao = waysmartDao;
    }


}
