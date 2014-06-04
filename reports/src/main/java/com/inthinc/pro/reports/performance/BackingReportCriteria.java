package com.inthinc.pro.reports.performance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.joda.time.DateTimeZone;

import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import org.joda.time.Interval;

public class BackingReportCriteria extends ReportCriteria {

    private static Logger logger = Logger.getLogger(BackingReportCriteria.class);

    private BackingReportCriteria(Locale locale) {
        super(ReportType.BACKING_REPORT, null, locale);
        logger.debug(String.format("Creating BackingReportCriteria with locale %s", locale.toString()));
    }

    public static class Builder {

        private Locale locale;

        private DateTimeZone dateTimeZone;

        private Integer groupID;

        private TimeFrame timeFrame;

        private Interval interval;

        private MeasurementType measurementType;

        private GroupReportDAO groupReportDAO;

        private GroupHierarchy groupHierarchy;

        private Boolean includeInactiveDrivers;

        private Boolean includeZeroMilesDrivers;

        public Builder(Interval interval, GroupHierarchy groupHierarchy, GroupReportDAO groupReportDAO, Integer groupID, TimeFrame timeFrame, MeasurementType measurementType) {
            this(interval, groupHierarchy, groupReportDAO, groupID, timeFrame, measurementType, ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS, ReportCriteria.DEFAULT_EXCLUDE_ZERO_MILES_DRIVERS);
        }

        public Builder(Interval interval, GroupHierarchy groupHierarchy, GroupReportDAO groupReportDAO, Integer groupID, TimeFrame timeFrame, MeasurementType measurementType, boolean includeInactiveDrivers,
                boolean includeZeroMilesDrivers) {
            this.interval = interval;
            this.dateTimeZone = DateTimeZone.UTC;
            this.locale = Locale.US;
            this.groupID = groupID;
            this.timeFrame = timeFrame;
            this.groupHierarchy = groupHierarchy;
            this.groupReportDAO = groupReportDAO;
            this.includeInactiveDrivers = includeInactiveDrivers;
            this.includeZeroMilesDrivers = includeZeroMilesDrivers;
            this.measurementType = measurementType;
        }

        public void setDateTimeZone(DateTimeZone dateTimeZone) {
            this.dateTimeZone = dateTimeZone;
        }

        public DateTimeZone getDateTimeZone() {
            return dateTimeZone;
        }

        public void setLocale(Locale locale) {
            this.locale = locale;
        }

        public Locale getLocale() {
            return locale;
        }

        public Integer getGroupID() {
            return groupID;
        }

        public GroupReportDAO getGroupReportDAO() {
            return groupReportDAO;
        }

        public GroupHierarchy getGroupHierarchy() {
            return groupHierarchy;
        }

        public TimeFrame getInterval() {
            return timeFrame;
        }

