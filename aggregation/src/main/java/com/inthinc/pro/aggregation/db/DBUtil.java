package com.inthinc.pro.aggregation.db;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inthinc.pro.aggregation.AggregationProperties;
import com.inthinc.pro.aggregation.model.Device;
import com.inthinc.pro.aggregation.model.DeviceDay;
import com.inthinc.pro.aggregation.model.Note;
import com.inthinc.pro.aggregation.model.Trip;
import com.inthinc.pro.aggregation.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.TimeZone;

import javax.sql.DataSource;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;



public class DBUtil {

	private static Logger logger = LoggerFactory.getLogger(DBUtil.class.getName());
	
	private static DataSource tiwiproDS;

	public DataSource getDataSource() {
		return tiwiproDS;
	}

	public void setDataSource(DataSource ds) {
		tiwiproDS = ds;
	}
	
	public static void setDataSource(String jdbcURL, String jdbcUser, String jdbcPassword) {

		tiwiproDS = DataConnection.createDataSource(jdbcURL, jdbcUser, jdbcPassword);
;
	}

	public static void setDataSource(AggregationProperties props) {
		String jdbcClass = props.getProperty(AggregationProperties.JDBC_CLASS);
		String jdbcURL = props.getProperty(AggregationProperties.JDBC_URL);
		String jdbcUser = props.getProperty(AggregationProperties.JDBC_USER);
		String jdbcPassword = props.getProperty(AggregationProperties.JDBC_PASSWORD);

		logger.debug("jdbcClass: " + jdbcClass );
		logger.debug("jdbcURL: " + jdbcURL );
		logger.debug("jdbcUser: " + jdbcUser );
		logger.debug("jdbcPassword: " + jdbcPassword );
		
		try
		{
			Class.forName(jdbcClass);
			setDataSource(jdbcURL, jdbcUser, jdbcPassword);
		}
	    catch(Exception e)
	    {
	    	logger.error("Error while attempting to create JDBC connection  : " + e);
	    	e.printStackTrace();
			System.exit(0);
	    }
	}


	private static final String FETCH_DEVICEDAYS2AGG = "SELECT deviceID,  DATE_FORMAT(day, '%Y-%m-%d %H:%i:%s') FROM deviceDay2Agg ORDER BY deviceID, day ASC";
    public static List<DeviceDay> getDeviceDay2Agg()  throws SQLException
    {
        List<DeviceDay> deviceDayList = new ArrayList<DeviceDay>();
        
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try
        {
            conn =  tiwiproDS.getConnection();
            statement = conn.prepareStatement(FETCH_DEVICEDAYS2AGG);
            
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
            	
            	DeviceDay deviceDay = new DeviceDay();
            	deviceDay.setDeviceID(resultSet.getLong(1));
            	String strDay = resultSet.getString(2);
           		deviceDay.setDay(DateUtil.getDateFromString(strDay));

            	deviceDayList.add(deviceDay);
            }
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw e;
        }   // end catch
        finally
        { // clean up and release the connection
        	close(conn, statement, resultSet);
        } // end finally   
        
