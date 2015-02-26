package com.inthinc.pro.reports.performance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.joda.time.DateTimeZone;

import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.dao.DriveTimeDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.dao.report.MaintenanceReportsDAO;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.MaintenanceReportItem;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.configurator.MaintenanceSettings;
import com.inthinc.pro.model.configurator.SettingType;
import com.inthinc.pro.model.configurator.VehicleSetting;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;

public class MaintenanceIntervalReportCriteria extends ReportCriteria {
    
    private static Logger logger = Logger.getLogger(MaintenanceIntervalReportCriteria.class);
    
    private MaintenanceIntervalReportCriteria(Locale locale) {
        super(ReportType.VEHICLE_MAINTENANCE_INTERVAL_REPORT, null, locale);
        logger.debug(String.format("Creating MaintenanceIntervalReportCriteria with locale %s", locale.toString()));
    }
    
    public static class Builder {
        
        private final int DISTANCE_MARGIN = 250;
        private final int HOUR_MARGIN = 250;
        
        private Locale locale;
        
        private DateTimeZone dateTimeZone;
        private Integer groupID;
        private List<Integer> groupIDList;
        private MeasurementType measurementType;
        
        private GroupReportDAO groupReportDAO;
        private VehicleDAO vehicleDAO;
        private EventDAO eventDAO;
        private GroupDAO groupDAO;
        private ConfiguratorDAO configuratorJDBCDAO;
        private DriveTimeDAO driveTimeDAO;
        private MaintenanceReportsDAO maintenanceReportsDAO;
        
        private GroupHierarchy groupHierarchy;
        private Boolean includeInactiveDrivers;
        private Boolean includeZeroMilesDrivers;
        
        private DataSource dataSource;
        
