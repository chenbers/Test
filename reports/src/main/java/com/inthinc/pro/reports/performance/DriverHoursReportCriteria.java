package com.inthinc.pro.reports.performance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.pro.dao.DriveTimeDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.aggregation.DriveTimeRecord;
import com.inthinc.pro.model.performance.DriverHoursRecord;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.HosReportCriteria;
import com.inthinc.pro.reports.performance.model.DriverHours;
import com.inthinc.pro.reports.util.DateTimeUtil;

public class DriverHoursReportCriteria extends ReportCriteria {
    private static final String DAY_FORMAT = "MM/dd/yy";
	private static final String START_DATE_PARAM = "startDate";
	private static final String END_DATE_PARAM = "endDate";
	protected DateTimeFormatter dateTimeFormatter;
	protected DateTimeFormatter dayFormatter;

	protected DriverDAO driverDAO;
    private DriveTimeDAO driveTimeDAO;


    /**
	 * Constructor to initiate the report type and locale.
	 * @param locale
	 */
	public DriverHoursReportCriteria(Locale locale) {
		super(ReportType.DRIVER_HOURS, "", locale);
		dateTimeFormatter = DateTimeFormat.forPattern(ReportCriteria.DATE_FORMAT).withLocale(locale);
        dayFormatter = DateTimeFormat.forPattern(DriverHoursReportCriteria.DAY_FORMAT).withLocale(locale);
	}

	class DriverHoursComparator implements Comparator<DriverHours> {

		@Override
		public int compare(DriverHours o1, DriverHours o2) {
			int groupNamesComparison = compareValues(o1.getGroupName(),o2.getGroupName());
			
			// If Group Names are equal, then we compare the Driver Names
			if (groupNamesComparison == 0)
				return compareValues(o1.getDriverName(),o2.getDriverName());
			return groupNamesComparison;
		}
        @SuppressWarnings("unchecked")
        private int compareValues(Comparable o1, Object o2) {
            if (o1 == null) {
                if (o2 != null) {
                    return 1;
                } else {
                    return 0;
                }
            } else {
                if (o2 == null) {
                    return -1;
                } else {
                    return o1.compareTo(o2);
                }
            }
        }

	}
	
	void initDataSet(GroupHierarchy groupHierarchy, Map<Driver, List<DriverHoursRecord>> recordMap) {

		List<DriverHours> driverHoursList = new ArrayList<DriverHours>();

		for (Entry<Driver, List<DriverHoursRecord>> entry : recordMap.entrySet()) {
			Driver driver = entry.getKey();
			String driverGroupName = groupHierarchy.getShortGroupName(driver.getGroupID(), SLASH_GROUP_SEPERATOR);
	      	
			for (DriverHoursRecord rec : entry.getValue()) {
			    if (rec.getHoursThisDay()!=null && rec.getHoursThisDay().doubleValue() != 0.0) {
    				DriverHours bean = new DriverHours();
    				bean.setGroupName(driverGroupName);
    				bean.setDate(dayFormatter.print(rec.getDay()));
    				bean.setDriverName(driver.getPerson().getFullName());
    				bean.setHours(rec.getHoursThisDay());    
    				driverHoursList.add(bean);
			    }
			}
		}
		Collections.sort(driverHoursList, new DriverHoursComparator());
		setMainDataset(driverHoursList);
	}

	public void init(GroupHierarchy groupHierarchy, Integer groupID, Interval interval) {
	    this.setIncludeInactiveDrivers(HosReportCriteria.HOS_INACTIVE_DRIVERS_DEFAULT);
	    this.setIncludeZeroMilesDrivers(HosReportCriteria.HOS_ZERO_MILES_DRIVERS_DEFAULT);
        addParameter(DriverHoursReportCriteria.START_DATE_PARAM,dateTimeFormatter.print(interval.getStart()));
        addParameter(DriverHoursReportCriteria.END_DATE_PARAM,  dateTimeFormatter.print(interval.getEnd()));
        
        Interval queryInterval = new Interval(interval.getStart().minusDays(1), new DateMidnight(interval.getEnd()).toDateTime().plusDays(2));
//System.out.println("interval: " + queryInterval);     
        List<Driver> driverList = driverDAO.getAllDrivers(groupID);
        
        List<DriveTimeRecord> driveTimeRecordList = driveTimeDAO.getDriveTimeRecordListForGroup(groupID, queryInterval);

		Map<Driver, List<DriverHoursRecord>> driverHoursRecordMap = new HashMap<Driver, List<DriverHoursRecord>>();
		for (Driver driver : driverList) {
		    if (driver != null && includeDriver(driverDAO, driver.getDriverID(), interval)) {
                List<DriverHoursRecord> driverHoursList = getDriverHoursList(driver, interval, driveTimeRecordList);
	            driverHoursRecordMap.put(driver, driverHoursList);
		    }
		}

		initDataSet(groupHierarchy, driverHoursRecordMap);
	}
	
    List<DriverHoursRecord> getDriverHoursList(Driver driver, Interval interval, List<DriveTimeRecord> driveTimeList) {
        List<DateTime> dayList = DateTimeUtil.getDayList(interval, DateTimeZone.getDefault());
        List<DriverHoursRecord> driverHoursRecordList = new ArrayList<DriverHoursRecord>();
        for (DateTime day : dayList) {
            DriverHoursRecord driverHoursRecord = new DriverHoursRecord();
            driverHoursRecord.setDay(day);
            driverHoursRecord.setDriverID(driver.getDriverID());
            long seconds = 0;
            for (DriveTimeRecord driveTimeRecord : driveTimeList) {
                if (!driveTimeRecord.getDriverID().equals(driver.getDriverID()))
                    continue;
                
//System.out.println(driver.getDriverID() + " Day: " + day + " " + day.getMillis());
//System.out.println(driveTimeRecord.getDateTime() + " " + driveTimeRecord.getDateTime().getMillis());
                if (day.isEqual(driveTimeRecord.getDateTime())) {
                    seconds += driveTimeRecord.getDriveTimeSeconds();
//System.out.println("seconds " + seconds);
                 }
            }
            driverHoursRecord.setHoursThisDay(DateUtil.convertSecondsToDoubleHours(seconds));
            driverHoursRecordList.add(driverHoursRecord);
        }
        return driverHoursRecordList;
    }


	public void setDriverDAO(DriverDAO driverDAO) {
		this.driverDAO = driverDAO;
	}


    public DriveTimeDAO getDriveTimeDAO() {
        return driveTimeDAO;
    }

    public void setDriveTimeDAO(DriveTimeDAO driveTimeDAO) {
        this.driveTimeDAO = driveTimeDAO;
    }

}
