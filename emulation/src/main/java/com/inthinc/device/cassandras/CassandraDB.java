package com.inthinc.device.cassandras;

import me.prettyprint.cassandra.model.AllOneConsistencyLevelPolicy;
import me.prettyprint.cassandra.serializers.ByteBufferSerializer;
import me.prettyprint.cassandra.serializers.BytesArraySerializer;
import me.prettyprint.cassandra.serializers.CompositeSerializer;
import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.BigIntegerSerializer;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;

public class CassandraDB {

    public final static StringSerializer ss = StringSerializer.get();
    public final static UUIDSerializer us = UUIDSerializer.get();
    public final static IntegerSerializer is = IntegerSerializer.get();
    public final static BigIntegerSerializer bis = BigIntegerSerializer.get();
    public final static LongSerializer ls = LongSerializer.get();
    public final static ByteBufferSerializer bbs = ByteBufferSerializer.get();
    public final static BytesArraySerializer bas = BytesArraySerializer.get();
    public final static CompositeSerializer cs = new CompositeSerializer();

    private Cluster cluster;
    private String clusterName = "";
    private String keyspaceName = "";
    private String nodeAddress = "";
    private boolean autoDiscoverHosts = false;
    private int maxActive = 50;

    public CassandraDB(String clusterName, String keyspaceName, String nodeAddress, int maxActive, boolean autoDiscoverHosts) {
        this.clusterName = clusterName;
        this.keyspaceName = keyspaceName;
        this.nodeAddress = nodeAddress;
        this.maxActive = maxActive;
        this.autoDiscoverHosts = autoDiscoverHosts;

        CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator(nodeAddress);

        cassandraHostConfigurator.setRetryDownedHostsDelayInSeconds(5);
        cassandraHostConfigurator.setMaxActive(this.maxActive);
        cassandraHostConfigurator.setAutoDiscoverHosts(this.autoDiscoverHosts);
        cassandraHostConfigurator.setRunAutoDiscoveryAtStartup(this.autoDiscoverHosts);
        cassandraHostConfigurator.setUseThriftFramedTransport(true);
        cassandraHostConfigurator.setUseSocketKeepalive(true);

        cluster = HFactory.getOrCreateCluster(clusterName, cassandraHostConfigurator);
        // Log.i("Cluster " + clusterName + " maxActive: " + maxActive + " created. Addresses: " + nodeAddress);
    }

    public void shutdown() {
        // Log.i("cluster.getConnectionManager().shutdown() called");
        cluster.getConnectionManager().shutdown();
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getKeyspaceName() {
        return keyspaceName;
    }

    public void setKeyspaceName(String keyspaceName) {
        this.keyspaceName = keyspaceName;
    }

    public String getNodeAddress() {
        return nodeAddress;
    }

    public void setNodeAddress(String nodeAddress) {
        this.nodeAddress = nodeAddress;
    }

    public boolean isAutoDiscoverHosts() {
        return autoDiscoverHosts;
    }

    public void setAutoDiscoverHosts(boolean autoDiscoverHosts) {
        this.autoDiscoverHosts = autoDiscoverHosts;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public Keyspace getKeyspace() {
        Keyspace keyspaceOperator = HFactory.createKeyspace(keyspaceName, cluster);
        keyspaceOperator.setConsistencyLevelPolicy(new AllOneConsistencyLevelPolicy());
        return keyspaceOperator;
    }

}
