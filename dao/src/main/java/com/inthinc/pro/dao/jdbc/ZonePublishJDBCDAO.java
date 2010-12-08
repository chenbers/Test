package com.inthinc.pro.dao.jdbc;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.ZonePublishDAO;
import com.inthinc.pro.model.zone.ZonePublish;
import com.inthinc.pro.model.zone.option.type.ZoneVehicleType;
import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.PreparedStatement;

public class ZonePublishJDBCDAO extends GenericJDBCDAO implements ZonePublishDAO {


    private static final long serialVersionUID = 1L;
    
    private static final String FETCH_ZONE_PUBLISH_ID = "SELECT zonePublishID FROM zonePublish WHERE acctID = ? and zoneType = ?";
    private static final String INSERT_ZONE_PUBLISH = "INSERT INTO zonePublish(acctID, zoneType, zoneData) values (?, ?, ?)";
    private static final String UPDATE_ZONE_PUBLISH = "UPDATE zonePublish set zoneData = ? where  acctID = ? and zoneType = ?";
    private static final String FETCH_ZONE_PUBLISH = "SELECT zoneData FROM zonePublish WHERE acctID = ? and zoneType = ?";
    private static final String FETCH_ZONE_PUBLISH_BY_ID = "SELECT zonePublishID, acctID, zoneType, zoneData FROM zonePublish WHERE zonePublishID = ?";
    
    @Override
    public void publishZone(ZonePublish zonePublish) {
        if (publishZoneID(zonePublish) == null)
            publishZoneInsert(zonePublish);
        else publishZoneUpdate(zonePublish);
        
    }
        
    @Override
    public ZonePublish getPublishedZone(Integer acctID, ZoneVehicleType zoneVehicleType) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(FETCH_ZONE_PUBLISH);
            statement.setInt(1, acctID);
            statement.setInt(2, zoneVehicleType.getCode());
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                ZonePublish zonePublish = new ZonePublish();
                zonePublish.setAcctID(acctID);
                zonePublish.setZoneVehicleType(zoneVehicleType);
                zonePublish.setPublishZoneData(resultSet.getBytes(1));
                return zonePublish;
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
    public ZonePublish getPublishedZone(String mcmId) {
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn = getConnection();
            statement = (CallableStatement) conn.prepareCall("{call zonepublish_fetchForMcmId(?)}");
            statement.setString(1, mcmId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                ZonePublish zonePublish = new ZonePublish();
                zonePublish.setAcctID(resultSet.getInt(1));
                zonePublish.setZoneVehicleType((ZoneVehicleType)ZoneVehicleType.valueOf(resultSet.getInt(2)));
                zonePublish.setPublishZoneData(resultSet.getBytes(3));
                zonePublish.setZonePublishDate(new Date(resultSet.getLong(4)));
                
                return zonePublish;
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
    public Integer create(Integer id, ZonePublish zonePublish) {
        publishZoneInsert(zonePublish);
        
        return publishZoneID(zonePublish);
    }


    @Override
    public Integer deleteByID(Integer id) {
        // NOT SUPPORTED
        return null;
    }

    @Override
    public ZonePublish findByID(Integer id) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(FETCH_ZONE_PUBLISH_BY_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                ZonePublish zonePublish = new ZonePublish();
                zonePublish.setZonePublishID(resultSet.getInt(1));
                zonePublish.setAcctID(resultSet.getInt(2));
                zonePublish.setZoneVehicleType((ZoneVehicleType)ZoneVehicleType.valueOf(resultSet.getInt(3)));
                zonePublish.setPublishZoneData(resultSet.getBytes(4));
                return zonePublish;
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
    public Integer update(ZonePublish entity) {
        return publishZoneUpdate(entity);
    }
        
    private Integer publishZoneUpdate(ZonePublish zonePublish) {
        Connection conn = null;
        PreparedStatement statement = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(UPDATE_ZONE_PUBLISH);
            statement.setBlob(1, new ByteArrayInputStream(zonePublish.getPublishZoneData()));
            statement.setInt(2, zonePublish.getAcctID());
            statement.setInt(3, zonePublish.getZoneVehicleType().getCode());
            return statement.executeUpdate();
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
        
    }


    private Integer publishZoneID(ZonePublish zonePublish) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(FETCH_ZONE_PUBLISH_ID);
            statement.setInt(1, zonePublish.getAcctID());
            statement.setInt(2, zonePublish.getZoneVehicleType().getCode());
            resultSet = statement.executeQuery();

            if (resultSet.next())
                return resultSet.getInt(1);

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


    private void publishZoneInsert(ZonePublish zonePublish) {
        
        Connection conn = null;
        PreparedStatement statement = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(INSERT_ZONE_PUBLISH);
            statement.setInt(1, zonePublish.getAcctID());
            statement.setInt(2, zonePublish.getZoneVehicleType().getCode());
            statement.setBlob(3, new ByteArrayInputStream(zonePublish.getPublishZoneData()));
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
        
    }
}
