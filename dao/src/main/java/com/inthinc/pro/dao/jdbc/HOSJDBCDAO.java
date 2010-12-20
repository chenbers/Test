package com.inthinc.pro.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

import com.inthinc.hos.model.HOSOrigin;
import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.model.hos.HOSDriverLogin;
import com.inthinc.pro.model.hos.HOSGroupMileage;
import com.inthinc.pro.model.hos.HOSOccupantHistory;
import com.inthinc.pro.model.hos.HOSOccupantInfo;
import com.inthinc.pro.model.hos.HOSOccupantLog;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.model.hos.HOSVehicleDayData;
import com.inthinc.pro.model.hos.HOSVehicleMileage;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.MeasurementType;



public class HOSJDBCDAO extends GenericJDBCDAO implements HOSDAO {
    

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(HOSJDBCDAO.class);
    
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
            statement = conn.prepareCall("{call hos_getMileageByGroup(?, ?, ?)}");
            statement.setInt(1, groupID);
            statement.setLong(2, interval.getStartMillis());
            statement.setLong(3, interval.getEndMillis());
            
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

    @Override
    public List<HOSVehicleMileage> getHOSVehicleMileage(Integer groupID, Interval interval, Boolean noDriver) {
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;

        ArrayList<HOSVehicleMileage> recordList = new ArrayList<HOSVehicleMileage>();
        
        try
        {
            conn = getConnection();
            statement = conn.prepareCall("{call hos_getVehicleMileageByGroup(?, ?, ?)}");
            statement.setInt(1, groupID);
            statement.setLong(2, interval.getStartMillis());
            statement.setLong(3, interval.getEndMillis());
            
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
    
    public List<HOSOccupantHistory> getHOSOccupantHistory(String commAddress, String employeeId) {
        HOSDriverLogin driverLogin = getDriverForEmpid(commAddress, employeeId);
        return getHOSOccupantHistory(driverLogin);
    }

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

        for (int i = daysback; i <= 0; i++)
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
                    if (occupantDuplicateCheckList.indexOf(occupantLog.getDriverID()) == -1)
                    {
                        occupantDuplicateCheckList.add(occupantLog.getDriverID());
                        occupantHistoryList.add(new HOSOccupantHistory((i * -1), occupantLog.getDriverID(), startDayOfYear));
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
            
            resultSet = statement.executeQuery();
            if(logger.isDebugEnabled())
                logger.debug("statment: " + statement.toString());            

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
                
                recordList.add(occupantLog);
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
                
                hosRecord.setHosLogID(resultSet.getInt(1));
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
                
                recordList.add(hosRecord);
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
    public List<HOSVehicleDayData> getHOSVehicleDataByDay(Integer driverID, Interval interval) {
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;

        ArrayList<HOSVehicleDayData> recordList = new ArrayList<HOSVehicleDayData>();
        
        try
        {
            conn = getConnection();
            statement = conn.prepareCall("{call hos_getHOSVehicleRecordsForDriver(?, ?, ?)}");
            statement.setInt(1, driverID);
            statement.setLong(2, interval.getStartMillis());
            statement.setLong(3, interval.getEndMillis());
            resultSet = statement.executeQuery();

            long stopOdometer;
            long milesDriven;
            HOSVehicleDayData hosRecord = null;
            while (resultSet.next())
            {
                hosRecord = new HOSVehicleDayData();

                hosRecord.setVehicleID(resultSet.getInt(1));
                hosRecord.setVehicleName(resultSet.getString(2));
                long ms = resultSet.getLong(3);
                hosRecord.setDay(new Date(ms));
                hosRecord.setStartOdometer(resultSet.getLong(4));
                stopOdometer = resultSet.getLong(5);

                milesDriven = (stopOdometer > hosRecord.getStartOdometer()) ? (stopOdometer - hosRecord.getStartOdometer()) : 0;
                
                hosRecord.setMilesDriven(milesDriven);
                
                recordList.add(hosRecord);
                
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

    @Override
    public Integer create(Integer id, HOSRecord hosRecord) {
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;
        
        
        try
        {
            conn = getConnection();
            statement = conn.prepareCall("{call hos_createFromAdminPortal(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            statement.setInt(1, hosRecord.getDriverID());
            statement.setInt(2, hosRecord.getVehicleID() == null ? 0 : hosRecord.getVehicleID());
            statement.setLong(3, hosRecord.getLogTime().getTime());
            statement.setString(4, hosRecord.getTimeZone().getID());
            statement.setInt(5, hosRecord.getStatus().getCode());
            statement.setInt(6, hosRecord.getDriverDotType().getCode());
            statement.setString(7, hosRecord.getTrailerID());
            statement.setString(8, hosRecord.getServiceID());
            statement.setString(9, hosRecord.getLocation());
            statement.setInt(10, hosRecord.getEditUserID());

            resultSet = statement.executeQuery();

            if (resultSet.next())
                id = resultSet.getInt(1);
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
        
        return id;
    }

    @Override
    public Integer deleteByID(Integer id) {
        Connection conn = null;
        CallableStatement statement = null;
        
        try
        {
            conn = getConnection();
            statement = conn.prepareCall("{call hos_delete(?)}");
            statement.setInt(1, id);
            
            statement.executeUpdate();

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

        return id;
    }

    @Override
    public HOSRecord findByID(Integer id) {
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;

        HOSRecord hosRecord = null;
        
        try
        {
            conn = getConnection();
            statement = conn.prepareCall("{call hos_getFullRecord(?)}");
            statement.setInt(1, id);
            
            resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                
                hosRecord = new HOSRecord();
                hosRecord.setHosLogID(resultSet.getInt(1));
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

        return hosRecord;
    }

    @Override
    public Integer update(HOSRecord hosRecord) {
        Connection conn = null;
        CallableStatement statement = null;
        
        
        try
        {
            conn = getConnection();
            statement = conn.prepareCall("{call hos_update(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            statement.setInt(1, hosRecord.getHosLogID());
            statement.setInt(2, hosRecord.getDriverID());
            statement.setInt(3, hosRecord.getVehicleID() == null ? 0 : hosRecord.getVehicleID());
            statement.setLong(4, hosRecord.getLogTime().getTime());
            statement.setString(5, hosRecord.getTimeZone().getID());
            statement.setInt(6, hosRecord.getStatus().getCode());
            statement.setInt(7, hosRecord.getDriverDotType().getCode());
            statement.setString(8, hosRecord.getTrailerID());
            statement.setString(9, hosRecord.getServiceID());
            statement.setString(10, hosRecord.getLocation());
            statement.setInt(11, hosRecord.getEditUserID());

            
            statement.executeUpdate();

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
        return hosRecord.getHosLogID();
    }

    public HOSDriverLogin isValidLogin(String commAddress, String employeeId, long loginTime, boolean occupantFlag, int odometer) 
    {
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;
        HOSDriverLogin driverLogin = new HOSDriverLogin(0, 0, 0);
        try
        {
            conn = getConnection();
            
            if (conn == null)
                throw new ProDAOException("conn is null", new Exception("conn is null"));;
                
            statement = conn.prepareCall("{call hos_isValidLogin(?, ?, ?, ?, ?)}");


            statement.setString(1, commAddress);
            statement.setString(2, employeeId);
            statement.setLong(3, loginTime);
            statement.setBoolean(4, occupantFlag);
            statement.setInt(5, odometer);
            

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

        return driverLogin;
    }

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
            
            resultSet = statement.executeQuery();

            
            if (resultSet.next()) {
                driverLogin.setAcctID(resultSet.getInt(1));
                driverLogin.setDeviceID(resultSet.getInt(2));
                driverLogin.setDriverID(resultSet.getInt(3));
                driverLogin.setDriverDotType(RuleSetType.valueOf(resultSet.getInt(4)));
                driverLogin.setTimezoneID(resultSet.getString(5));
                driverLogin.setMeasurementType(MeasurementType.valueOf(resultSet.getInt(6)));
                driverLogin.setFuelEfficiencyType(FuelEfficiencyType.valueOf(resultSet.getInt(7)));
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

        return driverLogin;
    }

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
            
            resultSet = statement.executeQuery();

            
            if (resultSet.next()) {
                driverLogin.setDriverID(resultSet.getInt(1));
                driverLogin.setDriverDotType(RuleSetType.valueOf(resultSet.getInt(2)));
                driverLogin.setTimezoneID(resultSet.getString(3));
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

        return driverLogin;
    }

    
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
                        startDate = record.getLogTime();
                }
                
                Interval interval = new Interval(startDate.getTime(), endDate.getTime(), DateTimeZone.UTC);
                recordList = getHOSRecords(driverID, interval, true);

                //filter out the records that were contained in the date interval for the 
                //driver but weren't in params list.
                for (HOSRecord record : recordList) {
                    for (HOSRecord param : paramList) {
                        if (record.getLogTime() == param.getLogTime() 
                            && record.getStatus() == param.getStatus())
                        {
                            finalRecordList.add(record);
                        }
                    }
                }
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

        return finalRecordList;
    }

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
            
            resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                
                occupantInfo = new HOSOccupantInfo();
                occupantInfo.setFullName(resultSet.getString(1));
                occupantInfo.setEmpId(resultSet.getString(2));
            }
        }   // end try
        catch (SQLException e) { 
            throw new ProDAOException(statement.toString(), e);
        } 
        finally
        { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally

        return occupantInfo;
    }

}
