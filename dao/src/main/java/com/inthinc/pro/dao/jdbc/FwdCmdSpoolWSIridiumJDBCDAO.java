package com.inthinc.pro.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.ForwardCommandSpool;

public class FwdCmdSpoolWSIridiumJDBCDAO  extends GenericJDBCDAO {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(FwdCmdSpoolWSIridiumJDBCDAO.class);

    
    public Integer add(ForwardCommandSpool forwardCommandSpool) {
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;

        Integer id = 0;
        try
        {
            conn = getConnection();
            statement = conn.prepareCall("{call ws_addIridiumForwardCommandSpool(?, ?, ?, ?)}");
            statement.setInt(1, forwardCommandSpool.getForwardCommandID());
            statement.setBytes(2, forwardCommandSpool.getData());
            statement.setInt(3, forwardCommandSpool.getCommand());
            statement.setString(4, forwardCommandSpool.getAddress());
            
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) 
                id = resultSet.getInt(1);

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
        return id;
    }


    public List<ForwardCommandSpool> getForAddress(String address) {
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;

        ArrayList<ForwardCommandSpool> recordList = new ArrayList<ForwardCommandSpool>();
        
        try
        {
            conn = getConnection();
            statement = conn.prepareCall("{call ws_fetchIridiumForwardCommandSpool(?)}");
            statement.setString(1, address);
            
            resultSet = statement.executeQuery();

            ForwardCommandSpool record = null;
            while (resultSet.next())
            {
                record = new ForwardCommandSpool();
                
                record.setForwardCommandSpoolID(resultSet.getInt(1));
                record.setForwardCommandID(resultSet.getInt(2));
                record.setCommand(resultSet.getInt(3));
                record.setData(resultSet.getBytes(4));
                record.setTimeSubmitted(resultSet.getTimestamp(6));

                recordList.add(record);
                
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

    public void update(Integer iridiumForwardCommandSpoolID, Boolean processedSuccessfully) {
        Connection conn = null;
        CallableStatement statement = null;

        try
        {
            conn = getConnection();
            statement = conn.prepareCall("{call ws_updateIridiumForwardCommandSpool(?, ?)}");
            statement.setInt(1, iridiumForwardCommandSpoolID);
            statement.setBoolean(2, processedSuccessfully);
            
            statement.executeUpdate();
            
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            logger.error("sql hosLog", e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(statement);
            close(conn);
        } // end finally
    }

}
