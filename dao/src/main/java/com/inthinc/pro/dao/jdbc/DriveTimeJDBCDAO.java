package com.inthinc.pro.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.model.event.VehicleEventData;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.DriveTimeDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.aggregation.DriveTimeRecord;

import com.inthinc.pro.model.Vehicle;

public class DriveTimeJDBCDAO extends GenericJDBCDAO implements DriveTimeDAO {

    private static final long serialVersionUID = 1L;
    
    private static final String FETCH_DRIVE_TIME = "select a.driverID, a.driveTime, a.aggDate, a.vehicleID, v.name from agg a, vehicle v where v.vehicleID = a.vehicleID and a.driverID=? and a.aggDate between ? and ? order by a.aggDate";
    
    private static final String FETCH_GROUP_DRIVE_TIME1 = "select a.driverID, a.driveTime, a.aggDate, a.vehicleID, v.name from agg a, vehicle v " +
                                                         "where v.vehicleID = a.vehicleID and a.driverID in " +
                                                         "(";
    private static final String FETCH_GROUP_DRIVE_TIME2 = ") and a.aggDate between ? and ? order by a.aggDate, a.driverID";
    
    private static final String FETCH_DRIVER_IDS = "select distinct driverID from groupDriverFlat g where g.groupID in (select groupID from groupGroupFlat where parentID=?)";

    private static final String REPORT_HOURS = "select sum(a.driveTime) drvt from agg a where a.vehicleID = ? order by a.aggDate desc";

    private static final String REPORT_ODOMETER6 = "select sum(a.odometer6) drvo from agg a where a.vehicleID = ?";

    private static final String EVENT_PREV_DATE = "SELECT max(time) last_date FROM noteXX a WHERE a.vehicleID = ? and TYPE = ? AND attrs LIKE '%NCODE%' AND time < ?";

    private static final String REPORT_HOURS_AT_DATE = "select sum(a.driveTime) tdiff from agg a where a.vehicleID = ? and a.aggDate <= ?";

    private static final String REPORT_ODOMETER_AT_DATE = "select sum(a.odometer6) odiff from agg a where a.vehicleID = ? and a.aggDate <= ?";

    private static final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");

    public Long getEngineHoursAtDate(Vehicle vehicle, Date evDate) {
        java.sql.Date sqlEvDate = new java.sql.Date(evDate.getTime());

        Long hoursSince = 0L;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(REPORT_HOURS_AT_DATE);
            statement.setInt(1, vehicle.getVehicleID());
            statement.setDate(2, sqlEvDate);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                hoursSince = resultSet.getLong("tdiff");
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

        return hoursSince;
    }

    public Long getDriveOdometerAtDate(Vehicle vehicle, Date evDate) {
        java.sql.Date sqlEvDate = new java.sql.Date(evDate.getTime());

        Long driveSince = 0L;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(REPORT_ODOMETER_AT_DATE);
            statement.setInt(1, vehicle.getVehicleID());
            statement.setDate(2, sqlEvDate);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                driveSince = resultSet.getLong("odiff");
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

        return driveSince;
    }

    public Date getPrevEventDate(Vehicle vehicle, Integer nType, Integer eventCode, Date evDate, Integer deviceId) {
        java.sql.Date sqlEvDate = new java.sql.Date(evDate.getTime());

        Date date = new Date(0);
        java.sql.Date prevEventDate = new java.sql.Date(date.getTime());
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(EVENT_PREV_DATE.replace("XX", (deviceId%32 < 10 ? "0"+deviceId%32 : ""+deviceId%32)).replace("NCODE",eventCode.toString()));
            statement.setInt(1, vehicle.getVehicleID());
            statement.setInt(2, nType);
            statement.setDate(3, sqlEvDate);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                prevEventDate = resultSet.getDate("last_date");
            }

            if (prevEventDate==null) prevEventDate= new java.sql.Date(date.getTime());
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

