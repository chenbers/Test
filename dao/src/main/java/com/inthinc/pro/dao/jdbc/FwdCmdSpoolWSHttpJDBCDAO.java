package com.inthinc.pro.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.model.ForwardCommandSpool;
import com.inthinc.pro.model.ForwardCommandStatus;

public class FwdCmdSpoolWSHttpJDBCDAO  extends GenericJDBCDAO {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(FwdCmdSpoolWSHttpJDBCDAO.class);
    private static final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SZ");

    
    public Integer add(ForwardCommandSpool forwardCommandSpool) {
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;


        Integer id = 0;
        try
        {
            conn = getConnection();
            statement = conn.prepareCall("{call ws_addHttpForwardCommandSpool(?, ?, ?, ?)}");

            String strData = forwardCommandSpool.getStrData();
            if (strData == null) {
                if (forwardCommandSpool.getData() == null) {
                    strData = "";
                }
            }
            
            statement.setInt(1, forwardCommandSpool.getIntData() == null ? Integer.valueOf(-1) : forwardCommandSpool.getIntData());
            statement.setString(2, strData);
            statement.setInt(3, forwardCommandSpool.getCommand());
            statement.setString(4, forwardCommandSpool.getAddress());
            
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) 
                id = resultSet.getInt(1);

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
            statement = conn.prepareCall("{call ws_fetchHttpForwardCommandSpool(?)}");
            statement.setString(1, address);
            
            resultSet = statement.executeQuery();

            ForwardCommandSpool record = null;
            while (resultSet.next())
            {
                record = new ForwardCommandSpool();
                
                record.setFwdID(resultSet.getInt(1));
                record.setDeviceID(resultSet.getInt(2));
                record.setCommand(resultSet.getInt(3));
                record.setIntData(resultSet.getInt(4));
                record.setStrData(resultSet.getString(5));

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

    public void update(Integer fwdID, Boolean processedSuccessfully) {
        Connection conn = null;
        CallableStatement statement = null;

        try
        {
            conn = getConnection();
            statement = conn.prepareCall("{call ws_updateHttpForwardCommandSpool(?, ?)}");
            statement.setInt(1, fwdID);
            statement.setBoolean(2, processedSuccessfully);
            
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
    
    private static final String FETCH_FOR_DEVICE_CMD = "select fwdID, fwdCmd, created, modified, status  from fwd where deviceID = ? and fwdCmd in (";
    public List<ForwardCommandSpool> getForDevice(Integer deviceID, List<Integer> cmdIDList) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String queryString = FETCH_FOR_DEVICE_CMD + getCommaSepList(cmdIDList) + ")"; 

        List<ForwardCommandSpool> recordList = new ArrayList<ForwardCommandSpool>();
        
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(queryString);
            statement.setInt(1, deviceID);
            resultSet = statement.executeQuery();

            ForwardCommandSpool record = null;
            while (resultSet.next())
            {
                record = new ForwardCommandSpool();
                
                record.setFwdID(resultSet.getInt(1));
                record.setCommand(resultSet.getInt(2));
                
                String createdStr = resultSet.getString(3);
                String modStr = resultSet.getString(4);
                record.setCreated(new DateTime(dateFormatter.parseMillis(createdStr+ "+0000")).toDate());
                record.setModified(new DateTime(dateFormatter.parseMillis(modStr + "+0000")).toDate());

                record.setStatus(ForwardCommandStatus.valueOf(resultSet.getInt(5)));
                recordList.add(record);
                
            }
        }   // end try
        catch (SQLException e)
        { 
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
    private String getCommaSepList(List<Integer> cmdIDList) {
        
        StringBuffer buffer = new StringBuffer();
        for (Integer id : cmdIDList) {
            if (buffer.length() != 0)
                buffer.append(",");
            buffer.append(id);
        }
        return buffer.toString();
    }



}
