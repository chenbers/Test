package com.inthinc.device.noteservice;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import me.prettyprint.cassandra.model.AllOneConsistencyLevelPolicy;
import me.prettyprint.cassandra.serializers.ByteBufferSerializer;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.Composite;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.beans.Rows;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.MutationResult;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.MultigetSliceQuery;
import me.prettyprint.hector.api.query.QueryResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inthinc.device.CassandraProperties;
import com.inthinc.device.CassandraPropertiesBean;
import com.inthinc.device.resources.DeviceStatistics;

public class NoteService {
    private static Logger logger = LoggerFactory.getLogger(NoteLoader.class);

    private static StringSerializer stringSerializer = StringSerializer.get();
    private static UUIDSerializer uuidSerializer = UUIDSerializer.get();
    private static LongSerializer longSerializer = LongSerializer.get();
    private static ByteBufferSerializer byteBufferSerializer = ByteBufferSerializer.get();

    private Cluster cluster;

    private String clusterName = "";
    private String keyspaceName = "";
    private String nodeAddress = "";

    public static NoteService createNode() {
        CassandraPropertiesBean cpb = CassandraProperties.getPropertyBean();
        return new NoteService(cpb.getClusterName(), "note", cpb.getAddress(), cpb.getPoolSize(), cpb.isAutoDiscovery());
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

    public NoteService(String clusterName, String keyspaceName, String nodeAddress, int maxActive, boolean autoDiscoverHosts) {
        this.clusterName = clusterName;
        this.keyspaceName = keyspaceName;
        this.nodeAddress = nodeAddress;

        CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator(nodeAddress);

        cassandraHostConfigurator.setRetryDownedHostsDelayInSeconds(5);
        cassandraHostConfigurator.setMaxActive(maxActive);
        cassandraHostConfigurator.setAutoDiscoverHosts(autoDiscoverHosts);
        cassandraHostConfigurator.setRunAutoDiscoveryAtStartup(autoDiscoverHosts);
        // cassandraHostConfigurator.setUseThriftFramedTransport(true);
        cassandraHostConfigurator.setUseSocketKeepalive(true);

        // cluster = HFactory.getOrCreateCluster(clusterName, nodeAddress);
        cluster = HFactory.getOrCreateCluster(clusterName, cassandraHostConfigurator);
        logger.info("Cluster " + clusterName + " maxActive: " + maxActive + " created. Addresses: " + nodeAddress);
    }

    public void shutdown() {
        logger.info("cluster.getConnectionManager().shutdown() called");
        cluster.getConnectionManager().shutdown();
    }

    public MutationResult insertNote(Map<String, String> attribs) {
        // try {
        Keyspace keyspaceOperator = HFactory.createKeyspace(keyspaceName, cluster);

        keyspaceOperator.setConsistencyLevelPolicy(new AllOneConsistencyLevelPolicy());

        UUID noteId = UUID.randomUUID();
        Mutator<UUID> noteMutator = HFactory.createMutator(keyspaceOperator, uuidSerializer);
        for (Map.Entry<String, String> attrib : attribs.entrySet()) {
            noteMutator.addInsertion(noteId, "note", HFactory.createColumn(String.valueOf(attrib.getKey()), String.valueOf(attrib.getValue()), stringSerializer, stringSerializer));
        }
        MutationResult mr = noteMutator.execute();

        Long deviceId = new Long((String) attribs.get("32900"));
        Integer noteType = new Integer((String) attribs.get("10"));
        Integer noteTime = new Integer((String) attribs.get("11"));
        Mutator<Long> indexMutator = HFactory.createMutator(keyspaceOperator, LongSerializer.get());

        indexMutator.addInsertion(deviceId, "deviceNoteTimeTypeIndex", HFactory.createColumn(Composite.toByteBuffer(noteTime, noteType), noteId, byteBufferSerializer, uuidSerializer));
        indexMutator.addInsertion(deviceId, "deviceNoteTypeTimeIndex", HFactory.createColumn(Composite.toByteBuffer(noteType, noteTime), noteId, byteBufferSerializer, uuidSerializer));
        mr = indexMutator.execute();
        return mr;
        // } catch (Throwable e) {
        // logger.debug("EXCEPTION INSERTING NOTE: " + e);
        // e.printStackTrace();
        // }
        // return null;
    }

    public void insertRaw(long deviceId, byte[] in, byte[] out) {
        try {
            Keyspace keyspaceOperator = HFactory.createKeyspace(keyspaceName, cluster);
            Mutator<Long> rawMutator = HFactory.createMutator(keyspaceOperator, LongSerializer.get());

            ByteBuffer data = ByteBuffer.allocate(in.length + out.length + 2);

            data.put(in);
            data.put("||".getBytes());
            data.put(out);

            rawMutator.addInsertion(deviceId, "raw_call", HFactory.createColumn(new Date().getTime(), data, longSerializer, byteBufferSerializer));

            MutationResult mr = rawMutator.execute();
        } catch (HectorException e) {
            e.printStackTrace();
        }
    }

    public void fetchNote(List<UUID> keys) {
        Keyspace keyspaceOperator = HFactory.createKeyspace("note", cluster);
        MultigetSliceQuery<UUID, String, String> sliceQuery = HFactory.createMultigetSliceQuery(keyspaceOperator, uuidSerializer, stringSerializer, stringSerializer);

        sliceQuery.setRange("", "", false, 1000);

        sliceQuery.setColumnFamily("note");
        sliceQuery.setKeys(keys);
        // rangeSlicesQuery.setReturnKeysOnly();

        QueryResult<Rows<UUID, String, String>> result = sliceQuery.execute();

        Rows<UUID, String, String> rows = result.get();

        for (Row<UUID, String, String> row : rows) {
            ColumnSlice<String, String> columns = row.getColumnSlice();

            List<HColumn<String, String>> columnList = columns.getColumns();

            for (HColumn<String, String> column : columnList) {
                String name = column.getName();
                String value = column.getValue();

                System.out.println("Note key: " + row.getKey() + " name: " + name + " value: " + value);
            }
            System.out.println("***************************************************************");
        }
    }

    public List<MutationResult> insertNote(List<Map<String, String>> list) {
        List<MutationResult> results = new ArrayList<MutationResult>();
        for (Map<String, String> note : list) {
            results.add(insertNote(note));
            DeviceStatistics.addCall();
        }
        return results;
    }

    public Keyspace getKeyspace() {
        Keyspace keyspaceOperator = HFactory.createKeyspace(keyspaceName, cluster);
        keyspaceOperator.setConsistencyLevelPolicy(new AllOneConsistencyLevelPolicy());
        return keyspaceOperator;
    }
}