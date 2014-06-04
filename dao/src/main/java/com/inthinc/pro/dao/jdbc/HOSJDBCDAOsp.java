package com.inthinc.pro.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.hos.ddl.HOSOccupantLog;
import com.inthinc.hos.model.HOSOrigin;
import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.hos.HOSDriverLogin;
import com.inthinc.pro.model.hos.HOSGroupMileage;
import com.inthinc.pro.model.hos.HOSOccupantHistory;
import com.inthinc.pro.model.hos.HOSOccupantInfo;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.model.hos.HOSVehicleMileage;

// This is the old version that uses mostly stored procs.  Leaving the class for reference.
@Deprecated
public class HOSJDBCDAOsp extends GenericJDBCDAO implements HOSDAO {
    

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(HOSJDBCDAO.class);
    private static final SimpleDateFormat dbdateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static {
        dbdateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
    
    /**
     * 
     */

    @Override
    public List<HOSGroupMileage> getHOSMileage(Integer groupID, Interval interval, Boolean noDriver) {
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;

        ArrayList<HOSGroupMileage> recordList = new ArrayList<HOSGroupMileage>();
        
        try
        {
            conn = getConnection();
            statement = conn.prepareCall("{call hos_getMileageByGroup(?, ?, ?, ?)}");
            statement.setInt(1, groupID);
            statement.setLong(2, interval.getStartMillis());
            statement.setLong(3, interval.getEndMillis());
            statement.setBoolean(4, noDriver);
            
            if(logger.isDebugEnabled())
                logger.debug(statement.toString());
            
            resultSet = statement.executeQuery();

            HOSGroupMileage hosRecord = null;
            while (resultSet.next())
            {
                hosRecord = new HOSGroupMileage();

                hosRecord.setGroupID(resultSet.getInt(1));
                hosRecord.setDistance(resultSet.getLong(2));

                recordList.add(hosRecord);
                
            }
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw new ProDAOException((statement != null) ? statement.toString() : "", e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally

        return recordList;
    }

    @Override
    public List<HOSVehicleMileage> getHOSVehicleMileage(Integer groupID, Interval interval, Boolean noDriver) {
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;

        ArrayList<HOSVehicleMileage> recordList = new ArrayList<HOSVehicleMileage>();
        
        try
        {
            conn = getConnection();
            statement = conn.prepareCall("{call hos_getVehicleMileageByGroup(?, ?, ?, ?)}");
            statement.setInt(1, groupID);
            statement.setLong(2, interval.getStartMillis());
            statement.setLong(3, interval.getEndMillis());
            statement.setBoolean(4, noDriver);
            
            if(logger.isDebugEnabled())
                logger.debug(statement.toString());
            
            resultSet = statement.executeQuery();

            HOSVehicleMileage hosRecord = null;
            while (resultSet.next())
            {
                hosRecord = new HOSVehicleMileage();

                hosRecord.setGroupID(resultSet.getInt(1));
                hosRecord.setVehicleName(resultSet.getString(2));
                hosRecord.setDistance(resultSet.getLong(3));

                recordList.add(hosRecord);
                
            }
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw new ProDAOException((statement != null) ? statement.toString() : "", e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally

        return recordList;
    }
    
    @Override
    public List<HOSOccupantHistory> getHOSOccupantHistory(String commAddress, String employeeId) {
        HOSDriverLogin driverLogin = getDriverForEmpid(commAddress, employeeId);
        return getHOSOccupantHistory(driverLogin);
    }

    @Override
    public List<HOSOccupantHistory> getHOSOccupantHistory(HOSDriverLogin driverLogin) {
        final RuleSetType dotType = driverLogin.getDriverDotType();

        final int daysback = ( dotType == RuleSetType.CANADA_2007_OIL  || dotType == RuleSetType.CANADA_2007_60_DEGREES_OIL) ? -24 : -14;

        TimeZone driverTz = TimeZone.getTimeZone("MST7MDT");  //default
        if (!driverLogin.getTimezoneID().equals(""))
            driverTz = TimeZone.getTimeZone(driverLogin.getTimezoneID());

        
        GregorianCalendar startDayCalendar = new GregorianCalendar();
        startDayCalendar.setTimeZone(driverTz);
        startDayCalendar.setTime(new Date());
        startDayCalendar.add(Calendar.DATE, daysback);
        int startDayOfYear = startDayCalendar.get(Calendar.DAY_OF_YEAR);
        
        Interval interval = new Interval(startDayCalendar.getTime().getTime(), new Date().getTime());
        final List<HOSOccupantLog> recordList = getHOSOccupantLogs(driverLogin.getDriverID(), interval);
        
        startDayCalendar.set(startDayCalendar.get(Calendar.YEAR), startDayCalendar.get(Calendar.MONTH), startDayCalendar.get(Calendar.DATE), 0, 0, 0);
        GregorianCalendar endDayCalendar = new GregorianCalendar();
        endDayCalendar.setTimeZone(driverTz);
        endDayCalendar.setTime(startDayCalendar.getTime());
        endDayCalendar.add(Calendar.DATE, 1);
        GregorianCalendar startLogTimeCalendar = new GregorianCalendar();
        startLogTimeCalendar.setTimeZone(driverTz);
        GregorianCalendar endLogTimeCalendar = new GregorianCalendar();
        endLogTimeCalendar.setTimeZone(driverTz);
        List<Integer> occupantDuplicateCheckList = new ArrayList<Integer>();
        List<HOSOccupantHistory> occupantHistoryList = new ArrayList<HOSOccupantHistory>();

        for (int i = 0; i >= daysback; i--)
        {
            for (HOSOccupantLog occupantLog: recordList)
            {
                startLogTimeCalendar.setTime(occupantLog.getLogTime());
                endLogTimeCalendar.setTime(occupantLog.getEndTime());

                //if the dates for the log fit in the given day.
                if ((startLogTimeCalendar.after(startDayCalendar) && startLogTimeCalendar.before(endDayCalendar))
                    || (endLogTimeCalendar.after(startDayCalendar) && endLogTimeCalendar.before(endDayCalendar))
                    || (startLogTimeCalendar.before(startDayCalendar) && endLogTimeCalendar.after(endDayCalendar)))
                {
                    Integer driverID = (Integer)(occupantLog.getDriverID());
                    if (occupantDuplicateCheckList.indexOf(driverID) == -1)
                    {
                        occupantDuplicateCheckList.add(driverID);
                        occupantHistoryList.add(new HOSOccupantHistory((i * -1)+1, driverID, startDayOfYear));
                    }
                }
            }
            occupantDuplicateCheckList.clear();
            startDayCalendar.add(Calendar.DATE, 1);
            endDayCalendar.add(Calendar.DATE, 1);
        }
        
        return occupantHistoryList;
        
    }

    
    @Override
    public List<HOSOccupantLog> getHOSOccupantLogs(Integer driverID, Interval interval) {
        Date currentTime = new Date();
//System.out.println("getHOSOccupantLogs: " + interval);            
        
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;

        ArrayList<HOSOccupantLog> recordList = new ArrayList<HOSOccupantLog>();
        
        try
        {
            conn = getConnection();
            statement = conn.prepareCall("{call hos_getHOSOccupantLogsForDriverVehicle(?, ?, ?)}");
            statement.setInt(1, driverID);
            statement.setLong(2, interval.getStartMillis());
            statement.setLong(3, interval.getEndMillis());

            if(logger.isDebugEnabled())
                logger.debug(statement.toString());
            
            resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                HOSOccupantLog occupantLog = new HOSOccupantLog();
                
                occupantLog.setDriverID(resultSet.getInt(1));
                occupantLog.setDriverName(resultSet.getString(2));
                occupantLog.setVehicleID(resultSet.getInt(3));
                Long ms = resultSet.getLong(4);
                occupantLog.setLogTime(new Date(ms));
                ms = resultSet.getLong(5);                
                occupantLog.setEndTime((ms == null || ms == 0) ? currentTime : new Date(ms));
                occupantLog.setServiceID(resultSet.getString(6));
                occupantLog.setTrailerID(resultSet.getString(7));
                if(logger.isDebugEnabled())
                    logger.debug("adding OccupantLog: driverID" + occupantLog.getDriverID() + " vehicleID: " + occupantLog.getVehicleID() + " time: " + occupantLog.getLogTime() + " to " + occupantLog.getEndTime());                
                
                recordList.add(occupantLog);
            }
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw new ProDAOException((statement != null) ? statement.toString() : "", e);

        }   // end catch
        finally
        { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally

        return recordList;
    }

    @Override
    public List<HOSRecord> getHOSRecords(Integer driverID, Interval interval, Boolean driverStatusOnly)  {

        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;

        ArrayList<HOSRecord> recordList = new ArrayList<HOSRecord>();
        
        try
        {
            conn = getConnection();
            statement = conn.prepareCall("{call hos_getFullRecords(?, ?, ?, ?)}");
            statement.setInt(1, driverID);
            statement.setLong(2, interval.getStartMillis());
            statement.setLong(3, interval.getEndMillis());
            statement.setBoolean(4, driverStatusOnly);
            if(logger.isDebugEnabled())
                logger.debug(statement.toString());

            resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                HOSRecord hosRecord = new HOSRecord();
                
                hosRecord.setHosLogID(resultSet.getLong(1));
                hosRecord.setDriverID(resultSet.getInt(2));
                hosRecord.setVehicleID(resultSet.getInt(3));
                long ms = resultSet.getLong(4);
                hosRecord.setLogTime(new Date(ms));
                hosRecord.setTimeZone(TimeZone.getTimeZone(resultSet.getString(5)));
                hosRecord.setStatus(HOSStatus.valueOf(resultSet.getInt(6)));
                hosRecord.setDriverDotType(RuleSetType.valueOf(resultSet.getInt(7)));
                hosRecord.setVehicleIsDOT(resultSet.getBoolean(8));
                hosRecord.setVehicleOdometer(Long.valueOf(resultSet.getInt(9)));
                hosRecord.setOrigin(HOSOrigin.valueOf(resultSet.getInt(10)));
                hosRecord.setTrailerID(resultSet.getString(11));
                hosRecord.setServiceID(resultSet.getString(12));
                hosRecord.setLat(resultSet.getFloat(13));
                hosRecord.setLng(resultSet.getFloat(14));
                hosRecord.setLocation(resultSet.getString(15));
                hosRecord.setOriginalLocation(resultSet.getString(16));
                hosRecord.setDeleted(resultSet.getBoolean(17));
                hosRecord.setEdited(resultSet.getBoolean(18));
                hosRecord.setChangedCnt(resultSet.getInt(19));
                hosRecord.setEditUserName(resultSet.getString(20));
                hosRecord.setTruckGallons(resultSet.getFloat(21));
                hosRecord.setTrailerGallons(resultSet.getFloat(22));
                hosRecord.setTripReportFlag(resultSet.getBoolean(23));
                hosRecord.setTripInspectionFlag(resultSet.getBoolean(24));
                hosRecord.setVehicleName(resultSet.getString(25));
                ms = resultSet.getLong(26);
                hosRecord.setOriginalLogTime(new Date(ms));
                hosRecord.setVehicleLicense(resultSet.getString(27));
                hosRecord.setEmployeeID(resultSet.getString(28));
                hosRecord.setEditUserID(resultSet.getInt(29));
                hosRecord.setSingleDriver(resultSet.getBoolean(30));
                hosRecord.setOriginalStatus(HOSStatus.valueOf(resultSet.getInt(31)));
                hosRecord.setMobileUnitID(resultSet.getString(32));
//                hosRecord.setInspectionType(InspectionType.valueOf(resultSet.getInt(33)));
                
                recordList.add(hosRecord);
            }
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw new ProDAOException((statement != null) ? statement.toString() : "", e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally

        return recordList;
    }
    
    @Override
    public List<HOSRecord> getRecordsForVehicle(Integer vehicleID, Interval interval, Boolean driverStatusOnly)  {

        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;

        ArrayList<HOSRecord> recordList = new ArrayList<HOSRecord>();
        
        try
        {
            conn = getConnection();
            statement = conn.prepareCall("{call hos_getRecordsForVehicle(?, ?, ?, ?)}");
            statement.setInt(1, vehicleID);
            statement.setLong(2, interval.getStartMillis());
            statement.setLong(3, interval.getEndMillis());
            statement.setBoolean(4, driverStatusOnly);
            if(logger.isDebugEnabled())
                logger.debug(statement.toString());

            resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                HOSRecord hosRecord = new HOSRecord();
                
                hosRecord.setHosLogID(resultSet.getLong(1));
                hosRecord.setDriverID(resultSet.getInt(2));
                hosRecord.setVehicleID(resultSet.getInt(3));
                long ms = resultSet.getLong(4);
                hosRecord.setLogTime(new Date(ms));
                hosRecord.setTimeZone(TimeZone.getTimeZone(resultSet.getString(5)));
                hosRecord.setStatus(HOSStatus.valueOf(resultSet.getInt(6)));
                hosRecord.setDriverDotType(RuleSetType.valueOf(resultSet.getInt(7)));
                hosRecord.setVehicleIsDOT(resultSet.getBoolean(8));
                hosRecord.setVehicleOdometer(Long.valueOf(resultSet.getInt(9)));
                hosRecord.setOrigin(HOSOrigin.valueOf(resultSet.getInt(10)));
                hosRecord.setTrailerID(resultSet.getString(11));
                hosRecord.setServiceID(resultSet.getString(12));
                hosRecord.setLat(resultSet.getFloat(13));
                hosRecord.setLng(resultSet.getFloat(14));
                hosRecord.setLocation(resultSet.getString(15));
                hosRecord.setOriginalLocation(resultSet.getString(16));
                hosRecord.setDeleted(resultSet.getBoolean(17));
                hosRecord.setEdited(resultSet.getBoolean(18));
                hosRecord.setChangedCnt(resultSet.getInt(19));
                hosRecord.setEditUserName(resultSet.getString(20));
                hosRecord.setTruckGallons(resultSet.getFloat(21));
                hosRecord.setTrailerGallons(resultSet.getFloat(22));
                hosRecord.setTripReportFlag(resultSet.getBoolean(23));
                hosRecord.setTripInspectionFlag(resultSet.getBoolean(24));
                hosRecord.setVehicleName(resultSet.getString(25));
                ms = resultSet.getLong(26);
                hosRecord.setOriginalLogTime(new Date(ms));
                hosRecord.setVehicleLicense(resultSet.getString(27));
                hosRecord.setEmployeeID(resultSet.getString(28));
                hosRecord.setEditUserID(resultSet.getInt(29));
                hosRecord.setSingleDriver(resultSet.getBoolean(30));
                hosRecord.setOriginalStatus(HOSStatus.valueOf(resultSet.getInt(31)));
                hosRecord.setMobileUnitID(resultSet.getString(32));
//                hosRecord.setInspectionType(InspectionType.valueOf(resultSet.getInt(33)));
                
                recordList.add(hosRecord);
            }
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw new ProDAOException((statement != null) ? statement.toString() : "", e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally

        return recordList;
    }
        

    @Override
    public List<HOSRecord> getFuelStopRecordsForVehicle(Integer vehicleID, Interval interval)  {

        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;

        ArrayList<HOSRecord> recordList = new ArrayList<HOSRecord>();
        
        try
        {
            conn = getConnection();
            statement = conn.prepareCall("{call hos_getFuelStopRecordsForVehicle(?, ?, ?)}");
            statement.setInt(1, vehicleID);
            statement.setLong(2, interval.getStartMillis());
            statement.setLong(3, interval.getEndMillis());
            if(logger.isDebugEnabled())
                logger.debug(statement.toString());

            resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                HOSRecord hosRecord = new HOSRecord();
                
                hosRecord.setHosLogID(resultSet.getLong(1));
                hosRecord.setDriverID(resultSet.getInt(2));
                hosRecord.setVehicleID(resultSet.getInt(3));
                long ms = resultSet.getLong(4);
                hosRecord.setLogTime(new Date(ms));
                hosRecord.setTimeZone(TimeZone.getTimeZone(resultSet.getString(5)));
                hosRecord.setStatus(HOSStatus.valueOf(resultSet.getInt(6)));
                hosRecord.setDriverDotType(RuleSetType.valueOf(resultSet.getInt(7)));
                hosRecord.setVehicleIsDOT(resultSet.getBoolean(8));
                hosRecord.setVehicleOdometer(Long.valueOf(resultSet.getInt(9)));
                hosRecord.setOrigin(HOSOrigin.valueOf(resultSet.getInt(10)));
                hosRecord.setTrailerID(resultSet.getString(11));
                hosRecord.setServiceID(resultSet.getString(12));
                hosRecord.setLat(resultSet.getFloat(13));
                hosRecord.setLng(resultSet.getFloat(14));
                hosRecord.setLocation(resultSet.getString(15));
                hosRecord.setOriginalLocation(resultSet.getString(16));
                hosRecord.setDeleted(resultSet.getBoolean(17));
                hosRecord.setEdited(resultSet.getBoolean(18));
                hosRecord.setChangedCnt(resultSet.getInt(19));
                hosRecord.setEditUserName(resultSet.getString(20));
                hosRecord.setTruckGallons(resultSet.getFloat(21));
                hosRecord.setTrailerGallons(resultSet.getFloat(22));
                hosRecord.setTripReportFlag(resultSet.getBoolean(23));
                hosRecord.setTripInspectionFlag(resultSet.getBoolean(24));
                hosRecord.setVehicleName(resultSet.getString(25));
                ms = resultSet.getLong(26);
                hosRecord.setOriginalLogTime(new Date(ms));
                hosRecord.setVehicleLicense(resultSet.getString(27));
                hosRecord.setEmployeeID(resultSet.getString(28));
                hosRecord.setEditUserID(resultSet.getInt(29));
                hosRecord.setSingleDriver(resultSet.getBoolean(30));
                hosRecord.setOriginalStatus(HOSStatus.valueOf(resultSet.getInt(31)));
                
                recordList.add(hosRecord);
            }
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw new ProDAOException((statement != null) ? statement.toString() : "", e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally

        return recordList;
    }
    
    @Override
    public List<HOSRecord> getHOSRecordsFilteredByInterval(Integer driverID, Interval interval, Boolean driverStatusOnly) {
        List<HOSRecord> hosRecordList = getHOSRecords(driverID, interval, driverStatusOnly);
        if (hosRecordList == null)
            return null;
        if(logger.isDebugEnabled())
            logger.debug("original list size: " + hosRecordList.size());
        
        List<HOSRecord> hosFilteredRecordList = new ArrayList<HOSRecord>();
        for (HOSRecord rec : hosRecordList)
            if (interval.contains(rec.getLogTime().getTime()))
                hosFilteredRecordList.add(rec);
        
        
        return hosFilteredRecordList;
        
        
    }
    @Override
    public Long create(Long id, HOSRecord hosRecord) {
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;
        
        
        try
        {
            conn = getConnection();
            statement = conn.prepareCall("{call hos_createFromAdminPortal(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            statement.setInt(1, hosRecord.getDriverID());
            statement.setInt(2, hosRecord.getVehicleID() == null ? 0 : hosRecord.getVehicleID());
            statement.setLong(3, hosRecord.getLogTime().getTime());
            statement.setString(4, hosRecord.getTimeZone().getID());
            statement.setInt(5, hosRecord.getStatus().getCode());
            statement.setInt(6, hosRecord.getDriverDotType().getCode());
            statement.setString(7, hosRecord.getTrailerID());
            statement.setString(8, hosRecord.getServiceID());
            statement.setString(9, hosRecord.getLocation());
            statement.setFloat(10, (hosRecord.getTruckGallons()== null) ? 0f : hosRecord.getTruckGallons());
            statement.setFloat(11, (hosRecord.getTrailerGallons() == null) ? 0f : hosRecord.getTrailerGallons());
            statement.setInt(12, hosRecord.getEditUserID());
            statement.setString(13, hosRecord.getMobileUnitID());
            
            if(logger.isDebugEnabled())
                logger.debug(statement.toString());
            

            resultSet = statement.executeQuery();

            if (resultSet.next())
                id = resultSet.getLong(1);
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw new ProDAOException((statement != null) ? statement.toString() : "", e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally
        
        return id;
    }

    @Override
    public Long createFromNote(HOSRecord hosRecord) {
        Long id = null;
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;
        
        
        try
        {
            conn = getConnection();
            statement = conn.prepareCall("{call hos_createFromNote(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            statement.setInt(1, hosRecord.getDeviceID());
            statement.setInt(2, hosRecord.getVehicleID());
            statement.setLong(3, 0); //Note ID
            statement.setInt(4, hosRecord.getStatus() == null ? null : hosRecord.getStatus().getCode());
            statement.setLong(5, hosRecord.getLogTime().getTime());
            statement.setLong(6, hosRecord.getVehicleOdometer());
            statement.setFloat(7, hosRecord.getLat());
            statement.setFloat(8, hosRecord.getLng());
            statement.setString(9, hosRecord.getLocation());
            statement.setBoolean(10, hosRecord.getUserEnteredLocationFlag());
            statement.setByte(11, hosRecord.getNoteFlags());
            statement.setString(12, hosRecord.getTrailerID());
            statement.setString(13, hosRecord.getServiceID());
            statement.setFloat(14, (hosRecord.getTruckGallons()== null) ? 0f : hosRecord.getTruckGallons());
            statement.setFloat(15, (hosRecord.getTrailerGallons() == null) ? 0f : hosRecord.getTrailerGallons());
            statement.setString(16, hosRecord.getEmployeeID());
            statement.setInt(17, hosRecord.getStateID());
            statement.setBoolean(18, hosRecord.getTripInspectionFlag());
            statement.setBoolean(19, hosRecord.getTripReportFlag());
            statement.setString(20, hosRecord.getMobileUnitID());
//            statement.setInt(21, hosRecord.getInspectionType() == null ? 0 : hosRecord.getInspectionType().getCode());
//            statement.setInt(22, hosRecord.getOrigin() == null ? HOSOrigin.DEVICE.getCode() : hosRecord.getOrigin().getCode());
            
            if(logger.isDebugEnabled())
                logger.debug(statement.toString());
            

            resultSet = statement.executeQuery();

            if (resultSet.next())
                id = resultSet.getLong(1);
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw new ProDAOException((statement != null) ? statement.toString() : "", e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally
        
        return id;
    }
    
    private final static String DELETE_HOS_LOG = "UPDATE hoslog set deletedFlag = 1, editedFlag=1, editCount=editCount+1, timeLastUpdated=UTC_TIMESTAMP() WHERE hosLogId = ?"; 

    @Override
    public Integer deleteByID(Long id) {
        Connection conn = null;
        PreparedStatement statement = null;
        
        try
        {
            conn = getConnection();
            statement = conn.prepareStatement(DELETE_HOS_LOG);
            statement.setLong(1, id);
            
            if(logger.isDebugEnabled())
                logger.debug(statement.toString());
            
            statement.executeUpdate();

        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw new ProDAOException((statement != null) ? statement.toString() : "", e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(statement);
            close(conn);
        } // end finally

        return 1;
    }

    @Override
    public HOSRecord findByID(Long id) {
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;

        HOSRecord hosRecord = null;
        
        try
        {
            conn = getConnection();
            statement = conn.prepareCall("{call hos_getFullRecord(?)}");
            statement.setLong(1, id);
            
            if(logger.isDebugEnabled())
                logger.debug(statement.toString());

                resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                
                hosRecord = new HOSRecord();
                hosRecord.setHosLogID(resultSet.getLong(1));
                hosRecord.setDriverID(resultSet.getInt(2));
                hosRecord.setVehicleID(resultSet.getInt(3));
                long ms = resultSet.getLong(4);
                hosRecord.setLogTime(new Date(ms));
                hosRecord.setTimeZone(TimeZone.getTimeZone(resultSet.getString(5)));
                hosRecord.setStatus(HOSStatus.valueOf(resultSet.getInt(6)));
                hosRecord.setDriverDotType(RuleSetType.valueOf(resultSet.getInt(7)));
                hosRecord.setVehicleIsDOT(resultSet.getBoolean(8));
                hosRecord.setVehicleOdometer(Long.valueOf(resultSet.getInt(9)));
                hosRecord.setOrigin(HOSOrigin.valueOf(resultSet.getInt(10)));
                hosRecord.setTrailerID(resultSet.getString(11));
                hosRecord.setServiceID(resultSet.getString(12));
                hosRecord.setLat(resultSet.getFloat(13));
                hosRecord.setLng(resultSet.getFloat(14));
                hosRecord.setLocation(resultSet.getString(15));
                hosRecord.setOriginalLocation(resultSet.getString(16));
                hosRecord.setDeleted(resultSet.getBoolean(17));
                hosRecord.setEdited(resultSet.getBoolean(18));
                hosRecord.setChangedCnt(resultSet.getInt(19));
                hosRecord.setEditUserName(resultSet.getString(20));
                hosRecord.setTruckGallons(resultSet.getFloat(21));
                hosRecord.setTrailerGallons(resultSet.getFloat(22));
                hosRecord.setTripReportFlag(resultSet.getBoolean(23));
                hosRecord.setTripInspectionFlag(resultSet.getBoolean(24));
                hosRecord.setVehicleName(resultSet.getString(25));
                ms = resultSet.getLong(26);
                hosRecord.setOriginalLogTime(new Date(ms));
                hosRecord.setVehicleLicense(resultSet.getString(27));
                hosRecord.setEmployeeID(resultSet.getString(28));
                hosRecord.setEditUserID(resultSet.getInt(29));
                hosRecord.setSingleDriver(resultSet.getBoolean(30));
                hosRecord.setMobileUnitID(resultSet.getString(31));
//                hosRecord.setInspectionType(InspectionType.valueOf(resultSet.getInt(32)));
            
            }
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw new ProDAOException((statement != null) ? statement.toString() : "", e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally

        return hosRecord;
    }

    @Override
    public Integer update(HOSRecord hosRecord) {
        Connection conn = null;
        CallableStatement statement = null;
        
        
        try
        {
            conn = getConnection();
            statement = conn.prepareCall("{call hos_update(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            statement.setLong(1, hosRecord.getHosLogID());
            statement.setInt(2, hosRecord.getDriverID());
            statement.setInt(3, hosRecord.getVehicleID() == null ? 0 : hosRecord.getVehicleID());
            statement.setLong(4, hosRecord.getLogTime().getTime());
            statement.setString(5, hosRecord.getTimeZone().getID());
            statement.setInt(6, hosRecord.getStatus().getCode());
            statement.setInt(7, hosRecord.getDriverDotType().getCode());
            statement.setString(8, hosRecord.getTrailerID());
            statement.setString(9, hosRecord.getServiceID());
            statement.setString(10, hosRecord.getLocation());
            statement.setFloat(11, hosRecord.getTruckGallons() == null ? 0f : hosRecord.getTruckGallons());
            statement.setFloat(12, hosRecord.getTrailerGallons() == null ? 0f : hosRecord.getTrailerGallons());
            statement.setInt(13, hosRecord.getEditUserID());
            statement.setString(14, hosRecord.getMobileUnitID());
            
            if(logger.isDebugEnabled())
                logger.debug(statement.toString());
            
            statement.executeUpdate();

        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw new ProDAOException((statement != null) ? statement.toString() : "", e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(statement);
            close(conn);
        } // end finally
        return 1;
    }
    
    @Override
    public HOSDriverLogin isValidLogin(String commAddress, String employeeId, long loginTime, boolean occupantFlag, int odometer) 
    {
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;
        HOSDriverLogin driverLogin = new HOSDriverLogin(0, 0, 0);
        try
        {
            conn = getConnection();
            
            statement = conn.prepareCall("{call hos_isValidLogin(?, ?, ?, ?, ?)}");


            statement.setString(1, commAddress);
            statement.setString(2, employeeId);
            statement.setLong(3, loginTime);
            statement.setBoolean(4, occupantFlag);
            statement.setInt(5, odometer);
            
            if(logger.isDebugEnabled())
                logger.debug(statement.toString());

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                driverLogin.setAcctID(resultSet.getInt(1));
                driverLogin.setDeviceID(resultSet.getInt(2));
                driverLogin.setDriverID(resultSet.getInt(3));
                driverLogin.setDriverDotType(RuleSetType.valueOf(resultSet.getInt(4)));
                driverLogin.setTimezoneID(resultSet.getString(5));
                driverLogin.setCurrentVehicleID(resultSet.getInt(6));
                driverLogin.setCurrentOcupantFlag(resultSet.getBoolean(7));
                driverLogin.setCurrentDeviceID(resultSet.getInt(8));
                driverLogin.setCurrentAddress(resultSet.getString(9));
                driverLogin.setVehicleDotType(RuleSetType.valueOf(resultSet.getInt(10)));
                driverLogin.setMeasurementType(MeasurementType.valueOf(resultSet.getInt(11)));
                driverLogin.setFuelEfficiencyType(FuelEfficiencyType.valueOf(resultSet.getInt(12)));
                driverLogin.setFobId(resultSet.getString(13));
            }

        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw new ProDAOException((statement != null) ? statement.toString() : "", e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally

        return driverLogin;
    }

    @Override
    public HOSDriverLogin getDriverForEmpid(String commAddress, String employeeId) 
    {
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;
        HOSDriverLogin driverLogin = new HOSDriverLogin(0, 0, 0);
        try
        {
            conn = getConnection();
            statement = conn.prepareCall("{call hos_getDriverForEmpid(?, ?)}");
            statement.setString(1, commAddress);
            statement.setString(2, employeeId);
            
            if(logger.isDebugEnabled())
                logger.debug(statement.toString());
            
            resultSet = statement.executeQuery();

            
            if (resultSet.next()) {
                driverLogin.setAcctID(resultSet.getInt(1));
                driverLogin.setDeviceID(resultSet.getInt(2));
                driverLogin.setDriverID(resultSet.getInt(3));
                driverLogin.setVehicleID(resultSet.getInt(4));
                driverLogin.setDriverDotType(RuleSetType.valueOf(resultSet.getInt(5)));
                driverLogin.setTimezoneID(resultSet.getString(6));
                driverLogin.setMeasurementType(MeasurementType.valueOf(resultSet.getInt(7)));
                driverLogin.setFuelEfficiencyType(FuelEfficiencyType.valueOf(resultSet.getInt(8)));
                driverLogin.setFobId(resultSet.getString(9));
            }

        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw new ProDAOException((statement != null) ? statement.toString() : "", e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally

        return driverLogin;
    }

    @Override
    public HOSDriverLogin getDriverForEmpidLastName(String employeeId, String lastName) 
    {
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;
        HOSDriverLogin driverLogin = new HOSDriverLogin(0, 0, 0);
        try
        {
            conn = getConnection();
            statement = conn.prepareCall("{call hos_getDriverForEmpidLastname(?, ?)}");
            statement.setString(1, employeeId);
            statement.setString(2, lastName);
            
            if(logger.isDebugEnabled())
                logger.debug(statement.toString());
            
            resultSet = statement.executeQuery();

            
            if (resultSet.next()) {
                driverLogin.setDriverID(resultSet.getInt(1));
                driverLogin.setDriverDotType(RuleSetType.valueOf(resultSet.getInt(2)));
                driverLogin.setTimezoneID(resultSet.getString(3));
                driverLogin.setFobId(resultSet.getString(4));
            }

        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw new ProDAOException((statement != null) ? statement.toString() : "", e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally

        return driverLogin;
    }

    @Override
    public List<HOSRecord> getHOSRecordsForCommAddress(String address, List<HOSRecord> paramList)  
    {
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;
        List<HOSRecord> recordList = new ArrayList<HOSRecord>();
        List<HOSRecord> finalRecordList = new ArrayList<HOSRecord>();
        
        Integer driverID;
        try
        {
            conn = getConnection();
            statement = conn.prepareCall("{call hos_getDriverIDForCommAddress(?)}");
            statement.setString(1, address);
            
            if(logger.isDebugEnabled())
                logger.debug(statement.toString());
            
            resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                driverID = resultSet.getInt(1);
    
                //Grab the min/max dates from the list to use for call.
                Date startDate = new Date();
                Date endDate = new Date(0L);
                for (HOSRecord record : paramList)
                {
                    if (record.getLogTime().before(startDate))
                        startDate = record.getLogTime();
                    
                    if (record.getLogTime().after(endDate))
                        endDate = record.getLogTime();
                }
                
                Interval interval = new Interval(startDate.getTime(), endDate.getTime(), DateTimeZone.UTC);

                recordList = getHOSRecords(driverID, interval, true);
                //filter out the records that were contained in the date interval for the 
                //driver but weren't in params list.
                for (HOSRecord record : recordList) {
                    for (HOSRecord param : paramList) {
                        if (record.getLogTime().equals(param.getLogTime()) 
                            && record.getStatus().equals(param.getStatus()))
                        {
                            finalRecordList.add(record);
                        }
                    }
                }
            }
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw new ProDAOException((statement != null) ? statement.toString() : "", e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally

        return finalRecordList;
    }

    @Override
    public HOSOccupantInfo getOccupantInfo(Integer driverID) {
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;

        HOSOccupantInfo occupantInfo = null;
        
        try
        {
            conn = getConnection();
            statement = conn.prepareCall("{call hos_getOccupantInfo(?)}");
            statement.setInt(1, driverID);
            
            if(logger.isDebugEnabled())
                logger.debug(statement.toString());
            
            resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                
                occupantInfo = new HOSOccupantInfo();
                occupantInfo.setFullName(resultSet.getString(1));
                occupantInfo.setEmpId(resultSet.getString(2));
                occupantInfo.setFobId(resultSet.getString(3));
            }
        }   // end try
        catch (SQLException e) { 
            throw new ProDAOException((statement != null) ? statement.toString() : "", e);
        } 
        finally
        { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally

        return occupantInfo;
    }

    private static final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
    private static final String FETCH_MILEAGE_VEHICLE_DAY = "select driverID, odometer6 from agg where vehicleID = ? and aggDate = ?";
    
    @Override
    public Map<Integer, Long> fetchMileageForDayVehicle(DateTime day, Integer vehicleID) {
        
        Map<Integer, Long> mileageMap = new HashMap<Integer, Long>();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(FETCH_MILEAGE_VEHICLE_DAY);
            statement.setInt(1, vehicleID);
            statement.setDate(2, java.sql.Date.valueOf(dateFormatter.print(day)));
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Integer driverID = resultSet.getInt(1);
                Long mileage = resultSet.getLong(2);
                mileageMap.put(driverID, mileage);
                
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
        
        return mileageMap;
    }

    @Override
    public void logoutDriverFromDevice(String commAddress, String employeeId, long logoutTime,  int odometer) 
    {
        Connection conn = null;
        CallableStatement statement = null;
        try
        {
            conn = getConnection();
            
            statement = conn.prepareCall("{call hos_logoutDriverFromDevice(?, ?, ?, ?)}");


            statement.setString(1, commAddress);
            statement.setString(2, employeeId);
            statement.setLong(3, logoutTime);
            statement.setInt(4, odometer);
            
            if(logger.isDebugEnabled())
                logger.debug(statement.toString());

            statement.executeUpdate();

        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw new ProDAOException((statement != null) ? statement.toString() : "", e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(statement);
            close(conn);
        } // end finally
    }
    
    private final static String FETCH_IMEI_FOR_OCCUPANT = "SELECT imei FROM hosvehiclelogin l, device d WHERE l.deviceID=d.deviceID AND l.logoutTime IS NULL and l.occupantFlag=1 AND l.driverID = ? AND loginTime < ?"; 
    @Override
    public String fetchIMEIForOccupant(Integer driverID, Integer startTime) 
    {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String imei = "";
        
        try
        {
            conn = getConnection();
            
            statement = conn.prepareCall(FETCH_IMEI_FOR_OCCUPANT);


            statement.setInt(1, driverID);
            statement.setString(2, dbdateFormat.format(startTime));
            
            if(logger.isDebugEnabled())
                logger.debug(statement.toString());

            resultSet = statement.executeQuery();
            if (resultSet.next()) 
                imei = resultSet.getString(1);
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw new ProDAOException((statement != null) ? statement.toString() : "", e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally

        return imei;
    }

    
    
    // home office location for vehicle is in the actual settings 
    private final static String FETCH_VEHICLE_HOME_LOCATION = "select settingID, value from actualVSet where settingID in (1048, 1049) and vehicleID=?"; 
    @Override
    public LatLng getVehicleHomeOfficeLocation(Integer vehicleID) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        LatLng homeLocation = null;
        try
        {
            conn = getConnection();
            
            statement = conn.prepareCall(FETCH_VEHICLE_HOME_LOCATION);


            statement.setInt(1, vehicleID);
            
            if(logger.isDebugEnabled())
                logger.debug(statement.toString());

            resultSet = statement.executeQuery();
            while (resultSet.next()) { 
                Integer settingID = resultSet.getInt(1);
                if (homeLocation == null) {
                    homeLocation = new LatLng();
                }
                if (settingID == 1048) {
                    homeLocation.setLat(resultSet.getFloat(2));
                }
                else {
                    homeLocation.setLng(resultSet.getFloat(2));
                }
            }
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw new ProDAOException((statement != null) ? statement.toString() : "", e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally

        return homeLocation;
        
    }
    
    private final static String FETCH_HOS_DELTA_RECORDS = "SELECT h.hosLogID, h.logTime, cl.logTime as originalLogTime, " + 
                    "h.status, cl.status as originalStatus, h.driverDOTType, h.origin, h.deletedFlag, h.editedFlag, h.vehicleID, coalesce(v.deviceID, 0) as deviceID " +
                    "FROM hoslog h " + 
                    "left JOIN vddlog v on (h.vehicleID != 0 and h.vehicleID = v.vehicleID and h.logTime BETWEEN v.start AND coalesce(v.stop, UTC_TIMESTAMP())) " + 
                    "LEFT JOIN hoslog_changelog cl ON  (h.hosLogID = cl.hosLogID) " +
                    "WHERE h.driverID = ? AND (h.origin IN (2,3) OR h.editedFlag = true OR deviceID != ?) AND (h.timeLastUpdated > ?) AND h.status NOT IN (31,39,47,48) ORDER BY h.logTime DESC";
    @Override
    public List<HOSRecord> getHOSDeltaRecords(Integer driverID, Integer deviceID, Date deltaTime)  {

        String deltaTimeStr = dbdateFormat.format(deltaTime);
        
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        ArrayList<HOSRecord> recordList = new ArrayList<HOSRecord>();
        
        try
        {
            conn = getConnection();
            statement = conn.prepareCall(FETCH_HOS_DELTA_RECORDS);
            statement.setInt(1, driverID);
            statement.setInt(2, deviceID);
            statement.setString(3, deltaTimeStr);
            if(logger.isDebugEnabled())
                logger.debug(statement.toString());
            resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                HOSRecord hosRecord = new HOSRecord();
                
                hosRecord.setHosLogID(resultSet.getLong(1));
                hosRecord.setLogTime(getResultSetDate(resultSet, 2));
                hosRecord.setOriginalLogTime(getResultSetDate(resultSet, 3));
                hosRecord.setStatus(HOSStatus.valueOf(resultSet.getInt(4)));
                hosRecord.setOriginalStatus(resultSet.getObject(5) == null? null : HOSStatus.valueOf(resultSet.getInt(5)));
                hosRecord.setDriverDotType(RuleSetType.valueOf(resultSet.getInt(6)));
                hosRecord.setOrigin(HOSOrigin.valueOf(resultSet.getInt(7)));
                hosRecord.setDeleted(resultSet.getBoolean(8));
                hosRecord.setEdited(resultSet.getBoolean(9));
                hosRecord.setVehicleID(resultSet.getInt(10));
                hosRecord.setDeviceID(resultSet.getInt(11));
                     
                
                recordList.add(hosRecord);
            }
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw new ProDAOException((statement != null) ? statement.toString() : "", e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally

        return recordList;
    }
    
    
    private final static String COUNT_HOS_RECORDS_AT_TIMESTAMP = "select count(*) from hoslog where deletedFlag = false and driverID = ? and logTime = ? and hosLogID != ?";
    @Override
    public boolean otherHosRecordExistsForDriverTimestamp(Integer driverID, Date dateTime, Long hosLogID)  {
        boolean recordExists = false;
        String dateTimeStr = dbdateFormat.format(dateTime);
        
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try
        {
            conn = getConnection();
            statement = conn.prepareCall(COUNT_HOS_RECORDS_AT_TIMESTAMP);
            statement.setInt(1, driverID);
            statement.setString(2, dateTimeStr);
            statement.setLong(3, (hosLogID == null ? -1l : hosLogID));
            if(logger.isDebugEnabled())
                logger.debug(statement.toString());
            
            resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                Integer count = resultSet.getInt(1);
                recordExists = (count > 0);
            }
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw new ProDAOException((statement != null) ? statement.toString() : "", e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally

        return recordExists;
    }
    
    private final static String FETCH_HOS_RECORDS_AT_SUMMARY_TIME = "SELECT h.hosLogID, h.timeLastUpdated, h.timeAdded, h.logTime, cl.logTime as originalLogTime, " + 
            "h.status, cl.status as originalStatus, h.driverDOTType, coalesce(h.vehicleID,0), h.deletedFlag, h.editedFlag " +
            "FROM hoslog h " + 
            "LEFT JOIN hoslog_changelog cl ON  (h.hosLogID = cl.hosLogID) " +
            "WHERE h.driverID = ? AND h.status NOT IN (31,39,47,48) " +
            "AND (h.logTime between ? and ? or cl.logTime between ? and ?)";
    @Override
    public List<HOSRecord> getHOSRecordAtSummaryTime(Integer driverID, Integer vehicleID,  Date summaryTime, Date startTime, Date endTime)  {

        String startTimeStr = dbdateFormat.format(startTime);
        String endTimeStr = dbdateFormat.format(endTime);

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        ArrayList<HOSRecord> recordList = new ArrayList<HOSRecord>();
        
        try
        {
            conn = getConnection();
            statement = conn.prepareCall(FETCH_HOS_RECORDS_AT_SUMMARY_TIME);
            statement.setInt(1, driverID);
            statement.setString(2, startTimeStr);
            statement.setString(3, endTimeStr);
            statement.setString(4, startTimeStr);
            statement.setString(5, endTimeStr);
            if(logger.isDebugEnabled())
                logger.debug(statement.toString());
            
            resultSet = statement.executeQuery();
        
            while (resultSet.next())
            {
                HOSRecord hosRecord = new HOSRecord();
                
                hosRecord.setHosLogID(resultSet.getLong(1));
                Date lastUpdateTime = getResultSetDate(resultSet, 2);
                Date addedTime = getResultSetDate(resultSet, 3);
                Date logTime = getResultSetDate(resultSet, 4);
                Date originalLogTime = getResultSetDate(resultSet, 5);
                HOSStatus status = HOSStatus.valueOf(resultSet.getInt(6));
                HOSStatus originalStatus = resultSet.getObject(7) == null ? null : HOSStatus.valueOf(resultSet.getInt(7));
                hosRecord.setDriverDotType(RuleSetType.valueOf(resultSet.getInt(8)));
                hosRecord.setVehicleID(resultSet.getInt(9));
                hosRecord.setDeleted(resultSet.getBoolean(10));
                hosRecord.setEdited(resultSet.getBoolean(11));
                if (addedTime.after(summaryTime) && (!hosRecord.getVehicleID().equals(vehicleID) || hosRecord.getDeleted() || hosRecord.getEdited())) {
                    continue;
                }
                
                if (lastUpdateTime.after(summaryTime)) {
                    hosRecord.setLogTime(originalLogTime == null ? logTime : originalLogTime);
                    hosRecord.setStatus(originalStatus== null ? status : originalStatus);
                }
                else {
                    hosRecord.setLogTime(logTime);
                    hosRecord.setStatus(status);
                }

                     
                
                recordList.add(hosRecord);
            }
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw new ProDAOException((statement != null) ? statement.toString() : "", e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally
    
        Collections.sort(recordList);
        return recordList;
    }


    private Date getResultSetDate(ResultSet resultSet, Integer index) throws SQLException {
        String dateStr = resultSet.getString(index);
        Date date = null;
        try {
            date = dateStr == null ? null : dbdateFormat.parse(dateStr);
        } catch (ParseException e) {
        }
        return date;
    }
}