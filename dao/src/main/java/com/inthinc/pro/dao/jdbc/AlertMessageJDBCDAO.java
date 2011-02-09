package com.inthinc.pro.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import com.inthinc.pro.model.AlertEscalationStatus;
import com.inthinc.pro.model.AlertMessage;
import com.inthinc.pro.model.AlertMessageBuilder;
import com.inthinc.pro.model.AlertMessageDeliveryType;
import com.inthinc.pro.model.AlertMessageType;
import com.inthinc.pro.model.AlertSentStatus;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.NoAddressFoundException;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.RedFlagLevel;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.SpeedingEvent;

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
    //3 escalated awaiting acknowledge
    //4 escalated acknowledged
    //5 canceled 

    @Override
    public Boolean acknowledgeMessage(Integer msgID) {
        
        if (msgID==null) return false;
        
        Connection conn = null;
        Integer numRows=0;
        PreparedStatement statement = null;
        try
        {
            conn = getConnection();
            // if (status == 1) status=2; else if (status == 3) status= 4; else status = status;
            statement = (PreparedStatement) conn.prepareStatement("UPDATE message SET status=IF(status=1,2,IF(status=6,4,status)), modified=utc_timestamp() WHERE msgID=?");
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
    public Boolean cancelPendingMessage(Integer msgID) {
        Connection conn = null;
        Integer numRows=0;
        PreparedStatement statement = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement("UPDATE message SET status=5, modified=utc_timestamp() WHERE msgID=?");
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

    String FETCH_ALERT_MESSAGE= "SELECT noteID,personID,alertID,alertTypeID,deliveryMethodID,status,level,escalationOrdinal,escalationTryCount FROM message WHERE msgID = ?";

    @Override
    public AlertMessage findByID(Integer id) {
        Connection conn = null;
        java.sql.PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try
        {
            conn = getConnection();
            statement = conn.prepareStatement(FETCH_ALERT_MESSAGE);
            statement.setInt(1, id);
            System.out.println("findAlertMessageByID - statement is: "+FETCH_ALERT_MESSAGE+id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                AlertMessage alertMessage = new AlertMessage();
                alertMessage.setMessageID(id);
                alertMessage.setNoteID(resultSet.getLong(1));
                alertMessage.setPersonID(resultSet.getInt(2));
                alertMessage.setAlertID(resultSet.getInt(3));
                alertMessage.setAlertMessageType(AlertMessageType.valueOf(resultSet.getInt(4)));
                alertMessage.setAlertMessageDeliveryType(AlertMessageDeliveryType.valueOf(resultSet.getInt(5)));
                alertMessage.setStatus(AlertEscalationStatus.valueOf(resultSet.getInt(6)));
                alertMessage.setLevel(RedFlagLevel.valueOf(resultSet.getInt(7)));
                alertMessage.setEscalationOrdinal(resultSet.getInt(8));
                alertMessage.setEscalationTryCount(resultSet.getInt(9));
                return alertMessage;
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
        
        return null;
    }

    @Override
    public Integer update(AlertMessage entity) {
        // TODO Auto-generated method stub
        return null;
    }

//    @Override
//    public synchronized List<AlertMessageBuilder> getMessageBuilders(AlertMessageDeliveryType messageType) 
//    {
//        Connection conn = null;
//        PreparedStatement statement = null;
//        ResultSet resultSet = null;
//        Long uid=-1L;
//        Integer numEscalationMsgs=0; //used to avoid unnecessary queries that won't have results
//        Integer numNoEscalationMsgs=0;
//        ArrayList<AlertMessageBuilder> recordList = new ArrayList<AlertMessageBuilder>();
//              
//        try
//        {
//            conn = getConnection();
///* 
//            
//            statement = (PreparedStatement) conn.prepareStatement("UPDATE msgQueueGuid set id = LAST_INSERT_ID((id+1)%1000000000) WHERE sequence=1", Statement.RETURN_GENERATED_KEYS);
//            statement.executeUpdate();
//             We are using the mysql driver specfic method to retreive the LastInsertID(). If there are issues with this, we can use one of the following methods:
//             http://dev.mysql.com/doc/refman/5.1/en/connector-j-usagenotes-basic.html#connector-j-examples-autoincrement-getgeneratedkeys
//             http://dev.mysql.com/doc/refman/5.1/en/connector-j-usagenotes-basic.html#connector-j-examples-autoincrement-select
//             
//             this was throwing class cast exception, so went with  #2 above
//            uid=((com.mysql.jdbc.PreparedStatement)statement).getLastInsertID();
//*/
//            
//            uid = getLastInsertID(conn);
//
////update the owner (ie the job number) in the new message records that are of the right delivery type and that don't have an owner yet and are not voice escalation items
//            statement = (PreparedStatement) conn.prepareStatement(
//                    "UPDATE message SET owner=?, message.modified=utc_timestamp() WHERE owner=0 AND status = 1 AND deliveryMethodID = ? LIMIT 100");
//            statement.setLong(1, uid);
//            statement.setInt(2, messageType.getCode());
//            numNoEscalationMsgs=statement.executeUpdate();    
//
////            if (AlertMessageDeliveryType.PHONE.equals(messageType))
////            {
//                conn.setAutoCommit(false);
//                
//                //prepare to send escalations to those that have status=3 (Awaiting acknowledge) and that are due to be sent
//                statement = (PreparedStatement) conn.prepareStatement(
//                    "UPDATE message" 
//                        + " JOIN alert ON alert.alertID=message.alertID"
//                        + " SET message.owner=?, message.escalationTryTime=utc_timestamp(), message.escalationTryCount=ifnull(message.escalationTryCount,0)+1, message.modified=utc_timestamp(), message.escalationStartTime=IF(ISNULL(message.escalationStartTime),utc_timestamp(),message.escalationStartTime)"
//                        + " WHERE message.status=3 AND message.deliveryMethodID = ? AND (message.owner=0 OR DATE_ADD(message.escalationTryTime, INTERVAL alert.escalationCallDelay SECOND) < utc_timestamp())");
//                statement.setLong(1, uid);
//                statement.setInt(2, messageType.getCode());
//                numEscalationMsgs=statement.executeUpdate();               
//
//                //update escalations with the next person to be called or emailed if all calls have failed
//                if (numEscalationMsgs>0)
//                {
//                    if(AlertMessageDeliveryType.PHONE.equals(messageType)){
//                        statement = (PreparedStatement) conn.prepareStatement(
//                                "UPDATE message "
//                                    + " JOIN alert ON alert.alertID=message.alertID"
//                                    + " LEFT JOIN alertEscalationPersons nextPerson ON message.alertID=nextPerson.alertID AND message.escalationOrdinal+1 = nextPerson.escalationOrder"
//                                    + " JOIN alertEscalationPersons mailPerson ON message.alertID=mailPerson.alertID AND mailPerson.escalationOrder = -1"
//                                    + " SET message.escalationStartTime=utc_timestamp(), message.escalationTryCount=1, message.modified=utc_timestamp(), message.escalationOrdinal=message.escalationOrdinal+1"
//                                    + " , message.personID = IF(ISNULL(nextPerson.personID), mailPerson.personID, nextPerson.personID)"
//                                    + " , message.deliveryMethodID = IF(ISNULL(nextPerson.personID), 3, message.deliveryMethodID)"
//                                    + " WHERE message.status=3 AND message.owner = ? "
//                                    + " AND (((alert.escalationTryTimeLimit IS NOT NULL) AND DATE_ADD(message.escalationStartTime, INTERVAL alert.escalationTryTimeLimit SECOND) < utc_timestamp())"
//                                    + "        OR ((alert.escalationTryLimit IS NOT NULL) AND message.escalationTryCount > alert.escalationTryLimit))");
//                        statement.setLong(1, uid);
//                        statement.executeUpdate(); 
//                        
//                        //remove from the job those that now don't have the chosen delivery type. 
//                        statement = (PreparedStatement) conn.prepareStatement(
//                                "UPDATE message "
//                                + "SET owner=0, modified=utc_timestamp()"
//                                + " WHERE owner = ? AND deliveryMethodID != ?"
//                                );
//                        statement.setLong(1, uid);
//                        statement.setInt(2, messageType.getCode());
//                        statement.executeUpdate(); 
//                        
//                    }                    
//                    statement = (PreparedStatement) conn.prepareStatement(
//                            "UPDATE message "
//                            + "SET status=2, modified=utc_timestamp()"
//                            + " WHERE status=1 and owner = ?"
//                            );
//                    statement.setLong(1, uid);
//                    statement.executeUpdate();  
//
//                }
//                
//                conn.commit();
//                conn.setAutoCommit(true);
////            }
//            // Grab all the messages for this job
//            statement = (PreparedStatement) conn.prepareStatement(
//                    "SELECT msgID,noteID,personID,alertID,alertTypeID,created,modified,deliveryMethodID,address,message,status,level,owner,zoneID, IF(status=2,0,1) as acknowledge FROM message WHERE owner=?");
//            statement.setLong(1, uid);
//            resultSet = statement.executeQuery();
//
//            AlertMessage alertMessage = null;
//            while ((numNoEscalationMsgs>0 || numEscalationMsgs>0) && resultSet.next())
//            {
//                alertMessage = new AlertMessage();
//                alertMessage.setMessageID(resultSet.getInt("msgID"));
//                alertMessage.setNoteID(resultSet.getLong("noteID"));
//                alertMessage.setPersonID(resultSet.getInt("personID"));
//                alertMessage.setAlertID(resultSet.getInt("alertID"));
//                alertMessage.setAlertMessageType(AlertMessageType.valueOf(resultSet.getInt("alertTypeID")));
////System.out.println("alertMessage type: " + alertMessage.getAlertMessageType());                
//                alertMessage.setAlertMessageDeliveryType(AlertMessageDeliveryType.valueOf(resultSet.getInt("deliveryMethodID")));
//                alertMessage.setMessage(resultSet.getString("message"));
//                
//                //We ignore what harry returns in address column for address aka phone/email
//                
//                alertMessage.setLevel(RedFlagLevel.valueOf(resultSet.getInt("level")));
//                alertMessage.setZoneID(resultSet.getInt("zoneID"));
//                alertMessage.setAcknowledge(resultSet.getBoolean("acknowledge"));
//                
//                Event event = eventDAO.findByID(alertMessage.getNoteID());
//                AlertMessageBuilder alertMessageBuilder = this.createAlertMessageBuilder(alertMessage, event, messageType);
//                //Just this time set it to cancelled
//                if(alertMessageBuilder != null){
//                    recordList.add(alertMessageBuilder); 
//                }
//                
//                statement = (PreparedStatement) conn.prepareStatement("INSERT INTO messageLog (msgID, personID, created) VALUES(?, ?, utc_timestamp())");
//                statement.setInt(1,alertMessage.getMessageID());
//                statement.setInt(2,alertMessage.getPersonID());
//                statement.execute();
//            }
//            
//        }   // end try
//        catch (SQLException e)
//        { // handle database hosLogs in the usual manner
//            throw new ProDAOException(statement.toString(), e);
//        }   // end catch
//        finally
//        { // clean up and release the connection
//            close(resultSet);
//            close(statement);
//            close(conn);
//        } // end finally
//
//        return recordList;
//        
//    }
    @Override
    public synchronized List<AlertMessageBuilder> getMessageBuilders(AlertMessageDeliveryType messageType) 
    {
        Connection conn = null;
        Long owner=-1L;
        List<AlertMessageBuilder> recordList = new ArrayList<AlertMessageBuilder>();
              
        try
        {
            conn = getConnection();
            
            owner = getNextJobOwner(conn);
            
            runMessageWatchDog(conn, owner, messageType);
            
            recordList = getScheduledMessages(conn, owner, messageType);
            
        }
        catch (SQLException e)
        { // handle database errors in the usual manner
            throw new ProDAOException("getMessageBuilders", e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(conn);
        } // end finally

        return recordList;
    }

    private Long getNextJobOwner(Connection conn) 
    {
        Statement statement = null;
        ResultSet resultSet = null;
        Long uid = 0l;
        try
        {
            statement = conn.createStatement();
            statement.executeUpdate("UPDATE msgQueueGuid set id = LAST_INSERT_ID((id+1)%1000000000) WHERE sequence=1");
            resultSet = statement.executeQuery("SELECT LAST_INSERT_ID()");

            if (resultSet.next()) {
                uid = resultSet.getLong(1);
//System.out.println("uid = " + uid);                
            }
        }   // end try
        catch (SQLException e)
        { // handle database errors in the usual manner
            throw new ProDAOException(statement.toString(), e);
        }   // end catch
        finally
        { // clean up 
            close(resultSet);
            close(statement);
        } // end finally   
        return uid;
    }
    private void runMessageWatchDog(Connection conn, Long owner, AlertMessageDeliveryType messageType){
        
        //call stored procedure to update escalations and pick messages to send
        CallableStatement callableStatement = null;
        try{
            callableStatement = conn.prepareCall("call messageWatchDog(?,?)"); 
            callableStatement.setInt(1, messageType.getCode()); 
            callableStatement.setLong(2, owner); 
    
            callableStatement.execute();
        }
        catch (SQLException e)
        { // handle database errors in the usual manner
            throw new ProDAOException(callableStatement.toString(), e);
        }   // end catch
        finally
        { // clean up 
            close(callableStatement);
        } // end finally   
    }
    
    private List<AlertMessageBuilder> getScheduledMessages(Connection conn, Long owner,AlertMessageDeliveryType messageType){
        
        List<AlertMessageBuilder> recordList = new ArrayList<AlertMessageBuilder>();
        
        PreparedStatement preparedStatement = null;
        ResultSet messageResultSet = null;
        try{
            // Grab all the messages for this job
            preparedStatement = (PreparedStatement) conn.prepareStatement(
                    "SELECT msgID,noteID,personID,alertID,alertTypeID,created,modified,deliveryMethodID,address,message,status,level,owner,zoneID, IF(status=2,0,1) as acknowledge FROM message WHERE owner=?");
            preparedStatement.setLong(1, owner);
            messageResultSet = preparedStatement.executeQuery();
            
            AlertMessage alertMessage = null;
            while (messageResultSet.next())
            {
                alertMessage = new AlertMessage();
                alertMessage.setMessageID(messageResultSet.getInt("msgID"));
                alertMessage.setNoteID(messageResultSet.getLong("noteID"));
                alertMessage.setPersonID(messageResultSet.getInt("personID"));
                alertMessage.setAlertID(messageResultSet.getInt("alertID"));
                alertMessage.setAlertMessageType(AlertMessageType.valueOf(messageResultSet.getInt("alertTypeID")));
    //System.out.println("alertMessage type: " + alertMessage.getAlertMessageType());                
                alertMessage.setAlertMessageDeliveryType(AlertMessageDeliveryType.valueOf(messageResultSet.getInt("deliveryMethodID")));
                alertMessage.setMessage(messageResultSet.getString("message"));
                              
                alertMessage.setLevel(RedFlagLevel.valueOf(messageResultSet.getInt("level")));
                alertMessage.setZoneID(messageResultSet.getInt("zoneID"));
                alertMessage.setAcknowledge(messageResultSet.getBoolean("acknowledge"));
                
                Event event = eventDAO.findByID(alertMessage.getNoteID());
                AlertMessageBuilder alertMessageBuilder = this.createAlertMessageBuilder(alertMessage, event, messageType);
                //Just this time set it to cancelled
                if(alertMessageBuilder != null){
                    recordList.add(alertMessageBuilder); 
                }
                
                logMessage(conn, alertMessage.getMessageID(), alertMessage.getPersonID());
            }
        }
        catch (SQLException e)
        { // handle database errors in the usual manner
            throw new ProDAOException(preparedStatement.toString(), e);
        }   // end catch
        finally
        { // clean up 
            close(preparedStatement);
            close(messageResultSet);
        } // end finally   
        return recordList;
    }
    private void logMessage(Connection conn, Integer messageID,Integer personID){
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = conn.prepareStatement("INSERT INTO messageLog (msgID, personID, created) VALUES(?, ?, utc_timestamp())");
            preparedStatement.setInt(1,messageID);
            preparedStatement.setInt(2,personID);
            preparedStatement.execute();
        }
        catch (SQLException e)
        { // handle database errors in the usual manner
            throw new ProDAOException(preparedStatement.toString(), e);
        }   // end catch
        finally
        { // clean up 
            close(preparedStatement);
        } // end finally   

    }
    private AlertMessageBuilder createAlertMessageBuilder(AlertMessage alertMessage, Event event, AlertMessageDeliveryType messageType) {
        if (alertMessage.getPersonID() == null || alertMessage.getPersonID() == 0 || event == null) {
            logger.debug("Person ID or Event is Null " + alertMessage.getPersonID() + event);
            return null;
        }
        
        Person person = personDAO.findByID(alertMessage.getPersonID());
        if (person == null) return null;
        
        logger.debug("Preparing message for: " + person.getFullName());
        
        AlertMessageBuilder alertMessageBuilder = createBuilder(alertMessage, person, messageType);
        
        List<String> parameterList = getParameterList(event, alertMessage, person);
        alertMessageBuilder.setParamterList(parameterList);
        
        return alertMessageBuilder;
    }
    
    private String getAddress(Person person, AlertMessageDeliveryType messageType){
        if (AlertMessageDeliveryType.EMAIL.equals(messageType))
            return person.getPriEmail();    
        else if(AlertMessageDeliveryType.TEXT_MESSAGE.equals(messageType))
            return person.getPriText();
        else if (AlertMessageDeliveryType.PHONE.equals(messageType))
            return person.getPriPhone();    
        return null;
    }
    private AlertMessageBuilder createBuilder(AlertMessage alertMessage, Person person, AlertMessageDeliveryType messageType){
        
        alertMessage.setAddress(getAddress(person, messageType));
        
        AlertMessageBuilder alertMessageBuilder = new AlertMessageBuilder();
        alertMessageBuilder.setLocale(getLocale(person));
        alertMessageBuilder.setAddress(alertMessage.getAddress());
        alertMessageBuilder.setAlertID(alertMessage.getAlertID());
        alertMessageBuilder.setMessageID(alertMessage.getMessageID());
        alertMessageBuilder.setAlertMessageType(alertMessage.getAlertMessageType());
        alertMessageBuilder.setAcknowledge(alertMessage.getAcknowledge());
        
        return alertMessageBuilder;
    }
    private Locale getLocale(Person person){
        Locale locale = Locale.ENGLISH;
        if (person != null && person.getLocale() != null)
            locale = person.getLocale();
        return locale;
    }

    private List<String> getParameterList(Event event, AlertMessage alertMessage,Person person){
        
      List<String> parameterList = new ArrayList<String>();
      
      parameterList = addDriverRelatedData(event, parameterList);
      parameterList = addVehicleRelatedData(event, parameterList);
      parameterList = addAlertRelatedData(event, person, alertMessage, parameterList);
      
      return parameterList;
    }
    private List<String> addDriverRelatedData(Event event, List<String> parameterList){
        
        Driver driver = driverDAO.findByID(event.getDriverID());
        
        SimpleDateFormat driverDateFormat = getDriverDate(driver);     
        // Construct the message parameter list
        parameterList.add(driverDateFormat.format(event.getTime()));
        
        String driverFullName = getDriverFullName(driver);
        parameterList.add(driverFullName);
        
        return parameterList;
    }
    private SimpleDateFormat getDriverDate(Driver driver){
        
        SimpleDateFormat driverDateFormat = new SimpleDateFormat("MMM d, yyyy h:mm a (z)");
        
        if ( (driver != null) && (driver.getPerson() != null) ) {
            driverDateFormat.setTimeZone(driver.getPerson().getTimeZone());
        } else {
            driverDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        }
        return driverDateFormat;
    }
    private String getDriverFullName(Driver driver){
        
        if (driver != null && driver.getPerson() != null)
           return driver.getPerson().getFullName();
        else {
            return "";
        }
        
    }
    private List<String> addVehicleRelatedData(Event event, List<String> parameterList){
        
        Vehicle vehicle = vehicleDAO.findByID(event.getVehicleID());
        parameterList.add(vehicle.getName());
        
        return parameterList;
    }
    private List<String> addAlertRelatedData(Event event, Person person, AlertMessage alertMessage, List<String> parameterList){
        
        switch (alertMessage.getAlertMessageType()) {
            case ALERT_TYPE_ENTER_ZONE:
            case ALERT_TYPE_EXIT_ZONE:
                
                parameterList = addZoneRelatedData(alertMessage.getZoneID(), parameterList);
                break;
            case ALERT_TYPE_SPEEDING:
                parameterList = addSpeedingRelatedData((SpeedingEvent) event,  person.getMeasurementType(), parameterList);
                break;
            case ALERT_TYPE_TAMPERING:
            case ALERT_TYPE_LOW_BATTERY:
                break;
            default:
                parameterList = addAddress(event, parameterList);
        }
       return parameterList;
    }
    private List<String> addZoneRelatedData(Integer zoneID, List<String> parameterList){
        
        Zone zone = zoneDAO.findByID(zoneID);
        if (zone == null) {
            logger.error("Zone could not be found for zoneID: " + zoneID);
        }
        else {
            parameterList.add(zone.getName());
        }

        return parameterList;
    }

    private List<String> addSpeedingRelatedData(SpeedingEvent event, MeasurementType measurementType, List<String> parameterList){
        Number topSpeed = MeasurementConversionUtil.convertSpeed(event.getTopSpeed(), measurementType);
        Number speedLimit = MeasurementConversionUtil.convertSpeed(event.getSpeedLimit(), measurementType);
        parameterList.add(String.valueOf(topSpeed));
        parameterList.add(String.valueOf(speedLimit));
        parameterList = addAddress(event, parameterList);
        
        return parameterList;

    }
    private List<String> addAddress(Event event, List<String> parameterList){
        
        try {
            parameterList.add(addressLookup.getAddress(new LatLng(event.getLatitude(), event.getLongitude()), true));
        }
        catch (NoAddressFoundException nafe){
            //Shouldn't happen because returning lat lng when there is no address
        }
        return parameterList;
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

    private static final String FETCH_RED_FLAG_MESSAGE_INFO_PREFIX = "SELECT noteID, msgID, status FROM message WHERE noteID IN (";
    private static final String FETCH_RED_FLAG_MESSAGE_INFO_SUFFIX = ") order by noteID";


    @Override
    public void fillInRedFlagMessageInfo(List<RedFlag> redFlagList) {
        Map<Long, RedFlag> redFlagMap = new HashMap<Long, RedFlag>();
        StringBuffer noteIDList = new StringBuffer();
        for(RedFlag redFlag : redFlagList) {
            redFlag.setMsgIDList(new ArrayList<Integer>());
            redFlag.setSent(AlertSentStatus.NONE);
            if (noteIDList.length() > 0)
                noteIDList.append(",");
            noteIDList.append(redFlag.getEvent().getNoteID());
            redFlagMap.put(redFlag.getEvent().getNoteID(), redFlag);
        }
        
        
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn = getConnection();
            statement = conn.createStatement();
            resultSet = statement.executeQuery(FETCH_RED_FLAG_MESSAGE_INFO_PREFIX + noteIDList.toString() + FETCH_RED_FLAG_MESSAGE_INFO_SUFFIX);

            while (resultSet.next()) {
                long noteID = resultSet.getLong(1);
                int msgID = resultSet.getInt(2);
                AlertEscalationStatus status = AlertEscalationStatus.valueOf(resultSet.getInt(3));
                RedFlag redFlag = redFlagMap.get(noteID);
                redFlag.getMsgIDList().add(msgID);
//System.out.println("msgStatus: " + status + " redFlag status: " + redFlag.getSent());            
                if (redFlag.getSent() != AlertSentStatus.PENDING && redFlag.getSent() != AlertSentStatus.CANCELED) {
                    
                    if (status == AlertEscalationStatus.CANCELED)
                        redFlag.setSent(AlertSentStatus.CANCELED);
                    else if (status == AlertEscalationStatus.ESCALATED_AWAITING_ACKNOWLEDGE || 
                                status == AlertEscalationStatus.NEW)
                        redFlag.setSent(AlertSentStatus.PENDING);
                    else if (status == AlertEscalationStatus.SENT || status == AlertEscalationStatus.ESCALATED_ACKNOWLEDGED)
                        redFlag.setSent(AlertSentStatus.SENT);
                }
//System.out.println("new redFlag status: " + redFlag.getSent());            
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
        
    }
}
