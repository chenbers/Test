package com.inthinc.pro.dao.cassandra;

import me.prettyprint.cassandra.model.AllOneConsistencyLevelPolicy;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CassandraDB {

    private static Cluster cluster;
    private static String clusterName = "";
    private static String keyspaceName = "";
    private static String cacheKeyspaceName = "";
    private static String nodeAddress = "";
    private static boolean autoDiscoverHosts = false;
    private static int maxActive = 50;
    private static Logger logger = LoggerFactory.getLogger(CassandraDB.class);

    public CassandraDB(boolean active, String clusterName, String keyspaceName, String cacheKeyspaceName, String nodeAddress, int maxActive, boolean autoDiscoverHosts) {
        CassandraDB.clusterName = clusterName;
        CassandraDB.keyspaceName = keyspaceName;
        CassandraDB.nodeAddress = nodeAddress;
        CassandraDB.maxActive = maxActive;
        CassandraDB.cacheKeyspaceName = cacheKeyspaceName;
        CassandraDB.autoDiscoverHosts = autoDiscoverHosts;

        logger.info("CassandrsaDB clusterName: " + clusterName + " nodeAddress: " + nodeAddress + " keyspaceName: " + keyspaceName + "  cacheKeyspaceName: " + cacheKeyspaceName + " autoDiscoverHosts: " + autoDiscoverHosts);
        
        CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator(nodeAddress);

        cassandraHostConfigurator.setRetryDownedHostsDelayInSeconds(5);
        cassandraHostConfigurator.setMaxActive(CassandraDB.maxActive);
        cassandraHostConfigurator.setAutoDiscoverHosts(CassandraDB.autoDiscoverHosts);
        cassandraHostConfigurator.setRunAutoDiscoveryAtStartup(CassandraDB.autoDiscoverHosts);
        cassandraHostConfigurator.setUseThriftFramedTransport(true);
        cassandraHostConfigurator.setUseSocketKeepalive(true);

        if (active)
            cluster = HFactory.getOrCreateCluster(clusterName, cassandraHostConfigurator);
    }

    public void shutdown() {
        // Log.i("cluster.getConnectionManager().shutdown() called");
        cluster.getConnectionManager().shutdown();
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

    public static int getMaxActive() {
        return maxActive;
    }

    public static void setMaxActive(int maxActive) {
        CassandraDB.maxActive = maxActive;
    }

    public static Keyspace getKeyspace() {
        Keyspace keyspaceOperator = HFactory.createKeyspace(keyspaceName, cluster);
        keyspaceOperator.setConsistencyLevelPolicy(new AllOneConsistencyLevelPolicy());
        return keyspaceOperator;
    }

    public static Keyspace getCacheKeyspace() {
        logger.debug("getCacheKeyspaceName(): " + cacheKeyspaceName);
        Keyspace keyspaceOperator = HFactory.createKeyspace(cacheKeyspaceName, cluster);
        keyspaceOperator.setConsistencyLevelPolicy(new AllOneConsistencyLevelPolicy());
        return keyspaceOperator;
    }
}
