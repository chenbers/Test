package com.inthinc.pro.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.AlertMessageDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.ZoneDAO;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.model.AlertMessage;
import com.inthinc.pro.model.AlertMessageBuilder;
import com.inthinc.pro.model.AlertMessageDeliveryType;
import com.inthinc.pro.model.AlertMessageType;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.NoAddressFoundException;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.RedFlagLevel;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.SpeedingEvent;
import com.mysql.jdbc.PreparedStatement;

public class AlertMessageJDBCDAO  extends GenericJDBCDAO  implements AlertMessageDAO{

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(AlertMessageJDBCDAO.class);
    private EventDAO eventDAO;
    private PersonDAO personDAO;
    private VehicleDAO vehicleDAO;
    private DriverDAO driverDAO;
    private ZoneDAO zoneDAO;
    private AddressLookup addressLookup;

    //message status 
    //1 new
    //2 sent
    //3 waiting acknowledge
    //4 acknowledged

    @Override
    public Boolean acknowledgeMessage(Integer msgID) {
        Connection conn = null;
        Integer numRows=0;
        PreparedStatement statement = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement("UPDATE message SET status=IF(status=1,2,IF(status=3,4,status)), modified=utc_timestamp() WHERE msgID=?");
            statement.setInt(1, msgID);
            numRows=statement.executeUpdate();
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw new ProDAOException(statement.toString(), e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(statement);
            close(conn);
        } // end finally   
        return numRows!=0;
    }

    @Override
    public List<AlertMessage> getMessages(AlertMessageDeliveryType messageType) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer create(Integer id, AlertMessage entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer deleteByID(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AlertMessage findByID(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer update(AlertMessage entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<AlertMessageBuilder> getMessageBuilders(AlertMessageDeliveryType messageType) 
    {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Long uid=-1L;
        Integer numEscalationMsgs=0; //used to avoid unnecessary queries that won't have results
        Integer numNoEscalationMsgs=0;
        ArrayList<AlertMessageBuilder> recordList = new ArrayList<AlertMessageBuilder>();
              
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement("UPDATE msgQueueGuid set id = LAST_INSERT_ID((id+1)%1000000000) WHERE sequence=1", Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            uid=statement.getLastInsertID();

//TODO the backend needs to insert escalations with status=3 and escalationOrdinal=0 and should copy a.escalationCallDelay to m.escalationCallDelay


            statement = (PreparedStatement) conn.prepareStatement(
                    "UPDATE message SET owner=?, message.modified=utc_timestamp() WHERE owner=0 AND status = 1 AND deliveryMethodID = ? LIMIT 100");
            statement.setLong(1, uid);
            statement.setInt(2, messageType.getCode());
            numNoEscalationMsgs=statement.executeUpdate();    

            if (AlertMessageDeliveryType.PHONE.equals(messageType))
            {
                conn.setAutoCommit(false);

                //prepare to send escalations
                statement = (PreparedStatement) conn.prepareStatement(
                    "UPDATE message" 
                        + " JOIN alert ON alert.alertID=message.alertID"
                        + " SET message.owner=?, message.escalationTryTime=utc_timestamp(), message.escalationTryCount=message.escalationTryCount+1, message.modified=utc_timestamp()"
                        + " WHERE message.status=3 AND message.deliveryMethodID = ? AND (message.owner=0 OR DATE_ADD(message.escalationTryTime, INTERVAL alert.escalationCallDelay SECOND) > utc_timestamp())");
                statement.setLong(1, uid);
                statement.setInt(2, messageType.getCode());
                numEscalationMsgs=statement.executeUpdate();               

                //update escalations
                if (numEscalationMsgs>0)
                {
                    statement = (PreparedStatement) conn.prepareStatement(
                            "UPDATE message "
                                + " JOIN alert ON alert.alertID=message.alertID"
                                + " JOIN alertEscalationPersons nextPerson ON message.alertID=nextPerson.alertID AND message.escalationOrdinal+1 = nextPerson.escalationOrder"
                                + " JOIN alertEscalationPersons mailPerson ON message.alertID=mailPerson.alertID AND mailPerson.escalationOrder = -1"
                                + " SET message.escalationStartTime=utc_timestamp(), message.escalationTryCount=1, message.modified=utc_timestamp()"
                                + " , message.personID = IF(nextPerson.personID<>NULL, nextPerson.personID, mailPerson.personID)"
                                + " , message.deliveryMethodID = IF(nextPerson.personID<>NULL, message.deliveryMethodID, 3)"
                                + " WHERE message.status=3 AND message.owner = ? AND nextPerson.personID <> NULL"
                                + " AND (DATE_ADD(message.escalationStartTime, INTERVAL alert.escalationTryTimeLimit SECOND) > utc_timestamp()"
                                + "        OR message.escalationTryCount>alert.escalationTryLimit)");
                    statement.setLong(1, uid);
                    statement.executeUpdate(); 
                }
                
                conn.commit();
                conn.setAutoCommit(true);
            }
            
            statement = (PreparedStatement) conn.prepareStatement(
                    "SELECT msgID,noteID,personID,alertID,alertTypeID,created,modified,deliveryMethodID,address,message,status,level,owner,zoneID, IF(status=2,0,1) as acknowledge FROM message WHERE (status=1 OR status=3) AND owner=?");
            statement.setLong(1, uid);
            resultSet = statement.executeQuery();

            AlertMessage alertMessage = null;
            while ((numNoEscalationMsgs>0 || numEscalationMsgs>0) && resultSet.next())
            {
                alertMessage = new AlertMessage();
                alertMessage.setMessageID(resultSet.getInt(1));
                alertMessage.setNoteID(resultSet.getLong(2));
                alertMessage.setPersonID(resultSet.getInt(3));
                alertMessage.setAlertID(resultSet.getInt(4));
                alertMessage.setAlertMessageType(AlertMessageType.valueOf(resultSet.getInt(5)));
                alertMessage.setAlertMessageDeliveryType(AlertMessageDeliveryType.valueOf(resultSet.getInt(8)));
                alertMessage.setAddress(resultSet.getString(9));
                alertMessage.setMessage(resultSet.getString(10));
                alertMessage.setLevel(RedFlagLevel.valueOf(resultSet.getInt(12)));
                alertMessage.setZoneID(resultSet.getInt(14));
                alertMessage.setAcknowledge(resultSet.getBoolean(15));
                
                Event event = eventDAO.findByID(alertMessage.getNoteID());
                AlertMessageBuilder alertMessageBuilder = this.createAlertMessageBuilder(alertMessage, event);
                recordList.add(alertMessageBuilder);    
                
                statement = (PreparedStatement) conn.prepareStatement("INSERT INTO messageLog (msgID, personID, created) VALUES(?, ?, utc_timestamp())");
                statement.setInt(1,alertMessage.getMessageID());
                statement.setInt(2,alertMessage.getPersonID());
                statement.execute();
            }
            
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw new ProDAOException(statement.toString(), e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally

        return recordList;
        
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
        alertMessageBuilder.setAcknowledge(alertMessage.getAcknowledge());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d, yyyy h:mm a (z)");
        
        // Check for unknown driver
        if ( (driver != null) && (driver.getPerson() != null) ) {
            simpleDateFormat.setTimeZone(driver.getPerson().getTimeZone());
        } else {
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        }
        
        // Construct the message parameter list
        List<String> parameterList = new ArrayList<String>();
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
}