        public BackingReportCriteria build() {
            logger.debug(String.format("Building BackingCriteria with locale %s", locale));
            List<DriverVehicleScoreWrapper> resultsList = new ArrayList<DriverVehicleScoreWrapper>();
            if (timeFrame != null && !timeFrame.equals(TimeFrame.CUSTOM_RANGE))  {
                resultsList = groupReportDAO.getDriverScores(groupID, timeFrame.getInterval(), groupHierarchy);

            }
            else{
                resultsList = groupReportDAO.getDriverScores(groupID, interval, groupHierarchy);

            }


            List<BackingReportCriteria.BackingWrapper> backingWrappers = new ArrayList<BackingReportCriteria.BackingWrapper>();
            for (DriverVehicleScoreWrapper dvsw : resultsList) {
                Score s = dvsw.getScore();
                Integer totalMiles = s.getOdometer6() == null ? 0 : s.getOdometer6().intValue();
                boolean includeThisInactiveDriver = (includeInactiveDrivers && totalMiles != 0);
                boolean includeThisZeroMilesDriver = (includeZeroMilesDrivers && dvsw.getDriver().getStatus().equals(Status.ACTIVE));
                if ((dvsw.getDriver().getStatus().equals(Status.ACTIVE) && totalMiles != 0) || (includeInactiveDrivers && includeZeroMilesDrivers) || includeThisInactiveDriver
                        || includeThisZeroMilesDriver) {
                    String driverName = dvsw.getDriver().getPerson().getLast() + ", " + dvsw.getDriver().getPerson().getFirst();

                    Score score = dvsw.getScore();
                    // float seatbelt = ((dvsw.getScore().getSeatbelt() != null) ? dvsw.getScore().getSeatbelt().intValue() : 0) / 10.0F;
                    int driveMiles = dvsw.getScore().getOdometer6().intValue();
                    String groupName = groupHierarchy.getFullGroupName(dvsw.getDriver().getGroupID());

                    BackingReportCriteria.BackingWrapper backingWrapper = new BackingReportCriteria.BackingWrapper(driverName, score.getTrips().intValue(), score.getBackingEvents().intValue(),
                            driveMiles, score.getBackingTime().intValue(), groupName);
                    backingWrappers.add(backingWrapper);
                }
            }
            Collections.sort(backingWrappers);

            BackingReportCriteria criteria = new BackingReportCriteria(this.locale);
            criteria.setMainDataset(backingWrappers);

            if (timeFrame != null && !timeFrame.equals(TimeFrame.CUSTOM_RANGE))  {
            criteria.addDateParameter(REPORT_START_DATE, timeFrame.getInterval().getStart().toDate(), this.dateTimeZone.toTimeZone());

            /* The interval returns for the end date the beginning of the next day. We minus a second to get the previous day */
            criteria.addDateParameter(REPORT_END_DATE, timeFrame.getInterval().getEnd().minusSeconds(1).toDate(), this.dateTimeZone.toTimeZone());
            } else {
                criteria.addDateParameter(REPORT_START_DATE, interval.getStart().toDate(), this.dateTimeZone.toTimeZone());
                criteria.addDateParameter(REPORT_END_DATE, interval.getEnd().toDate(), this.dateTimeZone.toTimeZone());

            }

            criteria.setUseMetric(measurementType == MeasurementType.METRIC);
            return criteria;

        }

        public Boolean getIncludeInactiveDrivers() {
            return includeInactiveDrivers;
        }

        public void setIncludeInactiveDrivers(Boolean includeInactiveDrivers) {
            this.includeInactiveDrivers = includeInactiveDrivers;
        }

        public Boolean getIncludeZeroMilesDrivers() {
            return includeZeroMilesDrivers;
        }

        public void setIncludeZeroMilesDrivers(Boolean includeZeroMilesDrivers) {
            this.includeZeroMilesDrivers = includeZeroMilesDrivers;
        }
    }

    public static class BackingWrapper implements Comparable<BackingWrapper> {

        private String groupPath;
        private String driverName;
        private Integer trips;
        private Integer drivingMiles;
        private Integer backupEvents;
        private Integer backupTime;

        public BackingWrapper(String driverName, Integer trips, Integer backupEvents, Integer drivingMiles, Integer backupTime, String groupPath) {
            this.driverName = driverName;
            this.trips = trips;
            this.backupEvents = backupEvents;
            this.drivingMiles = drivingMiles;
            this.backupTime = backupTime;
            this.groupPath = groupPath;
        }

        public String getGroupPath() {
            return groupPath;
        }

        public void setGroupPath(String groupPath) {
            this.groupPath = groupPath;
        }

        public Integer getTrips() {
            return trips;
        }

        public void setTrips(Integer trips) {
            this.trips = trips;
        }

        public Integer getDrivingMiles() {
            return drivingMiles;
        }

        public void setDrivingMiles(Integer drivingMiles) {
            this.drivingMiles = drivingMiles;
        }

        @Override
        public int compareTo(BackingWrapper arg0) {
            return this.getGroupPath().compareTo(arg0.getGroupPath());
        }

        public String getDriverName() {
            return driverName;
        }

        public void setDriverName(String driverName) {
            this.driverName = driverName;
        }

        public Integer getBackupEvents() {
            return backupEvents;
        }

        public void setBackupEvents(Integer backupEvents) {
            this.backupEvents = backupEvents;
        }

        public Integer getBackupTime() {
            return backupTime;
        }

        public void setBackupTime(Integer backupTime) {
            this.backupTime = backupTime;
        }


    }
}
