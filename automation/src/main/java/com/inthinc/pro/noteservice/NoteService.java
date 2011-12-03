package com.inthinc.pro.noteservice;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import me.prettyprint.cassandra.model.AllOneConsistencyLevelPolicy;
import me.prettyprint.cassandra.serializers.ByteBufferSerializer;
import me.prettyprint.cassandra.serializers.BytesArraySerializer;
import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.Composite;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HCounterColumn;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.beans.Rows;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.MutationResult;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.CountQuery;
import me.prettyprint.hector.api.query.CounterQuery;
import me.prettyprint.hector.api.query.MultigetSliceQuery;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.SliceQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoteService {
    private static Logger logger = LoggerFactory.getLogger(NoteLoader.class);

    private static StringSerializer stringSerializer = StringSerializer.get();
    private static UUIDSerializer uuidSerializer = UUIDSerializer.get();
    private static IntegerSerializer integerSerializer = IntegerSerializer.get();
    private static LongSerializer longSerializer = LongSerializer.get();
    private static ByteBufferSerializer byteBufferSerializer = ByteBufferSerializer.get();
    private static BytesArraySerializer bytesArraySerializer = BytesArraySerializer.get();
    
    private Cluster cluster;
	
    private String clusterName = "";
    private String keyspaceName = "";
    private String nodeAddress = "";
    
 
    public static void main(String[] args)
    {
    	NoteService ns = new NoteService("inthinc", "note", "cassandra-node1.tiwipro.com", 50, true);
    	ns.createNotes();
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

    
    public NoteService(String clusterName, String keyspaceName, String nodeAddress, int maxActive, boolean autoDiscoverHosts)
    {
        this.clusterName = clusterName;
        this.keyspaceName = keyspaceName;
        this.nodeAddress = nodeAddress;
        
        CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator(nodeAddress);
        
        cassandraHostConfigurator.setRetryDownedHostsDelayInSeconds(5);        
        cassandraHostConfigurator.setMaxActive(maxActive);
        cassandraHostConfigurator.setAutoDiscoverHosts(autoDiscoverHosts);
        cassandraHostConfigurator.setRunAutoDiscoveryAtStartup(autoDiscoverHosts);
//        cassandraHostConfigurator.setUseThriftFramedTransport(true);
        cassandraHostConfigurator.setUseSocketKeepalive(true);
        
//        cluster = HFactory.getOrCreateCluster(clusterName, nodeAddress);
        cluster = HFactory.getOrCreateCluster(clusterName, cassandraHostConfigurator);
      	 logger.info("Cluster " + clusterName + " maxActive: " + maxActive +" created. Addresses: " + nodeAddress);
    }
    
    public void shutdown()
    {
    	 logger.info("cluster.getConnectionManager().shutdown() called");
    	 cluster.getConnectionManager().shutdown();
    }
    
    public Map<String, String> createNotes()
    {
    	Map<String, String> attribs = new HashMap<String, String>();
    	attribs.put("32900", "1234567");
    	attribs.put("16","46459400");
    	attribs.put("19","0");
    	attribs.put("18","38498");
    	attribs.put("24578","D314035752");
    	attribs.put("8202","2");
    	attribs.put("231","4628873");
    	attribs.put("10","113");
    	attribs.put("11","1313978399");
    	attribs.put("227","75732");
    	attribs.put("148","310");
    	attribs.put("13","(64.95600001303386,-109.50000003667083)");
    	attribs.put("15","74");    	
    	
    	return attribs;
/*    	
    	//		insertNote(attribs);

    	attribs.put(String.valueOf(Attrib.DEVICEID.getCode()), "1234567");
    	attribs.put("16","46459400");
    	attribs.put("19","0");
    	attribs.put("18","38498");
    	attribs.put("24578","D314035752");
    	attribs.put("8202","2");
    	attribs.put("231","4628873");
    	attribs.put("10","300");
    	attribs.put("11","1313978400");
    	attribs.put("227","75732");
    	attribs.put("148","310");
    	attribs.put("13","(64.95600001303386,-109.50000003667083)");
    	attribs.put("15","74");    	
//		insertNote(attribs);
		
	
		fetchNotesByTime(new Long(1234567), new Integer(0), new Integer(0));
		fetchNotesByTime(new Long(12345678), new Integer(1313978398), new Integer(1313978400));
		fetchNotesByTime(new Long(1234567), new Integer(1313978398), new Integer(1313978400));
*/		
//        cluster.getConnectionManager().shutdown();
    }
    

    public MutationResult insertNote(Map<String, String> attribs)
    {
//        try {
        	Keyspace keyspaceOperator = HFactory.createKeyspace(keyspaceName, cluster);
        	
        	keyspaceOperator.setConsistencyLevelPolicy(new AllOneConsistencyLevelPolicy());

        	UUID noteId = UUID.randomUUID();
            Mutator<UUID> noteMutator = HFactory.createMutator(keyspaceOperator, uuidSerializer);
            for (Map.Entry<String, String> attrib : attribs.entrySet()) {
					noteMutator.addInsertion(noteId, "note", HFactory.createColumn(String.valueOf(attrib.getKey()), String.valueOf(attrib.getValue()), stringSerializer, stringSerializer));
            }
            MutationResult mr = noteMutator.execute();

            Long deviceId = new Long((String)attribs.get("32900"));
            Integer noteType = new Integer((String)attribs.get("10"));
            Integer noteTime = new Integer((String)attribs.get("11"));
            Mutator<Long> indexMutator = HFactory.createMutator(keyspaceOperator, LongSerializer.get());
            
            indexMutator.addInsertion(deviceId, "deviceNoteTimeTypeIndex", HFactory.createColumn(Composite.toByteBuffer(noteTime, noteType), noteId, byteBufferSerializer, uuidSerializer));
            indexMutator.addInsertion(deviceId, "deviceNoteTypeTimeIndex", HFactory.createColumn(Composite.toByteBuffer(noteType, noteTime), noteId, byteBufferSerializer, uuidSerializer));
            mr = indexMutator.execute();
            return mr;
//        } catch (Throwable e) {
//			logger.debug("EXCEPTION INSERTING NOTE: " + e);
//            e.printStackTrace();
//        }
//        return null;
    }

    public void insertRaw(long deviceId, byte[] in, byte[] out)
    {
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

    
    public void fetchNotesByTime(Long deviceId, Integer startTime, Integer endTime)
    {
    	System.out.println("fetchNotesByTime called: " + deviceId);

    	Keyspace keyspaceOperator = HFactory.createKeyspace("note", cluster);
        SliceQuery<Long, ByteBuffer, UUID> sliceQuery = HFactory.createSliceQuery(keyspaceOperator, longSerializer, byteBufferSerializer, uuidSerializer);
        
        sliceQuery.setRange(Composite.toByteBuffer(startTime, 0), Composite.toByteBuffer(endTime, 9999), false, 1000);
        
        sliceQuery.setColumnFamily("deviceNoteTimeTypeIndex");            
        sliceQuery.setKey(deviceId);
        //rangeSlicesQuery.setReturnKeysOnly();
        
        QueryResult<ColumnSlice<ByteBuffer, UUID>> result = sliceQuery.execute();
        ColumnSlice<ByteBuffer, UUID> columns = result.get();            
        
        List<HColumn<ByteBuffer, UUID>> columnList = columns.getColumns();
        
        
        List<UUID> keyList = new ArrayList<UUID>(); 
        for (HColumn<ByteBuffer, UUID> column : columnList)
        {
        	
        	ByteBuffer columnName = column.getName();
        	UUID key = column.getValue();

//        	System.out.println("Column Name: " + new String(columnName.array()));
        	System.out.println("Note key: " + key.toString());
        	keyList.add(key);
        }
    	fetchNote(keyList);
     }

    public Integer countNotesByDevice(Long deviceId)
    {
        Integer startTime = 0; 
        Integer endTime = (int)new java.util.Date().getTime()/1000;
    	System.out.println("fetchNotesByTime called: " + deviceId);

    	Keyspace keyspaceOperator = HFactory.createKeyspace("note", cluster);
        CountQuery<Long, ByteBuffer> countQuery = HFactory.createCountQuery(keyspaceOperator, longSerializer, byteBufferSerializer);
        
        countQuery.setRange(Composite.toByteBuffer(startTime, 0), Composite.toByteBuffer(endTime, 9999), 1000000);
        
        countQuery.setColumnFamily("deviceNoteTimeTypeIndex");            
        countQuery.setKey(deviceId);
        //rangeSlicesQuery.setReturnKeysOnly();
        
        QueryResult<Integer> result = countQuery.execute();
		logger.info("DeviceId: " + deviceId + " records: " + result.get());
        
		totalCount(result.get());
		
        return result.get();            
     }

    private long totalCount(int count)
    {
    	long deviceId = 0;
        try {
        	Keyspace keyspaceOperator = HFactory.createKeyspace("note", cluster);
        	CounterQuery<String, String> counterQuery = HFactory.createCounterColumnQuery(keyspaceOperator, stringSerializer, stringSerializer);
        	counterQuery.setColumnFamily("counters");
        	counterQuery.setKey("counters");
        	counterQuery.setName("total");
        	QueryResult<HCounterColumn<String>> result =  counterQuery.execute();
        	HCounterColumn<String> column = result.get();
        	deviceId = column.getValue();
    	
        	Mutator<String> counterMutator = HFactory.createMutator(keyspaceOperator, stringSerializer);
            MutationResult mr = counterMutator.incrementCounter("counters", "counters", "total", count);
        } catch (HectorException e) {
            e.printStackTrace();
        }
        return deviceId;
    }
    
    public void fetchNote(List<UUID> keys)
    {
    	Keyspace keyspaceOperator = HFactory.createKeyspace("note", cluster);
        MultigetSliceQuery<UUID, String, String> sliceQuery = HFactory.createMultigetSliceQuery(keyspaceOperator, uuidSerializer, stringSerializer, stringSerializer);
        
        sliceQuery.setRange("", "", false, 1000);
        
        sliceQuery.setColumnFamily("note");            
        sliceQuery.setKeys(keys);
        //rangeSlicesQuery.setReturnKeysOnly();
        
        QueryResult<Rows<UUID, String, String>> result = sliceQuery.execute();
        
        Rows<UUID, String, String> rows = result.get();            
        
        
        for (Row<UUID, String, String> row : rows)
        {
	        ColumnSlice<String, String> columns = row.getColumnSlice();            
	        
	        List<HColumn<String, String>> columnList = columns.getColumns();
	        
	        for (HColumn<String, String> column : columnList)
	        {
	        	String name = column.getName();
	        	String value = column.getValue();
	
	        	System.out.println("Note key: " + row.getKey() + " name: " + name + " value: " + value);
	        }
        	System.out.println("***************************************************************");
        }
    }

    public List<MutationResult> insertNote(List<Map<String, String>> list) {
        List<MutationResult> results = new ArrayList<MutationResult>();
        for (Map<String, String> note : list){
            results.add(insertNote(note));
        }
        return results;
    }
}