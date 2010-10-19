package it.config;

import java.sql.Connection;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class ITDataSource {

	private static Logger logger = Logger.getLogger(ITDataSource.class);

	DriverManagerDataSource datasource;
	
	public ITDataSource()
	{
		initDataSourceFromProperties(new IntegrationConfig());
	}

	private void initDataSourceFromProperties(IntegrationConfig config) {
        
		datasource = new DriverManagerDataSource();
		datasource.setDriverClassName(config.getProperty(IntegrationConfig.JDBC_DRIVER_CLASS_NAME));
		datasource.setUrl(config.getProperty(IntegrationConfig.JDBC_MYSQL_URL));
		datasource.setUsername(config.getProperty(IntegrationConfig.JDBC_MYSQL_USERNAME));
		datasource.setPassword(config.getProperty(IntegrationConfig.JDBC_MYSQL_PASSWORD));
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
