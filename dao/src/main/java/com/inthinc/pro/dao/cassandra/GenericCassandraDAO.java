package com.inthinc.pro.dao.cassandra;

import java.io.Serializable;
import org.apache.log4j.Logger;

import com.inthinc.pro.ProDAOException;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.BigIntegerSerializer; 
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.ByteBufferSerializer;
import me.prettyprint.cassandra.serializers.BytesArraySerializer;
import me.prettyprint.cassandra.serializers.CompositeSerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.model.AllOneConsistencyLevelPolicy;
import org.apache.cassandra.thrift.ConsistencyLevel;


public abstract class GenericCassandraDAO  implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(GenericCassandraDAO.class);
    
    final static StringSerializer stringSerializer = StringSerializer.get();
    final static UUIDSerializer uuidSerializer = UUIDSerializer.get();
    final static IntegerSerializer integerSerializer = IntegerSerializer.get();
    final static BigIntegerSerializer bigIntegerSerializer = BigIntegerSerializer.get();
    final static LongSerializer longSerializer = LongSerializer.get();
    final static ByteBufferSerializer byteBufferSerializer = ByteBufferSerializer.get();
    final static BytesArraySerializer bytesArraySerializer = BytesArraySerializer.get();
    final static CompositeSerializer compositeSerializer = new CompositeSerializer();
    
	final static String note_CF = "note";
	final static String vehicleNoteTimeTypeIndex_CF = "vehicleNoteTimeTypeIndex";
	final static String vehicleNoteTypeTimeIndex_CF = "vehicleNoteTypeTimeIndex";
	final static String driverNoteTimeTypeIndex_CF = "driverNoteTimeTypeIndex";
	final static String driverNoteTypeTimeIndex_CF = "driverNoteTypeTimeIndex";
	final static String driverGroupNoteTimeTypeIndex30_CF = "driverGroupNoteTimeTypeIndex30";
	final static String driverGroupNoteTypeTimeIndex30_CF = "driverGroupNoteTypeTimeIndex30";
	final static String vehicleNoteTimeTypeIndex60_CF = "vehicleNoteTimeTypeIndex60";
	final static String vehicleNoteTypeTimeIndex60_CF = "vehicleNoteTypeTimeIndex60";
	final static String driverNoteTimeTypeIndex60_CF = "driverNoteTimeTypeIndex60";
	final static String driverNoteTypeTimeIndex60_CF = "driverNoteTypeTimeIndex60";
	
	final static String driverBreadCrumb_CF = "driverBreadCrumb";
	final static String vehicleBreadCrumb_CF = "vehicleBreadCrumb";
	final static String driverBreadCrumb60_CF = "driverBreadCrumb60";
	final static String vehicleBreadCrumb60_CF = "vehicleBreadCrumb60";
	
	final static String currentTripDescription_CF = "currentTripDescription"; 
	final static String currentTripCounter_CF = "currentTripCounter"; 
	final static String trip_CF = "trip"; 
	final static String driverTripIndex_CF = "driverTripIndex"; 
	final static String vehicleTripIndex_CF = "vehicleTripIndex"; 
	
	
	final static String ATTRIBS_COL = "a"; 
	final static String RAW_COL = "r"; 
	final static String DRIVERID_COL = "d"; 
	final static String FORGIVEN_COL = "f"; 
	final static String METHOD_COL = "m"; 
    
    
    private CassandraDB cassandraDB;
    

	public GenericCassandraDAO()
    {
    }
	
	
	public CassandraDB getCassandraDB() {
		return cassandraDB;
	}


	public void setCassandraDB(CassandraDB cassandraDB) {
		this.cassandraDB = cassandraDB;
	}


    public void shutdown()
    {
    	 logger.info("cluster.getConnectionManager().shutdown() called");
    	 cassandraDB.shutdown();
    }

    public String getClusterName() {
		return CassandraDB.getClusterName();
	}

	public String getKeyspaceName() {
		return CassandraDB.getKeyspaceName();
	}

	public String getNodeAddress() {
		return CassandraDB.getNodeAddress();
	}


	public boolean isAutoDiscoverHosts() {
		return CassandraDB.isAutoDiscoverHosts();
	}

	public int getMaxActive() {
		return CassandraDB.getMaxActive();
	}

	
	public Keyspace getKeyspace()
	{
		return CassandraDB.getKeyspace();
	}
	
}
