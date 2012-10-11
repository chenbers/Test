package com.inthinc.device.noteservice;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

//import com.inthinc.pro.comm.parser.attrib.Attrib;

//public class NoteLoader implements Runnable {
public class NoteLoader {
//    private static Logger logger = LoggerFactory.getLogger(NoteLoader.class);
/*
    private static StringSerializer stringSerializer = StringSerializer.get();
    private static UUIDSerializer uuidSerializer = UUIDSerializer.get();
    private static IntegerSerializer integerSerializer = IntegerSerializer.get();
    private static LongSerializer longSerializer = LongSerializer.get();
    private static ByteBufferSerializer byteBufferSerializer = ByteBufferSerializer.get();
    private static Cluster cluster;
	private static Connection conn;
	private static int threadCountLimit = 10;
	private long deviceId;
	*/
    public static void main(String[] args) throws Exception {
/*    	String cassandraNodeAddress = "localhost:9161";
    	String mysqlURL = "jdbc:mysql://ec2-1.tiwipro.com:3306/salesreports?noAccessToProcedureBodies=true";
    	String mysqlUser = "bmiller";
    	String mysqlPassword = "mouse2zz";
*/

    	String cassandraNodeAddress = "localhost:9161";
    	String mysqlURL = "jdbc:mysql://weatherford-node0.tiwipro.com:3306/siloDB?noAccessToProcedureBodies=true";
    	String mysqlUser = "iridium";
    	String mysqlPassword = "aizahSu6ooGhu";

/*
    	if (args.length > 0)
    		cassandraNodeAddress = args[0];
    	if (args.length > 1)
    		threadCountLimit = Integer.parseInt(args[1]);
    	if (args.length > 2)
    		mysqlURL = args[2];
    	if (args.length > 3)
    		mysqlUser = args[3];
    	if (args.length > 4)
    		mysqlPassword = args[4];
    	
    	CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator(cassandraNodeAddress);
    	cassandraHostConfigurator.setAutoDiscoverHosts(true);
    	
    	NoteLoader.cluster = HFactory.getOrCreateCluster("inthinc", cassandraNodeAddress);
*/
		Connection conn = null;
    	
    	try {
			Class.forName("com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource");
//			NoteLoader.conn = DriverManager.getConnection(mysqlURL, mysqlUser, mysqlPassword);
			conn = DriverManager.getConnection(mysqlURL, mysqlUser, mysqlPassword);
    	} catch (Exception e) {
  //  		logger.error("MYSQL CONNECTION ERROR: " + e);
    		System.out.println("MYSQL CONNECTION ERROR: " + e);
    	}

			CallableStatement statement = null;
			ResultSet resultSet = null;
			try	{
				
	            statement = (CallableStatement) conn.prepareCall(" CALL hos_getDriverForEmpid('MCM112502', '151826')");
//	            statement.setLong(1, deviceId);

				resultSet = statement.executeQuery();
				
//				if (resultSet.next())
//					vehicleId = resultSet.getLong(1);
					
			}
			catch (Exception e)
			{ // handle database alerts in the usual manner
//				logger.error("Exception: " + e);
				System.out.println("Exception: " + e);
				e.printStackTrace();

			}	// end catch
			finally
			{ // clean up and release the connection
//				close(conn, statement, resultSet);
			} // end finally	
		
		
/*
    	Thread[] threadList = new Thread[threadCountLimit];
    	long deviceId = 0;
    	while (deviceId < 20000)
    	{
    		deviceId = NoteLoader.fetchNextDeviceId();
//    		logger.info("deviceId: " + deviceId);
    		
			try { 
				int i = 0;
				while (true)
				{
					if (threadList[i] == null || !threadList[i].isAlive())
					{
//						logger.info("Creating thread... " + i);
						threadList[i] = new Thread(new NoteLoader(deviceId));
						threadList[i].start();
						break;
					}
					
					i++;
					if (i == threadCountLimit)
					{
						Thread.sleep(1000);
						i = 0;
					}
				}
										
			} catch (Exception e)
			{
				logger.error("Processing exception: " + e);
				e.printStackTrace();
			}
    	}
        cluster.getConnectionManager().shutdown();
		try {
			conn.close();
		} catch (Exception e){}
    	
  */  	
    	
    }
/*    
    public NoteLoader(long deviceId)
    {
    	this.deviceId = deviceId;
    }

    public static long fetchNextDeviceId()
    {
    	long deviceId = 0;
        try {
        	Keyspace keyspaceOperator = HFactory.createKeyspace("note", cluster);
        	CounterQuery<String, String> counterQuery = HFactory.createCounterColumnQuery(keyspaceOperator, stringSerializer, stringSerializer);
        	counterQuery.setColumnFamily("counters");
        	counterQuery.setKey("counters");
        	counterQuery.setName("device");
        	QueryResult<HCounterColumn<String>> result =  counterQuery.execute();
        	HCounterColumn<String> column = result.get();
        	deviceId = column.getValue();
    	
        	Mutator<String> counterMutator = HFactory.createMutator(keyspaceOperator, stringSerializer);
            MutationResult mr = counterMutator.incrementCounter("counters", "counters", "device", 1);
        } catch (HectorException e) {
            e.printStackTrace();
        }
        return deviceId;
    }
    
    public void run()
    {
//		loadNotesForDeviceId(this.deviceId);
    	countNotesByDevice(this.deviceId);
    }
    
    public void insertNote(Properties attribs)
    {
        try {
        	Keyspace keyspaceOperator = HFactory.createKeyspace("note", cluster);
        	UUID noteId = UUID.randomUUID();
            Mutator<UUID> noteMutator = HFactory.createMutator(keyspaceOperator, uuidSerializer);
    	
            for (Map.Entry attrib : attribs.entrySet()) {
            	noteMutator.addInsertion(noteId, "note", HFactory.createColumn((String)attrib.getKey(), (String)attrib.getValue(), stringSerializer, stringSerializer));
            }
            MutationResult mr = noteMutator.execute();

            Long deviceId = new Long((String)attribs.get(String.valueOf(Attrib.DEVICEID.getCode())));
            Integer noteType = new Integer((String)attribs.get(String.valueOf(Attrib.NOTETYPE.getCode())));
            Integer noteTime = new Integer((String)attribs.get(String.valueOf(Attrib.NOTETIME.getCode())));
            Mutator<Long> indexMutator = HFactory.createMutator(keyspaceOperator, LongSerializer.get());
            
            indexMutator.addInsertion(deviceId, "deviceNoteTimeTypeIndex", HFactory.createColumn(Composite.toByteBuffer(noteTime, noteType), noteId, byteBufferSerializer, uuidSerializer));
            indexMutator.addInsertion(deviceId, "deviceNoteTypeTimeIndex", HFactory.createColumn(Composite.toByteBuffer(noteType, noteTime), noteId, byteBufferSerializer, uuidSerializer));
            mr = indexMutator.execute();

        } catch (HectorException e) {
            e.printStackTrace();
        }
    	
    	
    }
    
    private static final String FETCH_NOTE = "SELECT driverID, vehicleID, account_id, type, UNIX_TIMESTAMP(time), speed, odometer, state, coalesce(latitude, 0), coalesce(longitude, 0), coalesce(topSpeed, 0), coalesce(avgSpeed, 0), coalesce(speedLimit, 0), coalesce(distance, 0), coalesce(deltaX, 0), coalesce(deltaY, 0), coalesce(deltaZ, 0), attrs FROM note WHERE deviceID = ? ORDER BY deviceID, time";				
	private void loadNotesForDeviceId(long deviceId) {
			long driverId;
			long vehicleId;
			long accountId;
			int type;
			int time;
			int speed;
			long odometer;
			int state;
			double latitude;
			double longitude;
			int topSpeed;
			int avgSpeed;
			int speedLimit;
			int distance;
			int deltaX = 0;
			int deltaY = 0;
			int deltaZ = 0;
			String attrs;
		
			PreparedStatement statement = null;
			ResultSet resultSet = null;
			int counter = 0;
			try	{
				
	            statement = (PreparedStatement) conn.prepareStatement(FETCH_NOTE);
	            statement.setLong(1, deviceId);

				resultSet = statement.executeQuery();
				
				while (resultSet.next())
				{
					counter++;
					
					driverId = resultSet.getInt(1);
					vehicleId = resultSet.getInt(2);
					accountId = resultSet.getInt(3);
					type = resultSet.getInt(4);
					time = resultSet.getInt(5);
					speed = resultSet.getInt(6);
					odometer = resultSet.getLong(7);
					state = resultSet.getInt(8);
					latitude = resultSet.getDouble(9);
					longitude = resultSet.getDouble(10);
					topSpeed = resultSet.getInt(11);
					avgSpeed = resultSet.getInt(12);
					speedLimit = resultSet.getInt(13);
					distance = resultSet.getInt(14);
		
					if (deltaX != 0 && deltaY != 0 && deltaZ != 0)
					{
						deltaX = resultSet.getInt(15);
						deltaY = resultSet.getInt(16);
						deltaZ = resultSet.getInt(17);
					}
					
					attrs = resultSet.getString(18);

					Properties attribMap = new Properties();

					if (attrs != null)
					{
						if (attrs.substring(0, 1).equalsIgnoreCase(";"))
							attrs = attrs.substring(1);
						
						attrs.replace(';', '\r');
						attribMap.load(new ByteArrayInputStream(attrs.getBytes()));
					}
					
					attribMap.put(String.valueOf(Attrib.DEVICEID.getCode()), String.valueOf(deviceId));					
					attribMap.put(String.valueOf(Attrib.DRIVERID.getCode()), String.valueOf(driverId));					
					attribMap.put(String.valueOf(Attrib.VEHICLEID.getCode()), String.valueOf(vehicleId));					
					attribMap.put(String.valueOf(Attrib.ACCOUNTID.getCode()), String.valueOf(accountId));					
					attribMap.put(String.valueOf(Attrib.NOTETYPE.getCode()), String.valueOf(type));					
					attribMap.put(String.valueOf(Attrib.NOTETIME.getCode()), String.valueOf(time));					
					attribMap.put(String.valueOf(Attrib.NOTESPEED.getCode()), String.valueOf(speed));					
					attribMap.put(String.valueOf(Attrib.NOTEODOMETER.getCode()), String.valueOf(odometer));
					attribMap.put(String.valueOf(Attrib.BOUNDARYID.getCode()), String.valueOf(state));
					attribMap.put(String.valueOf(Attrib.NOTELATLONG.getCode()), String.valueOf(latitude) + ":" + String.valueOf(longitude));
					attribMap.put(String.valueOf(Attrib.TOPSPEED.getCode()), String.valueOf(topSpeed));
					attribMap.put(String.valueOf(Attrib.AVGSPEED.getCode()), String.valueOf(avgSpeed));
					attribMap.put(String.valueOf(Attrib.NOTESPEEDLIMIT.getCode()), String.valueOf(speedLimit));
					attribMap.put(String.valueOf(Attrib.DISTANCE.getCode()), String.valueOf(distance));
					attribMap.put(String.valueOf(Attrib.DVX.getCode()), String.valueOf(deltaX));
					attribMap.put(String.valueOf(Attrib.DVY.getCode()), String.valueOf(deltaY));
					attribMap.put(String.valueOf(Attrib.DVZ.getCode()), String.valueOf(deltaZ));

					insertNote(attribMap);

				}	
				logger.info("DeviceId: " + deviceId + " Records loaded: " + counter);
			}
			catch (Exception e)
			{ // handle database alerts in the usual manner
				logger.error("Exception: " + e);
				e.printStackTrace();

			}	// end catch
			finally
			{ // clean up and release the connection
//					socket.close();
				try {
					resultSet.close();
					statement.close();
				} catch (Exception e){}
			} // end finally					
	}
    
/*    
    public void createNotes()
    {
    	HashMap<String, String> attribs = new HashMap<String, String>();
    	attribs.put(String.valueOf(Attrib.DEVICEID.getCode()), "1234567");
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
		insertNote(attribs);

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
		insertNote(attribs);
		
		
		fetchNotesByTime(new Long(1234567), new Integer(0), new Integer(0));
		fetchNotesByTime(new Long(12345678), new Integer(1313978398), new Integer(1313978400));
		fetchNotesByTime(new Long(1234567), new Integer(1313978398), new Integer(1313978400));
		
//        cluster.getConnectionManager().shutdown();
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

    private static long totalCount(int count)
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
    
    public static byte[] asByteArray(java.util.UUID uuid)
    {
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        byte[] buffer = new byte[16];

        for (int i = 0; i < 8; i++) {
                buffer[i] = (byte) (msb >>> 8 * (7 - i));
        }
        for (int i = 8; i < 16; i++) {
                buffer[i] = (byte) (lsb >>> 8 * (7 - i));
        }

        return buffer;
    }
*/	
}
