package com.inthinc.pro.reports.performance;

import java.util.*;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.*;
import com.inthinc.pro.dao.jdbc.GenericJDBCDAO;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.aggregation.DriveTimeRecord;
import com.inthinc.pro.model.aggregation.VehiclePerformance;
import com.inthinc.pro.model.configurator.MaintenanceSettings;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventAttr;
import com.inthinc.pro.model.event.NoteType;
import org.apache.log4j.Logger;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;

import com.inthinc.pro.model.configurator.VehicleSetting;


import javax.sql.DataSource;

public class MaintenanceIntervalReportCriteria extends ReportCriteria {

    private static Logger logger = Logger.getLogger(MaintenanceIntervalReportCriteria.class);

    private MaintenanceIntervalReportCriteria(Locale locale) {
        super(ReportType.VEHICLE_MAINTENANCE_INTERVAL_REPORT, null, locale);
        logger.debug(String.format("Creating MaintenanceIntervalReportCriteria with locale %s", locale.toString()));
    }

    public static class Builder {

        private Locale locale;

        private DateTimeZone dateTimeZone;

        private Integer groupID;

        private List<Integer> groupIDList;

        private MeasurementType measurementType;

        private GroupReportDAO groupReportDAO;

        private VehicleDAO vehicleDAO;

        private EventDAO eventDAO;

        private GroupHierarchy groupHierarchy;

        private Boolean includeInactiveDrivers;

        private Boolean includeZeroMilesDrivers;

        private Interval interval;

        private GroupDAO groupDAO;

        private Integer vehicleID;

        private ConfiguratorDAO configuratorJDBCDAO;

        private Map<Integer, String> actual;

        private DataSource dataSource;

        private DriveTimeDAO driveTimeDAO;

        public Builder(GroupHierarchy groupHierarchy, GroupReportDAO groupReportDAO, GroupDAO groupDAO, VehicleDAO vehicleDAO, EventDAO eventDAO, List<Integer> groupIDList, Interval interval, MeasurementType measurementType,
                       ConfiguratorDAO configuratorDAO, DriveTimeDAO driveTimeDAO) {

            this.dateTimeZone = DateTimeZone.UTC;
            this.locale = Locale.US;
            this.groupHierarchy = groupHierarchy;
            this.groupReportDAO = groupReportDAO;
            this.measurementType = measurementType;
            this.vehicleDAO = vehicleDAO;
            this.eventDAO = eventDAO;
            this.interval = interval;
            this.groupIDList = groupIDList;
            this.groupDAO = groupDAO;
            this.configuratorJDBCDAO = configuratorDAO;
            this.driveTimeDAO = driveTimeDAO;
        }
        public DataSource getDataSource() {
            return dataSource;
        }

