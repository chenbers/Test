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
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.performance.DriverHoursRecord;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.dao.WaysmartDAO;
import com.inthinc.pro.reports.performance.model.DriverHours;

public class DriverHoursReportCriteria extends ReportCriteria {
    private static final String DATE_FORMAT = "MM/dd/yyyy";
    private static final String DAY_FORMAT = "MM/dd/yy";
	private static final String START_DATE_PARAM = "startDate";
	private static final String END_DATE_PARAM = "endDate";
	protected DateTimeFormatter dateTimeFormatter;
	protected DateTimeFormatter dayFormatter;

	protected GroupDAO groupDAO;
	protected DriverDAO driverDAO;
	protected WaysmartDAO waysmartDao;

	/**
	 * Constructor to initiate the report type and locale.
	 * @param locale
	 */
	public DriverHoursReportCriteria(Locale locale) {
		super(ReportType.DRIVER_HOURS, "", locale);
		dateTimeFormatter = DateTimeFormat.forPattern(DriverHoursReportCriteria.DATE_FORMAT).withLocale(locale);
        dayFormatter = DateTimeFormat.forPattern(DriverHoursReportCriteria.DAY_FORMAT).withLocale(locale);
	}

	private class DriverHoursComparator implements Comparator<DriverHours> {

		@Override
		public int compare(DriverHours o1, DriverHours o2) {
			int groupNamesComparison = o1.getGroupName().compareTo(o2.getGroupName());
			
			// If Group Names are equal, then we compare the Driver Names
			if (groupNamesComparison == 0)
				return o1.getDriverName().compareTo(o2.getDriverName());
			else
				return groupNamesComparison;
		}}
	
	void initDataSet(GroupHierarchy groupHierarchy, Interval interval,
			Map<Driver, List<DriverHoursRecord>> recordMap) {

		List<DriverHours> driverHoursList = new ArrayList<DriverHours>();

		for (Entry<Driver, List<DriverHoursRecord>> entry : recordMap.entrySet()) {
			Driver driver = entry.getKey();
			String driverGroupName = groupHierarchy.getFullGroupName(driver
					.getGroupID(), GROUP_SEPARATOR);
	        if (driverGroupName.endsWith(GROUP_SEPARATOR)) {
	            driverGroupName = driverGroupName.substring(0, driverGroupName.length() - GROUP_SEPARATOR.length());
	        }
			
			for (DriverHoursRecord rec : entry.getValue()) {
				DriverHours bean = new DriverHours();
				bean.setGroupName(driverGroupName);
				bean.setDate(dayFormatter.print(rec.getDate().getTime()));
				bean.setDriverName(driver.getPerson().getFullNameLastFirst());
				bean.setHours(rec.getHoursThisDay());

				driverHoursList.add(bean);
			}
		}
		Collections.sort(driverHoursList, new DriverHoursComparator());
		setMainDataset(driverHoursList);
	}

	public void init(Integer groupID, Interval interval) {
		Group topGroup = groupDAO.findByID(groupID);
		List<Driver> driverList = driverDAO.getAllDrivers(groupID);
		List<Group> groupList = groupDAO.getGroupHierarchy(topGroup
				.getAccountID(), topGroup.getGroupID());
		Map<Driver, List<DriverHoursRecord>> driverHoursRecordMap = new HashMap<Driver, List<DriverHoursRecord>>();
		for (Driver driver : driverList) {
		    if (driver == null) {
		        continue;
		    }
			List<DriverHoursRecord> driverHoursList = waysmartDao.getDriverHours(driver, interval);
			driverHoursRecordMap.put(driver, driverHoursList);
		}
		addParameter(DriverHoursReportCriteria.START_DATE_PARAM,dateTimeFormatter.print(interval.getStart()));
		addParameter(DriverHoursReportCriteria.END_DATE_PARAM,	dateTimeFormatter.print(interval.getEnd()));

		initDataSet(new GroupHierarchy(groupList), interval, driverHoursRecordMap);
	}

	public void setGroupDAO(GroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}

	public void setDriverDAO(DriverDAO driverDAO) {
		this.driverDAO = driverDAO;
	}

	public void setWaysmartDao(WaysmartDAO waysmartDao) {
		this.waysmartDao = waysmartDao;
	}


}
