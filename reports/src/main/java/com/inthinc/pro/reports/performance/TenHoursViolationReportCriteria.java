package com.inthinc.pro.reports.performance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
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
import com.inthinc.pro.model.performance.TenHoursViolationRecord;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.performance.model.TenHoursViolation;
import com.inthinc.pro.reports.util.DateTimeUtil;

public class TenHoursViolationReportCriteria extends ReportCriteria {
    
    private static final Logger logger = Logger.getLogger(TenHoursViolationReportCriteria.class);
    
    private static final String START_DATE_PARAM = "startDate";
    private static final String END_DATE_PARAM = "endDate";
    protected DateTimeFormatter dateTimeFormatter;

    protected DriverDAO driverDAO;
    private DriveTimeDAO driveTimeDAO;


    class TenHoursViolationComparator implements Comparator<TenHoursViolation> {

        private static final int COMPARISON_SAME = 0;
        private static final int COMPARISON_BEFORE = -1;
        private static final int COMPARISON_AFTER = 1;

        @Override
        public int compare(TenHoursViolation o1, TenHoursViolation o2) {

            // Checking for nulls on properties. Null values always goes at the end.
            int comparison = compareValues(o1.getGroupName(), o2.getGroupName());

            if (comparison == 0) {
                comparison = compareValues(o1.getDriverName(), o2.getDriverName());
            }

            return comparison;
        }

        @SuppressWarnings("unchecked")
        private int compareValues(Comparable o1, Object o2) {
            if (o1 == null) {
                if (o2 != null) {
                    return COMPARISON_AFTER;
                } else {
                    return COMPARISON_SAME;
                }
            } else {
                if (o2 == null) {
                    return COMPARISON_BEFORE;
                } else {
                    return o1.compareTo(o2);
                }
            }
        }
    }

    /**
     * Constructor
     * 
     * @param locale
     *            Local settings of the user - internationalization
     */
    public TenHoursViolationReportCriteria(Locale locale) {
        super(ReportType.TEN_HOUR_DAY_VIOLATIONS, "", locale);
        dateTimeFormatter = DateTimeFormat.forPattern(ReportCriteria.DATE_FORMAT).withLocale(locale);
    }

    void initDataSet(GroupHierarchy groupHierarchy, Interval interval, Map<Driver, List<TenHoursViolationRecord>> recordMap) {

        List<TenHoursViolation> violationList = new ArrayList<TenHoursViolation>();

        for (Entry<Driver, List<TenHoursViolationRecord>> entry : recordMap.entrySet()) {
            Driver driver = entry.getKey();
            String driverGroupName = groupHierarchy.getShortGroupName(driver.getGroupID(), SLASH_GROUP_SEPERATOR);
            for (TenHoursViolationRecord rec : entry.getValue()) {
                if (rec.getHoursThisDay() != null && rec.getHoursThisDay().doubleValue() > 0) {
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
        }
        Collections.sort(violationList, new TenHoursViolationComparator());
        setMainDataset(violationList);
    }

    /**
     * Retrieve all report data and pass them to a Map.
     * 
     * @param groupeID
     *            ID of the group chosen by the user
     * @param interval
     *            Interval chosen by the user
     */
    public void init(GroupHierarchy groupHierarchy, Integer groupID, Interval interval) {
        addParameter(TenHoursViolationReportCriteria.START_DATE_PARAM, dateTimeFormatter.print(interval.getStart()));
        addParameter(TenHoursViolationReportCriteria.END_DATE_PARAM, dateTimeFormatter.print(interval.getEnd()));
        Map<Driver, List<TenHoursViolationRecord>> violationRecordMap = new HashMap<Driver, List<TenHoursViolationRecord>>();
        Interval queryInterval = new Interval(interval.getStart().minusDays(1), new DateMidnight(interval.getEnd()).toDateTime().plusDays(2));
//System.out.println("interval: " + queryInterval);     

        List<Driver> driverList = driverDAO.getAllDrivers(groupID);
        List<DriveTimeRecord> driveTimeRecordList = driveTimeDAO.getDriveTimeRecordListForGroup(groupID, queryInterval);

        for (Driver driver : driverList) {
            if(includeDriver(getDriverDAO(), driver.getDriverID(), interval)){
                List<TenHoursViolationRecord> violationList = getTenHourViolationsList(driver, interval, driveTimeRecordList);

                if (!violationList.isEmpty()) {
                    violationRecordMap.put(driver, violationList);
                }
            }
        }

        initDataSet(groupHierarchy, interval, violationRecordMap);
    }

    private static final long TEN_HOURS_IN_SECONDS = 36000l;    

    private List<TenHoursViolationRecord> getTenHourViolationsList(Driver driver, Interval interval, List<DriveTimeRecord> driveTimeList) {
        List<DateTime> dayList = DateTimeUtil.getDayList(interval, DateTimeZone.getDefault());
        List<TenHoursViolationRecord> violationList = new ArrayList<TenHoursViolationRecord>();
        for (DateTime day : dayList) {
            Integer vehicleID = null;
            String vehicleName = null;
            long seconds = 0;
            for (DriveTimeRecord driveTimeRecord : driveTimeList) {
                if (!driveTimeRecord.getDriverID().equals(driver.getDriverID()))
                    continue;
//System.out.println(driver.getDriverID() + " Day: " + day + " " + day.getMillis());
//System.out.println(driveTimeRecord.getDateTime() + " " + driveTimeRecord.getDateTime().getMillis());
                if (day.isEqual(driveTimeRecord.getDateTime())) {
                    seconds += driveTimeRecord.getDriveTimeSeconds();
                    vehicleID = driveTimeRecord.getVehicleID();
                    vehicleName = driveTimeRecord.getVehicleName();
//System.out.println("seconds " + seconds);
                 }
            }
            
            if (seconds > TEN_HOURS_IN_SECONDS) {
                TenHoursViolationRecord tenHoursViolationRecord = new TenHoursViolationRecord();
                tenHoursViolationRecord.setDateTime(day);
                tenHoursViolationRecord.setDriverID(driver.getDriverID());
                tenHoursViolationRecord.setHoursThisDay(DateUtil.convertSecondsToDoubleHours(seconds));
                if (tenHoursViolationRecord.getHoursThisDay().doubleValue() > 24.0d) {
                    logger.error("Ten Hours Violations for driverID " + driver.getDriverID() + " exceeds 24 hours (" + tenHoursViolationRecord.getHoursThisDay() + " hrs)");
                    tenHoursViolationRecord.setHoursThisDay(24.0d);
                }
                tenHoursViolationRecord.setVehicleID(vehicleID);
                tenHoursViolationRecord.setVehicleName(vehicleName);
                violationList.add(tenHoursViolationRecord);
            }

        }
        return violationList;
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

    public DriveTimeDAO getDriveTimeDAO() {
        return driveTimeDAO;
    }

    public void setDriveTimeDAO(DriveTimeDAO driveTimeDAO) {
        this.driveTimeDAO = driveTimeDAO;
    }

}
