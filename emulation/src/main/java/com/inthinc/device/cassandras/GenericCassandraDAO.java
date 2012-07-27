package com.inthinc.device.cassandras;

import java.io.Serializable;

import me.prettyprint.cassandra.serializers.BigIntegerSerializer;
import me.prettyprint.cassandra.serializers.ByteBufferSerializer;
import me.prettyprint.cassandra.serializers.BytesArraySerializer;
import me.prettyprint.cassandra.serializers.CompositeSerializer;
import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.hector.api.Keyspace;

import com.inthinc.pro.automation.logging.Log;

public abstract class GenericCassandraDAO implements Serializable {
    private static final long serialVersionUID = 1L;

    final static StringSerializer stringSerializer = StringSerializer.get();
    final static UUIDSerializer uuidSerializer = UUIDSerializer.get();
    final static IntegerSerializer integerSerializer = IntegerSerializer.get();
    final static BigIntegerSerializer bigIntegerSerializer = BigIntegerSerializer.get();
    final static LongSerializer longSerializer = LongSerializer.get();
    final static ByteBufferSerializer byteBufferSerializer = ByteBufferSerializer.get();
    final static BytesArraySerializer bytesArraySerializer = BytesArraySerializer.get();
    final static CompositeSerializer compositeSerializer = new CompositeSerializer();

    private CassandraDB cassandraDB;

    public GenericCassandraDAO() {}

    public CassandraDB getCassandraDB() {
        return cassandraDB;
    }

    public void setCassandraDB(CassandraDB cassandraDB) {
        this.cassandraDB = cassandraDB;
    }

    public void shutdown() {
        Log.debug("cluster.getConnectionManager().shutdown() called");
        cassandraDB.shutdown();
    }

    public String getClusterName() {
        return cassandraDB.getClusterName();
    }

    public String getKeyspaceName() {
        return cassandraDB.getKeyspaceName();
    }

    public String getNodeAddress() {
        return cassandraDB.getNodeAddress();
    }

    public boolean isAutoDiscoverHosts() {
        return cassandraDB.isAutoDiscoverHosts();
    }

    public int getMaxActive() {
        return cassandraDB.getMaxActive();
    }

    public Keyspace getKeyspace() {
        return cassandraDB.getKeyspace();
    }

}
