package com.inthinc.pro.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.Interval;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.StateMileageDAO;

import com.inthinc.pro.model.StateMileage;
import com.inthinc.pro.model.hos.HOSGroupMileage;

public class StateMileageJDBCDAO  extends GenericJDBCDAO  implements StateMileageDAO{

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(StateMileageJDBCDAO.class);

    public List<StateMileage> getStateMileageByGroupAndMonth(Integer groupID, Interval interval, Boolean dotOnly)
    {
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;

        ArrayList<StateMileage> recordList = new ArrayList<StateMileage>();
        
        try
        {
            conn = getConnection();
            statement = conn.prepareCall("{call ifta_getStateMileageByGroupAndMonth(?, ?, ?, ?)}");
            statement.setInt(1, groupID);
            statement.setLong(2, interval.getStartMillis());
            statement.setLong(3, interval.getEndMillis());
            statement.setBoolean(4, dotOnly);
            
            resultSet = statement.executeQuery();

            StateMileage record = null;
            while (resultSet.next())
            {
                record = new StateMileage();

                record.setGroupID(resultSet.getInt(1));
                record.setStateName(resultSet.getString(2));
                record.setMonth(resultSet.getString(3));
                record.setMiles(resultSet.getLong(4));
                record.setTruckGallons(resultSet.getFloat(5));
                record.setTrailerGallons(resultSet.getFloat(6));
                
                recordList.add(record);
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
    
    public List<StateMileage> getStateMileageByGroup(Integer groupID, Interval interval, Boolean dotOnly)
    {
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;

        ArrayList<StateMileage> recordList = new ArrayList<StateMileage>();
        
        try
        {
            conn = getConnection();
            statement = conn.prepareCall("{call ifta_getStateMileageByGroup(?, ?, ?, ?)}");
            statement.setInt(1, groupID);
            statement.setLong(2, interval.getStartMillis());
            statement.setLong(3, interval.getEndMillis());
            statement.setBoolean(4, dotOnly);
            
            resultSet = statement.executeQuery();

            StateMileage record = null;
            while (resultSet.next())
            {
                record = new StateMileage();

                record.setGroupID(resultSet.getInt(1));
                record.setStateName(resultSet.getString(2));
                record.setMiles(resultSet.getLong(3));
                record.setTruckGallons(resultSet.getFloat(4));
                record.setTrailerGallons(resultSet.getFloat(5));
                recordList.add(record);
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
    
    public List<StateMileage> getStateMileageByVehicle(Integer groupID, Interval interval, Boolean dotOnly)
    {
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;

        ArrayList<StateMileage> recordList = new ArrayList<StateMileage>();
        
        try
        {
            conn = getConnection();
            statement = conn.prepareCall("{call ifta_getStateMileageByVehicle(?, ?, ?, ?)}");
            statement.setInt(1, groupID);
            statement.setLong(2, interval.getStartMillis());
            statement.setLong(3, interval.getEndMillis());
            statement.setBoolean(4, dotOnly);
            
            resultSet = statement.executeQuery();

            StateMileage record = null;
            while (resultSet.next())
            {
                record = new StateMileage();

                record.setGroupID(resultSet.getInt(1));
                record.setVehicleName(resultSet.getString(2));
                record.setStateName(resultSet.getString(3));
                record.setMiles(resultSet.getLong(4));
                record.setTruckGallons(resultSet.getFloat(5));
                record.setTrailerGallons(resultSet.getFloat(6));
                recordList.add(record);
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
    
    public List<StateMileage> getStateMileageByVehicleRoad(Integer groupID, Interval interval, Boolean dotOnly)
    {
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;

        ArrayList<StateMileage> recordList = new ArrayList<StateMileage>();
        
        try
        {
            conn = getConnection();
            statement = conn.prepareCall("{call ifta_getStateMileageByVehicleRoad(?, ?, ?, ?)}");
            statement.setInt(1, groupID);
            statement.setLong(2, interval.getStartMillis());
            statement.setLong(3, interval.getEndMillis());
            statement.setBoolean(4, dotOnly);
            
            resultSet = statement.executeQuery();

            StateMileage record = null;
            while (resultSet.next())
            {
                record = new StateMileage();

                record.setGroupID(resultSet.getInt(1));
                record.setVehicleName(resultSet.getString(2));
                record.setStateName(resultSet.getString(3));
                record.setOnRoadFlag(resultSet.getBoolean(4));
                record.setMiles(resultSet.getLong(5));
                record.setTruckGallons(resultSet.getFloat(6));
                record.setTrailerGallons(resultSet.getFloat(7));
                recordList.add(record);
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
    
    public List<StateMileage> getFuelStateMileageByVehicle(Integer groupID, Interval interval, Boolean dotOnly)
    {
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;

        ArrayList<StateMileage> recordList = new ArrayList<StateMileage>();
        
        try
        {
            conn = getConnection();
            statement = conn.prepareCall("{call ifta_getFuelStateMileageByVehicle(?, ?, ?, ?)}");
            statement.setInt(1, groupID);
            statement.setLong(2, interval.getStartMillis());
            statement.setLong(3, interval.getEndMillis());
            statement.setBoolean(4, dotOnly);
            
            resultSet = statement.executeQuery();

            StateMileage record = null;
            while (resultSet.next())
            {
                record = new StateMileage();

                record.setGroupID(resultSet.getInt(1));
                record.setVehicleName(resultSet.getString(2));
                record.setStateName(resultSet.getString(3));
                record.setMiles(resultSet.getLong(4));
                record.setTruckGallons(resultSet.getFloat(5));
                record.setTrailerGallons(resultSet.getFloat(6));
                recordList.add(record);
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

    public List<StateMileage> getMileageByVehicle(Integer groupID, Interval interval, Boolean dotOnly)
    {
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;

        ArrayList<StateMileage> recordList = new ArrayList<StateMileage>();
        
        try
        {
            conn = getConnection();
            statement = conn.prepareCall("{call ifta_getMileageByVehicle(?, ?, ?, ?)}");
            statement.setInt(1, groupID);
            statement.setLong(2, interval.getStartMillis());
            statement.setLong(3, interval.getEndMillis());
            statement.setBoolean(4, dotOnly);
            
            resultSet = statement.executeQuery();

            StateMileage record = null;
            while (resultSet.next())
            {
                record = new StateMileage();

                record.setGroupID(resultSet.getInt(1));
                record.setVehicleName(resultSet.getString(2));
                record.setMiles(resultSet.getLong(3));
                record.setTruckGallons(resultSet.getFloat(4));
                record.setTrailerGallons(resultSet.getFloat(5));
                recordList.add(record);
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

}
