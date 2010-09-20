package com.inthinc.pro.dao.jdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.Serializable;




import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public abstract class GenericJDBCDAO implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private static final Logger logger = Logger.getLogger(GenericJDBCDAO.class);
    private static DriverManagerDataSource dataSource;
    

    @SuppressWarnings("unchecked")
    public GenericJDBCDAO()
    {
    }


    
    public static DataSource getRealDataSource() {
        
        return GenericJDBCDAO.dataSource;
    }    

    public void setDatasource(DriverManagerDataSource dataSource)
    {
        GenericJDBCDAO.dataSource = dataSource;
    }




    public static Connection getConnection() throws NullPointerException, SQLException
    {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.error(e);
            return null;
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