        public Builder(GroupHierarchy groupHierarchy, GroupReportDAO groupReportDAO, GroupDAO groupDAO, VehicleDAO vehicleDAO, EventDAO eventDAO, List<Integer> groupIDList, MeasurementType measurementType,
                        ConfiguratorDAO configuratorDAO, DriveTimeDAO driveTimeDAO, MaintenanceReportsDAO maintenanceReportsDAO) {
            
            this.dateTimeZone = DateTimeZone.UTC;
            this.locale = Locale.US;
            this.groupHierarchy = groupHierarchy;
            this.groupReportDAO = groupReportDAO;
            this.measurementType = measurementType;
            this.vehicleDAO = vehicleDAO;
            this.eventDAO = eventDAO;
            this.groupIDList = groupIDList;
            this.groupDAO = groupDAO;
            this.configuratorJDBCDAO = configuratorDAO;
            this.driveTimeDAO = driveTimeDAO;
            this.maintenanceReportsDAO = maintenanceReportsDAO;
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
        
        private static Integer calcOverage(Integer start, Integer threshold, Integer actual) {
            start = (start != null) ? start : 0;
            threshold = (threshold != null && threshold != 0) ? threshold : 1; // no threshold is logically equivalent to threshold = 1
            actual = (actual != null) ? actual : 0;
            return (actual % threshold) - start;
        }
        
        public MaintenanceIntervalReportCriteria build() {
            logger.debug(String.format("Building MaintenanceIntervalReportCriteria with locale %s", locale));
            
            MaintenanceIntervalReportCriteria criteria = new MaintenanceIntervalReportCriteria(this.locale);
            List<BackingWrapper> backingWrappers = new ArrayList<BackingWrapper>();
            Set<Integer> groupHeirarchySet = new HashSet<Integer>();
            for(Integer groupID : groupIDList) {
                groupHeirarchySet.addAll(groupHierarchy.getGroupIDList(groupID));
            }
            List<Integer> completeGroupIDList = new ArrayList<Integer>();
            completeGroupIDList.addAll(groupHeirarchySet);
            // get vehicles with an odometer or engine hours threshold set
            // for each result add a row/entry to backingWrappers (if there are two (one each odo and hours) add TWO rows...)
            List<MaintenanceReportItem> reportItems = maintenanceReportsDAO.findVehiclesWithThreshold(completeGroupIDList);

            List<Integer> vehicleIDs = new ArrayList<Integer>();
            for (MaintenanceReportItem item : reportItems) {
                vehicleIDs.add(item.getVehicleID());
            }
            
            Map<Integer, Integer> baseOdometerMap = maintenanceReportsDAO.findBaseOdometer(vehicleIDs);
            
            // from/for that list of vehicles determine the latest hours?
            Map<Integer, MaintenanceReportItem> engineHoursMap;
            if (vehicleIDs != null && !vehicleIDs.isEmpty()) {
                engineHoursMap = maintenanceReportsDAO.findEngineHours(vehicleIDs);
            } else {
                engineHoursMap = Collections.emptyMap();
            }
            for (MaintenanceReportItem item : reportItems) {
                System.out.println("item: "+item);
                MaintenanceReportItem justOdoAndHours = engineHoursMap.get(item.getVehicleID());
                if(justOdoAndHours != null) {
                    if(justOdoAndHours.getVehicleEngineHours() != null && justOdoAndHours.getVehicleEngineHours() > 0) {
                        item.setVehicleEngineHours(justOdoAndHours.getVehicleEngineHours());
                    }
                    if(item.getVehicleEngineHours() == null) {
                        item.setVehicleEngineHours(0);
                    }
                    if(justOdoAndHours.getVehicleOdometer() != null && justOdoAndHours.getVehicleOdometer() > 0) {
                        item.setVehicleOdometer(justOdoAndHours.getVehicleOdometer());
                    }
                }
                Integer baseOdometer = baseOdometerMap.get(item.getVehicleID());
                baseOdometer = (baseOdometer!=null)?baseOdometer:0;
                Integer distanceOver = calcOverage(baseOdometer, item.getThresholdOdo(), item.getVehicleOdometer());
                Integer baseHours = 0;
                Integer hoursOver = calcOverage(baseHours, item.getThresholdHours(), item.getVehicleEngineHours());
                if(((hoursOver < 0 && hoursOver > - HOUR_MARGIN) || (distanceOver < 0 && distanceOver > - DISTANCE_MARGIN)) && (item.getThresholdHours() != null || item.getThresholdOdo() != null)) {
                    backingWrappers.add(new BackingWrapper(item.getVehicleName(), item.getYmmString(), baseOdometer, item.getThresholdOdo(), item.getVehicleOdometer(), distanceOver, baseHours, item.getThresholdHours(),
                                item.getVehicleEngineHours(), hoursOver, item.getGroupName()));
                }
            }
            for(BackingWrapper backingWrapper: backingWrappers) {
                System.out.println(backingWrapper.toString());
            }
            
            criteria.setMainDataset(backingWrappers);
            // criteria.addDateParameter(REPORT_START_DATE,interval.getStart().toDate(), this.dateTimeZone.toTimeZone());
            
            /* The interval returns for the end date the beginning of the next day. We minus a second to get the previous day */
            // criteria.addDateParameter(REPORT_END_DATE, interval.getEnd().minusSeconds(1).toDate(), this.dateTimeZone.toTimeZone());
            criteria.setUseMetric(measurementType == MeasurementType.METRIC);
            // NOT DONE WITH CRIT YET
            return criteria;
        }
        
        public MaintenanceIntervalReportCriteria buildOLD() {
            logger.debug(String.format("Building MaintenanceIntervalReportCriteria with locale %s", locale));
            
            List<BackingWrapper> backingWrappers = new ArrayList<BackingWrapper>();
            
            List<Vehicle> allVehicles = new ArrayList<Vehicle>();
            
            for (Integer id : groupIDList) {
                allVehicles.addAll(vehicleDAO.getVehiclesInGroupHierarchy(id));
            }
            List<Integer> allVehicleIDsIntegers = new ArrayList<Integer>();
            for (Vehicle vehicle : allVehicles) {
                allVehicleIDsIntegers.add(vehicle.getVehicleID());
            }
            Map<Integer, Group> knownGroupsMap = new HashMap<Integer, Group>();
            Map<Integer, VehicleSetting> vehicleSettingMap = configuratorJDBCDAO.getVehicleSettingsForAll(allVehicleIDsIntegers);
            for (Vehicle vehicle : allVehicles) {
                // VehicleSetting vehicleSetting = configuratorJDBCDAO.getVehicleSettings(vehicle.getVehicleID());
                VehicleSetting vehicleSetting = vehicleSettingMap.get(vehicle.getVehicleID());
                Integer loopGroupID = vehicle.getGroupID();
                if (!knownGroupsMap.containsKey(loopGroupID)) {
                    knownGroupsMap.put(loopGroupID, groupDAO.findByID(loopGroupID));
                }
                String groupName = knownGroupsMap.get(loopGroupID).getName();
                String vehicleYMM = vehicle.getYear() + " " + vehicle.getMake() + " " + vehicle.getModel();
                Long milesDriven = driveTimeDAO.getDriveOdometerSum(vehicle) / 100;
                
                // distance over
                Integer maintByDistanceStart = stringToInt(vehicleSetting.getActual().get(MaintenanceSettings.MAINT_BY_DIST_START.getCode()));
                Integer maintByDistanceInterval = stringToInt(vehicleSetting.getActual().get(MaintenanceSettings.MAINT_BY_DIST_INTERVAL.getCode()));
                Integer odometer = vehicle.getOdometer();
                Integer distanceOver = calcDistanceOver(maintByDistanceStart, maintByDistanceInterval, odometer);
                
                // hours over
                Integer baseHours = 0; // Manually setting this to zero for now, as we're not currently allowing engine hours to start from an arbitrary value
                Long driveTime = driveTimeDAO.getDriveTimeSum(vehicle) / 3600;
                Integer hours = driveTime.intValue();
                Integer intervalHours = stringToInt(vehicleSetting.getActual().get(MaintenanceSettings.MAINT_BY_ENGINE_HOURS_INTERVAL.getCode()));
                Integer hoursOver = calcHoursOver(baseHours, hours, intervalHours);
                
                int distanceInterval;
                int hourInterval;
                if (distanceOver != null || hoursOver != null) {
                    if (distanceOver == null) {
                        hourInterval = hoursOver;
                        if (hourInterval < 0 && Math.abs(hourInterval) < HOUR_MARGIN) {
                            BackingWrapper backingWrapper = new BackingWrapper(vehicle.getName(), vehicleYMM, maintByDistanceStart, maintByDistanceInterval, odometer, 0, baseHours, intervalHours,
                                            hours, hoursOver, groupName);
                            
                            backingWrappers.add(backingWrapper);
                        }
                    } else if (hoursOver == null) {
                        distanceInterval = distanceOver;
                        if (distanceInterval < 0 && Math.abs(distanceInterval) < DISTANCE_MARGIN) {
                            BackingWrapper backingWrapper = new BackingWrapper(vehicle.getName(), vehicleYMM, maintByDistanceStart, maintByDistanceInterval, odometer, distanceOver, baseHours,
                                            intervalHours, hours, 0, groupName);
                            
                            backingWrappers.add(backingWrapper);
                        }
                    } else {
                        distanceInterval = distanceOver;
                        hourInterval = hoursOver;
                        if ((distanceInterval < 0 && Math.abs(distanceInterval) < DISTANCE_MARGIN) || (hourInterval < 0 && Math.abs(hourInterval) < HOUR_MARGIN)) {
                            BackingWrapper backingWrapper = new BackingWrapper(vehicle.getName(), vehicleYMM, maintByDistanceStart, maintByDistanceInterval, odometer, distanceOver, baseHours,
                                            intervalHours, hours, hoursOver, groupName);
                            
                            backingWrappers.add(backingWrapper);
                        }
                    }
                }
            }
            
            MaintenanceIntervalReportCriteria criteria = new MaintenanceIntervalReportCriteria(this.locale);
            criteria.setMainDataset(backingWrappers);
            // criteria.addDateParameter(REPORT_START_DATE,interval.getStart().toDate(), this.dateTimeZone.toTimeZone());
            
            /* The interval returns for the end date the beginning of the next day. We minus a second to get the previous day */
            // criteria.addDateParameter(REPORT_END_DATE, interval.getEnd().minusSeconds(1).toDate(), this.dateTimeZone.toTimeZone());
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
        
        public MaintenanceReportsDAO getMaintenanceReportsDAO() {
            return maintenanceReportsDAO;
        }
        
        public void setMaintenanceReportsDAO(MaintenanceReportsDAO maintenanceReportsDAO) {
            this.maintenanceReportsDAO = maintenanceReportsDAO;
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
        
        public BackingWrapper(String vehicleName, String vehicleYMM, Integer baseOdometer, Integer intervalOdometer, Integer odometer, Integer distanceOver, Integer baseHours, Integer intervalHours,
                        Integer hours, Integer hoursOver, String groupPath) {
            this.vehicleID = vehicleName + "";
            this.vehicleYMM = vehicleYMM;
            this.baseOdometer = baseOdometer + "";
            this.intervalOdometer = intervalOdometer + "";
            this.odometer = odometer + "";
            this.distanceOver = distanceOver + "";
            this.baseHours = baseHours + "";
            this.intervalHours = intervalHours + "";
            this.hours = hours + "";
            this.hoursOver = hoursOver + "";
            this.groupPath = groupPath;
        }
        
        public BackingWrapper(String vehicleName, String vehicleYMM, String baseOdometer, String intervalOdometer, String odometer, String distanceOver, String baseHours, String intervalHours,
                        String hours, String hoursOver, String groupPath) {
            
            this.vehicleID = vehicleName;
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
    
    public static Integer calcDistanceOver(Integer startOdometer, Integer intervalOdometer, Integer actualOdometer) {
        Integer distanceOver;
        if (actualOdometer == null || intervalOdometer == null || intervalOdometer == 0) {
            return null;
        }
        if (startOdometer == null) {
            startOdometer = 0;
        }
        Integer firstIntervalEnd = (startOdometer + intervalOdometer);
        if (firstIntervalEnd < actualOdometer) {
            distanceOver = actualOdometer % firstIntervalEnd;
        } else {
            distanceOver = actualOdometer - firstIntervalEnd;
        }
        return distanceOver;
    }
    
    /**
     * Calculate calcHours = hours (drive time) driven minus the base hours (existing) divided by maintenance interval hours. Currently base hours start from 0 so the formula is simplified to hours =
     * drive time % maintenance interval hours.
     * 
     * @param baseHours
     *            base (existing) hours
     * @param hours
     *            hours (drive time)
     * @param intervalHours
     *            maintenance interval hours
     * @return calculated hours
     */
    public static Integer calcHoursOver(Integer baseHours, Integer hours, Integer intervalHours) {
        Integer hoursOver;
        
        int hour = 0;
        if (intervalHours == null || intervalHours == 0) {
            hoursOver = 0;
        } else {
            if (baseHours != null && intervalHours != null && hours != null) {
                hour = (hours - baseHours) % intervalHours;
            }
            
            if (baseHours == null || intervalHours == null) {
                hoursOver = null;
            } else if (hour > (intervalHours / 2)) {
                int hourInt = hour - intervalHours;
                hoursOver = hourInt;
            } else {
                hoursOver = hour;
            }
        }
        return hoursOver;
    }
}
