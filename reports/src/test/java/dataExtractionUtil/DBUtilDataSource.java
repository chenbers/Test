package dataExtractionUtil;

import java.sql.Connection;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DBUtilDataSource {

	private static Logger logger = Logger.getLogger(DBUtilDataSource.class);

	DriverManagerDataSource datasource;
	
	public DBUtilDataSource()
	{
		initDataSourceFromProperties(new DBUtilProperties());
	}

	private void initDataSourceFromProperties(DBUtilProperties dbUtilProperties) {
		
		datasource = new DriverManagerDataSource();
		datasource.setDriverClassName(dbUtilProperties.getJDBCClass());
		datasource.setUrl(dbUtilProperties.getJDBCUrl());
		datasource.setUsername(dbUtilProperties.getDBUser());
		datasource.setPassword(dbUtilProperties.getDBPassword());
	}
	
	public Connection getConnection()
	{
		try {
			return datasource.getConnection();
		} catch (SQLException e) {
			logger.error(e);
			return null;
		}
	}

	public DataSource getRealDataSource() {
		
		return datasource;
	}
	
	

}
