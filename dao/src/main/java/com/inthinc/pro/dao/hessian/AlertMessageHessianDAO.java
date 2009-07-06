package com.inthinc.pro.dao.hessian;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.AlertMessageDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.model.AggressiveDrivingEvent;
import com.inthinc.pro.model.AlertMessage;
import com.inthinc.pro.model.AlertMessageBuilder;
import com.inthinc.pro.model.AlertMessageDeliveryType;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.LowBatteryEvent;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.SeatBeltEvent;
import com.inthinc.pro.model.SpeedingEvent;
import com.inthinc.pro.model.TamperingEvent;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.ZoneArrivalEvent;
import com.inthinc.pro.model.ZoneDepartureEvent;

public class AlertMessageHessianDAO extends GenericHessianDAO<AlertMessage, Integer> implements AlertMessageDAO
{
    private static final Logger logger = Logger.getLogger(AlertMessageHessianDAO.class);
    private Integer MAX_SILO_ID = 1;

    private EventDAO eventDAO;
    private DriverDAO driverDAO;
    private VehicleDAO vehicleDAO;
    private GroupDAO groupDAO;
    private PersonDAO personDAO;
    
    private AddressLookup addressLookup;

    /*
     * (non-Javadoc)
     * @see com.inthinc.pro.dao.AlertMessageDAO#getMessages(com.inthinc.pro.model.AlertMessageDeliveryType)
     */
    @Override
    public List<AlertMessage> getMessages(AlertMessageDeliveryType messageType)
    {
        try
        {
            List<AlertMessage> messages = new ArrayList<AlertMessage>();

            for (Integer siloID = 0; siloID < MAX_SILO_ID; siloID++)
            {
                try
                {
                    messages.addAll(getMapper().convertToModelObject(getSiloService().getMessages(siloID << 24, messageType.getCode()), AlertMessage.class));
                }
                catch (ProxyException e)
                {
                    logger.debug("getMessages proxy exception");
                }
            }
            return messages;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
        // // TODO: remove once this is implemented on the back end
        catch (ProxyException e)
        {
            if (e.getErrorCode() == 422)
                return Collections.emptyList();
            else
                throw e;
        }

    }

    /*
     * (non-Javadoc)
     * @see com.inthinc.pro.dao.AlertMessageDAO#getMessageBuilders(com.inthinc.pro.model.AlertMessageDeliveryType)
     */
    public List<AlertMessageBuilder> getMessageBuilders(AlertMessageDeliveryType messageType)
    {
        try
        {
            List<AlertMessageBuilder> messages = new ArrayList<AlertMessageBuilder>();

            for (Integer siloID = 0; siloID < MAX_SILO_ID; siloID++)
            {
                try
                {
                    List<AlertMessage> alertMessageList = getMapper().convertToModelObject(getSiloService().getMessages(siloID << 24, messageType.getCode()), AlertMessage.class);
                    for (AlertMessage alertMessage : alertMessageList)
                    {
                        AlertMessageBuilder alertMessageBuilder = getMessageBuilder(alertMessage);
                        if(alertMessageBuilder != null)
                            messages.add(alertMessageBuilder);
                    }
                }
                catch (ProxyException e)
                {
                    logger.debug(e);
                    logger.debug("getMessages proxy exception");
                }
            }
            return messages;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

    private AlertMessageBuilder getMessageBuilder(AlertMessage alertMessage)
    {
        
        Event event = null;
        switch (alertMessage.getAlertMessageType()) {
        case ALERT_TYPE_SPEEDING:
            event = eventDAO.getEventByType(alertMessage.getNoteID(), SpeedingEvent.class);
            break;
        case ALERT_TYPE_AGGRESSIVE_DRIVING:
            break;
        case ALERT_TYPE_ENTER_ZONE:
            event = eventDAO.getEventByType(alertMessage.getNoteID(), ZoneArrivalEvent.class);
            break;
        case ALERT_TYPE_TAMPERING:
            event = eventDAO.getEventByType(alertMessage.getNoteID(), TamperingEvent.class);
            break;
        case ALERT_TYPE_EXIT_ZONE:
            event = eventDAO.getEventByType(alertMessage.getNoteID(), ZoneDepartureEvent.class);
            break;
        case ALERT_TYPE_LOW_BATTERY:
            event = eventDAO.getEventByType(alertMessage.getNoteID(), LowBatteryEvent.class);
            break;
        case ALERT_TYPE_SEATBELT:
            event = eventDAO.getEventByType(alertMessage.getNoteID(), SeatBeltEvent.class);
            break;
        case ALERT_TYPE_UNKNOWN:
            break;
        case ALERT_TYPE_CRASH:
            // event = eventDAO.getEventByType(alertMessage.getNoteID(), .class);
            break;
        case ALERT_TYPE_HARD_ACCELL:
        case ALERT_TYPE_HARD_BRAKE:
        case ALERT_TYPE_HARD_BUMP:
        case ALERT_TYPE_HARD_TURN:
            event = eventDAO.getEventByType(alertMessage.getNoteID(), AggressiveDrivingEvent.class);
            break;
        }
        
        AlertMessageBuilder alertMessageBuilder = createAlertMessageBuilder(alertMessage,event);
        
        return alertMessageBuilder;
    }

    private AlertMessageBuilder createAlertMessageBuilder(AlertMessage alertMessage,Event event)
    {
        if(alertMessage.getPersonID() == null || alertMessage.getPersonID() == 0)
            return null;
        
        
        Person person = personDAO.findByID(alertMessage.getPersonID());
        Driver driver = driverDAO.findByID(event.getDriverID());
        Vehicle vehicle = vehicleDAO.findByID(event.getVehicleID());
        Locale locale = Locale.ENGLISH;
        if (person.getUser() != null && person.getUser().getLocale() != null)
            locale = person.getUser().getLocale();
        
        AlertMessageBuilder alertMessageBuilder = new AlertMessageBuilder();
        alertMessageBuilder.setLocale(locale);
        alertMessageBuilder.setAddress(alertMessage.getAddress());
        alertMessageBuilder.setAlertMessageID(alertMessage.getAlertID());
        alertMessageBuilder.setAlertMessageType(alertMessage.getAlertMessageType());
        List<String> parameterList = new ArrayList<String>();
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d, yyyy h:mm a (z)");
        simpleDateFormat.setTimeZone(driver.getPerson().getTimeZone());
        
        //Construct the message parameter list
        parameterList.add(simpleDateFormat.format(event.getTime()));
        parameterList.add(driver.getPerson().getFullName());
        parameterList.add(vehicle.getName());
 
        switch(alertMessage.getAlertMessageType())
        {
        case ALERT_TYPE_ENTER_ZONE:
        case ALERT_TYPE_EXIT_ZONE:
            Group group = groupDAO.findByID(alertMessage.getZoneID());
            parameterList.add(group.getName());
            break;
        case ALERT_TYPE_SPEEDING:
            parameterList.add(String.valueOf(((SpeedingEvent)event).getTopSpeed()));
            parameterList.add(String.valueOf(((SpeedingEvent)event).getSpeedLimit()));
            parameterList.add(addressLookup.getAddress(new LatLng(event.getLatitude(),event.getLongitude()),true));
        default:
            parameterList.add(addressLookup.getAddress(new LatLng(event.getLatitude(),event.getLongitude()),true));
        }
        
        alertMessageBuilder.setParamterList(parameterList);
        
        return alertMessageBuilder;
    }

    public void setEventDAO(EventDAO eventDAO)
    {
        this.eventDAO = eventDAO;
    }

    public EventDAO getEventDAO()
    {
        return eventDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO)
    {
        this.driverDAO = driverDAO;
    }

    public DriverDAO getDriverDAO()
    {
        return driverDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO)
    {
        this.vehicleDAO = vehicleDAO;
    }

    public VehicleDAO getVehicleDAO()
    {
        return vehicleDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO)
    {
        this.groupDAO = groupDAO;
    }

    public GroupDAO getGroupDAO()
    {
        return groupDAO;
    }


    public PersonDAO getPersonDAO()
    {
        return personDAO;
    }

    public void setPersonDAO(PersonDAO personDAO)
    {
        this.personDAO = personDAO;
    }

    public void setAddressLookup(AddressLookup addressLookup)
    {
        this.addressLookup = addressLookup;
    }

    public AddressLookup getAddressLookup()
    {
        return addressLookup;
    }

}
