package com.inthinc.pro.reports.performance;

import java.util.*;

import com.inthinc.pro.dao.*;
import com.inthinc.pro.model.*;
import com.inthinc.pro.model.configurator.MaintenanceSettings;
import com.inthinc.pro.model.event.*;
import org.apache.log4j.Logger;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;

public class MaintenanceEventsReportCriteria extends ReportCriteria {

    private static Logger logger = Logger.getLogger(MaintenanceEventsReportCriteria.class);

    private MaintenanceEventsReportCriteria(Locale locale) {
        super(ReportType.VEHICLE_MAINTENANCE_EVENTS_REPORT, null, locale);
        logger.debug(String.format("Creating MaintenanceEventsReportCriteria with locale %s", locale.toString()));
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

        private ConfiguratorDAO configuratorJDBCDAO;

        private DriveTimeDAO driveTimeDAO;

        public Builder(GroupHierarchy groupHierarchy, GroupReportDAO groupReportDAO, GroupDAO groupDAO, VehicleDAO vehicleDAO, EventDAO eventDAO, List<Integer> groupIDList, Interval interval, MeasurementType measurementType,
                       ConfiguratorDAO configuratorDAO, DriveTimeDAO driveTimeDAO) {

            this.groupIDList = groupIDList;
            this.dateTimeZone = DateTimeZone.UTC;
            this.locale = Locale.US;
            this.groupHierarchy = groupHierarchy;
            this.groupReportDAO = groupReportDAO;
            this.measurementType = measurementType;
            this.vehicleDAO = vehicleDAO;
            this.eventDAO = eventDAO;
            this.interval = interval;
            this.groupDAO = groupDAO;
            this.driveTimeDAO = driveTimeDAO;
            this.configuratorJDBCDAO = configuratorDAO;
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

        public GroupDAO getGroupDAO() {
            return groupDAO;
        }

        public void setGroupDAO(GroupDAO groupDAO) {
            this.groupDAO = groupDAO;
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

        public MaintenanceEventsReportCriteria build() {
            logger.debug(String.format("Building MaintenanceEventsReportCriteria with locale %s", locale));

            List<NoteType> searchNoteType = new ArrayList<NoteType>();
            searchNoteType.add(NoteType.MAINTENANCE_EVENTS);
            searchNoteType.add(NoteType.IGNITION_OFF);
            searchNoteType.add(NoteType.CLEAR_DRIVER);
            searchNoteType.add(NoteType.FULLEVENT);
            searchNoteType.add(NoteType.ROLLOVER);
            searchNoteType.add(NoteType.UNPLUGGED);

            List<Vehicle> allVehicles = new ArrayList<Vehicle>();
            for (Integer id: groupIDList){
                allVehicles.addAll(vehicleDAO.getVehiclesInGroupHierarchy(id));
            }

            List<Vehicle> vehiclesWithEvents = new ArrayList<Vehicle>();
            Map<Integer, List<Event>> foundEvents = new HashMap<Integer, List<Event>>();

            for (Vehicle vehicle: allVehicles){
                List<Event> events = eventDAO.getEventsForVehicle(vehicle.getVehicleID(), interval.getStart().toDate(), interval.getEnd().toDate(), searchNoteType, 0);
                if (events != null && !events.isEmpty()){
                    vehiclesWithEvents.add(vehicle);
                    foundEvents.put(vehicle.getVehicleID(), events);
                }
            }

            List<BackingWrapper> backingWrappers = new ArrayList<BackingWrapper>();

            for (Vehicle vehicle: vehiclesWithEvents){

                List<Event> eventList = foundEvents.get(vehicle.getVehicleID());
                if (eventList != null) {
                    for (Event event : eventList){

                        int noteCod = event.getType().getCode();
                        int evCode = 0;
                        Integer groupId=vehicle.getGroupID();
                        Group group = groupDAO.findByID(groupId);
                        String groupName = group.getName();
                        String vehicleID=event.getVehicleID().toString();
                        String vehicleYMM=vehicle.getYear() + " " + vehicle.getMake() + " " + vehicle.getModel();
                        String maintenanceEvent=event.getEventType().toString();
                        Date date=event.getTime();

                        MaintenanceSettings maintenanceSettings = null;
                        if(event instanceof MaintenanceEvent && ((MaintenanceEvent)event).getOilPresure() != null){
                            maintenanceSettings = MaintenanceSettings.SET_OIL_PRESSURE;
                            evCode = EventAttr.ATTR_OIL_PRESSURE.getCode();
                        }else if (event instanceof MaintenanceEvent && ((MaintenanceEvent)event).getBatteryVoltage() != null){
                            maintenanceSettings = MaintenanceSettings.SET_BATT_VOLTAGE;
                            evCode = EventAttr.ATTR_BATTERY_VOLTAGE.getCode();
                        }else if (event instanceof MaintenanceEvent && ((MaintenanceEvent)event).getEngineTemp() != null){
                            maintenanceSettings = MaintenanceSettings.SET_ENGINE_TEMP;
                            evCode = EventAttr.ATTR_ENGINE_TEMP.getCode();
                        }else if(event instanceof MaintenanceEvent && ((MaintenanceEvent)event).getTransmissionTemp() != null){
                            maintenanceSettings = MaintenanceSettings.SET_TRANS_TEMP;
                            evCode = EventAttr.ATTR_TRANSMISSION_TEMP.getCode();
                        }else if(event instanceof MaintenanceEvent && ((MaintenanceEvent)event).getDpfFlowRate() != null){
                            maintenanceSettings = MaintenanceSettings.DPF_FLOW_RATE;
                            evCode = EventAttr.ATTR_DPF_FLOW_RATE.getCode();
                        }
                        else if(event instanceof IgnitionOffMaintenanceEvent && ((IgnitionOffMaintenanceEvent)event).getMalfunctionIndicatorLamp() != null){
                            evCode = EventAttr.ATTR_MALFUNCTION_INDICATOR_LAMP.getCode();
                        }
                        else if(event instanceof IgnitionOffMaintenanceEvent && ((IgnitionOffMaintenanceEvent)event).getCheckEngine() != null){
                            evCode = EventAttr.ATTR_CHECK_ENGINE.getCode();
                        }
                        else if(event instanceof MaintenanceEvent && ((MaintenanceEvent)event).getEngineHours() != null){
                            evCode = EventAttr.ENGINE_HOURS_X100.getCode();
                        }
                        else if(event instanceof MaintenanceEvent && ((MaintenanceEvent)event).getOdometer() != null){
                            maintenanceSettings=null;
                            evCode = EventAttr.ODOMETER.getCode();
                        }

                        String value = null;
                        String actual = null;
                        if(maintenanceSettings != null) {
                            value = ((MaintenanceEvent) event).getValue();
                            actual = configuratorJDBCDAO.getVehicleSettings(stringToInt(vehicleID)).getActual().get(maintenanceSettings.getCode());
                        }else{
                            value = "-";
                            actual = "-";
                        }
                        //take previous threshold same type
                        Date prevEventDate = driveTimeDAO.getPrevEventDate(vehicle, noteCod, evCode, date, event.getDeviceID());

                        //calculate odometer values
                        String odometer = (driveTimeDAO.getDriveOdometerAtDate(vehicle, date) / 100) + "";
                        String odometerLastEvent = (driveTimeDAO.getDriveOdometerAtDate(vehicle, prevEventDate) / 100) + "";
                        String distanceSince;
                        try{
                            distanceSince = stringToInt(odometer) - stringToInt(odometerLastEvent) + "";
                        } catch(NumberFormatException e) {
                            distanceSince = "N/A";
                        }

                        //calculate engine hours
                        String engineHours = driveTimeDAO.getEngineHoursAtDate(vehicle,date) / 3600 + "";
                        String engineHoursLastEvent = driveTimeDAO.getEngineHoursAtDate(vehicle, prevEventDate) / 3600 + "";
                        String hoursSince=stringToInt(engineHours) - stringToInt(engineHoursLastEvent) + "";
                        vehicleID=vehicle.getName();
                        BackingWrapper backingWrapper = new BackingWrapper(vehicleID, vehicleYMM, maintenanceEvent, date, value, actual,
                                odometer, distanceSince, engineHours, hoursSince, groupName);

                        backingWrappers.add(backingWrapper);
                    }
                }
            }

            MaintenanceEventsReportCriteria criteria = new MaintenanceEventsReportCriteria(this.locale);
            criteria.setMainDataset(backingWrappers);
            criteria.addDateParameter(REPORT_START_DATE,interval.getStart().toDate(), this.dateTimeZone.toTimeZone());

            /* The interval returns for the end date the beginning of the next day. We minus a second to get the previous day */
            criteria.addDateParameter(REPORT_END_DATE, interval.getEnd().minusSeconds(1).toDate(), this.dateTimeZone.toTimeZone());
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
        private String maintenanceEvent;
        private Date date;
        private String value;
        private String actual;
        private String odometer;
        private String distanceSince;
        private String engineHours;
        private String hoursSince;

        public BackingWrapper(String vehicleID, String vehicleYMM, String maintenanceEvent, Date date, String value,String actual, String odometer, String distanceSince,
                              String engineHours, String hoursSince, String groupPath) {
            this.vehicleID = vehicleID;
            this.vehicleYMM = vehicleYMM;
            this.maintenanceEvent = maintenanceEvent;
            this.date = date;
            this.value = value;
            this.actual = actual;
            this.odometer = odometer;
            this.distanceSince = distanceSince;
            this.engineHours = engineHours;
            this.hoursSince = hoursSince;
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

        public String getMaintenanceEvent() {
            return maintenanceEvent;
        }

        public void setMaintenanceEvent(String maintenanceEvent) {
            this.maintenanceEvent = maintenanceEvent;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getActual() {
            return actual;
        }

        public void setActual(String actual) {
            this.actual = actual;
        }

        public String getOdometer() {
            return odometer;
        }

        public void setOdometer(String odometer) {
            this.odometer = odometer;
        }

        public String getDistanceSince() {
            return distanceSince;
        }

        public void setDistanceSince(String distanceSince) {
            this.distanceSince = distanceSince;
        }

        public String getEngineHours() {
            return engineHours;
        }

        public void setEngineHours(String engineHours) {
            this.engineHours = engineHours;
        }

        public String getHoursSince() {
            return hoursSince;
        }

        public void setHoursSince(String hoursSince) {
            this.hoursSince = hoursSince;
        }

    }

}
