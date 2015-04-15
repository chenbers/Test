package com.inthinc.pro.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import com.inthinc.pro.aggregation.model.Note;
import com.inthinc.pro.dao.LocationDAO;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.event.FifteenMinuteBreakNotTakenEvent;
import com.inthinc.pro.model.event.NoteType;
import org.apache.log4j.Logger;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.comm.parser.attrib.Attrib;
import com.inthinc.pro.dao.AlertMessageDAO;
import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.ZoneDAO;
import com.inthinc.pro.dao.hessian.mapper.EventHessianMapper;
import com.inthinc.pro.dao.hessian.mapper.Mapper;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.model.AlertEscalationStatus;
import com.inthinc.pro.model.AlertMessage;
import com.inthinc.pro.model.AlertMessageBuilder;
import com.inthinc.pro.model.AlertMessageDeliveryType;
import com.inthinc.pro.model.AlertMessageType;
import com.inthinc.pro.model.AlertSentStatus;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.RedFlagLevel;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventAttr;
import com.inthinc.pro.model.event.IdleEvent;
import com.inthinc.pro.model.event.SpeedingEvent;
import com.inthinc.pro.model.event.VersionEvent;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import javax.swing.text.DateFormatter;

public class AlertMessageJDBCDAO extends GenericJDBCDAO implements AlertMessageDAO {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(AlertMessageJDBCDAO.class);
    private EventDAO eventDAO;
    private PersonDAO personDAO;
    private VehicleDAO vehicleDAO;
    private DriverDAO driverDAO;
    private DeviceDAO deviceDAO;
    private ZoneDAO zoneDAO;
    private GroupDAO groupDAO;
    private AddressLookup addressLookup;
    private LocationDAO locationDAO;
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

    final String FETCH_ALERT_MESSAGE = "SELECT noteID,driverID,vehicleID,deviceID,attribs,personID,alertID,alertTypeID,deliveryMethodID,status,level,escalationOrdinal,escalationTryCount FROM message WHERE msgID = ?";

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
                alertMessage.setDriverID(resultSet.getInt(2));
                alertMessage.setVehicleID(resultSet.getInt(3));
                alertMessage.setDeviceID(resultSet.getInt(4));
                alertMessage.setAttribs(resultSet.getString(5));
                alertMessage.setPersonID(resultSet.getInt(6));
                alertMessage.setAlertID(resultSet.getInt(7));
                alertMessage.setName(findAlertName(alertMessage.getAlertID()));
                alertMessage.setAlertMessageType(AlertMessageType.valueOf(resultSet.getInt(8)));
                alertMessage.setAlertMessageDeliveryType(AlertMessageDeliveryType.valueOf(resultSet.getInt(9)));
                alertMessage.setStatus(AlertEscalationStatus.valueOf(resultSet.getInt(10)));
                alertMessage.setLevel(RedFlagLevel.valueOf(resultSet.getInt(11)));
                alertMessage.setEscalationOrdinal(resultSet.getInt(12));
                alertMessage.setEscalationTryCount(resultSet.getInt(13));
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
    
    final String FETCH_ALERT_NAME = "SELECT name FROM alert WHERE alertID = ?";

