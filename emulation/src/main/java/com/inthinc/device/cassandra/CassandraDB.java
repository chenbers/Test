package com.inthinc.device.cassandra;

import me.prettyprint.cassandra.model.AllOneConsistencyLevelPolicy;
import me.prettyprint.cassandra.serializers.AsciiSerializer;
import me.prettyprint.cassandra.serializers.ByteBufferSerializer;
import me.prettyprint.cassandra.serializers.BytesArraySerializer;
import me.prettyprint.cassandra.serializers.CompositeSerializer;
import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;

import com.inthinc.pro.automation.logging.Log;

public class CassandraDB {
    

    public static StringSerializer ss = StringSerializer.get();
    public static UUIDSerializer us = UUIDSerializer.get();
    public static IntegerSerializer is = IntegerSerializer.get();
    public static LongSerializer ls = LongSerializer.get();
    public static ByteBufferSerializer bbs = ByteBufferSerializer.get();
    public static BytesArraySerializer bas = BytesArraySerializer.get();
    public static CompositeSerializer cs = new CompositeSerializer();
    public static AsciiSerializer as = AsciiSerializer.get();

    private static Cluster cluster;
    private static String clusterName = "";
    private static String keyspaceName = "";
    private static String nodeAddress = "";
    private static boolean autoDiscoverHosts = false;
    private static int maxActive = 50;

    public CassandraDB(String clusterName, String keyspaceName, String nodeAddress, int maxActive, boolean autoDiscoverHosts) {
        CassandraDB.clusterName = clusterName;
        CassandraDB.keyspaceName = keyspaceName;
        CassandraDB.nodeAddress = nodeAddress;
        CassandraDB.maxActive = maxActive;
        CassandraDB.autoDiscoverHosts = autoDiscoverHosts;

        CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator(nodeAddress);

        cassandraHostConfigurator.setRetryDownedHostsDelayInSeconds(5);
        cassandraHostConfigurator.setMaxActive(CassandraDB.maxActive);
        cassandraHostConfigurator.setAutoDiscoverHosts(CassandraDB.autoDiscoverHosts);
        cassandraHostConfigurator.setRunAutoDiscoveryAtStartup(CassandraDB.autoDiscoverHosts);
        cassandraHostConfigurator.setUseThriftFramedTransport(true);
        cassandraHostConfigurator.setUseSocketKeepalive(true);

        cluster = HFactory.getOrCreateCluster(clusterName, cassandraHostConfigurator);
        Log.info("Cluster " + clusterName + " maxActive: " + maxActive + " created. Addresses: " + nodeAddress);
    }

    public void shutdown() {
        Log.info("cluster.getConnectionManager().shutdown() called");
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

}
