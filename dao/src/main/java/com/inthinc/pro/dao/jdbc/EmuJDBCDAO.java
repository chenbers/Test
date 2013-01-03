package com.inthinc.pro.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateMidnight;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.EmuDAO;
import com.inthinc.pro.model.aggregation.DriveTimeRecord;

public class EmuJDBCDAO extends GenericJDBCDAO implements EmuDAO {
    
    private static final long serialVersionUID = 1L;
    
    private static final String FETCH_EMU_FILENAME = "select filename from download where md5sum=?";


    @Override
    public String lookupEMUFilename(String emuMd5) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String filename = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(FETCH_EMU_FILENAME);
            statement.setString(1, emuMd5);
System.out.println("statement:" + statement.toString());            
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                filename = resultSet.getString(1);
            }
                

        }   // end try
        catch (SQLException e)
        { // handle database in the usual manner
            throw new ProDAOException(statement.toString(), e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally   
        
        return filename;
        
    }

}