    private String findAlertName(Integer alertID) {

        Connection conn = null;
        java.sql.PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = getConnection();
            statement = conn.prepareStatement(FETCH_ALERT_NAME);
            statement.setInt(1, alertID);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
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
        	try {
	            Person person = getPerson(alertMessage.getPersonID());
	            if (person == null) {
	                continue;
	            }
	            logger.debug("Preparing message for: " + person.getFullName());
	
	            Event event = null;
	            
	            if (alertMessage.getAttribs() == null || alertMessage.getAttribs().isEmpty())
	                event = eventDAO.findByID(alertMessage.getNoteID());
	            
	            if (event == null) {
	                logger.debug("alertMessage.getAttribs(): " + alertMessage.getAttribs());
	                if (alertMessage.getAttribs() != null && !alertMessage.getAttribs().equalsIgnoreCase("")) {
	                    Mapper mapper = new EventHessianMapper();
	                    event = mapper.convertToModelObject(Attrib.convertToHashMap(alertMessage.getAttribs()), Event.class);
	                    event.setDriverID(alertMessage.getDriverID());
	                    event.setVehicleID(alertMessage.getVehicleID());
	                    event.setDeviceID(alertMessage.getDeviceID());
	                    logger.debug("event: " + event);
	                }    
	                if (event == null) {
	                    logger.debug("event is Null ");
	                    continue;
	                }
	            }
	            
	            if(event instanceof SpeedingEvent){
		            if(!((SpeedingEvent)event).isValidEvent()){
		            	
		            	StringBuilder sb = new StringBuilder();
		            	
		            	sb.append("Invalid speeding event has occured! ")
		            	.append(" DriverID: ")
		            	.append(event.getDriverID() == null ? "Not Available " : String.valueOf(event.getDriverID()))
		            	.append(" VehicleID: ")
		            	.append(event.getVehicleID() == null ? "Not Available " : String.valueOf(event.getVehicleID()))
		            	.append(" DeviceID: ")
		            	.append(event.getDeviceID() == null ? "Not Available " : String.valueOf(event.getDeviceID()))
		            	.append(" SpeedLimit: ")
		            	.append(((SpeedingEvent)event).getSpeedLimit() == null ? "Not Available " : String.valueOf(((SpeedingEvent)event).getSpeedLimit()))
		            	.append(" Speed: ")
		            	.append(((SpeedingEvent)event).getSpeed()  == null ? "Not Available" : String.valueOf(((SpeedingEvent)event).getSpeed()));
		            	
		            	logger.error(sb.toString());
		            	
		            	continue;
		            }
	            }
	
	            Locale locale = getLocale(person);
	            
	            List<String> parameterList = new ParameterList().getParameterList(event, person.getMeasurementType(), alertMessage.getAlertMessageType(), locale, alertMessage.getZoneID());
	            if (!alertReady(parameterList))
	                continue;
	
	            if (!AlertEscalationStatus.SENT.equals(alertMessage.getStatus())) {
	                alertMessage.setAddress(getAlertMessageAddress(person, messageDeliveryType));
	            }
	            
	            AlertMessageBuilder  alertMessageBuilder;
	            if (AlertMessageDeliveryType.EMAIL == messageDeliveryType &&
	                AlertMessageType.getEzCrmAlertTypes().contains(alertMessage.getAlertMessageType())) {
	                
	                List<String> ezParameterList = new EzCrmParameterList().getParameterList(event, person, alertMessage, locale);
	                if (!alertReady(ezParameterList))
	                    continue;

	                alertMessageBuilder = new AlertMessageBuilder(alertMessage.getAlertID(),
	                                alertMessage.getName(),
	                                alertMessage.getMessageID(), 
	                                locale, 
	                                alertMessage.getAddress(),
	                                alertMessage.getAlertMessageType(), 
	                                alertMessage.getAcknowledge(),
	                                parameterList,
	                                ezParameterList);
	            } else {
	            
    	            alertMessageBuilder = new AlertMessageBuilder(alertMessage.getAlertID(),
    	                    alertMessage.getMessageID(), 
    	                    locale, 
    	                    alertMessage.getAddress(),
    	                    alertMessage.getAlertMessageType(), 
    	                    alertMessage.getAcknowledge(),
    	                    parameterList);
	            }
	
	            if (alertMessageBuilder != null) {
	                messageBuilders.add(alertMessageBuilder);
	            }
	
	            logMessage(conn, alertMessage.getMessageID(), alertMessage.getPersonID());
        	} catch(Throwable e) {
            	logger.error("Exception sending alert message. MessageID: " + alertMessage.getMessageID() + " " + e);
        	}
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
            		.prepareStatement("SELECT a.name, m.msgID,m.noteID,m.personID,m.alertID,m.alertTypeID,m.created,m.modified,m.deliveryMethodID,m.address,m.message,m.status,m.level,m.owner,m.zoneID, IF(m.status=2,0,1) as acknowledge, m.attribs, m.driverID, m.vehicleID, m.deviceID FROM message m, alert a WHERE m.alertID = a.alertID and owner=?");
            preparedStatement.setLong(1, owner);
            messageResultSet = preparedStatement.executeQuery();

            while (messageResultSet.next()) {

                AlertMessage alertMessage = new AlertMessage(messageResultSet.getInt("msgID"), AlertMessageDeliveryType.valueOf(messageResultSet.getInt("deliveryMethodID")),
                        AlertMessageType.valueOf(messageResultSet.getInt("alertTypeID")), RedFlagLevel.valueOf(messageResultSet.getInt("level")), messageResultSet.getString("address"),
                        messageResultSet.getString("message"), messageResultSet.getLong("noteID"), messageResultSet.getInt("personID"), messageResultSet.getInt("alertID"),
                        messageResultSet.getInt("zoneID"), messageResultSet.getBoolean("acknowledge"), AlertEscalationStatus.valueOf(messageResultSet.getInt("status")), messageResultSet.getString("attribs"), messageResultSet.getInt("driverID"), messageResultSet.getInt("vehicleID"), messageResultSet.getInt("deviceID"));

                alertMessage.setName(messageResultSet.getString("name"));
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

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
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

    public DeviceDAO getDeviceDAO() {
        return deviceDAO;
    }

    public void setDeviceDAO(DeviceDAO deviceDAO) {
        this.deviceDAO = deviceDAO;
    }

    public LocationDAO getLocationDAO() {
        return locationDAO;
    }

    public void setLocationDAO(LocationDAO locationDAO) {
        this.locationDAO = locationDAO;
    }

    private static final String FETCH_RED_FLAG_MESSAGE_INFO_PREFIX = "SELECT noteID, msgID, status FROM message WHERE noteID IN (";
    private static final String FETCH_RED_FLAG_MESSAGE_INFO_SUFFIX = ") order by noteID";

	@Override
	public void fillInRedFlagMessageInfo(List<RedFlag> redFlagList) {
		Map<Long, RedFlag> redFlagMap = new HashMap<Long, RedFlag>();
		StringBuffer noteIDList = new StringBuffer();
		for (RedFlag redFlag : redFlagList) {
			redFlag.setMsgIDList(new ArrayList<Integer>());
			if (noteIDList.length() > 0)
				noteIDList.append(",");
			noteIDList.append(redFlag.getEvent().getNoteID());
			redFlagMap.put(redFlag.getEvent().getNoteID(), redFlag);
		}
		if (!(noteIDList.toString() == null)) {
			Connection conn = null;
			Statement statement = null;
			ResultSet resultSet = null;
			try {
				conn = getConnection();
				statement = conn.createStatement();
				resultSet = statement
						.executeQuery(FETCH_RED_FLAG_MESSAGE_INFO_PREFIX
								+ noteIDList.toString() 
								+ FETCH_RED_FLAG_MESSAGE_INFO_SUFFIX);

				while (resultSet.next()) {
					long noteID = resultSet.getLong(1);
					int msgID = resultSet.getInt(2);
					AlertEscalationStatus status = AlertEscalationStatus
							.valueOf(resultSet.getInt(3));
					RedFlag redFlag = redFlagMap.get(noteID);
					redFlag.getMsgIDList().add(msgID);
					if (redFlag.getSent() != AlertSentStatus.PENDING
							&& redFlag.getSent() != AlertSentStatus.CANCELED) {

						if (status == AlertEscalationStatus.CANCELED)
							redFlag.setSent(AlertSentStatus.CANCELED);
						else if (status == AlertEscalationStatus.ESCALATED_AWAITING_ACKNOWLEDGE
								|| status == AlertEscalationStatus.NEW)
							redFlag.setSent(AlertSentStatus.PENDING);
						else if (status == AlertEscalationStatus.SENT
								|| status == AlertEscalationStatus.ESCALATED_ACKNOWLEDGED)
							redFlag.setSent(AlertSentStatus.SENT);
					}
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
            
            String driverOrgStructure = getDriverOrgStructure(driver);
            parameterList.add(driverOrgStructure);
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
        
        private String getDriverOrgStructure (Driver driver) {
            if (driver != null && driver.getPerson() != null) {
                Person driverPerson = driver.getPerson();
                Integer acctID = driverPerson.getAcctID();
                List<Group> groupList = groupDAO.getGroupsByAcctID(acctID);
                GroupHierarchy groupHierarchy = new GroupHierarchy(groupList);
                String groupName = groupHierarchy.getFullGroupName(driver.getGroupID(), " > ");
                return groupName;
            } else {
                return "";
            }
        }

        private void addVehicleRelatedData(Integer vehicleID) {
            Vehicle vehicle = vehicleDAO.findByID(vehicleID);
            parameterList.add(vehicle.getName());
        }

        private void addAlertRelatedData(Event event, MeasurementType personMeasurementType, AlertMessageType alertMessageType, Integer zoneID) {
            logger.debug("addAlertRelatedData alertMessageType: " + alertMessageType);
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
                case ALERT_TYPE_DVIR_REPAIR:
                    addDVIRRepairAttributes(event);
                    break;
                case ALERT_TYPE_TWO_HOURS_BREAK:
                    if (!(event instanceof FifteenMinuteBreakNotTakenEvent))
                        break;
                    addTwoHoursBreakAttributes((FifteenMinuteBreakNotTakenEvent)event);
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

        private void addTwoHoursBreakAttributes(FifteenMinuteBreakNotTakenEvent event) {
            parameterList.add("Event detail line 1 not printed");
            parameterList.add("Event detail line 2 not printed");

            DateTime dateStart = new DateTime().withTime(0, 0, 0, 0);
            DateTime dateEnd = dateStart.withTime(23, 59, 59, 999);

            SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy hh24:mm");
            PeriodFormatter dateFormat = new PeriodFormatterBuilder()
                    .appendHours().minimumPrintedDigits(2)
                    .appendSeparator(":")
                    .appendMinutes().minimumPrintedDigits(2)
                    .appendSeparator(":")
                    .appendSeconds().minimumPrintedDigits(2)
                    .toFormatter();

            Long drivingTime = 0l;
            Long stoppedTime = 0l;
            DateTime lastNoteDateTime = null;
            Duration duration = null;
            DateTime firstDrive = null;

            List<Event> events = eventDAO.getEventsForDriver(event.getDriverID(), dateStart.toDate(), dateEnd.toDate(), Arrays.asList(NoteType.values()), 0);
            for (Event ev : events) {

                // add times
                if (lastNoteDateTime != null) {
                    switch (ev.getType()) {
                        case TRIP_START:
                        case TRIP_INPROGRESS:
                        case NOTEEVENT:
                        case SPEEDING:
                        case ACCELERATION:
                        case DECELERATION:
                        case ON_ROAD:
                        case OFF_ROAD:
                        case SPEEDING_EX:
                        case SPEEDING_EX2:
                        case SPEEDING_EX3:
                        case WSZONES_ARRIVAL:
                        case WSZONES_DEPARTURE:
                        case WSZONES_ARRIVAL_EX:
                        case WSZONES_DEPARTURE_EX:
                        case VERTICAL_EVENT:
                        case VERTICAL_EVENT_SECONDARY:
                        case SPEEDING_EX4:
                        case SPEEDING_LOG4:
                        case SPEEDING_AV:
                        case START_SPEEDING:
                        case COACHING_SPEEDING:
                        case BACKING:
                        case FULLEVENT:
                        case ROLLOVER:
                        case START_MOTION:
                        case STOP_MOTION:
                        case PARKING_BRAKE:
                        case LOCATION:
                        case LOCATION_DEBUG:
                            // DRIVING
                            DateTime evTime = new DateTime(ev.getTime());
                            duration = new Duration(lastNoteDateTime, new DateTime(ev.getTime()));
                            drivingTime += duration.getMillis();

                            // first drive
                            if (firstDrive == null || evTime.isBefore(firstDrive))
                                firstDrive = evTime;
                        case IDLE:
                        case IGNITION_ON:
                        case POWER_ON:
                        case IGNITION_OFF:
                        case POWER_INTERRUPTED:
                            // STOPPED
                            duration = new Duration(lastNoteDateTime, new DateTime(ev.getTime()));
                            stoppedTime += duration.getMillis();
                        default:
                            continue;
                    }
                }
                lastNoteDateTime = new DateTime(ev.getTime());
            }

            // total drive time
            duration = new Duration(drivingTime);
            parameterList.add(dateFormat.print(duration.toPeriod()));

            // total stop time
            duration = new Duration(stoppedTime);
            parameterList.add(dateFormat.print(duration.toPeriod()));

            // expected stop time
            parameterList.add("00:15");

            // first drive time
            if (firstDrive != null)
                parameterList.add(sdf.format(firstDrive.toDate()));
            else
                parameterList.add("");


            // trips
            List<Trip> trips = locationDAO.getTripsForDriver(event.getDriverID(), dateStart.toDate(), dateEnd.toDate());
            if (trips != null && !trips.isEmpty()) {

                Trip firstTrip = trips.get(0);
                DateTime firstTripLastDriveTime = getLastDriveTimeForTrip(firstTrip);

                // last driving time first trip
                if (firstTripLastDriveTime != null)
                    parameterList.add(sdf.format(firstTripLastDriveTime.toDate()));
                else
                    parameterList.add("");


                Trip lastTrip = trips.get(trips.size() - 1);
                DateTime lastTripLastDriveTime = getLastDriveTimeForTrip(lastTrip);

                // last driving time last trip
                if (lastTripLastDriveTime != null)
                    parameterList.add(sdf.format(lastTripLastDriveTime.toDate()));
                else
                    parameterList.add("");

            }else{
                parameterList.add("");
                parameterList.add("");
            }

            // violation start time
            if (event.getStartTime() != null)
                parameterList.add(event.getStartTime());
            else
                parameterList.add("");
        }

        private DateTime getLastDriveTimeForTrip(Trip trip) {
            DateTime lastNoteDateTime = null;
            if (trip != null) {
                for (Event ev : trip.getEvents()) {
                    switch (ev.getType()) {
                        case TRIP_START:
                        case TRIP_INPROGRESS:
                        case NOTEEVENT:
                        case SPEEDING:
                        case ACCELERATION:
                        case DECELERATION:
                        case ON_ROAD:
                        case OFF_ROAD:
                        case SPEEDING_EX:
                        case SPEEDING_EX2:
                        case SPEEDING_EX3:
                        case WSZONES_ARRIVAL:
                        case WSZONES_DEPARTURE:
                        case WSZONES_ARRIVAL_EX:
                        case WSZONES_DEPARTURE_EX:
                        case VERTICAL_EVENT:
                        case VERTICAL_EVENT_SECONDARY:
                        case SPEEDING_EX4:
                        case SPEEDING_LOG4:
                        case SPEEDING_AV:
                        case START_SPEEDING:
                        case COACHING_SPEEDING:
                        case BACKING:
                        case FULLEVENT:
                        case ROLLOVER:
                        case START_MOTION:
                        case STOP_MOTION:
                        case PARKING_BRAKE:
                        case LOCATION:
                        case LOCATION_DEBUG:
                            // DRIVING
                            DateTime evTime = new DateTime(ev.getTime());

                            // last drive
                            if (lastNoteDateTime == null || evTime.isAfter(lastNoteDateTime))
                                lastNoteDateTime = evTime;
                    }
                }

            }
            return lastNoteDateTime;
        }


        private void addDVIRRepairAttributes(Event event){
            String mechanicID = event.getAttrMap().get(EventAttr.ATTR_DVIR_MECHANIC_ID_STR.toString()) == null ? "" : String.valueOf(event.getAttrMap().get(EventAttr.ATTR_DVIR_MECHANIC_ID_STR.toString()));
            String inspectorID = event.getAttrMap().get(EventAttr.ATTR_DVIR_INSPECTOR_ID_STR.toString()) == null ? "" : String.valueOf(event.getAttrMap().get(EventAttr.ATTR_DVIR_INSPECTOR_ID_STR.toString()));
            String signOffID = event.getAttrMap().get(EventAttr.ATTR_DVIR_SIGNOFF_ID_STR.toString()) == null ? "" : String.valueOf(event.getAttrMap().get(EventAttr.ATTR_DVIR_SIGNOFF_ID_STR.toString()));
            String comments = event.getAttrMap().get(EventAttr.ATTR_DVIR_COMMENTS.toString()) == null ? "" : String.valueOf(event.getAttrMap().get(EventAttr.ATTR_DVIR_COMMENTS.toString()));
            String formDefID = event.getAttrMap().get(EventAttr.ATTR_DVIR_FORM_ID.toString()) == null ? "" : String.valueOf(event.getAttrMap().get(EventAttr.ATTR_DVIR_FORM_ID.toString()));
            String submissionTime = event.getAttrMap().get(EventAttr.ATTR_DVIR_SUBMISSION_TIME.toString()) == null ? "" : String.valueOf(event.getAttrMap().get(EventAttr.ATTR_DVIR_SUBMISSION_TIME.toString()));

            parameterList.add(mechanicID);
            parameterList.add(inspectorID);
            parameterList.add(signOffID);
            parameterList.add(comments);
            parameterList.add(formDefID);
            parameterList.add(submissionTime)
            ;        }

        public List<String> getParameterList() {
            return parameterList;
        }
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
            addDriverInfo(driver);                      //#4 - #6
            addVehicleInfo(event.getVehicleID());       //#7 - #11
            addLocationInfo(event);                     //#12 - #14
            addOdometer(event);                         //#15
            addSpeed(event);                            //#16
            parameterList.add(String.valueOf(personMeasurementType.ordinal()));    //#17 - needs to be 0|1 -- 0-english or 1-metric
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
            addDriverInfo(driver);                      //#4 - #6
            addVehicleInfo(event.getVehicleID());       //#7 - #11
            addLocationInfo(event);                     //#12 - #14
            addOdometer(event);                         //#15
            addSpeed(event);                            //#16
            parameterList.add(String.valueOf(personMeasurementType.ordinal()));    //#17 - needs to be 0|1 -- 0-english or 1-metric
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

        private void addDriverInfo(Driver driver) {
            if ((driver != null) && (driver.getPerson() != null)) {
                parameterList.add(driver.getPerson().getEmpid());
                parameterList.add(driver.getDriverID().toString());
                parameterList.add(getDriverFullName(driver));
                
            } else {
                parameterList.add("");
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
                        Integer totalIdle = ((IdleEvent) event).getTotalIdling() / 60;
                        parameterList.add(totalIdle.toString());
                    }
                    break;
                case ALERT_TYPE_TWO_HOURS_BREAK:
                    if (!(event instanceof FifteenMinuteBreakNotTakenEvent))
                        break;
                    addTwoHoursBreakAttributes((FifteenMinuteBreakNotTakenEvent)event);
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

        private void addTwoHoursBreakAttributes(FifteenMinuteBreakNotTakenEvent event) {
            parameterList.add(event.getDriverName() != null ? event.getDriverName() : "");
            parameterList.add(event.getVehicleName() != null ? event.getVehicleName() : "");
            parameterList.add(event.getAddressStr() != null ? event.getAddressStr() : "");
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
