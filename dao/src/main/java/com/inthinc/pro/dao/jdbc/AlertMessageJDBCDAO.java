package com.inthinc.pro.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.RedFlagLevel;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.IdleEvent;
import com.inthinc.pro.model.event.SpeedingEvent;
import com.inthinc.pro.model.event.VersionEvent;

public class AlertMessageJDBCDAO extends GenericJDBCDAO implements AlertMessageDAO {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(AlertMessageJDBCDAO.class);
    private EventDAO eventDAO;
    private PersonDAO personDAO;
    private VehicleDAO vehicleDAO;
    private DriverDAO driverDAO;
    private ZoneDAO zoneDAO;
    private AddressLookup addressLookup;
    // private FormsDAO formsDAO;
    private String formSubmissionsURL;

    // message status
    // 1 new
    // 2 sent
    // 3 escalated awaiting acknowledge
    // 4 escalated acknowledged
    // 5 canceled

    // public FormsDAO getFormsDAO() {
    // return formsDAO;
    // }
    //
    // public void setFormsDAO(FormsDAO formsDAO) {
    // this.formsDAO = formsDAO;
    // }

    public String getFormSubmissionsURL() {
        return formSubmissionsURL;
    }

    public void setFormSubmissionsURL(String formSubmissionsURL) {
        this.formSubmissionsURL = formSubmissionsURL;
    }

    @Override
    public Boolean acknowledgeMessage(Integer msgID) {

        if (msgID == null)
            return false;

        Connection conn = null;
        Integer numRows = 0;
        PreparedStatement statement = null;
        try {
            conn = getConnection();
            // if (status == 1) status=2; else if (status == 3) status= 4; else status = status;
            statement = (PreparedStatement) conn.prepareStatement("UPDATE message SET status=IF(status=1,2,IF(status=3 or status=6,4,status)), modified=utc_timestamp() WHERE msgID=?");
            statement.setInt(1, msgID);
            numRows = statement.executeUpdate();
        } // end try
        catch (SQLException e) { // handle database hosLogs in the usual manner
            throw new ProDAOException(statement.toString(), e);
        } // end catch
        finally { // clean up and release the connection
            close(statement);
            close(conn);
        } // end finally
        return numRows != 0;
    }

