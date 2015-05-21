package com.inthinc.pro.dao.cassandra.datastax;

import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.Session;

public class BaseCassandraDAO {

    protected final static int ONEYEAR_SECONDS = 31622400;
    protected final static int TWODAYS_SECONDS = 172800;
    protected final static int FOURDAYS_SECONDS = 345600;

    protected static CassandraDB cassandraDB = null;

    public CassandraDB getCassandraDB() {
        return cassandraDB;
    }

    public void setCassandraDB(CassandraDB cassandraDB) {
    	if (this.cassandraDB == null)
    		this.cassandraDB = cassandraDB;
    }

	public Session getSession() {
		return cassandraDB.getSession();
	}
	
	public Session getCacheSession() {
		return cassandraDB.getCacheSession();
	}
	
	public static ConsistencyLevel getCL() {
		return cassandraDB.getCL();
	}
}