        return deviceDayList;
    }

	private static final String DELETE_DEVICEDAYS2AGG = "DELETE FROM deviceDay2Agg WHERE deviceID = ? AND day = ?";
    public static void deleteDeviceDay2Agg(long deviceId, Date day)  throws SQLException
    {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
		logger.info("deleteDeviceDay2Agg called deviceID: " + deviceId + " day: " + day);
        try
        {
            conn =  tiwiproDS.getConnection();
            statement = conn.prepareStatement(DELETE_DEVICEDAYS2AGG);

            statement.setLong(1, deviceId);
            statement.setDate(2, new java.sql.Date(day.getTime()));
            
            statement.executeUpdate();

        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw e;
        }   // end catch
        finally
        { // clean up and release the connection
        	close(conn, statement, resultSet);
        } // end finally   
    }
	
    private static final String FETCH_WAYSMARTS_BY_ACCT= "SELECT deviceID FROM device WHERE acctID=? AND status!=3 AND productVer=2";
    public static List<Device> getWSDevicesByAcctID(Integer accountID) throws SQLException
    {
        List<Device> deviceList = new ArrayList<Device>();
        
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn =  tiwiproDS.getConnection();
            statement = conn.createStatement();
            resultSet = statement.executeQuery(FETCH_WAYSMARTS_BY_ACCT);

            while (resultSet.next()) {
                Device device = new Device();
                device.setDeviceID(resultSet.getInt(1));
                deviceList.add(device);
            }
                

        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
        	logger.error("SQL Exception: " + e);
        	throw e;
        }   // end catch
        finally
        { // clean up and release the connection
        	close(conn, statement, resultSet);
        } // end finally   
        
        return deviceList;
    }
    
	private static final String FETCH_WAYSMARTS= "SELECT deviceID FROM device WHERE status!=3 AND productVer=2";
    public static List<Device> getWSDevices(Integer accountID) throws SQLException
    {
        List<Device> deviceList = new ArrayList<Device>();
        
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn =  tiwiproDS.getConnection();
            statement = conn.createStatement();
            resultSet = statement.executeQuery(FETCH_WAYSMARTS);

            while (resultSet.next()) {
                Device device = new Device();
                device.setDeviceID(resultSet.getInt(1));
                deviceList.add(device);
            }
                

        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
        	logger.error("SQL Exception: " + e);
        	throw e;
        }   // end catch
        finally
        { // clean up and release the connection
        	close(conn, statement, resultSet);
        } // end finally   
        
        return deviceList;
    }
	
	private static final String FETCH_WS_TRIPNOTES_BETWEEN = "SELECT noteID,vehicleID,driverID,groupID,type,DATE_FORMAT(time, '%Y-%m-%d %H:%i:%s'),speed,odometer,state,flags,maprev,latitude,longitude,topSpeed,avgSpeed,speedLimit,distance,deltaX,deltaY,deltaZ,attrs FROM __NOTETABLE__ WHERE deviceID=? AND `type` IN (7,19,20,22,66,73,96,113,116,166,208,219) AND time BETWEEN  ? AND ? ORDER BY time";
    public static List<Note> getWSTripNotesForDevice(Long deviceID, Date startTimeStamp, Date endTimeStamp)  throws SQLException
    {
    	final String ATTR_driverFlag	= "8201";
    	final int HOS_CHANGE_STATE = 113; 

    	List<Note> noteList = new ArrayList<Note>();
        
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        int shard = (int) (deviceID%32);
        String noteTable = String.format("note%02d", shard);
        String sqlStatement = FETCH_WS_TRIPNOTES_BETWEEN.replace("__NOTETABLE__", noteTable);

        try
        {
            conn =  tiwiproDS.getConnection();
            statement = conn.prepareStatement(sqlStatement);
            statement.setLong(1, deviceID);
            statement.setString(2, DateUtil.getDisplayDate(startTimeStamp));
            statement.setString(3, DateUtil.getDisplayDate(endTimeStamp));
            
            logger.debug(statement.toString());

            resultSet = statement.executeQuery();
            

            while (resultSet.next()) {
            	
            	Note note = new Note();
            	note.setDeviceID(deviceID);
                note.setNoteID(resultSet.getLong(1));
                note.setVehicleID(resultSet.getLong(2));
                note.setDriverID(resultSet.getLong(3));
                note.setGroupID(resultSet.getLong(4));
                note.setType(resultSet.getInt(5));

                String strDate = resultSet.getString(6);
                Date noteTime = DateUtil.getDateFromString(strDate, TimeZone.getTimeZone("UTC"));
                note.setTime(noteTime);
  

                logger.debug("NoteID: " + note.getNoteID() + " Type: " + note.getType() + " TS: " + strDate);
                
                note.setSpeed(resultSet.getInt(7));
                note.setOdometer(resultSet.getLong(8));
                note.setState(resultSet.getInt(9));
                note.setFlags(resultSet.getInt(10));
                note.setMapRev(resultSet.getInt(11));
                note.setLatitude(resultSet.getFloat(12));
                note.setLongitude(resultSet.getFloat(13));
                note.setTopSpeed(resultSet.getInt(14));
                note.setAvgSpeed(resultSet.getInt(15));
                note.setSpeedLimit(resultSet.getInt(16));
                note.setDistance(resultSet.getInt(17));
                note.setDeltaX(resultSet.getInt(18));
                note.setDeltaY(resultSet.getInt(19));
                note.setDeltaZ(resultSet.getInt(20));                
                note.setAttributes(resultSet.getString(21));                
                
            	if (note.getType() != HOS_CHANGE_STATE || (note.getType() == HOS_CHANGE_STATE && ((note.getAttributeAsInt(ATTR_driverFlag) & 0x20) != 0)))
            		noteList.add(note);
            }
                
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw e;
        }   // end catch
        finally
        { // clean up and release the connection
        	close(conn, statement, resultSet);
        } // end finally   
        return noteList;
    }
	
	private static final String INSERT_TRIP = "INSERT INTO trip (deviceID,driverID,vehicleID,idleTime,mileage,mileageOffset,startNoteID,startType,startOdometer,endNoteID,endType,endOdometer,status,startTime,endTime) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,CAST('1970-01-01' AS DATETIME) + INTERVAL ? SECOND,CAST('1970-01-01' AS DATETIME) + INTERVAL ? SECOND)";
    public static void insertTrip(Trip trip)  throws SQLException
    {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try
        {
            conn =  tiwiproDS.getConnection();
            statement = conn.prepareStatement(INSERT_TRIP);

            statement.setLong(1, trip.getDeviceID());
            statement.setLong(2, trip.getDriverID());
            statement.setLong(3, trip.getVehicleID());
            statement.setInt(4, trip.getIdleTime());
            statement.setInt(5, trip.getMileage());
            statement.setLong(6, trip.getMileageOffset());
            statement.setLong(7, trip.getStartNoteID());
            statement.setInt(8, trip.getStartNoteType());
            statement.setLong(9, trip.getStartOdometer());
            statement.setLong(10, trip.getEndNoteID());
            statement.setInt(11, trip.getEndNoteType());
            statement.setLong(12, trip.getEndOdometer());
            statement.setInt(13, trip.getStatus().getCode());
            statement.setInt(14, (int)(trip.getStartTime().getTime()/1000));
            statement.setInt(15, (int)(trip.getEndTime().getTime()/1000));
            
            logger.debug("statement: " + statement);

            statement.executeUpdate();
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
        	logger.error("Exception: " + e);
            throw e;
        }   // end catch
        finally
        { // clean up and release the connection
        	close(conn, statement, resultSet);
        } // end finally   
    }
    

	private static final String DELETE_TRIPS = "DELETE FROM trip WHERE deviceID = ? AND startTime BETWEEN ? AND ?";
    public static void deleteTrips(long deviceId, Date startTS, Date endTS)  throws SQLException
    {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try
        {
            conn =  tiwiproDS.getConnection();
            statement = conn.prepareStatement(DELETE_TRIPS);
            statement.setLong(1, deviceId);
            statement.setString(2, DateUtil.getDisplayDate(startTS));
            statement.setString(3, DateUtil.getDisplayDate(endTS));
            
            logger.debug(statement.toString());

            statement.executeUpdate();
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
        	
            throw e;
        }   // end catch
        finally
        { // clean up and release the connection
        	close(conn, statement, resultSet);
        } // end finally   
    }
    
    
	private static final String SELECT_TRIP_ENDTIME_BEFORE_DAY = "SELECT type, DATE_FORMAT(time, '%Y-%m-%d %H:%i:%s') FROM __NOTETABLE__ WHERE deviceID = ? AND time < ? AND type IN (7,19,20,22,66,96,113,116) ORDER BY time DESC LIMIT 1";
    public static Date fetchTripEndTimeStampBeforeDay(Long deviceId, Date startTime)  throws SQLException
    {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Date tripTime = startTime;
        
        int shard = (int) (deviceId%32);
        String noteTable = String.format("note%02d", shard);
        String sqlStatement = SELECT_TRIP_ENDTIME_BEFORE_DAY.replace("__NOTETABLE__", noteTable);
        
        try
        {
            conn =  tiwiproDS.getConnection();
            statement = conn.prepareStatement(sqlStatement);
            statement.setLong(1, deviceId);
            statement.setString(2, DateUtil.getDisplayDate(startTime));
            
            logger.debug(statement.toString());

            resultSet  = statement.executeQuery(); 
            
            if (resultSet.next())
            {
            	int type = resultSet.getInt(1);
            	String strDate = resultSet.getString(2);

            	if (Note.isStartNote(type))
            	{
	            	tripTime = DateUtil.getDateFromString(strDate);
	                tripTime.setTime(tripTime.getTime()-1000); //Add a second
            	}
            }
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
        	
            throw e;
        }   // end catch
        finally
        { // clean up and release the connection
        	close(conn, statement, resultSet);
        } // end finally   
        return tripTime;
    }

	private static final String SELECT_TRIP_STARTTIME_AFTER_DAY = "SELECT type, DATE_FORMAT(min(time), '%Y-%m-%d %H:%i:%s') FROM __NOTETABLE__ WHERE deviceID = ? AND time > ? AND type IN (7,19,20,22,66,96,113,116) ORDER BY time LIMIT 1";
    public static Date fetchTripStartTimeStampAfterDay(Long deviceId, Date endDayTS)  throws SQLException
    {
    	Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        Date tripTime = endDayTS;

        int shard = (int) (deviceId%32);
        String noteTable = String.format("note%02d", shard);
        String sqlStatement = SELECT_TRIP_STARTTIME_AFTER_DAY.replace("__NOTETABLE__", noteTable);

        try
        {
            conn =  tiwiproDS.getConnection();
            statement = conn.prepareStatement(sqlStatement);
            statement.setLong(1, deviceId);
            statement.setString(2, DateUtil.getDisplayDate(endDayTS));
            
            logger.debug(statement.toString());

            resultSet  = statement.executeQuery(); 
            
            if (resultSet.next())
            {
            	int type = resultSet.getInt(1);
            	String strDate = resultSet.getString(2);
            	if (Note.isEndNote(type))
            	{
	            	tripTime = DateUtil.getDateFromString(strDate);
	                tripTime.setTime(tripTime.getTime()+1000); //Add a second
            	}
            }
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
        	
            throw e;
        }   // end catch
        finally
        { // clean up and release the connection
        	close(conn, statement, resultSet);
        } // end finally   
        return tripTime;
    }

	private static final String CLEAR_AGG = "UPDATE agg%02d SET driveTime=0, odometer6=0, trips=0, modified=UTC_TIMESTAMP() WHERE deviceID = ? AND aggDate = ?";
    public static void clearAgg(Long deviceId, Date day, TimeZone driverTZ)  throws SQLException
    {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        int shard = (int) (deviceId%32);
        String sqlStatement = String.format(CLEAR_AGG, shard);
        
        try
        {
            conn = tiwiproDS.getConnection();
            statement = conn.prepareStatement(sqlStatement);
            statement.setLong(1, deviceId);
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setTimeZone(driverTZ);
            
            statement.setString(2, dateFormat.format(day));
//            statement.setDate(2, new java.sql.Date(day.getTime()));
            
            logger.debug(statement.toString());

            statement.executeUpdate();
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
        	
            throw e;
        }   // end catch
        finally
        { // clean up and release the connection
        	close(conn, statement, resultSet);
        } // end finally   
    }

	private static final String UPDATE_AGG = "INSERT INTO agg%02d (aggDate, driverID, vehicleID, deviceID, driveTime, odometer6, trips, modified) VALUES(?, ?, ?, ?, ?, ?, 1,UTC_TIMESTAMP()) " +
			"ON DUPLICATE KEY UPDATE trips=trips+1, driveTime=driveTime + ?, odometer6=odometer6 + ?, modified=UTC_TIMESTAMP()";

	public static void updateAgg(Trip trip, Date day, TimeZone driverTZ)  throws SQLException
    {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

		GregorianCalendar endDayCalendar = new GregorianCalendar();
		endDayCalendar.setTime(day);
		endDayCalendar.add(Calendar.DATE, 1);

		if (trip.getStartTime().after(trip.getEndTime()))
			trip.setEndTime(trip.getStartTime());
		
		int startTS = (int) (trip.getStartTime().getTime()/1000);
		int endTS = (int) (trip.getEndTime().getTime()/1000);
		
		if (startTS < (int) (day.getTime()/1000))
		{
			logger.debug("Setting startTime to Day start");
			startTS = (int) (day.getTime()/1000);
		}
		
		if (endTS > (int) (endDayCalendar.getTime().getTime()/1000))
		{
			logger.debug("Setting endTime to Day end");
			endTS = (int) (endDayCalendar.getTime().getTime()/1000);
		}
		
		int milesDrivenForDay = (int) (trip.getMileage()+trip.getMileageOffset());

		//Prorate mileage if trip crosses a day 
		logger.debug("tripID: " + trip.getTripID());
		logger.debug("day: " + day);
		logger.debug("dayTS: " + (day.getTime()/1000));
		logger.debug("endDayCalendar.getTime(): " + endDayCalendar.getTime());
		logger.debug("(endDayCalendar.getTime().getTime()/1000): " + (endDayCalendar.getTime().getTime()/1000));
		logger.debug("startTS: " + startTS);
		logger.debug("endTS: " + endTS);
		logger.debug("trip.getStartTime(): " + trip.getStartTime());
		logger.debug("trip.getEndTime(): " + trip.getEndTime());
		logger.debug("trip.getStartTimeTS(): " + trip.getStartTime().getTime());
		logger.debug("trip.getEndTimeTS(): " + trip.getEndTime().getTime());
		logger.debug("milesDrivenForDay: " + milesDrivenForDay);
		
		if (startTS > (trip.getStartTime().getTime()/1000) || endTS < (trip.getEndTime().getTime()/1000))
		{
			float percent = ((float)(endTS-startTS))/((trip.getEndTime().getTime()/1000F)-(trip.getStartTime().getTime()/1000F));
			logger.debug("percent: " + percent);
			logger.debug("milesDrivenForDay before: " + milesDrivenForDay);
			milesDrivenForDay = (int) (milesDrivenForDay * percent);
			logger.debug("milesDrivenForDay after: " + milesDrivenForDay);
		}
		int shard = (int)(trip.getDeviceID()%32);
        String sqlStatement = String.format(UPDATE_AGG, shard);
        try
        {
            conn =  tiwiproDS.getConnection();
            statement = conn.prepareStatement(sqlStatement);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setTimeZone(driverTZ);

            statement.setDate(1, new java.sql.Date(day.getTime()));
            statement.setLong(2, trip.getDriverID());
            statement.setLong(3, trip.getVehicleID());
            statement.setLong(4, trip.getDeviceID());
            statement.setInt(5, endTS-startTS);
            statement.setInt(6, milesDrivenForDay);
            statement.setInt(7, endTS-startTS);
            statement.setInt(8, milesDrivenForDay);
            
            logger.debug(statement.toString());

            statement.executeUpdate();
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
        	
            throw e;
        }   // end catch
        finally
        { // clean up and release the connection
        	close(conn, statement, resultSet);
        } // end finally   
    }

	
	private static final String SELECT_TRIPS_FOR_AGG = "SELECT tripID,driverID,vehicleID,coalesce(idleTime,0),coalesce(mileage,0),coalesce(mileageOffset,0),DATE_FORMAT(CONVERT_TZ(startTime, 'GMT', ?), '%Y-%m-%d %H:%i:%s'), DATE_FORMAT(CONVERT_TZ(COALESCE(endTime,UTC_TIMESTAMP()), 'GMT', ?), '%Y-%m-%d %H:%i:%s'), startOdometer, endOdometer FROM trip WHERE deviceID = ? " +
			"AND (CONVERT_TZ(startTime, 'GMT', ?) BETWEEN ? AND ? OR CONVERT_TZ(endTime, 'GMT', ?) BETWEEN ? AND ?)";
	public static List<Trip> fetchTripsForAggDay(Date startDayTimeDriverTZ, Date endDayTimeDriverTZ, Long deviceId, TimeZone driverTZ) throws SQLException
	{
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        List<Trip> tripList = new ArrayList<Trip>();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(driverTZ);

        
        try
        {
            conn =  tiwiproDS.getConnection();
            statement = conn.prepareStatement(SELECT_TRIPS_FOR_AGG);
            statement.setString(1, driverTZ.getID());
            statement.setString(2, driverTZ.getID());
            statement.setLong(3, deviceId);
            statement.setString(4, driverTZ.getID());
            statement.setString(5, dateFormat.format(startDayTimeDriverTZ));
            statement.setString(6, dateFormat.format(endDayTimeDriverTZ));
            statement.setString(7, driverTZ.getID());
            statement.setString(8, dateFormat.format(startDayTimeDriverTZ));
            statement.setString(9, dateFormat.format(endDayTimeDriverTZ));

            logger.debug(statement.toString());
            
            resultSet = statement.executeQuery();

    	    GregorianCalendar driverTimeCal = new GregorianCalendar();
    	    driverTimeCal.setTimeZone(driverTZ);

            while (resultSet.next())
            {
                Trip trip = new Trip();
            	trip.setDeviceID(deviceId);
            	
            	trip.setTripID(resultSet.getLong(1));
            	trip.setDriverID(resultSet.getLong(2));
            	trip.setVehicleID(resultSet.getLong(3));
            	trip.setIdleTime(resultSet.getInt(4));
            	trip.setMileage(resultSet.getInt(5));
            	trip.setMileageOffset(resultSet.getLong(6));
                
            	String strTime = resultSet.getString(7);
            	
        	    int year = Integer.parseInt(strTime.substring(0,4));
        	    int month = Integer.parseInt(strTime.substring(5,7));
        	    int day = Integer.parseInt(strTime.substring(8,10));
        	    int hour = Integer.parseInt(strTime.substring(11,13));
        	    int minute = Integer.parseInt(strTime.substring(14,16));
        	    int second = Integer.parseInt(strTime.substring(17,19));
        	    driverTimeCal.set(year, month-1, day, hour, minute, second);
                trip.setStartTime(driverTimeCal.getTime());

                strTime = resultSet.getString(8);
        	    year = Integer.parseInt(strTime.substring(0,4));
        	    month = Integer.parseInt(strTime.substring(5,7));
        	    day = Integer.parseInt(strTime.substring(8,10));
        	    hour = Integer.parseInt(strTime.substring(11,13));
        	    minute = Integer.parseInt(strTime.substring(14,16));
        	    second = Integer.parseInt(strTime.substring(17,19));
        	    driverTimeCal.set(year, month-1, day, hour, minute, second);
                trip.setEndTime(driverTimeCal.getTime());
            	
                trip.setStartOdometer(resultSet.getLong(9));
            	trip.setEndOdometer(resultSet.getLong(10));
            	
            	tripList.add(trip);
            }
            
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
        	logger.error("Exception: " + e);
            throw e;
        }   // end catch
        finally
        { // clean up and release the connection
        	close(conn, statement, resultSet);
        }
        return tripList;
	}



	private static final String SELECT_DRIVER_TZ = "SELECT coalesce(t.tzName, 'GMT') FROM driver d JOIN person p ON p.personID=d.personID JOIN timezone t ON t.tzID=p.tzID WHERE d.driverID=?";
	public static String getDriverTimeZone(Long driverId) //throws SQLException
	{
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String tzName = "UTC";
        
        try
        {
            conn =  tiwiproDS.getConnection();
            statement = conn.prepareStatement(SELECT_DRIVER_TZ);
            statement.setLong(1, driverId);

            logger.debug(statement.toString());

            resultSet = statement.executeQuery();

            if (resultSet.next())
            	tzName = resultSet.getString(1);
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
        	logger.error("Exception:" + e);
            //throw e;
        }   // end catch
        finally
        { // clean up and release the connection
        	close(conn, statement, resultSet);
        }
        return tzName;
	}


    
    private static void close(Connection conn, Statement statement, ResultSet resultSet)
	{
		try {
			if (conn != null)
				conn.close();
			if (statement != null)
				statement.close();
			if (resultSet != null)
				resultSet.close();
		} catch (Exception e)
		{
			logger.error("Exception Closing connection: " + e);
		}
	}
}
