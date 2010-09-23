package com.inthinc.pro.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.joda.time.Interval;

import com.inthinc.hos.model.HOSOrigin;
import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.model.hos.HOSGroupMileage;
import com.inthinc.pro.model.hos.HOSOccupantLog;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.model.hos.HOSVehicleDayData;
import com.inthinc.pro.model.hos.HOSVehicleMileage;



public class HOSJDBCDAO extends GenericJDBCDAO implements HOSDAO {

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
            statement.setTimestamp(2, new Timestamp(interval.getStartMillis()));
            statement.setTimestamp(3, new Timestamp(interval.getEndMillis()));
            
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
            logger.error("sql hosLog", e);
            //throw e;
            return null;

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
            statement = conn.prepareCall("{call hos_getMileageByGroup(?, ?, ?)}");
            statement.setInt(1, groupID);
            statement.setTimestamp(2, new Timestamp(interval.getStartMillis()));
            statement.setTimestamp(3, new Timestamp(interval.getEndMillis()));
            
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
            logger.error("sql hosLog", e);
            //throw e;
            return null;

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
    public List<HOSOccupantLog> getHOSOccupantLogs(Integer driverID, Interval interval) {


        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;

        ArrayList<HOSOccupantLog> recordList = new ArrayList<HOSOccupantLog>();
        
        try
        {
            conn = getConnection();
            statement = conn.prepareCall("{call hos_getHOSOccupantLogsForDriverVehicle(?, ?, ?)}");
            statement.setInt(1, driverID);
            statement.setTimestamp(2, new Timestamp(interval.getStartMillis()));
            statement.setTimestamp(3, new Timestamp(interval.getEndMillis()));
            
            resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                HOSOccupantLog occupantLog = new HOSOccupantLog();
                
                occupantLog.setDriverID(resultSet.getInt(1));
                occupantLog.setDriverName(resultSet.getString(2));
                occupantLog.setVehicleID(resultSet.getInt(3));
                occupantLog.setLogTime(resultSet.getTimestamp(4));
                occupantLog.setEndTime(resultSet.getTimestamp(5));
                occupantLog.setServiceID(resultSet.getString(6));
                occupantLog.setTrailerID(resultSet.getString(7));
                
                recordList.add(occupantLog);
            }
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            logger.error("sql hosLog", e);
            //throw e;
            return null;

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
            statement.setTimestamp(2, new Timestamp(interval.getStartMillis()));
            statement.setTimestamp(3, new Timestamp(interval.getEndMillis()));
            statement.setBoolean(4, driverStatusOnly);
            
            resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                HOSRecord hosRecord = new HOSRecord();
                
                hosRecord.setHosLogID(resultSet.getInt(1));
                hosRecord.setDriverID(resultSet.getInt(2));
                hosRecord.setVehicleID(resultSet.getInt(3));
                hosRecord.setLogTime(resultSet.getTimestamp(4));
                hosRecord.setTimeZone(TimeZone.getTimeZone(resultSet.getString(5)));
                hosRecord.setStatus(HOSStatus.valueOf(resultSet.getInt(6)));
                hosRecord.setDriverDotType(RuleSetType.valueOf(resultSet.getInt(7)));
                hosRecord.setVehicleIsDOT(resultSet.getBoolean(8));
                hosRecord.setVehicleOdometer(resultSet.getInt(9));
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
               
                recordList.add(hosRecord);
            }
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            logger.error("sql hosLog", e);
            return null;
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
    public List<HOSVehicleDayData> getHOSVehicleDataByDay(Integer driverID, Interval interval) {
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;

        ArrayList<HOSVehicleDayData> recordList = new ArrayList<HOSVehicleDayData>();
        
        try
        {
            conn = getConnection();
            statement = conn.prepareCall("{call hos_getHOSVehicleRecordsForDriver(?, ?, ?, ?)}");
            statement.setInt(1, driverID);
            statement.setTimestamp(2, new Timestamp(interval.getStartMillis()));
            statement.setTimestamp(3, new Timestamp(interval.getEndMillis()));
            statement.setBoolean(4, true); //driverStatusOnly
            
            resultSet = statement.executeQuery();

            long stopOdometer;
            long milesDriven;
            HOSVehicleDayData hosRecord = null;
            while (resultSet.next())
            {
                hosRecord = new HOSVehicleDayData();

                hosRecord.setVehicleID(resultSet.getInt(1));
                hosRecord.setVehicleName(resultSet.getString(2));
                hosRecord.setDay(resultSet.getTimestamp(3));
                hosRecord.setStartOdometer(resultSet.getLong(4));
                stopOdometer = resultSet.getLong(5);

                milesDriven = (stopOdometer > hosRecord.getStartOdometer()) ? 0 : (stopOdometer - hosRecord.getStartOdometer());
                
                hosRecord.setMilesDriven(milesDriven);
                
                recordList.add(hosRecord);
                
            }
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            logger.error("sql hosLog", e);
            //throw e;
            return null;

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
            statement.setInt(2, hosRecord.getVehicleID());
            statement.setTimestamp(3, new Timestamp(hosRecord.getLogTime().getTime()));
            statement.setString(4, hosRecord.getTimeZone().getID());
            statement.setInt(5, hosRecord.getStatus().getCode());
            statement.setInt(6, hosRecord.getDriverDotType().getCode());
            statement.setString(7, hosRecord.getTrailerID());
            statement.setString(8, hosRecord.getServiceID());
            statement.setString(9, hosRecord.getLocation());
            
            //hosRecord.setEditUserID(1);
            statement.setInt(10, hosRecord.getEditUserID());

            resultSet = statement.executeQuery();

            if (resultSet.next())
                id = resultSet.getInt(1);
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            logger.error("sql hosLog", e);
            //throw e;
            return null;

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
            logger.error("sql hosLog", e);
            return null;
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
                hosRecord.setLogTime(resultSet.getTimestamp(4));
                hosRecord.setTimeZone(TimeZone.getTimeZone(resultSet.getString(5)));
                hosRecord.setStatus(HOSStatus.valueOf(resultSet.getInt(6)));
                hosRecord.setDriverDotType(RuleSetType.valueOf(resultSet.getInt(7)));
                hosRecord.setVehicleIsDOT(resultSet.getBoolean(8));
                hosRecord.setVehicleOdometer(resultSet.getInt(9));
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
            }
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            logger.error("sql hosLog", e);
            return null;
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
            statement.setInt(3, hosRecord.getVehicleID());
            statement.setTimestamp(4, new Timestamp(hosRecord.getLogTime().getTime()));
            statement.setString(5, hosRecord.getTimeZone().getID());
            statement.setInt(6, hosRecord.getStatus().getCode());
            statement.setInt(7, hosRecord.getDriverDotType().getCode());
            statement.setString(8, hosRecord.getTrailerID());
            statement.setString(9, hosRecord.getServiceID());
            statement.setString(10, hosRecord.getLocation());

//            hosRecord.setEditUserID(1);
            statement.setInt(11, hosRecord.getEditUserID());

            
            statement.executeUpdate();

        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            logger.error("sql hosLog", e);
            return -1;
            //throw e;

        }   // end catch
        finally
        { // clean up and release the connection
            close(statement);
            close(conn);
        } // end finally
        return hosRecord.getHosLogID();
    }
}
