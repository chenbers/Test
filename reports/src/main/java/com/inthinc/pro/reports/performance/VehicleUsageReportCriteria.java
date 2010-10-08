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
import com.inthinc.pro.dao.report.WaysmartDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.performance.VehicleUsageRecord;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.model.VehicleUsage;
import com.inthinc.pro.reports.util.DateTimeUtil;


public class VehicleUsageReportCriteria extends ReportCriteria {
    private static final String START_DATE_PARAM = "startDate";
    private static final String END_DATE_PARAM = "endDate";
    private static final String LOGO_PARAM = "logoImageURL";
    protected DateTimeFormatter dateTimeFormatter; 
    
    protected AccountDAO accountDAO;
    protected GroupDAO groupDAO;
    protected DriverDAO driverDAO;
    protected WaysmartDAO waysmartDao;

	private class VehicleUsageComparator implements Comparator<VehicleUsage> {

		@Override
		public int compare(VehicleUsage o1, VehicleUsage o2) {
				return o1.getDriver().compareTo(o2.getDriver());
		}}    
    
    /**
     * Constructor
     * @param locale Local settings of the user - internationalization 
     */
    public VehicleUsageReportCriteria(Locale locale) 
    {
        super(ReportType.VEHICLE_USAGE, "", locale);
        dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy").withLocale(locale);
    }

    void initDataSet(Interval interval, 
            Map<Driver, List<VehicleUsageRecord>> recordMap)
    {   
        List<VehicleUsage> violationList = new ArrayList<VehicleUsage>();
        
        for (Entry<Driver, List<VehicleUsageRecord>> entry : recordMap.entrySet()) {
            Driver driver = entry.getKey();
            for (VehicleUsageRecord rec : entry.getValue()) {
                VehicleUsage bean = new VehicleUsage();
                bean.setDate(rec.getDate());
                bean.setDriver(driver.getPerson().getFullNameLastFirst());
                bean.setVehicle(rec.getVehicle().toString());
                
                violationList.add(bean);
            }
        }
		Collections.sort(violationList, new VehicleUsageComparator());        
        setMainDataset(violationList);
    }
    
    /**
     * Retrieve        All report data and pass them to a Map.
     * 
     * @param id ID of the group chosen by the user
     * @param interval Interval chosen by the user 
     * @param group    Indicating if type of ID is group or driver
     */
    public void init(Integer id, Interval interval, boolean group)
    {
        List<Driver> driverList;
        Map<Driver, List<VehicleUsageRecord>> vehicleUsageRecordMap = new HashMap<Driver, List<VehicleUsageRecord>> ();
        
        addParameter(VehicleUsageReportCriteria.START_DATE_PARAM, dateTimeFormatter.print(interval.getStart()));
        addParameter(VehicleUsageReportCriteria.END_DATE_PARAM,   dateTimeFormatter.print(interval.getEnd()));
        
        if(group) {
            Group topGroup = groupDAO.findByID(id);
            driverList = driverDAO.getAllDrivers(id);
            List<Group> groupList = groupDAO.getGroupHierarchy(topGroup.getAccountID(), topGroup.getGroupID());
            for (Driver driver : driverList) {
                this.getVehicleUsageRecord(driver, interval, vehicleUsageRecordMap);
            }
        }
        else {
            Driver driver = driverDAO.findByID(id);
            this.getVehicleUsageRecord(driver, interval, vehicleUsageRecordMap);         
        }
        initDataSet(interval, vehicleUsageRecordMap);

        
    }
    
    private void getVehicleUsageRecord(Driver driver, Interval interval,Map<Driver, List<VehicleUsageRecord>> vehicleUsageRecordMap  )
    {
        DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
        Interval queryInterval = DateTimeUtil.getExpandedInterval(interval, dateTimeZone, 1, 1);
        List<VehicleUsageRecord> vehicleUsageList = waysmartDao.getVehicleUsage(driver.getDriverID(), queryInterval);
        if (!vehicleUsageList.isEmpty()) {
            vehicleUsageRecordMap.put(driver, vehicleUsageList);
        }
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