    @Override
    public Boolean cancelPendingMessage(Integer msgID) {
        Connection conn = null;
        Integer numRows = 0;
        PreparedStatement statement = null;
        try {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement("UPDATE message SET status=5, modified=utc_timestamp() WHERE msgID=?");
            statement.setInt(1, msgID);
            numRows = statement.executeUpdate();
        } // end try
        catch (SQLException e) { // handle database hosLogs in the usual manner
            throw new ProDAOException(statement.toString(), e);
        } // end catch
        finally { // clean up and release the connection
            close(statement);
            close(conn);
        } // end finally
        return numRows != 0;
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

    final String FETCH_ALERT_MESSAGE = "SELECT noteID,personID,alertID,alertTypeID,deliveryMethodID,status,level,escalationOrdinal,escalationTryCount FROM message WHERE msgID = ?";

    @Override
    public AlertMessage findByID(Integer id) {
        Connection conn = null;
        java.sql.PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = getConnection();
            statement = conn.prepareStatement(FETCH_ALERT_MESSAGE);
            statement.setInt(1, id);
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

        } // end try
        catch (SQLException e) { // handle database hosLogs in the usual manner
            throw new ProDAOException(statement.toString(), e);
        } // end catch
        finally { // clean up and release the connection
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

    @Override
    public synchronized List<AlertMessageBuilder> getMessageBuilders(AlertMessageDeliveryType messageDeliveryType) {

        List<AlertMessageBuilder> messageBuilders = new ArrayList<AlertMessageBuilder>();
        if (messageDeliveryType == null)
            return messageBuilders;

        Connection conn = null;
        try {
            conn = getConnection();

            Long owner = getNextJobOwner(conn);

            runMessageWatchDog(conn, owner, messageDeliveryType);

            List<AlertMessage> messages = getScheduledMessages(conn, owner);

            messageBuilders = getMessageBuilders(conn,messages, messageDeliveryType);

        } catch (SQLException e) {
            throw new ProDAOException("getMessageBuilders", e);
        } catch (ProDAOException e) { // handle database errors in the usual manner
            throw e;
        } // end catch
        finally { // clean up and release the connection
            close(conn);
        } // end finally

        return messageBuilders;
    }

    private List<AlertMessageBuilder> getMessageBuilders(Connection conn, List<AlertMessage> messages, AlertMessageDeliveryType messageDeliveryType) {
        List<AlertMessageBuilder> messageBuilders = new ArrayList<AlertMessageBuilder>();

        for (AlertMessage alertMessage : messages) {
            Person person = getPerson(alertMessage.getPersonID());
            if (person == null) {
                continue;
            }
            logger.debug("Preparing message for: " + person.getFullName());


            Event event = eventDAO.findByID(alertMessage.getNoteID());
            if (event == null) {
                logger.debug("event is Null ");
                continue;
            }

            Locale locale = getLocale(person);
            
            List<String> parameterList = new ParameterList().getParameterList(event, person.getMeasurementType(), alertMessage.getAlertMessageType(), locale, alertMessage.getZoneID());
            if (!alertReady(parameterList))
                continue;

            if (!AlertEscalationStatus.SENT.equals(alertMessage.getStatus())) {
                alertMessage.setAddress(getAlertMessageAddress(person, messageDeliveryType));
            }
            AlertMessageBuilder  alertMessageBuilder = new AlertMessageBuilder(alertMessage.getAlertID(), 
                    alertMessage.getMessageID(), 
                    locale, 
                    alertMessage.getAddress(),
                    alertMessage.getAlertMessageType(), 
                    alertMessage.getAcknowledge(),
                    parameterList);

            if (alertMessageBuilder != null) {
                messageBuilders.add(alertMessageBuilder);
            }

            logMessage(conn, alertMessage.getMessageID(), alertMessage.getPersonID());
        }
        return messageBuilders;
    }
    private Long getNextJobOwner(Connection conn) {
        Statement statement = null;
        ResultSet resultSet = null;
        Long uid = 0l;
        try {
            statement = conn.createStatement();
            statement.executeUpdate("UPDATE msgQueueGuid set id = LAST_INSERT_ID((id+1)%1000000000) WHERE sequence=1");
            resultSet = statement.executeQuery("SELECT LAST_INSERT_ID()");

            if (resultSet.next()) {
                uid = resultSet.getLong(1);
                // System.out.println("uid = " + uid);
            }
        } // end try
        catch (SQLException e) { // handle database errors in the usual manner
            throw new ProDAOException(statement.toString(), e);
        } // end catch
        finally { // clean up
            close(resultSet);
            close(statement);
        } // end finally
        return uid;
    }

    private void runMessageWatchDog(Connection conn, Long owner, AlertMessageDeliveryType messageDeliveryType) {

        // call stored procedure to update escalations and pick messages to send
        CallableStatement callableStatement = null;
        try {
            callableStatement = conn.prepareCall("call messageWatchDog(?,?)");
            callableStatement.setInt(1, messageDeliveryType.getCode());
            callableStatement.setLong(2, owner);

            callableStatement.execute();
        } catch (SQLException e) { // handle database errors in the usual manner
            throw new ProDAOException(callableStatement.toString(), e);
        } // end catch
        finally { // clean up
            close(callableStatement);
        } // end finally
    }

    private List<AlertMessage> getScheduledMessages(Connection conn, Long owner) {
        PreparedStatement preparedStatement = null;
        ResultSet messageResultSet = null;
        List<AlertMessage> messages = new ArrayList<AlertMessage>();
        try {
            // Grab all the messages for this job
            preparedStatement = (PreparedStatement) conn
                    .prepareStatement("SELECT msgID,noteID,personID,alertID,alertTypeID,created,modified,deliveryMethodID,address,message,status,level,owner,zoneID, IF(status=2,0,1) as acknowledge FROM message WHERE owner=?");
            preparedStatement.setLong(1, owner);
            messageResultSet = preparedStatement.executeQuery();

            while (messageResultSet.next()) {

                AlertMessage alertMessage = new AlertMessage(messageResultSet.getInt("msgID"), AlertMessageDeliveryType.valueOf(messageResultSet.getInt("deliveryMethodID")),
                        AlertMessageType.valueOf(messageResultSet.getInt("alertTypeID")), RedFlagLevel.valueOf(messageResultSet.getInt("level")), messageResultSet.getString("address"),
                        messageResultSet.getString("message"), messageResultSet.getLong("noteID"), messageResultSet.getInt("personID"), messageResultSet.getInt("alertID"),
                        messageResultSet.getInt("zoneID"), messageResultSet.getBoolean("acknowledge"), AlertEscalationStatus.valueOf(messageResultSet.getInt("status")));
                messages.add(alertMessage);
            }
        } catch (SQLException e) { // handle database errors in the usual manner
            throw new ProDAOException(preparedStatement.toString(), e);
        } // end catch
        finally { // clean up
            close(preparedStatement);
            close(messageResultSet);
        } // end finally
        return messages;
    }

    private void logMessage(Connection conn, Integer messageID, Integer personID) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement("INSERT INTO messageLog (msgID, personID, created) VALUES(?, ?, utc_timestamp())");
            preparedStatement.setInt(1, messageID);
            preparedStatement.setInt(2, personID);
            preparedStatement.execute();
        } catch (SQLException e) { // handle database errors in the usual manner
            throw new ProDAOException(preparedStatement.toString(), e);
        } // end catch
        finally { // clean up
            close(preparedStatement);
        } // end finally

    }

