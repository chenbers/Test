package com.inthinc.pro.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.SiloDAO;
import com.inthinc.pro.model.silo.SiloDef;

public class SiloJDBCDAO extends GenericJDBCDAO implements SiloDAO {

    private static final String FETCH_SILO_DEFS= "SELECT siloID, serverURL, host, port, proxyHost, proxyPort FROM centDB.silomap";
    private static final String FETCH_SILO_DEF_BY_ID = "SELECT siloID, serverURL, host, port, proxyHost, proxyPort FROM centDB.silomap WHERE siloID = ?";

    @Override
    public List<SiloDef> getSiloDefs() {
        List<SiloDef> siloDefList = new ArrayList<SiloDef>();
        
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn = getConnection();
            statement = conn.createStatement();
            resultSet = statement.executeQuery(FETCH_SILO_DEFS);

            while (resultSet.next()) {
                SiloDef siloDef = new SiloDef();
                siloDef.setSiloID(resultSet.getInt(1));
                siloDef.setUrl(resultSet.getString(2));
                siloDef.setHost(resultSet.getString(3));
                siloDef.setPort(resultSet.getInt(4));
                siloDef.setProxyHost(resultSet.getString(5));
                siloDef.setProxyPort(resultSet.getInt(6));
                siloDefList.add(siloDef);
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
        
        return siloDefList;
    }

    @Override
    public SiloDef findByID(Integer id) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(FETCH_SILO_DEF_BY_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                SiloDef siloDef = new SiloDef();
                siloDef.setSiloID(resultSet.getInt(1));
                siloDef.setUrl(resultSet.getString(2));
                siloDef.setHost(resultSet.getString(3));
                siloDef.setPort(resultSet.getInt(4));
                siloDef.setProxyHost(resultSet.getString(5));
                siloDef.setProxyPort(resultSet.getInt(6));
                return siloDef;
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
    public Integer create(Integer id, SiloDef entity) {
        // NOT SUPPORTED
        return null;
    }

    @Override
    public Integer deleteByID(Integer id) {
        // NOT SUPPORTED
        return null;
    }

    @Override
    public Integer update(SiloDef entity) {
        // NOT SUPPORTED
        return null;
    }
}
