package com.inthinc.pro.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

public class DriveTimeJDBCDAO extends GenericJDBCDAO implements DriveTimeDAO {

    private static final long serialVersionUID = 1L;
    
    private static final String FETCH_DRIVE_TIME = "select a.driverID, a.driveTime, a.aggDate, a.vehicleID, v.name from agg a, vehicle v where v.vehicleID = a.vehicleID and a.driverID=? and a.aggDate between ? and ? order by a.aggDate";
    
    private static final String FETCH_GROUP_DRIVE_TIME1 = "select a.driverID, a.driveTime, a.aggDate, a.vehicleID, v.name from agg a, vehicle v " +
                                                         "where v.vehicleID = a.vehicleID and a.driverID in " +
                                                         "(";
    private static final String FETCH_GROUP_DRIVE_TIME2 = ") and a.aggDate between ? and ? order by a.aggDate, a.driverID";
    
    private static final String FETCH_DRIVER_IDS = "select distinct driverID from groupDriverFlat g where g.groupID in (select groupID from groupGroupFlat where parentID=?)";

    private static final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");

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
