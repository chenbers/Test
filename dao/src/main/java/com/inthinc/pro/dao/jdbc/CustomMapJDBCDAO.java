package com.inthinc.pro.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.CustomMapDAO;
import com.inthinc.pro.model.CustomMap;

public class CustomMapJDBCDAO extends GenericJDBCDAO implements CustomMapDAO {
    
    private static final long serialVersionUID = 1L;
    
    //alter table customMap ADD COLUMN layer VARCHAR(30) AFTER pngFormat;
    
    private static final String CUSTOM_MAP_FIELDS = "customMapID, acctID, name, url, minZoom, maxZoom, opacity, pngFormat, layer";
    
    private static final String INSERT_CUSTOM_MAP = "INSERT INTO customMap(acctID, name, url, minZoom, maxZoom, opacity, pngFormat, layer) values (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_CUSTOM_MAP = "UPDATE customMap set name = ?, url = ?, minZoom = ?, maxZoom = ?, opacity = ?, pngFormat = ?, layer = ? where acctID = ? and customMapID = ?";
    private static final String FIND_CUSTOM_MAP = "SELECT " + CUSTOM_MAP_FIELDS + " FROM customMap where customMapID = ?";
    private static final String DELETE_CUSTOM_MAP = "DELETE FROM customMap where customMapID = ?";
    private static final String FETCH_CUSTOM_MAPS_FOR_ACCOUNT = "SELECT " + CUSTOM_MAP_FIELDS + " FROM customMap where acctID = ?";

    @Override
    public Integer create(Integer acctID, CustomMap customMap) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(INSERT_CUSTOM_MAP);
            statement.setInt(1, customMap.getAcctID());
            statement.setString(2, customMap.getName());
            statement.setString(3, customMap.getUrl());
            statement.setInt(4, customMap.getMinZoom());
            statement.setInt(5, customMap.getMaxZoom());
            statement.setFloat(6, customMap.getOpacity().floatValue());
            statement.setInt(7, customMap.getPngFormat() == null || !customMap.getPngFormat() ? 0 : 1);
            statement.setString(8, customMap.getLayer());
            statement.executeUpdate();
            
            resultSet = statement.executeQuery("SELECT LAST_INSERT_ID()");
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return null;
        } 
        catch (SQLException e)
        { 
            throw new ProDAOException(statement.toString(), e);
        } 
        finally {
            close(resultSet);
            close(statement);
            close(conn);
        }    
        
    }

    @Override
    public Integer deleteByID(Integer customMapID) {
        Connection conn = null;
        PreparedStatement statement = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(DELETE_CUSTOM_MAP);
            statement.setInt(1, customMapID);
            return statement.executeUpdate();
        } 
        catch (SQLException e)
        { 
            throw new ProDAOException(statement.toString(), e);
        } 
        finally {
            close(statement);
            close(conn);
        }    
    }

    @Override
    public CustomMap findByID(Integer customMapID) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(FIND_CUSTOM_MAP);
            statement.setInt(1, customMapID);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return extractCustomMapFromResultSet(resultSet);
            }
                

        }   // end try
        catch (SQLException e) { 
            throw new ProDAOException(statement.toString(), e);
        }  
        finally { 
            close(resultSet);
            close(statement);
            close(conn);
        }    
        
        return null;
    }

    @Override
    public Integer update(CustomMap customMap) {
        Connection conn = null;
        PreparedStatement statement = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(UPDATE_CUSTOM_MAP);
            statement.setString(1, customMap.getName());
            statement.setString(2, customMap.getUrl());
            statement.setInt(3, customMap.getMinZoom());
            statement.setInt(4, customMap.getMaxZoom());
            statement.setFloat(5, customMap.getOpacity().floatValue());
            statement.setInt(6, customMap.getPngFormat() == null || !customMap.getPngFormat() ? 0 : 1);
            statement.setString(7, customMap.getLayer());
            statement.setInt(8, customMap.getAcctID());
            statement.setInt(9, customMap.getCustomMapID());
            return statement.executeUpdate();
        }
        catch (SQLException e) { 
            throw new ProDAOException(statement.toString(), e);
        } 
        finally {
            close(statement);
            close(conn);
        }    
    }

    @Override
    public List<CustomMap> getCustomMapsByAcctID(Integer acctID) {
        
        List<CustomMap> customMapList = new ArrayList<CustomMap>();
        
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(FETCH_CUSTOM_MAPS_FOR_ACCOUNT);
            statement.setInt(1, acctID);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                customMapList.add(extractCustomMapFromResultSet(resultSet));
            }
                

        }   // end try
        catch (SQLException e) { 
            throw new ProDAOException(statement.toString(), e);
        }  
        finally { 
            close(resultSet);
            close(statement);
            close(conn);
        }    
        
        return customMapList;
    }

    private CustomMap extractCustomMapFromResultSet(ResultSet resultSet) throws SQLException {
        CustomMap customMap = new CustomMap();
        customMap.setCustomMapID(resultSet.getInt(1));
        customMap.setAcctID(resultSet.getInt(2));
        customMap.setName(resultSet.getString(3));
        customMap.setUrl(resultSet.getString(4));
        customMap.setMinZoom(resultSet.getInt(5));
        customMap.setMaxZoom(resultSet.getInt(6));
        customMap.setOpacity(new Double(resultSet.getFloat(7)));
        customMap.setPngFormat(resultSet.getInt(8) == 1 ? Boolean.TRUE : Boolean.FALSE);
        customMap.setLayer(resultSet.getString(9));
        return customMap;
    }
}
