package com.inthinc.pro.dao.hessian;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.AlertMessageDAO;
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
import com.inthinc.pro.model.event.AggressiveDrivingEvent;
import com.inthinc.pro.model.AlertMessage;
import com.inthinc.pro.model.AlertMessageBuilder;
import com.inthinc.pro.model.AlertMessageDeliveryType;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.NoAddressFoundException;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.event.SpeedingEvent;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.Zone;

public class AlertMessageHessianDAO extends GenericHessianDAO<AlertMessage, Integer> implements AlertMessageDAO {
    
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private static final Logger logger = Logger.getLogger(AlertMessageHessianDAO.class);
    private Integer MAX_SILO_ID = 1;
    private EventDAO eventDAO;
    private DriverDAO driverDAO;
    private VehicleDAO vehicleDAO;
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
            for (Integer siloID = 0; siloID < MAX_SILO_ID; siloID++) {
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
            for (Integer siloID = 0; siloID < MAX_SILO_ID; siloID++) {
                try {
                    List<AlertMessage> alertMessageList = getMapper().convertToModelObject(getSiloService().getMessages(siloID << 24, messageType.getCode()), AlertMessage.class);
                    for (AlertMessage alertMessage : alertMessageList) {
                        AlertMessageBuilder alertMessageBuilder = getMessageBuilder(alertMessage);
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

    private AlertMessageBuilder getMessageBuilder(AlertMessage alertMessage) {
        Event event = eventDAO.findByID(alertMessage.getNoteID());

        AlertMessageBuilder alertMessageBuilder = createAlertMessageBuilder(alertMessage, event);
        return alertMessageBuilder;
    }

    private AlertMessageBuilder createAlertMessageBuilder(AlertMessage alertMessage, Event event) {
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
        List<String> parameterList = new ArrayList<String>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d, yyyy h:mm a (z)");
        
        // Check for unknown driver
        if ( (driver != null) && (driver.getPerson() != null) ) {
            simpleDateFormat.setTimeZone(driver.getPerson().getTimeZone());
        } else {
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        }
        
        // Construct the message parameter list
        parameterList.add(simpleDateFormat.format(event.getTime()));
        if (driver != null && driver.getPerson() != null)
        	parameterList.add(driver.getPerson().getFullName());
        else {
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
                try {
                	parameterList.add(addressLookup.getAddress(new LatLng(event.getLatitude(), event.getLongitude()), true));
                }
                catch (NoAddressFoundException nafe){
                	//Shouldn't happen because returning lat lng when there is no address
                }
            case ALERT_TYPE_TAMPERING:
            case ALERT_TYPE_LOW_BATTERY:
                break;
            default:
                try {
                	parameterList.add(addressLookup.getAddress(new LatLng(event.getLatitude(), event.getLongitude()), true));
                }
                catch (NoAddressFoundException nafe){
                	//Shouldn't happen because returning lat lng when there is no address
                }
        }
        alertMessageBuilder.setParamterList(parameterList);
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

}
