package com.inthinc.pro.table.dao.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class BaseDAO {
	
	
	private DataSource dataSource;
	private static int m_nConnectionsOpen = 0;
	protected static final Logger logger = LogManager.getLogger(BaseDAO.class);

	public BaseDAO()
	{
	}

	
	public static void initDataSource()
	{

	}
	/**
	 * Sets this class's data source object. This should be called with a non-<code>null</code>
	 * value before {@link #getConnection()} is called.
	 * 
	 * @param value
	 *            data source object or <code>null</code>.
	 */
	public void setDataSource(DataSource value)
	{
		dataSource = value;

	}

	/**
	 * Gets the last set data source object or <code>null</code> if never set.
	 * 
	 * @return data source object or <code>null</code>.
	 * @see #setDataSource(DataSource)
	 */
	public DataSource getDataSource()
	{
		return dataSource;
	}

	/**
	 * Attempts to establish a connection with the data source that the IwiDao
	 * class was last associated with.
	 * 
	 * @return a connection to the data source.
	 * @throws NullPointerException
	 *             if {@link IwiDao#setDataSource(DataSource)} has not
	 *             previously been called with a non-<code>null</code> data
	 *             source object.
	 * @throws SQLException
	 *             if a database access error occurs.
	 * @see IwiDao#setDataSource(DataSource)
	 */
	public Connection getConnection() throws NullPointerException, SQLException
	{
		final Connection connection = dataSource.getConnection();
		if (connection != null) {
			m_nConnectionsOpen++;
		}
		return connection;
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
	public void close(final Statement s)
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

	/**
	 * Perform a "safe" close, by checking for null and catching SQL exceptions.
	 * Call this method from finally clauses to insure all code is executed.
	 *
	 * @param r SQL result set to be closed.
	 */
	public void close(final ResultSet r)
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

	/**
	 * Perform a "safe" close, by checking for null and catching SQL exceptions.
	 * Call this method from finally clauses to insure all code is executed.
	 * 
	 * @param c
	 *            SQL connection object or <code>null</code>.
	 */
	public void close(final Connection c) {
		if (c != null) {
			try {
				c.close();
				m_nConnectionsOpen--;
				// logger.info("connections open: " + m_nConnectionsOpen);
			} catch (final Throwable e) {
				logger.error("error closing connection", e);
			}
		}
	}
/*
	public ResultList query2(final int sessionId, final String customerId, final String query, final boolean bCrossTab) throws SQLException
	{
		Connection conn = null;
		CallableStatement statement = null;
		ResultSet resultSet = null;
		ResultList resultList = null;
		System.out.println("QUERY2: " + query);

		try		{
			conn = getConnection();
			statement = conn.prepareCall("{call query(?, ?, ?)}");
			statement.setInt(1, sessionId);
			statement.setString(2, "analyze");
			statement.setString(3, query);
			resultSet = statement.executeQuery();

			resultList = createExcelResultList(customerId, resultSet, bCrossTab);

		}	// end try
		catch (final SQLException e)
		{ // handle database exceptions in the usual manner
			logger.error("sql error", e);
			throw e;

		}	// end catch
		finally
		{ // clean up and release the connection
			close(resultSet);
			close(statement);
			close(conn);
		} // end finally

		return resultList;
	}	// end add

	public void setReportParams(final String reportName, final String param1, final String param2, final String param3) throws SQLException
	{

		Connection conn = null;
		CallableStatement statement = null;

		try
		{
			conn = getConnection();
			statement = conn.prepareCall("{call setReportParams(?, ?, ?, ?)}");
			statement.setString(1, reportName);
			statement.setString(2, param1);
			statement.setString(3, param2);
			statement.setString(4, param3);

			statement.executeUpdate();

		}	// end try
		catch (final SQLException e)
		{ // handle database exceptions in the usual manner
			logger.error("sql exception", e);
			throw e;

		}	// end catch
		finally
		{ // clean up and release the connection
			close(statement);
			close(conn);
		} // end finally
	}

	public String addToQueryFilter(final String queryFilter, final String filter)
	{
		return queryFilter +  " AND " + filter;
	}
*/

}
