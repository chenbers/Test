package com.inthinc.pro.reports.performance;

import java.util.*;

import com.inthinc.pro.dao.*;
import com.inthinc.pro.model.*;
import com.inthinc.pro.model.configurator.MaintenanceSettings;
import com.inthinc.pro.model.configurator.VehicleSetting;
import com.inthinc.pro.model.event.*;

import org.apache.log4j.Logger;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.dao.report.MaintenanceReportsDAO;
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
        private MaintenanceReportsDAO maintenanceReportsDAO;
        private GroupDAO groupDAO;
        private ConfiguratorDAO configuratorJDBCDAO;
        private DriveTimeDAO driveTimeDAO;
        
        private GroupHierarchy groupHierarchy;

        private Boolean includeInactiveDrivers;

        private Boolean includeZeroMilesDrivers;

        private Interval interval;



        public Builder(GroupHierarchy groupHierarchy, GroupReportDAO groupReportDAO, GroupDAO groupDAO, VehicleDAO vehicleDAO, EventDAO eventDAO, List<Integer> groupIDList, Interval interval, MeasurementType measurementType,
                       ConfiguratorDAO configuratorDAO, DriveTimeDAO driveTimeDAO, MaintenanceReportsDAO maintenanceReportsDAO) {

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
            this.maintenanceReportsDAO = maintenanceReportsDAO;
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
            List<BackingWrapper> backingWrappers = new ArrayList<BackingWrapper>();
            //filter by date range and groupIDs
            Set<Integer> groupHeirarchySet = new HashSet<Integer>();
            for(Integer groupID : groupIDList) {
                groupHeirarchySet.addAll(groupHierarchy.getGroupIDList(groupID));
            }
            groupIDList.clear();
            groupIDList.addAll(groupHeirarchySet);
            List<MaintenanceReportItem> events = maintenanceReportsDAO.findMaintenanceEventsByGroupIDs(groupIDList, interval.getStart().toDate(), interval.getEnd().toDate());
            
            if(events.size() > 0) {
                for (MaintenanceReportItem event : events) {
                    
                    String threshold = null;
                    
                    String distanceSince = "*";
                    if (event.getVehicleOdometer() != null && event.getEventOdometer() != null) {
                        distanceSince = (event.getVehicleOdometer() - event.getEventOdometer()) + "";
                    }
                    String hoursSince = "*";
                    if (event.getVehicleEngineHours() != null && event.getEventEngineHours() != null) {
                        hoursSince = (event.getVehicleEngineHours() - event.getEventEngineHours()) + "";
                    }
                    String eventValue = "";
                    String vehicleName = event.getVehicleName();
                    String vehicleYMM = event.getYmmString();
                    String maintenanceEvent = (event.getMaintenanceEventType() != null) ? event.getMaintenanceEventType().toString() : "unknown";
                    Date date = (event.getEventTime() != null) ? event.getEventTime().toDate() : null;
                    String odometer = event.getEventOdometer().toString();
                    String engineHours = event.getEventEngineHours().toString();
                    String groupPath = event.getGroupName();
                    System.out.println(""+event.getNoteID());
                    if (event.getEventDpfFlowRate() > 0) {
                        eventValue = event.getEventDpfFlowRate().toString();
                        threshold = event.getThresholdDpfFlowRate()+"";
                        maintenanceEvent = EventType.DPF_FLOW_RATE.toString();
                    } else if (event.getEventEngineHours() > 0) {
                        eventValue = event.getEventEngineHours().toString();
                        threshold = event.getThresholdHours()+"";
                        maintenanceEvent = MaintenanceEventType.ATTR_ENGINE_HOURS.toString();
                    } else if (event.getEventEngineTemp() > 0) {
                        eventValue = event.getEventEngineTemp().toString();
                        threshold = event.getThresholdEngineTemp()+"";
                        maintenanceEvent = MaintenanceEventType.ENGINE_TEMP.toString();
                    } else if (event.getEventOilPressure() > 0) {
                        eventValue = event.getEventOilPressure().toString();
                        System.out.println(""+event.getNoteID());
                        if(event.getThresholdOilPressure() == null) {
                            System.out.println("event with oil pressure but no oil pressure THRESHOLD??? "+event.getNoteID());
                        }
                        threshold = event.getThresholdOilPressure()+"";
                        maintenanceEvent = MaintenanceEventType.OIL_PRESSURE.toString();
                    } else if (event.getEventTransmissionTemp() > 0) {
                        eventValue = event.getEventTransmissionTemp().toString();
                        System.out.println(""+event.getNoteID());
                        if(event.getThresholdTransmissionTemp() == null) {
                            System.out.println("event with transmission temp but no transmission temp THRESHOLD??? "+event.getNoteID());
                        }
                        threshold = event.getThresholdTransmissionTemp()+"";
                        maintenanceEvent = MaintenanceEventType.TRANSMISSION_TEMP.toString();
                    } else if (event.getEventVoltage() > 0) {
                        eventValue = event.getEventVoltage().toString();
                        threshold = event.getThresholdVoltage()+"";
                        maintenanceEvent = MaintenanceEventType.BATTERY_VOLTAGE.toString();
                    }  else if (event.getEventOdometer() != null) {
                        eventValue = event.getEventOdometer().toString();
                        threshold = event.getThresholdOdo()+"";
                        maintenanceEvent = MaintenanceEventType.ODOMETER.toString();
                    }  else {
                        eventValue = "unknown";
                    }
                    BackingWrapper backingWrapper = new BackingWrapper(vehicleName + "nameTest", vehicleYMM, maintenanceEvent, date, eventValue, threshold, odometer, distanceSince, engineHours,
                                    hoursSince, groupPath, event.getNoteID());
                    System.out.println("backingWrapper: " + backingWrapper);
                    backingWrappers.add(backingWrapper);
                }
            }
            //
            MaintenanceEventsReportCriteria criteria = new MaintenanceEventsReportCriteria(this.locale);
            criteria.setMainDataset(backingWrappers);
            criteria.addDateParameter(REPORT_START_DATE,interval.getStart().toDate(), this.dateTimeZone.toTimeZone());

            /* The interval returns for the end date the beginning of the next day. We minus a second to get the previous day */
            criteria.addDateParameter(REPORT_END_DATE, interval.getEnd().minusSeconds(1).toDate(), this.dateTimeZone.toTimeZone());
            criteria.setUseMetric(measurementType == MeasurementType.METRIC);
            return criteria;
        }
        public MaintenanceEventsReportCriteria buildLoops() {
            logger.debug(String.format("Building MaintenanceEventsReportCriteria with locale %s", locale));

            List<NoteType> searchNoteType = new ArrayList<NoteType>();
            searchNoteType.add(NoteType.MAINTENANCE_EVENTS);
            searchNoteType.add(NoteType.IGNITION_OFF);
            searchNoteType.add(NoteType.CLEAR_DRIVER);
            searchNoteType.add(NoteType.FULLEVENT);
            searchNoteType.add(NoteType.ROLLOVER);
            searchNoteType.add(NoteType.UNPLUGGED);
            List<Event> events = new ArrayList<Event>();
            List<Vehicle> allVehicles = new ArrayList<Vehicle>();
            for (Integer id: groupIDList){
                allVehicles.addAll(vehicleDAO.getVehiclesInGroupHierarchy(id));
                events.addAll(eventDAO.getEventsForGroupFromVehicles(id, searchNoteType, interval.getStart().toDate(), interval.getEnd().toDate()));
            }

            List<Vehicle> vehiclesWithEvents = new ArrayList<Vehicle>();
            List<Integer> vehicleIDForVehiclesWithEvents = new ArrayList<Integer>();
            Map<Integer, List<Event>> foundEvents = new HashMap<Integer, List<Event>>();

            
            Map<Integer, Group> groupMap = new HashMap<Integer, Group>();
            for (Vehicle vehicle: allVehicles){
                //List<Event> events = eventDAO.getEventsForVehicle(vehicle.getVehicleID(), interval.getStart().toDate(), interval.getEnd().toDate(), searchNoteType, 0);
                
                if (events != null && !events.isEmpty()){
                    
                    // Filter vehicles without maintenance events
                    if(containsMaintenanceEvents(events)){
                        vehiclesWithEvents.add(vehicle);
                        vehicleIDForVehiclesWithEvents.add(vehicle.getVehicleID());
                        List<Event> maintenanceEvents = new ArrayList<Event>();
                        for(int i = 0; i < events.size(); i++){
                            
                            // Filter non-maintenance events
                            if(events.get(i) instanceof MaintenanceEvent || events.get(i) instanceof IgnitionOffMaintenanceEvent){
                                maintenanceEvents.add(events.get(i));
                            }
                        }
                        foundEvents.put(vehicle.getVehicleID(), maintenanceEvents);
                    }
                }
            }

            Map<Integer, VehicleSetting> vehiclesWithEventsSettings = getVehiclesWithEventsSettings(vehicleIDForVehiclesWithEvents);
            List<BackingWrapper> backingWrappers = new ArrayList<BackingWrapper>();
            Map<Vehicle, List<Event>> dataset = new HashMap<Vehicle, List<Event>>();

            boolean found = false;

            for (Vehicle vehicle: vehiclesWithEvents) {
                List<Event> eventList = foundEvents.get(vehicle.getVehicleID());
                if (eventList!=null && !eventList.isEmpty()) {
                    found = true;
                    dataset.put(vehicle, eventList);
                }
            }

            while (found){
                found = false;

                Map<Vehicle, Event> calcMap = new HashMap<Vehicle, Event>();
                for (Map.Entry<Vehicle,List<Event>> entry: dataset.entrySet()){ //173 QA 850
                    Vehicle vehicle = entry.getKey();
                    List<Event> vehicleEvents = entry.getValue();

                    if (vehicleEvents!=null && !vehicleEvents.isEmpty()){
                        calcMap.put(vehicle, vehicleEvents.remove(vehicleEvents.size() - 1));
                        dataset.put(vehicle, vehicleEvents);
                        found = true;
                    }
                }
                
                

                if (found) {
                    VehicleEventData vehicleEventData = new VehicleEventData();
                    for (Map.Entry<Vehicle, Event> calcEntry : calcMap.entrySet()) {
                        Vehicle vehicle = calcEntry.getKey();
                        Event event = calcEntry.getValue();

                        int noteCod = event.getType().getCode();
                        int evCode = 0;
                        Integer groupId = vehicle.getGroupID();
                        
                        Group group;
                        if(groupMap.containsKey(groupId)) {
                            group = groupMap.get(groupId);
                        } else {
                            group = groupDAO.findByID(groupId);
                            groupMap.put(groupId, group);
                        }
                        String groupName = group.getName();
                        String vehicleID = event.getVehicleID().toString();
                        String vehicleYMM = vehicle.getYear() + " " + vehicle.getMake() + " " + vehicle.getModel();
                        String maintenanceEvent = event.getEventType().toString();
                        Date date = event.getTime();

                        MaintenanceSettings maintenanceSettings = null;
                        if (event instanceof MaintenanceEvent && ((MaintenanceEvent) event).getOilPresure() != null) {
                            maintenanceSettings = MaintenanceSettings.SET_OIL_PRESSURE;
                            evCode = EventAttr.ATTR_OIL_PRESSURE.getCode();
                        } else if (event instanceof MaintenanceEvent && ((MaintenanceEvent) event).getBatteryVoltage() != null) {
                            maintenanceSettings = MaintenanceSettings.SET_BATT_VOLTAGE;
                            evCode = EventAttr.ATTR_BATTERY_VOLTAGE.getCode();
                        } else if (event instanceof MaintenanceEvent && ((MaintenanceEvent) event).getEngineTemp() != null) {
                            maintenanceSettings = MaintenanceSettings.SET_ENGINE_TEMP;
                            evCode = EventAttr.ATTR_ENGINE_TEMP.getCode();
                        } else if (event instanceof MaintenanceEvent && ((MaintenanceEvent) event).getTransmissionTemp() != null) {
                            maintenanceSettings = MaintenanceSettings.SET_TRANS_TEMP;
                            evCode = EventAttr.ATTR_TRANSMISSION_TEMP.getCode();
                        } else if (event instanceof MaintenanceEvent && ((MaintenanceEvent) event).getDpfFlowRate() != null) {
                            maintenanceSettings = MaintenanceSettings.DPF_FLOW_RATE;
                            evCode = EventAttr.ATTR_DPF_FLOW_RATE.getCode();
                        } else if (event instanceof IgnitionOffMaintenanceEvent && ((IgnitionOffMaintenanceEvent) event).getMalfunctionIndicatorLamp() != null) {
                            evCode = EventAttr.ATTR_MALFUNCTION_INDICATOR_LAMP.getCode();
                        } else if (event instanceof IgnitionOffMaintenanceEvent && ((IgnitionOffMaintenanceEvent) event).getCheckEngine() != null) {
                            evCode = EventAttr.ATTR_CHECK_ENGINE.getCode();
                        } else if (event instanceof MaintenanceEvent && ((MaintenanceEvent) event).getEngineHours() != null) {
                            evCode = EventAttr.ENGINE_HOURS_X100.getCode();
                        } else if (event instanceof MaintenanceEvent && ((MaintenanceEvent) event).getOdometer() != null) {
                            maintenanceSettings = null;
                            evCode = EventAttr.ODOMETER.getCode();
                        }

                        String value = null;
                        String actual = null;
                        if (maintenanceSettings != null) {
                            value = ((MaintenanceEvent) event).getValue();
                            actual = vehiclesWithEventsSettings.get(stringToInt(vehicleID)).getActual().get(maintenanceSettings.getCode());
                        } else {
                            value = "-";
                            actual = "-";
                        }

                        vehicleEventData.putVehicle(vehicle.getVehicleID(), vehicle);
                        vehicleEventData.putNoteCode(vehicle.getVehicleID(), noteCod);
                        vehicleEventData.putEventCode(vehicle.getVehicleID(), evCode);
                        vehicleEventData.putDate(vehicle.getVehicleID(), date);
                        vehicleEventData.putDeviceID(vehicle.getVehicleID(), event.getDeviceID());
                        vehicleEventData.putVehicleYmm(vehicle.getVehicleID(), vehicleYMM);
                        vehicleEventData.putMaintenanceEvent(vehicle.getVehicleID(), maintenanceEvent);
                        vehicleEventData.putValue(vehicle.getVehicleID(), value);
                        vehicleEventData.putActual(vehicle.getVehicleID(), actual);
                        vehicleEventData.putGroupName(vehicle.getVehicleID(), groupName);
                    }

                    Map<Integer, Date> prevEventVehicleDate = getPrevEventDateMap(vehicleEventData);
                    vehicleEventData.setPrevEventDates(prevEventVehicleDate);

                    Map<Integer, String> driveOdometers = getDriveOdometerMap(vehicleEventData);
                    vehicleEventData.setDriveOdometers(driveOdometers);

                    Map<Integer, String> odometersLastEventMap = getOdometersLastEventMap(vehicleEventData);
                    vehicleEventData.setOdometersLastEvent(odometersLastEventMap);

                    Map<Integer, String> engineHoursAtDates = getEngineHoursAtDatesMap(vehicleEventData);
                    vehicleEventData.setEngineHoursAtDates(engineHoursAtDates);

                    Map<Integer, String> engineHoursAtLastDates = getEngineHoursAtLastDatesMap(vehicleEventData);
                    vehicleEventData.setEngineHoursAtLastDates(engineHoursAtLastDates);

                    for (Integer vehicleID : vehicleEventData.getVehicles().keySet()) {
                        String actual = vehicleEventData.getActuals().get(vehicleID);
                        Date date = vehicleEventData.getDates().get(vehicleID);
                        Integer deviceID = vehicleEventData.getDeviceIDs().get(vehicleID);
                        String driveOdometer = vehicleEventData.getDriveOdometers().get(vehicleID);
                        String engineHourAtDate = vehicleEventData.getEngineHoursAtDates().get(vehicleID);
                        String engineHourAtLastDate = vehicleEventData.getEngineHoursAtLastDates().get(vehicleID);
                        Integer eventCode = vehicleEventData.getEventCodes().get(vehicleID);
                        String groupName = vehicleEventData.getGroupNames().get(vehicleID);
                        String maintenanceEvent = vehicleEventData.getMaintenanceEvents().get(vehicleID);
                        Integer noteCode = vehicleEventData.getNoteCodes().get(vehicleID);
                        String odometerLastEvent = vehicleEventData.getOdometersLastEvent().get(vehicleID);
                        Date prevEventDate = vehicleEventData.getPrevEventDates().get(vehicleID);
                        String value = vehicleEventData.getValues().get(vehicleID);
                        Vehicle vehicle = vehicleEventData.getVehicles().get(vehicleID);
                        String vehicleYms = vehicleEventData.getVehicleYmms().get(vehicleID);
                        String vehicleName = vehicle.getName();

                        if (prevEventDate == null)
                            prevEventDate = date;

                        String distanceSince;
                        try {
                            distanceSince = stringToInt(driveOdometer) - stringToInt(odometerLastEvent) + "";
                        } catch (NumberFormatException e) {
                            distanceSince = "N/A";
                        }

                        String hoursSince = stringToInt(engineHourAtDate) - stringToInt(engineHourAtLastDate) + "";
                        Long noteID = null;

                        BackingWrapper backingWrapper = new BackingWrapper(vehicleName, vehicleYms, maintenanceEvent, date, value, actual,
                                driveOdometer, distanceSince, engineHourAtDate, hoursSince, groupName, noteID);

                        // Filter non-maintenance events
                        if (eventCode > 0)
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


        /**
         * Batch gets all the settings for vehicles with events given by a list of vehicle IDs.
         *
         * @param vehicleIDs list of vehicle IDs
         * @return all settings
         */
        private Map<Integer, VehicleSetting> getVehiclesWithEventsSettings(List<Integer> vehicleIDs){
            return configuratorJDBCDAO.getVehicleSettingsForAll(vehicleIDs);
        }

        /**
         * Batch gets all the previous event dates for vehicle event data.
         *
         * @param vehicleEventData vehicle event data
         * @return all prev event dates
         */
        private Map<Integer, Date> getPrevEventDateMap(VehicleEventData vehicleEventData) {
            return driveTimeDAO.getPrevEventDates(vehicleEventData);
        }

        /**
         * Batch gets all the previous odometers for vehicle event data.
         *
         * @param vehicleEventData vehicle event data
         * @return all prev odometers
         */
        private Map<Integer, String> getDriveOdometerMap(VehicleEventData vehicleEventData) {
            return driveTimeDAO.getDriveOdometersAtDates(vehicleEventData);
        }

        /**
         * Batch gets all the previous odometers last events for vehicle event data.
         *
         * @param vehicleEventData vehicle event data
         * @return all prev odometers last events
         */
        private Map<Integer, String> getOdometersLastEventMap(VehicleEventData vehicleEventData) {
            return driveTimeDAO.getDriveOdometersAtLastDates(vehicleEventData);
        }

        /**
         * Batch gets all the engine hours at dates for vehicle event data.
         *
         * @param vehicleEventData vehicle event data
         * @return engine hours at dates
         */
        private Map<Integer, String> getEngineHoursAtDatesMap(VehicleEventData vehicleEventData) {
            return driveTimeDAO.getEngineHoursAtDates(vehicleEventData);
        }

        /**
         * Batch gets all the engine hours at last dates for vehicle event data.
         *
         * @param vehicleEventData vehicle event data
         * @return engine hours at last dates
         */
        private Map<Integer, String> getEngineHoursAtLastDatesMap(VehicleEventData vehicleEventData) {
            return driveTimeDAO.getEngineHoursAtLastDates(vehicleEventData);
        }

        public MaintenanceReportsDAO getMaintenanceReportsDAO() {
            return maintenanceReportsDAO;
        }

        public void setMaintenanceReportsDAO(MaintenanceReportsDAO maintenanceReportsDAO) {
            this.maintenanceReportsDAO = maintenanceReportsDAO;
        }

    }

    /*
     * Tests to see if a List<Event> contains a maintenance event
     */
    private static boolean containsMaintenanceEvents(List<Event> events) {
        for(int i = 0; i < events.size(); i++){
            if(events.get(i) instanceof MaintenanceEvent || events.get(i) instanceof IgnitionOffMaintenanceEvent){
                return true;
            }
        }
        return false;
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
        private Long noteID;

        public BackingWrapper(String vehicleID, String vehicleYMM, String maintenanceEvent, Date date, String value,String actual, String odometer, String distanceSince,
                              String engineHours, String hoursSince, String groupPath, Long noteID) {
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
            this.noteID = noteID;
        }
        
        @Override
        public String toString() {
            StringBuilder toString = new StringBuilder();
            toString.append("Event BackingWrapper: [");
            toString.append("vehicleID=" + vehicleID + ", ");
            toString.append("vehicleYMM=" + vehicleYMM + ", ");
            toString.append("maintenanceEvent=" + maintenanceEvent + ", ");
            toString.append("date=" + date + ", ");
            toString.append("value=" + value + ", ");
            toString.append("actual(threshold)=" + actual + ", ");
            
            toString.append("odometer=" + odometer + ", ");
            toString.append("distanceSince=" + distanceSince + ", ");
            toString.append("engineHours=" + engineHours + ", ");
            toString.append("hoursSince=" + hoursSince + ", ");
            toString.append("groupPath=" + groupPath + ", ");
            
            toString.append("]");
            
            return toString.toString();
            
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

        public Long getNoteID() {
            return noteID;
        }

        public void setNoteID(Long noteID) {
            this.noteID = noteID;
        }

    }

}
