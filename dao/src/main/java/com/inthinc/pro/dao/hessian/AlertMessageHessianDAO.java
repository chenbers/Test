package com.inthinc.pro.dao.hessian;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.AlertMessageDAO;
import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.ZoneDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.model.AlertMessage;
import com.inthinc.pro.model.AlertMessageBuilder;
import com.inthinc.pro.model.AlertMessageDeliveryType;
import com.inthinc.pro.model.AlertMessageType;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.Silo;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventAttr;
import com.inthinc.pro.model.event.IdleEvent;
import com.inthinc.pro.model.event.SpeedingEvent;

public class AlertMessageHessianDAO extends GenericHessianDAO<AlertMessage, Integer> implements AlertMessageDAO {
    
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private static final Logger logger = Logger.getLogger(AlertMessageHessianDAO.class);
    private EventDAO eventDAO;
    private DriverDAO driverDAO;
    private VehicleDAO vehicleDAO;
    private DeviceDAO deviceDAO;
    private GroupDAO groupDAO;
    private PersonDAO personDAO;
	private ZoneDAO zoneDAO;
    private AddressLookup addressLookup;

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.AlertMessageDAO#getMessages(com.inthinc.pro.model.AlertMessageDeliveryType)
     */
    @Override
    public List<AlertMessage> getMessages(AlertMessageDeliveryType messageType) {
        try {
            List<AlertMessage> messages = new ArrayList<AlertMessage>();
//            for (Integer siloID = 0; siloID < MAX_SILO_ID; siloID++)
            Silo silo = getMapper().convertToModelObject(getSiloService().getNextSilo(), Silo.class);
            Integer siloID = silo.getSiloID();
            {
                try {
                    messages.addAll(getMapper().convertToModelObject(getSiloService().getMessages(siloID << 24, messageType.getCode()), AlertMessage.class));
                }
                catch (ProxyException e) {
                    logger.debug("getMessages proxy exception");
                }
            }
            return messages;
        }
        catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
        // // TODO: remove once this is implemented on the back end
        catch (ProxyException e) {
            if (e.getErrorCode() == 422)
                return Collections.emptyList();
            else
                throw e;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.AlertMessageDAO#getMessageBuilders(com.inthinc.pro.model.AlertMessageDeliveryType)
     */
    public List<AlertMessageBuilder> getMessageBuilders(AlertMessageDeliveryType messageType) {
        try {
            List<AlertMessageBuilder> messages = new ArrayList<AlertMessageBuilder>();
            Silo silo = getMapper().convertToModelObject(getSiloService().getNextSilo(), Silo.class);
            Integer siloID = silo.getSiloID();
            {
                try {
                    List<AlertMessage> alertMessageList = getMapper().convertToModelObject(getSiloService().getMessages(siloID << 24, messageType.getCode()), AlertMessage.class);
                    for (AlertMessage alertMessage : alertMessageList) {
                        AlertMessageBuilder alertMessageBuilder = getMessageBuilder(alertMessage, messageType);
                        if (alertMessageBuilder != null)
                            messages.add(alertMessageBuilder);
                    }
                }
                catch (ProxyException e) {
                    logger.debug(e);
                    logger.debug("getMessages proxy exception");
                }
            }
            return messages;
        }
        catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

    private AlertMessageBuilder getMessageBuilder(AlertMessage alertMessage, AlertMessageDeliveryType messageDeliveryType) {
        Event event = eventDAO.findByID(alertMessage.getNoteID());

        AlertMessageBuilder alertMessageBuilder = createAlertMessageBuilder(alertMessage, event, messageDeliveryType);
        return alertMessageBuilder;
    }

    private AlertMessageBuilder createAlertMessageBuilder(AlertMessage alertMessage, Event event, AlertMessageDeliveryType messageDeliveryType) {
        if (alertMessage.getPersonID() == null || alertMessage.getPersonID() == 0 || event == null) {
            logger.debug("Person ID or Event is Null");
            return null;
        }
        Person person = personDAO.findByID(alertMessage.getPersonID());
        logger.debug("Preparing message for: " + person.getFullName());
        Driver driver = driverDAO.findByID(event.getDriverID());
        Vehicle vehicle = vehicleDAO.findByID(event.getVehicleID());
        Locale locale = Locale.ENGLISH;
        if (person != null && person.getLocale() != null)
            locale = person.getLocale();

        AlertMessageBuilder alertMessageBuilder = new AlertMessageBuilder();
        alertMessageBuilder.setLocale(locale);
        alertMessageBuilder.setAddress(alertMessage.getAddress());
        alertMessageBuilder.setAlertID(alertMessage.getAlertID());
        alertMessageBuilder.setMessageID(alertMessage.getMessageID());
        alertMessageBuilder.setAlertMessageType(alertMessage.getAlertMessageType());
        alertMessageBuilder.setAlertName(alertMessage.getName());

        logger.debug("AlertMessageBuilder: Preparing parameterList for alert: " + alertMessageBuilder.getAlertName());
        List<String> parameterList = new ArrayList<String>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d, yyyy h:mm a (z)", locale);
        
        // Check for unknown driver
        if ( (driver != null) && (driver.getPerson() != null) ) {
            simpleDateFormat.setTimeZone(driver.getPerson().getTimeZone());
        } else {
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        }
        
        // Construct the message parameter list
        parameterList.add(simpleDateFormat.format(event.getTime()));
        if (driver != null && driver.getPerson() != null) {
            
            // Add the drivers name to the parameter list
        	parameterList.add(driver.getPerson().getFullName());
            
            // Add the full group name of the driver to the parameter list
        	Person driverPerson = driver.getPerson();
            Integer acctID = driverPerson.getAcctID();
            List<Group> groupList = groupDAO.getGroupsByAcctID(acctID);
            GroupHierarchy groupHierarchy = new GroupHierarchy(groupList);
            String groupName = groupHierarchy.getFullGroupName(driver.getGroupID(), " > ");
            parameterList.add(groupName);
        } else {
            
            // We still need to return the correct number
            // of parameters, as i18n is dependent upon the
            // correct parameters being returned in a fixed
            // position within the list
        	parameterList.add("");
        	parameterList.add("");
        }
        parameterList.add(vehicle.getName());
        switch (alertMessage.getAlertMessageType()) {
            case ALERT_TYPE_ENTER_ZONE:
            case ALERT_TYPE_EXIT_ZONE:
                Zone zone = zoneDAO.findByID(alertMessage.getZoneID());
                parameterList.add(zone.getName());
                break;
            case ALERT_TYPE_SPEEDING:
                Number topSpeed = MeasurementConversionUtil.convertSpeed(((SpeedingEvent) event).getTopSpeed(), person.getMeasurementType());
                Number speedLimit = MeasurementConversionUtil.convertSpeed(((SpeedingEvent) event).getSpeedLimit(), person.getMeasurementType());
                parameterList.add(String.valueOf(topSpeed));
                parameterList.add(String.valueOf(speedLimit));
                parameterList.add(addressLookup.getAddressOrLatLng(new LatLng(event.getLatitude(), event.getLongitude())));
            case ALERT_TYPE_TAMPERING:
            case ALERT_TYPE_LOW_BATTERY:
                break;
            case ALERT_TYPE_IDLING:
                int totalIdling = 0;
                
                if ( ((IdleEvent)event).getHighIdle() != null ) {
                    totalIdling += ((IdleEvent)event).getHighIdle();
                }
                if ( ((IdleEvent)event).getLowIdle() != null ) {
                    totalIdling += ((IdleEvent)event).getLowIdle();
                }
                parameterList.add(String.valueOf(totalIdling/60));
                parameterList.add(addressLookup.getAddressOrLatLng(new LatLng(event.getLatitude(), event.getLongitude())));
                break;
            default:
                parameterList.add(addressLookup.getAddressOrLatLng(new LatLng(event.getLatitude(), event.getLongitude())));
        }
        alertMessageBuilder.setParamterList(parameterList);

	    // ExCrm Param List
	    if (AlertMessageDeliveryType.EMAIL == messageDeliveryType &&
			AlertMessageType.getEzCrmAlertTypes().contains(alertMessage.getAlertMessageType())) {
	        logger.debug("AlertMessageBuilder: ExCrm preparing ExCrmParameterList for alert: " + alertMessageBuilder.getAlertName());
	        
	        List<String> ezParameterList = new EzCrmParameterList().getParameterList(event, person, alertMessage, locale);
	        alertMessageBuilder.setEzParameterList(ezParameterList);
	        logger.debug("ExCrm: alertMessageBuilder.getAlertName() = "+ alertMessageBuilder.getAlertName() + ", alertMessage.gerName() = " + alertMessage.getName() + ", ezParameterList.size() = " + ezParameterList.size());
	    }
	    
        return alertMessageBuilder;
    }

    public void setEventDAO(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    public EventDAO getEventDAO() {
        return eventDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }

    public DeviceDAO getDeviceDAO() {
		return deviceDAO;
	}

	public void setDeviceDAO(DeviceDAO deviceDAO) {
		this.deviceDAO = deviceDAO;
	}

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public PersonDAO getPersonDAO() {
        return personDAO;
    }

    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public void setAddressLookup(AddressLookup addressLookup) {
        this.addressLookup = addressLookup;
    }

    public AddressLookup getAddressLookup() {
        return addressLookup;
    }

    public ZoneDAO getZoneDAO() {
        return zoneDAO;
    }

    public void setZoneDAO(ZoneDAO zoneDAO) {
        this.zoneDAO = zoneDAO;
    }

    @Override
    public Boolean acknowledgeMessage(Integer msgID) {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public Boolean cancelPendingMessage(Integer msgID) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void fillInRedFlagMessageInfo(List<RedFlag> redFlagList) {
        // TODO Auto-generated method stub
        
    }

    public class EzCrmParameterList {
        //#RedFlag ezCRM Parmeter List
        //#0 {Red Flag Alert Name} - for subject line
        //#1 {GROUP} - fmt: division-division-...-team
        //#2 {CATEGORY} - 0|1|2 for now...One of: Critical|Normal|Information 
        //#3 {DATE} - fmt: yyyy-mm-dd hh:mm:ss
        //#4 {EMP ID} - fmt: external driver id | omitted if no driver
        //#5 {DRIVER ID} - fmt: internal driver id | UNKNOWN if no driver - empty here
        //#6 {DRIVER NAME} - fmt: First Middle Last | UNKNOWN - empty here
        //#7 {VEHICLE NAME}
        //#8 {VEHICLE ID} - fmt: internal vehicle ID
        //#9 {YEAR} - fmt: yyyy | ommited if blank
        //#10 {MAKE} - ommited if blank
        //#11 {MODEL} - ommited if blank
        //#12 {LAT} - latitude | NO GPS LOCK
        //#13 {LON} - longitude | NO GPS LOCK
        //#14 {ADDRESS} - address | UNKNOWN
        //#15 {ODOMETER} - fmt: NNNNN (KM | Mi)
        //#16 {SPEED} - fmt: NN (KPH | MPH)
        //#17 {MeasurementType}: 0 or 1
        //#18 {Event Detail Param1}
        //#19 {Event Detail Param2}

        private List<String> parameterList;
        private Event event;
        private MeasurementType personMeasurementType;
        private Locale locale;
        private Person person;
        private Driver driver;
        private AlertMessage alertMessage;
        
        public List<String> getParameterList(Event event, Person person, AlertMessage alertMessage, Locale locale) {
            if (event == null)
                return null;
            parameterList = new ArrayList<String>();
            personMeasurementType = person.getMeasurementType();
            this.locale = locale;
            this.setPerson(person);
            driver = driverDAO.findByID(event.getDriverID());
            
            parameterList.add(alertMessage.getName());          //#0
            parameterList.add(getDriverOrgStructure(driver));   //#1
            addRedFlagLevel(alertMessage);              //#2
            addEventTime(event.getTime());              //#3
            getEmpID(person);                           //#4
            addDriverInfo(driver);                      //#5 & #6
            addVehicleInfo(event.getVehicleID());       //#7 - #11
            addLocationInfo(event);                     //#12 - #14
            addOdometer(event);                         //#15
            addSpeed(event);                            //#16
            parameterList.add(String.valueOf(personMeasurementType.ordinal()-1));    //#17 - needs to be 0|1 -- 0-english or 1-metric
            addEventParams(event, alertMessage);        //#18+  if any

            return parameterList;
        }

        public List<String> getParameterListTest() {
            if (this.event == null)
                return null;
            parameterList = new ArrayList<String>();
            //personMeasurementType = person.getMeasurementType();
            //this.locale = locale;
            //this.setPerson(person);
            //driver = driverDAO.findByID(event.getDriverID());
            
            parameterList.add(this.alertMessage.getName());          //#0
            parameterList.add(getDriverOrgStructure(driver));   //#1
            addRedFlagLevel(alertMessage);              //#2
            addEventTime(event.getTime());              //#3
            getEmpID(person);                           //#4
            addDriverInfo(driver);                      //#5 & #6
            addVehicleInfo(event.getVehicleID());       //#7 - #11
            addLocationInfo(event);                     //#12 - #14
            addOdometer(event);                         //#15
            addSpeed(event);                            //#16
            int mType = personMeasurementType.ordinal();
            parameterList.add(String.valueOf(mType));    //#17 - needs to be 0|1 -- 0-english or 1-metric
            addEventParams(event, alertMessage);        //#18+  if any

            return parameterList;
        }

        private void addRedFlagLevel(AlertMessage alertMessage) {
            int category;
            switch(alertMessage.getLevel()) {
                case CRITICAL:
                    category = 0;
                    break;
                case WARNING:
                    category = 1;
                    break;
                case INFO:
                default:
                    category = 2;
                    break;
            }
            parameterList.add(String.valueOf(category));
        }
        
        private void addEventTime(Date eventTime) {
            SimpleDateFormat driverDateFormat = getDriverDate(driver);
            // Construct the message parameter list
            parameterList.add(driverDateFormat.format(eventTime));
        }

        private SimpleDateFormat getDriverDate(Driver driver) {

            SimpleDateFormat driverDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss (z)", locale);

            if ((driver != null) && (driver.getPerson() != null)) {
                driverDateFormat.setTimeZone(driver.getPerson().getTimeZone());
            } else {
                driverDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            }
            return driverDateFormat;
        }

        private void getEmpID(Person person) {
            String empID = new String("");
            if (person != null) {
                empID = person.getEmpid();
            }
            parameterList.add(empID);
        }
        
        private void addDriverInfo(Driver driver) {
            if ((driver != null) && (driver.getPerson() != null)) {
                parameterList.add(driver.getDriverID().toString());
                parameterList.add(getDriverFullName(driver));
                
            } else {
                parameterList.add("");
                parameterList.add("");
            }
        }
        
        private String getDriverFullName(Driver driver) {

            if (driver != null && driver.getPerson() != null)
                return driver.getPerson().getFullName();
            else {
                return "";
            }
        }
        
        private String getDriverOrgStructure (Driver driver) {
            if (driver != null && driver.getPerson() != null) {
                Person driverPerson = driver.getPerson();
                Integer acctID = driverPerson.getAcctID();
                List<Group> groupList = groupDAO.getGroupsByAcctID(acctID);
                GroupHierarchy groupHierarchy = new GroupHierarchy(groupList);
                String groupName = groupHierarchy.getFullGroupName(driver.getGroupID(), " - ");
                return groupName;
            } else {
                return "";
            }
        }

        private void addVehicleInfo(Integer vehicleID) {
            Vehicle vehicle = vehicleDAO.findByID(vehicleID);
            //#7 {VEHICLE NAME}
            parameterList.add(vehicle.getName());
            //#8 {VEHICLE ID} - fmt: internal vehicle ID
            parameterList.add(vehicleID.toString());
            //#9 {YEAR} - fmt: yyyy | ommited if blank
            String tmp = new String("");
            Integer year = vehicle.getYear();
            if (year > 0) {
                tmp = String.format("%04d", year);
            }
            parameterList.add(tmp);
            //#10 {MAKE} - ommited if blank
            tmp = vehicle.getMake();
            parameterList.add(tmp);
            //#11 {MODEL} - ommited if blank
            tmp = vehicle.getModel();
            parameterList.add(tmp);
        }
        
        private void addLocationInfo(Event event) {
            String tmp = new String("");
            //#12 {LAT} - latitude | NO GPS LOCK
            if (!event.getLatitude().equals(0.0))
                tmp = String.format("%.5f", event.getLatitude());
            parameterList.add(tmp);
            //#13 {LON} - longitude | NO GPS LOCK
            tmp = "";
            if (!event.getLongitude().equals(0.0))
                tmp = String.format("%.5f", event.getLongitude());
            parameterList.add(tmp);
            //#14 {ADDRESS} - address | UNKNOWN
            addAddress(event);
        }

        private void addOdometer(Event event) {
            String tmp = new String();
            //#15 {ODOMETER} - fmt: NNNNN (KM | Mi)
            tmp = String.format("%d", event.getOdometer());
            parameterList.add(tmp);
        }

        private void addSpeed(Event event) {
            String tmp = new String();
            //#16 {SPEED} - fmt: NN (KPH | MPH)
            tmp = String.format("%d", event.getSpeed());
            parameterList.add(tmp);
        }

        private void addEventParams(Event event, AlertMessage alertMessage) {
            logger.debug("addEventParams alertMessageType: " + alertMessage.getAlertMessageType());
            switch (alertMessage.getAlertMessageType()) {
                case ALERT_TYPE_SPEEDING:   // Add {Top Speed} & {Speed Limit}
                    {
                        addSpeedingRelatedData((SpeedingEvent) event);
                    }
                    break;
                case ALERT_TYPE_ENTER_ZONE: // add {Zone Name} & {ZoneID}
                case ALERT_TYPE_EXIT_ZONE:  // add {Zone Name} & {ZoneID}
                    addZoneRelatedData(alertMessage.getZoneID());
                    break;
                case ALERT_TYPE_FIRMWARE_CURRENT:   // add {Firmware version}
                    {
                        Device device = deviceDAO.findByID(event.getDeviceID());
                        if (device == null) {
                            logger.error("Device could not be found for deviceID: " + event.getDeviceID());
                            parameterList.add("");
                        } else {
                            parameterList.add(device.getFirmwareVersion().toString());
                        }
                    }
                    break;
                case ALERT_TYPE_QSI_UPDATED:    // add {QSI Update Status}
                case ALERT_TYPE_ZONES_CURRENT: // add {Zones Update Status}
                    {
                        // EventAttr.UP_TO_DATE_STATUS gives an update status
                        //  up to date flags:
                        //      #define FLAG_UP_TO_DATE_UPDATED             1
                        //      #define FLAG_UP_TO_DATE_ALREADY_CURRENT     2
                        //      #define FLAG_UP_TO_DATE_SERVER_OLDER        3
                        //      #define FLAG_UP_TO_DATE_INVALID             4
                        //      #define FLAG_UP_TO_DATE_MISSING             5
                        //      #define FLAG_UP_TO_DATE_DEVICE_TIMEOUT      6
                        String status = event.getAttrByType(EventAttr.UP_TO_DATE_STATUS);
                        if (status.isEmpty()) {
                            logger.error("Invalid update status");
                            parameterList.add("");
                        } else {
                            parameterList.add(status);
                        }
                    }
                    break;
                case ALERT_TYPE_WITNESS_UPDATED:    // add {Witness version}
                    {
                        Device device = deviceDAO.findByID(event.getDeviceID());
                        if (device == null) {
                            logger.error("Device could not be found for deviceID: " + event.getDeviceID());
                            parameterList.add("");
                        } else {
                            parameterList.add(device.getWitnessVersion().toString());
                        }
                    }
                    break;
                case ALERT_TYPE_IDLING: // add {Total idling Time}
                    {
                        Integer totalIdle = ((IdleEvent) event).getTotalIdling();
                        parameterList.add(totalIdle.toString());
                    }
                    break;
                default:
            }
        }

        private void addAddress(Event event) {
            parameterList.add(addressLookup.getAddressOrLatLng(new LatLng(event.getLatitude(), event.getLongitude())));
        }

        private void addZoneRelatedData(Integer zoneID) {

            Zone zone = zoneDAO.findByID(zoneID);
            if (zone == null) {
                logger.error("Zone could not be found for zoneID: " + zoneID);
                parameterList.add("");
            } else {
                parameterList.add(zone.getName());
            }
            parameterList.add(zoneID.toString());
        }

        private void addSpeedingRelatedData(SpeedingEvent event) {
            Number topSpeed = MeasurementConversionUtil.convertSpeed(event.getTopSpeed(), personMeasurementType);
            parameterList.add(String.valueOf(topSpeed));

            Number speedLimit = MeasurementConversionUtil.convertSpeed(event.getSpeedLimit(), personMeasurementType);
            parameterList.add(String.valueOf(speedLimit));
        }
        
        public List<String> getParameterList() {
            return parameterList;
        }

        public Event getEvent() {
            return event;
        }

        public void setEvent(Event event) {
            this.event = event;
        }

        public Person getPerson() {
            return person;
        }

        public void setPerson(Person person) {
            this.person = person;
        }
        public void setMeasurementType(MeasurementType type) {
            this.personMeasurementType = type;
        }
        public Locale getLocale() {
            return locale;
        }
        public void setLocal(Locale locale) {
            this.locale = locale;
        }
        public Driver getDriver() {
            return driver;
        }
        public void setDriver(Driver driver) {
            this.driver = driver;
        }
        public void setAlertMessage(AlertMessage alertMessage) {
            this.alertMessage = alertMessage;
        }
    }

}