        public void setDataSource(DataSource dataSource) {
            this.dataSource = dataSource;
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

        public List<Integer> getGroupIDList() {
            return groupIDList;
        }

        public GroupReportDAO getGroupReportDAO() {
            return groupReportDAO;
        }

        public GroupHierarchy getGroupHierarchy() {
            return groupHierarchy;
        }

        public VehicleDAO getVehicleDAO() {
            return vehicleDAO;
        }

        public void setVehicleDAO(VehicleDAO vehicleDAO) {
            this.vehicleDAO = vehicleDAO;
        }

        public EventDAO getEventDAO() {
            return eventDAO;
        }

        public void setEventDAO(EventDAO eventDAO) {
            this.eventDAO = eventDAO;
        }

        public GroupDAO getGroupDAO() {
            return groupDAO;
        }

        public void setGroupDAO(GroupDAO groupDAO) {
            this.groupDAO = groupDAO;
        }

        public ConfiguratorDAO getConfiguratorJDBCDAO() {
            return configuratorJDBCDAO;
        }

        public void setConfiguratorJDBCDAO(ConfiguratorDAO configuratorDAO) {
            this.configuratorJDBCDAO = configuratorDAO;
        }

        public DriveTimeDAO getDriveTimeDAO() {
            return driveTimeDAO;
        }

        public void setDriveTimeDAO(DriveTimeDAO driveTimeDAO) {
            this.driveTimeDAO = driveTimeDAO;
        }

        public MaintenanceIntervalReportCriteria build() {
            logger.debug(String.format("Building MaintenanceIntervalReportCriteria with locale %s", locale));

            List<BackingWrapper> backingWrappers = new ArrayList<BackingWrapper>();

            List<Vehicle> allVehicles = new ArrayList<Vehicle>();

            for (Integer id: groupIDList){
                allVehicles.addAll(vehicleDAO.getVehiclesInGroup(id));
            }

            for (Vehicle vehicle: allVehicles){

                VehicleSetting vehicleSetting = configuratorJDBCDAO.getVehicleSettings(vehicle.getVehicleID());
                Integer groupId = vehicle.getGroupID();
                Group group = groupDAO.findByID(groupId);
                String groupName = group.getName();
                String vehicleID = vehicle.getVehicleID().toString();
                String vehicleYMM = vehicle.getYear() + " " + vehicle.getMake() + " " + vehicle.getModel();
                String baseOdometer = vehicleSetting.getActual().get(MaintenanceSettings.MAINT_BY_DIST_START.getCode());
                String intervalOdometer = vehicleSetting.getActual().get(MaintenanceSettings.MAINT_BY_DIST_INTERVAL.getCode());
                String odometer = driveTimeDAO.getDriveOdometerSum(vehicle) + "";
                String distanceOver;

                int distance = 0;
                if(baseOdometer != null && intervalOdometer != null) {
                    distance = Integer.valueOf(odometer) - Integer.valueOf(baseOdometer);
                    while(distance > Integer.valueOf(intervalOdometer)) {
                        distance = distance - Integer.valueOf(intervalOdometer);
                    }
                }

                if(baseOdometer == null || intervalOdometer == null){
                    distanceOver = null;
                }else if(distance > (Integer.valueOf(intervalOdometer) / 2)){
                    int distanceInt = distance - Integer.valueOf(intervalOdometer);
                    distanceOver = distanceInt + "";
                }else distanceOver = distance + "";

                String baseHours = vehicleSetting.getActual().get(MaintenanceSettings.MAINT_BY_ENGINE_HOURS_START.getCode());
                String intervalHours = vehicleSetting.getActual().get(MaintenanceSettings.MAINT_BY_ENGINE_HOURS_INTERVAL.getCode());
                String hours = null;
                Long driveTime = driveTimeDAO.getDriveTimeSum(vehicle) / 3600;
                if (driveTime == 0){
                    hours = null;
                }else hours = driveTime.toString();
                String hoursOver;

                int hour = 0;
                if(baseHours != null && intervalHours != null) {
                    hour = Integer.valueOf(hours) - Integer.valueOf(baseHours);
                    while(hour > Integer.valueOf(intervalHours)) {
                        hour = hour - Integer.valueOf(intervalHours);
                    }
                }

                if(baseHours == null || intervalHours == null){
                    hoursOver = null;
                }else if(hour > (Integer.valueOf(intervalHours) / 2)){
                    int hourInt = hour - Integer.valueOf(intervalHours);
                    hoursOver = hourInt + "";
                }else hoursOver = hour + "";

                int distanceInterval;
                int hourInterval;
                if(distanceOver != null || hoursOver != null){
                    if (distanceOver == null) {
                        hourInterval = Integer.valueOf(hoursOver);
                        if( (200 > hourInterval && hourInterval > -200)) {
                            BackingWrapper backingWrapper = new BackingWrapper(vehicleID, vehicleYMM, baseOdometer,
                                        intervalOdometer, odometer, distanceOver, baseHours, intervalHours, hours, hoursOver, groupName);

                            backingWrappers.add(backingWrapper);
                        }
                    }else if (hoursOver == null) {
                        distanceInterval = Integer.valueOf(distanceOver);
                        if( (250 > distanceInterval && distanceInterval > -250)) {
                             BackingWrapper backingWrapper = new BackingWrapper(vehicleID, vehicleYMM, baseOdometer,
                                        intervalOdometer, odometer, distanceOver, baseHours, intervalHours, hours, hoursOver, groupName);

                             backingWrappers.add(backingWrapper);
                        }
                    }else {
                        distanceInterval=Integer.valueOf(distanceOver);
                        hourInterval=Integer.valueOf(hoursOver);
                        if( (250 > distanceInterval && distanceInterval > -250) || (200 > hourInterval && hourInterval > -200)) {
                             BackingWrapper backingWrapper = new BackingWrapper(vehicleID, vehicleYMM, baseOdometer,
                                        intervalOdometer, odometer, distanceOver, baseHours, intervalHours, hours, hoursOver, groupName);

                             backingWrappers.add(backingWrapper);
                        }
                    }
                }
            }

            MaintenanceIntervalReportCriteria criteria = new MaintenanceIntervalReportCriteria(this.locale);
            criteria.setMainDataset(backingWrappers);
            //criteria.addDateParameter(REPORT_START_DATE,interval.getStart().toDate(), this.dateTimeZone.toTimeZone());

            /* The interval returns for the end date the beginning of the next day. We minus a second to get the previous day */
            //criteria.addDateParameter(REPORT_END_DATE, interval.getEnd().minusSeconds(1).toDate(), this.dateTimeZone.toTimeZone());
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
        private String vehicleID;
        private String vehicleYMM;
        private String baseOdometer;
        private String intervalOdometer;
        private String odometer;
        private String distanceOver;
        private String baseHours;
        private String intervalHours;
        private String hours;
        private String hoursOver;

        public BackingWrapper(String vehicleID, String vehicleYMM, String baseOdometer, String intervalOdometer, String odometer, String distanceOver, String baseHours,
                              String intervalHours, String hours, String hoursOver, String groupPath) {

            this.vehicleID = vehicleID;
            this.vehicleYMM = vehicleYMM;
            this.baseOdometer = baseOdometer;
            this.intervalOdometer = intervalOdometer;
            this.odometer = odometer;
            this.distanceOver = distanceOver;
            this.baseHours = baseHours;
            this.intervalHours = intervalHours;
            this.hours = hours;
            this.hoursOver = hoursOver;
            this.groupPath = groupPath;
        }

        @Override
        public int compareTo(BackingWrapper arg0) {
            return this.getGroupPath().compareTo(arg0.getGroupPath());
        }

        public String getGroupPath() {
            return groupPath;
        }

        public void setGroupPath(String groupPath) {
            this.groupPath = groupPath;
        }

        public String getVehicleID() {
            return vehicleID;
        }

        public void setVehicleID(String vehicleID) {
            this.vehicleID = vehicleID;
        }

        public String getVehicleYMM() {
            return vehicleYMM;
        }

        public void setVehicleYMM(String vehicleYMM) {
            this.vehicleYMM = vehicleYMM;
        }

        public String getBaseOdometer() {
            return baseOdometer;
        }

        public void setBaseOdometer(String baseOdometer) {
            this.baseOdometer = baseOdometer;
        }

        public String getIntervalOdometer() {
            return intervalOdometer;
        }

        public void setIntervalOdometer(String intervalOdometer) {
            this.intervalOdometer = intervalOdometer;
        }

        public String getOdometer() {
            return odometer;
        }

        public void setOdometer(String odometer) {
            this.odometer = odometer;
        }

        public String getDistanceOver() {
            return distanceOver;
        }

        public void setDistanceOver(String distanceOver) {
            this.distanceOver = distanceOver;
        }

        public String getBaseHours() {
            return baseHours;
        }

        public void setBaseHours(String baseHours) {
            this.baseHours = baseHours;
        }

        public String getIntervalHours() {
            return intervalHours;
        }

        public void setIntervalHours(String intervalHours) {
            this.intervalHours = intervalHours;
        }

        public String getHours() {
            return hours;
        }

        public void setHours(String hours) {
            this.hours = hours;
        }

        public String getHoursOver() {
            return hoursOver;
        }

        public void setHoursOver(String hoursOver) {
            this.hoursOver = hoursOver;
        }

    }

}
