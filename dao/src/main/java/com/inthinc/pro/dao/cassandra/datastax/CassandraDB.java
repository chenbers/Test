package com.inthinc.pro.dao.cassandra.datastax;


import org.apache.log4j.Logger;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;


public class CassandraDB {
	private Cluster cluster;
	private Session session;
	private Session cacheSession;
    private static String clusterName = "";
    private static String keyspaceName = "";
    private static String cacheKeyspaceName = "";
    private static String nodeAddress = "";
    private static boolean autoDiscoverHosts = false;
    private static boolean quorumConsistency = false;
    private static int maxActive = 10;
    
    private static Logger logger = Logger.getLogger(CassandraDB.class);

    public CassandraDB(boolean active, String clusterName, String keyspaceName, String cacheKeyspaceName, String nodeAddress, int maxActive, boolean autoDiscoverHosts, boolean quorumConsistency) {
        CassandraDB.clusterName = clusterName;
        CassandraDB.keyspaceName = keyspaceName;
        CassandraDB.nodeAddress = nodeAddress;
        CassandraDB.maxActive = maxActive;
        CassandraDB.cacheKeyspaceName = cacheKeyspaceName;
        CassandraDB.autoDiscoverHosts = autoDiscoverHosts;
        CassandraDB.quorumConsistency = quorumConsistency;
        
        logger.info("CassandrsaDB clusterName: " + clusterName + " nodeAddress: " + nodeAddress + " keyspaceName: " + keyspaceName + "  cacheKeyspaceName: " + cacheKeyspaceName + " autoDiscoverHosts: " + autoDiscoverHosts + " quorumConsistency: " + quorumConsistency);
        
        
        if (active)
    		connect();
    }

	public void connect() {
		cluster = Cluster.builder().addContactPoint(nodeAddress).build();
		Metadata metadata = cluster.getMetadata();
		System.out.printf("Connected to cluster: %s\n", metadata.getClusterName());
		for (Host host : metadata.getAllHosts()) {
			logger.info("Datatacenter: " + host.getDatacenter() + " Host: " +  host.getAddress() + " Rack: " + host.getRack());
		}
		session = cluster.connect(keyspaceName);
		cacheSession = cluster.connect(cacheKeyspaceName);
	}
	
	public Session getSession() {
		return session;
	}

	public Session getCacheSession() {
		return cacheSession;
	}

	public void close() {
		cacheSession.close();
		session.close();
	    cluster.close();
	}


    public static String getClusterName() {
        return clusterName;
    }

    public static void setClusterName(String clusterName) {
        CassandraDB.clusterName = clusterName;
    }

    public static String getKeyspaceName() {
        return keyspaceName;
    }

    public static void setKeyspaceName(String keyspaceName) {
        CassandraDB.keyspaceName = keyspaceName;
    }

    public static String getCacheKeyspaceName() {
        return cacheKeyspaceName;
    }

    public static void setCacheKeyspaceName(String cacheKeyspaceName) {
        CassandraDB.cacheKeyspaceName = cacheKeyspaceName;
    }
    
    public static String getNodeAddress() {
        return nodeAddress;
    }

    public static void setNodeAddress(String nodeAddress) {
        CassandraDB.nodeAddress = nodeAddress;
    }

    public static boolean isAutoDiscoverHosts() {
        return autoDiscoverHosts;
    }

    public static void setAutoDiscoverHosts(boolean autoDiscoverHosts) {
        CassandraDB.autoDiscoverHosts = autoDiscoverHosts;
    }

    public static boolean isQuorumConsistency() {
        return quorumConsistency;
    }

    public static void setQuorumConsistency(boolean quorumConsistency) {
        CassandraDB.quorumConsistency = quorumConsistency;
    }

	public static ConsistencyLevel getCL() {
		return (quorumConsistency) ? ConsistencyLevel.valueOf("QUORUM") : ConsistencyLevel.valueOf("ONE");
	}
    
    public static int getMaxActive() {
        return maxActive;
    }

    public static void setMaxActive(int maxActive) {
        CassandraDB.maxActive = maxActive;
    }
}
