package com.inthinc.pro.table.dao.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.inthinc.pro.table.dao.model.SimpleTableItem;
import com.inthinc.pro.table.dao.model.TableFilterField;
import com.inthinc.pro.table.dao.model.TableSortField;
import com.inthinc.pro.table.dao.model.TableSortField.SortOrder;
import com.inthinc.pro.table.dao.pagination.PaginationDAOService;

public class SimplePaginationDAO  extends BaseDAO implements PaginationDAOService<SimpleTableItem> {

	protected static final Logger logger = LogManager.getLogger(SimplePaginationDAO.class);
	
	public void init()
	{
		logger.info("init SimplePaginationDAO");
		
	}
	@Override
	public int countAll(List<TableFilterField> filter) {
		
		String queryStr = "select count(driverID) from redFlagNote ";
		queryStr += getFilterQuery(filter);
		logger.info(queryStr);

		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;


		try		{
			conn = this.getConnection();
			statement = conn.createStatement();

			resultSet = statement.executeQuery(queryStr);

			while (resultSet.next())
			{

				int count = resultSet.getInt(1);
				return count;
			}

		}	// end try
		catch (SQLException e)
		{ // handle database hosLogs in the usual manner
			logger.error("error getting count", e);

		}	// end catch
		finally
		{ // clean up and release the connection
			close(resultSet);
			close(statement);
			if (conn != null)
				close(conn);
		} // end finally


		return 0;
	}

	@Override
	public List<SimpleTableItem> getItemsByRange(int firstRow, int lastRow,
			List<TableSortField> sort, List<TableFilterField> filter) {

		Date startTime = null;
		Date nowTime = null;
			
		// ignore sort/filter for now
		List<SimpleTableItem> itemList = new ArrayList<SimpleTableItem>();

		String queryStr = "select driverID, penalty, last from redFlagNote ";
		queryStr += getFilterQuery(filter);
		queryStr += getSortQuery(sort);
		queryStr += " limit " + (lastRow-firstRow) + " offset " + firstRow;
		logger.info(queryStr);

		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;


		try		{
			conn = this.getConnection();
			statement = conn.createStatement();
			
			
			startTime = new Date();
			resultSet = statement.executeQuery(queryStr);
			
			nowTime = new Date();
			logger.info("query elapsed " + (nowTime.getTime()-startTime.getTime()));
			startTime = nowTime;
			while (resultSet.next())
			{

				int driverID = resultSet.getInt(1);
				float penalty = resultSet.getFloat(2);
				String last = resultSet.getString(3);
				itemList.add(new SimpleTableItem(last, driverID, penalty));
			}
			nowTime = new Date();
			logger.info("item list elapsed " + (nowTime.getTime()-startTime.getTime()));

		}	// end try
		catch (SQLException e)
		{ // handle database hosLogs in the usual manner
			logger.error("error getting count", e);

		}	// end catch
		finally
		{ // clean up and release the connection
			close(resultSet);
			close(statement);
			if (conn != null)
				close(conn);
		} // end finally


		return itemList;
	}
	private String getSortQuery(List<TableSortField> sortList) {
		
		String orderBy = "";
		if (sortList != null) {
			for (TableSortField sortField : sortList) {
				String clause = null;
				SortOrder order = sortField.getOrder();
				if (order == null)
					continue;
				if (sortField.getField().contains("strItem")) {
					clause = "last " + ((order == SortOrder.ASCENDING) ? "asc " : "desc ");
				}
				else if (sortField.getField().contains("intItem")) {
					clause = "driverID "  + ((order == SortOrder.ASCENDING) ? "asc " : "desc ");
				}
				else if (sortField.getField().contains("floatItem")) {
					clause = "penalty "  + ((order == SortOrder.ASCENDING) ? "asc " : "desc ");
				}
				if (clause != null) {
					if (orderBy.isEmpty())
						orderBy += " order by ";
					else orderBy += ", ";
					orderBy += clause;
				}
			}
		}
		
		return orderBy;
	}
	private String getFilterQuery(List<TableFilterField> filterList) {
		String where = "";
		if (filterList != null) {
			for (TableFilterField filterField : filterList)
			{
				String clause = null;
				if (filterField.getField().contains("strItem")) {
					clause = "last like '%" + filterField.getFilter() + "%'" ;
				}
				else if (filterField.getField().contains("intItem")) {
					clause = "driverID = " + filterField.getFilter();
				}
				else if (filterField.getField().contains("floatItem")) {
					clause = "penalty = " + filterField.getFilter();
				}
				if (clause != null) {
					if (where.isEmpty())
						where += "where ";
					else where += "and ";
					where += clause;
				}
			}
		}
		
		return where;
	}
	
}

