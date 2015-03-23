package com.inthinc.pro.reports.performance;

import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import org.apache.log4j.Logger;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class FirstMoveForwardReportCriteria extends ReportCriteria {

    private static Logger logger = Logger.getLogger(FirstMoveForwardReportCriteria.class);

    private FirstMoveForwardReportCriteria(Locale locale) {
        super(ReportType.FIRST_MOVE_FORWARD_REPORT, null, locale);
        logger.debug(String.format("Creating FirstMoveForwardReportCriteria with locale %s", locale.toString()));
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

        public FirstMoveForwardReportCriteria build() {
            logger.debug(String.format("Building FirstMoveForwardCriteria with locale %s", locale));
            List<DriverVehicleScoreWrapper> resultsList = new ArrayList<DriverVehicleScoreWrapper>();
            if (timeFrame != null && !timeFrame.equals(TimeFrame.CUSTOM_RANGE))  {
                resultsList = groupReportDAO.getDriverScores(groupID, timeFrame.getInterval(), groupHierarchy);

            }
            else{
                resultsList = groupReportDAO.getDriverScores(groupID, interval, groupHierarchy);

            }


            List<FirstMoveForwardReportCriteria.FirstMoveForwardWrapper> firstMoveForwardWrappers = new ArrayList<FirstMoveForwardReportCriteria.FirstMoveForwardWrapper>();
            for (DriverVehicleScoreWrapper dvsw : resultsList) {
                Score s = dvsw.getScore();
                Integer totalMiles = s.getOdometer6() == null ? 0 : s.getOdometer6().intValue();
                boolean includeThisInactiveDriver = (includeInactiveDrivers && totalMiles != 0);
                boolean includeThisZeroMilesDriver = (includeZeroMilesDrivers && dvsw.getDriver().getStatus().equals(Status.ACTIVE));
                if ((dvsw.getDriver().getStatus().equals(Status.ACTIVE) && totalMiles != 0) || (includeInactiveDrivers && includeZeroMilesDrivers) || includeThisInactiveDriver
                        || includeThisZeroMilesDriver) {
                    String driverName = dvsw.getDriver().getPerson().getLast() + ", " + dvsw.getDriver().getPerson().getFirst();
                    String license = dvsw.getDriver().getLicense();

                    Score score = dvsw.getScore();
                    // float seatbelt = ((dvsw.getScore().getSeatbelt() != null) ? dvsw.getScore().getSeatbelt().intValue() : 0) / 10.0F;
                    int driveMiles = dvsw.getScore().getOdometer6().intValue();
                    String groupName = groupHierarchy.getFullGroupName(dvsw.getDriver().getGroupID());

                    FirstMoveForwardReportCriteria.FirstMoveForwardWrapper firstMoveForwardWrapper = new FirstMoveForwardReportCriteria.FirstMoveForwardWrapper(driverName, score.getTrips().intValue(), score.getFirstMoveForwardEvents().intValue(),
                            driveMiles, score.getFirstMoveForwardTime().intValue(), groupName,license);
                    firstMoveForwardWrappers.add(firstMoveForwardWrapper);
                }
            }
            Collections.sort(firstMoveForwardWrappers);

            FirstMoveForwardReportCriteria criteria = new FirstMoveForwardReportCriteria(this.locale);
            criteria.setMainDataset(firstMoveForwardWrappers);

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

    public static class FirstMoveForwardWrapper implements Comparable<FirstMoveForwardWrapper> {

        private String groupPath;
        private String driverName;
        private Integer trips;
        private Integer drivingMiles;
        private Integer firstMoveForwardEvents;
        private Integer firstMoveForwardTime;
        private String license;

        public FirstMoveForwardWrapper(String driverName, Integer trips, Integer firstMoveForwardEvents, Integer drivingMiles, Integer firstMoveForwardTime, String groupPath, String license) {
            this.driverName = driverName;
            this.trips = trips;
            this.firstMoveForwardEvents = firstMoveForwardEvents;
            this.drivingMiles = drivingMiles;
            this.firstMoveForwardTime = firstMoveForwardTime;
            this.groupPath = groupPath;
            this.license=license;

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
        public int compareTo(FirstMoveForwardWrapper arg0) {
            return this.getGroupPath().compareTo(arg0.getGroupPath());
        }

        public String getDriverName() {
            return driverName;
        }

        public void setDriverName(String driverName) {
            this.driverName = driverName;
        }

        public Integer getFirstMoveForwardEvents() {
            return firstMoveForwardEvents;
        }

        public void setFirstMoveForwardEvents(Integer firstMoveForwardEvents) {
            this.firstMoveForwardEvents = firstMoveForwardEvents;
        }

        public Integer getFirstMoveForwardTime() {
            return firstMoveForwardTime;
        }

        public void setFirstMoveForwardTime(Integer firstMoveForwardTime) {
            this.firstMoveForwardTime = firstMoveForwardTime;
        }

        public String getLicense() {
            return license;
        }

        public void setLicense(String license) {
            this.license = license;
        }


    }
}