        return prevEventDate;
    }

    @Override
    public Date getPrevEventDates(VehicleEventData vehicleEventData) {
        Map<Integer, java.sql.Date> sqlEventDates = new HashMap<Integer, java.sql.Date>(vehicleEventData.getDates().size());
        for (Map.Entry<Integer, Date> )
    }

    public Long getDriveTimeAtDate(Vehicle vehicle, Integer nType, Integer eventCode, Date evDate) {
        //take previous last date for Event of same type
        //if not exists will be used a very old date 1970
        java.sql.Date sqlEvDate = new java.sql.Date(evDate.getTime());

        Long hourSince = 0L;
        Date date = new Date(0);
        java.sql.Date prevEventDate = new java.sql.Date(date.getTime());
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(EVENT_PREV_DATE.replace("NCODE",eventCode.toString()));
            statement.setInt(1, vehicle.getVehicleID());
            statement.setInt(2, nType);
            statement.setDate(3, sqlEvDate);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                prevEventDate = resultSet.getDate("last_date");
            }
            statement = (PreparedStatement) conn.prepareStatement(REPORT_HOURS_AT_DATE);
            statement.setInt(1, vehicle.getVehicleID());
            statement.setDate(2, prevEventDate);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                hourSince = resultSet.getLong("tdiff");
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

        return hourSince;
    }

    public Long getDriveTimeSum(Vehicle vehicle) {

        Long driveTime = 0L;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(REPORT_HOURS);
            statement.setInt(1, vehicle.getVehicleID());
            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                driveTime = resultSet.getLong("drvt");
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

        return driveTime;
    }

    public Long getDriveOdometerSum(Vehicle vehicle) {

        Long driveOdometer = 0L;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(REPORT_ODOMETER6);
            statement.setInt(1, vehicle.getVehicleID());
            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                driveOdometer = resultSet.getLong("drvo");
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

        return driveOdometer;
    }

    @Override
    public List<DriveTimeRecord> getDriveTimeRecordList(Driver driver, Interval queryInterval) {
        
        DateTimeZone driverTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());

        List<DriveTimeRecord> driverTimeRecordList = new ArrayList<DriveTimeRecord>();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(FETCH_DRIVE_TIME);
            statement.setInt(1, driver.getDriverID());
            statement.setDate(2, java.sql.Date.valueOf(dateFormatter.print(queryInterval.getStart())));
            statement.setDate(3, java.sql.Date.valueOf(dateFormatter.print(queryInterval.getEnd())));
System.out.println("statement:" + statement.toString());            
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                DriveTimeRecord driverTimeRecord = new DriveTimeRecord();
                driverTimeRecord.setDriverID(resultSet.getInt(1));
                driverTimeRecord.setDriveTimeSeconds(resultSet.getLong(2));
                driverTimeRecord.setDateTime(new DateMidnight(resultSet.getDate(3).getTime(), driverTimeZone).toDateTime());
                driverTimeRecord.setVehicleID(resultSet.getInt(4));
                driverTimeRecord.setVehicleName(resultSet.getString(5));
                driverTimeRecordList.add(driverTimeRecord);
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
        
        return driverTimeRecordList;
    }

    
    @Override
    public List<DriveTimeRecord> getDriveTimeRecordListForGroup(Integer groupID, Interval queryInterval) {
        

        List<DriveTimeRecord> driverTimeRecordList = new ArrayList<DriveTimeRecord>();
        String driverIDsList = getDriverIDsForGroup(groupID);
        if (driverIDsList.isEmpty())
            return driverTimeRecordList;
        
        // for some reason, doing it this way executed much faster than embedding the select query on the driverIDs
        String queryString = FETCH_GROUP_DRIVE_TIME1 + driverIDsList + FETCH_GROUP_DRIVE_TIME2; 
        
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(queryString);
            statement.setDate(1, java.sql.Date.valueOf(dateFormatter.print(queryInterval.getStart())));
            statement.setDate(2, java.sql.Date.valueOf(dateFormatter.print(queryInterval.getEnd())));
//System.out.println("statement:" + statement.toString());            
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                DriveTimeRecord driverTimeRecord = new DriveTimeRecord();
                driverTimeRecord.setDriverID(resultSet.getInt(1));
                driverTimeRecord.setDriveTimeSeconds(resultSet.getLong(2));
//                driverTimeRecord.setDateTime(new DateTime(resultSet.getDate(3).getTime(), DateTimeZone.UTC));
                driverTimeRecord.setDateTime(new DateTime(resultSet.getDate(3).getTime()));
                driverTimeRecord.setVehicleID(resultSet.getInt(4));
                driverTimeRecord.setVehicleName(resultSet.getString(5));
                driverTimeRecordList.add(driverTimeRecord);
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
        
        return driverTimeRecordList;
    }

    private String getDriverIDsForGroup(Integer groupID) {
        

        StringBuffer driverIDs = new StringBuffer();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(FETCH_DRIVER_IDS);
            statement.setInt(1, groupID);
//System.out.println("statement:" + statement.toString());            
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                if (driverIDs.length() != 0)
                    driverIDs.append(",");
                driverIDs.append(resultSet.getInt(1));
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
        
        return driverIDs.toString();
    }

}