    private Person getPerson(Integer personID) {
        if (personID == null || personID == 0) {
            logger.debug("Person ID is Null or invalid " + personID);
            return null;
        }

        Person person = personDAO.findByID(personID);
        if (person == null) {
            logger.debug("Person is Null" + personID);
            return null;
        }
        return person;
    }

    private boolean alertReady(List<String> parameterList) {
        return parameterList != null;
    }

    private String getAlertMessageAddress(Person person, AlertMessageDeliveryType messageDeliveryType) {
        if (AlertMessageDeliveryType.EMAIL.equals(messageDeliveryType))
            return person.getPriEmail();
        else if (AlertMessageDeliveryType.TEXT_MESSAGE.equals(messageDeliveryType))
            return person.getPriText();
        else
            // if (AlertMessageDeliveryType.PHONE.equals(messageDeliveryType))
            return person.getPriPhone();
    }

    private Locale getLocale(Person person) {
        Locale locale = Locale.ENGLISH;
        if (person != null && person.getLocale() != null)
            locale = person.getLocale();
        return locale;
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
        for (RedFlag redFlag : redFlagList) {
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
        try {
            conn = getConnection();
            statement = conn.createStatement();
            resultSet = statement.executeQuery(FETCH_RED_FLAG_MESSAGE_INFO_PREFIX + noteIDList.toString() + FETCH_RED_FLAG_MESSAGE_INFO_SUFFIX);

            while (resultSet.next()) {
                long noteID = resultSet.getLong(1);
                int msgID = resultSet.getInt(2);
                AlertEscalationStatus status = AlertEscalationStatus.valueOf(resultSet.getInt(3));
                RedFlag redFlag = redFlagMap.get(noteID);
                redFlag.getMsgIDList().add(msgID);
                // System.out.println("msgStatus: " + status + " redFlag status: " + redFlag.getSent());
                if (redFlag.getSent() != AlertSentStatus.PENDING && redFlag.getSent() != AlertSentStatus.CANCELED) {

                    if (status == AlertEscalationStatus.CANCELED)
                        redFlag.setSent(AlertSentStatus.CANCELED);
                    else if (status == AlertEscalationStatus.ESCALATED_AWAITING_ACKNOWLEDGE || status == AlertEscalationStatus.NEW)
                        redFlag.setSent(AlertSentStatus.PENDING);
                    else if (status == AlertEscalationStatus.SENT || status == AlertEscalationStatus.ESCALATED_ACKNOWLEDGED)
                        redFlag.setSent(AlertSentStatus.SENT);
                }
                // System.out.println("new redFlag status: " + redFlag.getSent());
            }
        } // end try
        catch (SQLException e) { // handle database hosLogs in the usual manner
            throw new ProDAOException(statement.toString(), e);
        } // end catch
        finally { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally

    }

    public class ParameterList {
        private List<String> parameterList;

        public List<String> getParameterList(Event event, MeasurementType personMeasurementType, AlertMessageType alertMessageType, Locale locale, Integer zoneID) {
            if (event == null)
                return null;
            parameterList = new ArrayList<String>();
            addDriverRelatedData(event.getDriverID(), event.getTime(), locale);
            addVehicleRelatedData(event.getVehicleID());
            addAlertRelatedData(event, personMeasurementType, alertMessageType, zoneID);
            return parameterList;
        }

        private void addDriverRelatedData(Integer driverID, Date eventTime, Locale locale) {
            Driver driver = driverDAO.findByID(driverID);
            SimpleDateFormat driverDateFormat = getDriverDate(driver, locale);
            // Construct the message parameter list
            parameterList.add(driverDateFormat.format(eventTime));

            String driverFullName = getDriverFullName(driver);
            parameterList.add(driverFullName);
        }

        private SimpleDateFormat getDriverDate(Driver driver, Locale locale) {

            SimpleDateFormat driverDateFormat = new SimpleDateFormat("MMM d, yyyy h:mm a (z)", locale);

            if ((driver != null) && (driver.getPerson() != null)) {
                driverDateFormat.setTimeZone(driver.getPerson().getTimeZone());
            } else {
                driverDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            }
            return driverDateFormat;
        }

        private String getDriverFullName(Driver driver) {

            if (driver != null && driver.getPerson() != null)
                return driver.getPerson().getFullName();
            else {
                return "";
            }
        }

        private void addVehicleRelatedData(Integer vehicleID) {
            Vehicle vehicle = vehicleDAO.findByID(vehicleID);
            parameterList.add(vehicle.getName());
        }

        private void addAlertRelatedData(Event event, MeasurementType personMeasurementType, AlertMessageType alertMessageType, Integer zoneID) {
            switch (alertMessageType) {
                case ALERT_TYPE_ENTER_ZONE:
                case ALERT_TYPE_EXIT_ZONE:
                    addZoneRelatedData(zoneID);
                    break;
                case ALERT_TYPE_SPEEDING:
                    if (!(event instanceof SpeedingEvent))
                        break;
                    addSpeedingRelatedData((SpeedingEvent) event, personMeasurementType);
                    addAddress(event);
                    break;
                case ALERT_TYPE_TAMPERING:
                case ALERT_TYPE_LOW_BATTERY:
                    break;
                case ALERT_TYPE_IDLING:
                    if (!(event instanceof IdleEvent))
                        break;
                    parameterList.add(String.valueOf(((IdleEvent) event).getTotalIdling() / 60));
                    addAddress(event);
                    break;
                case ALERT_TYPE_WITNESS_UPDATED:
                case ALERT_TYPE_FIRMWARE_CURRENT:
                case ALERT_TYPE_ZONES_CURRENT:
                case ALERT_TYPE_QSI_UPDATED:
                    addAddress(event);
                    if (event instanceof VersionEvent)
                        parameterList.add(((VersionEvent) event).getStatusMessageKey());
                    break;
                case ALERT_TYPE_DVIR_PRE_TRIP_FAIL:
                case ALERT_TYPE_DVIR_POST_TRIP_FAIL:
                case ALERT_TYPE_DVIR_PRE_TRIP_PASS:
                case ALERT_TYPE_DVIR_POST_TRIP_PASS:
                   addAddress(event);
                    parameterList.add(formSubmissionsURL);
                    // if (!addFailReasons(event)) {
                    // // form not available yet
                    // parameterList = null;
                    // return;
                    // }
                    break;
                case ALERT_TYPE_DVIR_DRIVEN_WITHOUT_INSPECTION:
                case ALERT_TYPE_DVIR_DRIVEN_INSPECTED_UNSAFE:
                case ALERT_TYPE_DVIR_NO_POST_TRIP_INSPECTION:
                    addAddress(event);
                    break;
                default:
                    addAddress(event);
            }
        }

        // private boolean addFailReasons(Event event) {
        // FormSubmission submission = formsDAO.getForm(event.getTime().getTime(), event.getVehicleID());
        // if (submission == null) {
        // return false;
        // }
        // parameterList.add(submission.toString());
        // return true;
        //
        // }

        private void addAddress(Event event) {
            parameterList.add(addressLookup.getAddressOrLatLng(new LatLng(event.getLatitude(), event.getLongitude())));
        }

        private void addZoneRelatedData(Integer zoneID) {

            Zone zone = zoneDAO.findByID(zoneID);
            if (zone == null) {
                logger.error("Zone could not be found for zoneID: " + zoneID);
            } else {
                parameterList.add(zone.getName());
            }
        }

        private void addSpeedingRelatedData(SpeedingEvent event, MeasurementType measurementType) {
            Number topSpeed = MeasurementConversionUtil.convertSpeed(event.getTopSpeed(), measurementType);
            parameterList.add(String.valueOf(topSpeed));

            Number speedLimit = MeasurementConversionUtil.convertSpeed(event.getSpeedLimit(), measurementType);
            parameterList.add(String.valueOf(speedLimit));
        }

        public List<String> getParameterList() {
            return parameterList;
        }
    }
}
