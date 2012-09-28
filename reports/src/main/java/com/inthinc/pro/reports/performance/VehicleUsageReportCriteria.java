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

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.performance.VehicleUsageRecord;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.dao.WaysmartDAO;
import com.inthinc.pro.reports.performance.model.VehicleUsage;
import com.inthinc.pro.reports.util.DateTimeUtil;


public class VehicleUsageReportCriteria extends ReportCriteria {
    protected DateTimeFormatter dateTimeFormatter; 
    
    protected GroupDAO groupDAO;
    protected DriverDAO driverDAO;
    protected WaysmartDAO waysmartDAO;

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
                VehicleUsage bean = new VehicleUsage(
                        driver.getPerson().getFirst() + " " + driver.getPerson().getLast(), 
                        rec.getVehicle(), 
                        rec.getDate(), 
                        rec.getZoneName(), 
                        rec.getTimeEntered(), 
                        rec.getTimeExited(), 
                        rec.getMileage(), 
                        rec.getTotalMiles(), 
                        rec.getJobUse(), 
                        rec.getCompanyUse(), 
                        rec.getPersonalUse()
                );
                
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
        addParameter(ReportCriteria.REPORT_START_DATE, dateTimeFormatter.print(interval.getStart()));
        addParameter(ReportCriteria.REPORT_END_DATE, dateTimeFormatter.print(interval.getEnd()));
        
        List<Driver> driverList;
        Map<Driver, List<VehicleUsageRecord>> vehicleUsageRecordMap = new HashMap<Driver, List<VehicleUsageRecord>> ();
        
        if(group) {
            Group topGroup = groupDAO.findByID(id);
            if (topGroup != null) {
                driverList = driverDAO.getAllDrivers(topGroup.getGroupID());
                for (Driver driver : driverList) {
                    vehicleUsageRecordMap.put(driver, this.getVehicleUsageRecord(driver, interval));
                   
                }
            }
        } else {
            Driver driver = driverDAO.findByID(id);
            vehicleUsageRecordMap.put(driver, this.getVehicleUsageRecord(driver, interval));         
        }
        initDataSet(interval, vehicleUsageRecordMap);
    }
    
    private List<VehicleUsageRecord> getVehicleUsageRecord(Driver driver, Interval interval)
    {
        DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
        Interval queryInterval = DateTimeUtil.getExpandedInterval(interval, dateTimeZone, 1, 1);
        List<VehicleUsageRecord> vehicleUsageList = waysmartDAO.getVehicleUsage(driver.getDriverID(), queryInterval);
        
        return vehicleUsageList;
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

    public WaysmartDAO getWaysmartDAO() {
        return waysmartDAO;
    }

    public void setWaysmartDAO(WaysmartDAO waysmartDao) {
        this.waysmartDAO = waysmartDao;
    }


}
