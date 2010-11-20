package com.inthinc.pro.dao.jdbc;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.inthinc.pro.ProDAOException;

public abstract class GenericJDBCDAO implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private static final Logger logger = Logger.getLogger(GenericJDBCDAO.class);
    private DataSource dataSource;
    


    @SuppressWarnings("unchecked")
    public GenericJDBCDAO()
    {
    }


    
    public DataSource getDataSource() {
        
        return dataSource;
    }    

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() throws NullPointerException, SQLException
    {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new ProDAOException(e);
        }
    }

    /*---------------------------------------------------------------------------
     * Internal operations
     *---------------------------------------------------------------------------
     */

    /**
     * Perform a "safe" close, by checking for null and catching SQL exceptions.
     * Call this method from finally clauses to insure all code is executed.
     *
     * @param s SQL statement to be closed.
     */
    public static void close(final Statement s)
    {
        if (s!=null)
        {
            try
            {
                s.close();

            }
            catch (final Throwable e)
            {
                logger.error("error closing statement", e);
            }
        }
    }

    public static void close(final ResultSet r)
    {
        if (r!=null)
        {
          try
          {
            r.close();

          }
          catch (final Throwable e)
          {
              logger.error("error closing result set", e);
          }
        }
    }

    public static void close(final Connection c) {
        if (c != null) {
            try {
                c.close();
            } catch (final Throwable e) {
                logger.error("error closing connection", e);
            }
        }
    }
    
}
